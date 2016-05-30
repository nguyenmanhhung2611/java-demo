package jp.co.transcosmos.dm3.frontCore.housingDetail.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.frontCore.housingDetail.displayAdapter.HousingDetailDisplayAdapter;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;


/**
 * �����ڍ׉��.
 * <p>
 * <ul>
 * <li>URL�i���N�G�X�g�p�����[�^�j����V�X�e�������b�c���擾����B</li>
 * <li>�V�X�e�������b�c���L�[�Ƃ��ĕ��������擾����B</li>
 * <li>�擾�����l����ʂɕ\������B</li>
 * </ul>
 * <br>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.16	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br>
 *
 */
public class HousingDetailCommand implements Command {

	/** �������p Model �I�u�W�F�N�g */
	private HousingManage housingManage;

	/** �����ڍ׉�ʕ\���p DisplayAdapter */
	private HousingDetailDisplayAdapter displayAdapter;



	
	/**
	 * �������p Model �I�u�W�F�N�g��ݒ肷��B<br>
	 * <br>
	 * @param housingManage �������p Model �I�u�W�F�N�g
	 */
	public void setHousingManage(HousingManage housingManage) {
		this.housingManage = housingManage;
	}

	/**
	 * �����ڍ׉�ʕ\���p DisplayAdapter ��ݒ肷��B<br>
	 * <br>
	 * @param buildingInfoDisp �����ڍ׉�ʗp DisplayAdapter
	 */
	public void setDisplayAdapter(HousingDetailDisplayAdapter displayAdapter) {
		this.displayAdapter = displayAdapter;
	}
	
	
	
	/**
	 * �����ڍ׉�ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// view �w�֓n���ׂ� model �I�u�W�F�N�g
		Map<String, Object> model = new HashMap<>();
		
		// �V�X�e�������b�c�̎擾
		// �����擾�o���Ȃ��ꍇ�̓V�X�e���G���[�Ƃ��ď�������B
		String sysHousingCd = request.getParameter("sysHousingCd");
		if (StringValidateUtil.isEmpty(sysHousingCd)){
			throw new RuntimeException("system housing cd is null");
		}


		// ���������擾����B
		// �Y���f�[�^�����݂��Ȃ��ꍇ�i�ߋ��Ɍf�ڂ���Ă����������j�A�Y������������ʂ�\������B
		Housing housing = this.housingManage.searchHousingPk(sysHousingCd);
		if (housing == null){
			return new ModelAndView("notfound");
		}
		model.put("housing", housing);


		// �g�p���� DisplayAdapter �� model �֊i�[����B
		model.put("displayAdapter", this.displayAdapter);


		return new ModelAndView("success", model);
	}

}
