package jp.co.transcosmos.dm3.test.core.displayAdapter;

import org.junit.Assert;
import org.junit.Test;

import jp.co.transcosmos.dm3.core.displayAdapter.DisplayUtils;

public class DisplayUtilsTest {

	// 万円単位表示テスト（デフォルト設定）
	@Test
	public void toTenThousandUnitsTest(){
		
		DisplayUtils utils = new DisplayUtils();
		

		// null の場合
		String ret = utils.toTenThousandUnits(null);
		Assert.assertEquals("空文字列が表示される事", "", ret);

		// 0 の場合
		ret = utils.toTenThousandUnits(0L);
		Assert.assertEquals("0円が表示される事", "0円", ret);

		
		// １万未満の値の場合（小数点以下切り上げ）
		ret = utils.toTenThousandUnits(100L);
		Assert.assertEquals("一万未満の場合でも小数点以下が切り上げられている事", "1万円", ret);
		
		// １万の場合（小数点以下切り上げ）
		ret = utils.toTenThousandUnits(10000L);
		Assert.assertEquals("一万の場合でも正しく加工される事", "1万円", ret);

		// １万１千の場合（小数点以下切り上げ）
		ret = utils.toTenThousandUnits(11000L);
		Assert.assertEquals("一万を超える場合でも、小数点以下が切上げられる事", "2万円", ret);

		// １１万の場合（小数点以下切り上げ）
		ret = utils.toTenThousandUnits(110000L);
		Assert.assertEquals("一万未満の場合でも小数点以下が切り上げられている事", "11万円", ret);
		
		// 加工後、４桁を越える場合、カンマ編集される事（小数点以下切り上げ）
		ret = utils.toTenThousandUnits(11000000000L);
		Assert.assertEquals("一万未満の場合でも小数点以下が切り上げられている事", "1,100,000万円", ret);
		
		
		// 小数点以下１桁を指定
		utils.setPriceRoundScale(1);

		// １万未満の値の場合（小数点以下切り上げ）
		ret = utils.toTenThousandUnits(100L);
		Assert.assertEquals("100 の場合、指定した小数点以下に切り上げられる事", "0.1万円", ret);

		// １万未満の値の場合（小数点以下切り上げ）
		ret = utils.toTenThousandUnits(1000L);
		Assert.assertEquals("一万未満の場合でも指定した小数点以下に切り上げられている事", "0.1万円", ret);
		
		// １万の場合（小数点以下切り上げ）
		ret = utils.toTenThousandUnits(10000L);
		Assert.assertEquals("一万の場合でも正しく加工される事", "1万円", ret);

		// １万１千の場合（小数点以下切り上げ）
		ret = utils.toTenThousandUnits(11000L);
		Assert.assertEquals("一万を超える場合でも、小数点以下が切上げられる事", "1.1万円", ret);

		// １１万の場合（小数点以下切り上げ）
		ret = utils.toTenThousandUnits(110000L);
		Assert.assertEquals("一万未満の場合でも小数点以下が切り上げられている事", "11万円", ret);
		
		// 加工後、４桁を越える場合、カンマ編集される事（小数点以下切り上げ）
		ret = utils.toTenThousandUnits(11000000000L);
		Assert.assertEquals("一万未満の場合でも小数点以下が切り上げられている事", "1,100,000万円", ret);
		
		
		
		
	}
	
	
	
}
