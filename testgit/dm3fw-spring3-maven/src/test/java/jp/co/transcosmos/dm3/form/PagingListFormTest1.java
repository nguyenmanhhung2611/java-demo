package jp.co.transcosmos.dm3.form;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class PagingListFormTest1 {

	@Test
	public void LeftNavigationPageNoTest1() {
		
		// �i�r�Q�[�V�����y�[�W�� 2�@�i���̏ꍇ�A�u1 2 3�v�������\���j
		// �\���y�[�W 1 �i�擪�y�[�W�j
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  10 �i�ő�2�y�[�W�j
		// �z��\�����e�@1 2
		Assert.assertEquals("�\���y�[�W�����A�i�r��菭�Ȃ���ԂŐ擪�y�[�W", 1, testSupport(2, 1, 5, 10));

		// �i�r�Q�[�V�����y�[�W�� 2�@�i���̏ꍇ�A�u1 2 3�v�������\���j
		// �\���y�[�W 2
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  10 �i�ő�2�y�[�W�j
		// �z��\�����e�@1 2
		Assert.assertEquals("�\���y�[�W�����A�i�r��菭�Ȃ���ԂōŏI�y�[�W", 1, testSupport(2, 2, 5, 10));
		
		// �i�r�Q�[�V�����y�[�W�� 2�@�i���̏ꍇ�A�u1 2 3�v�������\���j
		// �\���y�[�W 3
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  10 �i�ő�2�y�[�W�j
		// �z��\�����e�@1 2
		Assert.assertEquals("�\���y�[�W�����A�i�r��菭�Ȃ���ԂŃy�[�W���I�[�o�[", 1, testSupport(2, 3, 5, 10));

		// �i�r�Q�[�V�����y�[�W�� 2�@�i���̏ꍇ�A�u1 2 3�v�������\���j
		// �\���y�[�W 3
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  15 �i�ő�3�y�[�W�j
		// �z��\�����e�@1 2 3
		Assert.assertEquals("�\���y�[�W�����A�i�r�Ɠ����ōŏI�y�[�W", 1, testSupport(2, 3, 5, 15));

		// �i�r�Q�[�V�����y�[�W�� 2�@�i���̏ꍇ�A�u1 2 3�v�������\���j
		// �\���y�[�W 2
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  100 �i�ő�20�y�[�W�j
		// �z��\�����e�@1 2 3
		Assert.assertEquals("�\���y�[�W�����i�r��葽����ԂłQ�y�[�W", 1, testSupport(2, 2, 5, 100));
		
		// �i�r�Q�[�V�����y�[�W�� 2�@�i���̏ꍇ�A�u1 2 3�v�������\���j
		// �\���y�[�W 3
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  100 �i�ő�20�y�[�W�j
		// �z��\�����e�@2 3 4
		Assert.assertEquals("�\���y�[�W�����i�r��葽����ԂłR�y�[�W�ڑI��", 2, testSupport(2, 3, 5, 100));
		
		// �i�r�Q�[�V�����y�[�W�� 2�@�i���̏ꍇ�A�u1 2 3�v�������\���j
		// �\���y�[�W 4
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  100 �i�ő�20�y�[�W�j
		// �z��\�����e�@3 4 5
		Assert.assertEquals("�\���y�[�W�����i�r��葽����ԂłS�y�[�W�ڑI��", 3, testSupport(2, 4, 5, 100));

		// �i�r�Q�[�V�����y�[�W�� 2�@�i���̏ꍇ�A�u1 2 3�v�������\���j
		// �\���y�[�W 20 �i�ŏI�y�[�W�j
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  100 �i�ő�20�y�[�W�j
		// �z��\�����e�@18 19 20
		Assert.assertEquals("�\���y�[�W�����i�r��葽����ԂōŏI�y�[�W", 18, testSupport(2, 20, 5, 100));

		// �i�r�Q�[�V�����y�[�W�� 2�@�i���̏ꍇ�A�u1 2 3�v�������\���j
		// �\���y�[�W 19
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  100 �i�ő�20�y�[�W�j
		// �z��\�����e�@18 19 20
		Assert.assertEquals("�\���y�[�W�����i�r��葽����ԂōŏI�y�[�W�ЂƂO", 18, testSupport(2, 19, 5, 100));

		// �i�r�Q�[�V�����y�[�W�� 2�@�i���̏ꍇ�A�u1 2 3�v�������\���j
		// �\���y�[�W 18
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  100 �i�ő�20�y�[�W�j
		// �z��\�����e�@17 18 19
		Assert.assertEquals("�\���y�[�W�����i�r��葽����ԂōŏI�y�[�W�Q�O", 17, testSupport(2, 18, 5, 100));

		// �i�r�Q�[�V�����y�[�W�� 3�@�i���̏ꍇ�A�u1 2 3 4�v�������\���j
		// �\���y�[�W 2
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  100 �i�ő�20�y�[�W�j
		// �z��\�����e�@1 2 3 4
		Assert.assertEquals("�i�r����w��łQ�y�[�W��", 1, testSupport(3, 2, 5, 100));
		
		// �i�r�Q�[�V�����y�[�W�� 3�@�i���̏ꍇ�A�u1 2 3 4�v�������\���j
		// �\���y�[�W 3
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  100 �i�ő�20�y�[�W�j
		// �z��\�����e�@2 3 4 5
		Assert.assertEquals("�i�r����w��łR�y�[�W��", 2, testSupport(3, 3, 5, 100));

		// �i�r�Q�[�V�����y�[�W�� 3�@�i���̏ꍇ�A�u1 2 3 4�v�������\���j
		// �\���y�[�W 20 �i�ŏI�y�[�W�j
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  100 �i�ő�20�y�[�W�j
		// �z��\�����e�@17 18 19 20
		Assert.assertEquals("�i�r����w��ōŏI�y�[�W", 17, testSupport(3, 20, 5, 100));

		// �i�r�Q�[�V�����y�[�W�� 3�@�i���̏ꍇ�A�u1 2 3 4�v�������\���j
		// �\���y�[�W 19
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  100 �i�ő�20�y�[�W�j
		// �z��\�����e�@17 18 19 20
		Assert.assertEquals("�i�r����w��ōŏI�y�[�W�P�O", 17, testSupport(3, 19, 5, 100));

		// �i�r�Q�[�V�����y�[�W�� 3�@�i���̏ꍇ�A�u1 2 3 4�v�������\���j
		// �\���y�[�W 18
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  100 �i�ő�20�y�[�W�j
		// �z��\�����e�@17 18 19 20
		Assert.assertEquals("�i�r����w��ōŏI�y�[�W�Q�O", 17, testSupport(3, 18, 5, 100));

		// �i�r�Q�[�V�����y�[�W�� 3�@�i���̏ꍇ�A�u1 2 3 4�v�������\���j
		// �\���y�[�W 17
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  100 �i�ő�20�y�[�W�j
		// �z��\�����e�@16 17 18 19
		Assert.assertEquals("�i�r����w��ōŏI�y�[�W�R�O", 16, testSupport(3, 17, 5, 100));

		// �i�r�Q�[�V�����y�[�W�� 3�@�i���̏ꍇ�A�u1 2 3 4�v�������\���j
		// �\���y�[�W 31
		// �y�[�W���s�� �i�P�y�[�W�T�s�j
		// ���s��  100 �i�ő�20�y�[�W�j
		// �z��\�����e�@17 18 19 20
		Assert.assertEquals("�\���y�[�W�����i�r��葽����ԂŃy�[�W���I�[�o�[", 17, testSupport(3, 31, 5, 100));

		
	}

	
	
	private int testSupport(int visiblePageCount, int selectPage, int rowPerPage, int maxDataCnt) {

		// �e�X�g�Ώ� Form �쐬
		PagingListForm<Object> form = new PagingListForm<>();
		
		// �i�r�Q�[�V�����y�[�W��
		form.setVisibleNavigationPageCount(visiblePageCount);
		
		// �\���y�[�W
		form.setSelectedPage(selectPage);
		
		// �y�[�W���s��
		form.setRowsPerPage(rowPerPage);

		// ���s���Ń_�~�[�f�[�^�[���쐬���Đݒ�
		List<Object> rows = new ArrayList<>();
		for (int i = 0; i < maxDataCnt ; ++i){
			rows.add(new Object());
		}
		form.setRows(rows);
		
		// �e�X�g�Ώۃ��\�b�h�����s���ăy�[�W�\���ʒu���擾
		return form.getLeftNavigationPageNo();

	}
	
	
}
