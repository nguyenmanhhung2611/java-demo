package jp.co.transcosmos.dm3.frontCore.housingDetail.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.frontCore.housingDetail.displayAdapter.HousingDetailDisplayAdapter;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;


/**
 * 物件詳細画面.
 * <p>
 * <ul>
 * <li>URL（リクエストパラメータ）からシステム物件ＣＤを取得する。</li>
 * <li>システム物件ＣＤをキーとして物件情報を取得する。</li>
 * <li>取得した値を画面に表示する。</li>
 * </ul>
 * <br>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.16	新規作成
 * </pre>
 * <p>
 * 注意事項<br>
 *
 */
public class HousingDetailCommand implements Command {

	/** 物件情報用 Model オブジェクト */
	private HousingManage housingManage;

	/** 物件詳細画面表示用 DisplayAdapter */
	private HousingDetailDisplayAdapter displayAdapter;



	
	/**
	 * 物件情報用 Model オブジェクトを設定する。<br>
	 * <br>
	 * @param housingManage 物件情報用 Model オブジェクト
	 */
	public void setHousingManage(HousingManage housingManage) {
		this.housingManage = housingManage;
	}

	/**
	 * 物件詳細画面表示用 DisplayAdapter を設定する。<br>
	 * <br>
	 * @param buildingInfoDisp 物件詳細画面用 DisplayAdapter
	 */
	public void setDisplayAdapter(HousingDetailDisplayAdapter displayAdapter) {
		this.displayAdapter = displayAdapter;
	}
	
	
	
	/**
	 * 物件詳細画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// view 層へ渡す為の model オブジェクト
		Map<String, Object> model = new HashMap<>();
		
		// システム物件ＣＤの取得
		// もし取得出来ない場合はシステムエラーとして処理する。
		String sysHousingCd = request.getParameter("sysHousingCd");
		if (StringValidateUtil.isEmpty(sysHousingCd)){
			throw new RuntimeException("system housing cd is null");
		}


		// 物件情報を取得する。
		// 該当データが存在しない場合（過去に掲載されていた物件等）、該当無し物件画面を表示する。
		Housing housing = this.housingManage.searchHousingPk(sysHousingCd);
		if (housing == null){
			return new ModelAndView("notfound");
		}
		model.put("housing", housing);


		// 使用する DisplayAdapter を model へ格納する。
		model.put("displayAdapter", this.displayAdapter);


		return new ModelAndView("success", model);
	}

}
