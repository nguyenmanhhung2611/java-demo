package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * �������̒ǉ��A�ύX�A�폜����.
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A��������V�K�o�^����B</li>
 * <li>�܂��A���J�悪����l�̏ꍇ�A�������J������V�K�o�^����B</li>
 * </ul>
 * <br/>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���������X�V����B</li>
 * <li>�������J����͈�x�폜���A�ύX��̌��J�悪����l�ł���΁A�������J������V�K�o�^����B</li>
 * <li>�����A�X�V�Ώۃf�[�^�����݂��Ȃ��ꍇ�A�X�V�������p���ł��Ȃ��̂ŊY��������ʂ�\������B</li>
 * </ul>
 * <br/>
 * �y�폜�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B�i��L�[�l�̂݁j</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���������폜����B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���_�C���N�g�y�[�W�j
 * <li>input</li>:�o���f�[�V�����G���[�ɂ��ē���
 * <li>notFound</li>:�Y���f�[�^�����݂��Ȃ��ꍇ�i�X�V�����̏ꍇ�j
 * <li>comp</li>:������ʕ\��
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.2	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class BuildingCompCommand implements Command  {

	/** ���������e�i���X���s�� Model �I�u�W�F�N�g */
	protected BuildingManage buildingManager;

	/** �������[�h (insert = �V�K�o�^�����A update=�X�V�����Adelete=�폜����)*/
	protected String mode;

	/** Form �̃o���f�[�V���������s����ꍇ�Atrue ��ݒ肷��B�@�i�f�t�H���g true�j */
	protected boolean useValidation = true;

	/** �s���{���}�X�^�擾�p DAO */
	protected DAO<PrefMst> prefMstDAO;
	
	/** �s�撬���}�X�^�擾�p DAO */
	protected DAO<AddressMst> addressMstDAO;
	
	
	/**
	 * ���������e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingManager ���������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setBuildingManager(BuildingManage buildingManager) {
		this.buildingManager = buildingManager;
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
	 * �s���{���}�X�^�pDAO��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @return �s�撬���}�X�^�p DAO
	 */
	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}

	/**
	 * �s�撬���}�X�^�pDAO��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @return �s�撬���}�X�^�p DAO
	 */
	public void setAddressMstDAO(DAO<AddressMst> addressMstDAO) {
		this.addressMstDAO = addressMstDAO;
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
	 * �������̒ǉ��A�ύX�A�폜����<br>
	 * <br>
	 * @param request �N���C�A���g�����Http���N�G�X�g�B
	 * @param response �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        BuildingForm inputForm = (BuildingForm) model.get("inputForm"); 
        
        // ���O�C�����[�U�[�̏����擾����B�@�i�^�C���X�^���v�̍X�V�p�j
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

        
        // ������ʂŃ����[�h�����ꍇ�A�X�V�������Ӑ}�������s������肪��������B
        // ���̖�����������ׁAview ���� "success"�@���w�肷��Ǝ������_�C���N�g��ʂ��\�������B
        // ���̃��_�C���N�g��ʂ́Acommand �p�����[�^�� "redirect"�@�ɐݒ肵�Ċ�����ʂփ��N�G�X�g��
        // ���M����B
        // ����āAcommand = "redirect" �̏ꍇ�́A�c�a�X�V�͍s�킸�A������ʂ�\������B
        String command = inputForm.getCommand();
        if (command != null && "redirect".equals(command)){
        	return new ModelAndView("comp" , model);
        }

		// �o���f�[�V�����̎��s���[�h���L���̏ꍇ�A�o���f�[�V���������s����B
        // �폜�����A���b�N���������̏ꍇ�̓��O�C��ID �ȊO�̃p�����[�^�͕s�v�Ȃ̂ŁAspring �����疳�������Ă���B
        if (this.useValidation){
        	List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        	if (!inputForm.validate(errors)){
        		// �s���{���}�X�^���擾����
        		List<PrefMst> prefMstList = this.prefMstDAO.selectByFilter(null);
        		model.put("prefMstList", prefMstList);
    			// �s�撬���}�X�^���擾����
    			model.put("addressMstList", getAddressMstList(inputForm.getPrefCd()));
        		// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
        		model.put("errors", errors);
        		return new ModelAndView("input" , model);
        	}
        }
        
        // �e�폈�������s
        try {
        	execute(model, inputForm, loginUser);

        } catch (NotFoundException e) {
            // ���O�C��ID �����݂��Ȃ��ꍇ�́A�Y���Ȃ���ʂ�
        	return new ModelAndView("notFound", model);

        }

		return new ModelAndView("success" , model);
	}
	
	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		BuildingFormFactory factory = BuildingFormFactory.getInstance(request);

		model.put("searchForm", factory.createBuildingSearchForm(request));
		model.put("inputForm", factory.createBuildingForm(request));

		return model;

	}
	
	/**
	 * �����̐U�蕪���Ǝ��s���s���B<br/>
	 * <br/>
 	 * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	protected void execute(Map<String, Object> model, BuildingForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {

		if (this.mode.equals("insert")){
			insert(inputForm, loginUser);
        } else if (this.mode.equals("update")) {
			update(inputForm, loginUser);
        } else if (this.mode.equals("delete")) {
			delete(inputForm);
        } else {
        	// �z�肵�Ă��Ȃ��������[�h�̏ꍇ�A��O���X���[����B
        	throw new RuntimeException ("execute mode bad setting.");
        }

	}
	
	/**
	 * �V�K�o�^����<br/>
	 * �����œn���ꂽ���e�ł��m�点����ǉ�����B<br/>
	 * <br/>
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	protected void insert(BuildingForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// �V�K�o�^����
    	String sysBuildingCd = this.buildingManager.addBuilding(inputForm, (String)loginUser.getUserId());
    	
    	// �����{���ւ̃L�[
    	inputForm.setSysBuildingCd(sysBuildingCd);
	}

	/**
	 * �X�V�o�^����<br/>
	 * �����œn���ꂽ���e�Ō��������X�V����B<br/>
	 * <br/>
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	protected void update(BuildingForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {
    	
		// �X�V����
    	this.buildingManager.updateBuildingInfo(inputForm, (String)loginUser.getUserId());

	}
	
	/**
	 * �폜����<br/>
	 * �����œn���ꂽ���e�Ō��������폜����B<br/>
	 * <br/>
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	protected void delete(BuildingForm inputForm)
			throws Exception, NotFoundException {
    	
		// �폜����
    	this.buildingManager.delBuildingInfo(inputForm.getSysBuildingCd());

	}
	
	/**
	 * �s���{��CD�Ŏs�撬�����X�g���擾����B<br/>
	 * <br/>
	 * @param prefCd �s���{��CD
	 * @return �s�撬�����X�g�𕜋A����B
	 */
	protected List<AddressMst> getAddressMstList(String prefCd) {
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("prefCd", prefCd);
		List<AddressMst> addressMstList = this.addressMstDAO.selectByFilter(criteria);
		return addressMstList;
		
	}
}
