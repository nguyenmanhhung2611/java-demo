package jp.co.transcosmos.dm3.adminCore.information.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���m�点�����͊m�F���
 * ���N�G�X�g�p�����[�^�œn���ꂽ�Ǘ����[�U�[���̃o���f�[�V�������s���A�m�F��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 * 
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"input" : �o���f�[�V�����G���[�ɂ��ē���
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.09	�V�K�쐬
 *
 * ���ӎ���
 * 
 * </pre>
*/
public class InformationConfirmCommand implements Command {

	/** ���m�点�����e�i���X���s�� Model �I�u�W�F�N�g */
	protected InformationManage informationManager;
	
	/** �������[�h (insert = �V�K�o�^�����A update=�X�V�����A delete=�폜����)*/
	protected String mode;

	/** �}�C�y�[�W������擾�p DAO */
	protected DAO<MemberInfo> memberInfoDAO;
	
	/** Form �̃o���f�[�V���������s����ꍇ�Atrue ��ݒ肷��B�@�i�f�t�H���g true�j */
	protected boolean useValidation = true;
	
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
	 * Form �̃o���f�[�V���������s����ꍇ�Atrue ��ݒ肷��B�@�i�f�t�H���g true�j<br/>
	 * <br/>
	 * @param useValidation true �̏ꍇ�AForm �̃o���f�[�V���������s
	 */
	public void setUseValidation(boolean useValidation) {
		this.useValidation = useValidation;
	}

	
	/**
	 * �}�C�y�[�W������擾�p DAO��ݒ肷��B<br/>
	 * <br/>
	 * @param memberInfoDAO �}�C�y�[�W������擾�p DAO
	 */
	public void setMemberInfoDAO(DAO<MemberInfo> memberInfoDAO) {
		this.memberInfoDAO = memberInfoDAO;
	}
	
	/**
	 * ���m�点���͊m�F��ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

 		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
 		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
         Map<String, Object> model = createModel(request);
         InformationForm inputForm = (InformationForm) model.get("inputForm"); 
         
         
        // �X�V���[�h�̏ꍇ�A�X�V�Ώۂ̎�L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
        if ("update".equals(this.mode)){
        	if (StringValidateUtil.isEmpty(inputForm.getInformationNo())) throw new RuntimeException ("pk value is null.");
        }


        // note
        // ���������p�����[�^�̈Ӑ}�I������ɑ΂���o���f�[�V�����͂��̃^�C�~���O�ł͍s��Ȃ��B
        // �o�^������̌�����ʂł̃o���f�[�V�����Ɉς˂�B

        
        // view ���̏����l��ݒ�
        String viewName = "success"; 
        
		// �o���f�[�V���������s
        if (this.useValidation){
	        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
	        if (!inputForm.validate(errors)){
	        	// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
	        	model.put("errors", errors);
	        	viewName = "input";
	        	return new ModelAndView(viewName , model);
	        }
        }

        // ���[�U�[��񂪎w�肳�ꂽ�ꍇ�ADB �Ƃ̏ƍ��`�F�b�N���s��
        // �������̏ꍇ
        if (!"delete".equals(this.mode) && !StringUtils.isEmpty(inputForm.getDspFlg()) && "2".equals(inputForm.getDspFlg())) {
        	MemberInfo memberInfo = memberInfoDAO.selectByPK(inputForm.getUserId());
        	if (memberInfo == null) {
        		throw new RuntimeException ("�w�肳�ꂽ����͑��݂��Ă��܂���B");
        	}
        }
        
        if ("delete".equals(this.mode)){
            // ���m�点�ԍ�
            String informationNo = inputForm.getInformationNo();
            
            // �w�肳�ꂽinformationNo�ɊY�����邨�m�点�̏����擾����B
            // ���������擾
            JoinResult informationDetail = this.informationManager.searchAdminInformationPk(informationNo);
            
            // �Y������f�[�^�����݂��Ȃ��ꍇ
            // �Y��������ʂ�\������B
            if (informationDetail == null) {
            	return new ModelAndView("notFound", model);
            }
            
            // �擾�ł����ꍇ�� model �ɐݒ肷��B
    		model.put("informationDetail", informationDetail);
        }

        return new ModelAndView(viewName, model);
	}



	/**
	 * ���N�G�X�g�p�����[�^���� Form �I�u�W�F�N�g���쐬����B<br/>
	 * �������� Form �I�u�W�F�N�g�� Map �Ɋi�[���ĕ��A����B<br/>
	 * key = �t�H�[���N���X���i�p�b�P�[�W�Ȃ��j�AValue = �t�H�[���I�u�W�F�N�g
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g���i�[���� Map �I�u�W�F�N�g
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		InformationFormFactory factory = InformationFormFactory.getInstance(request);

		model.put("searchForm", factory.createInformationSearchForm(request));
		model.put("inputForm", factory.createInformationForm(request));

		return model;

	}

}
