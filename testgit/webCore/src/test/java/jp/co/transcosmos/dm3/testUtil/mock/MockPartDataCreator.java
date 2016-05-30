package jp.co.transcosmos.dm3.testUtil.mock;

import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.dao.DAO;


// HousingPartThumbnailProxy　のテストコード用 mock クラス
public class MockPartDataCreator implements HousingPartCreator {

	// 物件こだわり条件情報用 DAO
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
		// 設定されているメソッド名に一致する場合は true を復帰する。
		// このメソッドが true を復帰した場合、createPart() が実行される。
		return methodName.equals(this.executeMethod);
	}



	@Override
	public void createPart(Housing housing) throws Exception {

		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");


		// このクラスはテストコード用の mock なので、指定されたシステム物件CD に対して
		// ダミーとこだわり条件を作成する。
		HousingPartInfo housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd(housingInfo.getSysHousingCd());
		housingPartInfo.setPartSrchCd("S20");

		this.housingPartInfoDAO.insert(new HousingPartInfo[]{housingPartInfo});

	}

}
