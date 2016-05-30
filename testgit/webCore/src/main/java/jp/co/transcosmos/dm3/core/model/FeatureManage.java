package jp.co.transcosmos.dm3.core.model;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.feature.form.FeatureSearchForm;
import jp.co.transcosmos.dm3.core.vo.FeaturePageInfo;


/**
 * ���W�̏����Ǘ����� Model �N���X�p�C���^�[�t�F�[�X.
 * <p>
 * ���W�̏��𑀍삷�� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
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
public interface FeatureManage {

	
	/**
	 * �w�肳�ꂽ�O���[�v�h�c�œ��W���̃��X�g���擾����B<br/>
	 * <br/>
	 * @param featureGroupId ���W�O���[�vID
	 * 
	 * @return�@���W�y�[�W���̃��X�g�I�u�W�F�N�g
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<FeaturePageInfo> searchFeature(String featureGroupId) throws Exception;
	
	
	
	/**
	 * �w�肳�ꂽ���������i���WID�A����уy�[�W�ʒu�j�œ��W�ɊY�����镨���̏����擾����B<br/>
	 * <br/>
	 * @param searchForm ���W�̌��������i���WID�A�y�[�W�ʒu�j
	 * 
	 * @return �Y������
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int searchHousing (FeatureSearchForm searchForm) throws Exception;
		
}
