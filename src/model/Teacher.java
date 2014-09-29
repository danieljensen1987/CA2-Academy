package model;

import javax.persistence.Entity;

@Entity
public class Teacher extends RoleSchool
{
    private static final long serialVersionUID = 1L;
    private String degree;

    public Teacher()
    {
    }

    public Teacher(String degree)
    {
        this.degree = degree;
    }

    public String getDegree()
    {
        return degree;
    }

    public void setDegree(String degree)
    {
        this.degree = degree;
    }

    @Override
    public String getRoleName()
    {
        return super.getRoleName(); //To change body of generated methods, choose Tools | Templates.
    }
   
    
}
