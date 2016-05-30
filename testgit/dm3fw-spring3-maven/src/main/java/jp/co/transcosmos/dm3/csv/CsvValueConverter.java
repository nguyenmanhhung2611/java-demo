package jp.co.transcosmos.dm3.csv;


/**
 * CSV �o�͎��̕ϊ������p�C���^�[�t�F�[�X<br/>
 * <br/>
 * SimpleCSVModel ���g�p���� CSV �o�͎��ɁA�o�͒l�̕ϊ����s���ꍇ�A���̃C���^�[�t�F�[�X
 * �����������N���X���쐬���ACsvConfig �� csvValueConverter �v���p�e�B�ɐݒ肷��B<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public interface CsvValueConverter {

	/**
	 * �R���o�[�g����<br/>
	 * ���̃��\�b�h�́ACSV �o�͎��A�P�t�B�[���h���Ɏ��s�����B
	 * �ϊ�����ꍇ�͕K�v�ɉ����Ēl��ϊ����ĕ��A���A�ϊ����s�v�ȏꍇ�͌��̒l�����̂܂�
	 * ���A���鎖�B<br/>
	 * <br/>
	 * @param columnName �o�̓t�B�[���h��
	 * @param value ���̃J�����̏o�͒l
	 * @param thisOne CSV �s�f�[�^ �iValue �I�u�W�F�N�g�A�܂��́AJoinResult�j
	 * @return�@�ϊ������l
	 */
	public Object convert(String columnName, Object value, Object thisOne);

}
