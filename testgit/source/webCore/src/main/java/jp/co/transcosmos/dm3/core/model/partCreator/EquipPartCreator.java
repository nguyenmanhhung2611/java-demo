package jp.co.transcosmos.dm3.core.model.partCreator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * 設備情報からこだわり条件を生成する.
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.13	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public class EquipPartCreator implements HousingPartCreator {

	/** 物件こだわり条件情報用 DAO */
	private DAO<HousingPartInfo> housingPartInfoDAO;

	/**
	 * 設備CD、こだわり条件CD 変換 map<br/>
	 * ※現在の方法は、１個の設備CD を複数のこだわり条件として登録する事をサポートしていない。<br/>
	 * Key = 設備CD、Value = こだわり条件CD<br/>
	 */
	protected Map<String, String> equipToPart;

	/** ValueObject の Factory クラス */
	protected ValueObjectFactory valueObjectFactory;

	/** このクラスの生成対象となる、こだわり条件CD の Set オブジェクト */
	protected String myPartCds[];



	/**
	 * このクラスのこだわり条件を生成するメソッドを判定する。<br/>
	 * <br/>
	 * @param methodName これから実行するメソッド名
	 * @return メソッド名が updateHousingEquip の場合、true を復帰
	 */
	@Override
	public boolean isExecuteMethod(String methodName) {

		// 設備情報更新処理時に実行する。
		// もし、カスタマイズ等で updateHousingEquip() を別のメソッドから実行している
		// 場合は、それらのメソッドからでも実行される様に変更する事。
		if ("updateHousingEquip".equals(methodName)){
			return true;
		}
		return false;
	}



	/**
	 * 登録した物件情報オブジェクトから設備情報を作成する。<br/>
	 * <br/>
	 */
	@Override
	public void createPart(Housing housing) throws Exception {

		// 登録した設備情報を取得する。
		Map<String, EquipMst> equipMap = housing.getHousingEquipInfos();
		// 更新対象となる物件基本情報を取得する。
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");


		// 現在のこだわり条件CD を削除する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", housingInfo.getSysHousingCd());
		criteria.addInSubQuery("partSrchCd", this.myPartCds);

		this.housingPartInfoDAO.deleteByFilter(criteria);

		
		// 追加するこだわり条件情報の Map オブジェクト
		// Key = こだわり条件CD、Value = これから追加する物件こだわり条件情報の Value オブジェクト
		Map<String, HousingPartInfo> addPartMap = new HashMap<>();

		// 登録した設備分ループし、こだわり条件の変換テーブルに存在するかをチェックする。
		for (Entry<String, EquipMst> e : equipMap.entrySet()){
			String partCd = this.equipToPart.get(e.getValue().getEquipCd());
			if (!StringValidateUtil.isEmpty(partCd)){

				// 変換テーブルに設備CD が登録されている場合、追加するこだわり条件情報の Map に、
				// こだわり条件が存在するかをチェックする。
				// もし存在する場合は登録済なので、次のデータを処理する。
				if (addPartMap.get(partCd) == null){
					addPartMap.put(partCd, createHousingPartInfo(housingInfo, partCd, e.getValue()));
				}
			}
		}

		// こだわり条件を追加する。
		this.housingPartInfoDAO.insert(addPartMap.values().toArray(new HousingPartInfo[addPartMap.size()]));
	}

	

	/**
	 * 新規追加する、物件こだわり条件情報の Value オブジェクトを生成する。<br/>
	 * 本来は、システム物件CD と、こだわり条件CD だけで足りるはずだが、拡張性を考慮して、他の属性も渡しておく。<br/>
	 * <br/>
	 * @param housingInfo 物件情報
	 * @param partCd　こだわり条件CD
	 * @param equipMst 設備CD
	 * @return
	 */
	protected HousingPartInfo createHousingPartInfo(HousingInfo housingInfo, String partCd, EquipMst equipMst) {
		// Value オブジェクトを生成してプロパティ値を設定する。
		HousingPartInfo housingPartInfo = (HousingPartInfo) this.valueObjectFactory.getValueObject("HousingPartInfo");
		housingPartInfo.setSysHousingCd(housingInfo.getSysHousingCd());
		housingPartInfo.setPartSrchCd(partCd);
		return housingPartInfo;
	}



	/**
	 * 設備CD、こだわり条件CD 変換 map を設定する。<br/>
	 * また、このクラスが生成対象とするこだわり条件CD のリストも作成する。<br/>
	 * Key = 設備CD、Value = こだわり条件CD
	 * <br/>
	 * @param 設備CD、こだわり条件CD 変換 map
	 */
	public synchronized void setEquipToPart(Map<String, String> equipToPart) {

		// このメソッドの実行には同期化が必要なので、このクラスのインスタンスは必ずシングルトンで定義する事。
		// また、このメソッドは、DI コンテナの Bean 定義以外では使用しない事。

		// 変換 Map を設定する。
		this.equipToPart = equipToPart;

		// こだわり条件CD を Set を使用して重複を取り除き、myPartCds へ設定する。
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : equipToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myPartCds = partCdSet.toArray(new String[partCdSet.size()]);
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
