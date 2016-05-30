package jp.co.transcosmos.dm3.corePana.model.partCreator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;

/**
 * 物件基本情報からこだわり条件を生成する.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * チョ夢		2015.04.14	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 *
 */
public class PanaHousingPartCreator implements HousingPartCreator {

	/** 物件こだわり条件情報用 DAO */
	private DAO<HousingPartInfo> housingPartInfoDAO;

	/**
	 * 徒歩時間、こだわり条件CD 変換 map<br/>
	 * Key = 徒歩時間、Value = こだわり条件CD<br/>
	 */
	private Map<String, String> timeToPart;

	/**
	 * 物件階数、こだわり条件CD 変換 map<br/>
	 * Key = 物件階数、Value = こだわり条件CD<br/>
	 */
	private Map<String, String> floorToPart;

	/**
	 * 物件階数(N階以上)、こだわり条件CD 変換 map<br/>
	 * Key = 物件階数(N階以上)、Value = こだわり条件CD<br/>
	 */
	private Map<String, String> floorUpToPart;

	/**
	 * アイコン情報（おすすめポイント）、こだわり条件CD 変換 map<br/>
	 * Key = おすすめポイント、Value = こだわり条件CD<br/>
	 */
	private Map<String, String> iconToPart;

	/** ValueObject の Factory クラス */
	private ValueObjectFactory valueObjectFactory;

	/** このクラスの生成対象となる、こだわり条件CD(徒歩時間) の Set オブジェクト */
	private String myTimePartCds[];

	/** このクラスの生成対象となる、こだわり条件CD(物件階数) の Set オブジェクト */
	private String myFloorPartCds[];

	/** このクラスの生成対象となる、こだわり条件CD(物件階数(N階以上)) の Set オブジェクト */
	private String myFloorUpPartCds[];

	/** こだわり条件CD(最上階) */
	private String topFloorPartCd = "F99";

	/** このクラスの生成対象となる、こだわり条件CD(おすすめポイント) の Set オブジェクト */
	private String myIconPartCds[];



	/**
	 * このクラスのこだわり条件を生成するメソッドを判定する。<br/>
	 * <br/>
	 * @param methodName これから実行するメソッド名
	 * @return メソッド名が addHousing の場合、true を復帰
	 * @return メソッド名が updateHousing の場合、true を復帰
	 */
	@Override
	public boolean isExecuteMethod(String methodName) {

		// 物件基本情報新設／更新処理時に実行する。
		// もし、カスタマイズ等で 下記関数 を別のメソッドから実行している
		// 場合は、それらのメソッドからでも実行される様に変更する事。
		if ("addHousing".equals(methodName)){
			return true;
		}
		if ("updateHousing".equals(methodName)){
			return true;
		}

		return false;
	}



	/**
	 * 登録した物件情報オブジェクトからこだわり情報を作成する。<br/>
	 * <br/>
	 */
	@Override
	public void createPart(Housing housing) throws Exception {

		// 徒歩時間こだわり情報を作成する。
		createPartTime(housing);

		// 物件階数こだわり情報を作成する。
		createPartFloor(housing);

		// おすすめポイントこだわり情報を作成する。
		createPartIcon(housing);

	}



	/**
	 * 徒歩時間こだわり情報を作成する。<br/>
	 * <br/>
	 */
	protected void createPartTime(Housing housing) throws Exception {

		// 更新対象となる物件基本情報を取得する。
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");

		// 現在のこだわり条件CD を削除する。
		delPartCds(housingInfo.getSysHousingCd(), this.myTimePartCds);

		// 最小徒歩時間未登録場合は処理終了。
		if (housingInfo.getMinWalkingTime() == null) return;
		// 最小徒歩時間を取得する。
		int minWalkingTime = housingInfo.getMinWalkingTime();

		// 追加するこだわり条件情報の Map オブジェクト
		// Key = こだわり条件CD、Value = これから追加する物件こだわり条件情報の Value オブジェクト
		Map<String, HousingPartInfo> addPartMap = new HashMap<>();

		// 徒歩時間、こだわり条件CD 変換 mapをループし、物件基本情報の最小徒歩時間と比較する。
		for (Entry<String, String> e : this.timeToPart.entrySet()){
			if (minWalkingTime > Integer.parseInt(e.getKey())) continue;
			String partCd = e.getValue();
			if (!StringValidateUtil.isEmpty(partCd)){

				// 最小徒歩時間 が変換テーブルに徒歩時間以下ある場合、追加するこだわり条件情報の Map に、
				// こだわり条件が存在するかをチェックする。
				// もし存在する場合は登録済なので、次のデータを処理する。
				if (addPartMap.get(partCd) == null){
					addPartMap.put(partCd, createHousingPartInfo(housingInfo, partCd));
				}
			}
		}

		// こだわり条件を追加する。
		this.housingPartInfoDAO.insert(addPartMap.values().toArray(new HousingPartInfo[addPartMap.size()]));
	}



	/**
	 * 物件階数こだわり情報を作成する。<br/>
	 * <br/>
	 */
	protected void createPartFloor(Housing housing) throws Exception {

		// 更新対象となる物件基本情報を取得する。
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		BuildingInfo buildingInfo = (BuildingInfo) housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");

		// 現在のこだわり条件CD を削除する。
		delPartCds(housingInfo.getSysHousingCd(), this.myFloorPartCds);
		delPartCds(housingInfo.getSysHousingCd(), this.myFloorUpPartCds);

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", housingInfo.getSysHousingCd());
		criteria.addWhereClause("partSrchCd", this.topFloorPartCd);
		this.housingPartInfoDAO.deleteByFilter(criteria);

		// 物件階数未登録場合は処理終了。
		if (housingInfo.getFloorNo() == null) return;
		// 物件階数 総階数 を取得する。
		int housingFloor = housingInfo.getFloorNo();
		int buildingFloor = 0;
		if (buildingInfo.getTotalFloors() != null) buildingFloor = buildingInfo.getTotalFloors();

		// 追加するこだわり条件情報の Map オブジェクト
		// Key = こだわり条件CD、Value = これから追加する物件こだわり条件情報の Value オブジェクト
		Map<String, HousingPartInfo> addPartMap = new HashMap<>();

		// 物件階数、こだわり条件CD 変換テーブルに存在するかをチェックする。
		String floorPartCd = this.floorToPart.get(String.valueOf(housingFloor));
		if (!StringValidateUtil.isEmpty(floorPartCd)){

			// 変換テーブルに物件階数 が登録されている場合、追加するこだわり条件情報の Map に、
			// こだわり条件が存在するかをチェックする。
			if (addPartMap.get(floorPartCd) == null){
				addPartMap.put(floorPartCd, createHousingPartInfo(housingInfo, floorPartCd));
			}

		}

		// 物件階数(N階以上)、こだわり条件CD 変換 mapをループし、物件基本情報の物件階数と比較する。
		for (Entry<String, String> e : this.floorUpToPart.entrySet()){
			if (housingFloor < Integer.parseInt(e.getKey())) continue;
			String partCd = e.getValue();
			if (!StringValidateUtil.isEmpty(partCd)){

				// 物件階数 が変換テーブルに物件階数以上ある場合、追加するこだわり条件情報の Map に、
				// こだわり条件が存在するかをチェックする。
				// もし存在する場合は登録済なので、次のデータを処理する。
				if (addPartMap.get(partCd) == null){
					addPartMap.put(partCd, createHousingPartInfo(housingInfo, partCd));
				}
			}
		}

		// こだわり条件CD(最上階) をチェックする。
		if (housingFloor > 1 && housingFloor == buildingFloor){
			if (!StringValidateUtil.isEmpty(this.topFloorPartCd)){

				// こだわり条件が存在するかをチェックする。
				if (addPartMap.get(this.topFloorPartCd) == null){
					addPartMap.put(this.topFloorPartCd, createHousingPartInfo(housingInfo, this.topFloorPartCd));
				}

			}
		}

		// こだわり条件を追加する。
		this.housingPartInfoDAO.insert(addPartMap.values().toArray(new HousingPartInfo[addPartMap.size()]));
	}



	/**
	 * おすすめポイントこだわり情報を作成する。<br/>
	 * <br/>
	 */
	protected void createPartIcon(Housing housing) throws Exception {

		// 更新対象となる物件基本情報を取得する。
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");

		// 現在のこだわり条件CD を削除する。
		delPartCds(housingInfo.getSysHousingCd(), this.myIconPartCds);

		// アイコン情報（おすすめポイント）未登録場合は処理終了。
		if (housingInfo.getIconCd() == null) return;
		// アイコン情報（おすすめポイント）を取得する。
		String[] iconCds = housingInfo.getIconCd().split(",");

		// 追加するこだわり条件情報の Map オブジェクト
		// Key = こだわり条件CD、Value = これから追加する物件こだわり条件情報の Value オブジェクト
		Map<String, HousingPartInfo> addPartMap = new HashMap<>();

		// 物件基本情報のアイコン情報をループし、おすすめポイント、こだわり条件CD 変換 mapに存在するかをチェックする。
		for (String iconCd : iconCds) {
			String partCd = this.iconToPart.get(iconCd);
			if (!StringValidateUtil.isEmpty(partCd)){

				// 変換テーブルにおすすめポイント が登録されている場合、追加するこだわり条件情報の Map に、
				// こだわり条件が存在するかをチェックする。
				// もし存在する場合は登録済なので、次のデータを処理する。
				if (addPartMap.get(partCd) == null){
					addPartMap.put(partCd, createHousingPartInfo(housingInfo, partCd));
				}
			}
		}

		// こだわり条件を追加する。
		this.housingPartInfoDAO.insert(addPartMap.values().toArray(new HousingPartInfo[addPartMap.size()]));
	}



	/**
	 * 現在のこだわり条件CD を削除する。<br/>
	 * <br/>
	 */
	protected void delPartCds(String sysHousingCd, String partCds[]) {
		// 現在のこだわり条件CD を削除する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addInSubQuery("partSrchCd", partCds);
		this.housingPartInfoDAO.deleteByFilter(criteria);
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



	/**
	 * 徒歩時間、こだわり条件CD 変換 map を設定する。<br/>
	 * また、このクラスが生成対象とするこだわり条件CD のリストも作成する。<br/>
	 * Key = 徒歩時間、Value = こだわり条件CD
	 * <br/>
	 * @param 徒歩時間、こだわり条件CD 変換 map
	 */
	public synchronized void setTimeToPart(Map<String, String> timeToPart) {

		// このメソッドの実行には同期化が必要なので、このクラスのインスタンスは必ずシングルトンで定義する事。
		// また、このメソッドは、DI コンテナの Bean 定義以外では使用しない事。

		// 変換 Map を設定する。
		this.timeToPart = timeToPart;

		// こだわり条件CD を Set を使用して重複を取り除き、myPartCds へ設定する。
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : timeToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myTimePartCds = partCdSet.toArray(new String[partCdSet.size()]);
	}



	/**
	 * 物件階数、こだわり条件CD 変換 map を設定する。<br/>
	 * また、このクラスが生成対象とするこだわり条件CD のリストも作成する。<br/>
	 * Key = 物件階数、Value = こだわり条件CD
	 * <br/>
	 * @param 物件階数、こだわり条件CD 変換 map
	 */
	public synchronized void setFloorToPart(Map<String, String> floorToPart) {

		// このメソッドの実行には同期化が必要なので、このクラスのインスタンスは必ずシングルトンで定義する事。
		// また、このメソッドは、DI コンテナの Bean 定義以外では使用しない事。

		// 変換 Map を設定する。
		this.floorToPart = floorToPart;

		// こだわり条件CD を Set を使用して重複を取り除き、myPartCds へ設定する。
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : floorToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myFloorPartCds = partCdSet.toArray(new String[partCdSet.size()]);
	}



	/**
	 * 物件階数(N階以上)、こだわり条件CD 変換 map を設定する。<br/>
	 * また、このクラスが生成対象とするこだわり条件CD のリストも作成する。<br/>
	 * Key = 物件階数(N階以上)、Value = こだわり条件CD
	 * <br/>
	 * @param 物件階数(N階以上)、こだわり条件CD 変換 map
	 */
	public synchronized void setFloorUpToPart(Map<String, String> floorUpToPart) {

		// このメソッドの実行には同期化が必要なので、このクラスのインスタンスは必ずシングルトンで定義する事。
		// また、このメソッドは、DI コンテナの Bean 定義以外では使用しない事。

		// 変換 Map を設定する。
		this.floorUpToPart = floorUpToPart;

		// こだわり条件CD を Set を使用して重複を取り除き、myPartCds へ設定する。
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : floorUpToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myFloorUpPartCds = partCdSet.toArray(new String[partCdSet.size()]);
	}



	/**
	 * こだわり条件CD(最上階) を設定する。<br/>
	 * <br/>
	 * @param こだわり条件CD(最上階)
	 */
	public synchronized void setTopFloorPartCd(String topFloorPartCd) {

		// このメソッドの実行には同期化が必要なので、このクラスのインスタンスは必ずシングルトンで定義する事。
		// また、このメソッドは、DI コンテナの Bean 定義以外では使用しない事。

		this.topFloorPartCd = topFloorPartCd;
	}



	/**
	 * アイコン情報（おすすめポイント）、こだわり条件CD 変換 map を設定する。<br/>
	 * また、このクラスが生成対象とするこだわり条件CD のリストも作成する。<br/>
	 * Key = おすすめポイント、Value = こだわり条件CD
	 * <br/>
	 * @param おすすめポイント、こだわり条件CD 変換 map
	 */
	public synchronized void setIconToPart(Map<String, String> iconToPart) {

		// このメソッドの実行には同期化が必要なので、このクラスのインスタンスは必ずシングルトンで定義する事。
		// また、このメソッドは、DI コンテナの Bean 定義以外では使用しない事。

		// 変換 Map を設定する。
		this.iconToPart = iconToPart;

		// こだわり条件CD を Set を使用して重複を取り除き、myPartCds へ設定する。
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : iconToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myIconPartCds = partCdSet.toArray(new String[partCdSet.size()]);
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
	 * Value オブジェクトの Factory クラスを設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory Value オブジェクトの Factory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

}
