package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * おすすめポイント編集の入力パラメータ受取り用フォーム
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ		2015.4.10	新規作成
 *
 * 注意事項
 * この Form のバリデーションは、使用するモード（追加処理 or 更新処理）で異なるので、
 * フレームワークが提供する Validateable インターフェースは実装していない。
 * バリデーション実行時のパラメータが通常と異なるので注意する事。
 *
 * </pre>
 */
public class RecommendPointForm extends HousingForm {
    /**
     * デフォルトコンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     */
    protected RecommendPointForm() {
        super();
    }

    /**
     * コンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
     * @param　codeLookupManager　共通コード変換処理
     */
    protected RecommendPointForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
        super();
        this.lengthUtils = lengthUtils;
        this.codeLookupManager = codeLookupManager;
    }

	private static final Log log = LogFactory.getLog(RecommendPointForm.class);

	/** command パラメータ */
	private String command;

	/** アイコン情報 */
	private String[] icon;

	/** 物件種類CD */
	private String housingKindCd;

	/**
	 * command パラメータを取得する。<br/>
	 * <br/>
	 * @return command パラメータ
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * command パラメータを設定する。<br/>
	 * <br/>
	 * @param command command パラメータ
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * アイコン情報を取得する。<br/>
	 * <br/>
	 * @return アイコン情報
	 */
	public String[] getIcon() {
		return icon;
	}

	/**
	 * アイコン情報を設定する。<br/>
	 * <br/>
	 * @param icon アイコン情報
	 */
	public void setIcon(String[] icon) {
		this.icon = icon;
	}

	/**
	 * 物件種類CDを取得する。<br/>
	 * <br/>
	 * @return 物件種類CD
	 */
	public String getHousingKindCd() {
		return housingKindCd;
	}

	/**
	 * 物件種類CDを設定する。<br/>
	 * <br/>
	 * @param housingKindCd 物件種類CD
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	/**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 * @param housing Housing　を実装した問合せ情報管理用バリーオブジェクト
	 */
	public void setDefaultData(Housing housing, RecommendPointForm inputForm) {

		// 物件番号を設定する
        inputForm.setHousingCd(
        		((HousingInfo)housing.getHousingInfo().getItems().get("housingInfo")).getHousingCd());

        // 表示用物件名を設定する
        inputForm.setDisplayHousingName(
        		((HousingInfo)housing.getHousingInfo().getItems().get("housingInfo")).getDisplayHousingName());

        // アイコン情報を設定する
        String iconCd = ((HousingInfo)housing.getHousingInfo().getItems().get("housingInfo")).getIconCd();
        if(iconCd != null){
        	String[] icon = iconCd.split(",");
	        inputForm.setIcon(icon);
        }

        inputForm.setCommand("input");

	}

	/**
	 * 引数で渡されたお知らせ情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param housingInfo 値を設定する物件基本情報のバリーオブジェクト
	 *
	 */
	@Override
	public void copyToHousingInfo(HousingInfo housingInfo) {

		StringBuffer iconCd = new StringBuffer();

		if(this.icon != null){
			for (int i = 0; i < this.icon.length; i++) {
				if (!"".equals(this.icon[i])) {
					iconCd.append(this.icon[i]).append(",");
				}
			}
			// アイコン情報を設定
			if (iconCd.length() > 0) {
				housingInfo.setIconCd(iconCd.toString().substring(0, iconCd.toString().length() - 1));
			}
		} else {
			housingInfo.setIconCd(iconCd.toString());
		}
	}

	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * この Form クラスのバリデーションメソッドは、Validateable インターフェースの実装では無いので、
	 * バリデーション実行時の引数が異なるので、複数 Form をまとめてバリデーションする場合などは注意する事。<br/>
	 * <br/>
	 *
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @return 正常時 true、エラー時 false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {

		int startSize = errors.size();

		String[] icons = getIcon();

		if (icons != null) {
			for (int i = 0; i < icons.length; i++) {

				String icon = icons[i];
				// おすすめポイント
				ValidationChain iconVal = new ValidationChain("recommendPoint.input.icon", icon);

				// パターンチェック
				iconVal.addValidation(new CodeLookupValidation(this.codeLookupManager,"recommend_point_icon"));
				iconVal.validate(errors);
			}
		}

		return (startSize == errors.size());
	}
}
