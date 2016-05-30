package jp.co.transcosmos.dm3.corePana.model.inquiry.dao;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * �����⍇�� model �̎����N���X.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C		2015.04.02	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public interface HousingInquiryDAO {
	/**
	 * �⍇�ꗗ�����������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� List<Inquiry> �I�u�W�F�N�g�Ɋi�[����A�擾����List<Inquiry>��߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            ���������̊i�[�I�u�W�F�N�g
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<JoinResult> housingInquirySearch(PanaInquirySearchForm searchForm);

	/**
	 * �⍇�ꗗ�����������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� List<Housing> �I�u�W�F�N�g�Ɋi�[����A�擾����List<Housing>��߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            ���������̊i�[�I�u�W�F�N�g
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 * @param inquiryManage
	 *            �����⍇�� model
	 * @param generalInquiryManage
	 *            �ėp�⍇�� model
	 * @param assessmentInquiryManage
	 *            ����⍇�� model
	 * @throws IOException
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void housingInquirySearch(PanaInquirySearchForm searchForm,
			HttpServletResponse response,InquiryManage inquiryManage, InquiryManage generalInquiryManage,InquiryManage assessmentInquiryManage) throws IOException;

}
