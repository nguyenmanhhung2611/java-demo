package jp.co.transcosmos.dm3.corePana.vo;

/**
 * �����摜���i�\�[�g�p�j.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.05.08     �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class HousingImageSortInfo extends HousingImageInfo {

	/** temp�p�X�i�傫���摜�j */
	private String tempPath1;
	/** temp�p�X�i�������摜�j */
	private String tempPath2;

    /**
     * temp�p�X�i�傫���摜�j ���擾����B<br/>
     * <br/>
     * @return temp�p�X�i�傫���摜�j
     */
    public String getTempPath1() {
        return tempPath1;
    }

    /**
     * temp�p�X�i�傫���摜�j ��ݒ肷��B<br/>
     * <br/>
     * @param tempPath1
     */
    public void setTempPath1(String tempPath1) {
        this.tempPath1 = tempPath1;
    }

    /**
     * temp�p�X�i�������摜�j ���擾����B<br/>
     * <br/>
     * @return temp�p�X�i�������摜�j
     */
    public String getTempPath2() {
        return tempPath2;
    }

    /**
     * temp�p�X�i�������摜�j ��ݒ肷��B<br/>
     * <br/>
     * @param tempPath1
     */
    public void setTempPath2(String tempPath2) {
        this.tempPath2 = tempPath2;
    }

}
