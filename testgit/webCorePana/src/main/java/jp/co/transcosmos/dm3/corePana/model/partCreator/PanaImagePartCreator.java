package jp.co.transcosmos.dm3.corePana.model.partCreator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;

/**
 * 物件画像情報からこだわり条件を生成する.
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
public class PanaImagePartCreator implements HousingPartCreator {

	/** 物件こだわり条件情報用 DAO */
	private DAO<HousingPartInfo> housingPartInfoDAO;

	/**
	 * 間取り図数、こだわり条件CD 変換 map<br/>
	 * Key = 間取り図数、Value = こだわり条件CD<br/>
	 */
	private Map<String, String> mImageToPart;

	/**
	 * 詳細写真数、こだわり条件CD 変換 map<br/>
	 * Key = 詳細写真数、Value = こだわり条件CD<br/>
	 */
	private Map<String, String> imageToPart;

	/** ValueObject の Factory クラス */
	private ValueObjectFactory valueObjectFactory;

	/** このクラスの生成対象となる、こだわり条件CD(間取り図) の Set オブジェクト */
	private String myMImagePartCds[];

	/** このクラスの生成対象となる、こだわり条件CD(詳細写真) の Set オブジェクト */
	private String myImagePartCds[];



	/**
	 * このクラスのこだわり条件を生成するメソッドを判定する。<br/>
	 * <br/>
	 * @param methodName これから実行するメソッド名
	 * @return メソッド名が addHousingImg の場合、true を復帰
	 * @return メソッド名が updHousingImg の場合、true を復帰
	 * @return メソッド名が delHousingImg の場合、true を復帰
	 */
	@Override
	public boolean isExecuteMethod(String methodName) {

		// 物件画像情報新設／更新／削除処理時に実行する。
		// もし、カスタマイズ等で 下記関数 を別のメソッドから実行している
		// 場合は、それらのメソッドからでも実行される様に変更する事。
		if ("addHousingImg".equals(methodName)){
			return true;
		}
		if ("updHousingImg".equals(methodName)){
			return true;
		}
		if ("delHousingImg".equals(methodName)){
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

		// 更新対象となる物件基本情報を取得する。
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		// 物件画像情報のリストを取得する。
		List<HousingImageInfo> housingImageInfos = housing.getHousingImageInfos();

		// 現在のこだわり条件CD を削除する。
		delPartCds(housingInfo.getSysHousingCd(), this.myMImagePartCds);
		delPartCds(housingInfo.getSysHousingCd(), this.myImagePartCds);

		// 物件画像情報がない場合は処理終了。
		if (housingImageInfos == null || housingImageInfos.size() == 0) return;

		// 間取り図数を初期化する。
		int mimageCount = 0;
		// 詳細写真数を初期化する。
		int imageCount = 0;
		for (HousingImageInfo housingImageInfo : housingImageInfos){
			// 間取り図数をカウントする。
			if (PanaCommonConstant.IMAGE_TYPE_00.equals(housingImageInfo.getImageType())) mimageCount++;
			// 詳細写真数をカウントする。（「02:動画」が対象外）
			if (!PanaCommonConstant.IMAGE_TYPE_02.equals(housingImageInfo.getImageType())) imageCount++;
		}

		// 追加するこだわり条件情報の Map オブジェクト
		// Key = こだわり条件CD、Value = これから追加する物件こだわり条件情報の Value オブジェクト
		Map<String, HousingPartInfo> addPartMap = new HashMap<>();

		// 間取り図数、こだわり条件CD 変換 mapをループし、物件画像情報の間取り図数と比較する。
		for (Entry<String, String> e : this.mImageToPart.entrySet()){
			if (mimageCount < Integer.parseInt(e.getKey())) continue;
			String partCd = e.getValue();
			if (!StringValidateUtil.isEmpty(partCd)){

				// 間取り図数 が変換テーブルに間取り図数以上ある場合、追加するこだわり条件情報の Map に、
				// こだわり条件が存在するかをチェックする。
				// もし存在する場合は登録済なので、次のデータを処理する。
				if (addPartMap.get(partCd) == null){
					addPartMap.put(partCd, createHousingPartInfo(housingInfo, partCd));
				}
			}
		}

		// 詳細写真数、こだわり条件CD 変換 mapをループし、物件画像情報の詳細写真数と比較する。
		for (Entry<String, String> e : this.imageToPart.entrySet()){
			if (imageCount < Integer.parseInt(e.getKey())) continue;
			String partCd = e.getValue();
			if (!StringValidateUtil.isEmpty(partCd)){

				// 詳細写真数 が変換テーブルに詳細写真数以上ある場合、追加するこだわり条件情報の Map に、
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
	 * 間取り図数、こだわり条件CD 変換 map を設定する。<br/>
	 * また、このクラスが生成対象とするこだわり条件CD のリストも作成する。<br/>
	 * Key = 間取り図数、Value = こだわり条件CD
	 * <br/>
	 * @param 間取り図数、こだわり条件CD 変換 map
	 */
	public synchronized void setMImageToPart(Map<String, String> mImageToPart) {

		// このメソッドの実行には同期化が必要なので、このクラスのインスタンスは必ずシングルトンで定義する事。
		// また、このメソッドは、DI コンテナの Bean 定義以外では使用しない事。

		// 変換 Map を設定する。
		this.mImageToPart = mImageToPart;

		// こだわり条件CD を Set を使用して重複を取り除き、myMImagePartCds へ設定する。
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : mImageToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myMImagePartCds = partCdSet.toArray(new String[partCdSet.size()]);
	}



	/**
	 * 詳細写真数、こだわり条件CD 変換 map を設定する。<br/>
	 * また、このクラスが生成対象とするこだわり条件CD のリストも作成する。<br/>
	 * Key = 詳細写真数、Value = こだわり条件CD
	 * <br/>
	 * @param 詳細写真数、こだわり条件CD 変換 map
	 */
	public synchronized void setImageToPart(Map<String, String> imageToPart) {

		// このメソッドの実行には同期化が必要なので、このクラスのインスタンスは必ずシングルトンで定義する事。
		// また、このメソッドは、DI コンテナの Bean 定義以外では使用しない事。

		// 変換 Map を設定する。
		this.imageToPart = imageToPart;

		// こだわり条件CD を Set を使用して重複を取り除き、myImagePartCds へ設定する。
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : imageToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myImagePartCds = partCdSet.toArray(new String[partCdSet.size()]);
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
