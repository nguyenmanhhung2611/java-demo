package jp.co.transcosmos.mock.form;

import jp.co.transcosmos.dm3.form.annotation.UsePagingParam;

public class ArrayForm2 extends ArrayForm1 {

	@UsePagingParam
	private String[] field4;
	
	private String[] field5;

	@UsePagingParam
	private String[] field6;

	public String[] getField4() {
		return field4;
	}

	public void setField4(String[] field4) {
		this.field4 = field4;
	}

	public String[] getField5() {
		return field5;
	}

	public void setField5(String[] field5) {
		this.field5 = field5;
	}

	public String[] getField6() {
		return field6;
	}

	public void setField6(String[] field6) {
		this.field6 = field6;
	}

}
