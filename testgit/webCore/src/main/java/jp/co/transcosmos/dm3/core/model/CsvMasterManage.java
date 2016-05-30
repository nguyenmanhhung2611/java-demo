package jp.co.transcosmos.dm3.core.model;

import java.io.InputStream;
import java.util.List;

import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * CSV �t�@�C���ɂ��}�X�^�����e�i���X model �p�C���^�[�t�F�[�X.
 * <p/>
 * �w�}�X�^�A����сA�Z���}�X�^�������e�i���X���� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
 * <p/>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.05	�V�K�쐬
 * </pre>
 * <p/>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public interface CsvMasterManage {

	/**
	 * �}�X�^�����e�i���X�p CSV �̎�荞�ݏ�������������B<br/>
	 * <br/>
	 * @param inputStream CSV �t�@�C���� InputStream �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * @param errors �G���[�I�u�W�F�N�g�̃��X�g
	 * 
	 * @return ���펞 true�ACSV �̃o���f�[�V�����ŃG���[������ꍇ false
	 * 
	 * @exception Exception
	 */
	public boolean csvLoad(InputStream inputStream, String editUserId, List<ValidationFailure> errors)
			throws Exception;
	
}
