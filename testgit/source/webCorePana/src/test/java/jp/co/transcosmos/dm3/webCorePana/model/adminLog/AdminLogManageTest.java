package jp.co.transcosmos.dm3.webCorePana.model.adminLog;

import java.text.SimpleDateFormat;
import java.util.List;

import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.AdminLogManage;
import jp.co.transcosmos.dm3.corePana.model.adminLog.form.AdminLogFormFactory;
import jp.co.transcosmos.dm3.corePana.model.adminLog.form.AdminLogSearchForm;
import jp.co.transcosmos.dm3.corePana.vo.AdminLog;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.webCorePana.util.GenericTest;
import jp.co.transcosmos.dm3.webCorePana.util.SqlFile;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

// Test pagination OK
// Pagination negative page number
// Pagination over max page number
// Search by csv_function_id, result should match input csv_function_id (x5)
// Search by login_id, result should match input login_id
// Search by date range, result should in range of input: 
//    + start_date null
//    + end_date null
//    + start_date > end_date
//    + start_date < end_date
//    + start_date = end_date
//    + start_date null and end_date null
// Search by user_name, result should match input user_name
// Search all fields, result should match all data
public class AdminLogManageTest extends GenericTest {
    @Autowired
    DAO<AdminLog> adminLogDAO;

    @Autowired
    AdminLogManage adminLogManage;

    @Autowired
    AdminLogFormFactory formFactory;

    /**
     * Test pagination OK
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testPagination_OK() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setRowsPerPage(2);

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getVisibleRows();

        Assert.assertEquals(2, adminLogList.size());

        AdminLog log1 = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000001", log1.getAdminLogId());
        Assert.assertEquals("00000000000000000000", log1.getAdminUserId());
        Assert.assertEquals("[root]　（bukken_20150821.zip）出力成功", log1.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST, log1.getFunctionCd());
        Assert.assertEquals("2015-08-21 13:59:08", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log1.getInsDate()));

        AdminLog log2 = (AdminLog) adminLogList.get(1).getItems().get("adminLog");
        Assert.assertEquals("0000000000002", log2.getAdminLogId());
        Assert.assertEquals("00000000000000000000", log2.getAdminUserId());
        Assert.assertEquals("[root]　（bukken_20150826.zip）出力成功", log2.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST, log2.getFunctionCd());
        Assert.assertEquals("2015-08-22 09:50:25", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log2.getInsDate()));
    }

    /**
     * Pagination negative page number
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testPagination_negativePageNumber() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setSelectedPage(-1);
        searchForm.setRowsPerPage(2);

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getVisibleRows();
        Assert.assertEquals(0, adminLogList.size());
    }

    /**
     * Pagination over max page number<br/>
     * 
     * If selected page is larger than max page number, rewind to the first
     * available page. <br/>
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testPagination_overMaxPageNumber() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setRowsPerPage(2);
        searchForm.setSelectedPage(1000);

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getVisibleRows();

        Assert.assertEquals(2, adminLogList.size());

        AdminLog log1 = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000001", log1.getAdminLogId());
        Assert.assertEquals("00000000000000000000", log1.getAdminUserId());
        Assert.assertEquals("[root]　（bukken_20150821.zip）出力成功", log1.getMsg());
        Assert.assertEquals("02", log1.getFunctionCd());
        Assert.assertEquals("2015-08-21 13:59:08", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log1.getInsDate()));

        AdminLog log2 = (AdminLog) adminLogList.get(1).getItems().get("adminLog");
        Assert.assertEquals("0000000000002", log2.getAdminLogId());
        Assert.assertEquals("00000000000000000000", log2.getAdminUserId());
        Assert.assertEquals("[root]　（bukken_20150826.zip）出力成功", log2.getMsg());
        Assert.assertEquals("02", log2.getFunctionCd());
        Assert.assertEquals("2015-08-22 09:50:25", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log2.getInsDate()));
    }

    /**
     * Search by csv_function_cd = 1<br/>
     * 
     * Search admin log records of inquiry function <br/>
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_Inquiry_CsvFunctionCd() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyAdminLogFC(PanaCommonConstant.ADMIN_LOG_FC_INQUIRY_LIST);

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getRows();

        Assert.assertEquals(2, adminLogList.size());

        AdminLog log = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000010", log.getAdminLogId());
        Assert.assertEquals("00000000000000000000", log.getAdminUserId());
        Assert.assertEquals("[root]　（toiawase_20150826.csv）出力成功", log.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_INQUIRY_LIST, log.getFunctionCd());
        Assert.assertEquals("2015-08-24 14:19:11", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log.getInsDate()));
    }

    /**
     * Search by csv_function_cd = 2<br/>
     * 
     * Search admin log records of housing function <br/>
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_Housing_CsvFunctionCd() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyAdminLogFC(PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST);

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getRows();

        Assert.assertEquals(7, adminLogList.size());

        AdminLog log1 = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000001", log1.getAdminLogId());
        Assert.assertEquals("00000000000000000000", log1.getAdminUserId());
        Assert.assertEquals("[root]　（bukken_20150821.zip）出力成功", log1.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST, log1.getFunctionCd());
        Assert.assertEquals("2015-08-21 13:59:08", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log1.getInsDate()));
    }

    /**
     * Search by csv_function_cd = 3<br/>
     * 
     * Search admin log records of mypage function <br/>
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_MyPage_CsvFunctionCd() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyAdminLogFC(PanaCommonConstant.ADMIN_LOG_FC_MEMBER_LIST);

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getRows();

        Assert.assertEquals(2, adminLogList.size());

        AdminLog log1 = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000012", log1.getAdminLogId());
        Assert.assertEquals("00000000000000000000", log1.getAdminUserId());
        Assert.assertEquals("[root]　（kaiin_20150826.csv）出力成功", log1.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_MEMBER_LIST, log1.getFunctionCd());
        Assert.assertEquals("2015-08-24 14:19:25", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log1.getInsDate()));
    }

    /**
     * Search by csv_function_cd = 4<br/>
     * 
     * Search admin log records of information function <br/>
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_Information_CsvFunctionCd() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyAdminLogFC(PanaCommonConstant.ADMIN_LOG_FC_INFO_LIST);

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getRows();

        Assert.assertEquals(3, adminLogList.size());

        AdminLog log1 = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000003", log1.getAdminLogId());
        Assert.assertEquals("00000000000000000000", log1.getAdminUserId());
        Assert.assertEquals("[root]　（oshirase_20150826.csv）出力成功", log1.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_INFO_LIST, log1.getFunctionCd());
        Assert.assertEquals("2015-08-22 09:53:00", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log1.getInsDate()));
    }

    /**
     * Search by csv_function_cd = 5<br/>
     * 
     * Search admin log records of admin user function <br/>
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_AdminUser_CsvFunctionCd() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyAdminLogFC(PanaCommonConstant.ADMIN_LOG_FC_ADMIN_USER_LIST);

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getRows();

        Assert.assertEquals(3, adminLogList.size());

        AdminLog log1 = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000004", log1.getAdminLogId());
        Assert.assertEquals("00000000000000000000", log1.getAdminUserId());
        Assert.assertEquals("[root]　（kanriuser_20150826.csv）出力成功", log1.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_ADMIN_USER_LIST, log1.getFunctionCd());
        Assert.assertEquals("2015-08-22 09:53:36", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log1.getInsDate()));
    }

    /**
     * Search by login_id, result should match input login_id<br/>
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_LoginId() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyLoginId("duong123");

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getRows();

        Assert.assertEquals(1, adminLogList.size());

        AdminLog log1 = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000018", log1.getAdminLogId());
        Assert.assertEquals("A0000000000000000005", log1.getAdminUserId());
        Assert.assertEquals("[duong123]　（bukken_20150826.zip）出力成功", log1.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST, log1.getFunctionCd());
        Assert.assertEquals("2015-08-26 14:20:03", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log1.getInsDate()));
    }

    /**
     * Search by date range, result should in range of input when start_date
     * null
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_dateRange_StartDateIsNull() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyInsDateStart(null);
        searchForm.setKeyInsDateEnd("2015-08-25");

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getRows();

        Assert.assertEquals(13, adminLogList.size());

        AdminLog log1 = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000001", log1.getAdminLogId());
        Assert.assertEquals("00000000000000000000", log1.getAdminUserId());
        Assert.assertEquals("[root]　（bukken_20150821.zip）出力成功", log1.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST, log1.getFunctionCd());
        Assert.assertEquals("2015-08-21 13:59:08", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log1.getInsDate()));
    }

    /**
     * Search by date range, result should in range of input when end_date null
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_dateRange_EndDateIsNull() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyInsDateStart("2015-08-25");
        searchForm.setKeyInsDateEnd(null);

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getRows();

        Assert.assertEquals(6, adminLogList.size());

        AdminLog log1 = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000013", log1.getAdminLogId());
        Assert.assertEquals("00000000000000000000", log1.getAdminUserId());
        Assert.assertEquals("[root]　（oshirase_20150826.csv）出力成功", log1.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_INFO_LIST, log1.getFunctionCd());
        Assert.assertEquals("2015-08-25 14:19:30", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log1.getInsDate()));
    }

    /**
     * Search by date range, result should in range of input when start_date >
     * end_date <br/>
     * 
     * Just return empty data
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_dateRange_StartDate_isLater_EndDate() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyInsDateStart("2015-08-30");
        searchForm.setKeyInsDateEnd("2015-08-25");

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getRows();

        Assert.assertEquals(0, adminLogList.size());
    }

    /**
     * Search by date range, result should in range of input when start_date <
     * end_date <br/>
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_dateRange_StartDate_isBefore_EndDate() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyInsDateStart("2015-08-23");
        searchForm.setKeyInsDateEnd("2015-08-25");

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getRows();

        Assert.assertEquals(8, adminLogList.size());

        AdminLog firstLog = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000006", firstLog.getAdminLogId());
        Assert.assertEquals("00000000000000000000", firstLog.getAdminUserId());
        Assert.assertEquals("[root]　（bukken_20150826.zip）出力失敗", firstLog.getMsg());
        Assert.assertEquals("02", firstLog.getFunctionCd());
        Assert.assertEquals("2015-08-23 09:59:17", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(firstLog.getInsDate()));

        AdminLog lastLog = (AdminLog) adminLogList.get(7).getItems().get("adminLog");
        Assert.assertEquals("0000000000014", lastLog.getAdminLogId());
        Assert.assertEquals("A0000000000000000006", lastLog.getAdminUserId());
        Assert.assertEquals("[administrator]　（kanriuser_20150826.csv）出力成功", lastLog.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_ADMIN_USER_LIST, lastLog.getFunctionCd());
        Assert.assertEquals("2015-08-25 14:19:37", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastLog.getInsDate()));
    }

    /**
     * Search by date range, result should in range of input when start_date =
     * end_date <br/>
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_dateRange_StartDate_equal_EndDate() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyInsDateStart("2015-08-24");
        searchForm.setKeyInsDateEnd("2015-08-24");

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getRows();

        Assert.assertEquals(4, adminLogList.size());

        AdminLog firstLog = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000008", firstLog.getAdminLogId());
        Assert.assertEquals("A0000000000000000006", firstLog.getAdminUserId());
        Assert.assertEquals("[administrator]　（kanriuser_20150826.csv）出力失敗", firstLog.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_ADMIN_USER_LIST, firstLog.getFunctionCd());
        Assert.assertEquals("2015-08-24 10:01:22", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(firstLog.getInsDate()));

        AdminLog lastLog = (AdminLog) adminLogList.get(3).getItems().get("adminLog");
        Assert.assertEquals("0000000000012", lastLog.getAdminLogId());
        Assert.assertEquals("00000000000000000000", lastLog.getAdminUserId());
        Assert.assertEquals("[root]　（kaiin_20150826.csv）出力成功", lastLog.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_MEMBER_LIST, lastLog.getFunctionCd());
        Assert.assertEquals("2015-08-24 14:19:25", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastLog.getInsDate()));
    }

    /**
     * Search by date range, result should in range of input when start_date =
     * null and end_date = null <br/>
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_dateRange_StartDate_and_EndDate_areNull() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyInsDateStart(null);
        searchForm.setKeyInsDateEnd(null);

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getRows();

        Assert.assertEquals(17, adminLogList.size());

        AdminLog firstLog = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000001", firstLog.getAdminLogId());
        Assert.assertEquals("00000000000000000000", firstLog.getAdminUserId());
        Assert.assertEquals("[root]　（bukken_20150821.zip）出力成功", firstLog.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST, firstLog.getFunctionCd());
        Assert.assertEquals("2015-08-21 13:59:08", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(firstLog.getInsDate()));

        AdminLog lastLog = (AdminLog) adminLogList.get(16).getItems().get("adminLog");
        Assert.assertEquals("0000000000018", lastLog.getAdminLogId());
        Assert.assertEquals("A0000000000000000005", lastLog.getAdminUserId());
        Assert.assertEquals("[duong123]　（bukken_20150826.zip）出力成功", lastLog.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST, lastLog.getFunctionCd());
        Assert.assertEquals("2015-08-26 14:20:03", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastLog.getInsDate()));
    }

    /**
     * Search by user_name, result should match input user_name <br/>
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_UserName() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyUserName("ドラえもんの猫");

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getRows();

        Assert.assertEquals(12, adminLogList.size());

        AdminLog firstLog = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000001", firstLog.getAdminLogId());
        Assert.assertEquals("00000000000000000000", firstLog.getAdminUserId());
        Assert.assertEquals("[root]　（bukken_20150821.zip）出力成功", firstLog.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST, firstLog.getFunctionCd());
        Assert.assertEquals("2015-08-21 13:59:08", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(firstLog.getInsDate()));

        AdminLog lastLog = (AdminLog) adminLogList.get(11).getItems().get("adminLog");
        Assert.assertEquals("0000000000016", lastLog.getAdminLogId());
        Assert.assertEquals("00000000000000000000", lastLog.getAdminUserId());
        Assert.assertEquals("[root]　（kaiin_20150826.csv）出力成功", lastLog.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_MEMBER_LIST, lastLog.getFunctionCd());
        Assert.assertEquals("2015-08-26 14:19:54", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastLog.getInsDate()));
    }

    /**
     * Search by user_name,login_id,start_date,end_date,csv_function_cd result
     * should match all data <br/>
     * 
     * @throws Exception
     */
    @Test
    @SqlFile("sql/jp/co/transcosmos/dm3/webCorePana/model/adminLog/AdminLogManageTest/data1.sql")
    public void testSearch_by_AllFields() throws Exception {
        // given
        AdminLogSearchForm searchForm = formFactory.createAdminLogSearchForm();
        searchForm.setKeyLoginId(null);
        searchForm.setKeyUserName(null);
        searchForm.setKeyInsDateStart(null);
        searchForm.setKeyInsDateEnd(null);
        searchForm.setKeyAdminLogFC(null);

        // when
        adminLogManage.searchAdminLog(searchForm);

        // then
        List<JoinResult> adminLogList = searchForm.getVisibleRows();

        Assert.assertEquals(17, adminLogList.size());

        AdminLog firstLog = (AdminLog) adminLogList.get(0).getItems().get("adminLog");
        Assert.assertEquals("0000000000001", firstLog.getAdminLogId());
        Assert.assertEquals("00000000000000000000", firstLog.getAdminUserId());
        Assert.assertEquals("[root]　（bukken_20150821.zip）出力成功", firstLog.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST, firstLog.getFunctionCd());
        Assert.assertEquals("2015-08-21 13:59:08", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(firstLog.getInsDate()));

        AdminLog lastLog = (AdminLog) adminLogList.get(16).getItems().get("adminLog");
        Assert.assertEquals("0000000000018", lastLog.getAdminLogId());
        Assert.assertEquals("A0000000000000000005", lastLog.getAdminUserId());
        Assert.assertEquals("[duong123]　（bukken_20150826.zip）出力成功", lastLog.getMsg());
        Assert.assertEquals(PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST, lastLog.getFunctionCd());
        Assert.assertEquals("2015-08-26 14:20:03",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastLog.getInsDate()));
    }
}
