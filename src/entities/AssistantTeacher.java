package entities;

import javax.persistence.Entity;
import javax.persistence.Table;
//

@Entity
@Table(name = "AssistantTeacher")
//@DiscriminatorValue("TEACHER")
public class AssistantTeacher extends RoleSchool
{

    private static final long serialVersionUID = 1L;

   

    public AssistantTeacher()
    {
    }

   
    @Override
    public String toString()
    {
        return "roleName=" + super.getRolename();
    }
    
    
}
