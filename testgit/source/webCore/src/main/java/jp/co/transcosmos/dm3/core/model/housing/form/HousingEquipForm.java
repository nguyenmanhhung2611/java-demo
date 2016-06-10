package jp.co.transcosmos.dm3.core.model.housing.form;

import java.util.List;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * 物件設備情報メンテナンスの入力パラメータ受取り用フォーム.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	新規作成
 * H.Mizuno		2015.04.06	こだわり条件CD から設備CD へ変更　（誤りを訂正）
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class HousingEquipForm implements Validateable {

	/** システム物件CD */
	private String sysHousingCd;
	
	/** 設備CD */
	private String[] equipCd;



	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected HousingEquipForm() {
		super();
	}


	
	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @return 正常時 true、エラー時 false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {

		int startSize = errors.size();

		// システム物件CD のバリデーションは、Command 側で対応するので From 側では実装しない。
		// （更新時にパラメータが欠落している場合や、該当データが無い場合は例外をスローする。）

		// 設備CD のバリデーション
		validEquipCd(errors);

		return (startSize == errors.size());
	}
	
	
	
	// 設備CD のバリデーション
	public void validEquipCd(List<ValidationFailure> errors){

		// 通常選択入力なので、ここでのバリデーションは行わない。
		// エラーが出るのはパラメータ改竄時のみ。

		// 実質的なバリデーションは、DB更新時の整合性制約で行う。
	}



	/**
	 * システム物件CD を取得する。<br/>
	 * <br/>
	 * @return システム物件CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * システム物件CD を設定する。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * 設備CD を取得する。<br/>
	 * <br/>
	 * @return 設備CD
	 */
	public String[] getEquipCd() {
		return equipCd;
	}

	/**
	 * 設備CD を設定する。<br/>
	 * <br/>
	 * @param equipCd 設備CD
	 */
	public void setEquipCd(String[] equipCd) {
		this.equipCd = equipCd;
	}

}
