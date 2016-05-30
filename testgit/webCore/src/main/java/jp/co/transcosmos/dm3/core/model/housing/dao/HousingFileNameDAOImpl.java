package jp.co.transcosmos.dm3.core.model.housing.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


/**
 * 物件画像ファイル名生成用 DAO の実装クラス.
 * 復帰する画像ファイル名には拡張子、およびファイルパスの情報は含まない。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.06	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class HousingFileNameDAOImpl implements HousingFileNameDAO {

	/** データソース */
	protected DataSource dataSource;

	/** シーケンスから値を取得する際に使用する SQL 文 */
	protected String sequenceNextvalExpression;



	/**
	 * データソースを取得する。<br/>
	 * <br/>
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * シーケンスから値を取得する際に使用する SQL 文を設定する。<br/>
	 * <br/>
	 * @param sequenceNextvalExpression シーケンスから値を取得する際に使用する SQL 文
	 */
	public void setSequenceNextvalExpression(String sequenceNextvalExpression) {
		this.sequenceNextvalExpression = sequenceNextvalExpression;
	}



	/**
	 * 物件画像ファイル名をシーケンスから取得して復帰する。<br/>
	 * 拡張子、およびファイルパスの情報は復帰するデータに含まない。<br/>
	 * <br/>
	 * @return 画像ファイル名　（拡張子、パスなし）
	 */
	@Override
	public String createFileName() {

		JdbcTemplate template = new JdbcTemplate(this.dataSource);

        List<String> list = template.query(this.sequenceNextvalExpression, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet resultset, int rowNum) throws SQLException {
				return resultset.getString(1);
			}});

		return list.get(0);
	}

}
