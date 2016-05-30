package jp.co.transcosmos.dm3.core.model.csvMaster;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.CsvMasterManage;
import jp.co.transcosmos.dm3.core.model.csvMaster.form.AddressCsvForm;
import jp.co.transcosmos.dm3.core.model.csvMaster.form.CsvUploadFormFactory;
import jp.co.transcosmos.dm3.core.util.CsvStreamReader;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.ZipMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.transaction.RequestScopeDataSource;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.NotExistsInCriteriaValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class AddressMasterManageImpl implements CsvMasterManage {

	private static final Log log = LogFactory.getLog(AddressMasterManageImpl.class);

	/** VO のインスタンスを生成する場合のファクトリー */
	protected ValueObjectFactory valueObjectFactory;

	/** 都道府県マスタ DAO */
	protected DAO<PrefMst> prefMstDAO;

	/** 市区町村マスタ DAO */
	protected DAO<AddressMst> addressMstDAO;
	
	/** 郵便番号マスタ DAO */
	protected DAO<ZipMst> zipMstDAO;

	/** 郵便番号CSV の加工した値を格納する Form を生成する Factory */
	protected CsvUploadFormFactory formFactory;

	/** CSV エンコード文字列 */
	protected String csvEncode = "ms932";

	/** 共通パラメーターオブジェクト */
	protected CommonParameters commonParameters;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;
	
	/**
	 * RequestScopeDataSource の Bean ID 名<br/>
	 * 手動でトランザクションを制御する場合、このプロパティに設定されている Bean ID で処理する。<br/>
	 * 通常は、CommonsParameter に設定されている値を使用するが、このプロパティに値が設定
	 * されている場合、そちらの値を優先する。<br/>
	 */
	protected String requestScopeDataSourceId = null;

	

	/**
	 * バリーオブジェクトのインスタンスを生成するファクトリーを設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory バリーオブジェクトのファクトリー
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * 都道府県マスタ DAO を設定する。<br/>
	 * <br/>
	 * @param prefMstDAO　都道府県マスタ　DAO
	 */
	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}

	/**
	 * 市区町村マスタ DAO を設定する。<br/>
	 * <br/>
	 * @param addressMstDAO 市区町村マスタ DAO
	 */
	public void setAddressMstDAO(DAO<AddressMst> addressMstDAO) {
		this.addressMstDAO = addressMstDAO;
	}

	/**
	 * 郵便番号マスタ DAO を設定する。<br/>
	 * <br/>
	 * @param zipMstDAO 郵便番号マスタ DAO
	 */
	public void setZipMstDAO(DAO<ZipMst> zipMstDAO) {
		this.zipMstDAO = zipMstDAO;
	}

	/**
	 * 郵便番号CSV の加工した値を格納する Form を生成する Factory を設定する。<br/>
	 * <br/>
	 * @param formFactory Form の Factory
	 */
	public void setFormFactory(CsvUploadFormFactory formFactory) {
		this.formFactory = formFactory;
	}

	/**
	 * CSV の文字コードを変更する場合、このプロパティに設定する。<br/>
	 * 未設定の場合、初期値として ms932 が使用される。<br/>
	 * <br/>
	 * @param csvEncode CSV ファイルの文字コード
	 */
	public void setCsvEncode(String csvEncode) {
		this.csvEncode = csvEncode;
	}

	/**
	 * 共通パラメーターオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters 共通パラメーターオブジェクト
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * 共通コード変換処理を設定する。<br/>
	 * <br/>
	 * @param codeLookupManager　共通コード変換処理
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * RequestScopeDataSource の Bean ID 名を設定する。<br/>
	 * 通常、commonParameters で設定されている値を使用するので、このプロパティを使用する事はない。<br/>
	 * <br/>
	 * @param requestScopeDataSourceId Bean ID
	 */
	public void setRequestScopeDataSourceId(String requestScopeDataSourceId) {
		this.requestScopeDataSourceId = requestScopeDataSourceId;
	}



	@Override
	public boolean csvLoad(InputStream inputStream, String editUserId, List<ValidationFailure> errors)
			throws IOException {

		// 追加された市区町村CD の Set オブジェクト
		Set<String> addAddressCdSet = new HashSet<>();
		
		//　住所マスタを全件削除する。 　（郵便番号マスタの削除は、整合性制約に委ねる。）
		DAOCriteria criteria = new DAOCriteria();
		this.addressMstDAO.deleteByFilter(criteria);		


		// 現在のシステム日付を取得（ＤＢ更新時のタイムスタンプとして使用。）
		Date currentDate = new Date();


		// アップロードされた CSV ファイルを読込む
		InputStreamReader stream = new InputStreamReader(inputStream, this.csvEncode);

		try (CsvStreamReader reader = new CsvStreamReader(stream)){

			int listCnt = 0;				// 行番号
			String oldPrefCd = "";			// １行前の都道府県CD


			reader.open();

			Map<Integer, String> csvData;	// 取得した CSV DATA
			while ((csvData = reader.getRowData()) != null){

				++listCnt;

				// 項目数が正しくない場合
				if(csvData.size() != 15){
					errors.add(new ValidationFailure("csvListCnt", String.valueOf(listCnt), null,null));
					errors.add(new ValidationFailure("csvFormat", null, null,null));
					manualRollback();
					return false;
				}


				// CSV の値を設定した Form を生成する。
				AddressCsvForm addressCsvForm = createAddressForm(csvData);

				// フォーマットチェック
				List<ValidationFailure> subErrors = new ArrayList<ValidationFailure>();
				addressCsvForm.validate(subErrors);
				
				// PrefCd が取得できて、かつ、都道府県CD が変わった場合、都道府県CD のバリデーションを行う。
				// 市区町村マスタのデータ量が多いので、負荷を軽減する為、最低限のチェックにする。
				String prefCd = addressCsvForm.getPrefCd();
				if (!StringValidateUtil.isEmpty(prefCd) && !oldPrefCd.equals(prefCd)){

					oldPrefCd = prefCd;

					// 都道府県CD が存在するかのバリデーションを実行
					DAOCriteria prefCri = new DAOCriteria();
					prefCri.addWhereClause("prefCd", prefCd, DAOCriteria.EQUALS, false);

					ValidationChain valPrefCd = new ValidationChain("stationCsv.prefCd", prefCd);
					valPrefCd.addValidation(new NotExistsInCriteriaValidation(this.prefMstDAO, prefCri));
					valPrefCd.validate(subErrors);

				}

				// エラーがあった場合、処理を中断する。
				if (subErrors.size() > 0) {
					errors.add(new ValidationFailure("csvListCnt", String.valueOf(listCnt), null,null));
					errors.addAll(subErrors);
					manualRollback();
					return false;
				}			


				// CSV の「更新の表示」（日本郵便が提供する CSV の14番目のフィールドの値）が２の場合、
				// 「廃止データ」を意味する。
				// 差分以外でその様なデータが含まれるとは思えないが、一応チェックしておく。
				if ("2".equals(addressCsvForm.getUpdFlg())){
					log.warn("zip csv 12 column value is 2. skipped.");
					continue;
				}


				// 未登録の市区町村CD の場合、新規追加する。
				if (!addAddressCdSet.contains(addressCsvForm.getAddressCd())){

					// エリア検索での除外設定がされている市区町村CD かをチェックする。
					// もし除外設定されている場合は、市区町村マスタの area_not_dsp に 1 を設定する。
					// このフラグがセットされた市区町村情報は、エリア検索では表示対象外となる。
					isException(addressCsvForm);

					// 一度登録した市区町村CD は、Set オブジェクトに退避する。
					addAddressCdSet.add(addressCsvForm.getAddressCd());

					AddressMst addressMst = createAddressMst(addressCsvForm, editUserId, currentDate);
					this.addressMstDAO.insert(new AddressMst[]{addressMst});
				}

				// 郵便番号マスタを新規追加する。
				ZipMst zipMst = createZipMst(addressCsvForm, editUserId, currentDate);
				try {
					this.zipMstDAO.insert(new ZipMst[]{zipMst});

				} catch (DuplicateKeyException e){
					// 郵便番号 CSV の郵便番号は、一部の特殊な番号の場合、重複したレコードが存在する。
					// その為、主キーの重複データは２個目を無視する。
					log.warn("The zip code of zip code CSV overlapped. (" + addressCsvForm.getZip() + ")");
				}


			}
		}

		// 登録データが1件もない場合
		if(addAddressCdSet.size()==0){
			errors.add(new ValidationFailure("csvNoData", null, null,null));
			manualRollback();
			return false;
		}

		return true;
	}


	
	/**
	 * 郵便番号CSV の値を設定した AddressCsvForm オブジェクト生成する。<br/>
	 * <br/>
	 * @param csvData CSV データー
	 * 
	 * @return　Form オブジェクト
	 */
	protected AddressCsvForm createAddressForm(Map<Integer, String> csvData){

		// 市区町村CD を取得する。
		String addressCd = csvData.get(1);

		AddressCsvForm form = this.formFactory.createAddressCsvForm();

		form.setAddressCd(addressCd);						// 市区町村CD　（全国地方公共団体コード）
		form.setAddressName(csvData.get(8));				// 市区町村名
		form.setZip(csvData.get(3));						// 郵便番号
		form.setUpdFlg(csvData.get(14));					// 更新の表示

		// 市区町村CD として、５文字の文字列が取得できた場合、先頭２桁を都道府県CD として設定する。
		if (!StringValidateUtil.isEmpty(addressCd) && addressCd.length() == 5){
			form.setPrefCd(addressCd.substring(0, 2));
		}

		return form;

	}



	/**
	 * CSV の値を設定した市区町村マスタの Value オブジェクトのインスタンスを作成する。
	 * <br/>
	 * @param csvForm　加工された CSV の情報
	 * @param editUserId ログインユーザーID（更新情報用）
	 * @param currentDate 現在の時間
	 * 
	 * @return addressMst CSV の値が格納された addressMst のインスタンス
	 */
	protected AddressMst createAddressMst(AddressCsvForm csvForm, String editUserId, Date currentDate){

		AddressMst addressMst = (AddressMst) this.valueObjectFactory.getValueObject("AddressMst");
			
		addressMst.setAddressCd(csvForm.getAddressCd());			// 市区町村CD
		addressMst.setPrefCd(csvForm.getPrefCd());					// 都道府県CD
		addressMst.setAddressName(csvForm.getAddressName());		// 市区町村名
		addressMst.setAreaNotDsp(csvForm.getAreaNotDsp());			// エリア検索非表示フラグ
		addressMst.setInsDate(currentDate);							// 登録日
		addressMst.setInsUserId(editUserId);						// 登録者
		addressMst.setUpdDate(currentDate);							// 更新日
		addressMst.setUpdUserId(editUserId);						// 更新者

		return addressMst;
	}

	
	
	/**
	 * CSV の値を設定した郵便番号マスタの Value オブジェクトのインスタンスを作成する。
	 * <br/>
	 * @param csvForm　加工された CSV の情報
	 * @param editUserId ログインユーザーID（更新情報用）
	 * @param currentDate 現在の時間
	 * 
	 * @return zipMst CSV の値が格納された zipMst のインスタンス
	 */
	protected ZipMst createZipMst(AddressCsvForm csvForm, String editUserId, Date currentDate){

		ZipMst zipMst = (ZipMst) this.valueObjectFactory.getValueObject("ZipMst");

		zipMst.setZip(csvForm.getZip());
		zipMst.setPrefCd(csvForm.getPrefCd());
		zipMst.setAddressCd(csvForm.getAddressCd());

		return zipMst;
	}



	/**
	 * エリア検索時に非表示にする市区町村CD かをチェックする。<br/>
	 * もし非表示設定されている場合、CSV フォームのエリア検索非表示フラグに 1 （非表示）を設定する。<br/>
	 * 非表示設定されていない場合は何もしない。　（プロパティの初期値が 0 な為。）<br/>
	 * <br/>
	 * @param csvForm 加工された CSV の情報
	 * @return
	 */
	public void isException(AddressCsvForm csvForm) {
		
		// 市区町村CD を Key として、除外設定されている市区町村名を取得する。
		String addressName = this.codeLookupManager.lookupValue("addressMstException", csvForm.getAddressCd());
		
		if (!StringValidateUtil.isEmpty(addressName)){
			csvForm.setAreaNotDsp("1");
		}

	}



	/**
	 * 手動でロールバック処理を行う。<br/>
	 * <br/>
	 */
	protected void manualRollback(){

		// 共通パラメータオブジェクトから値を取得する。
		String beanId = this.commonParameters.getRequestScopeDataSourceId();
		// もし、このクラスのプロパティに値が設定されていた場合、そちらの値を優先する。
		if (!StringValidateUtil.isEmpty(this.requestScopeDataSourceId)){
			beanId = this.requestScopeDataSourceId;
		}

		// ロールバック処理
		RequestScopeDataSource.closeCurrentTransaction(beanId, true);
	}

}
