package ctcav.com.demo.factory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomerFactory {
	
	private List lists;
	private Set sets;
	private Map maps;
	
	// Set, get properties

	public Set getSets() {
		return sets;
	}

	public void setSets(Set sets) {
		this.sets = sets;
	}

	public List getLists() {
		return lists;
	}

	public void setLists(List lists) {
		this.lists = lists;
	}
	
	public Map getMaps() {
		return maps;
	}

	public void setMaps(Map maps) {
		this.maps = maps;
	}

	@Override
	public String toString() {
		String getLists = lists != null ? "lists: " + lists.toString() : "";
		String getSets = sets != null ? "\nsets" + sets.toString() : "";
		String getMaps = maps != null ? "\nmaps" + maps.toString() : "";
		
		return  getLists + getSets + getMaps;
	}
}
