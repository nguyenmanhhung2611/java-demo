/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * User permission role container class. The LoginCommand will assign an instance of
 * this class after login to the user's session, along with which roles the user actually 
 * has permission to operate under.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: UserRoleSet.java,v 1.3 2007/05/31 09:44:51 rick Exp $
 */
public class UserRoleSet implements Serializable {
    
    private static final long serialVersionUID = -2823752856981479085L;

    private Object userId;
    private Set<String> roleNames;
    
    public UserRoleSet(Object userId, Set<String> roleNames) {
        this.userId = userId;
        this.roleNames = new HashSet<String>(roleNames);
    }
    
    public Object getUserId() {
        return this.userId;
    }
    
    public boolean hasRole(String myRolename) {
        synchronized (this.roleNames) {
            return this.roleNames.contains(myRolename);
        }
    }

    public boolean hasRole(String myRolenames[]) {
        return hasRole(Arrays.asList(myRolenames));
    }
    
    public boolean hasRole(Collection<String> myRolenames) {
        synchronized (this.roleNames) {
            for (Iterator<String> i = myRolenames.iterator(); i.hasNext(); ) {
                if (this.roleNames.contains(i.next())) {
                    return true;
                }
            }
        }
        return false;
    }
}
