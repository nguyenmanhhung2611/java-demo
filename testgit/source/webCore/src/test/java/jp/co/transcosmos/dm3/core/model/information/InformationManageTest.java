package jp.co.transcosmos.dm3.core.model.information;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.core.vo.InformationTarget;
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
 * お知らせ情報 model のテストケース
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class InformationManageTest {
	
	/** お知らせ情報更新用 DAO */
	@Autowired
	private DAO<Information> informationDAO;

	/** お知らせ公開先情報更新用 DAO */
	@Autowired
	private DAO<InformationTarget> informationTargetDAO;
	
	@Autowired
	private InformationManage informationManage;
	
	@Autowired
	private InformationFormFactory informationFormFactory;
	
	// 前処理
	@Before
	public void init() throws ParseException{
		// お知らせ情報全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.informationTargetDAO.deleteByFilter(criteria);
		this.informationDAO.deleteByFilter(criteria);
		initTestData();
	}
	
	// テストデータ作成
	private void initTestData() throws ParseException{

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		Information information;
		InformationTarget informationTarget;
		information = new Information();
		information.setInformationNo("0000000000001");
		information.setTitle("test01");
		information.setDspFlg("1");
		information.setInformationMsg("test内容01");
		information.setInsDate(StringUtils.stringToDate("2015/04/30 23:59:59", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});

		information = new Information();
		information.setInformationNo("0000000000002");
		information.setTitle("test02");
		information.setDspFlg("2");
		information.setInformationMsg("test内容02");
		information.setInsDate(StringUtils.stringToDate("2015/05/01 00:00:00", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});

		informationTarget = new InformationTarget();
		informationTarget.setInformationNo("0000000000002");
		informationTarget.setUserId("00000000000000000002");
		informationTargetDAO.insert(new InformationTarget[]{informationTarget});

		information = new Information();
		information.setInformationNo("0000000000003");
		information.setTitle("test03");
		information.setDspFlg("0");
		information.setInformationMsg("test内容03");
		information.setStartDate(sdf.parse("2015/01/01"));
		information.setEndDate(sdf.parse("2016/01/01"));
		information.setInsDate(StringUtils.stringToDate("2015/05/01 23:59:59", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000004");
		information.setTitle("test04");
		information.setDspFlg("2");
		information.setInformationMsg("test内容04");
		informationTarget.setUserId("00000000000000000004");
		information.setInsDate(StringUtils.stringToDate("2015/06/01 00:00:00", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000005");
		information.setTitle("test05");
		information.setDspFlg("1");
		information.setInformationMsg("test内容05");
		information.setStartDate(sdf.parse("2015/01/01"));
		information.setInsDate(StringUtils.stringToDate("2015/06/01 23:59:59", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000006");
		information.setTitle("test06");
		information.setDspFlg("1");
		information.setInformationMsg("test内容06");
		information.setStartDate(sdf.parse("2016/01/01"));
		information.setInsDate(StringUtils.stringToDate("2015/05/31 00:00:00", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000007");
		information.setTitle("test07");
		information.setDspFlg("1");
		information.setInformationMsg("test内容07");
		information.setEndDate(sdf.parse("2016/01/01"));
		information.setInsDate(StringUtils.stringToDate("2015/05/31 23:59:59", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000008");
		information.setTitle("test08");
		information.setDspFlg("1");
		information.setInformationMsg("test内容08");
		information.setEndDate(sdf.parse("2015/01/01"));
		information.setInsDate(StringUtils.stringToDate("2015/05/20 00:00:00", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000009");
		information.setTitle("test09");
		information.setDspFlg("1");
		information.setInformationMsg("test内容09");
		information.setStartDate(sdf.parse("2015/01/01"));
		information.setEndDate(sdf.parse("2016/01/01"));
		information.setInsDate(StringUtils.stringToDate("2015/06/20 00:00:00", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000010");
		information.setTitle("test10");
		information.setDspFlg("1");
		information.setInformationMsg("test内容10");
		information.setStartDate(sdf.parse("2015/01/01"));
		information.setEndDate(sdf.parse("2015/02/01"));
		information.setInsDate(StringUtils.stringToDate("2014/05/20 00:00:00", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
	}
	
	/**
	 * サイト TOP に表示するお知らせ情報を取得するテスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>公開対象区分 = 「仮を含む全会員」のデータを取得できる事</li>
	 * </ul>
	 */
	@Test
	public void searchTopInformationTest() throws Exception {

		List<Information> informationList = informationManage.searchTopInformation();
		
		Assert.assertNotNull("対象データが取得できている事", informationList.get(0));
		Assert.assertEquals("お知らせ情報のお知らせ番号が正しい事", informationList.get(0).getInformationNo(), "0000000000003");
	}
	
	/**
	 * リクエストパラメータで渡された「お知らせ番号」 （主キー値）に該当するお知らせ情報を復帰する。（サイト TOP 用）テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>公開対象区分 = 「仮を含む全会員」が取得対象になる。また、システム日付が公開期間中であるお知らせ情報が取得対象となる。もし該当データが見つからない場合、null を復帰する。</li>
	 * </ul>
	 */
	@Test
	public void searchTopInformationPkTest() throws Exception {

		Information information = informationManage.searchTopInformationPk("0000000000003");
		
		Assert.assertNotNull("対象データが取得できている事", information);
		Assert.assertEquals("お知らせ情報のお知らせ番号が正しい事", information.getInformationNo(), "0000000000003");
		
		information = informationManage.searchTopInformationPk("0000000000002");
		Assert.assertNull("対象データが取得できていない事", information);
	}
	
	/**
	 * マイページ TOP に表示するお知らせ情報を取得するテスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>公開対象区分 = 「全本会員」、または、「個人」が取得対象になるが、「個人」の場合、お知らせ公開先情報のユーザーID が引数と一致する必要がある。また、システム日付が公開期間中であるお知らせ情報が取得対象となる。</li>
	 * </ul>
	 */
	@Test
	public void searchMyPageInformationTest() throws Exception {
		List<Information> informationList = informationManage.searchMyPageInformation("00000000000000000002");
		
		Assert.assertNotNull("対象データが取得できている事", informationList);
		Assert.assertEquals("お知らせ情報のお知らせ番号が正しい事", informationList.get(0).getInformationNo(), "0000000000001");
		Assert.assertEquals("お知らせ情報のお知らせ番号が正しい事", informationList.get(1).getInformationNo(), "0000000000002");
		Assert.assertEquals("お知らせ情報のお知らせ番号が正しい事", informationList.get(2).getInformationNo(), "0000000000005");
		Assert.assertEquals("お知らせ情報のお知らせ番号が正しい事", informationList.get(3).getInformationNo(), "0000000000007");
		Assert.assertEquals("お知らせ情報のお知らせ番号が正しい事", informationList.get(4).getInformationNo(), "0000000000009");
	}
	
	/**
	 * マイページ TOP に表示するお知らせ情報を取得するテスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>公開対象区分 = 「全本会員」、または、「個人」が取得対象になるが、「個人」の場合、お知らせ公開先情報のユーザーID が引数と一致する必要がある。また、システム日付が公開期間中であるお知らせ情報が取得対象となる。</li>
	 * </ul>
	 */
	@Test
	public void searchMyPageInformationPkTest() throws Exception {
		Information information = informationManage.searchMyPageInformationPk("0000000000001", "00000000000000000002");
		
		Assert.assertNotNull("対象データが取得できている事", information);
		Assert.assertEquals("お知らせ情報のお知らせ番号が正しい事", information.getInformationNo(), "0000000000001");
		
		information = informationManage.searchMyPageInformationPk("0000000000002", "00000000000000000002");
		Assert.assertNotNull("対象データが取得できている事", information);
		Assert.assertEquals("お知らせ情報のお知らせ番号が正しい事", information.getInformationNo(), "0000000000002");
		
		information = informationManage.searchMyPageInformationPk("0000000000005", "00000000000000000002");
		Assert.assertNotNull("対象データが取得できている事", information);
		Assert.assertEquals("お知らせ情報のお知らせ番号が正しい事", information.getInformationNo(), "0000000000005");
		
		information = informationManage.searchMyPageInformationPk("0000000000007", "00000000000000000002");
		Assert.assertNotNull("対象データが取得できている事", information);
		Assert.assertEquals("お知らせ情報のお知らせ番号が正しい事", information.getInformationNo(), "0000000000007");
		
		information = informationManage.searchMyPageInformationPk("0000000000009", "00000000000000000002");
		Assert.assertNotNull("対象データが取得できている事", information);
		Assert.assertEquals("お知らせ情報のお知らせ番号が正しい事", information.getInformationNo(), "0000000000009");
		
		information = informationManage.searchMyPageInformationPk("0000000000010", "00000000000000000002");
		Assert.assertNull("対象データが取得できていない事", information);
	}
	
	/**
	 * パラメータで渡された Form の情報でお知らせ情報を新規追加するテスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>formの値で登録できる事</li>
	 * </ul>
	 */
	@Test
	public void addInformationTest() throws Exception {
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		InformationForm informationForm = this.informationFormFactory.createInformationForm();
		informationForm.setInformationNo("0000000000011");
		informationForm.setTitle("test01");
		informationForm.setDspFlg("1");
		informationForm.setInformationMsg("test内容01");
		informationManage.addInformation(informationForm, "Test");
		
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("informationNo", informationForm.getInformationNo());
		List<Information> information = informationDAO.selectByFilter(criteria);
		
		Assert.assertEquals("formの値で登録できる事", information.get(0).getInformationNo(), "0000000000011");
	}
	
	/**
	 * パラメータで渡された Form の情報でお知らせ情報を削除するテスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>主キーでお知らせ情報を削除できる事</li>
	 * </ul>
	 */
	@Test
	public void delInformationTest() throws Exception {
		InformationForm informationForm = this.informationFormFactory.createInformationForm();
		informationForm.setInformationNo("0000000000001");
		informationManage.delInformation(informationForm);
		
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("informationNo", informationForm.getInformationNo());
		List<Information> information = informationDAO.selectByFilter(criteria);
		
		Assert.assertEquals("主キーでお知らせ情報を削除できる事", information.size(), 0);
	}
	
	/**
	 * お知らせの更新テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>formでお知らせ情報を更新できる事</li>
	 * </ul>
	 */
	@Test
	public void updateInformationTest() throws Exception {
		InformationForm informationForm = this.informationFormFactory.createInformationForm();
		informationForm.setInformationNo("0000000000001");
		informationForm.setTitle("test11");
		informationForm.setDspFlg("1");
		informationForm.setInformationMsg("test内容11");
		
		informationManage.updateInformation(informationForm, "Test");
		
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("informationNo", informationForm.getInformationNo());
		List<Information> information = informationDAO.selectByFilter(criteria);
		
		Assert.assertEquals("formでお知らせ情報を更新できる事", information.get(0).getTitle(), informationForm.getTitle());
		Assert.assertEquals("formでお知らせ情報を更新できる事", information.get(0).getDspFlg(), informationForm.getDspFlg());
		Assert.assertEquals("formでお知らせ情報を更新できる事", information.get(0).getInformationMsg(), informationForm.getInformationMsg());

	}
	
	/**
	 * お知らせ情報を検索し、結果リストを復帰する。（管理画面用）テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li> 引数で渡された Form パラメータの値で検索条件を生成し、お知らせ情報を検索できる事</li>
	 * </ul>
	 */
	@Test
	public void searchAdminInformationTest() throws Exception {
		InformationSearchForm searchForm = this.informationFormFactory.createInformationSearchForm();
		int size = informationManage.searchAdminInformation(searchForm);
		Assert.assertEquals("formでお知らせ情報を検索できる事",  size, 10);
		
		searchForm = this.informationFormFactory.createInformationSearchForm();
		searchForm.setKeyInformationNo("0000000000001");
		size = informationManage.searchAdminInformation(searchForm);
		Assert.assertEquals("formでお知らせ情報を検索できる事",  size, 1);
		
		searchForm = this.informationFormFactory.createInformationSearchForm();
		searchForm.setKeyTitle("st");
		size = informationManage.searchAdminInformation(searchForm);
		Assert.assertEquals("formでお知らせ情報を検索できる事",  size, 10);
		
		searchForm = this.informationFormFactory.createInformationSearchForm();
		searchForm.setKeyInsDateFrom("2015/05/01");
		size = informationManage.searchAdminInformation(searchForm);
		Assert.assertEquals("formでお知らせ情報を検索できる事",  size, 8);
		
		searchForm = this.informationFormFactory.createInformationSearchForm();
		searchForm.setKeyInsDateTo("2015/06/01");
		size = informationManage.searchAdminInformation(searchForm);
		Assert.assertEquals("formでお知らせ情報を検索できる事",  size, 9);
		
		searchForm = this.informationFormFactory.createInformationSearchForm();
		searchForm.setKeyInsDateFrom("2015/05/01");
		searchForm.setKeyInsDateTo("2015/05/31");
		size = informationManage.searchAdminInformation(searchForm);
		Assert.assertEquals("formでお知らせ情報を検索できる事",  size, 5);
	}
}
