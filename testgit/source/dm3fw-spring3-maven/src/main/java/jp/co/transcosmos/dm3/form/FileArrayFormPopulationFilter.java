package jp.co.transcosmos.dm3.form;

import org.apache.commons.fileupload.FileItem;

/**
 * ���݂� FormPopulator �́AFileItem �̔z����T�|�[�g���Ă��Ȃ��B<br/>
 * ���̃C���^�[�t�F�[�X�́A�z��Ή��Ɋg�������C���^�[�t�F�[�X��񋟂���B<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public interface FileArrayFormPopulationFilter extends FormPopulationFilter {

	/**
	 * FileItem ���z��Ő錾����Ă���ꍇ�p�� Filter �C���^�[�t�F�[�X<br/>
	 * <br/>
	 * @param input FileImte �̔z��I�u�W�F�N�g
	 * @return FileImte �̔z��I�u�W�F�N�g
	 */
    public FileItem[] filterFileItem(FileItem[] input);

}
