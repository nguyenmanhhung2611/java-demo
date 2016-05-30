package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * �p�X���[�h���x�`�F�b�N����
 * 
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.04	�V�K�쐬
 * 
 * ���ӎ���
 *
 * </pre>
 */
public class PasswordValidation implements Validation {

	/** ���͂���������L��������@*/
	private String signList = null;



	/**
	 * �f�t�H���g�R���X�g���N�^<br/>
	 * <br/>
	 * 
	 */
	public PasswordValidation() {

	}

	/**
	 * �R���X�g���N�^<br/>
	 * ���͂���������L����������w�肷��B<br/>
	 * <br/>
	 * 
	 * @param signList
	 *            �L�����X�g
	 */
	public PasswordValidation(String signList) {
		this.signList = signList;
	}



	/**
	 * �p�X���[�h���x�`�F�b�N�B<br/>
	 * <br/>
	 * 
	 */
	public ValidationFailure validate(String name, Object value) {
		// �󈽂���null�̏ꍇ�A����I��
		if (value == null || "".equals(value.toString())) {
			return null;
		}

		// ��������t���O
		boolean numFlg = false;
		// ���p�p������������t���O
		boolean ��CharFlg = false;
		// ���p�p���啶������t���O
		boolean lCharFlg = false;
		// �L������t���O
		boolean signFlg = false;
		if (StringValidateUtil.isEmpty(this.signList)){
			// �L�������񂪖��ݒ�̏ꍇ�A�L�������̓��͂��������Ȃ��B
			signFlg = true;
		}


		String valueStr = value.toString();
		// �p�X���[�h�̌������A���[�v�Ń`�F�b�N���܂�
		for (int i = 0; i < valueStr.length(); i++) {
			if (valueStr.substring(i, i + 1).matches("[0-9]")) {
				// ��������
				numFlg = true;
			} else if (valueStr.substring(i, i + 1).matches("[a-z]")) {
				// ���p�p������������
				��CharFlg = true;
			} else if (valueStr.substring(i, i + 1).matches("[A-Z]")) {
				// ���p�p���啶������
				lCharFlg = true;
			} else if (signMatches(valueStr.substring(i, i + 1))) {
				// ���p�ł���L������
				signFlg = true;
			}
		}
		// �p�X���[�h�ɕ�����ނ�������Ă���ꍇ�A����I��
		// �L�������p�ł��Ȃ��ꍇ�A���p�ł���L������̃`�F�b�N���v��Ȃ�
		if (numFlg && ��CharFlg && lCharFlg && signFlg) {
			return null;
		}
		// �p�X���[�h�ɕ�����ނ�������Ă��Ȃ��ꍇ
		if (StringValidateUtil.isEmpty(this.signList)) {
			return new ValidationFailure("passwordNG", name, value, null);
		} else {
			return new ValidationFailure("passwordMarkNG", name, value, new String[]{this.signList});
		}
	}



	/**
	 * ���p�ł���L���`�F�b�N�B<br/>
	 * <br/>
	 */
	private boolean signMatches(String valueStr) {
		if (signList != null && signList.contains(valueStr)) {
			return true;
		}
		return false;
	}

}
