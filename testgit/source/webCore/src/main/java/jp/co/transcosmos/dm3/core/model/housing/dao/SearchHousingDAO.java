package jp.co.transcosmos.dm3.core.model.housing.dao;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;

public interface SearchHousingDAO {

	public List<Housing> searchHousing(HousingSearchForm searchForm);

	public List<Housing> searchHousing(HousingSearchForm searchForm, boolean full);

}
