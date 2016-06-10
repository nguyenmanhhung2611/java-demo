package jp.co.transcosmos.dm3.test.core.validation;

import jp.co.transcosmos.dm3.core.validation.FileExtensionValidation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.junit.Assert;
import org.junit.Test;

/**
 * 物件情報 model 更新系処理のテスト
 * 
 */
public class FileExtensionValidationTest {

	/**
	 * 指定された拡張子に該当する場合
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>大文字、小文字が異なる場合でも null が復帰される事</li>
	 * </ul>
	 */
	@Test
	public void oKtest1(){
		
		FileExtensionValidation validation = new FileExtensionValidation(new String[]{".jpg", ".jpeg"});
		
		Assert.assertNull("拡張子が一致する場合 null が復帰する事", validation.validate("test", "ABC.jpg"));
		Assert.assertNull("拡張子が一致する場合 null が復帰する事", validation.validate("test", "ABC.JPG"));
		Assert.assertNull("拡張子が一致する場合 null が復帰する事", validation.validate("test", "ABC.jpeg"));
		Assert.assertNull("拡張子が一致する場合 null が復帰する事", validation.validate("test", "ABC.JPEG"));
		
	}
	

	/**
	 * 指定された値が空文字列の場合
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>～文字列の場合でも null が復帰される事</li>
	 * </ul>
	 */
	@Test
	public void okTest2(){
		
		FileExtensionValidation validation = new FileExtensionValidation(new String[]{".jpg", ".jpeg"});
		
		Assert.assertNull("空文字列の場合 null が復帰する事", validation.validate("test", ""));
		
	}


	
	/**
	 * 指定された値が null の場合
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>～文字列の場合でも null が復帰される事</li>
	 * </ul>
	 */
	@Test
	public void okTest3(){
		
		FileExtensionValidation validation = new FileExtensionValidation(new String[]{".jpg", ".jpeg"});
		
		Assert.assertNull("null の場合 null が復帰する事", validation.validate("test", null));
		
	}

	
	
	/**
	 * 指定された拡張以外が使用された場合
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事</li>
	 * </ul>
	 */
	@Test
	public void ngTest1(){
		
		FileExtensionValidation validation = new FileExtensionValidation(new String[]{".jpg", ".jpeg"});
		
		ValidationFailure fail = validation.validate("test", "ABC.PG");
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（タイプ）", "fileExtNG", fail.getType());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（値）", "ABC.PG", fail.getValue());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（ラベル）", "test", fail.getName());

		fail = validation.validate("test", "ABC.");
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（タイプ）", "fileExtNG", fail.getType());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（値）", "ABC.", fail.getValue());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（ラベル）", "test", fail.getName());

		fail = validation.validate("test", "ABC.gif");
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（タイプ）", "fileExtNG", fail.getType());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（値）", "ABC.gif", fail.getValue());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（ラベル）", "test", fail.getName());
		
		fail = validation.validate("test", "jpg");
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（タイプ）", "fileExtNG", fail.getType());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（値）", "jpg", fail.getValue());
		Assert.assertEquals("正しいエラーオブジェクトが復帰される事（ラベル）", "test", fail.getName());
		
	}

}
