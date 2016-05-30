package jp.co.transcosmos.dm3.corePana.model.partCreator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * This is housing part creator for re-new housing part info of reform category
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * Thi Tran		2015.12.16		Create
 * </pre>
 * <p>
 *
 */
public class PanaReformPartCreator implements HousingPartCreator{

	/** Housing part info DAO	 */
	private DAO<HousingPartInfo> housingPartInfoDAO;
	/** Factory of value object*/
	private ValueObjectFactory valueObjectFactory;
	/** 
	 * Map of category part
	 * Each entry is couple of key-value.
	 * key = category ID
	 * value = category part CD
	 */
	private Map<String, String> categoryToPart;
	
	/** List of all category part cd*/
	private String myCategoryPartCds[];
	
	/**
	 * 
	 * @param housingPartInfoDAO
	 */
	public void setHousingPartInfoDAO(DAO<HousingPartInfo> housingPartInfoDAO) {
		this.housingPartInfoDAO = housingPartInfoDAO;
	}

	/**
	 * 
	 * @param valueObjectFactory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * Inject map of category.
	 * Beside, loop through this map to get all category part CDs
	 * @param categoryToPart
	 */
	public synchronized void setCategoryToPart(Map<String, String> categoryToPart) {
		this.categoryToPart = categoryToPart;
		
		// get all category part CDs
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : categoryToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myCategoryPartCds = partCdSet.toArray(new String[partCdSet.size()]);
	}

	@Override
	public boolean isExecuteMethod(String methodName) {
		// housing part info of reform category is updated 
		// when new reform is added, a reform is updated/deleted
		if ("addReform".equals(methodName) || 
				"updReform".equals(methodName) ||
				"delReform".equals(methodName)){
			return true;
		}
		
		return false;
	}

	@Override
	public void createPart(Housing housing) throws Exception {
		HousingInfo housingInfo = (HousingInfo)  housing.getHousingInfo().getItems().get("housingInfo");
		
		// delete all category part info of the housing
		delPartCds(housingInfo.getSysHousingCd(), this.myCategoryPartCds);
		
		// get reforms of the housing
		PanaHousing panaHousing = (PanaHousing) housing;
		List<Map<String, Object>>  reformList = panaHousing.getReforms();
		
		// if the housing has reform plans
		if(reformList == null || reformList.size() == 0){
			return;
		}
		
		// loop through reform plan list and get matched and distinct part CDs
		Map<String, HousingPartInfo> addPartMap = new HashMap<>();
		for (Map<String, Object> reforms : reformList) {
			// リフォームプラン情報
		    ReformPlan reformPlan = (ReformPlan) reforms.get("reformPlan");
		    if(reformPlan != null){
		    	String partCd = this.categoryToPart.get(reformPlan.getPlanCategory1());
		    	if (!StringValidateUtil.isEmpty(partCd) && addPartMap.get(partCd) == null){
					addPartMap.put(partCd, createHousingPartInfo(housingInfo, partCd));
				}
		    	
		    	partCd = this.categoryToPart.get(reformPlan.getPlanCategory2());
		    	if (!StringValidateUtil.isEmpty(partCd) && addPartMap.get(partCd) == null){
					addPartMap.put(partCd, createHousingPartInfo(housingInfo, partCd));
				}
		    }
		}
		
		// insert new housing part info for found part CDs
		this.housingPartInfoDAO.insert(addPartMap.values().toArray(new HousingPartInfo[addPartMap.size()]));
	}
	
	/**
	 * Delete part info of the given housing with part_srch_cd in given list of cd
	 * @param sysHousingCd the given housing cd
	 * @param partCds list of part cd need to remove
	 */
	protected void delPartCds(String sysHousingCd, String partCds[]) {
		// 現在のこだわり条件CD を削除する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addInSubQuery("partSrchCd", partCds);
		this.housingPartInfoDAO.deleteByFilter(criteria);
	}

	protected HousingPartInfo createHousingPartInfo(HousingInfo housingInfo, String partCd) {
		// Value オブジェクトを生成してプロパティ値を設定する。
		HousingPartInfo housingPartInfo = (HousingPartInfo) this.valueObjectFactory.getValueObject("HousingPartInfo");
		housingPartInfo.setSysHousingCd(housingInfo.getSysHousingCd());
		housingPartInfo.setPartSrchCd(partCd);
		return housingPartInfo;
	}
}
