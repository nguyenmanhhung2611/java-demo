package jp.co.transcosmos.dm3.adminCore.information.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.core.vo.InformationTarget;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���m�点�����͉��
 * 
 * �y�V�K�o�^�̏ꍇ�z
 *     �E���N�G�X�g�p�����[�^�i������ʂœ��͂������������A�\���y�[�W�ʒu���j�̎���݂̂��s���B
 *     
 * �y�X�V�o�^�̏ꍇ�z
 *     �E���N�G�X�g�p�����[�^�i������ʂœ��͂������������A�\���y�[�W�ʒu���j�̎����s���B
 *     �E���N�G�X�g�p�����[�^�iinformationNo�j�ɊY�����邨�m�点���A�}�C�y�[�W��������擾���A���
 *      �\������B
 *      
 * �y���N�G�X�g�p�����[�^�icommand�j �� "back"�̏ꍇ�z
 *     �E���N�G�X�g�p�����[�^�œn���ꂽ���͒l����͉�ʂɕ\������B�@�i���͊m�F��ʂ��畜�A�����P�[�X�B�j
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"notFound" : �Y���f�[�^�����݂��Ȃ��ꍇ
 *     
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.6	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class InformationInputCommand implements Command {

	/** ���m�点�����e�i���X���s�� Model �I�u�W�F�N�g */
	protected InformationManage informationManager;
	
	/** �������[�h (insert = �V�K�o�^�����A update=�X�V����)*/
	protected String mode;
	
	
	
	/**
	 * ���m�点�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param informationManager ���m�点�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setInformationManager(InformationManage informationManager) {
		this.informationManager = informationManager;
	}

	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 * @param mode "insert" = �V�K�o�^�����A"update" = �X�V����
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	
	
	/**
	 * ���m�点�����͉�ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        InformationForm inputForm = (InformationForm) model.get("inputForm"); 
        InformationSearchForm searchForm = (InformationSearchForm) model.get("searchForm"); 

		// �X�V�����̏ꍇ�A�����ΏۂƂȂ��L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
		if ("update".equals(this.mode) && StringValidateUtil.isEmpty(searchForm.getInformationNo())){
			throw new RuntimeException ("pk value is null.");
		}

        // �V�K�o�^���[�h�ŏ�����ʕ\���̏ꍇ�A�f�[�^�̎擾�͍s��Ȃ��B
        if ("insert".equals(this.mode)) {
        	return new ModelAndView("success", model);
        }
        
        // ���m�点�����擾
        JoinResult information = this.informationManager.searchAdminInformationPk(searchForm.getInformationNo());
        
        // �Y������f�[�^�����݂��Ȃ��ꍇ�A�Y��������ʂ�\������B
        if (information == null) {
        	return new ModelAndView("notFound", model);
        }

        model.put("informationInsDate", (Information) information.getItems().get(InformationManageImpl.IMFORMATION_ALIA));
        
		// command �p�����[�^�� "back" �̏ꍇ�A���͊m�F��ʂ���̕��A�Ȃ̂ŁA�c�a���珉���l���擾���Ȃ��B
		// �i���N�G�X�g�p�����[�^����擾�����l���g�p����B�j
		String command = inputForm.getCommand();
		if (command != null && "back".equals(command)) {
			
			return new ModelAndView("success", model);
		}
		
        // �擾�����l����͗p Form �֊i�[����B
        CommonParameters commonParameters = CommonParameters.getInstance(request);
        // �擾�����l����͗p Form �֊i�[����B �i���͒l�̏����l�p�j
		inputForm.setDefaultData(
				(Information) information.getItems().get(InformationManageImpl.IMFORMATION_ALIA),
				(InformationTarget) information.getItems().get(InformationManageImpl.IMFORMATION_TARGET_ALIA),
				(MypageUserInterface) information.getItems().get(commonParameters.getMemberDbAlias()));
		
		return new ModelAndView("success", model);
	}


	
	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

        Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		InformationFormFactory factory = InformationFormFactory.getInstance(request);

		// ���������A����сA��ʃR���g���[���p�����[�^���擾����B
		InformationSearchForm searchForm = factory.createInformationSearchForm(request);
		model.put("searchForm", searchForm);

		
		// ���̓t�H�[���̎󂯎��́Acommand �p�����[�^�̒l�ɂ���ĈقȂ�B
		// command �p�����[�^���n����Ȃ��ꍇ�A���͉�ʂ̏����\���Ƃ݂Ȃ��A��̃t�H�[���𕜋A����B
		// command �p�����[�^�� "back" ���n���ꂽ�ꍇ�A���͊m�F��ʂ���̕��A���Ӗ�����B
		// ���̏ꍇ�̓��N�G�X�g�p�����[�^���󂯎��A���͉�ʂ֏����\������B
		// �����A"back" �ȊO�̒l���n���ꂽ�ꍇ�Acommand �p�����[�^���n����Ȃ��ꍇ�Ɠ����Ɉ����B

		InformationForm inputForm = factory.createInformationForm(request);
		String command = inputForm.getCommand();
		
		if ("back".equals(command)){
			// �߂�{�^���̏ꍇ�́A���N�G�X�g�p�����[�^�̒l��ݒ肵�� Form �𕜋A����B
			model.put("inputForm", inputForm);
		} else {

			// ������ʕ\���̏ꍇ�A��t�H�[���𐶐����A�f�t�H���g�̓��͒l��ݒ肷��B
			inputForm = factory.createInformationForm();

			// ���͉�ʂ̏����l��ݒ�
			inputForm.setInformationType("001");
			inputForm.setDspFlg("0");

			model.put("inputForm", inputForm);
		}

		return model;

	}

}
