package jp.co.transcosmos.dm3.corePana.vo;

/**
 * �����摜���.
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
public class HousingImageInfo extends jp.co.transcosmos.dm3.core.vo.HousingImageInfo {

    /** �{������ */
    private String roleId;

    /**
     * �{������ ���擾����B<br/>
     * <br/>
     * @return �{������
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * �{������ ��ݒ肷��B<br/>
     * <br/>
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
