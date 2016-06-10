package jp.co.transcosmos.mock.vo;

public class ReflectionUtilsTestVo2 extends ReflectionUtilsTestVo {

	private String fieldName11;
	private Integer fieldName12;
	private Boolean fieldName13;
	private Double fieldName14;
	private boolean fieldName15;
	protected String fieldName16;				// 外部非公開メソッド

	
	
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
	public Boolean getFieldName13() {
		return fieldName13;
	}
	public void setFieldName13(Boolean fieldName13) {
		this.fieldName13 = fieldName13;
	}
	public Double getFieldName14() {
		return fieldName14;
	}
	public void setFieldName14(Double fieldName14) {
		this.fieldName14 = fieldName14;
	}
	public boolean isFieldName15() {
		return fieldName15;
	}
	public void setFieldName15(boolean fieldName15) {
		this.fieldName15 = fieldName15;
	}

}
