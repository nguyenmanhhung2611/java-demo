package jp.co.transcosmos.dm3.webAdmin.userManage.command;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.userManage.command.CsvCommand;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.utils.CommonLogging;
import jp.co.transcosmos.dm3.webAdmin.utils.AdminLogging;

import org.springframework.web.servlet.ModelAndView;

/**
 * 管理ユーザー CSV 出力処理.
 * <p>
 * 入力された検索条件を元に管理者ユーザーを検索し、CSV 出力する。<br/>
 * 管理ユーザーの一覧出力と基本的に同一処理であり、使用する Form オブジェクトを CSV 用に 変更しているのみの違いとなる。<br/>
 * この CSV 用 Form オブジェクトは、ページ処理を行わず、全データを取得対象とする。<br/>
 * <br/>
 * 
 * <pre>
 * 担当者        修正日       修正内容
 * ------------ ----------- -----------------------------------------------------
 * unknown      unknown     No written header comments
 * Duong.Nguyen 2015.08.26  Change simple log by admin log for exporting csv file
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class PanaUserCsvCommand extends CsvCommand {

    /** ログ出力処理 */
    private CommonLogging logging;

    /**
     * ログ出力処理を設定する。<br/>
     * <br/>
     * 
     * @param logging ログ出力処理
     */
    public void setLogging(CommonLogging logging) {
        this.logging = logging;
    }

    /*
     * (non-Javadoc)
     * 
     * @see jp.co.transcosmos.dm3.adminCore.userManage.command.UserListCommand#
     * handleRequest(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // Prepare data for admin log
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dt = sdf.format(new Date());
        String csvName = "kanriuser_" + dt + ".csv";
        AdminUserInterface adminUser = AdminLoginUserUtils.getInstance(request)
                .getLoginUserInfo(request, response);
        String loginID = adminUser.getLoginId();
        String adminUserId = String.valueOf(adminUser.getUserId());

        try {
            // call business logic of super class
            ModelAndView mav = super.handleRequest(request, response);
            // write success message
            ((AdminLogging) this.logging).write("[" + loginID + "]　（" + csvName
                    + "）出力成功", adminUserId,
                    PanaCommonConstant.ADMIN_LOG_FC_ADMIN_USER_LIST);
            return mav;
        } catch (Exception ex) {
            // when an exception occurs, write fail message
            ((AdminLogging) this.logging).write("[" + loginID + "]　（" + csvName
                    + "）出力失敗", adminUserId,
                    PanaCommonConstant.ADMIN_LOG_FC_ADMIN_USER_LIST);
            throw ex;
        }
    }
}
