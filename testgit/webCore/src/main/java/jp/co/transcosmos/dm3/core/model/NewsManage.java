package jp.co.transcosmos.dm3.core.model;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.news.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.news.form.NewsSearchForm;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * ���m�点�����Ǘ����� Model �N���X�p�C���^�[�t�F�[�X.
 * <p>
 * ���m�点�������𑀍삷�� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.16	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public interface NewsManage {

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ł��m�点����V�K�ǉ�����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * ���m�点�ԍ� �͎����̔Ԃ����̂ŁAInformationForm �� informationNo �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
	 * <br/>
	 * @param inputForm ���m�点���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return ���m�点�ԍ�
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public String addInformation(NewsForm inputForm, String editUserId)
			throws Exception;



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ł��m�点�����X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * InformationForm �� informationNo �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * @param inputForm ���m�点���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return 0 = ����I���A-2 = �X�V�ΏۂȂ�
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	public void updateInformation(NewsForm inputForm, String editUserId)
			throws Exception, NotFoundException;


	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ł��m�点�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * InformationSearchForm �� informationNo �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��č폜����B
	 * �܂��A�폜�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�ł�����I���Ƃ��Ĉ������B<br/>
	 * <br/>
	 * @param inputForm ���m�点���̌����l�i�폜�ΏۂƂȂ� informationNo�j���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
    public void delNews(NewsForm inputForm) throws Exception;


	/**
	 * ���m�点�����������A���ʃ��X�g�𕜋A����B�i�Ǘ���ʗp�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A���m�点������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * ���������Ƃ��āA���J�Ώۋ敪���n���ꂽ�ꍇ�A�ȉ��̃f�[�^�������ΏۂƂ���B<br/>
	 * 
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * 
	 * @return �Y������
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int searchAdminNews(NewsSearchForm searchForm) throws Exception;
	
	
	
	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�u���m�点�ԍ��v �i��L�[�l�j�ɊY�����邨�m�点���𕜋A����B�i�T�C�g TOP �p�j<br/>
	 * ���J�Ώۋ敪 = �u�����܂ޑS����v���擾�ΏۂɂȂ�B<br/>
	 * �܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param informationNo �擾�ΏۂƂȂ邨�m�点�ԍ�
	 * 
	 * @return�@���m�点���
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public News searchTopNewsPk(String newsId)
			throws Exception;

}
