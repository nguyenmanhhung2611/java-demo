package jp.co.transcosmos.dm3.webAdmin.recommendPoint.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.RecommendPointForm;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * �����������߃|�C���g���̍X�V����.
 * <p>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�����������߃|�C���g�����X�V����B</li>
 * <li>�����A�X�V�Ώۃf�[�^�����݂��Ȃ��ꍇ�A�X�V�������p���ł��Ȃ��̂ŊY��������ʂ�\������B</li>
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
 * �s�����C     2015.4.10  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class RecommendPointCompCommand implements Command {

	/** ���������s�� Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * ���������s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param panaHousingManager �������� model �I�u�W�F�N�g
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * �����������߃|�C���g���̍X�V����<br>
	 * <br>
	 * @param request �N���C�A���g�����Http���N�G�X�g�B
	 * @param response �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        RecommendPointForm inputForm = (RecommendPointForm) model.get("inputForm");


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

		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {
			// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
			model.put("errors", errors);

			return new ModelAndView("input" , model);
		}

		// ���O�`�F�b�N
		if (!targetExist(inputForm)) {
			throw new NotFoundException();
		}

        // �e�폈�������s
        try {
        	this.panaHousingManager.updateHousing(inputForm, (String)loginUser.getUserId());

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
        PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
        RecommendPointForm form = factory.createRecommendPointForm(request);
        PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);

		model.put("inputForm", form);
		model.put("searchForm", searchForm);

		return model;

	}

	/**
	 * ���O�`�F�b�N���s���B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @return true �f�[�^�����݂̏ꍇ�A false �f�[�^�����݂��Ȃ��ꍇ
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException
	 *                �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	private boolean targetExist(RecommendPointForm inputForm) throws Exception,
			NotFoundException {

		// ���������擾����B
		Housing housing = this.panaHousingManager.searchHousingPk(inputForm.getSysHousingCd(), true);

		// �f�[�^�̑��݂��Ȃ��ꍇ�B
		if (housing == null) {
			return false;
		}
		return true;
	}

}
