package jp.co.transcosmos.dm3.core.vo;

/**
 * �o�^���O�H���}�X�^�N���X.
 * <p>
 * ���̃}�X�^�ɐݒ肳�ꂽ�H��CD �́ACSV �ɂ��}�X�^�����e�i���X�̑ΏۊO�ɂȂ�B<br/>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * �ד��@��		2006.12.29	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class UnregistRouteMst {

	/**�@CSV ��荞�ݑΏۊO�ƂȂ�H��CD */
	private String routeCd;
	
	/** CSV ��荞�ݑΏۊO�ƂȂ�H���� */	
	private String routeName;



	/**
	 * CSV ��荞�ݑΏۊO�ƂȂ�H��CD ���擾����B<br/>
	 * <br/>
	 * @return ��荞�ݑΏۊO�ƂȂ�H��CD
	 */
	public String getRouteCd() {
		return routeCd;
	}
	
	/**
	 * CSV ��荞�ݑΏۊO�ƂȂ�H��CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param routeCd ��荞�ݑΏۊO�ƂȂ�H��CD
	 */
	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}
	
	/**
	 * CSV ��荞�ݑΏۊO�ƂȂ�H�������擾����B<br/>
	 * <br/>
	 * @return CSV ��荞�ݑΏۊO�ƂȂ�H����
	 */
	public String getRouteName() {
		return routeName;
	}
	
	/**
	 * CSV ��荞�ݑΏۊO�ƂȂ�H������ݒ肷��B<br/>
	 * <br/>
	 * @param routeName CSV ��荞�ݑΏۊO�ƂȂ�H����
	 */
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

}
