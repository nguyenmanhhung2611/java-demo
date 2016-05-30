package jp.co.transcosmos.dm3.core.model.housing;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.dao.SearchHousingDAO;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingImgForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.util.imgUtils.ImgInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingEquipInfo;
import jp.co.transcosmos.dm3.core.vo.HousingExtInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.DAOCriteriaWhereClause;
import jp.co.transcosmos.dm3.dao.FormulaUpdateExpression;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.dao.OrCriteria;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;

/**
 * 物件情報用 Model クラス.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.12	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public class HousingManageImpl implements HousingManage {

	private static final Log log = LogFactory.getLog(HousingManageImpl.class);
	
	/** Value オブジェクトの Factory */
	protected ValueObjectFactory valueObjectFactory;

	/** 建物情報メンテナンスの model */
	protected BuildingManage buildingManager;

	/** 物件基本情報用DAO */
	protected DAO<HousingInfo> housingInfoDAO;

	/** 物件基本詳細情報用DAO */
	protected DAO<HousingDtlInfo> housingDtlInfoDAO;

	/** 物件ステータス情報用DAO */
	protected DAO<HousingStatusInfo> housingStatusInfoDAO;
	
	/** 物件設備情報用DAO（メンテナンス用） */
	protected DAO<HousingEquipInfo> housingEquipInfoDAO;

	/**
	 * 物件設備情報用DAO（情報取得用。マスターの表示順でソート）
	 * 物件設備情報、設備マスタを結合し、設備マスタの表示順でソートした結果<br/>
	 */
	protected DAO<JoinResult> housingEquipListDAO;

	/**
	 * 物件こだわり条件情報 DAO（情報取得用。マスターの表示順でソート）
	 * 物件こだわり条件情報、こだわり条件マスタを結合し、こだわり条件マスタの表示順でソートした結果<br/>
	 */
	protected DAO<JoinResult> housingPartListDAO;

	/** 物件画像情報DAO */
	protected DAO<HousingImageInfo> HousingImageInfoDAO;
	
	/** 物件拡張属性用DAO */
	protected DAO<HousingExtInfo> housingExtInfoDAO;
	
	/** 物件基本情報と１対１の関係にある関連テーブルを取得する為の JoinDAO (物件を構成する、全データの取得用) */
	protected DAO<JoinResult> housingMainJoinDAO;

	/** 物件検索用 DAO */
	protected SearchHousingDAO searchHousingDAO;
	
	/** サムネイル画像関連ユーティリティ */
	protected ImgUtils imgUtils;

	/** 共通パラメータオブジェクト */
	protected CommonParameters commonParameters;


	
	/**
	 * Value オブジェクトの Factory を設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory Value オブジェクトの Factory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * 建物情報メンテナンス用 model を設定する。<br/>
	 * <br/>
	 * @param buildingManage 建物情報メンテナンス用 model
	 */
	public void setBuildingManager(BuildingManage buildingManager) {
		this.buildingManager = buildingManager;
	}

	/**
	 * 物件基本情報用DAO を設定する。<br/>
	 * <br/>
	 * @param housingInfoDAO 物件基本情報用 DAO
	 */
	public void setHousingInfoDAO(DAO<HousingInfo> housingInfoDAO) {
		this.housingInfoDAO = housingInfoDAO;
	}

	/**
	 * 物件詳細情報DAO を設定する。<br/>
	 * <br/>
	 * @param housingDtlInfoDAO 物件詳細情報DAO
	 */
	public void setHousingDtlInfoDAO(DAO<HousingDtlInfo> housingDtlInfoDAO) {
		this.housingDtlInfoDAO = housingDtlInfoDAO;
	}

	/**
	 * 物件ステータス情報用DAO を設定する。<br/>
	 * <br/>
	 * @param housingStatusInfoDAO 物件ステータス情報用DAO 
	 */
	public void setHousingStatusInfoDAO(DAO<HousingStatusInfo> housingStatusInfoDAO) {
		this.housingStatusInfoDAO = housingStatusInfoDAO;
	}

	/**
	 * 物件設備情報用DAO を設定する。<br/>
	 * <br/>
	 * @param housingEquipInfo 物件設備情報用DAO
	 */
	public void setHousingEquipInfoDAO(DAO<HousingEquipInfo> housingEquipInfoDAO) {
		this.housingEquipInfoDAO = housingEquipInfoDAO;
	}

	/**
	 * 物件設備情報用DAO（情報取得用。マスターの表示順でソート） を設定する。<br/>
	 * <br/>
	 * @param housingEquipListDAO 物件設備情報用DAO（情報取得用。マスターの表示順でソート）
	 */
	public void setHousingEquipListDAO(DAO<JoinResult> housingEquipListDAO) {
		this.housingEquipListDAO = housingEquipListDAO;
	}

	/**
	 * 物件こだわり条件用DAO（情報取得用。マスターの表示順でソート） を設定する。<br/>
	 * <br/>
	 * @return 物件こだわり条件用DAO（情報取得用。マスターの表示順でソート）
	 */
	public DAO<JoinResult> getHousingPartListDAO() {
		return housingPartListDAO;
	}

	/**
	 * 物件画像情報用 DAO を設定する。<br/>
	 * <br/>
	 * @param housingImageInfoDAO 物件画像情報用 DAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		HousingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 * 物件拡張属性用DAO を設定する。<br/>
	 * <br/>
	 * @param housingExtInfoDAO 物件拡張属性用 DAO
	 */
	public void setHousingExtInfoDAO(DAO<HousingExtInfo> housingExtInfoDAO) {
		this.housingExtInfoDAO = housingExtInfoDAO;
	}

	/**
	 * 物件を構成する、全データの取得用の DAO を設定する。<br/>
	 * <br/>
	 * @param housingMainJoinDAO 全データの取得用の DAO
	 */
	public void setHousingMainJoinDAO(DAO<JoinResult> housingMainJoinDAO) {
		this.housingMainJoinDAO = housingMainJoinDAO;
	}

	/**
	 * 物件検索用 DAO を設定する。<br/>
	 * <br/>
	 * @param searchHousingDAO 物件検索用 DAO
	 */
	public void setSearchHousingDAO(SearchHousingDAO searchHousingDAO) {
		this.searchHousingDAO = searchHousingDAO;
	}

	/**
	 * 画像ファイルユーティリティを設定する。<br/>
	 * <br/>
	 * @param imgUtils 画像ファイルユーティリティ
	 */
	public void setImgUtils(ImgUtils imgUtils) {
		this.imgUtils = imgUtils;
	}

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters 共通パラメータオブジェクト
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * パラメータで渡された Form の情報で物件基本情報を新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * ※表示用アイコンは設備情報で更新するので、このメソッドでは更新されない。<br/>
	 * <br/>
	 * @param inputForm 物件基本情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return 採番されたシステム物件CD
	 */
	@Override
	public String addHousing(HousingForm inputForm, String editUserId)
			throws Exception {

		// 空のバリーオブジェクトを作成しフォームの入力値を設定する。
		HousingInfo housingInfo = (HousingInfo)this.valueObjectFactory.getValueObject("HousingInfo");
		inputForm.copyToHousingInfo(housingInfo);

		// タイムスタンプ情報を設定
		Date sysDate = new Date();
		housingInfo.setInsDate(sysDate);
		housingInfo.setInsUserId(editUserId);
		housingInfo.setUpdDate(sysDate);
		housingInfo.setUpdUserId(editUserId);

		// データを追加
		try {
			this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		} catch (DataIntegrityViolationException e) {
			// この例外は、登録直前に依存先となる建物情報が削除された場合に発生する。
			log.warn(e.getMessage(), e);
			throw new NotFoundException();
		}

		// 採番されたシステム物件CD を復帰
		return housingInfo.getSysHousingCd();
		
	}



	/**
	 * パラメータで渡された Form の情報で物件基本情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * HousingForm の sysHousingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * ※表示用アイコンは設備情報で更新するので、このメソッドでは更新されない。<br/>
	 * <br/>
	 * @param inputForm 物件基本情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	@Override
	public void updateHousing (HousingForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// 更新対象の現在の値を取得する。
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(inputForm.getSysHousingCd());
		if (housingInfo == null){
			// 更新対象が取得出来ない場合は処理が継続できないので例外をスローして終了する。
			throw new NotFoundException();
		}

		// 取得した値を Form の入力値で上書きする。
		inputForm.copyToHousingInfo(housingInfo);

		// タイムスタンプ情報を設定
		Date sysDate = new Date();
		housingInfo.setUpdDate(sysDate);
		housingInfo.setUpdUserId(editUserId);

		// データを更新
		try {
			this.housingInfoDAO.update(new HousingInfo[]{housingInfo});
		} catch (DataIntegrityViolationException e) {
			// この例外は、登録直前に変更先となる建物情報が削除された場合に発生する。
			log.warn(e.getMessage(), e);
			throw new NotFoundException();
		}
	}



	/**
	 * パラメータで渡された Form の情報で物件基本情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * HousingForm の sysHousingCd プロパティに設定された値を主キー値として削除する。
	 * また、削除対象レコードが存在しない場合でも正常終了として扱う事。<br/>
	 * 削除時は物件基本情報の従属表も削除対象とする。<br/>
	 * 関連する画像ファイルの削除は Proxy クラス側で対応するので、このクラス内には実装しない。<br/>
	 * <br/>
	 * @param inputForm 削除対象となる sysHousingCd を格納した Form オブジェクト
	 * @param editUserId 削除担当者
	 */
	@Override
	public void delHousingInfo(HousingForm inputForm, String editUserId) {

		// 主キー値で削除処理を実行
		this.housingInfoDAO.deleteByPK(new String[]{inputForm.getSysHousingCd()});

		// 依存表は整合性制約で削除する事を想定しているので、明示的な削除は行わない。
		// ただし、物件ステータス情報は物理的な依存関係が存在しないので削除する。
		this.housingStatusInfoDAO.deleteByPK(new String[]{inputForm.getSysHousingCd()});

		// 画像ファイルは Proxy 側で削除するのでここでは対応しない。

	}

	

	/**
	 * パラメータで渡された Form の情報で物件詳細情報を更新する。<br/>
	 * 該当する物件詳細情報が存在しなくても、該当する物件基本情報が存在する場合、レコードを新たに
	 * 追加して物件詳細情報を登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * HousingDtlForm の sysHousingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * @param inputForm 物件詳細情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 */
	@Override
	public void updateHousingDtl(HousingDtlForm inputForm, String editUserId) {

		// 更新対象の現在の値を取得する。
		HousingDtlInfo housingDtlInfo = this.housingDtlInfoDAO.selectByPK(inputForm.getSysHousingCd());

		if (housingDtlInfo == null){
			// 更新対象データが取得出来ない場合は新規登録する。

			// 空のバリーオブジェクトを作成しフォームの入力値を設定する。
			housingDtlInfo = (HousingDtlInfo)this.valueObjectFactory.getValueObject("HousingDtlInfo");
			inputForm.copyToHousingDtlInfo(housingDtlInfo);
			// データを追加
			try {
				this.housingDtlInfoDAO.insert(new HousingDtlInfo[]{housingDtlInfo});
			} catch (DataIntegrityViolationException e){
				// もし、新規登録時に親レコードが存在しない場合は例外をスローする。
				// 物件詳細情報のみ存在するケースはDBの制約上有り得ないので、新規登録時のみ制御する。
				log.warn(e.getMessage(), e);
				throw new NotFoundException();
			}

		} else {
			// 更新対象データが取得出来た場合は更新する。

			// 取得したバリーオブジェクトに Form の値を設定する。
			inputForm.copyToHousingDtlInfo(housingDtlInfo);

			// データを更新
			this.housingDtlInfoDAO.update(new HousingDtlInfo[]{housingDtlInfo});
		}

		// 物件情報のタイムスタンプを更新
		updateEditTimestamp(inputForm.getSysHousingCd(), editUserId);

	}



	/**
	 * パラメータで渡された Form の情報で物件設備情報を更新する。<br/>
	 * 設備情報は DELETE & INSERT で一括更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * HousingDtlForm の sysHousingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * ※基本仕様では、設備情報を表示用アイコンとして登録する。　その為、このメソッド内で物件基本情報
	 * のアイコン情報を更新している。<br/>
	 * <br/>
	 * @param inputForm 物件詳細情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 */
	@Override
	public void updateHousingEquip(HousingEquipForm inputForm, String editUserId){

		// 既存設備情報を削除する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());
		this.housingEquipInfoDAO.deleteByFilter(criteria);


		List<HousingEquipInfo> list = new ArrayList<>();			// 設備情報登録用
		List<String> iconCdList = new ArrayList<>();				// ICON 情報登録用

		// 既存設備情報を追加する。
		if (inputForm.getEquipCd() != null ) {
			

			for (String equipCd : inputForm.getEquipCd()){

				if (!StringValidateUtil.isEmpty(equipCd)) {

					// バリーオブジェクトを生成して値を設定する。
					HousingEquipInfo housingEquipInfo = (HousingEquipInfo) this.valueObjectFactory.getValueObject("HousingEquipInfo");
					housingEquipInfo.setSysHousingCd(inputForm.getSysHousingCd());
					housingEquipInfo.setEquipCd(equipCd);

					list.add(housingEquipInfo);
					iconCdList.add(equipCd);
				}
			}

			if (list.size() > 0) {
				try {
					// 設備CD が設定されている場合はレコードを追加する。
					this.housingEquipInfoDAO.insert(list.toArray(new HousingEquipInfo[list.size()]));
				} catch (DataIntegrityViolationException e) {
					// 追加中に親レコードが削除された場合、NotFoundException の例外をスローする。
					// 例えば、物件の削除処理と競合した場合など..。
					log.warn(e.getMessage(), e);
					throw new NotFoundException();
				}
			}
		}

		// 基本仕様としては、設備情報から物件基本情報のアイコン情報を更新する。
		equipToiconData(inputForm.getSysHousingCd(), iconCdList.toArray(new String[iconCdList.size()]), editUserId);

		// note
		// アイコン情報も物件基本情報なので、タイムスタンプの更新は同時に行った方が効率的だが、
		// アイコン情報の更新はオーバーライドされて無効化される可能性があるので、オーバーヘッドではあるが、
		// タイムスタンプは別に更新する。

		// 物件情報のタイムスタンプを更新
		updateEditTimestamp(inputForm.getSysHousingCd(), editUserId);

	}


	
	/**
	 * 設備CD から表示用アイコン情報を更新する。<br/>
	 * このメソッドは設備情報の更新処理内から実行され、入力した設備CD を元に表示用アイコン情報を
	 * 生成する。<br/>
	 * 表示用アイコン情報を別の源泉から作成する場合、何も処理しない様にこのメソッドをオーバーライド
	 * する必要がある。<br/>
	 * <br/>
	 * @param sysHousingCd
	 * @param equipCds
	 */
	protected void equipToiconData(String sysHousingCd, String[] equipCds, String editUserId) {
		
		StringBuffer buff = new StringBuffer(4000);
		boolean isFirst = true;
		
		if (equipCds != null) {
			for (String equipCd : equipCds) {
				if (!StringValidateUtil.isEmpty(equipCd)) {
					if (!isFirst) {
						buff.append(",");
					}
					buff.append(equipCd);
					isFirst = false;
				}
			}
		}


		// 更新条件を生成
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);

		// 更新オブジェクトを作成
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("iconCd", buff.toString())};

		// 物件基本情報のアイコン情報を更新
		this.housingInfoDAO.updateByCriteria(criteria, updateExpression);

		// 内部的な部品なので、物件基本情報のタイムスタンプは更新しない。
		// 呼び出し元側で必要に応じて更新する事。
	}



	/**
	 * パラメータで渡された Form の情報で物件画像情報を新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * 関連する画像ファイルの公開処理やサムネイルの作成は Proxy クラス側で対応するので、
	 * このクラス内では対応しない。<br/>
	 * <br/>
	 * @param inputForm 物件画像情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return 新たに追加した画像情報のリスト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public List<HousingImageInfo> addHousingImg(HousingImgForm inputForm, String editUserId)
			throws Exception{

		// 戻り値となる、新たに追加した物件画像情報のリスト
		List<HousingImageInfo> imgList = new ArrayList<>();


		// 更新対象となる物件詳細を取得する。 （画像パスに使用する、物件、建物情報を取得する為。
		// もし該当データが存在しない場合（メンテ中に他から削除された場合など）は例外をスローする。
// 2015.05.01 H.Mizuno 非公開物件の場合、画等登録するとエラーになる問題を修正 start
//		Housing housing = this.searchHousingPk(inputForm.getSysHousingCd());
		Housing housing = this.searchHousingPk(inputForm.getSysHousingCd(), true);
// 2015.05.01 H.Mizuno 非公開物件の場合、画等登録するとエラーになる問題を修正 end
		if (housing == null){
			throw new NotFoundException();
		}


		// メイン画像フラグをリセットし、システム物件CD 単位で物件画像情報をロックする。
		lockAndRestFlag(inputForm.getSysHousingCd());


		// 画像ファイルを基準に件数分ループする。
		for (int idx=0; idx < inputForm.getFileName().length; ++idx){

			// ファイル名が無い場合次の処理へ
			if (StringUtils.isEmpty(inputForm.getFileName()[idx])) continue;

			// フォームの値を Value オブジェクトへ copy する。
			HousingImageInfo housingImageInfo = (HousingImageInfo) this.valueObjectFactory.getValueObject("HousingImageInfo");
			inputForm.copyToHousingImageInfo(housingImageInfo, idx);

			// パス名を設定する。
			housingImageInfo.setPathName(createImagePath(housing));

			// 縦長・横長フラグは、仮フォルダ内の画像ファイルから情報を取得して設定する。
			String tempFileName = this.commonParameters.getHousImgTempPhysicalPath() +
								  inputForm.getTempDate() + "/" + inputForm.getFileName()[idx]; 
			ImgInfo info = imgUtils.getImgInfo(tempFileName);
			housingImageInfo.setHwFlg(info.getHwFlg().toString());


			// 枝番は、呼び出し元側で、システム物件番号、画像タイプ内で連番を設定する。
			housingImageInfo.setDivNo(getMaxDivNo(housingImageInfo.getSysHousingCd(), housingImageInfo.getImageType()));
			this.HousingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});

			// 追加した画像情報を戻り値用のリストへ設定する。
			imgList.add(housingImageInfo);
		}


		// メイン画像フラグ、枝番を更新する。
		updateMainFlgAndDivNo(inputForm.getSysHousingCd());

		// 物件基本情報のタイムスタンプを更新
		updateEditTimestamp(inputForm.getSysHousingCd(), editUserId);

		return imgList;
	}



	/**
	 * パラメータで渡されたシステム物件CD、画像タイプ、枝番で物件画像情報を削除する。<br/>
	 * もし削除フラグに 1 が設定されている場合は更新せずに削除処理する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * 関連する画像ファイルの削除は、Proxy クラス側で対応するので、このクラス内では対応しない。<br/>
	 * また、更新処理は、画像パス、画像ファイル名の更新はサポートしていない。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 * @param imageType 物件画像タイプ
	 * @param divNo 枝番
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return 削除が発生した画像情報のリスト
	 */
	@Override
	public List<HousingImageInfo> updHousingImg(HousingImgForm inputForm, String editUserId){

		// 戻り値となる、削除が発生した物件画像情報のリスト
		List<HousingImageInfo> delImgList = new ArrayList<>();

		
		// メイン画像フラグをリセットし、システム物件CD 単位で物件画像情報をロックする。
		lockAndRestFlag(inputForm.getSysHousingCd());


		// 画像ファイルを基準に件数分ループする。
		for (int idx=0; idx < inputForm.getFileName().length; ++idx){

			// 削除フラグが設定されている場合は削除処理へ
			if ("1".equals(inputForm.getDelFlg()[idx])) {

				// 物件画像情報を削除
				HousingImageInfo imgInfo
							= delHousingImg(inputForm.getSysHousingCd(),
						     				inputForm.getImageType()[idx],
						      				Integer.valueOf(inputForm.getDivNo()[idx]));
				// 実際に削除する情報が無かった場合、 null が復帰されるので、その場合はリストに追加しない。
				if (imgInfo != null){
					delImgList.add(imgInfo);
				}
					
			} else {

				// 物件画像の更新処理

				// 条件を生成する。
				DAOCriteria criteria = new DAOCriteria();
				criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());
				criteria.addWhereClause("imageType", inputForm.getOldImageType()[idx]);
				criteria.addWhereClause("divNo", Integer.valueOf(inputForm.getDivNo()[idx]));

				// Form から UpdateExpression を生成してデータを更新する。
				this.HousingImageInfoDAO.updateByCriteria(criteria, inputForm.buildUpdateExpression(idx));
			}
		}

		// メイン画像フラグ、枝番を更新する。
		updateMainFlgAndDivNo(inputForm.getSysHousingCd());

		// 物件基本情報のタイムスタンプを更新
		updateEditTimestamp(inputForm.getSysHousingCd(), editUserId);

		return delImgList;
	}



	/**
	 * パラメータで渡されたシステム物件CD、画像タイプ、枝番で物件画像情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 更新者ID を省略したこのメソッド場合、メイン画像フラグ、枝番、物件基本情報のタイムスタンプは更新されない。
	 * <br/>
	 * 関連する画像ファイルの削除は、Proxy クラス側で対応するので、このクラス内では対応しない。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 * @param imageType 物件画像タイプ
	 * @param divNo 枝番
	 * 
	 * @return 削除が発生した画像情報
	 * 
	 */
	protected HousingImageInfo delHousingImg(String sysHousingCd, String imageType, int divNo)	{
		
		// 物件画像情報を削除する条件を作成
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("imageType", imageType);
		criteria.addWhereClause("divNo", divNo);

		// 削除前に、削除対象データの情報を取得する。
		// もし取得出来ない場合は null を復帰する。
		List<HousingImageInfo> imgInfo = this.HousingImageInfoDAO.selectByFilter(criteria);
		if (imgInfo.size() == 0) return null;

		this.HousingImageInfoDAO.deleteByFilter(criteria);

		// 画像ファイルの物理削除は拡張性を考慮してこのクラス内では行わない。
		// 物件画像公開、サムネイル作成、物理削除等の処理は、このクラスの Proxy クラス側で対応する。

		return imgInfo.get(0);
	}

	/**
	 * パラメータで渡されたシステム物件CD、画像タイプ、枝番で物件画像情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 削除後、メイン画像フラグと物件基本情報のタイムスタンプ情報を更新する。<br/>
	 * <br/>
	 * 関連する画像ファイルの削除は、Proxy クラス側で対応するので、このクラス内では対応しない。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 * @param imageType 物件画像タイプ
	 * @param divNo 枝番
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return 削除が発生した画像情報
	 */
	@Override
	public HousingImageInfo delHousingImg(String sysHousingCd, String imageType, int divNo, String editUserId)	{

		// メイン画像フラグをリセットし、システム物件CD 単位で物件画像情報をロックする。
		lockAndRestFlag(sysHousingCd);
		
		// 指定された物件画像情報を削除する。
		HousingImageInfo imgInfo = delHousingImg(sysHousingCd, imageType, divNo);

		// メイン画像フラグを更新する。
		updateMainFlgAndDivNo(sysHousingCd);

		// 物件基本情報のタイムスタンプを更新
		updateEditTimestamp(sysHousingCd, editUserId);

		return imgInfo;
	}


	
	/**
	 * 物件画像情報のパス名に格納する値を取得する。<br/>
	 * <br/>
	 * @param housing 更新対象となる物件オブジェクト
	 * 
	 * @return 加工されたパス名
	 */
	protected String createImagePath(Housing housing){
	
		// 画像パスは、下記構成となる。
		// /「定数値」/[物件種別CD]/[都道府県CD]/[市区町村CD]/[システム物件番号]/[サイズ]/シーケンス(10桁).jpg

		// DBのパス名に格納するのは、/[物件種別CD]/[都道府県CD]/[市区町村CD]/[システム物件番号]/　まで。
		
		// 建物オブジェクト、物件オブジェクトを取得し、物件種別CD、都道府県CD、市区町村CD、システム物件CD を取得する。
		// もし、値が未設定の場合はオール9 の値を使用する。
		BuildingInfo buildingInfo = (BuildingInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");


		String prefCd = buildingInfo.getPrefCd();
		if (StringValidateUtil.isEmpty(prefCd)) prefCd = "99";

		String addressCd = buildingInfo.getAddressCd();
		if (StringValidateUtil.isEmpty(addressCd)) addressCd = "99999";

		String housingKindCd = buildingInfo.getHousingKindCd();
		if (StringValidateUtil.isEmpty(housingKindCd)) housingKindCd = "999";

		
		return housingKindCd + "/" + prefCd + "/" + addressCd + "/" + housingInfo.getSysHousingCd() + "/";
	}

	
	
	/**
	 * 部件番号、画像タイプ毎に、最大の枝番を取得する。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 * @param imageType 画像タイプ
	 */
	protected int getMaxDivNo(String sysHousingCd, String imageType){

		// この処理は、排他制御を行っていない。
		// この処理を使用する前には、lockAndRestFlag() で行ロックを行っている必要がある。
		// ただし、lockAndRestFlag() は、メイン画像フラグをリセット更新する事による行ロックなので、
		// 使用には注意する事。

		// システム物件CD、
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("imageType", imageType);
		criteria.addOrderByClause("divNo", false);

		// 同一システム物件CD、画像タイプ内で枝番を降順にソートする。
		// その結果の最大値 + 1 を枝番として復帰する。
		// 該当データが無い場合は 1 を復帰する。
		List<HousingImageInfo> imgList = this.HousingImageInfoDAO.selectByFilter(criteria);

		if (imgList.size() == 0) return 0;
		return imgList.get(0).getDivNo() + 1;
	}

	
	
	/**
	 * システム物件CD 単位に物件画像情報の行ロックを取得する。<br/>
	 * また、その際、メイン画像フラグのリセットを行う。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 */
	protected void lockAndRestFlag(String sysHousingCd) {
		
		// システム物件CD 内のメイン画像フラグを一度リセットする。
		// この操作は、システム物件CD 単位での物件画像情報の行ロッキングの取得でもある。
		DAOCriteria tagetCri = new DAOCriteria();
		tagetCri.addWhereClause("sysHousingCd", sysHousingCd);

		UpdateExpression[] expression
			= new UpdateExpression[] {new UpdateValue("mainImageFlg", "0")};

		this.HousingImageInfoDAO.updateByCriteria(tagetCri, expression);

	}
	
	
	
	/**
	 * 物件画像情報のメイン画像フラグ、および枝番の更新を行う<br/>
	 * 画像種別毎に一番最初に表示する物件画像情報に対してメイン画像フラグを設定する。<br/>
	 * また、画像タイプ毎に表示順で枝番を更新する。<br/>
	 * <br/>
	 * @param getSysHousingCd システム物件CD
	 */
	protected void updateMainFlgAndDivNo(String sysHousingCd){
		DAOCriteria tagetCri = new DAOCriteria();
		tagetCri.addWhereClause("sysHousingCd", sysHousingCd);

		
		// 表示順のみを変更した場合、枝番が重複してエラーが発生する。
		// そこで、トリッキーな方法だが、一度、枝番をマイナスの値に UPDATE した後に枝番の更新処理を行う。
		UpdateExpression[] initExpression
			= new UpdateExpression[] {new FormulaUpdateExpression("div_no = div_no * -1")};

		this.HousingImageInfoDAO.updateByCriteria(tagetCri, initExpression);


		// ソート条件を追加
		// 枝番はマイナスの値に更新されているので、DESC でソートする。
		tagetCri.addOrderByClause("imageType");
		tagetCri.addOrderByClause("sortOrder");
		tagetCri.addOrderByClause("divNo", false);

		// システム物件CD 内の物件画像情報を取得
		List<HousingImageInfo> imgList = this.HousingImageInfoDAO.selectByFilter(tagetCri);

		String oldImgType = "";					// 一行前の物件画像タイプ
		int divNo = 0;							// 枝番
		for (HousingImageInfo imgInfo : imgList){
			++divNo;

			// 更新対象のキー情報を作成
			DAOCriteria updCri = new DAOCriteria();
			updCri.addWhereClause("sysHousingCd", sysHousingCd);
			updCri.addWhereClause("imageType", imgInfo.getImageType());
			updCri.addWhereClause("divNo", imgInfo.getDivNo());

			UpdateExpression[] expression;
			if (!oldImgType.equals(imgInfo.getImageType())){
				// 画像タイプが前回の行から変わった場合、そのレコードをメイン画像として更新する。

				divNo = 1;
				expression = new UpdateExpression[] {new UpdateValue("mainImageFlg", "1"),
													 new UpdateValue("divNo", divNo)};
			} else {
				// 画像タイプが前回の行と同じ場合、メイン画像をオフとして枝番を更新する。

				expression = new UpdateExpression[] {new UpdateValue("mainImageFlg", "0"),
						 new UpdateValue("divNo", divNo)};
			}

			this.HousingImageInfoDAO.updateByCriteria(updCri, expression);
			oldImgType = imgInfo.getImageType();

		}
	}



	/**
	 * 物件情報（一部、建物情報）を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * このメソッドを使用した場合、暗黙の抽出条件（例えば、非公開物件の除外など）が適用される。<br/>
	 * よって、フロント側は基本的にこのメソッドを使用する事。<br/>
	 * <br/>
	 * 
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * @param full false の場合、公開対象外を除外する。　true の場合は除外しない
	 * 
	 * @return 該当件数
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm) throws Exception {
		return searchHousing(searchForm, false);
	}

	/**
	 * 物件情報（一部、建物情報）を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * 
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * @param full false の場合、公開対象外を除外する。　true の場合は除外しない
	 * 
	 * @return 該当件数
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm, boolean full) throws Exception {

		// 検索処理
		List<Housing> housingList = null;
		try {
			housingList = this.searchHousingDAO.searchHousing(searchForm, full);
		} catch (NotEnoughRowsException err) {
			// 範囲外のページが設定された場合、再検索
			int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
			searchForm.setSelectedPage(pageNo);
			housingList = this.searchHousingDAO.searchHousing(searchForm, full);
		}

		// 物件画像情報を取得
		for (Housing housing : housingList) {
			HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
			String sysHousingCd = housingInfo.getSysHousingCd();
			DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("sysHousingCd", sysHousingCd);
			criteria.addOrderByClause("imageType");
			criteria.addOrderByClause("mainImageFlg", false);
			criteria.addOrderByClause("sortOrder");
			List<HousingImageInfo> imageList = HousingImageInfoDAO.selectByFilter(criteria);
			housing.setHousingImageInfos(imageList);
		}

		searchForm.setRows(housingList);

		return searchForm.getMaxRows();
	}



	/**
	 * リクエストパラメータで渡されたシステム物件CD （主キー値）に該当する物件情報を復帰する。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * このメソッドを使用した場合、暗黙の抽出条件（例えば、非公開物件の除外など）が適用される。<br/>
	 * よって、フロント側は基本的にこのメソッドを使用する事。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 * @param full false の場合、公開対象外を除外する。　true の場合は除外しない
	 * 
	 * @return　DB から取得したバリーオブジェクトを格納したコンポジットクラス
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public Housing searchHousingPk(String sysHousingCd) throws Exception {
		return searchHousingPk(sysHousingCd, false);
	}
	
	

	/**
	 * リクエストパラメータで渡されたシステム物件CD （主キー値）に該当する物件情報を復帰する。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * ※基本的に、パラメータ full を true にして使用するのは管理画面のみ。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 * @param full false の場合、公開対象外を除外する。　true の場合は除外しない
	 * 
	 * @return　DB から取得したバリーオブジェクトを格納したコンポジットクラス
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public Housing searchHousingPk(String sysHousingCd, boolean full)
			throws Exception {

		// 物件情報のインスタンスを生成する。
		Housing housing = createHousingInstace();


		// confMainData() は、１対１の関係にある関連テーブルの情報を取得し、housing へ設定する。
		// もし該当するデータが無い場合、housing へ何も設定せずに null を復帰してくるので、戻り値が
		// null の場合は処理を中断する。
		if (confMainData(housing, sysHousingCd, full) == null) return null; 


		// 物件設備情報を取得する。
		confHousingEquip(housing, sysHousingCd);

		// 物件画像情報を取得する。
		confHousingImage(housing, sysHousingCd);

		// 物件拡張属性情報の取得
		// このテーブルの取得結果は、Map オブジェクトに変換して復帰する。
		confHousingExtInfo(housing, sysHousingCd);
		
		return housing;
	}

	
	
	/**
	 * 物件詳細情報と １対１の関係にあるメインの関連テーブルの情報を取得し、Housing オブジェクトへ設定する。<br/>
	 * <br/>
	 * @param housing 値の設定先となる Housing オブジェクト
	 * @param sysHousingCd 取得対象システム物件CD
	 * @param full false の場合、公開対象外を除外する。　true の場合は除外しない
	 * 
	 * @return 取得結果 （該当なしの場合、null）
	 * @throws Exception 委譲先がスローする例外 
	 */
	protected JoinResult confMainData(Housing housing, String sysHousingCd, boolean full)
			throws Exception{

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("housingInfo", "sysHousingCd", sysHousingCd, DAOCriteria.EQUALS, false);

		// 引数 full = false の場合、暗黙の除外条件を追加する。
		// 通常、フロント側から情報を取得する場合はこの引数を false にする。
		if (!full) {
			addNegativeFilter(criteria);
		}

		// 物件詳細情報と 1 対 1 の関係にある物件情報を取得する。
		// その際、建物関連の情報は結合しない。　建物関連の情報は建物情報の model へ処理を委譲して取得する。
		List<JoinResult> result = this.housingMainJoinDAO.selectByFilter(criteria);
		if (result.size() == 0) return null;


		// 物件情報を設定
		housing.setHousingInfo(result.get(0));

		// 物件情報が取得できた場合は、建物情報の model を使用して建物情報を取得する。
		String sysBuildingCd = ((HousingInfo)result.get(0).getItems().get("housingInfo")).getSysBuildingCd();
		housing.setBuilding(this.buildingManager.searchBuildingPk(sysBuildingCd));

		return result.get(0);
	}



	/**
	 * 物件設備情報を取得する。<br/>
 	 * 取得結果は、LinkedHashMap に変換して housing へ格納される。<br/>
 	 * ・Key = 設備CD<br/>
 	 * ・Value = 設備マスタ<br/>
 	 * <br/>
	 * @param housing 値の設定先となる Housing オブジェクト
	 * @param sysHousingCd 取得対象システム物件CD
	 * 
	 * @return 取得結果
	 */
	protected Map<String, EquipMst> confHousingEquip(Housing housing, String sysHousingCd){

		// 物件設備情報の取得
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("housingEquipInfo", "sysHousingCd", sysHousingCd, DAOCriteria.EQUALS, false);
		criteria.addOrderByClause("equipMst", "sortOrder", true);
		List<JoinResult> list = this.housingEquipListDAO.selectByFilter(criteria);

		
		// List オブジェクトだと、入力画面などで選択状態にするのが面倒なので、設備CD を Key とした Map に変換する。
		Map<String, EquipMst> equipMap = new LinkedHashMap<>();
		
		for (JoinResult result : list) {
			// Key は設備CD を使用する。
			String key = ((HousingEquipInfo)result.getItems().get("housingEquipInfo")).getEquipCd();
			EquipMst value = (EquipMst)result.getItems().get("equipMst");
			equipMap.put(key, value);
		}

		housing.setHousingEquipInfos(equipMap);
		return equipMap;
	}


	
	/**
	 * 物件画像情報を取得する。<br/>
	 * 取得した結果を housing オブジェクトへ格納する。<br/>
	 * <br/>
	 * @param housing 値の設定先となる Housing オブジェクト
	 * @param sysHousingCd 取得対象システム物件CD
	 * 
	 * @return 取得結果
	 */
	protected List<HousingImageInfo> confHousingImage(Housing housing, String sysHousingCd){

		// 物件画像情報を取得する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addOrderByClause("sortOrder");
		List<HousingImageInfo> list = this.HousingImageInfoDAO.selectByFilter(criteria);

		housing.setHousingImageInfos(list);
		
		return list;
		
	}

	
	
	/**
	 * 物件拡張属性情報に格納されている情報を Map オブジェクトに変換して格納する。<br/>
	 * Map オブジェクトの構成は、以下の通り。<br/>
	 *     ・Key = 物件拡張情報のカテゴリ名（category）
	 *     ・Value = カテゴリの該当する、Key値が設定された Map オブジェクト（Key = keyName列、Value = dataValue列）
	 * <br/>
	 * @param housing 格納先となる Housing オブジェクト
	 * @param sysHousingCd 取得対象システム物件CD
	 * 
	 */
	protected void confHousingExtInfo(Housing housing, String sysHousingCd){

		// 物件拡張属性情報を取得する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addOrderByClause("category");
		criteria.addOrderByClause("keyName");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);

		
		// 格納先の Map を取得する。
		// この Map は LinedHashMap なので、追加した順番が保障される。
		Map<String, Map<String, String>> extMap = housing.getHousingExtInfos();


		// 物件拡張属性情報は、カテゴリ名、Key名 の順でソートされている。
		for (HousingExtInfo ext : list) {
			// カテゴリ名を取得
			String category = ext.getCategory();

			// カテゴリ名に該当する Map を取得する。
			Map<String, String> cateMap = extMap.get(category);

			// もし存在しない場合はカテゴリ名に該当する Map を作成する。
			if (cateMap == null){
				cateMap = new LinkedHashMap<>();
				extMap.put(category, cateMap);
			}

			// カテゴリ名の Map に取得した Key、Value を追加する。
			cateMap.put(ext.getKeyName(), ext.getDataValue());
		}

	}


	
	/**
	 * パラメータで渡された Map の情報で物件拡張属性情報を更新する。（システム物件CD 単位）<br/>
	 * 更新は、Delete & Insert で処理する。<br/>
	 * <br/>
	 * inputData の Map の構成は以下の通り。<br/>
	 *   ・key = カテゴリ名 （拡張属性情報の、category に格納する値）
	 *   ・value = 値が格納された Map オブジェクト
	 * inputData の value に格納される Map の構成は以下の通り。<br/>
	 *   ・key = Key名 （拡張属性情報の、key_name に格納する値）
	 *   ・value = 入力値 （拡張属性情報の、data_value に格納する値）
	 * <br/>
	 * @param sysHousingCd 更新対象システム物件CD
	 * @param inputData 登録情報となる Map オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 親となる物件基本情報が存在しない場合
	 */
	@Override
	public void updExtInfo(String sysHousingCd, Map<String, Map<String,String>> inputData, String editUserId)
			throws Exception, NotFoundException {

		// カテゴリ単位で削除してからの入れ替えなので、一度データを削除する。
		// タイムスタンプは、この時に更新する。
		delExtInfo(sysHousingCd, editUserId);


		// Map が null の場合、削除のみを実行して復帰する。
		if (inputData == null) return;


		// Map をループし、カテゴリ情報を取得する。
		for (Map.Entry<String, Map<String,String>> e : inputData.entrySet()){
			// カテゴリの Map に格納されている Map オブジェクト分ループして、カテゴリ単位のデータを登録する。
			updExtInfo(sysHousingCd, e.getKey(), e.getValue(), editUserId);
		}
	}

	
	
	/**
	 * パラメータで渡されたシステム物件CD に該当する物件拡張属性情報を削除する。（システム物件CD 単位）<br/>
	 * <br/>
	 * @param sysHousingCd 削除対象システム物件CD
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String editUserId) {

		// 親レコードのタイムスタンプを更新
		updateEditTimestamp(sysHousingCd, editUserId);

		// 削除条件生成
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		this.housingExtInfoDAO.deleteByFilter(criteria);

	}

	
	
	/**
	 * パラメータで渡された Map の情報で物件拡張属性情報を更新する。（カテゴリー 単位）<br/>
	 * 更新は、Delete & Insert で処理する。<br/>
	 * <br/>
	 * inputData の Map の構成は以下の通り。<br/>
	 *   ・key = Key名 （拡張属性情報の、key_name に格納する値）
	 *   ・value = 入力値 （拡張属性情報の、data_value に格納する値）
	 * <br/>
	 * <br/>
	 * @param sysHousingCd 更新対象システム物件CD
	 * @param category 更新対象カテゴリ名
	 * @param inputData 登録情報となる Map オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 親となる物件基本情報が存在しない場合
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category, Map<String, String> inputData, String editUserId)
			throws Exception, NotFoundException {

		// カテゴリ単位で削除してからの入れ替えなので、一度データを削除する。
		// タイムスタンプは、この時に更新する。
		delExtInfo(sysHousingCd, category, editUserId);
		
		
		// Map が null の場合、削除のみを実行して復帰する。
		if (inputData == null) return;


		// Map をループし、物件拡張属性情報を追加する。
		for (Map.Entry<String, String> e : inputData.entrySet()){
			updExtInfo(sysHousingCd, category, e.getKey(), e.getValue(), editUserId);
		}

	}


	
	/**
	 * パラメータで渡されたシステム物件CD に該当する物件拡張属性情報を削除する。（カテゴリー 単位）<br/>
	 * <br/>
	 * @param sysHousingCd 削除対象システム物件CD
	 * @param category 削除対象カテゴリ名
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category, String editUserId)	{

		// 親レコードのタイムスタンプを更新
		updateEditTimestamp(sysHousingCd, editUserId);

		// 削除条件生成
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("category", category);
		this.housingExtInfoDAO.deleteByFilter(criteria);
		
	}



	/**
	 * パラメータで渡された Map の情報で物件拡張属性情報を更新する。（Key 単位）<br/>
	 * 更新は、Delete & Insert で処理する。<br/>
	 * <br/>
	 * @param sysHousingCd 更新対象システム物件CD
	 * @param category 更新対象カテゴリ名
	 * @param key 更新対象Key
	 * @param value 更新する値
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 親となる物件基本情報が存在しない場合
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category, String key, String value, String editUserId)
			throws Exception, NotFoundException {

		// 親レコードのタイムスタンプを更新
		updateEditTimestamp(sysHousingCd, editUserId);

		
		// 更新対象オブジェクトを取得
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("category", category);
		criteria.addWhereClause("keyName", key);
		List<HousingExtInfo> list =this.housingExtInfoDAO.selectByFilter(criteria);

		if (list.size() == 0) {
			// 該当データが存在しない場合、新規作成
			HousingExtInfo housingExtInfo
				= (HousingExtInfo) this.valueObjectFactory.getValueObject("HousingExtInfo");

			housingExtInfo.setSysHousingCd(sysHousingCd);
			housingExtInfo.setCategory(category);
			housingExtInfo.setKeyName(key);
			housingExtInfo.setDataValue(value);

			try {
				this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
			} catch (DataIntegrityViolationException e){
				log.warn(e.getMessage(), e);
				throw new NotFoundException();
			}

		} else {
			// 該当データが存在した場合、Value を 更新
			HousingExtInfo housingExtInfo = list.get(0);
			housingExtInfo.setDataValue(value);

			this.housingExtInfoDAO.update(new HousingExtInfo[]{housingExtInfo});
		}

	}



	/**
	 * パラメータで渡されたシステム物件CD に該当する物件拡張属性情報を削除する。（キー 単位）<br/>
	 * <br/>
	 * @param sysHousingCd 削除対象システム物件CD
	 * @param category 削除対象カテゴリ名
	 * @param category 削除対象 Key
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category, String key, String editUserId){

		// 親レコードのタイムスタンプを更新
		updateEditTimestamp(sysHousingCd, editUserId);

		// 削除条件生成
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("category", category);
		criteria.addWhereClause("keyName", key);
		this.housingExtInfoDAO.deleteByFilter(criteria);

	}

	
	
	/**
	 * 物件基本情報のタイムスタンプ情報を更新する。<br/>
	 * <br/>
	 * @param sysHousingCd 更新対象システム物件CD
	 * @param editUserId 更新者ID
	 */
	protected void updateEditTimestamp(String sysHousingCd, String editUserId){

		// 更新条件を生成
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		
		// 更新オブジェクトを作成
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("updUserId", editUserId),
									  new UpdateValue("updDate", new Date())};

		// 親レコードのタイムスタンプ更新
		this.housingInfoDAO.updateByCriteria(criteria, updateExpression);

	}
	
	
	
	/**
	 * 非公開となる条件を引数で渡された検索条件オブジェクトに設定する。<br/>
	 * このメソッドは、searchHousingPk()、searchBuilding() の full パラメータが false の場合に
	 * 実行される。<br/>
	 * <br/>
	 * デフォルト仕様として抽出対象になるには以下の条件を満たす必要がある。<br/>
	 * <ul>
	 *   <li>公開物件である事。（非公開フラグ hidden_flg = 1 の物件ステータス情報が存在しない事。）</li>
	 * </ul>
	 * <br/>
	 * 条件をカスタマイズする場合は、このメソッドをオーバーライドする事。<br/>
	 * <br/>
	 * @param criteria 検索で仕様する検索オブジェクト
	 * 
	 */
	protected void addNegativeFilter(DAOCriteria criteria) {

		// 非公開フラグが null か　0 である事。
		OrCriteria orCriteria = new OrCriteria();
		orCriteria.addWhereClause(new DAOCriteriaWhereClause("housingStatusInfo", "hiddenFlg", "0", DAOCriteria.EQUALS, false));
		orCriteria.addWhereClause(new DAOCriteriaWhereClause("housingStatusInfo", "hiddenFlg", null, DAOCriteria.IS_NULL, false));
		criteria.addSubCriteria(orCriteria);

	}



	/**
	 * 物件情報オブジェクトのインスタンスを生成する。<br/>
	 * もし、カスタマイズで物件情報を構成するテーブルを追加した場合、このメソッドをオーバーライドする事。<br/>
	 * <br/>
	 * @return Housing のインスタンス
	 */
	public Housing createHousingInstace() {
		return new Housing();
	}
}
