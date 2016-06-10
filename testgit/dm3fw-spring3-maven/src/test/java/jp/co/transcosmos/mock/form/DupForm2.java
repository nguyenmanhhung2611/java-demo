package jp.co.transcosmos.mock.form;

import jp.co.transcosmos.dm3.form.annotation.UsePagingParam;

public class DupForm2 extends DupForm1 {

	
	@UsePagingParam
	private String field1;

	private String field2;
	
	@UsePagingParam
	private String[] field3;

	private String[] field4;

	
	
	@Override
	public String getField1() {
		return field1;
	}

	@Override
	public void setField1(String field1) {
		super.setField1(field1+"_OYA");
		this.field1 = field1;
	}

	@Override
	public String getField2() {
		return field2;
	}

	@Override
	public void setField2(String field2) {
		super.setField2(field2+"_OYA");
		this.field2 = field2;
	}

	@Override
	public String[] getField3() {
		return field3;
	}

	@Override
	public void setField3(String[] field3) {
		
		String[] cpField3 = new String[field3.length];
		int idx = 0;
		for (String val : field3){
			cpField3[idx] = val + "_OYA";
			++idx;
		}

		super.setField3(cpField3);
		this.field3 = field3;
	}

	@Override
	public String[] getField4() {
		return field4;
	}

	@Override
	public void setField4(String[] field4) {
		String[] cpField4 = new String[field4.length];
		int idx = 0;
		for (String val : field4){
			cpField4[idx] = val + "_OYA";
			++idx;
		}

		super.setField4(cpField4);
		
		this.field4 = field4;
	}

}
