package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �������̌����E�ꗗ
 * ���͂��ꂽ�������������ɂ��m�点�����������A�ꗗ�\������B
 * ���������̓��͂ɖ�肪����ꍇ�A���������͍s��Ȃ��B
 * 
 * �y���A���� View ���z
 *    �E"success" : ������������I��
 *    �E"validFail" : �o���f�[�V�����G���[ 
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.27	�V�K�쐬
 * 
 * ���ӎ���
 * 
 * </pre>
 */
public class BuildingListCommand implements Command {

	/** ������񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
	protected BuildingManage buildingManager;
	
	/** �P�y�[�W�̕\������ */
	protected int rowsPerPage = 50;

	
	/** �s���{���}�X�^�擾�p DAO */
	protected DAO<PrefMst> prefMstDAO;
	
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
	 * 1�y�[�W������\������ݒ肷��B<br>
	 * 
	 * @param rowsPerPage
	 *            1�y�[�W������\����
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * ������񃊃N�G�X�g����<br>
	 * �������̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
	 * 
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// �����p�����[�^��M
        Map<String, Object> model = createModel(request);
        BuildingSearchForm form = (BuildingSearchForm) model.get("searchForm");

		// �s���{���}�X�^���擾����
		List<PrefMst> prefMstList = this.prefMstDAO.selectByFilter(null);
		model.put("prefMstList", prefMstList);

        // �o���f�[�V���������s
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        if (!form.validate(errors)) {
        	// �o���f�[�V�����G���[����
			model.put("errors", errors);
			return new ModelAndView("validFail", model);
        }

        // �������s
        // searchBuildingInfo() �́A�p�����[�^�œn���ꂽ form �̓��e�ł��m�点�����������A
        // �����������ʂ� form �Ɋi�[����B
        // ���̃��\�b�h�̖߂�l�͊Y�������Ȃ̂ŁA���̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
		model.put("hitcont", this.buildingManager.searchBuilding(form));


		// �o���f�[�V������ʉ߂��A�����������s��ꂽ�ꍇ�AForm �� command �p�����[�^�� list ��ݒ�
		// ����B�@���̃p�����[�^�l�́A�ڍ׉�ʂ�ύX��ʂ� searchCommand �p�����[�^�Ƃ��Ĉ����p����A
		// �Ăь�����ʂ֕��A����ہAcommand �p�����[�^�Ƃ��ēn�����B
		model.put("command", "list");

		return new ModelAndView("success", model);
	}
	
	/**
	 * ���N�G�X�g�p�����[�^���� Form �I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		BuildingFormFactory factory = BuildingFormFactory.getInstance(request);
		BuildingSearchForm searchFrom = factory.createBuildingSearchForm(request);
		
        // �y�[�W���̕\�������� From �ɐݒ肷��B
        // ���̒l�́A�t���[�����[�N�̃y�[�W�������g�p����B
		searchFrom.setRowsPerPage(this.rowsPerPage);
        model.put("searchForm", searchFrom);
		
		return model;

	}

	/**
	 * ������񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingManager ������񃁃��e�i���X�� model �I�u�W�F�N�g
	 */
	public void setBuildingManager(BuildingManage buildingManager) {
		this.buildingManager = buildingManager;
	}
}
