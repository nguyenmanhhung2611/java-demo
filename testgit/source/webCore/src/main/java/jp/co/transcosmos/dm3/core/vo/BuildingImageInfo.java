package jp.co.transcosmos.dm3.core.vo;

/**
 * �����摜���.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 *
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10  �V�K�쐬
 * H.Mizuno		2015.03.16	�}�Ԃ̃f�[�^�^���AString ���� Integer �֕ύX
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class BuildingImageInfo {

	/** �V�X�e������CD */
	private String sysBuildingCd;
	/** �摜�^�C�v */
	private String imageType;
	/** �}�� */
	private Integer divNo;
	/** �\���� */
	private Integer sortOrder;
	/** �p�X�� */
	private String pathName;
	/** ���C���摜�t���O */
	private String mainImageFlg;
	/** �L���v�V���� */
	private String caption;
	/** �R�����g */
	private String imgComment;
	/** �c���E�����t���O */
	private String hwFlg;

	/**
	 * �V�X�e������CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �V�X�e������CD
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}

	/**
	 * �V�X�e������CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param sysBuildingCd
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}

	/**
	 * �摜�^�C�v ���擾����B<br/>
	 * <br/>
	 *
	 * @return �摜�^�C�v
	 */
	public String getImageType() {
		return imageType;
	}

	/**
	 * �摜�^�C�v ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param imageType
	 */
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	/**
	 * �}�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �}��
	 */
	public Integer getDivNo() {
		return divNo;
	}

	/**
	 * �}�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param divNo
	 */
	public void setDivNo(Integer divNo) {
		this.divNo = divNo;
	}

	/**
	 * �\���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �\����
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * �\���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param sortOrder
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * �p�X�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �p�X��
	 */
	public String getPathName() {
		return pathName;
	}

	/**
	 * �p�X�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param pathName
	 */
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	/**
	 * ���C���摜�t���O ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���C���摜�t���O
	 */
	public String getMainImageFlg() {
		return mainImageFlg;
	}

	/**
	 * ���C���摜�t���O ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param mainImageFlg
	 */
	public void setMainImageFlg(String mainImageFlg) {
		this.mainImageFlg = mainImageFlg;
	}

	/**
	 * �L���v�V���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �L���v�V����
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * �L���v�V���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * �R�����g ���擾����B<br/>
	 * <br/>
	 *
	 * @return �R�����g
	 */
	public String getImgComment() {
		return imgComment;
	}

	/**
	 * �R�����g ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param imgComment
	 */
	public void setImgComment(String imgComment) {
		this.imgComment = imgComment;
	}

	/**
	 * �c���E�����t���O ���擾����B<br/>
	 * <br/>
	 *
	 * @return �c���E�����t���O
	 */
	public String getHwFlg() {
		return hwFlg;
	}

	/**
	 * �c���E�����t���O ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param hwFlg
	 */
	public void setHwFlg(String hwFlg) {
		this.hwFlg = hwFlg;
	}
}
