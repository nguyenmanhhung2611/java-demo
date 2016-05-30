package jp.co.transcosmos.dm3.validation;

/**
 * �\�i�����̃`�F�b�N<br/>
 * �������A�������̌������w�肳�ꂽ���x�������`�F�b�N����B<br/>
 * �g�p�\�ȕ����́A0 �` 9�A�s���I�h�P�ƂȂ�B
 * 
 * ���ϐ��̃J�X�^���o���f�[�V�������J�X�^�}�C�Y���Ď���
 * 
 * @author H.Mizuno
 *
 */
public class DecimalValidation implements Validation {

	/** ���������� */
	private int num;
	/** ���������� */
	private int dec;


	/**
	 * �R���X�g���N�^
	 * @param num �s���I�h�̔����ɂ����A���l�S�̂̌���
	 * @param dec �������̌���
	 */
	public DecimalValidation(int num, int dec) {
	    this.num = num - dec;
	    this.dec = dec;
	}

	
	
	@Override
	public ValidationFailure validate(String name, Object value) {

		// null�A�󕶎��̏ꍇ�͒ʉ�
		if ((value == null) || value.equals("")) {
			return null;
		}

		String target = value.toString();
		String array[] = target.split("\\.");

		// �s���I�h�݂̂̏ꍇ�A�z��̃T�C�Y�� 0 �ɂȂ�B
		// ���̏ꍇ�̓G���[�𕜋A����B
		if (array.length == 0) {
			return failure(name, value);
		}


		// �\�������񂪐��l���̃`�F�b�N
		for (String str : array){

	        for (int n = 0; n < str.length(); n++) {
	            char thisChar = str.charAt(n);
	            if ((thisChar >= '0') && (thisChar <= '9')) {
	                continue;
	            } else {
	            	return failure(name, value);
	            } 
	        }
		}

		// �s���I�h�̐����`�F�b�N �i2�ȏ�̓G���[�j
		if (array.length >= 3) {
        	return failure(name, value);
		}

		// �������̌����`�F�b�N
		if(array[0].length() > this.num) {
        	return failure(name, value);
		}

		// �s���I�h�̓��͂�����ꍇ�A�������̌����`�F�b�N
		if (array.length == 2 && array[1].length() > this.dec) {
			return failure(name, value);
		}

		return null;
	}

	

	/**
	 * �G���[�I�u�W�F�N�g�𕜋A����B<br/>
	 * <br/>
	 * @param name�@���̓t�B�[���h�̃��x����
	 * @param value ���͒l
	 * 
	 * @return �G���[�I�u�W�F�N�g
	 */
	protected ValidationFailure failure(String name, Object value){

		return new ValidationFailure("decimal", name, value.toString(),
									 new String[] {String.valueOf(this.num), String.valueOf(this.dec)});
		
	}
}
