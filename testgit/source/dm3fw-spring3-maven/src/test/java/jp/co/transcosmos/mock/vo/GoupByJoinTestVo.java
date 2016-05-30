package jp.co.transcosmos.mock.vo;

import jp.co.transcosmos.dm3.dao.annotation.DaoAlias;
import jp.co.transcosmos.dm3.dao.annotation.Function;

// Group by �󂯎��p VO�@�iReflectingDAO test �p�j
public class GoupByJoinTestVo {

	private String col1Name;						// TestLongNameVo �̒ʏ풷���t�B�[���h
	private String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA;	// TestLongNameVo �̒����I�[�o�[�t�B�[���h

	@DaoAlias("dao2")
	private String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD;	// �d���t�B�[���h��

	@Function(columnName="col1Name", functionName=Function.FunctionName.MAX)
	private String cnt;								// TestLongNameVo ����`�t�B�[���h

	@Function(columnName="col2Name", functionName=Function.FunctionName.MIN)
	private String cnt2;							// TestLongNameVo ����`�t�B�[���h

	@DaoAlias("dao2")
	@Function(columnName="xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD", functionName=Function.FunctionName.SUM)
	private String mx;								// �d���t�B�[���h�Ɋ֐��g�p

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
	public String getXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD() {
		return xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD;
	}
	public void setXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD(
			String xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD) {
		this.xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD = xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD;
	}
	public String getMx() {
		return mx;
	}
	public void setMx(String mx) {
		this.mx = mx;
	}
	public String getCnt3() {
		return cnt3;
	}
	public void setCnt3(String cnt3) {
		this.cnt3 = cnt3;
	}
	
}

