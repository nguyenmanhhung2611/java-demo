package jp.co.transcosmos.dm3.testUtil.mock.displayAdapter;

public class ChiDisplayAdapter2 extends BaseDisplayAdapter {

	
	public String getFieldName21(ChiBaseVo targetVo) {
		return targetVo.getFieldName21() + "UUU";
	}
	public Integer getFieldName22(ChiBaseVo targetVo) {
		return targetVo.getFieldName22() + 400;
	}
	public int getFieldName23(ChiBaseVo targetVo) {
		return targetVo.getFieldName23() + 440;
	}
	
}
