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
 * �Ǘ����[�U�[ CSV �o�͏���.
 * <p>
 * ���͂��ꂽ�������������ɊǗ��҃��[�U�[���������ACSV �o�͂���B<br/>
 * �Ǘ����[�U�[�̈ꗗ�o�͂Ɗ�{�I�ɓ��ꏈ���ł���A�g�p���� Form �I�u�W�F�N�g�� CSV �p�� �ύX���Ă���݂̂̈Ⴂ�ƂȂ�B<br/>
 * ���� CSV �p Form �I�u�W�F�N�g�́A�y�[�W�������s�킸�A�S�f�[�^���擾�ΏۂƂ���B<br/>
 * <br/>
 * 
 * <pre>
 * �S����        �C����       �C�����e
 * ------------ ----------- -----------------------------------------------------
 * unknown      unknown     No written header comments
 * Duong.Nguyen 2015.08.26  Change simple log by admin log for exporting csv file
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class PanaUserCsvCommand extends CsvCommand {

    /** ���O�o�͏��� */
    private CommonLogging logging;

    /**
     * ���O�o�͏�����ݒ肷��B<br/>
     * <br/>
     * 
     * @param logging ���O�o�͏���
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
            ((AdminLogging) this.logging).write("[" + loginID + "]�@�i" + csvName
                    + "�j�o�͐���", adminUserId,
                    PanaCommonConstant.ADMIN_LOG_FC_ADMIN_USER_LIST);
            return mav;
        } catch (Exception ex) {
            // when an exception occurs, write fail message
            ((AdminLogging) this.logging).write("[" + loginID + "]�@�i" + csvName
                    + "�j�o�͎��s", adminUserId,
                    PanaCommonConstant.ADMIN_LOG_FC_ADMIN_USER_LIST);
            throw ex;
        }
    }
}
