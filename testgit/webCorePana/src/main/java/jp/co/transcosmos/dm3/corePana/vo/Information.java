package jp.co.transcosmos.dm3.corePana.vo;


/**
 * ���m�点���.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10     �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class Information extends jp.co.transcosmos.dm3.core.vo.Information {

    /** ���[�����M�t���O */
    private String sendFlg;

    /**
     * ���[�����M�t���O ���擾����B<br/>
     * <br/>
     * @return ���[�����M�t���O
     */
    public String getSendFlg() {
        return sendFlg;
    }

    /**
     * ���[�����M�t���O ��ݒ肷��B<br/>
     * <br/>
     * @param sendFlg
     */
    public void setSendFlg(String sendFlg) {
        this.sendFlg = sendFlg;
    }
}
