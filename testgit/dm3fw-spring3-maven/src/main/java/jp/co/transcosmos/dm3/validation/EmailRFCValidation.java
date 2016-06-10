package jp.co.transcosmos.dm3.validation;

import java.util.Arrays;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * RFC 822 �Ή��� Email �o���f�[�V����<br/>
 * �������A���S�Ή����Ă����ł͂Ȃ��A�V�X�e���s���ɍ��킹�Ă������d�l��ύX���Ă���B<br/>
 * <ul>
 *   <li>�h���C�����e�����ɂ�� IP�w��@�i�Ⴆ�΁AXXXXX@[192.168.1.1]�̗l�ȏ����j�̓G���[�Ƃ���B</li>
 *   <li>() �ɂ��R�����g�w��@�i�Ⴆ�΁A�ihoge�jXXXXX@trans-cosmos.co.jp �̗l�ȏ����j�̓G���[�Ƃ���B</li>
 *   <li>��L�A����ȈӖ������� () [] �̓_�u���R�[�g�ŃG�X�P�[�v����΃��[�U�[���Ŏg�p�\�����G���[�Ƃ���B</li>
 *   <li>@�@���A�_�u���R�[�g�ŃG�X�P�[�v����΃��[�U�[���Ŏg�p�\�����G���[�Ƃ���B</li>
 *   <li>���[�U�[���̐擪�A�����ł̃s���I�h�A�A�������s���I�h�͒ʏ�G���[�����A�t���[�����[�N�̃��[�����M������
 *   �����I�Ƀ_�u���R�[�g�ŃG�X�P�[�v���鎖���\�Ȃ̂Ő���Ƃ���B</li>
 * </ul>
 * <br/>
 * @author H.Mizuno
 *
 */
public class EmailRFCValidation implements Validation {

	// �g�p�������Ȃ����ꐧ�䕶��
	// RFC �I�ɂ́A���[�U�[���ł���΃_�u���R�[�g�ň͂߂Ύg�p�\�����A�듮��̖�������̂ŃG���[�Ƃ���B
	private static final char NG_CHARS[] = {'"', '(', ')', ',', ':', ';', '<', '>', '[', '\\', ']'};

	// ��
	// Arrays.binarySearch() ���g�p����ꍇ�ANG_CHARS �z��̓\�[�g�ςł���K�v������B
	// ����āA��L�z��̏��Ԃ͈Ӗ�������̂ŕύX����ꍇ�͒��ӂ��鎖�B

	
	
    @Override
    public ValidationFailure validate(String name, Object value) {

    	// �f�[�^����̏ꍇ�A�o���f�[�V�����n�j
        if ((value == null) || value.equals("")) {
            return null;
        }

        // �`�F�b�N�Ώۂ𕶎���ɕϊ�
        String valueStr = value.toString().toLowerCase();


        // note
        // InternetAddress�@�́ARFC-822 �o���f�[�V�����́A�h���C�����ɑ΂��Ă̂݁B
        // ��O���X���[�����͉̂��L�p�^�[���̂݁B
        //   �E�u@�v ���������݂���ꍇ
        //   �E���p�p���A�u.�v�A�u-�v�ȊO�̕������h���C�����Ɋ܂܂��ꍇ
        //   �E�u.�v���h���C�����ŘA������ꍇ�A�u.�v���h���C�����̐擪�A�����Ŏg�p����Ă���ꍇ
        try {
			new InternetAddress(valueStr, true);
		} catch (AddressException e) {
			return new ValidationFailure("email", name, value, null);
		}

        
        // �g�p�֎~������̃`�F�b�N
        // RFC-822 �Ƃ��Ă͎g�p�\�ȕ�����̏ꍇ�ł��A�듮��������ׁA�g�p�֎~�ɂ��Ă���ꍇ������B
        // �܂��A�X�y�[�X�A����сA�\���o���Ȃ�����R�[�h���G���[�Ƃ���B
        for (int n = 0; n < valueStr.length(); n++) {
            char thisChar = valueStr.charAt(n);

            if (Arrays.binarySearch(NG_CHARS, thisChar) >= 0) {
            	// �g�p�֎~���������݂���ꍇ�̓G���[�Ƃ���B
            	return new ValidationFailure("email", name, value, null);

            } else if (thisChar <= 0x20 || thisChar == 0x7f){
            	// �\���s�\�Ȕ��p���䕶�����G���[�Ƃ���B
            	return new ValidationFailure("email", name, value, null);

            }
        }
    	
        return null;
    }

}
