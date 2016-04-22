package ctcav.com.demo.collections;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomerCollections {
	
	private List<Object> lists;
	private Set<Object> sets;
	private Map<Object,Object> maps;
	private Properties pros;

	public List<Object> getLists() {
		return lists;
	}

	public void setLists(List<Object> lists) {
		this.lists = lists;
	}
	
	public Set<Object> getSets() {
		return sets;
	}

	public void setSets(Set<Object> sets) {
		this.sets = sets;
	}

	public Map<Object, Object> getMaps() {
		return maps;
	}

	public void setMaps(Map<Object, Object> maps) {
		this.maps = maps;
	}
	
	public Properties getPros() {
		return pros;
	}

	public void setPros(Properties pros) {
		this.pros = pros;
	}

	@Override
	public String toString() {
		String getLists = "getLists: " + this.getLists().toString();
		String getSets = "getSets: " + this.getSets().toString();
		String getMaps = "getMaps: " + this.getMaps().toString();
		String getPros = "getPros: " + this.getPros().toString();
		return getLists + "\n" + getSets + "\n" + getMaps + "\n" + getPros;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		CustomerCollections cols = (CustomerCollections) context.getBean("customercollections");
		System.out.println(cols.toString());
	}
	
}
