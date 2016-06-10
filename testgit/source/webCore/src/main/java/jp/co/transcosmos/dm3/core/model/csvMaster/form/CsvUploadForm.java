package jp.co.transcosmos.dm3.core.model.csvMaster.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.validation.FileUploadedValidator;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;

/**
 * �w�}�X�^CSV�A�Z���}�X�^CSV ��荞�ݏ����p�t�H�[��.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	Shamaison ���Q�l�ɐV�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class CsvUploadForm implements Validateable {

	/** �A�b�v���[�h�t�@�C�� */
	private FileItem csvFile;


	
	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected CsvUploadForm(){
		super();
	}

	
	
	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @return ���펞 true�A�G���[�� false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {

		int startSize = errors.size();

		// �t�@�C���A�b�v���[�h�̃`�F�b�N
		validCsvFile(errors);
		// �g���q�`�F�b�N
		validNotCsv(errors, startSize);

		return startSize == errors.size();

	}

	/**
	 * �t�@�C���A�b�v���[�h �o���f�[�V����<br/>
	 * �E�t�@�C���A�b�v���[�h�̃`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validCsvFile(List<ValidationFailure> errors) {
		// �t�@�C���A�b�v���[�h�̃`�F�b�N
		ValidationChain valid = new ValidationChain("master.csvFile.file", this.csvFile);
		valid.addValidation(new FileUploadedValidator());
		valid.validate(errors);
	}

	/**
	 * �g���q �o���f�[�V����<br/>
	 * �E�t�@�C���������ݒ�̃`�F�b�N
	 * �E�啶���A�������𖳎����Ċg���q�� .csv�̃`�F�b�N
	 * �E�t�@�C���������`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validNotCsv(List<ValidationFailure> errors, int startSize) {
		// �g���q�`�F�b�N
		if (startSize == errors.size()) {
			String fileName = this.csvFile.getName();
			ValidationFailure notCsvFailure = new ValidationFailure("notCsv", "", "", null);

			if (fileName == null) {
				// �t�@�C���������ݒ�̏ꍇ�̓G���[
				errors.add(notCsvFailure);

			} else {

				// �啶���A�������𖳎����Ċg���q�� .csv �łȂ���΃G���[
				if (!fileName.toLowerCase().endsWith(".csv")){
					errors.add(notCsvFailure);
				}

				// �g���q�� .csv �̏ꍇ�A�t�@�C�������� 4�����ȏ�̂Ȃ�̂ŁA���̃`�F�b�N���s���B
				if (fileName.length() <= 4){
					errors.add(notCsvFailure);
				}
			}
		}
	}


	
	/**
	 * �A�b�v���[�h�t�@�C���̏����擾����B<br/>
	 * <br/>
	 * @return �A�b�v���[�h�t�@�C�����
	 */
	public FileItem getCsvFile() {
		return this.csvFile;
	}

	/**
	 * �A�b�v���[�h�t�@�C���̏���ݒ肷��B<br/>
	 * <br/>
	 * @param csvFile �A�b�v���[�h�t�@�C�����
	 */
	public void setCsvFile(FileItem csvFile) {
		this.csvFile = csvFile;
	}

}
