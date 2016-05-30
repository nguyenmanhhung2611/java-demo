package jp.co.transcosmos.dm3.testUtil.mock;

import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;


public class MockFactory extends ValueObjectFactory {

	// �e�X�g�p�Ƀ��\�b�h���I�[�o�[���C�h���Ċg��
	@Override
	protected Object buildValueObject(String shortClassName){

		if ("PrefMst".equals(shortClassName)){
			return new MockPrefMst();
		}
		
		return super.buildValueObject(shortClassName);
	}

}
