package jp.co.transcosmos.dm3.testUtil.mock.displayAdapter;

import jp.co.transcosmos.dm3.core.displayAdapter.DisplayAdapter;

public class BaseDisplayAdapter extends DisplayAdapter {


	// �Ϗ���̒l�����H����
	public String getFieldName11(BaseVo targetVo){
		return targetVo.getFieldName11() + "XYZ";
	}

	// �Ϗ���̒l�����H����
	public Integer getFieldName12(BaseVo targetVo){
		return targetVo.getFieldName12() + 50;
	}

	// �Ϗ���̒l�����H����
	public int getFieldName13(BaseVo targetVo){
		return targetVo.getFieldName13() + 55;
	}

	// �Ϗ���̒l�����H����
	public String getFieldName1(OtherVo targetVo){
		return targetVo.getFieldName1() + "TTT";
	}
	
}
