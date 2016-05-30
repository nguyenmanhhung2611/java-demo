package jp.co.transcosmos.dm3.form;

import java.util.Iterator;
import org.apache.commons.fileupload.FileItem;


/**
 * ���݂� FormPopulator �́AFileItem �̔z����T�|�[�g���Ă��Ȃ��B<br/>
 * ���́@Chain �N���X�́A�z��Ή��Ɋg���������\�b�h��񋟂���B<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public class FileArrayFormPopulationFilterChain extends FormPopulationFilterChain {

	// note
	// FileItem �� Filter ���g�p����ꍇ�́A
	// addAllFieldFilter()�AaddPerFieldFilter() �� Filter ��ǉ�����ۂ��A
	// FileArrayFormPopulationFilter �C���^�[�t�F�[�X�����������N���X���g�p���鎖�B


	/**
	 * FileItem�@�z��v���p�e�B�p�� Filter �����B<br/>
	 * <br/>
	 * @param fieldName �t�B�[���h��
	 * @param fileItem �擾�����l
	 * @return
	 */
    public FileItem[] filterFileItem(String fieldName, FileItem[] fileItem) {
        if (this.elements != null) {
            for (Iterator<ChainElement> i = this.elements.iterator(); i.hasNext(); ) {
                ChainElement elm = i.next();
                if ((elm.fieldName == null) || elm.fieldName.equals(fieldName)) {
                    fileItem = ((FileArrayFormPopulationFilter)elm.filter).filterFileItem(fileItem);
                }
            }
        }
        return fileItem;
    }

}
