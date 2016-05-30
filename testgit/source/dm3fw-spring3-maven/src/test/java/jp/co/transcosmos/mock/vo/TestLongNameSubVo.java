package jp.co.transcosmos.mock.vo;

// テーブル別名＋フィールド名で生成される別名が、３０文字を超える場合、
// ３０文字以内に切り詰められるので別名が重複する可能性がある。
// その場合、２個目以降の末尾は、000、001、002 の様に連番が付与される。
// この VO は、そのテスト用
public class TestLongNameSubVo {

	private String subCol1Name;		// 通常長さのフィールド名
	private String subCol2Name;		// 通常長さのフィールド名
	private String subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA;
	private String subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB;
	private String subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC;
	private String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD;		// 重複フィールド名
	private String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxE;		// 重複フィールド名



	public String getSubCol1Name() {
		return subCol1Name;
	}
	public void setSubCol1Name(String subCol1Name) {
		this.subCol1Name = subCol1Name;
	}
	public String getSubCol2Name() {
		return subCol2Name;
	}
	public void setSubCol2Name(String subCol2Name) {
		this.subCol2Name = subCol2Name;
	}
	public String getSubXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA() {
		return subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA;
	}
	public void setSubXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA(
			String subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA) {
		this.subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA = subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA;
	}
	public String getSubXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB() {
		return subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB;
	}
	public void setSubXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB(
			String subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB) {
		this.subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB = subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB;
	}
	public String getSubXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC() {
		return subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC;
	}
	public void setSubXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC(
			String subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC) {
		this.subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC = subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC;
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
