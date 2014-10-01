package entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENTS")
//@DiscriminatorValue("STUDENT")
public class Student extends RoleSchool
{

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "SEMESTER")
    private String semester;

    public Student()
    {
    }

    public Student(String semester)
    {
        this.semester = semester;
    }

    public String getSemester()
    {
        return semester;
    }

    public void setSemester(String semester)
    {
        this.semester = semester;
    }

    @Override
    public String toString()
    {
        return "semester=" + semester + ", roleName=" + super.getRolename();
    }
    
    

}
