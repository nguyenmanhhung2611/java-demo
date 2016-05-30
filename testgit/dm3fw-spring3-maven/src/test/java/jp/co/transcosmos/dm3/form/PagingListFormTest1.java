package jp.co.transcosmos.dm3.form;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class PagingListFormTest1 {

	@Test
	public void LeftNavigationPageNoTest1() {
		
		// ナビゲーションページ数 2　（この場合、「1 2 3」が初期表示）
		// 表示ページ 1 （先頭ページ）
		// ページ内行数 （１ページ５行）
		// 総行数  10 （最大2ページ）
		// 想定表示内容　1 2
		Assert.assertEquals("表示ページ数が、ナビより少ない状態で先頭ページ", 1, testSupport(2, 1, 5, 10));

		// ナビゲーションページ数 2　（この場合、「1 2 3」が初期表示）
		// 表示ページ 2
		// ページ内行数 （１ページ５行）
		// 総行数  10 （最大2ページ）
		// 想定表示内容　1 2
		Assert.assertEquals("表示ページ数が、ナビより少ない状態で最終ページ", 1, testSupport(2, 2, 5, 10));
		
		// ナビゲーションページ数 2　（この場合、「1 2 3」が初期表示）
		// 表示ページ 3
		// ページ内行数 （１ページ５行）
		// 総行数  10 （最大2ページ）
		// 想定表示内容　1 2
		Assert.assertEquals("表示ページ数が、ナビより少ない状態でページ数オーバー", 1, testSupport(2, 3, 5, 10));

		// ナビゲーションページ数 2　（この場合、「1 2 3」が初期表示）
		// 表示ページ 3
		// ページ内行数 （１ページ５行）
		// 総行数  15 （最大3ページ）
		// 想定表示内容　1 2 3
		Assert.assertEquals("表示ページ数が、ナビと同じで最終ページ", 1, testSupport(2, 3, 5, 15));

		// ナビゲーションページ数 2　（この場合、「1 2 3」が初期表示）
		// 表示ページ 2
		// ページ内行数 （１ページ５行）
		// 総行数  100 （最大20ページ）
		// 想定表示内容　1 2 3
		Assert.assertEquals("表示ページ数がナビより多い状態で２ページ", 1, testSupport(2, 2, 5, 100));
		
		// ナビゲーションページ数 2　（この場合、「1 2 3」が初期表示）
		// 表示ページ 3
		// ページ内行数 （１ページ５行）
		// 総行数  100 （最大20ページ）
		// 想定表示内容　2 3 4
		Assert.assertEquals("表示ページ数がナビより多い状態で３ページ目選択", 2, testSupport(2, 3, 5, 100));
		
		// ナビゲーションページ数 2　（この場合、「1 2 3」が初期表示）
		// 表示ページ 4
		// ページ内行数 （１ページ５行）
		// 総行数  100 （最大20ページ）
		// 想定表示内容　3 4 5
		Assert.assertEquals("表示ページ数がナビより多い状態で４ページ目選択", 3, testSupport(2, 4, 5, 100));

		// ナビゲーションページ数 2　（この場合、「1 2 3」が初期表示）
		// 表示ページ 20 （最終ページ）
		// ページ内行数 （１ページ５行）
		// 総行数  100 （最大20ページ）
		// 想定表示内容　18 19 20
		Assert.assertEquals("表示ページ数がナビより多い状態で最終ページ", 18, testSupport(2, 20, 5, 100));

		// ナビゲーションページ数 2　（この場合、「1 2 3」が初期表示）
		// 表示ページ 19
		// ページ内行数 （１ページ５行）
		// 総行数  100 （最大20ページ）
		// 想定表示内容　18 19 20
		Assert.assertEquals("表示ページ数がナビより多い状態で最終ページひとつ前", 18, testSupport(2, 19, 5, 100));

		// ナビゲーションページ数 2　（この場合、「1 2 3」が初期表示）
		// 表示ページ 18
		// ページ内行数 （１ページ５行）
		// 総行数  100 （最大20ページ）
		// 想定表示内容　17 18 19
		Assert.assertEquals("表示ページ数がナビより多い状態で最終ページ２つ前", 17, testSupport(2, 18, 5, 100));

		// ナビゲーションページ数 3　（この場合、「1 2 3 4」が初期表示）
		// 表示ページ 2
		// ページ内行数 （１ページ５行）
		// 総行数  100 （最大20ページ）
		// 想定表示内容　1 2 3 4
		Assert.assertEquals("ナビを奇数指定で２ページ目", 1, testSupport(3, 2, 5, 100));
		
		// ナビゲーションページ数 3　（この場合、「1 2 3 4」が初期表示）
		// 表示ページ 3
		// ページ内行数 （１ページ５行）
		// 総行数  100 （最大20ページ）
		// 想定表示内容　2 3 4 5
		Assert.assertEquals("ナビを奇数指定で３ページ目", 2, testSupport(3, 3, 5, 100));

		// ナビゲーションページ数 3　（この場合、「1 2 3 4」が初期表示）
		// 表示ページ 20 （最終ページ）
		// ページ内行数 （１ページ５行）
		// 総行数  100 （最大20ページ）
		// 想定表示内容　17 18 19 20
		Assert.assertEquals("ナビを奇数指定で最終ページ", 17, testSupport(3, 20, 5, 100));

		// ナビゲーションページ数 3　（この場合、「1 2 3 4」が初期表示）
		// 表示ページ 19
		// ページ内行数 （１ページ５行）
		// 総行数  100 （最大20ページ）
		// 想定表示内容　17 18 19 20
		Assert.assertEquals("ナビを奇数指定で最終ページ１つ前", 17, testSupport(3, 19, 5, 100));

		// ナビゲーションページ数 3　（この場合、「1 2 3 4」が初期表示）
		// 表示ページ 18
		// ページ内行数 （１ページ５行）
		// 総行数  100 （最大20ページ）
		// 想定表示内容　17 18 19 20
		Assert.assertEquals("ナビを奇数指定で最終ページ２つ前", 17, testSupport(3, 18, 5, 100));

		// ナビゲーションページ数 3　（この場合、「1 2 3 4」が初期表示）
		// 表示ページ 17
		// ページ内行数 （１ページ５行）
		// 総行数  100 （最大20ページ）
		// 想定表示内容　16 17 18 19
		Assert.assertEquals("ナビを奇数指定で最終ページ３つ前", 16, testSupport(3, 17, 5, 100));

		// ナビゲーションページ数 3　（この場合、「1 2 3 4」が初期表示）
		// 表示ページ 31
		// ページ内行数 （１ページ５行）
		// 総行数  100 （最大20ページ）
		// 想定表示内容　17 18 19 20
		Assert.assertEquals("表示ページ数がナビより多い状態でページ数オーバー", 17, testSupport(3, 31, 5, 100));

		
	}

	
	
	private int testSupport(int visiblePageCount, int selectPage, int rowPerPage, int maxDataCnt) {

		// テスト対象 Form 作成
		PagingListForm<Object> form = new PagingListForm<>();
		
		// ナビゲーションページ数
		form.setVisibleNavigationPageCount(visiblePageCount);
		
		// 表示ページ
		form.setSelectedPage(selectPage);
		
		// ページ内行数
		form.setRowsPerPage(rowPerPage);

		// 総行数でダミーデーターを作成して設定
		List<Object> rows = new ArrayList<>();
		for (int i = 0; i < maxDataCnt ; ++i){
			rows.add(new Object());
		}
		form.setRows(rows);
		
		// テスト対象メソッドを実行してページ表示位置を取得
		return form.getLeftNavigationPageNo();

	}
	
	
}
