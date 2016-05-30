package jp.co.transcosmos.mock.vo;

// テーブル別名＋フィールド名で生成される別名が、３０文字を超える場合、
// ３０文字以内に切り詰められるので別名が重複する可能性がある。
// その場合、２個目以降の末尾は、000、001、002 の様に連番が付与される。
// この VO は、そのテスト用
public class TestLongNameVo {

	private String col1Name;		// 通常長さのフィールド名
	private String col2Name;		// 通常長さのフィールド名
	private String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA;
	private String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB;
	private String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC;
	private String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD;
	private String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxE;


	
	public String getCol1Name() {
		return col1Name;
	}
	public void setCol1Name(String col1Name) {
		this.col1Name = col1Name;
	}
	public String getCol2Name() {
		return col2Name;
	}
	public void setCol2Name(String col2Name) {
		this.col2Name = col2Name;
	}
	public String getXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA() {
		return xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA;
	}
	public void setXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA(
			String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA) {
		this.xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA = xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA;
	}
	public String getXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB() {
		return xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB;
	}
	public void setXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB(
			String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB) {
		this.xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB = xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB;
	}
	public String getXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC() {
		return xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC;
	}
	public void setXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC(
			String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC) {
		this.xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC = xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC;
	}
	public String getXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD() {
		return xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD;
	}
	public void setXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD(
			String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD) {
		this.xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD = xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD;
	}
	public String getXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxE() {
		return xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxE;
	}
	public void setXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxE(
			String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxE) {
		this.xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxE = xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxE;
	}
	
}
