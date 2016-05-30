package jp.co.transcosmos.dm3.corePana.model.reform.form;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This is data transfer object of reform category.
 * A category consists of id, name, its children, its parent.
 * A category has children but not parent, it is parent category.
 * <p>
 * <pre>
 * íSìñé“		èCê≥ì˙		èCê≥ì‡óe
 * ------------ ----------- -----------------------------------------------------
 * Thi Tran		2015.12.15	Create
 * </pre>
 * <p>
 */
public class ReformPlanCategory {
    private String id;
    private String name;
    private List<ReformPlanCategory> children;
    @JsonIgnore
    private ReformPlanCategory parent;

    public ReformPlanCategory(String id) {
        this.id = id;
    }

    public ReformPlanCategory(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * This is constructor
     */
    public ReformPlanCategory(String id, String name, ReformPlanCategory parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the children
     */
    public List<ReformPlanCategory> getChildren() {
        return children;
    }
    
    @JsonIgnore
    public boolean isNew() {
        return getName() == null;
    }

    /**
     * @param parent
     *            the parent to set
     */
    public void setParent(ReformPlanCategory parent) {
        this.parent = parent;
    }

    /**
     * Add a child category
     * @param child
     */
    public void addChild(ReformPlanCategory child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        if (!children.contains(child)) {
            children.add(child);
        }
    }

    /**
     * @return the parent
     */
    @JsonIgnore
    public ReformPlanCategory getParent() {
        return parent;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public boolean isSuperCategory() {
        return parent == null && children != null && !children.isEmpty();
    }
}
