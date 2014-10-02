/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TestModel;

import java.util.Collection;

/**
 *
 * @author David
 */
public interface Person {
    int getId();
    Collection<RoleSchool> getRoles();
    String getFname();
    String getLname();
    String getPhone();
    String getEmail();
    
}
