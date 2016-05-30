package jp.co.transcosmos.dm3.validation;

/**
 * �����`�F�b�N�o���f�[�V����<br/>
 * 0 �` 9 �܂ł̐����ō\������Ă��邩���`�F�b�N����B �}�C�i�X�L�����G���[�Ƃ���B<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public class NumericValidation implements Validation {

	@Override
	public ValidationFailure validate(String name, Object value) {

        if ((value == null) || value.equals("")) {
            return null;
        }

        String valueStr = value.toString().toLowerCase();
        for (int n = 0; n < valueStr.length(); n++) {
            char thisChar = valueStr.charAt(n);
            if ((thisChar >= '0') && (thisChar <= '9')) {
                continue;
            } else {
                return new ValidationFailure("positNumberOnly", name, value, null);
            } 
        }
        return null;

	}

}
