package jp.co.transcosmos.dm3.corePana.model.reform;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.core.util.ThumbnailCreator;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformDtlForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

/**
 * リフォーム情報用 Model の proxy クラス. この proxy
 * クラスでは、リフォーム画像ファイルのサムネイル作成や、リフォーム詳細情報のPDFアップロードなどを行う。<br/>
 * リフォーム情報用 Model は個別カスタマイズで継承・委譲により拡張される可能性がある。<br/>
 * 画像ファイルの操作はロールバックできないので、全てのDB更新処理完了後に実行する必要がある。<br/>
 * その為、これらの処理は proxy 内から実行して Model 本体から分離する。<br/>
 * <p>
 *
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.04.13	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 *
 */
public class ReformPartThumbnailProxy implements ReformManage {

	/** 委譲先となるリフォーム Model クラス */
	protected ReformManage reformManager;

	/** 共通パラメータオブジェクト */
	protected PanaCommonParameters commonParameters;

	/** サムネイル画像作成処理Util */
	private ThumbnailCreator thumbnailCreator;
	
	/** こだわり条件生成クラスのリスト */
	protected List<HousingPartCreator> partCreateors;

	/** サムネイル画像作成クラス */
	private ImgUtils imgUtils;
	/**
	 * サムネイル画像作成クラスを設定する。<br/>
	 * <br/>
	 * @param imgUtils サムネイル画像作成クラス
	 */
	public void setImgUtils(ImgUtils imgUtils) {
		this.imgUtils = imgUtils;
	}

	/**
	 * 委譲先となるリフォーム Model を設定する。<br/>
	 * <br/>
	 *
	 * @param housingManage
	 *            委譲先となるリフォーム Model
	 */
	public void setReformManager(ReformManage reformManager) {
		this.reformManager = reformManager;
	}

	public void setPartCreateors(List<HousingPartCreator> partCreateors) {
		this.partCreateors = partCreateors;
	}

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param commonParameters
	 *            共通パラメータオブジェクト
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * サムネイル画像作成処理Utilを設定する。<br/>
	 * <br/>
	 *
	 * @param fileUtil
	 *            パナ共通ファイル処理Util
	 */
	public void setThumbnailCreator(ThumbnailCreator thumbnailCreator) {
		this.thumbnailCreator = thumbnailCreator;
	}

	/**
	 * パラメータで渡された リフォームプラン情報を新規追加する。<br/>
	 * システムリフォームCD は自動採番されるので、ReformPlan の sysReformCd プロパティには値を設定しない事。<br/>
	 * <br/>
	 *
	 * @param reformPlan
	 *            リフォームプラン情報
     * @param inputForm 入力値が格納された Form オブジェクト
	 * @return システムリフォームCD
	 */
	@Override
	public String addReformPlan(ReformPlan reformPlan, ReformInfoForm inputForm, String userId) throws Exception {
		// リフォーム情報の登録処理へ委譲する。
		String sysReformCd = this.reformManager.addReformPlan(reformPlan, inputForm, userId);
		createPartInfo(inputForm.getSysHousingCd(), "addReform");

        // 建物基本情報を取得する。
        BuildingInfo buildingInfo = this.reformManager.searchBuildingInfo(inputForm.getSysHousingCd());
        String uploadPath = ReformManageImpl.getUploadPath(buildingInfo, inputForm.getSysHousingCd());

		// Tempパスの取得
		String temPath = this.commonParameters.getHousImgTempPhysicalPath();
		temPath = PanaFileUtil.conPhysicalPath(temPath, inputForm.getTemPath());

		// baseパスの取得
		String basePath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
		// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システムリフォーム番号/chart/
		basePath = PanaFileUtil.conPhysicalPath(basePath, uploadPath);
		basePath = PanaFileUtil.conPhysicalPath(basePath, commonParameters.getAdminSiteChartFolder());

		// Tempフォルダーのファイル⇒公開フォルダーへ移動
		this.createImgFile(inputForm, basePath);

		return sysReformCd;
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

		PanaHousing housing = (PanaHousing)this.reformManager.searchHousingByPk(sysHousingCd);

		for (HousingPartCreator creator : this.partCreateors){
			// 実行対象外の場合は次のこだわり条件作成クラスへ
			if (!creator.isExecuteMethod(methodName)) continue;

			// こだわり条件を作成する。
			creator.createPart(housing);
		}
	}

	/**
	 * リフォームプラン情報の更新を行う<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            リフォーム情報フォーム
	 * @param userId
	 *            ログインユーザID
	 *
	 * @exception NotFoundException
	 *                更新対象が存在しない場合
	 */
	@Override
	public void updateReformPlan(ReformInfoForm inputForm, String userId) throws Exception {
		// リフォーム情報の更新処理へ委譲する。
		this.reformManager.updateReformPlan(inputForm, userId);
		
		createPartInfo(inputForm.getSysHousingCd(), "updReform");

        // 建物基本情報を取得する。
        BuildingInfo buildingInfo = this.reformManager.searchBuildingInfo(inputForm.getSysHousingCd());
        String uploadPath = ReformManageImpl.getUploadPath(buildingInfo, inputForm.getSysHousingCd());

		// レーターチァート画像ファイルを削除
		if ("on".equals(inputForm.getReformImgDel())) {
			String rootPath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
			String file[] = inputForm.getImgFile2().split("/");
			String imgName = file[file.length-1];

			// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システム物件番号/
			String delPath = PanaFileUtil.conPhysicalPath(rootPath, uploadPath);
			Map<String, String> delFiles = new HashMap<String, String>();
			delFiles.put(imgName, delPath);

			// 委譲先の model を実行してリフォーム情報を削除する。
			for (String keyFileName : delFiles.keySet()) {
				PanaFileUtil.delPhysicalPathFile(delFiles.get(keyFileName),
						keyFileName);
			}
		}
		// レーターチァート画像ファイルを追加
		if("1".equals(inputForm.getImgSelFlg())){

			// Tempパスの取得
			String temPath = this.commonParameters.getHousImgTempPhysicalPath();
			temPath = PanaFileUtil.conPhysicalPath(temPath, inputForm.getTemPath());

			// baseパスの取得
			String basePath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
			// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システムリフォーム番号/chart/
			basePath = PanaFileUtil.conPhysicalPath(basePath, uploadPath);
			basePath = PanaFileUtil.conPhysicalPath(basePath, commonParameters.getAdminSiteChartFolder());

			// Tempフォルダーのファイル⇒公開フォルダーへ移動
			this.createImgFile(inputForm, basePath);

			// 委譲先の model を実行してリフォーム情報を削除する。
			String file[] = inputForm.getImgFile2().split("/");
			String imgName = file[file.length-1];
			Map<String, String> delFiles = new HashMap<String, String>();
			delFiles.put(imgName, basePath);
			for (String keyFileName : delFiles.keySet()) {
				PanaFileUtil.delPhysicalPathFile(delFiles.get(keyFileName),
						keyFileName);
			}

		}
	}


	/**
	 * サムネイル画像を作成する。<br/>
	 * また、オリジナルサイズの画像を、画像サイズの階層が full のフォルダへ配置する。<br/>
	 * thumbnailMap の構造は下記の通り。　ファイル名は元のファイル名が使用される。<br/>
	 * <ul>
	 * <li>Key : サムネイル作成元のファイル名（フルパス）</li>
	 * <li>value : サムネイルの出力先パス（ルート～システム物件番号までのパス。　サイズや、ファイル名は含まない。）</li>
	 * </ul>
	 *
	 * @param inputForm リフォーム情報フォーム
	 * @param basePath baseパス
	 * @throws IOException
	 * @throws Exception 委譲先がスローする任意の例外
	 */
	public void createImgFile(ReformInfoForm inputForm, String basePath)
			throws IOException, Exception {

		if (!StringUtils.isEmpty(inputForm.getImgName())) {
			String srcRoot = this.commonParameters.getHousImgTempPhysicalPath();
			if (!srcRoot.endsWith("/")) srcRoot += "/";
			srcRoot += PanaFileUtil.getUploadTempPath() + "/";

			// サムネイル作成用 MAP の作成
			Map<String, String> thumbnailMap = new HashMap<>();
			thumbnailMap.put(
					PanaFileUtil.conPhysicalPath(srcRoot,
							inputForm.getImgName()), basePath);

			// 作成するファイル分繰り返す
			for (Entry<String, String> e : thumbnailMap.entrySet()){

				// サムネイル作成元のファイルオブジェクトを作成する。
				File srcFile = new File(e.getKey());

				// サムネイル出力先のルートパス （画像サイズの直前までのフォルダ階層）
				String destRootPath = e.getValue();
				if (!destRootPath.endsWith("/")) destRootPath += "/";

				// オリジナル画像をフルサイズ画像として copy する。
				FileUtils.copyFileToDirectory(srcFile, new File(destRootPath));

			}
		}
	}


	/**
	 * リフォーム情報削除時のフィルター処理<br/>
	 * リフォーム情報を削除する前に、削除対象となるリフォーム画像情報、リフォーム詳細情報を取得する。<br/>
	 * 委譲先のリフォーム model を使用してリフォーム情報の削除を行い、<br/>
	 * 該当するリフォーム画像ファイルとリフォーム詳細ファイル（PDF）を削除する。<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            システムリフォームCD
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void delReformPlan(String sysHousingCd, String sysReformCd,
			String userId) throws Exception {
		// リフォーム情報
		Map<String, Object> reform = this.reformManager
				.searchReform(sysReformCd);
		// リフォームプラン情報
	    ReformPlan reformPlan = (ReformPlan) reform.get("reformPlan");
		// リフォーム詳細情報
		List<ReformDtl> dtlList = (List<ReformDtl>) reform.get("dtlList");
		// リフォーム画像情報
		List<ReformImg> imgList = (List<ReformImg>) reform.get("imgList");

		Map<String, String> delFiles = new HashMap<String, String>();

		// レーダーチャート画像を削除
	    String rootPath = ""; // /「定数値」
	    if (reformPlan != null) {
	    	rootPath = PanaFileUtil.conPhysicalPath(
					this.commonParameters.getHousImgOpenPhysicalMemberPath(), reformPlan.getReformChartImagePathName());

			delFiles.put(reformPlan.getReformChartImageFileName(), rootPath);
	    }

		// リフォーム詳細情報（PDFファイル）を削除
		for (ReformDtl dtl : dtlList) {
			rootPath = ""; // /「定数値」
			// 閲覧権限が会員のみの場合
			if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(dtl.getRoleId())) {
				rootPath = this.commonParameters
						.getHousImgOpenPhysicalMemberPath();
			} else {
				// 閲覧権限が全員の場合
				rootPath = this.commonParameters.getHousImgOpenPhysicalPath();
			}
			// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システムリフォーム番号/
			rootPath = PanaFileUtil
					.conPhysicalPath(rootPath, dtl.getPathName());
			delFiles.put(dtl.getFileName(), rootPath);
		}

		// リフォーム画像ファイルを削除
		for (ReformImg img : imgList) {
			rootPath = ""; // /「定数値」
			// 閲覧権限が会員のみの場合
			if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(img.getRoleId())) {
				rootPath = this.commonParameters
						.getHousImgOpenPhysicalMemberPath();
			} else {
				// 閲覧権限が全員の場合
				rootPath = this.commonParameters.getHousImgOpenPhysicalPath();
			}
			// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システム物件番号/
			// After画像ファイルパス
			String afterPath = PanaFileUtil.conPhysicalPath(rootPath,
					img.getAfterPathName());
			// Before画像ファイルパス
			String beforePath = PanaFileUtil.conPhysicalPath(rootPath,
					img.getBeforePathName());

			delFiles.put(img.getAfterFileName(), afterPath);
			delFiles.put(img.getBeforeFileName(), beforePath);
		}

		// 委譲先の model を実行してリフォーム情報を削除する。
		this.reformManager.delReformPlan(sysHousingCd, sysReformCd, userId);
		
		createPartInfo(sysHousingCd, "delReform");

		for (String keyFileName : delFiles.keySet()) {
			if(keyFileName!= null){
				PanaFileUtil.delPhysicalPathFile(delFiles.get(keyFileName),
						keyFileName);
			}
		}
	}

	/**
	 * パラメータで渡された リフォーム詳細情報を新規追加する。<br/>
	 * 枝番 は自動採番されるので、ReformDtl の divNo プロパティには値を設定しない事。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            リフォーム詳細情報フォーム
	 *
	 * @return 新たに追加したリフォーム詳細情報のリスト
	 * @throws Exception
	 */
	@Override
	public List<ReformDtl> addReformDtl(ReformDtlForm inputForm)
			throws Exception {
		// 委譲先の処理を実行する。
		// このメソッドの戻り値は、新規追加したリフォーム詳細情報のリストが復帰される。
		List<ReformDtl> dtlList = this.reformManager.addReformDtl(inputForm);

		// サムネイルの作成元フォルダ名　（日付を指定したフォルダ階層まで）
		String srcRoot = this.commonParameters.getHousImgTempPhysicalPath();
		if (!srcRoot.endsWith("/")) srcRoot += "/";
		srcRoot += PanaFileUtil.getUploadTempPath() + "/";

		for(int i=0;i<dtlList.size();i++){
			// Tempフォルダーから公開フォルダーへ移動
			String basePath = "";
			// 閲覧権限が会員のみの場合
			if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(dtlList.get(i).getRoleId())) {
				basePath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
			} else {
				// 閲覧権限が全員の場合
				basePath = this.commonParameters.getHousImgOpenPhysicalPath();
			}

			// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システムリフォーム番号/
			String afterPath = PanaFileUtil.conPhysicalPath(basePath,
					dtlList.get(i).getPathName());

			// サムネイル作成用 MAP の作成
			Map<String, String> thumbnailMap = new HashMap<>();
			thumbnailMap.put(
					PanaFileUtil.conPhysicalPath(srcRoot,
							dtlList.get(i).getFileName()), afterPath);

			// テストメソッド実行
			this.create(thumbnailMap);
		}

		return dtlList;
	}

	/**
	 * リフォーム詳細情報更新時のフィルター処理<br/>
	 * ※リフォーム詳細ファイルの閲覧権限による入れ替えは発生する。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            リフォーム詳細情報の入力フォーム
	 *
	 * @return 削除が発生した リフォーム詳細情報
	 */
	@Override
	public List<ReformDtl> updateReformDtl(ReformDtlForm inputForm)
			throws Exception {
		// 委譲先の処理を実行する。
		// 削除された リフォーム詳細情報のリストが戻される。
		List<ReformDtl> dtlList = this.reformManager.updateReformDtl(inputForm);


		// null の場合、削除画像が無かったとして null を復帰する。
		if (dtlList == null)
			return null;

		for (int idx = 0; idx < inputForm.getDivNo().length; idx++) {
			if (!StringUtils.isEmpty(inputForm.getDivNo()[idx])) {
				String srcPath = "";
				// 閲覧権限が会員のみの場合
				if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(inputForm.getOldRoleId()[idx])) {
					srcPath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
				} else {
					// 閲覧権限が全員の場合
					srcPath = this.commonParameters.getHousImgOpenPhysicalPath();
				}

				// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システムリフォーム番号/
				String afterSrcPath = PanaFileUtil.conPhysicalPath(srcPath,
						inputForm.getPathName()[idx]);

				// 削除処理の場合
				if ("1".equals(inputForm.getDelFlg()[idx])) {
					// 移動後、移動元のイメージファイルとそのサムネイルを削除
					this.deleteImgFile(afterSrcPath,
							inputForm.getUpdHidFileName()[idx]);
				} else {
					// 更新処理の場合
					// 閲覧権限の設定が変更された場合
					if (!inputForm.getOldRoleId()[idx].equals(inputForm
							.getRoleId()[idx])) {
						String uploadPath = "";
						// 閲覧権限が会員のみの場合
						if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(inputForm.getRoleId()[idx])) {
							uploadPath = this.commonParameters
									.getHousImgOpenPhysicalMemberPath();
						} else {
							// 閲覧権限が全員の場合
							uploadPath = this.commonParameters
									.getHousImgOpenPhysicalPath();
						}

						// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システムリフォーム番号/
						String afterUploadPath = PanaFileUtil.conPhysicalPath(
								uploadPath,
								inputForm.getPathName()[idx]);

						// サムネイル作成用 MAP の作成
						Map<String, String> thumbnailMap = new HashMap<>();
						thumbnailMap.put(PanaFileUtil.conPhysicalPath(
								afterSrcPath,
								this.commonParameters.getAdminSitePdfFolder()+"/"+ inputForm.getUpdHidFileName()[idx]),
								afterUploadPath);

						// テストメソッド実行
						this.create(thumbnailMap);

						// 移動後、移動元のイメージファイルとそのサムネイルを削除
						this.deleteImgFile(afterSrcPath,
								inputForm.getUpdHidFileName()[idx]);
					}
				}
			}
		}

		return dtlList;
	}

	/**
	 * リフォーム詳細削除時のフィルター処理<br/>
	 * ※updateReformDtl() からでも削除可。　個別削除用機能。<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            システムリフォームCd
	 * @param divNo
	 *            枝番
	 * @return 削除した リフォーム詳細情報
	 */
	@Override
	public ReformDtl delReformDtl(String sysReformCd, int divNo) {
		// 委譲先の処理を実行してリフォーム詳細情報を削除する。
		ReformDtl reformDtl = this.reformManager.delReformDtl(sysReformCd,
				divNo);

		// ファイルの削除処理
		String srcPath = "";
		// 閲覧権限が会員のみの場合
		if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(reformDtl.getRoleId())) {
			srcPath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
		} else {
			// 閲覧権限が全員の場合
			srcPath = this.commonParameters.getHousImgOpenPhysicalPath();
		}
		// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システムリフォーム番号/
		srcPath = PanaFileUtil
				.conPhysicalPath(srcPath, reformDtl.getPathName());

		// PDFファイルを削除
		PanaFileUtil.delPhysicalPathFile(srcPath, reformDtl.getFileName());

		return reformDtl;
	}

	/**
	 * サムネイル画像を作成する。<br/>
	 * また、オリジナルサイズの画像を、画像サイズの階層が full のフォルダへ配置する。<br/>
	 * thumbnailMap の構造は下記の通り。　ファイル名は元のファイル名が使用される。<br/>
	 * <ul>
	 * <li>Key : サムネイル作成元のファイル名（フルパス）</li>
	 * <li>value : サムネイルの出力先パス（ルート～システム物件番号までのパス。　サイズや、ファイル名は含まない。）</li>
	 * </ul>
	 * @param thumbnailMap 作成するファイルの情報
	 *
	 * @throws IOException
	 * @throws Exception 委譲先がスローする任意の例外
	 */
	public void create(Map<String, String> thumbnailMap)
			throws IOException, Exception {

		// 作成するファイル分繰り返す
		for (Entry<String, String> e : thumbnailMap.entrySet()){

			// サムネイル作成元のファイルオブジェクトを作成する。
			File srcFile = new File(e.getKey());

			// サムネイル出力先のルートパス （画像サイズの直前までのフォルダ階層）
			String destRootPath = e.getValue();
			if (!destRootPath.endsWith("/")) destRootPath += "/";

			// オリジナル画像をフルサイズ画像として copy する。
			FileUtils.copyFileToDirectory(srcFile, new File(destRootPath + this.commonParameters.getAdminSitePdfFolder()));

		}
	}
	/**
	 * 物件画像ファイルを個別に削除する。<br/>
	 * filePath で指定したフォルダ内のファイルが空の場合、フォルダごと削除する。
	 * <br/>
	 * @param filePath ルート～システム物件CD までのパス（画像サイズの下までのパス）
	 * @param fileName　画像ファイル名
	 *
	 * @throws IOException
	 */
	public void deleteImgFile(String filePath, String fileName) throws IOException{

		// オリジナル画像の削除
		if(!StringValidateUtil.isEmpty(fileName)){
			(new File(filePath + this.commonParameters.getAdminSitePdfFolder() + "/" + fileName)).delete();
		}
	}

	/**
	 * パラメータで渡された リフォーム画像情報を新規追加する。<br/>
	 * 枝番 は自動採番されるので、ReformDtl の divNo プロパティには値を設定しない事。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            リフォーム画像情報フォーム
	 *
	 * @return 新たに追加したリフォーム画像情報のリスト
	 * @throws Exception
	 */
	@Override
	public ReformImg addReformImg(ReformImgForm inputForm) throws Exception {
		// 委譲先の処理を実行する。
		// このメソッドの戻り値は、新規追加した画像情報のリストが復帰される。
		ReformImg reformImg = this.reformManager.addReformImg(inputForm);

		// サムネイルの作成元フォルダ名　（日付を指定したフォルダ階層まで）
		String srcRoot = this.commonParameters.getHousImgTempPhysicalPath();
		if (!srcRoot.endsWith("/")) srcRoot += "/";
		srcRoot += PanaFileUtil.getUploadTempPath() + "/";

		// Tempフォルダーから公開フォルダーへ移動
		String basePath = "";
		// 閲覧権限が会員のみの場合
		if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(reformImg.getRoleId())) {
			basePath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
		} else {
			// 閲覧権限が全員の場合
			basePath = this.commonParameters.getHousImgOpenPhysicalPath();
		}

		// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システムリフォーム番号/
		String afterPath = PanaFileUtil.conPhysicalPath(basePath,
				reformImg.getAfterPathName());
		// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システムリフォーム番号/
		String beforePath = PanaFileUtil.conPhysicalPath(basePath,
				reformImg.getBeforePathName());

		// サムネイル作成用 MAP の作成
		Map<String, String> thumbnailMap = new HashMap<>();
		thumbnailMap.put(
				PanaFileUtil.conPhysicalPath(srcRoot,
						reformImg.getAfterFileName()), afterPath);
		thumbnailMap.put(
				PanaFileUtil.conPhysicalPath(srcRoot,
						reformImg.getBeforeFileName()), beforePath);

		// テストメソッド実行
		this.thumbnailCreator.create(thumbnailMap);

		return reformImg;
	}

	/**
	 * リフォーム画像情報更新時のフィルター処理<br/>
	 * ※リフォーム画像ファイルの閲覧権限による入れ替えは発生する。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            リフォーム画像情報の入力フォーム
	 *
	 * @return 削除が発生した リフォーム画像情報
	 */
	@Override
	public List<ReformImg> updateReformImg(ReformImgForm inputForm)
			throws Exception {
		// 委譲先の処理を実行する。
		// 削除されたリフォーム画像情報のリストが戻される。
		List<ReformImg> imgList = this.reformManager.updateReformImg(inputForm);

		// null の場合、削除画像が無かったとして null を復帰する。
		if (imgList == null)
			return null;

		for (int idx = 0; idx < inputForm.getDivNo().length; idx++) {
			if (!StringUtils.isEmpty(inputForm.getDivNo()[idx])) {
				String srcPath = "";
				// 閲覧権限が会員のみの場合
				if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(inputForm.getEditOldRoleId()[idx])) {
					srcPath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
				} else {
					// 閲覧権限が全員の場合
					srcPath = this.commonParameters.getHousImgOpenPhysicalPath();
				}

				// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システムリフォーム番号/
				String afterSrcPath = PanaFileUtil.conPhysicalPath(srcPath,
						inputForm.getEditAfterPathName()[idx]);
				// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システムリフォーム番号/
				String beforeSrcPath = PanaFileUtil.conPhysicalPath(srcPath,
						inputForm.getEditBeforePathName()[idx]);

				// 削除処理の場合
				if ("1".equals(inputForm.getDelFlg()[idx])) {

					this.thumbnailCreator.deleteImgFile(afterSrcPath,
							inputForm.getEditAfterFileName()[idx]);
					// 移動後、移動元のイメージファイルとそのサムネイルを削除
					this.thumbnailCreator.deleteImgFile(beforeSrcPath,
							inputForm.getEditBeforeFileName()[idx]);
				} else {
					// 更新処理の場合
					// 閲覧権限の設定が変更された場合
					if (!inputForm.getEditOldRoleId()[idx].equals(inputForm
							.getEditRoleId()[idx])) {
						String uploadPath = "";
						// 閲覧権限が会員のみの場合
						if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(inputForm.getEditRoleId()[idx])) {
							uploadPath = this.commonParameters
									.getHousImgOpenPhysicalMemberPath();
						} else {
							// 閲覧権限が全員の場合
							uploadPath = this.commonParameters
									.getHousImgOpenPhysicalPath();
						}

						// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システムリフォーム番号/
						String afterUploadPath = PanaFileUtil.conPhysicalPath(
								uploadPath,
								inputForm.getEditAfterPathName()[idx]);
						String beforeUploadPath = PanaFileUtil.conPhysicalPath(
								uploadPath,
								inputForm.getEditBeforePathName()[idx]);

						// サムネイル作成用 MAP の作成
						Map<String, String> thumbnailMap = new HashMap<>();
						thumbnailMap.put(PanaFileUtil.conPhysicalPath(
								afterSrcPath,
								this.commonParameters.getAdminSiteFullFolder()+"/"+ inputForm.getEditAfterFileName()[idx]),
								afterUploadPath);
						thumbnailMap.put(PanaFileUtil.conPhysicalPath(
								beforeSrcPath,
								this.commonParameters.getAdminSiteFullFolder()+"/"+ inputForm.getEditBeforeFileName()[idx]),
								beforeUploadPath);

						// テストメソッド実行
						this.thumbnailCreator.create(thumbnailMap);

						this.thumbnailCreator.deleteImgFile(afterSrcPath,
								inputForm.getEditAfterFileName()[idx]);
						// 移動後、移動元のイメージファイルとそのサムネイルを削除
						this.thumbnailCreator.deleteImgFile(beforeSrcPath,
								inputForm.getEditBeforeFileName()[idx]);
					}
				}
			}
		}

		return imgList;
	}

	/**
	 * リフォーム画像削除時のフィルター処理<br/>
	 * ※updateReformImg() からでも削除可。　個別削除用機能。<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            システムリフォームCd
	 * @param divNo
	 *            枝番
	 * @return 削除した リフォーム画像情報
	 * @throws IOException
	 */
	@Override
	public ReformImg delReformImg(String sysReformCd, int divNo)
			throws IOException {
		// 委譲先の処理を実行してリフォーム画像情報を削除する。
		ReformImg reformImg = this.reformManager.delReformImg(sysReformCd,
				divNo);

		// ファイルの削除処理
		String filePath = "";
		// 閲覧権限が会員のみの場合
		if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(reformImg.getRoleId())) {
			filePath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
		} else {
			// 閲覧権限が全員の場合
			filePath = this.commonParameters.getHousImgOpenPhysicalPath();
		}
		// /「定数値」/[リフォーム種別CD]/[都道府県CD]/[市区町村CD]/システムリフォーム番号/
		String afterSrcPath = PanaFileUtil.conPhysicalPath(filePath,
				reformImg.getAfterPathName());
		String beforeSrcPath = PanaFileUtil.conPhysicalPath(filePath,
				reformImg.getBeforePathName());

		this.thumbnailCreator.deleteImgFile(afterSrcPath,
				reformImg.getAfterPathName());
		// 移動後、移動元のイメージファイルとそのサムネイルを削除
		this.thumbnailCreator.deleteImgFile(beforeSrcPath,
				reformImg.getBeforePathName());

		return reformImg;
	}

	/**
	 * リフォーム関連情報（ReformPlan, ReformChart, ReformDtl, ReformImg)を検索し、結果Mapを復帰する。<br/>
	 * 引数で渡された sysReformCd パラメータの値で検索条件を生成し、リフォーム情報を検索する。<br/>
	 * <br/>
	 * 検索条件として、以下のデータを検索対象とする。<br/>
	 *
	 * @param sysReformCd
	 *            システムリフォームCD
	 *
	 * @return リフォームプラン情報のMap
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	@Override
	public Map<String, Object> searchReform(String sysReformCd) {
		// 委譲先の処理を実行してリフォーム画像情報を削除する。
		return this.reformManager.searchReform(sysReformCd);
	}

	/**
	 * パラメータで渡された リフォーム・レーダーチャート詳細情報を更新（update後insert)する。<br/>
	 * <br/>
	 *
	 * @param reformChart
	 *            リフォーム・レーダーチャート詳細情報
	 *
	 */
	@Override
	public void updReformChart(ReformInfoForm form, int count) {
		// 委譲先の処理を実行してリフォーム画像情報を削除する。
		this.reformManager.updReformChart(form, count);

	}

	/**
	 * パラメータで渡された リフォーム・レーダーチャート詳細情報を新規追加する。<br/>
	 * <br/>
	 *
	 * @param reformChart
	 *            リフォーム・レーダーチャート詳細情報
	 *
	 */
	@Override
	public void addReformChart(ReformInfoForm form, int count) {
		// 委譲先の処理を実行してリフォーム画像情報を削除する。
		this.reformManager.addReformChart(form, count);

	}

	/**
	 * パラメータで渡された システムリフォームCDでリフォーム・レーダーチャート情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            システムリフォームCD
	 *
	 */
	@Override
	public void delReformChart(String sysReformCd) {
		// 委譲先の処理を実行してリフォーム画像情報を削除する。
		this.reformManager.delReformChart(sysReformCd);

	}

	/**
	 * リフォーム画像情報の枝番を採番する処理。<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            システムリフォームCD
	 *
	 */
	@Override
	public int getReformImgDivNo(String sysReformCd) {
		// 委譲先の処理を実行してリフォーム画像情報を削除する。
		return this.reformManager.getReformImgDivNo(sysReformCd);
	}

	/**
	 * リフォーム詳細情報の枝番を採番する処理。<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            システムリフォームCD
	 *
	 */
	@Override
	public int getReformDtlDivNo(String sysReformCd) {
		// 委譲先の処理を実行してリフォーム画像情報を削除する。
		return this.reformManager.getReformDtlDivNo(sysReformCd);
	}

	/**
	 * パラメータ システム物件CD をキーに、物件基本情報を検索する。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム物件CD
	 *
	 * @return 物件基本情報
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	@Override
	public HousingInfo searchHousingInfo(String sysHousingCd) throws Exception {
		// 委譲先の処理を実行してリフォーム画像情報を削除する。
		return this.reformManager.searchHousingInfo(sysHousingCd);
	}

	/**
	 * パラメータ システム建物CD をキーに、物件基本情報を検索する。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム建物CD
	 *
	 * @return 建物基本情報
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	@Override
	public BuildingInfo searchBuildingInfo(String sysHousingCd)
			throws Exception {
		// 委譲先の処理を実行してリフォーム画像情報を削除する。
		return this.reformManager.searchBuildingInfo(sysHousingCd);
	}

	/**
	 * リフォーム詳細情報を検索し、結果を復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、リフォーム詳細情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当レコードを戻り値として復帰する。<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            システムリフォームCD
	 * @param divNo
	 *            枝番
	 *
	 * @return 検索条件に該当するリフォーム詳細情報
	 *
	 */
	@Override
	public ReformDtl searchReformDtlByPk(String sysReformCd, String divNo)
			throws Exception {
		// 委譲先の処理を実行してリフォーム画像情報を削除する。
		return this.reformManager.searchReformDtlByPk(sysReformCd, divNo);
	}

	/**
	 * リフォームプラン情報を検索し、結果を復帰する。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム物件CD
	 *
	 * @return 検索条件に該当するリフォームプラン情報
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	@Override
	public List<ReformPlan> searchReformPlan(String sysHousingCd)
			throws Exception {
		// 委譲先の処理を実行してリフォーム画像情報を削除する。
		return this.reformManager.searchReformPlan(sysHousingCd);
	}

	/**
	 * リフォーム画像ファイル名を取得するメソッド.<br>
	 * <br>
	 *
	 * @return リフォーム画像ファイル名
	 * @throws Exception
	 */
	public String getReformJpgFileName() throws Exception {
		return getSequenceFileName("jpg");
	}

	/**
	 * リフォームPDFファイル名を取得するメソッド.<br>
	 * <br>
	 *
	 * @return リフォームPDFファイル名
	 * @throws Exception
	 */
	public String getReformPdfFileName() throws Exception {
		return getSequenceFileName("pdf");
	}

	/**
	 * リフォーム画像/PDFファイル名をシーケンスから取得して復帰する。<br/>
	 * <br/>
	 *
	 * @return 画像/PDFファイル名
	 */
	public String getSequenceFileName(String extension) throws Exception {
		String fileName = this.thumbnailCreator.getFIleName();

		if (!StringUtils.isEmpty(fileName)) {
			fileName = fileName + "." + extension;
		}

		return fileName;
	}

	/**
	 * 物件基本情報のタイムスタンプ情報を更新する。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            更新対象システム物件CD
	 * @param editUserId
	 *            更新者ID
	 * @throws Exception
	 */
	public void updateEditTimestamp(String sysHousingCd, String sysReformCd,
			String editUserId) throws Exception {
		this.reformManager.updateEditTimestamp(sysHousingCd, sysReformCd,
				editUserId);
	}

    /**
     * リフォームプラン情報を検索し、結果を復帰する。<br/>
     * <br/>
     * @param sysHousingCd システム物件CD
     *
     * @return 検索条件に該当するリフォームプラン情報
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
	@Override
	public List<ReformPlan> searchReformPlan(String sysHousingCd, boolean full) throws Exception {
		return this.reformManager.searchReformPlan(sysHousingCd, full);
	}

	/**
	 * サムネイル画像を作成する。<br/>
	 * また、オリジナルサイズの画像を、画像サイズの階層が full のフォルダへ配置する。<br/>
	 * thumbnailMap の構造は下記の通り。　ファイル名は元のファイル名が使用される。<br/>
	 * <ul>
	 * <li>Key : サムネイル作成元のファイル名（フルパス）</li>
	 * <li>value : サムネイルの出力先パス（ルート～システム物件番号までのパス。　サイズや、ファイル名は含まない。）</li>
	 * </ul>
	 * @param thumbnailMap 作成するファイルの情報
	 *
	 * @throws Exception 委譲先がスローする任意の例外
	 */
	public void addTempFile(FileItem fileItem, String temPath, String fileName) throws Exception{

		String tempUploadPath=PanaFileUtil.conPhysicalPath(this.commonParameters.getHousImgTempPhysicalPath(), temPath+"/");
		PanaFileUtil.uploadFile(fileItem,tempUploadPath, fileName);

		// サムネイル作成元のファイルオブジェクトを作成する。
		File srcFile = new File(tempUploadPath + fileName);

		// サムネイル出力先のルートパス （画像サイズの直前までのフォルダ階層）
		String destRootPath = tempUploadPath;
		if (!destRootPath.endsWith("/")) destRootPath += "/";

		// サイズリストが未設定の場合はサムネイル画像を作成しない。
		if (this.commonParameters.getThumbnailSizes() == null) return;

		// 作成するサムネイルサイズ分繰り返す
		for (Integer size : this.commonParameters.getThumbnailSizes()){

			// 出力先サブフォルダが存在しない場合、フォルダを作成する。
			// createImgFile() は、サブフォルダを作成しないので..。
			File subDir = new File(destRootPath + size.toString());
			if (!subDir.exists()){
				FileUtils.forceMkdir(subDir);
			}

			// サムネイルの出力先はファイルサイズ毎に異なるので、サイズ毎に生成する。
			File destFile = new File(destRootPath + size.toString() + "/" + srcFile.getName());
			// サムネイル画像を作成
			this.imgUtils.createImgFile(srcFile, destFile, size.intValue());
		}
	}

	@Override
	public Housing searchHousingByPk(String sysHousingCd) throws Exception {
		return this.reformManager.searchHousingByPk(sysHousingCd);
	}
}
