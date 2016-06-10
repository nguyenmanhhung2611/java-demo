package jp.co.transcosmos.dm3.core.model;

import jp.co.transcosmos.dm3.core.model.housing.Housing;


/**
 * �������������쐬����N���X�̃C���^�[�t�F�[�X.
 * <p>
 * �������������쐬����N���X�́A���̃C���^�[�t�F�[�X���������鎖�B<br/>
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.09	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public interface HousingPartCreator {

	/**
	 * ���� model �� proxy �́A�����Ƃ��� ���s���� model �̃��\�b�h���������n���B<br/>
	 * ���̃��\�b�h�̖߂�l�� false �̏ꍇ�AcreatePart() �̎��s���L�����Z������B<br/> 
	 * ���̃��\�b�h�̖߂�l�� true �̏ꍇ�AcreatePart() �����s����B<br/>
	 * <br/>
	 * @return ���s�������Ȃ� model �̃��\�b�h���̏ꍇ�Afalse �𕜋A����B
	 */
	public boolean isExecuteMethod(String methodName);

	
	/**
	 * �����������̍쐬��������������B<br/>
	 * <br/>
	 * @param housing �������������쐬���镨���{�u�W�F�N�g
	 * @throws Exception �����悪�X���[����C�ӂ̗�O
	 */
	public void createPart(Housing housing) throws Exception;
}
