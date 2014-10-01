package SetUp;

import entities.Person;
import entities.RoleSchool;
import entities.Student;
import entities.Teacher;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SetUp
{

public static void main(String[] args)
{
    facades.PersonFacadeDB db = new facades.PersonFacadeDB();
EntityManagerFactory emf = Persistence.createEntityManagerFactory("KA2JPADB");
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

RoleSchool student = new Student("3rd-b");
RoleSchool teacher = new Teacher("theBest");
RoleSchool student2 = new Student("1st-cos");

Person p1 = new Person("Daniel", "Jensen", "1987", "dj@cph.dk");
Person p2 = new Person("Lars", "Mortensen", "1954", "lm@cph.dk");


em.persist(student);
em.persist(teacher);
em.persist(student2);
em.persist(p1);
em.persist(p2);

student.setPerson(p1);
teacher.setPerson(p2);
student2.setPerson(p2);

    
//System.out.println(test);

em.getTransaction().commit();
em.close();

System.out.println(db.getRoles(p1));
System.out.println(db.getRoles(p2));




}

}