package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * ���W�y�[�W�}�X�^�N���X.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.19	�V�K�쐬
 * H.Mizuno		2015.03.12	�摜�֘A�����ǉ�
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class FeaturePageInfo {
	
	/** ���W�y�[�W�h�c */
	private String featurePageId;
	/** ���W�y�[�W�� */
	private String featurePageName;
	/** �R�����g */
	private String featureComment;
	/** �N�G���[������ */
	private String queryStrings;
	/** �\���t���O  0: ��\���A1:�\���@*/
	private String displayFlg;
// 2015.03.12 H.Mizuno �摜�֘A������ǉ� start
	/** �摜�p�X�� */
	private String pathName;
	/** �c���E�����t���O �i0:�����摜�A1:�c���摜�A2:�c������j */
	private String hwFlg;
// 2015.03.12 H.Mizuno �摜�֘A������ǉ� end
	/** �\���J�n�� */
	private Date displayStartDate;
	/** �\���I���� */
	private Date displayEndDate;
	/** �o�^�� */
	private Date insDate;
	/** �o�^�� */
	private String insUserId;
	/** �ŏI�X�V�� */
	private Date updDate;
	/** �ŏI�X�V�� */
	private String updUserId; 



	/**
	 * ���W�y�[�W�h�c ���擾����B<br/>
	 * <br/>
	 * @return ���W�y�[�W�h�c
	 */
	public String getFeaturePageId() {
		return featurePageId;
	}
	
	/**
	 * ���W�y�[�W�h�c ��ݒ肷��B<br/>
	 * <br/>
	 * @param featurePageId�@���W�y�[�W�h�c
	 */
	public void setFeaturePageId(String featurePageId) {
		this.featurePageId = featurePageId;
	}

	/**
	 * ���W�y�[�W�����擾����B<br/>
	 * <br/>
	 * @return ���W�y�[�W��
	 */
	public String getFeaturePageName() {
		return featurePageName;
	}
	
	/**
	 * ���W�y�[�W����ݒ肷��B<br/>
	 * <br/>
	 * @param featurePageName�@���W�y�[�W��
	 */
	public void setFeaturePageName(String featurePageName) {
		this.featurePageName = featurePageName;
	}

	/**
	 * �R�����g���擾����B<br/>
	 * <br/>
	 * @return �R�����g
	 */
	public String getFeatureComment() {
		return featureComment;
	}
	
	/**
	 * �R�����g��ݒ肷��B<br/>
	 * <br/>
	 * @param featureComment �R�����g
	 */
	public void setFeatureComment(String featureComment) {
		this.featureComment = featureComment;
	}

	/**
	 * �N�G���[��������擾����B<br/>
	 * <br/>
	 * @return �N�G���[������
	 */
	public String getQueryStrings() {
		return queryStrings;
	}
	
	/**
	 * �N�G���[�������ݒ肷��B<br/>
	 * <br/>
	 * @param queryStrings�@�N�G���[������
	 */
	public void setQueryStrings(String queryStrings) {
		this.queryStrings = queryStrings;
	}

	/**
	 * �\���t���O���擾����B<br/>
	 * <br/>
	 * @return �\���t���O�i0: ��\���A1:�\���j
	 */
	public String getDisplayFlg() {
		return displayFlg;
	}
	
	/**
	 * �\���t���O��ݒ肷��B<br/>
	 * <br/>
	 * @param displayFlg �\���t���O�i0: ��\���A1:�\���j
	 */
	public void setDisplayFlg(String displayFlg) {
		this.displayFlg = displayFlg;
	}

// 2015.03.12 H.Mizuno �摜�֘A������ǉ� start
	/**
	 * �摜�p�X�����擾����B<br/>
	 * <br/>
	 * @return �摜�p�X��
	 */
	public String getPathName() {
		return pathName;
	}

	/**
	 * �摜�p�X����ݒ肷��B<br/>
	 * <br/>
	 * @param pathName �摜�p�X��
	 */
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	/**
	 * �c���E�����t���O���擾����B<br/>
	 * <br/>
	 * @return �c���E�����t���O �i0:�����摜�A1:�c���摜�A2:�c������j
	 */
	public String getHwFlg() {
		return hwFlg;
	}

	/**
	 * �c���E�����t���O��ݒ肷��B<br/>
	 * <br/>
	 * @param hwFlg �c���E�����t���O �i0:�����摜�A1:�c���摜�A2:�c������j
	 */
	public void setHwFlg(String hwFlg) {
		this.hwFlg = hwFlg;
	}
// 2015.03.12 H.Mizuno �摜�֘A������ǉ� end

	/**
	 * �\���J�n�����擾����B<br/>
	 * <br/>
	 * @return �\���J�n��
	 */
	public Date getDisplayStartDate() {
		return displayStartDate;
	}
	
	/**
	 * �\���J�n����ݒ肷��B<br/>
	 * <br/>
	 * @param displayStartDate�@�\���J�n��
	 */
	public void setDisplayStartDate(Date displayStartDate) {
		this.displayStartDate = displayStartDate;
	}
	
	/**
	 * �\���I�������擾����B<br/>
	 * <br/>
	 * @param displayEndDate�@�\���I����
	 */
	public Date getDisplayEndDate() {
		return displayEndDate;
	}
	
	/**
	 * �\���I������ݒ肷��B<br/>
	 * <br/>
	 * @param displayEndDate �\���I����
	 */
	public void setDisplayEndDate(Date displayEndDate) {
		this.displayEndDate = displayEndDate;
	}
	
	/**
	 * �o�^�����擾����B<br/>
	 * <br/>
	 * @return �o�^��
	 */
	public Date getInsDate() {
		return insDate;
	}
	
	/**
	 * �o�^����ݒ肷��B<br/>
	 * <br/>
	 * @return �o�^��
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * �o�^�ҁi�Ǘ��҃��[�U�[ID�j���擾����B<br/>
	 * <br/>
	 * @return �o�^��
	 */
	public String getInsUserId() {
		return insUserId;
	}
	
	/**
	 * �o�^�ҁi�Ǘ��҃��[�U�[ID�j��ݒ肷��B<br/>
	 * <br/>
	 * @param insUserId �o�^��
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	/**
	 * �ŏI�X�V�����擾����B<br/>
	 * <br/>
	 * @return �ŏI�X�V��
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * �ŏI�X�V����ݒ肷��B<br/>
	 * <br/>
	 * @param updDate �ŏI�X�V��
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	
	/**
	 * �ŏI�X�V�҂��擾����B<br/>
	 * <br/>
	 * @return �ŏI�X�V��
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	/**
	 * �ŏI�X�V�҂�ݒ肷��B<br/>
	 * <br/>
	 * @param updUserId �ŏI�X�V��
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

}
