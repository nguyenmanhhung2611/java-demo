package jp.co.transcosmos.dm3.testUtil.mock;

import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;


public class MockFactory extends ValueObjectFactory {

	// テスト用にメソッドをオーバーライドして拡張
	@Override
	protected Object buildValueObject(String shortClassName){

		if ("PrefMst".equals(shortClassName)){
			return new MockPrefMst();
		}
		
		return super.buildValueObject(shortClassName);
	}

}
