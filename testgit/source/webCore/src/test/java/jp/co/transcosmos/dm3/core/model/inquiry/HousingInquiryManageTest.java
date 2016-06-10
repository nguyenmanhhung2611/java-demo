package jp.co.transcosmos.dm3.core.model.inquiry;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.inquiry.form.HousingInquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryFormFactory;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryHeader;
import jp.co.transcosmos.dm3.core.vo.InquiryHousing;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.StringUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 物件問合せ model のテストケース
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingInquiryManageTest {
	
	// 問合せヘッダ情報用DAO
	@Autowired
	private DAO<InquiryHeader> inquiryHeaderDAO;
	
	// お問合せ内容種別情報用DAO
	@Autowired
	private DAO<InquiryDtlInfo> inquiryDtlInfoDAO;
	
	// 物件問い合わせ情報 DAO
	@Autowired
	private DAO<InquiryHousing> inquiryHousingDAO;

	// 問合せフォームの Factory クラス
	@Autowired
	private InquiryFormFactory formFactory;
	
	@Autowired
	private InquiryManage inquiryManage;

	
	
	// 前処理
	@Before
	public void init() throws ParseException{
		// 問合せ情報全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.inquiryHeaderDAO.deleteByFilter(criteria);
		this.inquiryDtlInfoDAO.deleteByFilter(criteria);
	}
	
	
	
	/**
	 * パラメータで渡された Form の情報で問合せ情報を新規追加（お問合内容種別CD １個）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>formの値で登録できる事</li>
	 *     <li>お問合せヘッダのお問合せ区分が 00 である事</li>
	 *     <li>お問合せヘッダのお問合せ日時にシステム日付が設定されている事</li>
	 *     <li>お問合せヘッダの対応ステータスが 0 である事</li>
	 *     <li>お問合せヘッダの対応内容、開封日、開封者、対応完了日、対抗完了者が null である事</li>
	 *     <li>マイページユーザーID が指定されている場合、お問合せヘッダのユーザーID が設定される事</li>
	 * </ul>
	 */
	@Test
	public void addInquiryTest1() throws Exception {
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		HousingInquiryForm inquiryForm = this.formFactory.createHousingInquiryForm();
		InquiryHeaderForm inquiryHeaderForm = this.formFactory.createInquiryHeaderForm();

		// 氏名(姓)を設定
		inquiryHeaderForm.setLname("山口");
		// 氏名(名)を設定
		inquiryHeaderForm.setFname("薫");
		// 氏名・カナ(姓)を設定
		inquiryHeaderForm.setLnameKana("ヤマクチ");
		// 氏名・カナ(名)を設定
		inquiryHeaderForm.setFnameKana("カオル");
		// メールアドレスを設定
		inquiryHeaderForm.setEmail("yamakuchi@163.com");
		// 電話番号を設定
		inquiryHeaderForm.setTel("12345678");
		// お問合せ内容を設定
		inquiryHeaderForm.setInquiryText("お問合せ内容test");
		inquiryHeaderForm.setInquiryDtlType(new String[]{"1"});

		inquiryForm.setCommonInquiryForm(inquiryHeaderForm);
		// システム物件番号を設定
		inquiryForm.setSysHousingCd(new String[]{"HOU000005"});

		String inquiryId = inquiryManage.addInquiry(inquiryForm, "Test1", "Test2");

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHeader> inquiryHeaders = inquiryHeaderDAO.selectByFilter(criteria);

		List<InquiryDtlInfo> inquiryDtlInfos = inquiryDtlInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("お問合せヘッダのお問合せ区分が 00 である事", "00", inquiryHeaders.get(0).getInquiryType());
		Assert.assertEquals("お問合せヘッダのユーザーIDに、MyPage ユーザーID　が設定されている事", "Test1", inquiryHeaders.get(0).getUserId());
		Assert.assertEquals("お問合せヘッダの氏名（姓）に、Form の氏名（姓）が設定されている事", inquiryHeaderForm.getLname(), inquiryHeaders.get(0).getLname());
		Assert.assertEquals("お問合せヘッダの氏名（名）に、Form の氏名（名）が設定されている事", inquiryHeaderForm.getFname(), inquiryHeaders.get(0).getFname());
		Assert.assertEquals("お問合せヘッダの氏名・カナ（姓）に、Form の氏名・カナ（姓）が設定されている事", inquiryHeaderForm.getLnameKana(), inquiryHeaders.get(0).getLnameKana());
		Assert.assertEquals("お問合せヘッダの氏名・カナ（名）に、Form の氏名・カナ（名）が設定されている事", inquiryHeaderForm.getFnameKana(), inquiryHeaders.get(0).getFnameKana());
		Assert.assertEquals("お問合せヘッダのメールアドレスに、Form のメールアドレスが設定されている事", inquiryHeaderForm.getEmail(), inquiryHeaders.get(0).getEmail());
		Assert.assertEquals("お問合せヘッダの TEL に、Form の TEL が設定されている事", inquiryHeaderForm.getTel(), inquiryHeaders.get(0).getTel());
		Assert.assertEquals("お問合せヘッダのお問合せ内容に、Form お問合せないようが設定されている事", inquiryHeaderForm.getInquiryText(), inquiryHeaders.get(0).getInquiryText());

		// お問合せ日時は、日付部のみチェック
		String now = StringUtils.dateToString(new Date());
		String date = StringUtils.dateToString(inquiryHeaders.get(0).getInquiryDate());
		Assert.assertEquals("お問合せヘッダのお問合せ日時に、システム日付が設定されている事", now, date);

		Assert.assertEquals("お問合せヘッダの対応ステータスが 0 である事", "0", inquiryHeaders.get(0).getAnswerStatus());
		Assert.assertNull("お問合せヘッダの対応内容が null である事", inquiryHeaders.get(0).getAnswerText());
		Assert.assertNull("お問合せヘッダの開封日が null である事", inquiryHeaders.get(0).getOpenDate());
		Assert.assertNull("お問合せヘッダの開封者が null である事", inquiryHeaders.get(0).getOpenUserId());
		Assert.assertNull("お問合せヘッダの対応完了日が null である事", inquiryHeaders.get(0).getAnswerCompDate());
		Assert.assertNull("お問合せヘッダの対応完了者が null である事", inquiryHeaders.get(0).getAnswerCompUserId());
		
		Assert.assertEquals("formの値で登録できる事", inquiryDtlInfos.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("formの値で登録できる事", inquiryDtlInfos.get(0).getInquiryDtlType(), inquiryHeaderForm.getInquiryDtlType()[0]);
		
	}


	
	/**
	 * パラメータで渡された Form の情報で問合せ情報を新規追加（お問合内容種別CD １個）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>マイページユーザーID が指定されていない場合、お問合せヘッダのユーザーID が null である事</li>
	 * </ul>
	 */
	@Test
	public void addInquiryTest2() throws Exception {
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		HousingInquiryForm inquiryForm = this.formFactory.createHousingInquiryForm();
		InquiryHeaderForm inquiryHeaderForm = this.formFactory.createInquiryHeaderForm();

		// 氏名(姓)を設定
		inquiryHeaderForm.setLname("山口");
		// 氏名(名)を設定
		inquiryHeaderForm.setFname("薫");
		// 氏名・カナ(姓)を設定
		inquiryHeaderForm.setLnameKana("ヤマクチ");
		// 氏名・カナ(名)を設定
		inquiryHeaderForm.setFnameKana("カオル");
		// メールアドレスを設定
		inquiryHeaderForm.setEmail("yamakuchi@163.com");
		// 電話番号を設定
		inquiryHeaderForm.setTel("12345678");
		// お問合せ内容を設定
		inquiryHeaderForm.setInquiryText("お問合せ内容test");
		inquiryHeaderForm.setInquiryDtlType(new String[]{"1"});

		inquiryForm.setCommonInquiryForm(inquiryHeaderForm);
		// システム物件番号を設定
		inquiryForm.setSysHousingCd(new String[]{"HOU000005"});


		String inquiryId = inquiryManage.addInquiry(inquiryForm, null, "Test2");

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHeader> inquiryHeaders = inquiryHeaderDAO.selectByFilter(criteria);

		List<InquiryDtlInfo> inquiryDtlInfos = inquiryDtlInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("formの値で登録できる事", inquiryHeaders.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("formの値で登録できる事", inquiryHeaders.get(0).getInquiryText(), inquiryHeaderForm.getInquiryText());
		Assert.assertNull("MyPage ユーザーID　が null である事", inquiryHeaders.get(0).getUserId());

		Assert.assertEquals("formの値で登録できる事", inquiryDtlInfos.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("formの値で登録できる事", inquiryDtlInfos.get(0).getInquiryDtlType(), inquiryHeaderForm.getInquiryDtlType()[0]);
		
	}

	
	
	/**
	 * パラメータで渡された Form の情報で問合せ情報を新規追加（お問合内容種別CD が複数）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>複数のお問合内容種別CD が登録できる事</li>
	 *     <li>同じお問合内容種別CD は無視される事</li>
	 * </ul>
	 */
	@Test
	public void addInquiryMultiTest() throws Exception {
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		HousingInquiryForm inquiryForm = this.formFactory.createHousingInquiryForm();
		InquiryHeaderForm inquiryHeaderForm = this.formFactory.createInquiryHeaderForm();

		// 氏名(姓)を設定
		inquiryHeaderForm.setLname("山口");
		// 氏名(名)を設定
		inquiryHeaderForm.setFname("薫");
		// 氏名・カナ(姓)を設定
		inquiryHeaderForm.setLnameKana("ヤマクチ");
		// 氏名・カナ(名)を設定
		inquiryHeaderForm.setFnameKana("カオル");
		// メールアドレスを設定
		inquiryHeaderForm.setEmail("yamakuchi@163.com");
		// 電話番号を設定
		inquiryHeaderForm.setTel("12345678");
		// お問合せ内容を設定
		inquiryHeaderForm.setInquiryText("お問合せ内容test");
		// お問合せ内容種別CD　（複数）
		inquiryHeaderForm.setInquiryDtlType(new String[]{"1", "1", "2"});

		inquiryForm.setCommonInquiryForm(inquiryHeaderForm);
		// システム物件番号を設定
		inquiryForm.setSysHousingCd(new String[]{"HOU000004"});


		String inquiryId = inquiryManage.addInquiry(inquiryForm, "Test", "Test");


		// お問合せヘッダの確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHeader> inquiryHeaders = inquiryHeaderDAO.selectByFilter(criteria);

		Assert.assertEquals("formの値で登録できる事", inquiryHeaders.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("formの値で登録できる事", inquiryHeaders.get(0).getInquiryText(), inquiryHeaderForm.getInquiryText());


		// お問合せ内容種別情報の確認
		criteria.addOrderByClause("inquiryDtlType");
		List<InquiryDtlInfo> inquiryDtlInfos = inquiryDtlInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("重複したお問合せ内容種別が排除されている事", 2, inquiryDtlInfos.size());
		Assert.assertEquals("重複したお問合せ内容種別が排除されている事", inquiryDtlInfos.get(0).getInquiryDtlType(), inquiryHeaderForm.getInquiryDtlType()[0]);
		Assert.assertEquals("重複したお問合せ内容種別が排除されている事", inquiryDtlInfos.get(1).getInquiryDtlType(), inquiryHeaderForm.getInquiryDtlType()[2]);


		// 物件問合せ情報の確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHousing> inquiryHousings = inquiryHousingDAO.selectByFilter(criteria);

		Assert.assertEquals("物件問い合わせ情報の件数が正しい事", 1, inquiryHousings.size());
		Assert.assertEquals("form の値で物件問合せ情報が登録されている事", "HOU000004", inquiryHousings.get(0).getSysHousingCd());

	}

	
	
	/**
	 * パラメータで渡された Form の情報で問合せ情報を新規追加（お問合内容種別CD が null の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>内容種別CD が null でも、お問合せヘッダ情報が登録できる事</li>
	 *     <li>内容種別CD が null の場合、お問合せ内容種別情報のデータが空である事</li>
	 *     <li>内容種別CD が null でも、お問合せ物件情報が登録できる事</li>
	 * </ul>
	 */
	@Test
	public void addInquiryNonDtlTest1() throws Exception {
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		HousingInquiryForm inquiryForm = this.formFactory.createHousingInquiryForm();
		InquiryHeaderForm inquiryHeaderForm = this.formFactory.createInquiryHeaderForm();

		// 氏名(姓)を設定
		inquiryHeaderForm.setLname("山口");
		// 氏名(名)を設定
		inquiryHeaderForm.setFname("薫");
		// 氏名・カナ(姓)を設定
		inquiryHeaderForm.setLnameKana("ヤマクチ");
		// 氏名・カナ(名)を設定
		inquiryHeaderForm.setFnameKana("カオル");
		// メールアドレスを設定
		inquiryHeaderForm.setEmail("yamakuchi@163.com");
		// 電話番号を設定
		inquiryHeaderForm.setTel("12345678");
		// お問合せ内容を設定
		inquiryHeaderForm.setInquiryText("お問合せ内容test");
		// お問合せ内容種別CD　（null）
		inquiryHeaderForm.setInquiryDtlType(null);

		inquiryForm.setCommonInquiryForm(inquiryHeaderForm);
		// システム物件番号を設定
		inquiryForm.setSysHousingCd(new String[]{"HOU000001"});

		String inquiryId = inquiryManage.addInquiry(inquiryForm, "Test", "Test");


		// お問合せヘッダの確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHeader> inquiryHeaders = inquiryHeaderDAO.selectByFilter(criteria);

		Assert.assertEquals("formの値で登録できる事", inquiryHeaders.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("formの値で登録できる事", inquiryHeaders.get(0).getInquiryText(), inquiryHeaderForm.getInquiryText());

		
		// お問合せ内容種別情報の確認
		criteria.addOrderByClause("inquiryDtlType");
		List<InquiryDtlInfo> inquiryDtlInfos = inquiryDtlInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("内容種別CD が null の場合、お問合せ内容種別情報のデータが空である事", 0, inquiryDtlInfos.size());

		
		// 物件問合せ情報の確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHousing> inquiryHousings = inquiryHousingDAO.selectByFilter(criteria);

		Assert.assertEquals("物件問い合わせ情報の件数が正しい事", 1, inquiryHousings.size());
		Assert.assertEquals("form の値で物件問合せ情報が登録されている事", "HOU000001", inquiryHousings.get(0).getSysHousingCd());

	}

	
	
	/**
	 * パラメータで渡された Form の情報で問合せ情報を新規追加（お問合内容種別CD が 空の配列）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>内容種別CD が空の配列でも、お問合せヘッダ情報が登録できる事</li>
	 *     <li>内容種別CD が空の配列の場合、お問合せ内容種別情報のデータが空である事</li>
	 *     <li>内容種別CD が空の配列でも、お問合せ物件情報が登録できる事</li>
	 * </ul>
	 */
	@Test
	public void addInquiryNonDtlTest2() throws Exception {
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		HousingInquiryForm inquiryForm = this.formFactory.createHousingInquiryForm();
		InquiryHeaderForm inquiryHeaderForm = this.formFactory.createInquiryHeaderForm();

		// 氏名(姓)を設定
		inquiryHeaderForm.setLname("山口");
		// 氏名(名)を設定
		inquiryHeaderForm.setFname("薫");
		// 氏名・カナ(姓)を設定
		inquiryHeaderForm.setLnameKana("ヤマクチ");
		// 氏名・カナ(名)を設定
		inquiryHeaderForm.setFnameKana("カオル");
		// メールアドレスを設定
		inquiryHeaderForm.setEmail("yamakuchi@163.com");
		// 電話番号を設定
		inquiryHeaderForm.setTel("12345678");
		// お問合せ内容を設定
		inquiryHeaderForm.setInquiryText("お問合せ内容test");
		// お問合せ内容種別CD （空の配列）
		inquiryHeaderForm.setInquiryDtlType(new String[0]);

		inquiryForm.setCommonInquiryForm(inquiryHeaderForm);
		// システム物件番号を設定
		inquiryForm.setSysHousingCd(new String[]{"HOU000002"});


		String inquiryId = inquiryManage.addInquiry(inquiryForm, "Test", "Test");


		// お問合せヘッダの確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHeader> inquiryHeaders = inquiryHeaderDAO.selectByFilter(criteria);

		Assert.assertEquals("formの値で登録できる事", inquiryHeaders.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("formの値で登録できる事", inquiryHeaders.get(0).getInquiryText(), inquiryHeaderForm.getInquiryText());
		
		
		// お問合せ内容種別情報の確認
		criteria.addOrderByClause("inquiryDtlType");
		List<InquiryDtlInfo> inquiryDtlInfos = inquiryDtlInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("内容種別CD が空の配列の場合、お問合せ内容種別情報のデータが空である事", 0, inquiryDtlInfos.size());


		// 物件問合せ情報の確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHousing> inquiryHousings = inquiryHousingDAO.selectByFilter(criteria);

		Assert.assertEquals("物件問い合わせ情報の件数が正しい事", 1, inquiryHousings.size());
		Assert.assertEquals("form の値で物件問合せ情報が登録されている事", "HOU000002", inquiryHousings.get(0).getSysHousingCd());

	}


	
	/**
	 * パラメータで渡された Form の情報で問合せ情報を新規追加（お問合内容種別CD 配列内が空文字列）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>内容種別CD が空文字列でも、お問合せヘッダ情報が登録できる事</li>
	 *     <li>内容種別CD が空文字列の場合、お問合せ内容種別情報のデータが空である事</li>
	 *     <li>内容種別CD が空文字列でも、お問合せ物件情報が登録できる事</li>
	 * </ul>
	 */
	@Test
	public void addInquiryNonDtlTest3() throws Exception {
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		HousingInquiryForm inquiryForm = this.formFactory.createHousingInquiryForm();
		InquiryHeaderForm inquiryHeaderForm = this.formFactory.createInquiryHeaderForm();

		// 氏名(姓)を設定
		inquiryHeaderForm.setLname("山口");
		// 氏名(名)を設定
		inquiryHeaderForm.setFname("薫");
		// 氏名・カナ(姓)を設定
		inquiryHeaderForm.setLnameKana("ヤマクチ");
		// 氏名・カナ(名)を設定
		inquiryHeaderForm.setFnameKana("カオル");
		// メールアドレスを設定
		inquiryHeaderForm.setEmail("yamakuchi@163.com");
		// 電話番号を設定
		inquiryHeaderForm.setTel("12345678");
		// お問合せ内容を設定
		inquiryHeaderForm.setInquiryText("お問合せ内容test");
		// お問合せ内容種別CD （空文字列）
		inquiryHeaderForm.setInquiryDtlType(new String[]{""});

		inquiryForm.setCommonInquiryForm(inquiryHeaderForm);
		// システム物件番号を設定
		inquiryForm.setSysHousingCd(new String[]{"HOU000003"});


		String inquiryId = inquiryManage.addInquiry(inquiryForm, "Test", "Test");

		
		// お問合せヘッダの確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHeader> inquiryHeaders = inquiryHeaderDAO.selectByFilter(criteria);

		Assert.assertEquals("formの値で登録できる事", inquiryHeaders.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("formの値で登録できる事", inquiryHeaders.get(0).getInquiryText(), inquiryHeaderForm.getInquiryText());


		// お問合せ内容種別情報の確認
		criteria.addOrderByClause("inquiryDtlType");
		List<InquiryDtlInfo> inquiryDtlInfos = inquiryDtlInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("重複したお問合せ内容種別が排除されている事", 0, inquiryDtlInfos.size());


		// 物件問合せ情報の確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHousing> inquiryHousings = inquiryHousingDAO.selectByFilter(criteria);

		Assert.assertEquals("物件問い合わせ情報の件数が正しい事", 1, inquiryHousings.size());
		Assert.assertEquals("form の値で物件問合せ情報が登録されている事", "HOU000003", inquiryHousings.get(0).getSysHousingCd());
	}

}
