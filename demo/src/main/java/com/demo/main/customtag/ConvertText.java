package com.demo.main.customtag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ConvertText extends SimpleTagSupport {

	private int number;
	private String name;

	public void setNumber(int number) {
		this.number = number;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		out.print("Your number:asdasda " + number + "  || name :" + name);
	}

}
