package jp.co.transcosmos.mock.form;

import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.form.annotation.UsePagingParam;

public class ArrayForm1 extends PagingListForm<Object> {

	@UsePagingParam
	private String[] field1;
	
	private String[] field2;

	@UsePagingParam
	private String[] field3;

	
	public String[] getField1() {
		return field1;
	}

	
	public void setField1(String[] field1) {
		this.field1 = field1;
	}

	public String[] getField2() {
		return field2;
	}

	public void setField2(String[] field2) {
		this.field2 = field2;
	}

	public String[] getField3() {
		return field3;
	}

	public void setField3(String[] field3) {
		this.field3 = field3;
	}

}
