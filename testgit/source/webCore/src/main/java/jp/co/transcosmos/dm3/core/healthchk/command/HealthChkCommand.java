package jp.co.transcosmos.dm3.core.healthchk.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.dao.ReadOnlyDAO;


/**
 * ヘルスチェッククラス.
 * <p>
 * 指定された DAO を使用してデータ件数を取得し、結果が１件以上だった場合、OK ページを復帰する。<br/>
 * それ以外の場合は例外をスローする。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.22	Shamaison のソースを参考に作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class HealthChkCommand implements Command {

	/**
	 * ヘルスチェックで使用する DAO<br/>
	 * 何でも良いが、件数をカウントするので件数の少ない DAO を使用する事。
	 */
	protected ReadOnlyDAO<?> checkDAO;

	/**
	 * ヘルスチェックで使用する DAO を設定する。<br/>
	 * <br/>
	 * @param checkDAO　ヘルスチェックで使用する DAO
	 */
	public void setCheckDAO(ReadOnlyDAO<?> checkDAO) {
		this.checkDAO = checkDAO;
	}



	/**
	 * ヘルスチェック用リクエストに対する応答処理<br/>
	 * チェック用に指定された DAO を使用してレコード件数を取得し、該当件数が取得出来ない場合は例外をスローする。<br/>
	 * チェック用 DAO が参照するテーブルにはデータが存在する事。　また、大量のデータが存在するテーブルは負荷がかかる
	 * ので避ける事。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @return ModelAndView のインスタンス
	 * 
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// レコード件数を取得する。
		// 本来は件数をチェックする必要性は無い（例外がスローされる為）が、一応、件数をチェックする。
		if (this.checkDAO.getRowCountMatchingFilter(null) <=0 ){
			throw new RuntimeException("check table is empty");
		}

		return new ModelAndView("success");
	}

}
