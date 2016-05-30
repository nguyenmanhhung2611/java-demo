package jp.co.transcosmos.dm3.corePana.model.information.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.core.vo.InformationTarget;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.validation.DateFromToValidation;
import jp.co.transcosmos.dm3.corePana.validation.UrlValidation;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.util.StringUtils;

/**
 * <pre>
 * ���m�点�����e�i���X�̓��̓p�����[�^����p�t�H�[��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * zhang		2015.04.21	�V�K�쐬
 *
 * ���ӎ���
 * ���� Form �̃o���f�[�V�����́A�g�p���郂�[�h�i�ǉ����� or �X�V�����j�ňقȂ�̂ŁA
 * �t���[�����[�N���񋟂��� Validateable �C���^�[�t�F�[�X�͎������Ă��Ȃ��B
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B
 *
 * </pre>
 */
public class PanaInformationForm extends InformationForm implements
		Validateable {

	/** ���[���A�h���X */
	private String email;

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email �Z�b�g���� email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * ���� Form �N���X�̃o���f�[�V�������\�b�h�́AValidateable �C���^�[�t�F�[�X�̎����ł͖����̂ŁA
	 * �o���f�[�V�������s���̈������قȂ�̂ŁA���� Form ���܂Ƃ߂ăo���f�[�V��������ꍇ�Ȃǂ͒���
	 * ���鎖�B<br/>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 * @return ���펞 true�A�G���[�� false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();
		// ���m�点��ʓ��̓`�F�b�N
        validInformationType(errors);

        // ���J�Ώۋ敪���̓`�F�b�N
        validDspFlg(errors);

        // �������̏ꍇ
        if (!StringUtils.isEmpty(this.getDspFlg()) && PanaCommonConstant.DSP_FLG_PRIVATE.equals(this.getDspFlg())) {
            // ������̓`�F�b�N
        	if (StringValidateUtil.isEmpty(this.getUserId())) {
                ValidationFailure vf = new ValidationFailure(
                        "informationError", "������", "�������e�L�X�g", null);
                errors.add(vf);
            }
        }

        // �^�C�g�����̓`�F�b�N
        validTitle(errors);

        // ���m�点���e���̓`�F�b�N
        validInformationMsg(errors);

        // �J�n�� ���̓`�F�b�N
        validStartDate(errors);

        // �I���� ���̓`�F�b�N
        validEndDate(errors);

		// �\�����Ԃ̓��t��r�`�F�b�N
		ValidationChain valMemberFnameKana = new ValidationChain("information.input.startDate",this.getStartDate());
        valMemberFnameKana.addValidation(new DateFromToValidation("yyyy/MM/dd", "�\�����ԏI����", this.getEndDate()));
        valMemberFnameKana.validate(errors);

        // �����N�� URL ���̓`�F�b�N
        validInputUrl(errors);

        //����ƃ��[�����M�̊֘A�`�F�b�N
        validDspMail(errors);

		return (startSize == errors.size());

	}

	/**
	 * �����N�� URL �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E���p�p���L���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validInputUrl(List<ValidationFailure> errors) {
        // �����N�� URL ���̓`�F�b�N
        ValidationChain valUrl = new ValidationChain("information.input.url", this.getUrl());
        // �����`�F�b�N
        valUrl.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.input.url", 255)));
        // ���p�p���L���`�F�b�N
        valUrl.addValidation(new AsciiOnlyValidation());
        // URL�̃t�H�[�}�b�g�`�F�b�N
        if (!StringValidateUtil.isEmpty(this.getUrl())) {
        	valUrl.addValidation(new UrlValidation());
        }
        valUrl.validate(errors);
	}

	/**
	 * ����ƃ��[�����M�̊֘A�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validDspMail(List<ValidationFailure> errors) {
        // �����N�� URL ���̓`�F�b�N
        if(PanaCommonConstant.SEND_FLG_1.equals(this.getMailFlg()) && !PanaCommonConstant.DSP_FLG_PRIVATE.equals(this.getDspFlg()))
        {
        	ValidationFailure vf = new ValidationFailure(
                    "informationMailError", "���[�����M", "������", null);
            errors.add(vf);
        }
	}



	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param information
	 *            Information�@�����������A���m�点���Ǘ��p�o���[�I�u�W�F�N�g
	 * @param informationTarget
	 *            InformationTarget �������������m�点���J����Ǘ��p�o���[�I�u�W�F�N�g
	 * @param mypageUserInterface
	 *            MypageUserInterface �����������}�C�y�[�W������Ǘ��p�o���[�I�u�W�F�N�g
	 */
	@Override
	public void setDefaultData(Information information,
			InformationTarget informationTarget,
			MypageUserInterface mypageUserInterface) {

		super.setDefaultData(information, informationTarget,
				mypageUserInterface);

		// ���[�����MFLG
		this.setMailFlg(((jp.co.transcosmos.dm3.corePana.vo.Information) information)
				.getSendFlg());
		// ���[���A�h���X
		this.setEmail(mypageUserInterface.getEmail());

	}

	/**
	 * �����œn���ꂽ���m�点���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 *
	 * @param information
	 *            �l��ݒ肷�邨�m�点���̃o���[�I�u�W�F�N�g
	 *
	 */
	@Override
	public void copyToInformation(Information information, String editUserId) {

		super.copyToInformation(information, editUserId);

		((jp.co.transcosmos.dm3.corePana.vo.Information) information)
				.setSendFlg(this.getMailFlg());

	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 *
	 * @param lengthUtils
	 *            �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param codeLookupManager
	 *            ���ʃR�[�h�ϊ�����
	 */
	PanaInformationForm(LengthValidationUtils lengthUtils,
			CodeLookupManager codeLookupManager) {
		super(lengthUtils, codeLookupManager);
		this.setMailFlg("0");
	}

}
