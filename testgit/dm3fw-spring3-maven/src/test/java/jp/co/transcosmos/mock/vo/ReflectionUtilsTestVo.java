package jp.co.transcosmos.mock.vo;

public class ReflectionUtilsTestVo {

	private String fieldName1;
	private Integer fieldName2;
	private Boolean fieldName3;
	private Double fieldName4;
	private boolean fieldName5;
	protected String fieldName6;				// 外部非公開メソッド
	
	
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
	public Boolean getFieldName3() {
		return fieldName3;
	}
	public void setFieldName3(Boolean fieldName3) {
		this.fieldName3 = fieldName3;
	}
	public Double getFieldName4() {
		return fieldName4;
	}
	public void setFieldName4(Double fieldName4) {
		this.fieldName4 = fieldName4;
	}
	public boolean isFieldName5() {
		return fieldName5;
	}
	public void setFieldName5(boolean fieldName5) {
		this.fieldName5 = fieldName5;
	}
	
}
