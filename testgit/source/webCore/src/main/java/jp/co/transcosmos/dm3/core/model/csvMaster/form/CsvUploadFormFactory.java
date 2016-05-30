package jp.co.transcosmos.dm3.core.model.csvMaster.form;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * CSV ファイルアップロード用 Form の Factory クラス.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.05	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 * 
 */
public class CsvUploadFormFactory {

	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "csvUploadFormFactory";

	/** レングスバリデーション用ユーティリティ */
    protected LengthValidationUtils lengthUtils;

    /** 共通パラメータオブジェクト */
    protected CommonParameters commonParameters;
    
    /**
     * アップロードファイルの最大サイズ　（デフォルト 4M）<br/>
     * このサイズを超えた場合、作業フォルダへの書き込みが発生する。<br/>
     */
    protected int maxFileSize = 4 * 1024 * 1024;
    
    
    
	/**
	 * レングスバリデーション用ユーティリティを設定する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーション用ユーティリティ
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters 共通パラメータオブジェクト
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * アップロードファイルの最大サイズを設定する。<br/>
	 * デフォルトでは 4M が設定されており、このサイズを超えると作業フォルダへの書き込みが発生する。<br/>
	 * <br/>
	 * @param maxFileSize アップロードファイルの最大サイズ
	 */
	public void setMaxFileSize(int maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	
	
	/**
	 * CsvUploadFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 csvUploadFormFactory で定義された CsvUploadFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、csvUploadFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return CsvUploadFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static CsvUploadFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (CsvUploadFormFactory)springContext.getBean(CsvUploadFormFactory.FACTORY_BEAN_ID);
	}

	
	
	/**
	 * アップロードされた CSV 情報を格納する空の CsvUploadForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の CsvUploadForm インスタンス 
	 */
	public CsvUploadForm createCsvUploadForm(){
		return new CsvUploadForm();
	}

	
	
	/**
	 * アップロードされた CSV 情報を格納する CsvUploadForm のインスタンスを生成する。<br/>
	 * CsvUploadFormFactory には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した CsvUploadForm インスタンス 
	 */
	public CsvUploadForm createCsvUploadForm(HttpServletRequest request){
		CsvUploadForm form = createCsvUploadForm();
		FormPopulator.populateFormBeanFromRequest(request, form, this.maxFileSize, new File(this.commonParameters.getUploadWorkPath()));	
		return form; 
	}



	/**
	 * アップロードされた駅情報CSV から加工した値を格納する空の StationCsvForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の StationCsvForm インスタンス 
	 */
	public StationCsvForm createStationCsvForm(){
		return new StationCsvForm(this.lengthUtils);
	}


	
	/**
	 * アップロードされた郵便番号情報CSV から加工した値を格納する空の AddressCsvForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の AddressCsvForm インスタンス 
	 */
	public AddressCsvForm createAddressCsvForm(){
		return new AddressCsvForm(this.lengthUtils);
	}
	
}
