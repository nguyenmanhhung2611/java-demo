package jp.co.transcosmos.dm3.corePana.model.housing;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.HousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingImgForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInspectionForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingStatusForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

public class PanaHousingPartThumbnailProxy extends HousingPartThumbnailProxy {

	/** リフォーム情報用 Model の proxy クラス */
	protected ReformPartThumbnailProxy reformManage;

	/** こだわり条件マスタDAO */
	private DAO<JoinResult> partSrchMstListDAO;
	/** 物件画像情報用　DAO */
	protected DAO<HousingImageInfo> panaHousingImageInfoDAO;
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
	 * こだわり条件マスタ用 Model の proxy クラスを設定する。<br/>
	 * <br/>
	 *
	 * @param partSrchMstDAO
	 *            こだわり条件マスタ用 Model の proxy クラス
	 */
	public void setPartSrchMstListDAO(DAO<JoinResult> partSrchMstListDAO) {
		this.partSrchMstListDAO = partSrchMstListDAO;
	}

	/**
	 * リフォーム情報用 Model の proxy クラスを設定する。<br/>
	 * <br/>
	 *
	 * @param reformManage
	 *            リフォーム情報用 Model の proxy クラス
	 */
	public void setReformManage(ReformPartThumbnailProxy reformManage) {
		this.reformManage = reformManage;
	}

	/**
	 * 物件画像情報用　DAOを設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingImageInfoDAO
	 *            物件画像情報用　DAO
	 */
	public void setPanaHousingImageInfoDAO(DAO<HousingImageInfo> panaHousingImageInfoDAO) {
		this.panaHousingImageInfoDAO = panaHousingImageInfoDAO;
	}

	/**
	 * 物件基本情報新規登録時のフィルター処理<br/>
	 * 委譲先の物件 model を使用して物件基本情報を登録後、こだわり条件を再構築する。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            物件基本情報の入力フォーム
	 * @param editUserId
	 *            更新者ID
	 *
	 * @return 採番されたシステム物件CD
	 */
	@Override
	public String addHousing(HousingForm inputForm, String editUserId)
			throws Exception {

		return super.addHousing(inputForm, editUserId);
	}

	/**
	 * 物件基本情報更新時のフィルター処理<br/>
	 * 委譲先の物件 model を使用して物件基本情報を更新後、こだわり条件を再構築する。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            物件基本情報の入力フォーム
	 * @param editUserId
	 *            更新者ID
	 */
	@Override
	public void updateHousing(HousingForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		super.updateHousing(inputForm, editUserId);
	}

	/**
	 * 物件情報削除時のフィルター処理<br/>
	 * 物件情報を削除する前に、削除対象となる物件画像情報を取得し、パス名を集約する。<br/>
	 * 委譲先の物件 model を使用して物件情報の削除を行い、該当する公開物件画像ファイルを削除する。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            物件基本情報の入力フォーム
	 * @param editUserId
	 *            更新者ID
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delHousingInfo(HousingForm inputForm, String editUserId)
			throws Exception {
		Set<String> delPath = new HashSet<>();
		PanaHousing housing = (PanaHousing)this.housingManage.searchHousingPk(inputForm.getSysHousingCd(), true);
		if (housing != null) {
			// リフォームプラン情報を検索し、結果を復帰する
			List<Map<String, Object>>  reformList = housing.getReforms();
			for (Map<String, Object> reforms : reformList) {
				// リフォームプラン情報
			    ReformPlan reformPlan = (ReformPlan) reforms.get("reformPlan");
				// リフォーム詳細情報
			    List<ReformDtl> dtlList = (List<ReformDtl>) reforms.get("dtlList");
			    // リフォーム画像情報
			    List<ReformImg> imgList = (List<ReformImg>) reforms.get("imgList");
			    // レーダーチャート画像を削除
			    String rootPath = ""; // /「定数値」
			    if (reformPlan != null) {
			    	if(!StringValidateUtil.isEmpty(reformPlan.getReformChartImageFileName())){
				    	rootPath = PanaFileUtil.conPhysicalPath(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath(), reformPlan.getReformChartImagePathName());
				    	if (!delPath.contains(rootPath)) {
				    		delPath.add(rootPath);
				    	}
			    	}
			    }

			    // リフォーム詳細情報（PDFファイル）を削除
			    for (ReformDtl dtl : dtlList) {
			    	rootPath = ""; // /「定数値」
			        // 閲覧権限が会員のみの場合
			        if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(dtl.getRoleId())) {
			           rootPath = ((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath();
			        } else {
			           // 閲覧権限が全員の場合
			        	rootPath = ((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath();
			        }
			        if(!StringValidateUtil.isEmpty(dtl.getFileName())){
				        // /「定数値」/[物件種別CD]/[都道府県CD]/[市区町村CD]/システム物件CD/
				        rootPath = PanaFileUtil.conPhysicalPath(rootPath, dtl.getPathName());

				        if (!delPath.contains(rootPath)) {
				        	delPath.add(rootPath);
				        }
			        }
			    }

			    // リフォーム画像ファイルを削除
			    for (ReformImg img : imgList) {
			    	rootPath = ""; // /「定数値」
			        // 閲覧権限が会員のみの場合
			        if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(img.getRoleId())) {
			        	rootPath = ((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath();
			        } else {
			        	// 閲覧権限が全員の場合
			        	rootPath = ((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath();
			        }
			        // /「定数値」/[物件種別CD]/[都道府県CD]/[市区町村CD]/システム物件CD/
			        // After画像ファイルパス
			        String afterPath = PanaFileUtil.conPhysicalPath(rootPath, img.getAfterPathName());
			        // Before画像ファイルパス
			        String beforePath = PanaFileUtil.conPhysicalPath(rootPath, img.getBeforePathName());

			        if (!delPath.contains(afterPath)) {
			                delPath.add(afterPath);
			        }
			        if (!delPath.contains(beforePath)) {
			           delPath.add(beforePath);
			        }
			    }
			}

		    // 物件画像情報
		    DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());
			List<HousingImageInfo> delImgList = this.panaHousingImageInfoDAO.selectByFilter(criteria);

		    // 物件拡張属性情報
		    Map<String, Map<String,String>> housingExtInfos = housing.getHousingExtInfos();

		    // 物件画像情報画像ファイルを削除
		    for (HousingImageInfo img : delImgList) {
		    	String rootPath = ""; ///「定数値」
		        // 閲覧権限が会員のみの場合
		        if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(img.getRoleId())) {
		        	rootPath = ((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath();
		        } else {
		        	// 閲覧権限が全員の場合
		        	rootPath = ((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath();
		        }
		        // /「定数値」/[物件種別CD]/[都道府県CD]/[市区町村CD]/システム物件CD/
		        rootPath = PanaFileUtil.conPhysicalPath(rootPath, img.getPathName());

		        if (!delPath.contains(rootPath)) {
		                delPath.add(rootPath);
		        }
		    }

		    // 物件拡張属性情報画像ファイルを削除
		    if (housingExtInfos != null) {
		    	String rootPath = "";
		    	// 住宅診断ファイル
		    	if(housingExtInfos.get("housingInspection") != null){
		    		Map<String,String> housingInspection = housingExtInfos.get("housingInspection");

// 2015.06.26 H.Mizuno 画像ルートから削除される問題を修正 start
//		    		rootPath = PanaFileUtil.conPhysicalPath(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath(), housingInspection.get("inspectionPathName"));
//		    		if (!delPath.contains(rootPath)) {
//		                delPath.add(rootPath);
//		    		}

		    		// housingInspection の Key が存在するが、そのカテゴリ内に inspectionPathName が存在しないケースがあると、
		    		// 画像ルートから削除される危険性がある。　inspectionPathName　が取得できた場合のみ削除するように変更。
		    		String inspectionPathName = housingInspection.get("inspectionPathName");
		    		if (!StringValidateUtil.isEmpty(inspectionPathName)){
		    			rootPath = PanaFileUtil.conPhysicalPath(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath(), inspectionPathName);
		    			if (!delPath.contains(rootPath)) {
		    				delPath.add(rootPath);
		    			}
		    		}
// 2015.06.26 H.Mizuno 画像ルートから削除される問題を修正 end
		    		String inspectionImagePathName = housingInspection.get("inspectionImagePathName");
		    		if (!StringValidateUtil.isEmpty(inspectionImagePathName)){
		    			rootPath = PanaFileUtil.conPhysicalPath(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath(), inspectionImagePathName);
		    			if (!delPath.contains(rootPath)) {
		    				delPath.add(rootPath);
		    			}
		    		}
		    	}
		    	// 担当者写真
		    	if(housingExtInfos.get("housingDetail") != null){
		    		Map<String,String> housingDetail = housingExtInfos.get("housingDetail");

// 2015.06.26 H.Mizuno 画像ルートから削除される問題を修正 start
//		    		rootPath = PanaFileUtil.conPhysicalPath(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath(), housingDetail.get("staffImagePathName"));
//		    		if (!delPath.contains(rootPath)) {
//		                delPath.add(rootPath);
//		    		}

		    		// housingDetail の Key が存在するが、そのカテゴリ内に staffImagePathName が存在しないケースがあると、
		    		// 画像ルートから削除される危険性がある。　staffImagePathName　が取得できた場合のみ削除するように変更。
		    		String staffImagePathName = housingDetail.get("staffImagePathName");
		    		if (!StringValidateUtil.isEmpty(staffImagePathName)){
		    			rootPath = PanaFileUtil.conPhysicalPath(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath(), staffImagePathName);
		    			if (!delPath.contains(rootPath)) {
		    				delPath.add(rootPath);
		    			}
		    		}
// 2015.06.26 H.Mizuno 画像ルートから削除される問題を修正 start
		    	}
		    }

		    // 委譲先の model を実行して物件情報を削除する。
		    HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		    if (housingInfo != null) {
		    	inputForm.setSysBuildingCd(housingInfo.getSysBuildingCd());
		    }
		 	this.housingManage.delHousingInfo(inputForm, editUserId);

		 	for (String path : delPath){
				if (StringValidateUtil.isEmpty(path) || path.equals("/")){
					continue;
				}
// 2015.06.26 H.Mizuno 画像ルートから削除される問題を修正 start
				// 削除対象が画像公開ルートパスだった場合、削除しない様にチェックを追加
				if (path.equals(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath()) ||
					path.equals(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath())){
					continue;
				}
// 2015.06.26 H.Mizuno 画像ルートから削除される問題を修正 end

				// 指定されたフォルダ配下を削除
				FileUtils.deleteDirectory(new File(path));
			}
		}
	}

	/**
	 * 物件詳細更新時のフィルター処理<br/>
	 * 委譲先の物件 model を使用して物件詳細情報を追加・更新後、こだわり条件を再構築する。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            物件詳細情報の入力フォーム
	 * @param editUserId
	 *            更新者ID
	 */
	@Override
	public void updateHousingDtl(HousingDtlForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		super.updateHousingDtl(inputForm, editUserId);
	}

	/**
	 * 物件設備更新時のフィルター処理<br/>
	 * 委譲先の物件 model を使用して物件設備情報を更新後、こだわり条件を再構築する。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            物件詳細情報の入力フォーム
	 * @param editUserId
	 *            更新者ID
	 */
	@Override
	public void updateHousingEquip(HousingEquipForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		super.updateHousingEquip(inputForm, editUserId);
	}
	/**
	 * 指定された物件画像情報に該当する公開先となる物件画像の Root パスを取得する。<br/>
	 * <br/>
	 * @return 公開されている物件画像の Root パスリスト
	 */
	@Override
	protected String getImgOpenRootPath(jp.co.transcosmos.dm3.core.vo.HousingImageInfo housingImageInfo){
		// 基本機能では、サムネイルの出力先ルートフォルダは固定値
		String destRoot = "";
		if(PanaCommonConstant.ROLE_ID_PUBLIC.equals(((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfo).getRoleId())){
			destRoot = ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath();
		}
		else if(PanaCommonConstant.ROLE_ID_PRIVATE.equals(((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfo).getRoleId())){
			destRoot = ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath();
		}
		if (!destRoot.endsWith("/")) destRoot += "/";

		return destRoot;
	}
	/**
	 * 物件画像追加時のフィルター処理<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            物件詳細情報の入力フォーム
	 * @param editUserId
	 *            更新者ID
	 *
	 * @return 新たに追加した画像情報のリスト
	 */
	@Override
	public List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> addHousingImg(HousingImgForm inputForm,
			String editUserId) throws Exception, NotFoundException {

		return super.addHousingImg(inputForm, editUserId);

	}

	/**
	 * 物件画像更新時のフィルター処理<br/>
	 * ※画像ファイルの入れ替えは発生しない。　削除のみ。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            物件詳細情報の入力フォーム
	 * @param editUserId
	 *            更新者ID
	 *
	 * @return 削除が発生した画像情報
	 */
	@Override
	public List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> updHousingImg(HousingImgForm inputForm,
			String editUserId) throws Exception, NotFoundException {

		// 委譲先の処理を実行する。
		// 削除された物件画像情報のリストが戻される。
		List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> imgList = this.housingManage.updHousingImg(inputForm, editUserId);

		// こだわり条件を再構築する。
		createPartInfo(inputForm.getSysHousingCd(), "updHousingImg");

		// null の場合、削除画像が無かったとして null を復帰する。
		if (imgList == null)
			return null;

		for (int idx = 0; idx < inputForm.getDivNo().length; idx++) {
			if (!StringUtils.isEmpty(inputForm.getDivNo()[idx])) {

				HousingImageInfo imgInfo = new HousingImageInfo();

				imgInfo.setRoleId(((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm)inputForm).getOldRoleId()[idx]);

				String srcPath = getImgOpenRootPath(imgInfo);
				String filePath = PanaFileUtil.conPhysicalPath(srcPath , ((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm)inputForm).getPathName()[idx]);

				// 削除処理の場合
				if ("1".equals(inputForm.getDelFlg()[idx])) {

					// 移動後、移動元のイメージファイルとそのサムネイルを削除
					this.thumbnailCreator.deleteImgFile(filePath,
							inputForm.getFileName()[idx]);
				} else {
					// 更新処理の場合
					// 閲覧権限の設定が変更された場合
					if (!((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm)inputForm).getOldRoleId()[idx].equals(
							((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm)inputForm).getRoleId()[idx])) {

						imgInfo.setRoleId(((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm)inputForm).getRoleId()[idx]);

						String NewsrcPath = getImgOpenRootPath(imgInfo);
						String NewfilePath = PanaFileUtil.conPhysicalPath(NewsrcPath , ((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm)inputForm).getPathName()[idx]);

						// サムネイル作成用 MAP の作成
						Map<String, String> thumbnailMap = new HashMap<>();
						thumbnailMap.put(PanaFileUtil.conPhysicalPath(
								filePath,
								((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteFullFolder()+"/"+ inputForm.getFileName()[idx]),
								NewfilePath);

						// テストメソッド実行
						this.thumbnailCreator.create(thumbnailMap);

						// 移動後、移動元のイメージファイルとそのサムネイルを削除
						this.thumbnailCreator.deleteImgFile(filePath,
								inputForm.getFileName()[idx]);
					}
				}
			}
		}

		return imgList;
	}

	/**
	 * 物件画像削除時のフィルター処理<br/>
	 * ※updHousingImg() からでも削除可。　個別削除用機能。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            物件詳細情報の入力フォーム
	 * @param editUserId
	 *            更新者ID
	 */
	@Override
	public jp.co.transcosmos.dm3.core.vo.HousingImageInfo delHousingImg(String sysHousingCd,
			String imageType, int divNo, String editUserId) throws Exception {

		return super.delHousingImg(sysHousingCd, imageType, divNo, editUserId);
	}

	/**
	 * 物件検索処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            検索条件フォーム
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm) throws Exception {
		// 物件情報の検索処理では、委譲先をそのまま実行する。
		return super.searchHousing(searchForm);
	}

	/**
	 * 物件検索処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            検索条件フォーム
	 * @param full
	 *            false の場合、公開対象外を除外する。　true の場合は除外しない
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm, boolean full)
			throws Exception {
		// 物件情報の検索処理では、委譲先をそのまま実行する。
		return super.searchHousing(searchForm, full);
	}

	/**
	 * 物件詳細取得処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            取得対象システム物件CD
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
	 *
	 * @param sysHousingCd
	 *            取得対象システム物件CD
	 * @param full
	 *            false の場合、公開対象外を除外する。　true の場合は除外しない
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
	public void updExtInfo(String sysHousingCd,
			Map<String, Map<String, String>> inputData, String editUserId)
			throws Exception, NotFoundException {

		// 物件拡張属性の更新処理では、委譲先の処理をそのまま実行する。
		super.updExtInfo(sysHousingCd, inputData, editUserId);
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
		super.delExtInfo(sysHousingCd, editUserId);
	}

	/**
	 * 物件拡張属性更新のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category,
			Map<String, String> inputData, String editUserId) throws Exception,
			NotFoundException {

		// 物件拡張属性の更新処理では、委譲先の処理をそのまま実行する。
		super.updExtInfo(sysHousingCd, category, inputData, editUserId);
	}

	/**
	 * 物件拡張属性削除のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category,
			String editUserId) throws Exception {

		// 物件拡張属性の削除処理では、委譲先の処理をそのまま実行する。
		super.delExtInfo(sysHousingCd, category, editUserId);
	}

	/**
	 * 物件拡張属性更新のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category, String key,
			String value, String editUserId) throws Exception,
			NotFoundException {

		// 物件拡張属性の更新処理では、委譲先の処理をそのまま実行する。
		super.updExtInfo(sysHousingCd, category, key, value, editUserId);
	}

	/**
	 * 物件拡張属性削除のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category, String key,
			String editUserId) throws Exception {

		// 物件拡張属性の削除処理では、委譲先の処理をそのまま実行する。
		super.delExtInfo(sysHousingCd, category, key, editUserId);
	}

	/**
	 * 物件情報オブジェクトのインスタンス処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 *
	 * @return Housing のインスタンス
	 */
	@Override
	public Housing createHousingInstace() {
		// 物件オブジェクトのインスタンス生成は、委譲先の処理をそのまま実行する。
		return super.createHousingInstace();
	}

	/**
	 * こだわり条件を再作成する。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム物件CD
	 * @param methodName
	 *            実行された物件 model のメソッド名
	 *
	 * @throws Exception
	 */
	protected void createPartInfo(String sysHousingCd, String methodName)
			throws Exception {

		super.createPartInfo(sysHousingCd, methodName);
	}

	/**
	 * ステータスの新規を行う<br/>
	 * <br/>
	 *
	 * @param form
	 *            ステータスの入力値を格納した Form オブジェクト
	 *
	 */
	public String addHousingStatus(PanaHousingStatusForm form)
			throws Exception {

		return ((PanaHousingManageImpl)this.housingManage).addHousingStatus(form);
	}

	/**
	 * ステータスの更新を行う<br/>
	 * <br/>
	 *
	 * @param form
	 *            ステータスの入力値を格納した Form オブジェクト
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 * @exception NotFoundException
	 *                更新対象が存在しない場合
	 */
	public void updateHousingStatus(PanaHousingStatusForm form,
			String editUserId) throws Exception, NotFoundException {
		((PanaHousingManageImpl)this.housingManage).updateHousingStatus(form, editUserId);
	}

	/**
	 * パラメータで渡されたシステム物件CD に該当する物件拡張属性情報を削除する。（システム物件CD 単位）<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            削除対象システム物件CD
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public void delHousingInspection(String sysHousingCd) throws Exception {

		((PanaHousingManageImpl)this.housingManage).delHousingInspection(sysHousingCd);

	}

	/**
	 * ステータスの新規を行う<br/>
	 * <br/>
	 *
	 * @param form
	 *            ステータスの入力値を格納した Form オブジェクト
	 *
	 */
	public String addHousingInspection(PanaHousingInspectionForm form, int idx)
			throws Exception {

		return ((PanaHousingManageImpl)this.housingManage).addHousingInspection(form, idx);
	}

	/**
	 * パラメータで渡された Form の情報でリフォームプラン情報を新規追加する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム物件CD
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public List<HousingInspection> searchHousingInspection(String sysHousingCd)
			throws Exception {

		return ((PanaHousingManageImpl)this.housingManage).searchHousingInspection(sysHousingCd);
	}

	/**
	 * パラメータで渡された 建物詳細情報を新規追加する。<br/>
	 * 枝番 は自動採番されるので、ReformDtl の divNo プロパティには値を設定しない事。<br/>
	 * <br/>
	 *
	 * @param reformDtl
	 *            建物詳細情報
	 *
	 */
	public void updateBuildingDtlInfo(PanaHousingInfoForm inputForm,
			String editUserId) {

		((PanaHousingManageImpl)this.housingManage).updateBuildingDtlInfo(inputForm, editUserId);

	}

	/**
	 * リクエストパラメータで渡された物件種類CD （主キー値）に該当する設備マスタ情報を復帰する。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * このメソッドを使用した場合、暗黙の抽出条件（例えば、非公開物件の除外など）が適用される。<br/>
	 * よって、フロント側は基本的にこのメソッドを使用する事。<br/>
	 * <br/>
	 *
	 * @param housingKindCd
	 *            物件種類CD
	 *
	 * @return　DB から取得した設備マスタ情報を格納したリスト
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public List<JoinResult> searchEquipMst(String housingKindCd) throws Exception {

		return ((PanaHousingManageImpl)this.housingManage).searchEquipMst(housingKindCd);
	}

	/**
	 * CSV出力情報を検索し、結果リストを復帰する。<br/>
	 * 引数で渡された searchForm パラメータの値で検索条件を生成し、CSV出力情報を検索する。<br/>
	 * 検索結果は searchForm オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            検索条件、および、検索結果の格納オブジェクト
	 *
	 * @return 該当件数
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public void searchCsvHousing(PanaHousingSearchForm searchForm,HttpServletResponse response,
			PanaHousingPartThumbnailProxy panaHousingManager, PanaCommonManage panamCommonManager)throws Exception {

		((PanaHousingManageImpl)this.housingManage).searchCsvHousing(searchForm, response, panaHousingManager, panamCommonManager);
	}

	/**
	 * 物件情報のタイムスタンプを更新。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム物件CD
	 * @param editUserId
	 *            ユーザID
	 *
	 * @return なし
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public void updateEditTimestamp(String sysHousingCd, String editUserId)
			throws Exception {
		((PanaHousingManageImpl)this.housingManage).updateEditTimestamp(sysHousingCd, editUserId);
	}

	/**
	 * こだわり条件マスタ情報を取得。<br/>
	 * <br/>
	 *
	 * @param housingKindCd
	 *            物件種類
	 * @return こだわり条件マスタ情報
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public List<JoinResult> searchPartSrchMst(String housingKindCd) throws Exception {


		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("housingKindCd", housingKindCd);

		return this.partSrchMstListDAO.selectByFilter(criteria);
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
	public void deleteImgFile(String filePathName, String fileName, jp.co.transcosmos.dm3.core.vo.HousingImageInfo imgInfo, String fileType) throws IOException{
		String filePath = getImgOpenRootPath(imgInfo) + filePathName;
		if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder().equals(fileType)){
			// 画像ファイルの削除処理を実行する。
			this.deleteImgFile(filePath, fileName , ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder());
		}
		if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder().equals(fileType)){
			// 画像ファイルの削除処理を実行する。
			this.deleteImgFile(filePath, fileName , ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder());
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
	 * @param thumbnailMap 作成するファイルの情報
	 *
	 * @throws Exception 委譲先がスローする任意の例外
	 */
	public void addImgFile(String sysHousingCd, String fileName, jp.co.transcosmos.dm3.core.vo.HousingImageInfo imgInfo , String fileType) throws Exception{

		// もし該当データが存在しない場合（メンテ中に他から削除された場合など）は例外をスローする。
		Housing housing = this.searchHousingPk(sysHousingCd,true);
		if (housing == null){
			throw new NotFoundException();
		}
		// パス名を設定する。
		String filePath = ((PanaHousingManageImpl)this.housingManage).createImagePath(housing);
		// サムネイルの作成元フォルダ名　（日付を指定したフォルダ階層まで）
		String srcRoot = this.commonParameters.getHousImgTempPhysicalPath();
		if (!srcRoot.endsWith("/")) srcRoot += "/";
		srcRoot += PanaFileUtil.getUploadTempPath() + "/";

		// サムネイルを作成するファイル名のマップオブジェクトを作成する。
		Map<String, String> thumbnailMap = new HashMap<>();

		// Map の Key は、サムネイル作成元のファイル名（フルパス）
		String key = srcRoot + fileName;
		// Map の Value は、サムネイルの出力先パス（ルート～システム物件番号までのパス）
		String value = getImgOpenRootPath(imgInfo) + filePath;

		thumbnailMap.put(key, value);

		if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder().equals(fileType)){
			this.create(thumbnailMap, ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder());
		}
		if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder().equals(fileType)){
			this.create(thumbnailMap, ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder());
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
	 * @param thumbnailMap 作成するファイルの情報
	 *
	 * @throws IOException
	 * @throws Exception 委譲先がスローする任意の例外
	 */
	public void create(Map<String, String> thumbnailMap,String filetype)
			throws IOException, Exception {

		// 作成するファイル分繰り返す
		for (Entry<String, String> e : thumbnailMap.entrySet()){

			// サムネイル作成元のファイルオブジェクトを作成する。
			File srcFile = new File(e.getKey());

			// サムネイル出力先のルートパス （画像サイズの直前までのフォルダ階層）
			String destRootPath = e.getValue();
			if (!destRootPath.endsWith("/")) destRootPath += "/";
			// オリジナル画像をフルサイズ画像として copy する。
			if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder().equals(filetype)){
				FileUtils.copyFileToDirectory(srcFile, new File(destRootPath + ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder()));
			}
			if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder().equals(filetype)){
				FileUtils.copyFileToDirectory(srcFile, new File(destRootPath + ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder()));
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

	/**
	 * 物件画像ファイルを個別に削除する。<br/>
	 * filePath で指定したフォルダ内のファイルが空の場合、フォルダごと削除する。
	 * <br/>
	 * @param filePath ルート～システム物件CD までのパス（画像サイズの下までのパス）
	 * @param fileName　画像ファイル名
	 *
	 * @throws IOException
	 */
	public void deleteImgFile(String filePath, String fileName , String filetype) throws IOException{

		// オリジナル画像の削除
		if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder().equals(filetype)){
			(new File(filePath + ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder() + "/" + fileName)).delete();
		}
		if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder().equals(filetype)){
			(new File(filePath + ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder() + "/" + fileName)).delete();
		}
	}
	/**
	 * 物件画像情報のパス名に格納する値を取得する。<br/>
	 * <br/>
	 *
	 * @param housing
	 *            更新対象となる物件オブジェクト
	 *
	 * @return 加工されたパス名
	 */
	public String createImagePath(Housing housing) {

		return ((PanaHousingManageImpl)this.housingManage).createImagePath(housing);
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
	 * 市区町村値を取得する。<br/>
	 * <br/>
	 *
	 * @param housing
	 *            更新対象となる物件オブジェクト
	 *
	 * @return 加工されたパス名
	 * @throws Exception
	 */
	public int searchHousingInfo(PanaHousingSearchForm areaForm) throws Exception {

		return ((PanaHousingManageImpl)this.housingManage).searchHousingInfo(areaForm);
	}
}
