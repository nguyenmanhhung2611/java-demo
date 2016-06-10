package jp.co.transcosmos.dm3.corePana.model.reform.form;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This is data transfer object of reform category. A category consists of id, name, its children, its parent. A
 * category has children but not parent, it is parent category.
 * <p>
 * 
 * <pre>
 * íSìñé“		èCê≥ì˙		èCê≥ì‡óe
 * ------------ ----------- -----------------------------------------------------
 * Thi Tran     2015.12.18      Create
 * </pre>
 * <p>
 */
public class ReformPlanCategory {

    /** Category key */
    private String id;
    /** Category name */
    private String name;
    /** Its children categories */
    private List<ReformPlanCategory> children;
    /** Its parent category */
    @JsonIgnore
    private ReformPlanCategory parent;

    /**
     * Constructor with category key
     * @param id category key
     */
    public ReformPlanCategory(String id) {
        this.id = id;
    }

    /**
     * Constructor with category key and name
     * @param id Category key
     * @param name Category name
     */
    public ReformPlanCategory(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * This is constructor with key, name and parent
     * @param id Category key
     * @param name Category name
     * @param parent Parent category
     */
    public ReformPlanCategory(String id, String name, ReformPlanCategory parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    /**
     * Getter of category id
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter of category name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of children categories
     * @return the children
     */
    public List<ReformPlanCategory> getChildren() {
        return children;
    }

    /**
     * Check the category is new or not
     * @return true if its name has not existed yet
     */
    @JsonIgnore
    public boolean isNew() {
        return getName() == null;
    }

    /**
     * Setter of parent category
     * @param parent the parent to set
     */
    public void setParent(ReformPlanCategory parent) {
        this.parent = parent;
    }

    /**
     * Add a child category
     * 
     * @param child Child category
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
     * Getter of parent category
     * @return the parent
     */
    @JsonIgnore
    public ReformPlanCategory getParent() {
        return parent;
    }

    /**
     * Setter of category name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Check this category is parent category or not.
     * @return true if the category has children but not parent
     */
    @JsonIgnore
    public boolean isSuperCategory() {
        return parent == null;
    }
}
