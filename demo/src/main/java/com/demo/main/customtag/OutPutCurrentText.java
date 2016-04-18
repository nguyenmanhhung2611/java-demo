package com.demo.main.customtag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class OutPutCurrentText extends SimpleTagSupport {
	
	StringWriter ws = new StringWriter();
	
	public void doTag() throws JspException, IOException {
		getJspBody().invoke(ws);
		getJspContext().getOut().print(ws);
	}
}
