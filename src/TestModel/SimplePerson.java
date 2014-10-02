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
public class SimplePerson implements Person {

    private int id;
    private Collection<RoleSchool> roles;
    private String fname;
    private String lname;
    private String phone;
    private String email;

    public SimplePerson(int id, String fname, String lname, String phone, String email) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Collection<RoleSchool> getRoles() {
        return roles;
    }

    @Override
    public String getFname() {
        return fname;
    }

    @Override
    public String getLname() {
        return lname;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getEmail() {
        return email;
    }

}
