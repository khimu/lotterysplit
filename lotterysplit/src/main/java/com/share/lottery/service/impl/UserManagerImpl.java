package com.share.lottery.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.lottery.dao.IBalanceDao;
import com.share.lottery.dao.UserDao;
import com.share.lottery.model.Role;
import com.share.lottery.model.SessionDTO;
import com.share.lottery.model.User;
import com.share.lottery.service.MailEngine;
import com.share.lottery.service.UserExistsException;
import com.share.lottery.service.UserManager;
import com.share.lottery.service.UserService;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Transactional(rollbackFor=Exception.class, value = "lotterySplitTransactionManager")
@Service("userManager")
@WebService(serviceName = "UserService", endpointInterface = "com.share.lottery.service.UserService")
public class UserManagerImpl extends GenericManagerImpl<User, Long> implements UserManager, UserService {
	private final static Logger logger = Logger.getLogger(UserManagerImpl.class);
	private PasswordEncoder passwordEncoder;
    private UserDao userDao;
    
    @Autowired
    private IBalanceDao balanceDao;

    private MailEngine mailEngine;
    private SimpleMailMessage message;
    private PasswordTokenManager passwordTokenManager;

    private String passwordRecoveryTemplate = "passwordRecovery.vm";
    private String passwordUpdatedTemplate = "passwordUpdated.vm";

    @Autowired
    @Qualifier("passwordEncoder")
    public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Autowired
    public void setUserDao(final UserDao userDao) {
        this.dao = userDao;
        this.userDao = userDao;
    }

    @Autowired(required = false)
    public void setMailEngine(final MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    @Autowired(required = false)
    public void setMailMessage(final SimpleMailMessage message) {
        this.message = message;
    }

    @Autowired(required = false)
    public void setPasswordTokenManager(final PasswordTokenManager passwordTokenManager) {
        this.passwordTokenManager = passwordTokenManager;
    }

    /**
     * Velocity template name to send users a password recovery mail (default
     * passwordRecovery.vm).
     *
     * @param passwordRecoveryTemplate the Velocity template to use (relative to classpath)
     * @see com.share.lottery.service.MailEngine#sendMessage(org.springframework.mail.SimpleMailMessage, String, java.util.Map)
     */
    public void setPasswordRecoveryTemplate(final String passwordRecoveryTemplate) {
        this.passwordRecoveryTemplate = passwordRecoveryTemplate;
    }

    /**
     * Velocity template name to inform users their password was updated
     * (default passwordUpdated.vm).
     *
     * @param passwordUpdatedTemplate the Velocity template to use (relative to classpath)
     * @see com.share.lottery.service.MailEngine#sendMessage(org.springframework.mail.SimpleMailMessage, String, java.util.Map)
     */
    public void setPasswordUpdatedTemplate(final String passwordUpdatedTemplate) {
        this.passwordUpdatedTemplate = passwordUpdatedTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(final String userId) {
        return userDao.get(new Long(userId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getUsers() {
        return userDao.getAllDistinct();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public User saveNewUser(User user) throws UserExistsException {

        if (user.getVersion() == null) {
            // if new user, lowercase userId
            user.setUsername(user.getUsername().toLowerCase());
        }

        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (passwordEncoder != null) {
            // Check whether we have to encrypt (or re-encrypt) the password
            if (user.getVersion() == null) {
                // New user, always encrypt
                passwordChanged = true;
            } else {
                // Existing user, check password in DB
                final String currentPassword = userDao.getUserPassword(user.getId());
                if (currentPassword == null) {
                    passwordChanged = true;
                } else {
                    if (!currentPassword.equals(user.getPassword())) {
                        passwordChanged = true;
                    }
                }
            }

            // If password was changed (or new user), encrypt it
            if (passwordChanged) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        } else {
            log.warn("PasswordEncoder not set, skipping password encryption...");
        }

        try {
            return userDao.saveUser(user);
        } catch (final Exception e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public User saveUser(final User user) throws UserExistsException {

        if (user.getVersion() == null) {
            // if new user, lowercase userId
            user.setUsername(user.getUsername().toLowerCase());
        }

        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (passwordEncoder != null) {
            // Check whether we have to encrypt (or re-encrypt) the password
            if (user.getVersion() == null) {
                // New user, always encrypt
                passwordChanged = true;
            } else {
                // Existing user, check password in DB
                final String currentPassword = userDao.getUserPassword(user.getId());
                if (currentPassword == null) {
                    passwordChanged = true;
                } else {
                    if (passwordEncoder.matches(currentPassword, user.getPassword()) == false) {
                        passwordChanged = true;
                    }
                }
            }

            // If password was changed (or new user), encrypt it
            if (passwordChanged) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        } else {
            log.warn("PasswordEncoder not set, skipping password encryption...");
        }

        try {
            return userDao.saveUser(user);
        } catch (final Exception e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        }
    }
    
    public void updateReferralCount(String referralCode){
    	userDao.updateReferralCount(referralCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUser(final User user) {
        log.debug("removing user: " + user);
        userDao.remove(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUser(final String userId) {
        log.debug("removing user: " + userId);
        userDao.remove(new Long(userId));
    }

    /**
     * {@inheritDoc}
     *
     * @param username the login name of the human
     * @return User the populated user object
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException thrown when username not found
     */
    @Override
    public User getUserByUsername(final String username) throws UsernameNotFoundException {
        return (User) userDao.loadUserByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> search(final String searchTerm) {
        return super.search(searchTerm, User.class);
    }

    @Override
    public String buildRecoveryPasswordUrl(final User user, final String urlTemplate) {
        final String token = generateRecoveryToken(user);
        final String username = user.getUsername();
        return StringUtils.replaceEach(urlTemplate,
                new String[]{"{username}", "{token}"},
                new String[]{username, token});
    }

    @Override
    public String generateRecoveryToken(final User user) {
        return passwordTokenManager.generateRecoveryToken(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRecoveryTokenValid(final String username, final String token) {
        return isRecoveryTokenValid(getUserByUsername(username), token);
    }

    @Override
    public boolean isRecoveryTokenValid(final User user, final String token) {
        return passwordTokenManager.isRecoveryTokenValid(user, token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPasswordRecoveryEmail(final String username, final String urlTemplate) {
        log.debug("Sending password recovery token to user: " + username);

        final User user = getUserByUsername(username);
        final String url = buildRecoveryPasswordUrl(user, urlTemplate);

        sendUserEmail(user, passwordRecoveryTemplate, url);
    }

    private void sendUserEmail(final User user, final String template, final String url) {
        message.setTo(user.getFullName() + "<" + user.getEmail() + ">");

        final Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("user", user);
        model.put("applicationURL", url);

        mailEngine.sendMessage(message, template, model);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public User updatePassword(final String username, final String currentPassword, final String recoveryToken, final String newPassword, final String applicationUrl) throws UserExistsException {
        User user = getUserByUsername(username);
        if (isRecoveryTokenValid(user, recoveryToken)) {
            log.debug("Updating password from recovery token for user:" + username);
            user.setPassword(newPassword);
            user = saveUser(user);
            passwordTokenManager.invalidateRecoveryToken(user, recoveryToken);

            sendUserEmail(user, passwordUpdatedTemplate, applicationUrl);

            return user;
        } else if (StringUtils.isNotBlank(currentPassword)) {
            if (passwordEncoder.matches(currentPassword, user.getPassword())) {
                log.debug("Updating password (providing current password) for user:" + username);
                user.setPassword(newPassword);
                user = saveUser(user);
                return user;
            }
        }
        // or throw exception
        return null;
    }
    
	@Override
	public SessionDTO authenticate(String username, String password) {
		logger.debug("authenticate username " + username + " password ");
		User user = getUserByUsername(username);
		
		/*
		if(user.isAccountExpired() || user.isAccountLocked() || user.isEnabled() == false){
			return null;
		}
		*/
		
		
		// if passwords do not match, throw Invalid Credential Exception
		if (user == null || passwordEncoder.matches(password, user.getPassword()) == false) {
			logger.debug("password did not match encoded password " + passwordEncoder.matches(password, user.getPassword()));
			logger.debug("or user is null");
			return null;
		}
		
		 Set<Role> roles = user.getRoles();
		 Iterator<Role> itr = roles.iterator();
		 while(itr.hasNext()){
			Role role = (Role) itr.next();
			if(role.getName().equals("ROLE_ADMIN")){
				return new SessionDTO( user.getId(),  user.getEmail(), user.getReferralCode(), true, true);
			}
		 }
		 
		 itr = roles.iterator();
		 while(itr.hasNext()){
			Role role = (Role) itr.next();
			if(role.getName().equals("ROLE_CAMPAIGN")){
				return new SessionDTO( user.getId(),  user.getEmail(), user.getReferralCode(), false, true);
			}
		 }
		 
		 logger.debug("returning a session");
		return new SessionDTO( user.getId(),  user.getEmail(), user.getReferralCode(), false, false);
	}

	@Override
	public SessionDTO authenticate(String username) {
		logger.debug("username is " + username);
		User user = getUserByUsername(username);
		
		
		 Set<Role> roles = user.getRoles();
		 Iterator<Role> itr = roles.iterator();
		 while(itr.hasNext()){
			Role role = (Role) itr.next();
			if(role.getName().equals("ROLE_ADMIN")){
				return new SessionDTO( user.getId(),  user.getEmail(), user.getReferralCode(), true, true);
			}
		 }
		 
		 itr = roles.iterator();
		 while(itr.hasNext()){
			Role role = (Role) itr.next();
			if(role.getName().equals("ROLE_CAMPAIGN")){
				return new SessionDTO( user.getId(),  user.getEmail(), user.getReferralCode(), false, true);
			}
		 }
		 
		return new SessionDTO( user.getId(),  user.getEmail(), user.getReferralCode(), false, false);
	}

	@Override
	public User findByReferralCode(String referralCode) {
		return userDao.findByReferralCode(referralCode);
	}

	@Override
	public List<Long> findAllUserForEmails(String emails) {
		return userDao.findAllUserForEmails(emails);
	}

}
