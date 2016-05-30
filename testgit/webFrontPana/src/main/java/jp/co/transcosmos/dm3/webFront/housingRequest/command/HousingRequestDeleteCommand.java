package jp.co.transcosmos.dm3.webFront.housingRequest.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequest;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingRequest;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingRequestForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * �������N�G�X�g���͉��
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 *   ��		  2015.04.22    �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingRequestDeleteCommand implements Command {

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** �������p Model �I�u�W�F�N�g */
	private HousingRequestManage housingRequestManage;

	/**
	 * �������N�G�X�g���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingRequestManage
	 *            �������N�G�X�g���p Model �I�u�W�F�N�g
	 */
	public void setHousingRequestManage(HousingRequestManage housingRequestManage) {
		this.housingRequestManage = housingRequestManage;
	}

	/**
	 * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/** �������p Model �I�u�W�F�N�g */
	private PanaCommonManage panaCommonManage;

	/**
	 * ���ʏ���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaCommonManage ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanaCommonManage(PanaCommonManage panaCommonManage) {
		this.panaCommonManage = panaCommonManage;
	}

	/**
	 * �������N�G�X�g���͉�ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);

		// �y�[�W�����p�̃t�H�[���I�u�W�F�N�g���쐬
		PanaHousingRequestForm housingRequestForm = factory
				.createPanaHousingRequestForm(request);

		// ���O�C�����[�U�[�̏����擾
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(
			    request, response);
		// ���[�UID���擾
		String userId = "";
		if (loginUser != null) {
			userId = loginUser.getUserId().toString();
			model.put("loginFlg", 0);
		}else{
			userId = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response).getUserId().toString();
			model.put("loginFlg", 1);
		}

		model.put("housingRequestForm", housingRequestForm);

		// �`�B���̕������N�G�X�gID�ihousing_request_id�j
		if(StringUtils.isEmpty(housingRequestForm.getHousingRequestId())){
			throw new RuntimeException("�������N�G�X�gID���w�肳��Ă��܂���.");
		}

		// �������N�G�X�g���
		List<HousingRequest> requestList = new ArrayList<HousingRequest>();
		HousingRequest requestInfo = new PanaHousingRequest();
		String housingRequestId = housingRequestForm.getHousingRequestId();

		requestList = this.housingRequestManage.searchRequest(userId);
		int searchCount = 0;
		for(int i=0;i<requestList.size();i++){
			if(!StringUtils.isEmpty(housingRequestId)){
				if(housingRequestId.equals(requestList.get(i).getHousingRequestInfo().getHousingRequestId())){
					requestInfo = requestList.get(i);
					searchCount++;
				}
			}
		}

		if(searchCount == 0){
			return new ModelAndView("404");
		}

		// Form �֏����l��ݒ肷��B
		if(requestList != null && requestList.size()>0){
			housingRequestForm.setDefaultData(requestInfo);
		}

		// �s���{�����X�g�̐ݒ�
		String prefName = this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd());
		model.put("prefName", prefName);


		// �擾�����f�[�^�������_�����O�w�֓n��
		return new ModelAndView("success", model);
	}

}
