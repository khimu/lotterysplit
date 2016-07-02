package com.share.lottery.webapp.taglib;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class YearTag extends TagSupport {

	private String date;

    /**
     * Main method that does processing and exposes Constants in specified scope 
     * @return int
     * @throws JspException if processing fails
     */
    @Override
    public int doStartTag() throws JspException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhha");
        
        String time = dateFormat.format(date);          
        String year = time.substring(0, 4);

        try
        {
            JspWriter out = pageContext.getOut();
            HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
            out.write(year);
        }
        catch(Exception e)
        {   
            throw new JspException(e.getMessage());
        }
        return EVAL_PAGE;
    }

	public void setDate(String date) {
		this.date = date;
	}

}
