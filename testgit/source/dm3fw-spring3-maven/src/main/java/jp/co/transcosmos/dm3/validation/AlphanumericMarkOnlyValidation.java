package jp.co.transcosmos.dm3.validation;

public class AlphanumericMarkOnlyValidation implements Validation {

	/** �g�p�\�L�������� */
	private String marks;


    /**
     * �R���X�g���N�^<br/>
     * �����Ŏw�肵���L�����������p�\�ɂȂ�B<br/>
     * <br/>
     * @param marks �g�p�\�L������
     */
    public AlphanumericMarkOnlyValidation(String marks) {
        this.marks = marks;
    }



	@Override
	public ValidationFailure validate(String name, Object value) {

		// ��r�Ώۂ���̏ꍇ�͐���I��
        if ((value == null) || value.equals("")) {
            return null;
        }

        String valueStr = value.toString().toLowerCase();
        for (int n = 0; n < valueStr.length(); n++) {
            char thisChar = valueStr.charAt(n);
            if ((thisChar >= '0') && (thisChar <= '9')) {
                continue;
            } else if ((thisChar >= 'a') && (thisChar <= 'z')) {
                continue;
            } else {
            	
            	// ������Ă镶�������w��̏ꍇ�A�G���[�Ƃ���B
            	if (this.marks == null || this.marks.length() == 0){
            		// ���̏ꍇ�̃G���[���b�Z�[�W�́A���p�p�����`�F�b�N�Ɠ����G���[���b�Z�[�W�Ƃ��Ĉ���
            		return new ValidationFailure("alphanumericOnly", name, value, null);
            	}

            	// �������L���������ݒ肳��Ă���ꍇ�A���̋L�������ł���΂n�j�Ƃ���B
           		if (marks.indexOf(thisChar) >= 0){
           			continue;
           		}
                return new ValidationFailure("aplhaNumMarkOnly", name, value, new String[]{marks});
            } 
        }

        return null;
	}

}
