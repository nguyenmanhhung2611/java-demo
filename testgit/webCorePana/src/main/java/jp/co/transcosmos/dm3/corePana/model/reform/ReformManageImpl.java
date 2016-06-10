package jp.co.transcosmos.dm3.corePana.model.reform;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformDtlForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformChart;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.FormulaUpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;

/** model クラスはこのインターフェースを実装する事。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.03.10	新規作成
 * Thi Tran     2015.12.18      Search housing by cd
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 *
 */
public class ReformManageImpl implements ReformManage {
    /** リフォームプランDAO */
    private DAO<ReformPlan> reformPlanDAO;

    /** リフォーム・レーダーチャートDAO */
    private DAO<ReformChart> reformChartDAO;

    /** リフォーム詳細情報DAO */
    private DAO<ReformDtl> reformDtlDAO;

    /** リフォーム画像情報DAO */
    private DAO<ReformImg> reformImgDAO;

    /** 建物情報メンテナンスの model */
    private PanaHousingPartThumbnailProxy panaHousingManager;

    /**
     * リフォームプランDAO の設定<br>
     * <br>
     * @param reformPlanDAO リフォームプランDAO
     */
    public void setReformPlanDAO(DAO<ReformPlan> reformPlanDAO) {
        this.reformPlanDAO = reformPlanDAO;
    }

    /**
     * リフォーム・レーダーチャートDAO の設定<br>
     * <br>
     * @param reformChartDAO リフォーム・レーダーチャートDAO
     */
    public void setReformChartDAO(DAO<ReformChart> reformChartDAO) {
        this.reformChartDAO = reformChartDAO;
    }

    /**
     * リフォーム詳細情報DAO の設定<br>
     * <br>
     * @param reformDtlDAO リフォーム詳細情報DAO
     */
    public void setReformDtlDAO(DAO<ReformDtl> reformDtlDAO) {
        this.reformDtlDAO = reformDtlDAO;
    }

    /**
     * リフォーム画像情報DAO の設定<br>
     * <br>
     * @param reformImgDAO リフォーム画像情報DAO
     */
    public void setReformImgDAO(DAO<ReformImg> reformImgDAO) {
        this.reformImgDAO = reformImgDAO;
    }

    /**
     * Pana物件情報メンテナンス用 model を設定する。<br/>
     * <br/>
     * @param panaHousingManager Pana物件情報メンテナンス用 model
     */
    public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
        this.panaHousingManager = panaHousingManager;
    }

    /**
     * パラメータで渡された リフォームプラン情報を新規追加する。<br/>
     * <br/>
     * @param reformPlan リフォームプラン情報
     * @param inputForm 入力値が格納された Form オブジェクト
     * @param userId ログインユーザーID （更新情報用）
     *
     * @return システムリフォームCD
     */
    public String addReformPlan(ReformPlan reformPlan, ReformInfoForm inputForm, String userId) throws Exception {

        // 建物基本情報を取得する。
        BuildingInfo buildingInfo = this.searchBuildingInfo(inputForm.getSysHousingCd());
        String uploadPath = getUploadPath(buildingInfo, inputForm.getSysHousingCd());

        // データを追加
        try {

        	// レーダーチャート画像パス名の設定
        	reformPlan.setReformChartImagePathName(uploadPath);

            // 取得した主キー値でリフォームプラン情報を登録
            this.reformPlanDAO.insert(new ReformPlan[] { reformPlan });

        } catch (DataIntegrityViolationException e) {
            // この例外は、登録直前に依存先となる建物情報が削除された場合に発生する。
            e.printStackTrace();
            throw new NotFoundException();
        }

        return reformPlan.getSysReformCd();
    }

    /**
     * リフォームプラン情報の更新を行う<br/>
     * <br/>
     * @param inputForm 入力値が格納された Form オブジェクト
     * @param userId ログインユーザーID （更新情報用）
     *
     * @exception NotFoundException 更新対象が存在しない場合
     */
    public void updateReformPlan(ReformInfoForm inputForm, String userId)  throws Exception {

        // 建物基本情報を取得する。
        BuildingInfo buildingInfo = this.searchBuildingInfo(inputForm.getSysHousingCd());
        String uploadPath = getUploadPath(buildingInfo, inputForm.getSysHousingCd());

        ReformPlan reformPlan = this.reformPlanDAO.selectByPK(inputForm.getSysReformCd());

        // 該当するデータが存在しない場合は、例外をスローする。
        if (reformPlan == null) {
            throw new NotFoundException();
        }

        inputForm.copyToReformPlan(reformPlan, userId);

        try {

            // レーダーチャート画像の更新
            if (!"on".equals(inputForm.getReformImgDel())) {

            	// レーダーチャート画像パス名の更新
            	reformPlan.setReformChartImagePathName(uploadPath);

    			// レーダーチャート画像の削除
            } else {

            	//  レーダーチャート画像パス名
            	reformPlan.setReformChartImagePathName("");
            	//  レーダーチャート画像ファイル名
            	reformPlan.setReformChartImageFileName("");
            }

            // リフォームプラン情報の更新
            this.reformPlanDAO.update(new ReformPlan[] { reformPlan });

        } catch (DataIntegrityViolationException e) {
            // この例外は、登録直前に変更先となるリフォーム情報が削除された場合に発生する。
            e.printStackTrace();
            throw new NotFoundException();
        }
    }

    /**
     * パラメータで渡された sysReformCdをキーにリフォームプラン情報を削除する。<br/>
     * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
     * <br/>
     * @param sysReformCd システムリフォームCD
     * @throws Exception
     *
     */
    public void delReformPlan(String sysHousingCd, String sysReformCd, String userId) throws Exception {

        // 主キー値で削除処理を実行
        this.reformPlanDAO.deleteByPK(new String[] { sysReformCd });

    	//  物件拡張属性情報の削除（レーダーチャート画像パス名）
    	this.panaHousingManager.delExtInfo(sysHousingCd, "reformChart",
				"reformChartImagePathName", userId);

    	//  物件拡張属性情報の削除（レーダーチャート画像ファイル名）
    	this.panaHousingManager.delExtInfo(sysHousingCd, "reformChart",
				"reformChartImageFileName", userId);

        // 依存表は整合性制約で削除する事を想定しているので、明示的な削除は行わない。
        // reformDtl, reformImg, reformChart

        // 画像ファイルは Proxy 側で削除するのでここでは対応しない。
    }

    /**
     * パラメータで渡された リフォーム詳細情報を新規追加する。<br/>
     * 枝番 は自動採番されるので、ReformDtl の divNo プロパティには値を設定しない事。<br/>
     * <br/>
     * @param reformDtl リフォーム詳細情報
     *
     * @return 枝番
     */
    public List<ReformDtl> addReformDtl(ReformDtlForm inputForm) throws Exception {
        List<ReformDtl> reformDtlList = new ArrayList<ReformDtl>();

        // 建物基本情報を取得する。
        BuildingInfo buildingInfo = this.searchBuildingInfo(inputForm.getSysHousingCd());

        for (int idx = 0; idx < inputForm.getAddHidFileName().length; idx++) {
            if (!StringUtils.isEmpty(inputForm.getAddHidFileName()[idx])) {
                ReformDtl reformDtl = new ReformDtl();
                inputForm.copyToReformDtl(reformDtl, idx);

                reformDtl.setPathName(getUploadPath(buildingInfo, inputForm.getSysHousingCd()));
                // 連番設定
                reformDtl.setDivNo(getReformDtlDivNo(reformDtl.getSysReformCd()));

                try {
                    // リフォームプラン情報の更新
                    this.reformDtlDAO.insert(new ReformDtl[] { reformDtl });
                } catch (DataIntegrityViolationException e) {
                    // この例外は、登録直前に変更先となるリフォーム情報が削除された場合に発生する。
                    e.printStackTrace();
                    throw new NotFoundException();
                }
                reformDtlList.add(reformDtl);
            }
        }
        // メイン画像フラグ、枝番を更新する。
     	updateMainFlgAndDivNo(inputForm.getSysReformCd(),"reformDtlDAO");
        return reformDtlList;
    }

    /**
     * リフォーム詳細情報の更新を行う<br/>
     * <br/>
     * @param form リフォーム詳細情報の入力値を格納した Form オブジェクト
     *
     * @exception NotFoundException 更新対象が存在しない場合
     */
    public List<ReformDtl> updateReformDtl(ReformDtlForm inputForm) throws Exception {

        // 戻り値となる、削除が発生した物件画像情報のリスト
        List<ReformDtl> reformDtlDelList = new ArrayList<ReformDtl>();
        List<ReformDtl> reformDtlList = new ArrayList<ReformDtl>();

        // 画像ファイルを基準に件数分ループする。
        for (int idx = 0; idx < inputForm.getDivNo().length; ++idx) {

            // 削除フラグが設定されている場合は削除処理へ
            if ("1".equals(inputForm.getDelFlg()[idx])) {

                // 物件画像情報を削除
                ReformDtl reformDtl = delReformDtl(inputForm.getSysReformCd(),
                        PanaStringUtils.toInteger(inputForm.getDivNo()[idx]));
                // 実際に削除する情報が無かった場合、 null が復帰されるので、その場合はリストに追加しない。
                if (reformDtl != null) {
                    reformDtlDelList.add(reformDtl);
                }

            } else {

                DAOCriteria criteria = new DAOCriteria();
                criteria.addWhereClause("sysReformCd", inputForm.getSysReformCd());
                criteria.addWhereClause("divNo", inputForm.getDivNo()[idx]);

                List<ReformDtl> reformDtlResult = this.reformDtlDAO.selectByFilter(criteria);

                // 該当するデータが存在しない場合は、例外をスローする。
                if (reformDtlResult == null || reformDtlResult.size() == 0) {
                    throw new NotFoundException();
                }

                ReformDtl reformDtl = reformDtlResult.get(0);
                inputForm.copyToReformDtl(reformDtl, idx);

                reformDtlList.add(reformDtl);
            }
        }
        if (reformDtlList.size() > 0) {
            ReformDtl reformDtls[] = new ReformDtl[reformDtlList.size()];
            reformDtlList.toArray(reformDtls);

            try {
                // リフォームプラン情報の更新
                reformDtlDAO.update(reformDtls);
            } catch (DataIntegrityViolationException e) {
                // この例外は、登録直前に変更先となるリフォーム情報が削除された場合に発生する。
                e.printStackTrace();
                throw new NotFoundException();
            }
        }
        // メイン画像フラグ、枝番を更新する。
     	updateMainFlgAndDivNo(inputForm.getSysReformCd(),"reformDtlDAO");
        return reformDtlDelList;
    }

    /**
     * パラメータで渡された Form の情報でリフォーム詳細情報を削除する。<br/>
     * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
     * <br/>
     * @param form リフォーム詳細情報の削除条件を格納した
     *
     */
    public ReformDtl delReformDtl(String sysReformCd, int divNo) {

        // 物件画像情報を削除する条件を作成
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);
        criteria.addWhereClause("divNo", divNo);

        // 削除前に、削除対象データの情報を取得する。
        // もし取得出来ない場合は null を復帰する。
        List<ReformDtl> reformDtlList = this.reformDtlDAO.selectByFilter(criteria);
        if (reformDtlList.size() == 0)
            return null;

        this.reformDtlDAO.deleteByFilter(criteria);

        // 画像ファイルの物理削除は拡張性を考慮してこのクラス内では行わない。
        // 物件画像公開、サムネイル作成、物理削除等の処理は、このクラスの Proxy クラス側で対応する。

        return reformDtlList.get(0);
    }

    /**
     * パラメータで渡された リフォーム画像情報を新規追加する。<br/>
     * 枝番 は自動採番されるので、ReformImg の divNo プロパティには値を設定しない事。<br/>
     * <br/>
     * @param reformImg リフォーム画像情報
     *
     * @return 枝番
     */
    public ReformImg addReformImg(ReformImgForm inputForm) throws Exception {

        // 建物基本情報を取得する。
        BuildingInfo buildingInfo = this.searchBuildingInfo(inputForm.getSysHousingCd());

        ReformImg reformImg = inputForm.newToReformImg();
        String uploadPath = getUploadPath(buildingInfo, inputForm.getSysHousingCd());

        reformImg.setAfterPathName(uploadPath);
        reformImg.setBeforePathName(uploadPath);
        // 連番設定
        reformImg.setDivNo(getReformImgDivNo(reformImg.getSysReformCd()));

        try {
            // リフォームプラン情報の更新
            this.reformImgDAO.insert(new ReformImg[] { reformImg });
        } catch (DataIntegrityViolationException e) {
            // この例外は、登録直前に変更先となるリフォーム情報が削除された場合に発生する。
            e.printStackTrace();
            throw new NotFoundException();
        }
        // メイン画像フラグ、枝番を更新する。
     	updateMainFlgAndDivNo(inputForm.getSysReformCd(),"reformImgDAO");
        return reformImg;
    }

    /**
     * リフォーム画像情報の更新を行う<br/>
     * <br/>
     * @param form リフォーム画像情報の入力値を格納した Form オブジェクト
     *
     * @exception NotFoundException 更新対象が存在しない場合
     */
    public List<ReformImg> updateReformImg(ReformImgForm inputForm) throws Exception {
        List<ReformImg> reformImgList = new ArrayList<ReformImg>();
        List<ReformImg> reformImgDelList = new ArrayList<ReformImg>();

        // 更新処理の場合、更新対象データを取得する。
        for (int idx = 0; idx < inputForm.getDivNo().length; idx++) {
            // 削除処理でない場合
            if ("1".equals(inputForm.getDelFlg()[idx])) {

                // 物件画像情報を削除
                ReformImg reformImg = delReformImg(inputForm.getSysReformCd(),
                        PanaStringUtils.toInteger(inputForm.getDivNo()[idx]));
                // 実際に削除する情報が無かった場合、 null が復帰されるので、その場合はリストに追加しない。
                if (reformImg != null) {
                    reformImgDelList.add(reformImg);
                }
            } else {
                DAOCriteria criteria = new DAOCriteria();
                criteria.addWhereClause("sysReformCd", inputForm.getSysReformCd());
                criteria.addWhereClause("divNo", inputForm.getDivNo()[idx]);

                List<ReformImg> reformImgResult = this.reformImgDAO.selectByFilter(criteria);

                // 該当するデータが存在しない場合は、例外をスローする。
                if (reformImgResult == null || reformImgResult.size() == 0) {
                    throw new NotFoundException();
                }

                ReformImg reformImg = reformImgResult.get(0);

                // フォームの入力値をバリーオブジェクトに設定する。
                inputForm.copyToReformImg(reformImg, idx);

                reformImgList.add(reformImg);
            }
        }

        if (reformImgList.size() > 0) {
            ReformImg reformImgs[] = new ReformImg[reformImgList.size()];
            reformImgList.toArray(reformImgs);

            try {
                // リフォームプラン情報の更新
                reformImgDAO.update(reformImgs);
            } catch (DataIntegrityViolationException e) {
                // この例外は、登録直前に変更先となるリフォーム情報が削除された場合に発生する。
                e.printStackTrace();
                throw new NotFoundException();
            }

        }
        // メイン画像フラグ、枝番を更新する。
     	updateMainFlgAndDivNo(inputForm.getSysReformCd(),"reformImgDAO");
        return reformImgDelList;
    }

    /**
     * パラメータで渡された Form の情報でリフォーム画像情報を削除する。<br/>
     * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
     * <br/>
     * @param form リフォーム画像情報の削除条件を格納した
     *
     */
    public ReformImg delReformImg(String sysReformCd, int divNo) {

        // 物件画像情報を削除する条件を作成
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);
        criteria.addWhereClause("divNo", divNo);

        // 削除前に、削除対象データの情報を取得する。
        // もし取得出来ない場合は null を復帰する。
        List<ReformImg> reformImgList = this.reformImgDAO.selectByFilter(criteria);
        if (reformImgList.size() == 0)
            return null;

        this.reformImgDAO.deleteByFilter(criteria);

        return reformImgList.get(0);
    }


    /**
	 * 物件画像情報のメイン画像フラグ、および枝番の更新を行う<br/>
	 * 画像種別毎に一番最初に表示する物件画像情報に対してメイン画像フラグを設定する。<br/>
	 * また、画像タイプ毎に表示順で枝番を更新する。<br/>
	 * <br/>
	 * @param getSysHousingCd システム物件CD
	 */
	protected void updateMainFlgAndDivNo(String sysReformCd,String daoType){
		DAOCriteria tagetCri = new DAOCriteria();
		tagetCri.addWhereClause("sysReformCd", sysReformCd);


		// 表示順のみを変更した場合、枝番が重複してエラーが発生する。
		// そこで、トリッキーな方法だが、一度、枝番をマイナスの値に UPDATE した後に枝番の更新処理を行う。
		UpdateExpression[] initExpression
			= new UpdateExpression[] {new FormulaUpdateExpression("div_no = div_no * -1")};

		if("reformImgDAO".equals(daoType)){

			this.reformImgDAO.updateByCriteria(tagetCri, initExpression);

			// ソート条件を追加
			// 枝番はマイナスの値に更新されているので、DESC でソートする。
			tagetCri.addOrderByClause("sortOrder");
			tagetCri.addOrderByClause("divNo", false);

			// システム物件CD 内の物件画像情報を取得
			List<ReformImg> imgList = this.reformImgDAO.selectByFilter(tagetCri);

			int divNo = 0;							// 枝番
			for (ReformImg imgInfo : imgList){
				++divNo;

				// 更新対象のキー情報を作成
				DAOCriteria updCri = new DAOCriteria();
				updCri.addWhereClause("sysReformCd", sysReformCd);
				updCri.addWhereClause("divNo", imgInfo.getDivNo());

				UpdateExpression[] expression;
				// 画像タイプが前回の行と同じ場合、メイン画像をオフとして枝番を更新する。

				expression = new UpdateExpression[] {new UpdateValue("divNo", divNo)};

				this.reformImgDAO.updateByCriteria(updCri, expression);
			}
		}
		if("reformDtlDAO".equals(daoType)){
			this.reformDtlDAO.updateByCriteria(tagetCri, initExpression);

			// ソート条件を追加
			// 枝番はマイナスの値に更新されているので、DESC でソートする。
			tagetCri.addOrderByClause("sortOrder");
			tagetCri.addOrderByClause("divNo", false);

			// システム物件CD 内の物件画像情報を取得
			List<ReformDtl> imgList = this.reformDtlDAO.selectByFilter(tagetCri);

			int divNo = 0;							// 枝番
			for (ReformDtl imgInfo : imgList){
				++divNo;

				// 更新対象のキー情報を作成
				DAOCriteria updCri = new DAOCriteria();
				updCri.addWhereClause("sysReformCd", sysReformCd);
				updCri.addWhereClause("divNo", imgInfo.getDivNo());

				UpdateExpression[] expression;
				// 画像タイプが前回の行と同じ場合、メイン画像をオフとして枝番を更新する。

				expression = new UpdateExpression[] {new UpdateValue("divNo", divNo)};

				this.reformDtlDAO.updateByCriteria(updCri, expression);
			}
		}
	}

    /**
     * リフォーム関連情報（ReformPlan, ReformChart, ReformDtl, ReformImg)を検索し、結果Mapを復帰する。<br/>
     * 引数で渡された sysReformCd パラメータの値で検索条件を生成し、リフォーム情報を検索する。<br/>
     * <br/>
     * 検索条件として、以下のデータを検索対象とする。<br/>
     *
     * @param sysReformCd システムリフォームCD
     *
     * @return リフォームプラン情報のMap
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    public Map<String, Object> searchReform(String sysReformCd) {
        // リフォームプラン情報
        ReformPlan reformPlanResult =
                this.reformPlanDAO.selectByPK(sysReformCd);

        // 画面項目検索
        // リフォーム・レーダーチャート情報
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);
        List<ReformChart> chartResults = this.reformChartDAO.selectByFilter(criteria);

        // リフォーム詳細情報取得
        criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);
        criteria.addOrderByClause("sortOrder", true);
        criteria.addOrderByClause("divNo", true);
        List<ReformDtl> dtlResults = this.reformDtlDAO.selectByFilter(criteria);

        // リフォーム画像情報取得
        criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);
        criteria.addOrderByClause("sortOrder", true);
        criteria.addOrderByClause("divNo", true);
        List<ReformImg> imgResults = this.reformImgDAO.selectByFilter(criteria);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("reformPlan", reformPlanResult);
        // リフォーム・レーダーチャート情報を格納
        resultMap.put("chartList", chartResults);
        // リフォーム詳細情報を格納
        resultMap.put("dtlList", dtlResults);
        // リフォーム画像情報を格納
        resultMap.put("imgList", imgResults);
        // システムリフォームCdを格納
        resultMap.put("sysReformCd", sysReformCd);

        return resultMap;
    }

    /**
     * パラメータで渡された リフォーム・レーダーチャート詳細情報を更新（update後insert)する。<br/>
     * <br/>
     * @param reformChart リフォーム・レーダーチャート詳細情報
     *
     */
    public void updReformChart(ReformInfoForm form, int count) {
        delReformChart(form.getSysReformCd());
        addReformChart(form, count);
    }

    /**
     * パラメータで渡された リフォーム・レーダーチャート詳細情報を新規追加する。<br/>
     * <br/>
     * @param reformChart リフォーム・レーダーチャート詳細情報
     *
     */
    public void addReformChart(ReformInfoForm form, int count) {
        ReformChart reformCharts[] = new ReformChart[count];
        for (int i = 0; i < count; i++) {
            if (!StringValidateUtil.isEmpty(form.getChartValue()[i])) {
                ReformChart reformChart = new ReformChart();

                // フォームの入力値をバリーオブジェクトに設定する。
                form.copyToReformChart(reformChart, String.valueOf(i));
                reformCharts[i] = reformChart;
            }
        }
        try {
            // リフォームプラン情報の更新
            reformChartDAO.insert(reformCharts);
        } catch (DataIntegrityViolationException e) {
            // この例外は、登録直前に変更先となるリフォーム情報が削除された場合に発生する。
            e.printStackTrace();
            throw new NotFoundException();
        }
    }

    /**
     * パラメータで渡された システムリフォームCDでリフォーム・レーダーチャート情報を削除する。<br/>
     * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
     * <br/>
     * @param sysReformCd システムリフォームCD
     *
     */
    public void delReformChart(String sysReformCd) {
        DAOCriteria criteria = new DAOCriteria();

        // フォームの入力値をバリーオブジェクトに設定する。
        criteria.addWhereClause("sysReformCd", sysReformCd);

        reformChartDAO.deleteByFilter(criteria);
    }

    /**
     * リフォーム画像情報の枝番を採番する処理。<br/>
     * <br/>
     * @param sysReformCd システムリフォームCD
     *
     */
    public int getReformImgDivNo(String sysReformCd) {
        // この処理は、排他制御を行っていない。
        // リフォーム詳細の検索
        DAOCriteria daoCriteria = new DAOCriteria();

        if (StringUtils.isEmpty(sysReformCd)) {
            daoCriteria.addWhereClause("sysReformCd", sysReformCd);
        }

        daoCriteria.addOrderByClause("divNo", false);

        // 同一システムリフォームCDで枝番を降順にソートする。
        // その結果の最大値 + 1 を枝番として復帰する。
        // 該当データが無い場合は 1 を復帰する。
        List<ReformImg> reformImgList = this.reformImgDAO.selectByFilter(daoCriteria);

        if (reformImgList.size() == 0)
            return 1;
        return reformImgList.get(0).getDivNo() + 1;
    }

    /**
     * リフォーム詳細情報の枝番を採番する処理。<br/>
     * <br/>
     * @param sysReformCd システムリフォームCD
     *
     */
    public int getReformDtlDivNo(String sysReformCd) {
        // この処理は、排他制御を行っていない。
        // リフォーム詳細の検索
        DAOCriteria daoCriteria = new DAOCriteria();

        if (StringUtils.isEmpty(sysReformCd)) {
            daoCriteria.addWhereClause("sysReformCd", sysReformCd);
        }

        daoCriteria.addOrderByClause("divNo", false);

        // 同一システムリフォームCDで枝番を降順にソートする。
        // その結果の最大値 + 1 を枝番として復帰する。
        // 該当データが無い場合は 1 を復帰する。
        List<ReformDtl> reformDtlList = this.reformDtlDAO.selectByFilter(daoCriteria);

        if (reformDtlList.size() == 0)
            return 1;
        return reformDtlList.get(0).getDivNo() + 1;
    }

    /**
     * パラメータ システム物件CD をキーに、物件基本情報を検索する。<br/>
     * <br/>
     * @param sysHousingCd システム物件CD
     *
     * @return 物件基本情報
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    public HousingInfo searchHousingInfo(String sysHousingCd) throws Exception {
        // 物件情報を検索する
        Housing housing = this.panaHousingManager.searchHousingPk(sysHousingCd, true);

        HousingInfo housingInfo = new HousingInfo();
        if (housing != null && housing.getHousingInfo() != null) {
            housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
        }

        return housingInfo;
    }

    /**
     * パラメータ システム建物CD をキーに、物件基本情報を検索する。<br/>
     * <br/>
     * @param sysHousingCd システム建物CD
     *
     * @return 建物基本情報
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    public BuildingInfo searchBuildingInfo(String sysHousingCd) throws Exception {
        // 物件情報を検索する
        Housing housing = this.panaHousingManager.searchHousingPk(sysHousingCd, true);

        return (BuildingInfo) housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
    }

    /**
     * リフォーム詳細情報を検索し、結果を復帰する。<br/>
     * 引数で渡された Form パラメータの値で検索条件を生成し、リフォーム詳細情報を検索する。<br/>
     * 検索結果は Form オブジェクトに格納され、取得した該当レコードを戻り値として復帰する。<br/>
     * <br/>
     * @param sysReformCd システムリフォームCD
     * @param divNo 枝番
     *
     * @return 検索条件に該当するリフォーム詳細情報
     *
     */
    public ReformDtl searchReformDtlByPk(String sysReformCd, String divNo) {
        // 建物基本情報を検索する条件を生成する。
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);
        criteria.addWhereClause("divNo", divNo);

        List<ReformDtl> reformDtlList = this.reformDtlDAO.selectByFilter(criteria);

        if (reformDtlList == null || reformDtlList.size() == 0) {
            return null;
        }

        return reformDtlList.get(0);
    }

    /**
     * リフォームプラン情報を検索し、結果を復帰する。<br/>
     * <br/>
     * @param sysHousingCd システム物件CD
     *
     * このメソッドを使用した場合、暗黙の抽出条件（例えば、非公開物件の除外など）が適用される。<br/>
	 * よって、フロント側は基本的にこのメソッドを使用する事。<br/>
     *
     * @return 検索条件に該当するリフォームプラン情報
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    public List<ReformPlan> searchReformPlan(String sysHousingCd)
            throws Exception {
        return searchReformPlan(sysHousingCd, false);
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
    public List<ReformPlan> searchReformPlan(String sysHousingCd, boolean full)
            throws Exception {

        // リフォームプラン情報を検索する条件を生成する。
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("sysHousingCd", sysHousingCd);

        if (!full) {
            criteria.addWhereClause("hiddenFlg", 0);
        }

        List<ReformPlan> reformPlanList = this.reformPlanDAO.selectByFilter(criteria);

        return reformPlanList;
    }

    /**
     * 公開パスを復帰する。<br/>
     * <br/>
     * @param buildingInfo 建物基本情報
     * @param sysHousingCd システム物件番号
     * @return 公開パス
     *         例）[物件種別CD]/[都道府県CD]/[市区町村CD]/システム物件番号/
     */
    protected static String getUploadPath(BuildingInfo buildingInfo, String sysHousingCd) {
        StringBuffer transPath = new StringBuffer("");

        // 物件種別CDフォルダーをパスに設定
        // 物件種別CDが未設定の場合、「999」フォルダーに置く
        // [物件種別CD]/
        transPath.append(StringUtils.isEmpty(buildingInfo.getHousingKindCd()) ?
                "999" : buildingInfo.getHousingKindCd());
        transPath.append("/");

        // 都道府県コードフォルダーをパスに設定
        // 都道府県コードが未設定の場合、「99」フォルダーに置く
        // [物件種別CD]/[都道府県CD]/
        transPath.append(StringUtils.isEmpty(buildingInfo.getPrefCd()) ?
                "99" : buildingInfo.getPrefCd());
        transPath.append("/");

        // 市町村コードフォルダーをパスに設定
        // 市町村コードが未設定の場合、「99999」フォルダーに置く
        // [物件種別CD]/[都道府県CD]/[市区町村CD]/
        transPath.append(StringUtils.isEmpty(buildingInfo.getAddressCd()) ?
                "99999" : buildingInfo.getAddressCd());
        transPath.append("/");

        // システム物件CD
        // システム物件CDフォルダーをパスに設定
        // [物件種別CD]/[都道府県CD]/[市区町村CD]/システム物件CD/
        transPath.append(sysHousingCd);
        transPath.append("/");

        return transPath.toString();
    }

    /**
     * 物件基本情報のタイムスタンプ情報を更新する。<br/>
     * <br/>
     * @param sysHousingCd 更新対象システム物件CD
     * @param editUserId 更新者ID
     * @throws Exception
     */
    public void updateEditTimestamp(String sysHousingCd, String sysReformCd, String editUserId) throws Exception {
        this.panaHousingManager.updateEditTimestamp(sysHousingCd, editUserId);

        // 更新条件を生成
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);

        // 更新オブジェクトを作成
        UpdateExpression[] updateExpression = new UpdateExpression[] { new UpdateValue("updUserId", editUserId),
                new UpdateValue("updDate", new Date()) };

        this.reformPlanDAO.updateByCriteria(criteria, updateExpression);
    }

    /**
     * Search all housing by the given housing cd
     * @param sysHousingCd the housing cd
     * @param full Return public housing if false. Else, return all
     * @return Housing
     * @exception Exception is thrown while implementing
     */
    public Housing searchHousingByPk(String sysHousingCd, boolean full)
            throws Exception {
        return this.panaHousingManager.searchHousingPk(sysHousingCd, full);
    }
}
