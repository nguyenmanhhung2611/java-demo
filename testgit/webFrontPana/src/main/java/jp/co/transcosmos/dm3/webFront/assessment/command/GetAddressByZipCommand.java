package jp.co.transcosmos.dm3.webFront.assessment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;

/**
 * <pre>
 * �X�֔ԍ��ɂ��A�Z������������B
 *
 * �y���A���� View ���z
 *	�E"success" : ����I��
 *
 * �S����	   �C����	  �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �`����	   2015.04.27  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class GetAddressByZipCommand implements Command {

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

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// �����p�����[�^��M
		String zip = PanaStringUtils.changeToHankakuNumber(request.getParameter("zip"));

		 // �s�撬���}�X�^���擾����
		String[] zipMst = panamCommonManager.getZipToAddress(zip);

		if("0".equals(zipMst[0])){
			zipMst[2] = panamCommonManager.getAddressName(zipMst[2]);
		}

		return new ModelAndView("success", "zipMst", zipMst);
	}

}
