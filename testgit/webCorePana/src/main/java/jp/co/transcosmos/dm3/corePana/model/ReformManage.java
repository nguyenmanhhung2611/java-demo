package jp.co.transcosmos.dm3.corePana.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformDtlForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;

/**
 * リフォーム情報を管理する Model クラス用インターフェース.
 * <p>
 * リフォーム情報を操作する model クラスはこのインターフェースを実装する事。<br/>
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.03.10	新規作成
 * Thi Tran     2015.12.18  Update the interface to support searching housing by cd
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 *
 */
public interface ReformManage {

    /**
     * パラメータで渡された リフォームプラン情報を新規追加する。<br/>
     * システムリフォームCD は自動採番されるので、ReformPlan の sysReformCd プロパティには値を設定しない事。<br/>
     * <br/>
     * @param reformPlan リフォームプラン情報
     * @param inputForm リフォームForm オブジェクト
     * @param userId ログインユーザーID
     *
     * @return システムリフォームCD
     */
    public String addReformPlan(ReformPlan reformPlan, ReformInfoForm inputForm, String userId) throws Exception;

    /**
     * リフォームプラン情報の更新を行う<br/>
     * <br/>
     * @param reformPlan リフォームプラン情報
     *
     * @exception NotFoundException 更新対象が存在しない場合
     */
    public void updateReformPlan(ReformInfoForm inputForm, String userId) throws Exception;

    /**
     * パラメータで渡された sysReformCdをキーにリフォームプラン情報を削除する。<br/>
     * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
     * <br/>
     * @param sysReformCd システムリフォームCD
     * @throws IOException
     * @throws Exception
     *
     */
    public void delReformPlan(String sysHousingCd, String sysReformCd, String userId) throws IOException, Exception;

    /**
     * パラメータで渡された リフォーム詳細情報を新規追加する。<br/>
     * 枝番 は自動採番されるので、ReformDtl の divNo プロパティには値を設定しない事。<br/>
     * <br/>
     * @param reformDtl リフォーム詳細情報
     *
     * @return 枝番
     */
    public List<ReformDtl> addReformDtl(ReformDtlForm inputForm) throws Exception;

    /**
     * リフォーム詳細情報の更新を行う<br/>
     * <br/>
     * @param form リフォーム詳細情報の入力値を格納した Form オブジェクト
     *
     * @exception NotFoundException 更新対象が存在しない場合
     */
    public List<ReformDtl> updateReformDtl(ReformDtlForm inputForm) throws Exception;

    /**
     * パラメータで渡された Form の情報でリフォーム詳細情報を削除する。<br/>
     * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
     * <br/>
     * @param form リフォーム詳細情報の削除条件を格納した
     *
     */
    public ReformDtl delReformDtl(String sysReformCd, int divNo);

    /**
     * パラメータで渡された リフォーム画像情報を新規追加する。<br/>
     * 枝番 は自動採番されるので、ReformImg の divNo プロパティには値を設定しない事。<br/>
     * <br/>
     * @param reformImg リフォーム画像情報
     *
     * @return 枝番
     */
    public ReformImg addReformImg(ReformImgForm inputForm) throws Exception;

    /**
     * リフォーム画像情報の更新を行う<br/>
     * <br/>
     * @param form リフォーム画像情報の入力値を格納した Form オブジェクト
     *
     * @exception NotFoundException 更新対象が存在しない場合
     */
    public List<ReformImg> updateReformImg(ReformImgForm inputForm) throws Exception;

    /**
    /**
     * パラメータで渡された Form の情報でリフォーム画像情報を削除する。<br/>
     * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
     * <br/>
     * @param form リフォーム画像情報の削除条件を格納した
     * @throws IOException
     *
     */
    public ReformImg delReformImg(String sysReformCd, int divNo) throws IOException;

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
    public Map<String, Object> searchReform(String sysReformCd);

    /**
     * パラメータで渡された リフォーム・レーダーチャート詳細情報を更新（update後insert)する。<br/>
     * <br/>
     * @param reformChart リフォーム・レーダーチャート詳細情報
     *
     */
    public void updReformChart(ReformInfoForm form, int count);

    /**
     * パラメータで渡された リフォーム・レーダーチャート詳細情報を新規追加する。<br/>
     * <br/>
     * @param reformChart リフォーム・レーダーチャート詳細情報
     *
     */
    public void addReformChart(ReformInfoForm form, int count);

    /**
     * パラメータで渡された システムリフォームCDでリフォーム・レーダーチャート情報を削除する。<br/>
     * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
     * <br/>
     * @param sysReformCd システムリフォームCD
     *
     */
    public void delReformChart(String sysReformCd);

    /**
     * リフォーム画像情報の枝番を採番する処理。<br/>
     * <br/>
     * @param sysReformCd システムリフォームCD
     *
     */
    public int getReformImgDivNo(String sysReformCd);

    /**
     * リフォーム詳細情報の枝番を採番する処理。<br/>
     * <br/>
     * @param sysReformCd システムリフォームCD
     *
     */
    public int getReformDtlDivNo(String sysReformCd);

    /**
     * パラメータ システム物件CD をキーに、物件基本情報を検索する。<br/>
     * <br/>
     * @param form 検索条件、および、検索結果の格納オブジェクト
     *
     * @return 物件基本情報
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    public HousingInfo searchHousingInfo(String sysHousingCd) throws Exception;

    /**
     * パラメータ システム建物CD をキーに、物件基本情報を検索する。<br/>
     * <br/>
     * @param sysHousingCd システム建物CD
     *
     * @return 建物基本情報
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    public BuildingInfo searchBuildingInfo(String sysHousingCd) throws Exception;

    /**
     * リフォーム詳細情報を検索し、結果を復帰する。<br/>
     * 引数で渡された Form パラメータの値で検索条件を生成し、リフォーム詳細情報を検索する。<br/>
     * 検索結果は Form オブジェクトに格納され、取得した該当レコードを戻り値として復帰する。<br/>
     * <br/>
     * @param sysReformCd システムリフォームCD
     * @param div_no 枝番
     *
     * @return 検索条件に該当するリフォーム詳細情報
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    public ReformDtl searchReformDtlByPk(String sysReformCd, String div_no)
            throws Exception;

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
            throws Exception;


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
            throws Exception;

    /**
     * 物件基本情報のタイムスタンプ情報を更新する。<br/>
     * <br/>
     * @param sysHousingCd 更新対象システム物件CD
     * @param editUserId 更新者ID
     */
    public void updateEditTimestamp(String sysHousingCd, String sysReformCd, String editUserId) throws Exception;

    /**
     * Search housing by cd<br/>
     * <br/>
     * @param sysHousingCd housing cd
     * @param full Return public housing if false, return all if true
     * @return Housing information corresponding to the given housing cd
     * @throws Exception Exception is thrown while implementing
     */
    public Housing searchHousingByPk(String sysHousingCd, boolean full) throws Exception;
}
