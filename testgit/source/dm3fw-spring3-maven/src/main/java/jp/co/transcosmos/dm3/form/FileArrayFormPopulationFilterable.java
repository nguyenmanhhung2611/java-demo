package jp.co.transcosmos.dm3.form;

/**
 * ���݂� FormPopulator �́AFileItem �̔z����T�|�[�g���Ă��Ȃ��B<br/>
 * ���̃C���^�[�t�F�[�X�́A�z��Ή��Ɋg�������C���^�[�t�F�[�X��񋟂���B<br/>
 * <br/>
 * FormPopulationFilterable �C���^�[�t�F�[�X�́AForm �� Filter ����������ꍇ�Ɏg�p
 * ����C���^�[�t�F�[�X�ɂȂ�B<br/>
 * ���̃C���^�[�t�F�[�X���������Ă� Form �N���X�̏ꍇ�AFormPopulator �́AFormPopulationFilterChain
 * �̑���� FileArrayFormPopulationFilterChain ���g�p����B<br/>
 * FileArrayFormPopulationFilterChain �́AFileItem �̔z�� �v���p�e�B���T�|�[�g���Ă���B<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public interface FileArrayFormPopulationFilterable extends FormPopulationFilterable {

	// FormPopulationFilterable �Ƃ̍\���I�ȈႢ�͖����B
	// �ǂ���� Chain ���g�p���邩�̎��ʗp�C���^�[�t�F�[�X�B
}
