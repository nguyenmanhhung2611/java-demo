package jp.co.transcosmos.dm3.core.util;

import java.util.Random;

import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * <pre>
 * �����_���p�X���[�h��������
 * 
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.04	�V�K�쐬
 * H.Mizuno		2015.03.27	DI �R���e�i����ݒ��ς�����悤�ɕύX
 * 
 * ���ӎ���
 * �p�X���[�h���������͓��������Ă���̂ŁA���̃N���X�� bean ��`�� Singleton �ɂ��鎖�B
 *
 * </pre>
 */
public class CreatePassword {

	/** ��������p�X���[�h�̌����@ */
	protected int length = 8;

	/**
	 * �p�X���[�h�Ɋ܂߂�L�������@�i��  #!;:�j<br/>
	 * ���̃v���p�e�B�����ݒ�̏ꍇ�A�����_����������p�X���[�h�ɋL�������͊܂߂Ȃ��B<br/>
	 */
	protected String signList = "";

	
	/** �������:�����@ */
	private static final int NUM = 2;
	/** �������:�L���@ */
	private static final int SIGN = 3;
	/** �����_�������͈́@ */
	private static final int NUM_TO = 10;
	/** �����_���p���͈́@ */
	private static final int CHAR_TO = 52;
	/** �������Ƒ啶���敪�@ */
	private static final int SL = 26;
	/** ������from�@ */
	private static final int S_FROM = 97;
	/** �啶��from�@ */
	private static final int L_FROM = 65;
	/** �ő僊�g���C�� */
	private static final int MAX_RETRY = 100;
	
	/** ���������N���X  */
	protected static Random r = new Random();


	
	/**
	 * ��������p�X���[�h�̌�����ݒ肷��B<br/>
	 * �ȗ����� 8 ���Ő�������B<br\>
	 * <br/>
	 * @param length �p�X���[�h�̌���
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * �p�X���[�h�Ɋ܂߂�L��������ݒ肷��B�@�i��  #!;:�j<br/>
	 * ���̃v���p�e�B�����ݒ�̏ꍇ�A�����_����������p�X���[�h�ɋL�������͊܂߂Ȃ��B �i�f�t�H���g���ݒ�j<br/>
	 * <br/>
	 * @param signList �p�X���[�h�Ɋ܂߂�L������
	 */
	public void setSignList(String signList) {
		this.signList = signList;
	}



	/**
	 * �����_���Ńp�X���[�h��������B<br/>
	 * <br/>
	 * 
	 */
	public synchronized String getPassword() {

		// �����A�L����������܂߂Ȃ��ꍇ�A�����̐����͈͂� 0 �` 2 �ɕύX����B
		int maxRndCnt = 4;
		if (StringValidateUtil.isEmpty(this.signList)){
			maxRndCnt = 3;
		}


		// ���������p�X���[�h���v�������𖞂����Ȃ��ꍇ�A�ċN�R�[�����ă��g���C���s���Ă����B
		// �����I�ɂ͖��Ȃ��Ǝv���邪�A�X�^�b�N�ɂ��e����^����̂Ń��[�v�ɕύX�����B
		// �܂��A������A�������[�v�ɂȂ�Ƃ܂����̂ŁA�ő僊�g���C�񐔂�臒l��ݒ肵���B
		// 臒l�ɒB���Ă��v���𖞂����p�X���[�h�������ł��Ȃ��ꍇ�͗�O���X���[����B
		for (int retry=0; retry < MAX_RETRY; ++retry){

			StringBuilder password = new StringBuilder(this.length);

			// ���p�p������������t���O
			boolean ��CharFlg = false;
			// ���p�p���啶������t���O
			boolean lCharFlg = false;
			// ��������t���O
			boolean numFlg = false;
			// �L������t���O �i�L���������܂߂Ȃ��ꍇ�́A�L������t���O�� true �Œ�ɂ���B�j
			boolean signFlg = false;
			if (maxRndCnt == 3) signFlg = true;


			// �p�X���[�h�̌������A���[�v�Ő�������
			for (int i = 0; i < this.length; ++i) {

				// 0 �` 3 �i���́A2�j�͈̔͂ŗ����𐶐�����B
				// ���̒l�ɂ���āA0�A1 �̏ꍇ�͑召�p���A2 �̏ꍇ�͐����A3 �̏ꍇ�͋L��������
				// �����ւƐU�蕪����B
				int numCharSignFlg = r.nextInt(maxRndCnt);

				if (numCharSignFlg == NUM) {

					// 2: ���p�����̐�������
					// 0 �` 9 �̗����𐶐����A���̒l���p�X���[�h�����Ƃ��Ďg�p����B
					password.append(r.nextInt(NUM_TO));
					// ���p��������
					numFlg = true;

				} else if (numCharSignFlg == SIGN) {
					
					// 3: �L�������̐�������
					// signList �����ݒ�̏ꍇ�A�����ɗ���鎖�͂Ȃ��B
					// signList �̕������͈͂ŗ����𐶐����A���̈ʒu�̕������p�X���[�h�����Ƃ��Ďg�p����B
					password.append(signList.charAt(r.nextInt(signList.length())));
					signFlg = true;

				} else {

					// 0�A1: ���p�p���������Ɣ��p�p���啶��
					// 0 �` 51 �܂ł̗����𐶐����A26 �����̏ꍇ�͉p�������Ƃ��Đ����A����ȏ�̏ꍇ��
					// �p�啶���Ƃ��Đ�������B

					int temp = r.nextInt(CHAR_TO);
					// ���p�p���������┼�p�p���啶���𐶐�����
					char x;
					// ���p�p���������̏ꍇ
					if (temp < SL) {
						x = (char) (temp + S_FROM);
						// ���p�p������������
						��CharFlg = true;
					} else {
						// ���p�p���啶���̏ꍇ
						x = (char) ((temp % SL) + L_FROM);
						// ���p�p���啶������
						lCharFlg = true;
					}
					password.append(x);
				}
			}
			
			// ���������p�X���[�h���v�������𖞂����Ă���΁A���̒l�𕜋A����B
			if ((numFlg && ��CharFlg && lCharFlg && signFlg)) {
				return password.toString();
			}
		}

		// 臒l�ɒB���Ă��v���𖞂����p�X���[�h�������ł��Ȃ��ꍇ�͗�O���X���[����B
		throw new RuntimeException("random password generate failed.");
	}

}
