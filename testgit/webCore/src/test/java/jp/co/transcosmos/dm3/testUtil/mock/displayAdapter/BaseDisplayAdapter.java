package jp.co.transcosmos.dm3.testUtil.mock.displayAdapter;

import jp.co.transcosmos.dm3.core.displayAdapter.DisplayAdapter;

public class BaseDisplayAdapter extends DisplayAdapter {


	// ˆÏ÷æ‚Ì’l‚ğ‰ÁHˆ—
	public String getFieldName11(BaseVo targetVo){
		return targetVo.getFieldName11() + "XYZ";
	}

	// ˆÏ÷æ‚Ì’l‚ğ‰ÁHˆ—
	public Integer getFieldName12(BaseVo targetVo){
		return targetVo.getFieldName12() + 50;
	}

	// ˆÏ÷æ‚Ì’l‚ğ‰ÁHˆ—
	public int getFieldName13(BaseVo targetVo){
		return targetVo.getFieldName13() + 55;
	}

	// ˆÏ÷æ‚Ì’l‚ğ‰ÁHˆ—
	public String getFieldName1(OtherVo targetVo){
		return targetVo.getFieldName1() + "TTT";
	}
	
}
