package jp.co.transcosmos.dm3.adminCore.masterManage.command;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.CsvMasterManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.csvMaster.form.CsvUploadForm;
import jp.co.transcosmos.dm3.core.model.csvMaster.form.CsvUploadFormFactory;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * �}�X�^�[���X�V�p�A�b�v���[�h���.
 * <p>
 * �}�X�^�[���X�V�p�� CSV �t�@�C�����A�b�v���[�h���A�}�X�^�[�����X�V����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class MasterCsvUploadCommand implements Command {

	/** �w�}�X�^�����e�i���X���s�� Model �̃C���X�^���X */
	protected CsvMasterManage csvMasterManage;
	
	
	
	/**
	 * �w�}�X�^�����e�i���X���s�� Model �̃C���X�^���X��ݒ肷��B<br/>
	 * <br/>
	 * @param stationManage �w�}�X�^�����e�i���X���s�� Model
	 */
	public void setCsvMasterManage(CsvMasterManage csvMasterManage) {
		this.csvMasterManage = csvMasterManage;
	}
	


	/**
	 * CSV �t�@�C���ɂ��}�X�^�[���X�V����<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
        // ���N�G�X�g�p�����[�^���擾
		CsvUploadForm form = createForm(request);
		

		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!form.validate(errors)) {
			return new ModelAndView("validFail", "errors", errors);
		}

		
		// ���O�C�����[�U�[�̏����擾
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);


		// CSV �t�@�C���̃��[�h�������s
		try (InputStream inputStream = form.getCsvFile().getInputStream()){
			boolean ret = this.csvMasterManage.csvLoad(inputStream, (String)loginUser.getUserId(), errors);

			// ��荞�ݎ��ɃG���[���������Ă���ꍇ
			if (!ret){
				return new ModelAndView("validFail", "errors", errors);
			}
		}


		return new ModelAndView("success");
	}

	

	/**
	 * ���N�G�X�g�p�����[�^���� Form �I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g
	 */
	protected CsvUploadForm createForm(HttpServletRequest request) {

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		CsvUploadFormFactory factory = CsvUploadFormFactory.getInstance(request);
		return factory.createCsvUploadForm(request);

	}
}
