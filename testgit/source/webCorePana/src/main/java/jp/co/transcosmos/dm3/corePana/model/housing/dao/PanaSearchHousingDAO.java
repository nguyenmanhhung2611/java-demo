package jp.co.transcosmos.dm3.corePana.model.housing.dao;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;

public interface PanaSearchHousingDAO {

	public List<Housing> panaSearchHousing(PanaHousingSearchForm searchForm);

	public void panaSearchHousing(PanaHousingSearchForm searchForm, HttpServletResponse response,
			PanaHousingPartThumbnailProxy panaHousingManager, PanaCommonManage panamCommonManager) throws IOException;

}
