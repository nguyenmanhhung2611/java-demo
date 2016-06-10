package jp.co.transcosmos.dm3.webFront.inquiryGeneral.command;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryGeneralForm;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * 汎用お問合せ確認画面
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * qiao.meng	 2015.04.28    新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class InquiryGeneralConfirmCommand implements Command {

	/**
	 * 汎用お問合せ確認画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = createModel(request);
		PanaInquiryGeneralForm inputForm = (PanaInquiryGeneralForm)model.get("inputForm");

		// view 名の初期値を設定
		String viewName = "success";

		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {
			// バリデーションエラーあり
			model.put("errors", errors);
			model.put("errorsSingle", errors);
			viewName = "input";
		}

		if ("success".equals(viewName)) {
	        if (PanaCommonConstant.INQUIRY_DTL_TYPE_SEMINAR.equals(inputForm.getInquiryHeaderForm().getInquiryDtlType()[0])){
	    		DateFormat format1 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		DateFormat format =  new SimpleDateFormat("M月　d日　H:mm");
	    		inputForm.setEventDatetimeWithFormat(format.format(format1.parse(inputForm.getEventDatetime())));
	        }
    		inputForm.setInquiryText1(PanaStringUtils.encodeHtml(inputForm.getInquiryHeaderForm().getInquiryText()));
		}

		model.put("inputForm", inputForm);

		// 取得したデータをレンダリング層へ渡す
		return new ModelAndView(viewName, model);
	}



	/**
	 * リクエストパラメータから Form オブジェクトを作成する。<br/>
	 * 生成した Form オブジェクトは Map に格納して復帰する。<br/>
	 * key = フォームクラス名（パッケージなし）、Value = フォームオブジェクト <br/>
	 *
	 * @param request
	 *            HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクトを格納した Map オブジェクト
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();
        // リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaInquiryFormFactory factory = PanaInquiryFormFactory.getInstance(request);
		PanaInquiryFormFactory headerFactory = PanaInquiryFormFactory.getInstance(request);

		PanaInquiryGeneralForm inputForm = factory.createPanaInquiryGeneralForm(request);
		inputForm.setCommonInquiryForm(headerFactory.createInquiryHeaderForm(request));

		model.put("inputForm", inputForm);

		return model;

	}
}
