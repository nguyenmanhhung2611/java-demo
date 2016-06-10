package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �o���f�[�V�������b�Z�[�W�ɍs����\������ׂ� Adapter �N���X.
 * <p>
 * �G���[���b�Z�[�W�ɍs�ԍ����܂߂�ꍇ�A�o���f�[�V�����N���X������ Adapter �N���X�Ń��b�v����B<br/>
 * <br/>
 * �P�O�s�ڂ̃G���[�Ƃ��ďo�͂���ꍇ�̗�j<br/>
 * ValidationChain chain = new ValidationChain("label", value);<br/>
 * chain.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(),10));<br/>
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
public class LineAdapter implements Validation {

	/** �Ϗ���o���f�[�V�����N���X */
	private Validation validation;
	/** �s�ԍ� */
	private int lineNo;

	

	/**
	 * �R���X�g���N�^�[<br/>
	 * <br/>
	 * @param validation �Ϗ���o���f�[�V����
	 * @param lineNo �s�ԍ�
	 */
	public LineAdapter(Validation validation, int lineNo){
		this.validation = validation;
		this.lineNo = lineNo;
	}


	
	/**
	 * �o���f�[�V��������<br/>
	 * <br/>
	 * @param name �G���[�t�B�[���h�̃��x����
	 * @param value �`�F�b�N�Ώےl
	 */
	@Override
	public ValidationFailure validate(String name, Object value) {

		// �Ϗ���̃o���f�[�V���������s����B
		// ������O�����������ꍇ�́ALineValidationFailure �̃C���X�^���X�𐶐����ĕ��A����B
		// LineValidationFailure �́AValidationFailure�@�̊g���N���X�ōs�ԍ��̃v���p�e�B
		// ��ێ�����B
		ValidationFailure failure = this.validation.validate(name, value);
		if (failure == null) return null;

		return new LineValidationFailure(failure, this.lineNo);
	}

}
