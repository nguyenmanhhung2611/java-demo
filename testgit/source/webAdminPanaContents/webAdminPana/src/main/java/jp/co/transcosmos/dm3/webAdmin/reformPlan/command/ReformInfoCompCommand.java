package jp.co.transcosmos.dm3.webAdmin.reformPlan.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;

/**
 * リフォーム情報の追加、変更、削除処理.
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、リフォーム情報を新規登録する。</li>
 * <li>また、公開先が特定個人の場合、リフォーム公開先情報も新規登録する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、リフォーム情報を更新する。</li>
 * <li>リフォーム公開先情報は一度削除し、変更後の公開先が特定個人であれば、リフォーム公開先情報も新規登録する。</li>
 * <li>もし、更新対象データが存在しない場合、更新処理が継続できないので該当無し画面を表示する。</li>
 * </ul>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success</li>:正常終了（リダイレクトページ）
 * <li>input</li>:バリデーションエラーによる再入力
 * <li>notFound</li>:該当データが存在しない場合（更新処理の場合）
 * <li>comp</li>:完了画面表示
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * Zhang.Yu		2015.03.12	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class ReformInfoCompCommand implements Command {

    /** 共通コード変換処理 */
    private CodeLookupManager codeLookupManager;

    /** リフォーム情報メンテナンスを行う Model オブジェクト */
    private ReformPartThumbnailProxy reformManager;

    /** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

    /** Panasonic用ファイル処理関連共通 */
    private PanaFileUtil fileUtil;

    /**
     * 共通コード変換オブジェクトを設定する。<br/>
     * <br/>
     * @param codeLookupManager
     */
    public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * リフォーム情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
     * <br/>
     * @param reformManager リフォーム情報メンテナンスの model オブジェクト
     */
    public void setReformManager(ReformPartThumbnailProxy reformManager) {
        this.reformManager = reformManager;
    }

    /**
     * 共通パラメータオブジェクトを設定する。<br/>
     * <br/>
     * @param commonParameters 共通パラメータオブジェクト
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

    /**
     * Panasonic用ファイル処理関連共通を設定する。<br/>
     * <br/>
     * @param fileUtil Panasonic用ファイル処理関連共通
     */
    public void setFileUtil(PanaFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    /**
     * リフォーム情報の追加、変更処理<br>
     * <br>
     * @param request クライアントからのHttpリクエスト。
     * @param response クライアントに返すHttpレスポンス。
     */
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // リクエストパラメータを格納した model オブジェクトを生成する。
        // このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        ReformInfoForm inputForm = (ReformInfoForm) model.get("inputForm");

        // ログインユーザーの情報を取得する。　（タイムスタンプの更新用）
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

        // 完了画面でリロードした場合、更新処理が意図せず実行される問題が発生する。
        // その問題を解消する為、view 名で "success"　を指定すると自動リダイレクト画面が表示される。
        // このリダイレクト画面は、command パラメータを "redirect"　に設定して完了画面へリクエストを
        // 送信する。
        // よって、command = "redirect" の場合は、ＤＢ更新は行わず、完了画面を表示する。
        String command = inputForm.getCommand();
        if (command != null && "redirect".equals(command)) {
            return new ModelAndView("comp", model);
        }

        // 画面項目チェック、事前チェックを行う
        if ("insert".equals(command) || "update".equals(command)) {

            // バリデーションを実行
            List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
            inputForm.validate(errors);

            // 画像存在チェック
           // inputForm.reformImgFileCheck(errors, commonParameters);

            if (errors.size() > 0) {

                // バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
                model.put("errors", errors);

                // リフォーム情報入力画面格納
                pageFormat(model, inputForm);

                // ログインID が存在しない場合は、該当なし画面へ
                return new ModelAndView("input", model);
            }
        }

        // 事前チェック:物件基本情報テーブルの読込
        HousingInfo housingInfo = this.reformManager.searchHousingInfo(inputForm.getSysHousingCd());
        if (housingInfo.getSysHousingCd() == null) {
            // データの存在しない場合,メッセージ："｛0｝物件情報が存在しない"表示
            throw new NotFoundException();
        }

        try {
            // 各種処理を実行
            execute(model, inputForm, loginUser);
        } catch (Exception e) {
            // ログインID が存在しない場合は、該当なし画面へ
            return new ModelAndView("input", model);
        }

        return new ModelAndView("success", model);
    }

    /**
     * 処理の振り分けと実行を行う。<br/>
     * <br/>
     * @param model View 層へ引き渡す model オブジェクト
     * @param inputForm 入力値が格納された Form オブジェクト
     * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     * @exception NotFoundException 更新対象が存在しない場合
     */
    protected void execute(Map<String, Object> model, ReformInfoForm inputForm, AdminUserInterface loginUser)
            throws Exception, NotFoundException {

        if ("insert".equals(inputForm.getCommand())) {

            insert(model, inputForm, loginUser);

        } else if ("update".equals(inputForm.getCommand())) {

            update(model, inputForm, loginUser);

        }
    }

    /**
     * 新規登録処理<br/>
     * 引数で渡された内容でリフォーム情報を追加する。<br/>
     * <br/>
     * @param model View 層へ引き渡す model オブジェクト
     * @param inputForm 入力値が格納された Form オブジェクト
     * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    protected void insert(Map<String, Object> model, ReformInfoForm inputForm, AdminUserInterface loginUser)
            throws Exception {
        ReformPlan reformPlan = new ReformPlan();

        inputForm.copyToReformPlan(reformPlan, String.valueOf(loginUser.getUserId()));
        // リフォームプラン情報登録処理
        String sysReformCd = this.reformManager.addReformPlan(reformPlan, inputForm, (String)loginUser.getUserId());

        // リフォーム・レーダーチャート情報登録処理
        inputForm.setSysReformCd(sysReformCd);
        if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(inputForm.getHousingKindCd()) ||
        		PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(inputForm.getHousingKindCd())) {
        	this.reformManager.updReformChart(inputForm, 8);
        }

        // 物件基本情報のタイムスタンプ情報を更新
        this.reformManager.updateEditTimestamp(inputForm.getSysHousingCd(),
        		inputForm.getSysReformCd(), (String)loginUser.getUserId());
    }

    /**
     * 更新登録処理<br/>
     * 引数で渡された内容でリフォーム情報を更新する。<br/>
     * <br/>
     * @param model View 層へ引き渡す model オブジェクト
     * @param inputForm 入力値が格納された Form オブジェクト
     * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     * @exception NotFoundException 更新対象が存在しない場合
     */
    protected void update(Map<String, Object> model, ReformInfoForm inputForm, AdminUserInterface loginUser)
            throws Exception, NotFoundException {

        // リフォームプラン情報更新処理
        this.reformManager.updateReformPlan(inputForm, (String)loginUser.getUserId());

        // リフォーム・レーダーチャート情報更新処理
        if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(inputForm.getHousingKindCd()) ||
        		PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(inputForm.getHousingKindCd())) {
        	this.reformManager.updReformChart(inputForm, 8);
        }

        // 物件基本情報のタイムスタンプ情報を更新
        try{
            this.reformManager.updateEditTimestamp(inputForm.getSysHousingCd(),
            		inputForm.getSysReformCd(), (String)loginUser.getUserId());
        } catch (DataIntegrityViolationException e) {
            // この例外は、登録直前に変更先となるリフォーム情報が削除された場合に発生する。
            e.printStackTrace();
            throw new NotFoundException();
        }
    }



    /**
     * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
     * <br/>
     * @param request HTTP リクエストパラメータ
     * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
     */
    protected Map<String, Object> createModel(HttpServletRequest request) {

        Map<String, Object> model = new HashMap<>();

        // リクエストパラメータを取得して Form オブジェクトを作成する。
        ReformInfoForm requestForm = new ReformInfoForm(this.codeLookupManager);
        FormPopulator.populateFormBeanFromRequest(request, requestForm);
        model.put("inputForm", requestForm);

        PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
        PanaHousingSearchForm searchForm = housingFactory.createPanaHousingSearchForm(request);
        model.put("searchForm", searchForm);

        return model;

    }

    /**
     * リフォーム情報入力画面格納<br>
     * <br/>
     * @param model Map リクエストパラメータを格納用
     * @param form ReformInfoForm リフォームプラン情報の入力値を格納した Form オブジェクト
     *
     */
    @SuppressWarnings("unchecked")
    private void pageFormat(Map<String, Object> model, ReformInfoForm form) {

        if ("update".equals(form.getCommand())) {

            // リフォームプラン情報を取得
        	Map<String, Object> result = (Map<String, Object>) this.reformManager.searchReform(form.getSysReformCd());
            // リフォーム詳細リスト
            model.put("dtlList", form.setDtlList((List<ReformDtl>) result.get("dtlList"), commonParameters, fileUtil));
            // リフォーム画像情報リスト
            model.put("imgList", form.setImgList((List<ReformImg>) result.get("imgList"), commonParameters, fileUtil));
        }
    }

}
