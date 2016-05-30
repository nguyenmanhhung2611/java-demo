package jp.co.transcosmos.dm3.adminCore.information.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.springframework.web.servlet.ModelAndView;

/**
 * ���m�点�ڍ׉�ʁ@�i�폜�����̊m�F��ʌ��p�j.
 * <p>
 * ���N�G�X�g�p�����[�^�iinformaitonNo�j�œn���ꂽ�l�ɊY�����邨�m�点�����擾����ʕ\������B
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success<li>:������������I��
 * <li>notFound<li>:�Y���f�[�^�����݂��Ȃ��ꍇ
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.30	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class InformationDetailCommand implements Command  {

	/** ���m�点�����e�i���X���s�� Model �I�u�W�F�N�g */
	protected InformationManage informationManager;
	
	/**
	 * ���m�点�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param informationManager ���m�点�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setInformationManager(InformationManage informationManager) {
		this.informationManager = informationManager;
	}

	/**
	 * ���m�点�ڍו\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @return ModelAndView�@�̃C���X�^���X
	*/
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        InformationSearchForm searchForm = (InformationSearchForm) model.get("searchForm");
        
        // ���m�点�����擾
        JoinResult informationDetail = this.informationManager.searchAdminInformationPk(searchForm.getInformationNo());
        
        // �Y������f�[�^�����݂��Ȃ��ꍇ
        // �Y��������ʂ�\������B
        if (informationDetail == null) {
        	throw new NotFoundException();
        }
        
        // �擾�ł����ꍇ�� model �ɐݒ肷��B
		model.put("informationDetail", informationDetail);

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
		model.put("searchForm", factory.createInformationSearchForm(request));

		return model;
	}

}
