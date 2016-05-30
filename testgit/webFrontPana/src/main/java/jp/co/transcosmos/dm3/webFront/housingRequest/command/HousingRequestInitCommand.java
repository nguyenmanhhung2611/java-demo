package jp.co.transcosmos.dm3.webFront.housingRequest.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingRequestForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.LengthValidator;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

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
public class HousingRequestInitCommand implements Command {

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

		// �s���{���̃`�F�b�N
		if(!StringUtils.isEmpty(housingRequestForm.getPrefCd())){

			List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
			// �s���{���̐��l�`�F�b�N
	        ValidationChain valPrefCd = new ValidationChain("housingRequest.input.valPrefCd", housingRequestForm.getPrefCd());

	        // �����`�F�b�N
	        valPrefCd.addValidation(new LengthValidator(2));

	        // ���l�`�F�b�N
	        valPrefCd.addValidation(new NumericValidation());

	        valPrefCd.validate(errors);
	        if(errors.size()>0){
	        	throw new RuntimeException();
	        }
		}

		// ������ʂ̃`�F�b�N
		if(!StringUtils.isEmpty(housingRequestForm.getHousingKindCd())){

			List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
			// ������ʂ̐��l�`�F�b�N
	        ValidationChain valHousingKindCd = new ValidationChain("housingRequest.input.valHousingKindCd", housingRequestForm.getHousingKindCd());

	        // �����`�F�b�N
	        valHousingKindCd.addValidation(new LengthValidator(2));

	        // ���l�`�F�b�N
	        valHousingKindCd.addValidation(new NumericValidation());

	        valHousingKindCd.validate(errors);
	        if(errors.size()>0){
	        	throw new RuntimeException();
	        }
		}

		if("back".equals(housingRequestForm.getCommand())){

			if("confirm".equals(housingRequestForm.getModel())){
				housingRequestForm.setModel("update");
			}
			model.put("housingRequestForm", housingRequestForm);
			// �s���{�����X�g�̐ݒ�
			List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
			model.put("prefMstList", prefMstList);

            // �擾�����f�[�^�������_�����O�w�֓n��
            return new ModelAndView("success", model);
		}

		housingRequestForm.setModel("insert");

		model.put("housingRequestForm", housingRequestForm);

		// �s���{�����X�g�̐ݒ�
		List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
		model.put("prefMstList", prefMstList);


		// �擾�����f�[�^�������_�����O�w�֓n��
		return new ModelAndView("success", model);
	}

}
