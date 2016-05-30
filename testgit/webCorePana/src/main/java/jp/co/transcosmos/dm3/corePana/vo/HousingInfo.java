package jp.co.transcosmos.dm3.corePana.vo;


/**
 * ������{���.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.04.09     �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class HousingInfo extends jp.co.transcosmos.dm3.core.vo.HousingInfo {

    /** ���t�H�[�������i�i�ŏ��j */
    private Long priceFullMin;
    /** ���t�H�[�������i�i�ő�j */
    private Long priceFullMax;
    /** ���t�H�[���������R�����g */
    private String reformComment;
    /** �ŏ��k������ */
    private Integer minWalkingTime;
	/** �������N�G�X�g�����Ώۃt���O */
	private String requestFlg;


    /**
     * ���t�H�[�������i�i�ŏ��j ���擾����B<br/>
     * <br/>
     * @return ���t�H�[�������i�i�ŏ��j
     */
    public Long getPriceFullMin() {
        return priceFullMin;
    }

    /**
     * ���t�H�[�������i�i�ŏ��j ��ݒ肷��B<br/>
     * <br/>
     * @param priceFullMin
     */
    public void setPriceFullMin(Long priceFullMin) {
        this.priceFullMin = priceFullMin;
    }

    /**
     * ���t�H�[�������i�i�ő�j ���擾����B<br/>
     * <br/>
     * @return ���t�H�[�������i�i�ő�j
     */
    public Long getPriceFullMax() {
        return priceFullMax;
    }

    /**
     * ���t�H�[�������i�i�ő�j ��ݒ肷��B<br/>
     * <br/>
     * @param priceFullMax
     */
    public void setPriceFullMax(Long priceFullMax) {
        this.priceFullMax = priceFullMax;
    }

    /**
     * ���t�H�[���������R�����g ���擾����B<br/>
     * <br/>
     * @return ���t�H�[���������R�����g
     */
    public String getReformComment() {
        return reformComment;
    }

    /**
     * ���t�H�[���������R�����g ��ݒ肷��B<br/>
     * <br/>
     * @param reformComment
     */
    public void setReformComment(String reformComment) {
        this.reformComment = reformComment;
    }

    /**
     * �ŏ��k������ ���擾����B<br/>
     * <br/>
     * @return �ŏ��k������
     */
    public Integer getMinWalkingTime() {
        return minWalkingTime;
    }

    /**
     * �ŏ��k������ ��ݒ肷��B<br/>
     * <br/>
     * @param minWalkingTime
     */
    public void setMinWalkingTime(Integer minWalkingTime) {
        this.minWalkingTime = minWalkingTime;
    }

	/**
	 * �������N�G�X�g�����Ώۃt���O���擾����B<br/>
	 * <br/>
	 * @return �������N�G�X�g�����Ώۃt���O
	 */
	public String getRequestFlg() {
		return requestFlg;
	}

	/**
	 * �������N�G�X�g�����Ώۃt���O��ݒ肷��B<br/>
	 * <br/>
	 * @param basicComment�@�������N�G�X�g�����Ώۃt���O
	 */
	public void setRequestFlg(String requestFlg) {
		this.requestFlg = requestFlg;
	}
}
