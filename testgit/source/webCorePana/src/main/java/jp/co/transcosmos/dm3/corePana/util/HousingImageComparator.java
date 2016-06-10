package jp.co.transcosmos.dm3.corePana.util;

import java.util.Comparator;

import jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo;

/**
 * <pre>
 * �����摜���� Comparator �N���X
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Y.Zhang		2015.05.08	�V�K�쐬
 *
 *
 * </pre>
 */
public class HousingImageComparator implements Comparator<HousingImageInfo> {

	/** �������[�h (true = �}�Ԑݒ�p�\�[�g�Afalse = ��ʕ\���p�\�[�g) */
	private boolean sortForDivNo = false;

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	public HousingImageComparator(boolean sortForDivNo) {
		this.sortForDivNo = sortForDivNo;
	}


	/**
	 * �����摜���̓��e���r����B<br/>
	 * <br/>
	 * @param image1 �����摜���P
	 * @param image2 �����摜���Q
	 * @return -1�F���������������A0�F�������Ƒ��������������A1�F���������傫��
	 */
	@Override
	public int compare(HousingImageInfo image1, HousingImageInfo image2) {

		// �}�Ԑݒ�p�\�[�g�̏ꍇ�A�摜�^�C�v�A�\�����A�}�Ԃ̏����Ń\�[�g�����
		if (this.sortForDivNo) {
			if (Integer.parseInt(image1.getImageType()) > Integer.parseInt(image2.getImageType())) {
				return 1;

			} else if (Integer.parseInt(image1.getImageType()) == Integer.parseInt(image2.getImageType())) {

				if (image1.getSortOrder() > image2.getSortOrder()) {
					return 1;

				} else if (image1.getSortOrder() == image2.getSortOrder()) {

					if (image1.getDivNo() > image2.getDivNo()) {
						return 1;

					} else if (image1.getDivNo() == image2.getDivNo()) {
						return 0;
					} else {
						return -1;
					}

				} else {
					return -1;
				}

			} else {
				return -1;
			}
		}

		// �\�����A�摜�^�C�v�A�}�Ԃ̏����Ń\�[�g�����
		if (image1.getSortOrder() > image2.getSortOrder()) {
			return 1;

		} else if (image1.getSortOrder() == image2.getSortOrder()) {

			if (Integer.parseInt(image1.getImageType()) > Integer.parseInt(image2.getImageType())) {
				return 1;

			} else if (Integer.parseInt(image1.getImageType()) == Integer.parseInt(image2.getImageType())) {

				if (image1.getDivNo() > image2.getDivNo()) {
					return 1;

				} else if (image1.getDivNo() == image2.getDivNo()) {
					return 0;
				} else {
					return -1;
				}

			} else {
				return -1;
			}

		} else {
			return -1;
		}

	}

}
