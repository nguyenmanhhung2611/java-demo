package jp.co.transcosmos.mock.vo;

import jp.co.transcosmos.dm3.dao.annotation.Function;

// Group by 受け取り用 VO　（ReflectingDAO test 用）
public class GoupByRefTestVo {

	private String col1Name;						// TestLongNameVo の通常長さフィールド
	private String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA;	// TestLongNameVo の長さオーバーフィールド

	@Function(columnName="col1Name", functionName=Function.FunctionName.MAX)
	private String cnt;								// TestLongNameVo 未定義フィールド

	@Function(columnName="col2Name", functionName=Function.FunctionName.MIN)
	private String cnt2;							// TestLongNameVo 未定義フィールド

	@Function(columnName="*", functionName=Function.FunctionName.COUNT)
	private String cnt3;
	
	public String getCol1Name() {
		return col1Name;
	}
	public void setCol1Name(String col1Name) {
		this.col1Name = col1Name;
	}
	public String getXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA() {
		return xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA;
	}
	public void setXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA(
			String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA) {
		this.xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA = xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA;
	}
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	public String getCnt2() {
		return cnt2;
	}
	public void setCnt2(String cnt2) {
		this.cnt2 = cnt2;
	}
	public String getCnt3() {
		return cnt3;
	}
	public void setCnt3(String cnt3) {
		this.cnt3 = cnt3;
	}
	
}

