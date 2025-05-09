package com.share.lottery;


/**
 * Constant values used throughout the application.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public final class Constants {

    private Constants() {
        // hide me
    }
    
    public final static String EMAIL_OPT_IN = "splitlottery";
    
	public final static String ALL_BUYS = "all_buyers";
	public final static String ALL_TOP_LISTERS = "top_listers_all";
	public final static String RANDOM_PLAYERS = "random_players";

   
    //~ Static fields/initializers =============================================

    /**
     * Assets Version constant
     */
    public static final String ASSETS_VERSION = "assetsVersion";
    /**
     * The name of the ResourceBundle used in this application
     */
    public static final String BUNDLE_KEY = "ApplicationResources";

    /**
     * File separator from System properties
     */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * User home from System properties
     */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";

    /**
     * Session scope attribute that holds the locale set by the user. By setting this key
     * to the same one that Struts uses, we get synchronization in Struts w/o having
     * to do extra work or have two session-level variables.
     */
    public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

    /**
     * The request scope attribute under which an editable user form is stored
     */
    public static final String USER_KEY = "userForm";

    /**
     * The request scope attribute that holds the user list
     */
    public static final String USER_LIST = "userList";

    /**
     * The request scope attribute for indicating a newly-registered user
     */
    public static final String REGISTERED = "registered";

    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    /**
     * The name of the User role, as specified in web.xml
     */
    public static final String USER_ROLE = "ROLE_USER";
    
    public static final String ROLE_CAMPAIGN = "ROLE_CAMPAIGN";

    /**
     * The name of the user's role list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String USER_ROLES = "userRoles";

    /**
     * The name of the available roles list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";

    /**
     * The name of the CSS Theme setting.
     * @deprecated No longer used to set themes.
     */
    public static final String CSS_THEME = "csstheme";
    
    public static class BuyLottery{
    	public static final int OUT_OF_TICKETS = -1;
    	public static final int OUT_OF_CASH = -2;
    	public static final int REFERER_MAIL = -3;
    	 
    	public static final int END_FREEBIE = -4;
    	public static final int FREEBIE = -5;
    	
    	public static final int FREEBIE_ERROR = -6;
    	public static final int DUPLICATE_PURCHASE = -7;
    }
    
    public static class ListLottery {
    	public static final long UNSUPPORTED_BARCODE = -1;
    	public static final long DUPLICATE = -2;
    	public static final long EXPIRED = -3;
    }
    
}
