package jp.co.transcosmos.dm3.corePana.vo;

/**
 * �}�C�y�[�W�A���P�[�g���.
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
public class MemberQuestion {

    /** ���[�U�[ID */
    private String userId;
    /** �A���P�[�g�ԍ� */
    private String categoryNo;
    /** �I������CD */
    private String questionId;
    /** ���̑��񓚓��� */
    private String etcAnswer;

    /**
     * ���[�U�[ID ���擾����B<br/>
     * <br/>
     * @return ���[�U�[ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * ���[�U�[ID ��ݒ肷��B<br/>
     * <br/>
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * �A���P�[�g�ԍ� ���擾����B<br/>
     * <br/>
     * @return �A���P�[�g�ԍ�
     */
    public String getCategoryNo() {
        return categoryNo;
    }
    
    /**
     * �A���P�[�g�ԍ� ��ݒ肷��B<br/>
     * <br/>
     * @param categoryNo
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }
    
    /**
     * �I������CD ���擾����B<br/>
     * <br/>
     * @return �I������CD
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * �I������CD ��ݒ肷��B<br/>
     * <br/>
     * @param questionId
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    /**
     * ���̑��񓚓��� ���擾����B<br/>
     * <br/>
     * @return ���̑��񓚓���
     */
    public String getEtcAnswer() {
        return etcAnswer;
    }

    /**
     * ���̑��񓚓��� ��ݒ肷��B<br/>
     * <br/>
     * @param etcAnswer
     */
    public void setEtcAnswer(String etcAnswer) {
        this.etcAnswer = etcAnswer;
    }
}
