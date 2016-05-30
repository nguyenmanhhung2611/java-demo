package jp.co.transcosmos.dm3.core.model.partCreator;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * 最寄り駅情報からこだわり条件を生成する.
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.04.15	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public class StationPartCreator implements HousingPartCreator {

	/** 物件こだわり条件情報用 DAO */
	protected DAO<HousingPartInfo> housingPartInfoDAO;
	
	/** このクラスの生成対象となる、こだわり条件CD。（こだわり条件CDの下から2桁は徒歩時間の値と同じに想定する） */
	protected String myPartCds = "S05,S10,S15";
	
	/** 分割コード */
	protected String regex = ",";
	
	/** ValueObject の Factory クラス */
	protected ValueObjectFactory valueObjectFactory;

	/**
	 * Value オブジェクトの Factory クラスを設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory Value オブジェクトの Factory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}
	
	/**
	 * このクラスの生成対象となる、こだわり条件CDを設定する。<br/>
	 * <br/>
	 * @param myPartCds 
	 */
	public void setMyPartCds(String myPartCds) {
		this.myPartCds = myPartCds;
	}

	/**
	 * 分割コードを設定する。<br/>
	 * <br/>
	 * @param regex 分割コード
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}

	/**
	 * 物件こだわり条件情報DAO を設定する。<br/>
	 * <br/>
	 * @param housingPartInfoDAO 物件こだわり条件情報DAO
	 */
	public void setHousingPartInfoDAO(DAO<HousingPartInfo> housingPartInfoDAO) {
		this.housingPartInfoDAO = housingPartInfoDAO;
	}
	
	/**
	 * このクラスのこだわり条件を生成するメソッドを判定する。<br/>
	 * <br/>
	 * @param methodName これから実行するメソッド名
	 * @return メソッド名が updateHousingEquip の場合、true を復帰
	 */
	@Override
	public boolean isExecuteMethod(String methodName) {
		// 最寄り駅情報更新処理時に実行する。
		// もし、カスタマイズ等で updateBuildingStationInfo() を別のメソッドから実行している
		// 場合は、それらのメソッドからでも実行される様に変更する事。
		if ("updateBuildingStationInfo".equals(methodName)){
			return true;
		}
		return false;
	}

	/**
	 * 登録した最寄り駅情報オブジェクトから物件こだわり条件を作成する。<br/>
	 * <br/>
	 */
	@Override
	public void createPart(Housing housing) throws Exception {
		// 登録した最寄り駅情報を取得する。
		List<JoinResult> stationInfoList = housing.getBuilding().getBuildingStationInfoList();
		// 更新対象となる物件基本情報を取得する。
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		
		// 現在のこだわり条件CD を削除する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", housingInfo.getSysHousingCd());
		String[] myPartCdString = this.myPartCds.split(this.regex);
		criteria.addInSubQuery("partSrchCd", myPartCdString);
		this.housingPartInfoDAO.deleteByFilter(criteria);
		// 最寄り駅情報がある場合、こだわりを作成する
		if (stationInfoList != null && stationInfoList.size() != 0) {
			// 最短時間
			int time = ((BuildingStationInfo) stationInfoList.get(0).getItems().get("buildingStationInfo")).getTimeFromStation();
			// 最寄り駅情報に最短時間を取得する
			for (int i = 1;i < stationInfoList.size();i++) {
				// 徒歩の所要時間
				int walkTime = ((BuildingStationInfo) stationInfoList.get(i).getItems().get("buildingStationInfo")).getTimeFromStation();
				if (time > walkTime) {
					time = walkTime;
				}
			}
			for (int i = 0;i < myPartCdString.length;i++) {
				// こだわりCdを設定する
				if (time < Integer.valueOf(myPartCdString[i].substring(1))) {
					HousingPartInfo stationPartInfo = createHousingPartInfo(housingInfo, myPartCdString[i]);
					// こだわり条件を追加する。
					this.housingPartInfoDAO.insert(new HousingPartInfo[] { stationPartInfo });
					break;
				}
			}
		}
	}

	/**
	 * 新規追加する、物件こだわり条件情報の Value オブジェクトを生成する。<br/>
	 * 本来は、システム物件CD と、こだわり条件CD だけで足りるはずだが、拡張性を考慮して、他の属性も渡しておく。<br/>
	 * <br/>
	 * @param housingInfo 物件情報
	 * @param partCd　こだわり条件CD
	 * @return
	 */
	protected HousingPartInfo createHousingPartInfo(HousingInfo housingInfo, String partCd) {
		// Value オブジェクトを生成してプロパティ値を設定する。
		HousingPartInfo housingPartInfo = (HousingPartInfo) this.valueObjectFactory.getValueObject("HousingPartInfo");
		housingPartInfo.setSysHousingCd(housingInfo.getSysHousingCd());
		housingPartInfo.setPartSrchCd(partCd);
		return housingPartInfo;
	}

}
