package entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
//

@Entity
@Table(name = "TEACHERS")
//@DiscriminatorValue("TEACHER")
public class Teacher extends RoleSchool
{

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "DEGREE")
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
    public String toString()
    {
        return "degree=" + degree + ", roleName=" + super.getRolename();
    }
    
    
}
