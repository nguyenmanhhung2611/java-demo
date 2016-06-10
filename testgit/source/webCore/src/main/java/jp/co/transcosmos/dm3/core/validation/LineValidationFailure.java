package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �s����\������ׂ̊g���G���[�I�u�W�F�N�g.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.20	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class LineValidationFailure extends ValidationFailure {

	/** �s�ԍ��i�G���[���b�Z�[�W�Ɏg�p����B�j */
	private int lineNo;

	
	/**
	 * �R���X�g���N�^�[<br/>
	 * <br/>
	 * @param type �G���[�̎��
	 * @param name �o���f�[�V�����̓��̓��x����
	 * @param value �`�F�b�N�Ώےl
	 * @param extraInfo �I�u�V�������
	 */
	protected LineValidationFailure(String type, String name, Object value, String[] extraInfo) {
		super(type, name, value, extraInfo);
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * �ʏ�͂��̃R���X�g���N�^�[���g�p����B<br/>
	 * <br/>
	 * @param failure �Ϗ���̃o���f�[�V�����N���X
	 * @param lineNo �s�ԍ�
	 */
	public LineValidationFailure(ValidationFailure failure, int lineNo) {
		super(failure.getType(), failure.getName(), failure.getValue(), failure.getExtraInfo());
		this.lineNo = lineNo;
	}


	
	/**
	 * �s�ԍ����擾����B<br/>
	 * <br/>
	 * @return �s�ԍ�
	 */
	public int getLineNo() {
		return lineNo;
	}

	/**
	 * �s�ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param lineNo �s�ԍ�
	 */
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
}
