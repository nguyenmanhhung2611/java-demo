package jp.co.transcosmos.dm3.webFront.housingRequest.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingRequestForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.LengthValidator;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * 物件リクエスト入力画面
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *   焦		  2015.04.22    新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingRequestInitCommand implements Command {

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 物件情報用 Model オブジェクト */
	private HousingRequestManage housingRequestManage;

	/**
	 * 物件リクエスト情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param housingRequestManage
	 *            物件リクエスト情報用 Model オブジェクト
	 */
	public void setHousingRequestManage(HousingRequestManage housingRequestManage) {
		this.housingRequestManage = housingRequestManage;
	}


	/**
	 * 共通コード変換オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/** 物件情報用 Model オブジェクト */
	private PanaCommonManage panaCommonManage;

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanaCommonManage(PanaCommonManage panaCommonManage) {
		this.panaCommonManage = panaCommonManage;
	}

	/**
	 * 物件リクエスト入力画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);

		// ページ処理用のフォームオブジェクトを作成
		PanaHousingRequestForm housingRequestForm = factory
				.createPanaHousingRequestForm(request);

		// 都道府県のチェック
		if(!StringUtils.isEmpty(housingRequestForm.getPrefCd())){

			List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
			// 都道府県の数値チェック
	        ValidationChain valPrefCd = new ValidationChain("housingRequest.input.valPrefCd", housingRequestForm.getPrefCd());

	        // 桁数チェック
	        valPrefCd.addValidation(new LengthValidator(2));

	        // 数値チェック
	        valPrefCd.addValidation(new NumericValidation());

	        valPrefCd.validate(errors);
	        if(errors.size()>0){
	        	throw new RuntimeException();
	        }
		}

		// 物件種別のチェック
		if(!StringUtils.isEmpty(housingRequestForm.getHousingKindCd())){

			List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
			// 物件種別の数値チェック
	        ValidationChain valHousingKindCd = new ValidationChain("housingRequest.input.valHousingKindCd", housingRequestForm.getHousingKindCd());

	        // 桁数チェック
	        valHousingKindCd.addValidation(new LengthValidator(2));

	        // 数値チェック
	        valHousingKindCd.addValidation(new NumericValidation());

	        valHousingKindCd.validate(errors);
	        if(errors.size()>0){
	        	throw new RuntimeException();
	        }
		}

		if("back".equals(housingRequestForm.getCommand())){

			if("confirm".equals(housingRequestForm.getModel())){
				housingRequestForm.setModel("update");
			}
			model.put("housingRequestForm", housingRequestForm);
			// 都道府県リストの設定
			List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
			model.put("prefMstList", prefMstList);

            // 取得したデータをレンダリング層へ渡す
            return new ModelAndView("success", model);
		}

		housingRequestForm.setModel("insert");

		model.put("housingRequestForm", housingRequestForm);

		// 都道府県リストの設定
		List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
		model.put("prefMstList", prefMstList);


		// 取得したデータをレンダリング層へ渡す
		return new ModelAndView("success", model);
	}

}
