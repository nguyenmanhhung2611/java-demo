package jp.co.transcosmos.dm3.corePana.util;

import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.vo.AdminLog;
import jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingRequestInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.corePana.vo.Information;
import jp.co.transcosmos.dm3.corePana.vo.InquiryAssessment;
import jp.co.transcosmos.dm3.corePana.vo.InquiryGeneral;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHeader;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousing;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.corePana.vo.RecentlyInfo;


/**
 * �o���[�I�u�W�F�N�g�̃C���X�^���X���擾���� Factory �N���X.
 * <p>
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B<br/>
 * ��core ���Ƃ� Value �I�u�W�F�N�g�̃p�X���قȂ�̂Œ��ӂ��鎖�B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	�V�K�쐬
 * H.Mizuno		2015.03.12	���t���N�V�����Ή�
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 * ValueObjectFactory�@�ŃC���X�^���X�������s���Ă���APanasonic �Ōp�����Ă���N���X�̏ꍇ�A
 * �K�����̃N���X�ŃI�[�o�[���C�h���鎖�B
 *
 */
public class PanaValueObjectFactory extends ValueObjectFactory {



	/**
	 * �w�肳�ꂽ�N���X���i�V���v�����j�ɊY������o���[�I�u�W�F�N�g�̃C���X�^���X�𕜋A����B<br/>
	 * <br/>
	 * �o���[�I�u�W�F�N�g���g�������ꍇ�A���̃��\�b�h���I�[�o�[���C�h���Ċg�������N���X�𕜋A����l�ɃJ�X�^�}�C�Y���鎖�B<br/>
	 * <br/>
	 * @param shortClassName�@ �N���X���i�V���v�����j
	 *
	 * @return�@�o���[�I�u�W�F�N�g�̃C���X�^���X
	 *
	 */
	@Override
	protected Object buildValueObject(String shortClassName) {

		Object vo = null;

		if ("HousingInfo".equals(shortClassName)){
			// ������{���̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new HousingInfo();

		} else if ("HousingStatusInfo".equals(shortClassName)) {
			// �����X�e�[�^�X���̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new HousingStatusInfo();

		} else if ("HousingImageInfo".equals(shortClassName)) {
			// �����摜���̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new HousingImageInfo();

		} else if ("MemberInfo".equals(shortClassName)) {
			// ������̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new MemberInfo();

		} else if ("PrefMst".equals(shortClassName)) {
			// �s���{���}�X�^���̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new PrefMst();

		} else if ("Information".equals(shortClassName)){
			// ���m�点���̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new Information();

		} else if ("HousingRequestInfo".equals(shortClassName)){
			//�������N�G�X�g�̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new HousingRequestInfo();

		} else if ("RecentlyInfo".equals(shortClassName)){
			//�������N�G�X�g�̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new RecentlyInfo();

		} else if ("InquiryAssessment".equals(shortClassName)){
			//�������N�G�X�g�̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new InquiryAssessment();

		} else if ("InquiryHeader".equals(shortClassName)){
			//�������N�G�X�g�̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new InquiryHeader();

		} else if ("InquiryHousing".equals(shortClassName)){
			//�������N�G�X�g�̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new InquiryHousing();

		} else if ("InquiryHousingQuestion".equals(shortClassName)){
			//�������N�G�X�g�̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new InquiryHousingQuestion();

		} else if ("InquiryGeneral".equals(shortClassName)){
			//�ėp�⍇���̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new InquiryGeneral();

		} else if ("AdminLoginInfo".equals(shortClassName)){
			//�ėp�⍇���̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new AdminLoginInfo();
			
		} else if ("AdminLog".equals(shortClassName)){
			//�Ǘ��T�C�g���O�̏ꍇ�A Panasonic �p�̃I�u�W�F�N�g�𕜋A����B
			vo = new AdminLog();

		} else {
			// �g������Ă��Ȃ��ꍇ�A�I���W�i���̃I�u�W�F�N�g�𕜋A
			return super.buildValueObject(shortClassName);

		}

		// Default �l��ݒ�
		setDefaultValue(vo);
		return vo;

	}

}
