package com.share.lottery.webapp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.share.lottery.model.SessionDTO;

public class BaseController extends BaseFormController {
	
	protected static final ObjectMapper objectMapper = new ObjectMapper();
	
	protected Logger logger = null;
	
	@Autowired
	protected String accessToken;
	
	/**
	 * Instantiates a new base portal controller.
	 *
	 * @param clazz the clazz
	 */
	public BaseController(Class<?> clazz) {
		logger = Logger.getLogger(clazz);
	}
	
	public SessionDTO getSessionObject(HttpSession session) {
		if (session == null) {
			return null;
		}
		return (SessionDTO)session.getAttribute("SESSION_OBJECT");
	}
	
	public void setSessionObject(HttpSession session, SessionDTO sessionDTO) {
		session.setAttribute("SESSION_OBJECT", sessionDTO);
	}
	
	public void setModel(Map<String, Object> model, String referralCode){
		model.put("appId", "259008874473779");
		model.put("accessToken", accessToken);
		model.put("referralCode", referralCode);
	}
	
	public void setModel(Map<String, Object> model){
		model.put("appId", "259008874473779");
		model.put("accessToken", accessToken);
	}

	/**
	 * Output.
	 *
	 * @param json the json
	 * @param resp the resp
	 */
	public void output(String json, HttpServletResponse resp){
		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			resp.setContentType("application/json");
			
			//JsonNode node = objectMapper.readTree(json);
			
			out.write(json.getBytes());

			resp.setContentLength(json.toString().length());
			resp.setHeader("Cache-Control","no-cache");
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					//
					logger.error(e.getMessage());
				}
			}
		}
	}

	public void output(Map<String, Object> model, HttpServletResponse resp){
		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			resp.setContentType("application/json");
			
			String json = objectMapper.writeValueAsString(model);			
			out.write(json.getBytes());

			resp.setContentLength(json.toString().length());
			resp.setHeader("Cache-Control","no-cache");
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					//
					logger.error(e.getMessage());
				}
			}
		}
	}

}
