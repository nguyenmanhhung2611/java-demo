package jp.co.transcosmos.dm3.webAdmin.housingImageInfo.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * 物件画像情報の追加、変更、削除処理.
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、物件画像情報を新規登録する。</li>
 * <li>また、公開先が特定個人の場合、リフォーム公開先情報も新規登録する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、物件画像情報を更新する。</li>
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
 *
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.12	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class HousingImageInfoCompCommand implements Command {

    /** Housingメンテナンスを行う Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * Housingメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            Housingメンテナンスの model オブジェクト
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

    	Map<String, Object> model = new HashMap<String, Object>();

        // リクエストパラメータを取得して Form オブジェクトを作成する。
 		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);

 		// リクエストパラメータを格納した form オブジェクトを生成する。
 		PanaHousingImageInfoForm form = factory.createPanaHousingImageInfoForm(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

        // ログインユーザーの情報を取得
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

        // ユーザIDを取得
        String userId = String.valueOf(loginUser.getUserId());

        String command = form.getCommand();
        String sysHousingCd = form.getSysHousingCd();
		if (command != null && command.equals("redirect")) {
			model.put("sysHousingCd", sysHousingCd);
			return new ModelAndView("comp", model);
		}

		// 事前チェック:物件基本情報テーブルの読込
		Housing housingresults = this.panaHousingManager.searchHousingPk(
				form.getSysHousingCd(), true);

		if (housingresults == null) {
			// データの存在しない場合,メッセージ："｛0｝物件情報が存在しない"表示
			throw new NotFoundException();
		}

		List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageInfoList = housingresults
				.getHousingImageInfos();

		// 画面項目を設定する処理
    	// 画像タイプ
    	String[] imageType = new String[housingImageInfoList.size()];
        // 閲覧権限
    	String[] roleId = new String[housingImageInfoList.size()];

        for (int i = 0; i < housingImageInfoList.size(); i++) {
            imageType[i] = String.valueOf(((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(i)).getImageType());
            roleId[i] = String.valueOf(((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(i)).getRoleId());
        }

        // バリデーション処理
        Validateable validateableForm = (Validateable) form;
        // エラーメッセージ用のリストを作成
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        // バリデーションを実行
        if (!validateableForm.validate(errors)) {
            // エラー処理
            // エラーオブジェクトと、フォームオブジェクトをModelAndView に渡している
            model.put("errors", errors);
            form.setOldRoleId(roleId);
            form.setOldImageType(imageType);
            model.put("housingImageInfoForm", form);
            return new ModelAndView("validationError", model);
        }

        // 正常処理
 		 //「年月日」フォルダ作成用
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        // 仮フォルダ名　（日付部分）
        form.setTempDate(dateFormat.format(new Date()));

        if ("insert".equals(form.getCommand())) {
        	form.setRoleId(form.getAddRoleId());
        	form.setImageType(form.getAddImageType());
        	form.setSortOrder(form.getAddSortOrder());
        	form.setFileName(form.getAddHidFileName());
        	form.setImgComment(form.getAddImgComment());
        	// リフォーム詳細情報の新規追加
            this.panaHousingManager.addHousingImg(form, userId);
        } else if ("update".equals(form.getCommand())) {
            // リフォーム詳細情報の更新処理
            this.panaHousingManager.updHousingImg(form, userId);
        }

        // フォームオブジェクトのみをModelAndView に渡している
        model.put("housingImageInfoForm", form);
        return new ModelAndView("success", model);

    }
}
