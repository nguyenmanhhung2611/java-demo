package jp.co.transcosmos.dm3.core.util.imgUtils;


/**
 * �摜���N���X.
 * <p>
 * �摜�t�@�C���̃T�C�Y�����i�[����B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.23	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class ImgInfo {

	/** �摜�̏c�s�N�Z���� */
	private Integer height;
	/** �摜�̉��s�N�Z���� */
	private Integer width;
	/** �c���E�����X�e�[�^�X �i0:�����摜�A1:�c���摜�A2:�c������j*/
	private Integer hwFlg;



	/**
	 * �摜�̏c�s�N�Z�������擾����B<br/>
	 * <br/>
	 * @return �摜�̏c�s�N�Z����
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * �摜�̏c�s�N�Z������ݒ肷��B<br/>
	 * <br/>
	 * @param height �摜�̏c�s�N�Z����
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * �摜�̉��s�N�Z�������擾����B<br/>
	 * <br/>
	 * @return �摜�̉��s�N�Z����
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * �摜�̉��s�N�Z�������擾����B<br/>
	 * <br/>
	 * @param width �摜�̉��s�N�Z����
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * �c���E�����X�e�[�^�X���擾����B<br/>
	 * <br/>
	 * @return 0:�����摜�A1:�c���摜�A2:�c������
	 */
	public Integer getHwFlg() {
		return hwFlg;
	}

	/**
	 * �c���E�����X�e�[�^�X��ݒ肷��B<br/>
	 * <br/>
	 * @param hwFlg 0:�����摜�A1:�c���摜�A2:�c������
	 */
	public void setHwFlg(Integer hwFlg) {
		this.hwFlg = hwFlg;
	}

}
