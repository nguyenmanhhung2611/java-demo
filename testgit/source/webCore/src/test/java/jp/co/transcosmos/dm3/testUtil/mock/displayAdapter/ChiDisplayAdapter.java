package jp.co.transcosmos.dm3.testUtil.mock.displayAdapter;

public class ChiDisplayAdapter extends BaseDisplayAdapter {

	// �Ϗ���̒l�����H����
	@Override
	public String getFieldName11(BaseVo targetVo){
		return targetVo.getFieldName11() + "STU";
	}

	// �Ϗ���̒l�����H����
	@Override
	public Integer getFieldName12(BaseVo targetVo){
		return targetVo.getFieldName12() + 300;
	}

	// �Ϗ���̒l�����H����
	@Override
	public int getFieldName13(BaseVo targetVo){
		return targetVo.getFieldName13() + 355;
	}

	// �Ϗ���̒l�����H����
	public String getFieldName1(ChiOtherVo targetVo){
		return targetVo.getFieldName1() + "RRR";
	}
	
	// �Ϗ���̒l�����H����
	public String getFieldName11(ChiOtherVo targetVo){
		return targetVo.getFieldName11() + "GGG";
	}

}
