package jp.co.transcosmos.dm3.testUtil.mock.displayAdapter;

public class BaseVo {

	private String fieldName1;			// 非オーバーライドテスト用
	private Integer fieldName2;			// 非オーバーライドテスト用
	private int fieldName3;				// 非オーバーライドテスト用

	private String fieldName11;			// オーバーライドテスト用
	private Integer fieldName12;		//　オーバーライドテスト用
	private int fieldName13;			//　オーバーライドテスト用

	
	
	public String getFieldName1() {
		return fieldName1;
	}
	public void setFieldName1(String fieldName1) {
		this.fieldName1 = fieldName1;
	}
	public Integer getFieldName2() {
		return fieldName2;
	}
	public void setFieldName2(Integer fieldName2) {
		this.fieldName2 = fieldName2;
	}
	public int getFieldName3() {
		return fieldName3;
	}
	public void setFieldName3(int fieldName3) {
		this.fieldName3 = fieldName3;
	}
	public String getFieldName11() {
		return fieldName11;
	}
	public void setFieldName11(String fieldName11) {
		this.fieldName11 = fieldName11;
	}
	public Integer getFieldName12() {
		return fieldName12;
	}
	public void setFieldName12(Integer fieldName12) {
		this.fieldName12 = fieldName12;
	}
	public int getFieldName13() {
		return fieldName13;
	}
	public void setFieldName13(int fieldName13) {
		this.fieldName13 = fieldName13;
	}
	
}
