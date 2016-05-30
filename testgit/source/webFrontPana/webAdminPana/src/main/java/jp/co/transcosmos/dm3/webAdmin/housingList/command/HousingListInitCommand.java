package jp.co.transcosmos.dm3.webAdmin.housingList.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �����ꗗ�̏��������
 * �s���{�������擾�B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * guo.zhonglei     2015.04.02  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingListInitCommand implements Command {

	/** ���ʏ���p Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;

	/**
	 * ���ʏ���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaCommonManage ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * �����ꗗ�̏������\������<br>
	 * <br>
	 *
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// �����p�����[�^��M
    	Map<String, Object> model = new HashMap<String, Object>();
    	// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
    	PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
    	PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
        // ��ʍ��ڂ̏�����
        searchForm.setDefaultData(this.panamCommonManager.getPrefMstList(),model, true);

        // �s���{��List�̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
        model.put("searchForm", searchForm);

        return new ModelAndView("success", model);
    }
}
