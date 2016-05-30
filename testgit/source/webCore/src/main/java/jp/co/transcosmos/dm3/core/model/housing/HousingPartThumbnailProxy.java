package jp.co.transcosmos.dm3.core.model.housing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingImgForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.util.ThumbnailCreator;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

/**
 * 物件情報用 Model のこだわり条件、サムネイル画像作成 proxy クラス.
 * この proxy クラスでは、物件画像ファイルのサムネイル作成や、こだわり条件情報の再構築などを行う。<br/>
 * 物件情報用 Model は個別カスタマイズで継承・委譲により拡張される可能性がある。<br/>
 * 画像ファイルの操作はロールバックできないので、全てのDB更新処理完了後に実行する必要がある。<br/>
 * また、こだわり条件情報の再構築も全てのDB更新が完了している必要がある。<br/>
 * その為、これらの処理は proxy 内から実行して Model 本体から分離する。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.09	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public class HousingPartThumbnailProxy implements HousingManage {

	/** 委譲先となる物件 Model クラス */
	protected HousingManage housingManage;

	/** 物件画像情報用　DAO */
	protected DAO<HousingImageInfo> housingImageInfoDAO;

	/** 共通パラメータオブジェクト */
	protected CommonParameters commonParameters;

	/** こだわり条件生成クラスのリスト */
	protected List<HousingPartCreator> partCreateors;

	/** サムネイル画像作成クラス */
	protected ThumbnailCreator thumbnailCreator;



	/**
	 * 委譲先となる物件 Model を設定する。<br/>
	 * <br/>
	 * @param housingManage 委譲先となる物件 Model
	 */
	public void setHousingManage(HousingManage housingManage) {
		this.housingManage = housingManage;
	}

	/**
	 * 物件画像情報用 DAO を設定する。<br/>
	 * <br/>
	 * @param housingImageInfoDAO 物件画像情報用 DAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		this.housingImageInfoDAO = housingImageInfoDAO;
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
	 * こだわり条件生成クラスのリストを設定する。<br/>
	 * <br/>
	 * @param partCreateors  こだわり条件生成クラスのリスト
	 */
	public void setPartCreateors(List<HousingPartCreator> partCreateors) {
		this.partCreateors = partCreateors;
	}

	/**
	 * サムネイル画像作成クラスを設定する。<br/>
	 * <br/>
	 * @param thumbnailCreator サムネイル画像作成クラス
	 */
	public void setThumbnailCreator(ThumbnailCreator thumbnailCreator) {
		this.thumbnailCreator = thumbnailCreator;
	}



	/**
	 * 物件基本情報新規登録時のフィルター処理<br/>
	 * 委譲先の物件 model を使用して物件基本情報を登録後、こだわり条件を再構築する。<br/>
	 * <br/>
	 * @param inputForm 物件基本情報の入力フォーム
	 * @param editUserId 更新者ID
	 * 
	 * @return 採番されたシステム物件CD
	 */
	@Override
	public String addHousing(HousingForm inputForm, String editUserId)
			throws Exception {

		// 物件基本情報の登録処理へ委譲する。
		String sysHousingCd = this.housingManage.addHousing(inputForm, editUserId);

		// こだわり条件を再構築する。
// 2015.04.28 H.Mizuno 新規登録時のこだわり条件作成時に使用するキーの誤りを修正
//		createPartInfo(inputForm.getSysHousingCd(), "addHousing");
		createPartInfo(sysHousingCd, "addHousing");

		return sysHousingCd;
	}



	/**
	 * 物件基本情報更新時のフィルター処理<br/>
	 * 委譲先の物件 model を使用して物件基本情報を更新後、こだわり条件を再構築する。<br/>
	 * <br/>
	 * @param inputForm 物件基本情報の入力フォーム
	 * @param editUserId 更新者ID
	 */
	@Override
	public void updateHousing(HousingForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// 物件基本情報の更新処理へ委譲する。
		this.housingManage.updateHousing(inputForm, editUserId);

		// こだわり条件を再構築する。
		createPartInfo(inputForm.getSysHousingCd(), "updateHousing");
	}



	/**
	 * 物件情報削除時のフィルター処理<br/>
	 * 物件情報を削除する前に、削除対象となる物件画像情報を取得し、パス名を集約する。<br/>
	 * 委譲先の物件 model を使用して物件情報の削除を行い、該当する公開物件画像ファイルを削除する。<br/>
	 * <br/>
	 * @param inputForm 物件基本情報の入力フォーム
	 * @param editUserId 更新者ID
	 */
	@Override
	public void delHousingInfo(HousingForm inputForm, String editUserId)
			throws Exception{

		//　画像削除パスを格納する Set オブジェクト
		Set<String> delPath = new HashSet<>();

		// 削除対象となる物件画像情報を先に取得しておく。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());
		List<HousingImageInfo> delImgList = this.housingImageInfoDAO.selectByFilter(criteria);

		// 削除はフォルダ単位で行うので、画像情報の値を Set オブジェクトに格納して重複を取り除く
		for (HousingImageInfo imgInfo : delImgList){
			if (!delPath.contains(imgInfo.getPathName())){
				delPath.add(imgInfo.getPathName());
			}
		}


		// 委譲先の model を実行して物件情報を削除する。
		this.housingManage.delHousingInfo(inputForm, editUserId);


		// 物件情報の削除後、公開されている物件画像ファイルを削除する。
		this.thumbnailCreator.deleteImageDir(getImgOpenRootPath(), delPath);
	}

	
	
	/**
	 * 物件詳細更新時のフィルター処理<br/>
	 * 委譲先の物件 model を使用して物件詳細情報を追加・更新後、こだわり条件を再構築する。<br/>
	 * <br/>
	 * @param inputForm 物件詳細情報の入力フォーム
	 * @param editUserId 更新者ID
	 */
	@Override
	public void updateHousingDtl(HousingDtlForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// 物件詳細情報の更新処理へ委譲する。
		this.housingManage.updateHousingDtl(inputForm, editUserId);

		// こだわり条件を再構築する。
		createPartInfo(inputForm.getSysHousingCd(), "updateHousingDtl");
	}



	/**
	 * 物件設備更新時のフィルター処理<br/>
	 * 委譲先の物件 model を使用して物件設備情報を更新後、こだわり条件を再構築する。<br/>
	 * <br/>
	 * @param inputForm 物件詳細情報の入力フォーム
	 * @param editUserId 更新者ID
	 */
	@Override
	public void updateHousingEquip(HousingEquipForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// 物件設備情報の更新処理へ委譲する。
		this.housingManage.updateHousingEquip(inputForm, editUserId);

		// こだわり条件を再構築する。
		createPartInfo(inputForm.getSysHousingCd(), "updateHousingEquip");
	}



	/**
	 * 物件画像追加時のフィルター処理<br/>
	 * <br/>
	 * @param inputForm 物件詳細情報の入力フォーム
	 * @param editUserId 更新者ID
	 * 
	 * @return 新たに追加した画像情報のリスト
	 */
	@Override
	public List<HousingImageInfo> addHousingImg(HousingImgForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// 委譲先の処理を実行する。
		// このメソッドの戻り値は、新規追加した画像情報のリストが復帰される。
		List<HousingImageInfo> imgList = this.housingManage.addHousingImg(inputForm, editUserId);

		// こだわり条件を再構築する。
		createPartInfo(inputForm.getSysHousingCd(), "addHousingImg");

		// note
		// 物件画像のパスは、下記の通り。
		// /「定数値」/[物件種別CD]/[都道府県CD]/[市区町村CD]/[システム物件番号]/[サイズ]/シーケンス(10桁).jpg
		// DB に格納されているのは、[物件種別CD]　～ [システム物件番号]　までの値。


		// サムネイルの作成元フォルダ名　（日付を指定したフォルダ階層まで）
		String srcRoot = this.commonParameters.getHousImgTempPhysicalPath();
		if (!srcRoot.endsWith("/")) srcRoot += "/";
		srcRoot += inputForm.getTempDate() + "/";


		// 追加する入力データ０件でこのメソッドを実行する事は通常有り得ないが、一応、チェックしておく。
		// 追加された物件画像情報が存在しない場合は null を復帰する。
		if (imgList == null) return null;
		
		
		// サムネイルを作成するファイル名のマップオブジェクトを作成する。
		Map<String, String> thumbnailMap = new HashMap<>();
		for (HousingImageInfo imgInfo : imgList){

			// Map の Key は、サムネイル作成元のファイル名（フルパス）
			String key = srcRoot + imgInfo.getFileName();
			// Map の Value は、サムネイルの出力先パス（ルート～システム物件番号までのパス）
			String value = getImgOpenRootPath(imgInfo) + imgInfo.getPathName();

			thumbnailMap.put(key, value);
		}
		// 仮フォルダのファイルを使用して公開フォルダへサムネイルを作成
		this.thumbnailCreator.create(thumbnailMap);

		return imgList;
	}

	
	
	/**
	 * 物件画像更新時のフィルター処理<br/>
	 * ※画像ファイルの入れ替えは発生しない。　削除のみ。<br/>
	 * <br/>
	 * @param inputForm 物件詳細情報の入力フォーム
	 * @param editUserId 更新者ID
	 * 
	 * @return 削除が発生した画像情報
	 */
	@Override
	public List<HousingImageInfo> updHousingImg(HousingImgForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// 委譲先の処理を実行する。
		// 削除された物件画像情報のリストが戻される。
		List<HousingImageInfo> imgList = this.housingManage.updHousingImg(inputForm, editUserId);

		// こだわり条件を再構築する。
		createPartInfo(inputForm.getSysHousingCd(), "updHousingImg");

		
		// null の場合、削除画像が無かったとして null を復帰する。
		if (imgList == null) return null;


		// 物件画像の更新処理では、新たな画像ファイルが追加される事は無いが、
		// 画像ファイルの削除が行われる場合がる。
		//　委譲先メソッドの戻り値は、削除された物件画像情報のリストなので、該当する物件の画像ファイルを削除する。 
		for (HousingImageInfo imgInfo : imgList){

			String filePath = getImgOpenRootPath(imgInfo) + imgInfo.getPathName();

			// 画像ファイルの削除処理を実行する。
			this.thumbnailCreator.deleteImgFile(filePath, imgInfo.getFileName());
		}
		
		return imgList;
	}

	
	
	/**
	 * 物件画像削除時のフィルター処理<br/>
	 * ※updHousingImg() からでも削除可。　個別削除用機能。<br/>
	 * <br/>
	 * @param inputForm 物件詳細情報の入力フォーム
	 * @param editUserId 更新者ID
	 */
	@Override
	public HousingImageInfo delHousingImg(String sysHousingCd, String imageType, int divNo,	String editUserId)
			throws Exception {

		// 委譲先の処理を実行して物件画像情報を削除する。
		HousingImageInfo imgInfo = this.housingManage.delHousingImg(sysHousingCd, imageType, divNo, editUserId);


		// こだわり条件を再構築する。
		createPartInfo(sysHousingCd, "delHousingImg");


		// 画像ファイルの削除処理
		String filePath = getImgOpenRootPath(imgInfo) + imgInfo.getPathName();
		this.thumbnailCreator.deleteImgFile(filePath, imgInfo.getFileName());

		return imgInfo;
	}

	
	
	/**
	 * 物件検索処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 * @param searchForm 検索条件フォーム
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm) throws Exception {
		// 物件情報の検索処理では、委譲先をそのまま実行する。
		return this.housingManage.searchHousing(searchForm);
	}
	
	/**
	 * 物件検索処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 * @param searchForm 検索条件フォーム
	 * @param full false の場合、公開対象外を除外する。　true の場合は除外しない
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm, boolean full)
			throws Exception {
		// 物件情報の検索処理では、委譲先をそのまま実行する。
		return this.housingManage.searchHousing(searchForm, full);
	}


	/**
	 * 物件詳細取得処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 * @param sysHousingCd 取得対象システム物件CD
	 */
	@Override
	public Housing searchHousingPk(String sysHousingCd) throws Exception {
		// 物件情報の主キーによる検索処理では、委譲先をそのまま実行する。
		return this.housingManage.searchHousingPk(sysHousingCd);
	}

	/**
	 * 物件詳細取得処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 * @param sysHousingCd 取得対象システム物件CD
	 * @param full false の場合、公開対象外を除外する。　true の場合は除外しない
	 */
	@Override
	public Housing searchHousingPk(String sysHousingCd, boolean full)
			throws Exception {
		// 物件情報の主キーによる検索処理では、委譲先をそのまま実行する。
		return this.housingManage.searchHousingPk(sysHousingCd, full);
	}


	/**
	 * 物件拡張属性更新のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 */
	@Override
	public void updExtInfo(String sysHousingCd,	Map<String, Map<String, String>> inputData, String editUserId)
			throws Exception, NotFoundException {

		// 物件拡張属性の更新処理では、委譲先の処理をそのまま実行する。
		this.housingManage.updExtInfo(sysHousingCd,	inputData, editUserId);
	}

	/**
	 * 物件拡張属性削除のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String editUserId)
			throws Exception {

		// 物件拡張属性の削除処理では、委譲先の処理をそのまま実行する。
		this.housingManage.delExtInfo(sysHousingCd, editUserId);
	}

	/**
	 * 物件拡張属性更新のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category, Map<String, String> inputData, String editUserId)
			throws Exception, NotFoundException {

		// 物件拡張属性の更新処理では、委譲先の処理をそのまま実行する。
		this.housingManage.updExtInfo(sysHousingCd, category, inputData, editUserId);
	}

	/**
	 * 物件拡張属性削除のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category, String editUserId)
			throws Exception {

		// 物件拡張属性の削除処理では、委譲先の処理をそのまま実行する。
		this.housingManage.delExtInfo(sysHousingCd, category, editUserId);
	}

	/**
	 * 物件拡張属性更新のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category, String key, String value, String editUserId)
			throws Exception,NotFoundException {

		// 物件拡張属性の更新処理では、委譲先の処理をそのまま実行する。
		this.housingManage.updExtInfo(sysHousingCd, category, key, value, editUserId);
	}

	/**
	 * 物件拡張属性削除のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category, String key, String editUserId)
			throws Exception {

		// 物件拡張属性の削除処理では、委譲先の処理をそのまま実行する。
		this.housingManage.delExtInfo(sysHousingCd, category, key, editUserId);
	}


	
	/**
	 * 物件情報オブジェクトのインスタンス処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 * @return Housing のインスタンス
	 */
	@Override
	public Housing createHousingInstace() {
		// 物件オブジェクトのインスタンス生成は、委譲先の処理をそのまま実行する。
		return this.housingManage.createHousingInstace();
	}



	/**
	 * こだわり条件を再作成する。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 * @param methodName 実行された物件 model のメソッド名
	 * 
	 * @throws Exception 
	 */
	protected void createPartInfo(String sysHousingCd, String methodName) throws Exception{

		// こだわり条件生成用のクラスが設定されていない場合はなにもしない。
		if (this.partCreateors == null || this.partCreateors.size() == 0) return;


		// 条件の作成対象となる物件情報を取得する。
		Housing housing = this.housingManage.searchHousingPk(sysHousingCd, true);

		for (HousingPartCreator creator : this.partCreateors){
			// 実行対象外の場合は次のこだわり条件作成クラスへ
			if (!creator.isExecuteMethod(methodName)) continue;

			// こだわり条件を作成する。
			creator.createPart(housing);
		}
	}



	/**
	 * 公開先となる物件画像の Root パスを取得する。<br/>
	 * Form オブジェクトが省略された場合、全ての公開先 Root フォルダを復帰する。<br/>
	 * <br/>
	 * @return 公開されている物件画像の Root パスリスト
	 */
	protected List<String> getImgOpenRootPath(){
		// 基本機能としては、公開先フォルダは１個のみ。
		List<String> imgOpenRootList = new ArrayList<>();
		imgOpenRootList.add(this.commonParameters.getHousImgOpenPhysicalPath());
		return imgOpenRootList;
	}

	/**
	 * 指定された物件画像情報に該当する公開先となる物件画像の Root パスを取得する。<br/>
	 * <br/>
	 * @return 公開されている物件画像の Root パスリスト
	 */
	protected String getImgOpenRootPath(HousingImageInfo housingImageInfo){
		// 基本機能では、サムネイルの出力先ルートフォルダは固定値
		String destRoot = this.commonParameters.getHousImgOpenPhysicalPath();
		if (!destRoot.endsWith("/")) destRoot += "/";

		return destRoot;
	}
}
