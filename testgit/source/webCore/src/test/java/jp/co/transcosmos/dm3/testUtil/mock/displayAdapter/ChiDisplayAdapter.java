package jp.co.transcosmos.dm3.testUtil.mock.displayAdapter;

public class ChiDisplayAdapter extends BaseDisplayAdapter {

	// ˆÏ÷æ‚Ì’l‚ğ‰ÁHˆ—
	@Override
	public String getFieldName11(BaseVo targetVo){
		return targetVo.getFieldName11() + "STU";
	}

	// ˆÏ÷æ‚Ì’l‚ğ‰ÁHˆ—
	@Override
	public Integer getFieldName12(BaseVo targetVo){
		return targetVo.getFieldName12() + 300;
	}

	// ˆÏ÷æ‚Ì’l‚ğ‰ÁHˆ—
	@Override
	public int getFieldName13(BaseVo targetVo){
		return targetVo.getFieldName13() + 355;
	}

	// ˆÏ÷æ‚Ì’l‚ğ‰ÁHˆ—
	public String getFieldName1(ChiOtherVo targetVo){
		return targetVo.getFieldName1() + "RRR";
	}
	
	// ˆÏ÷æ‚Ì’l‚ğ‰ÁHˆ—
	public String getFieldName11(ChiOtherVo targetVo){
		return targetVo.getFieldName11() + "GGG";
	}

}
