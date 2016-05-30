package jp.co.transcosmos.dm3.test.core.validation;


import jp.co.transcosmos.dm3.core.validation.TraversalValidation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.junit.Assert;
import org.junit.Test;

/**
 * ディレクトリトラバーサルチェックのテスト
 * 
 */
public class TraversalValidationTest {

	
	/**
	 * スラッシュ、コロン、バックスラッシュ、連続しないコロンの場合
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li> null が復帰される事</li>
	 * </ul>
	 */
	@Test
	public void okTest1(){

		TraversalValidation validation = new TraversalValidation();

		Assert.assertNull("半角英小字の場合 null である事", validation.validate("test", "abcdefghijklmnopqrstuvwxyz"));
		Assert.assertNull("半角英大字の場合 null である事", validation.validate("test", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
		Assert.assertNull("半角数字の場合 null である事", validation.validate("test", "1234567890"));
		Assert.assertNull("許可れている半角記号の場合 null である事", validation.validate("test", "!\"#$%&'()=-~^|`{}[]+;*?_."));
		Assert.assertNull("半角英小字の場合 null である事", validation.validate("test", "ahc.jpg"));
		Assert.assertNull("全角文字の場合 null である事", validation.validate("test", "あいうえお・￥／"));
	}
	
	/**
	 * 空文字列の場合
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li> null が復帰される事</li>
	 * </ul>
	 */
	@Test
	public void okTest2(){

		TraversalValidation validation = new TraversalValidation();

		Assert.assertNull("空文字列の場合 null である事", validation.validate("test", ""));
	}

	/**
	 * null の場合
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li> null が復帰される事</li>
	 * </ul>
	 */
	@Test
	public void okTest3(){

		TraversalValidation validation = new TraversalValidation();

		Assert.assertNull("null の場合 null である事", validation.validate("test", null));
	}

	
	/**
	 * 使用禁止文字列が含まれる場合
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事</li>
	 * </ul>
	 */
	@Test
	public void ngTest1(){

		TraversalValidation validation = new TraversalValidation();

		ValidationFailure fail = validation.validate("test", "aaaa/a");
		
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（タイプ）", "traversal", fail.getType());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（値）", "aaaa/a", fail.getValue());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（ラベル）", "test", fail.getName());

		
		fail = validation.validate("test", "aaaa/../a");
		
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（タイプ）", "traversal", fail.getType());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（値）", "aaaa/../a", fail.getValue());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（ラベル）", "test", fail.getName());

		fail = validation.validate("test", "aaaa:a");
		
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（タイプ）", "traversal", fail.getType());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（値）", "aaaa:a", fail.getValue());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（ラベル）", "test", fail.getName());

	}

}
