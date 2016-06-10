package jp.co.transcosmos.dm3.testUtil.mock;

import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.dao.DAO;


// HousingPartThumbnailProxy�@�̃e�X�g�R�[�h�p mock �N���X
public class MockPartDataCreator implements HousingPartCreator {

	// �����������������p DAO
	private DAO<HousingPartInfo> housingPartInfoDAO;

	public void setHousingPartInfoDAO(DAO<HousingPartInfo> housingPartInfoDAO) {
		this.housingPartInfoDAO = housingPartInfoDAO;
	}


	private String executeMethod = "";

	public void setExecuteMethod(String executeMethod) {
		this.executeMethod = executeMethod;
	}

	@Override
	public boolean isExecuteMethod(String methodName) {
		// �ݒ肳��Ă��郁�\�b�h���Ɉ�v����ꍇ�� true �𕜋A����B
		// ���̃��\�b�h�� true �𕜋A�����ꍇ�AcreatePart() �����s�����B
		return methodName.equals(this.executeMethod);
	}



	@Override
	public void createPart(Housing housing) throws Exception {

		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");


		// ���̃N���X�̓e�X�g�R�[�h�p�� mock �Ȃ̂ŁA�w�肳�ꂽ�V�X�e������CD �ɑ΂���
		// �_�~�[�Ƃ������������쐬����B
		HousingPartInfo housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd(housingInfo.getSysHousingCd());
		housingPartInfo.setPartSrchCd("S20");

		this.housingPartInfoDAO.insert(new HousingPartInfo[]{housingPartInfo});

	}

}
