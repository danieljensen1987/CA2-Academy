package facades;

import com.google.gson.Gson;
import entities.Person;
import entities.RoleSchool;
import entities.Teacher;
import exceptions.NotFoundException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PersonFacadeTest {

    PersonFacadeDB facade;
    Gson gson = new Gson();
    EntityManagerFactory emf;
    EntityManager em;

    public PersonFacadeTest() {

    }

    @Before
    public void setUp() {
        facade = PersonFacadeDB.getFacade(true);
        emf = Persistence.createEntityManagerFactory("KA2JPADB");
        em = emf.createEntityManager();

    }

    @After
    public void tearDown() {

        em.getTransaction().begin();
        
        em.createNativeQuery("delete from Persons").executeUpdate();
        em.createNativeQuery("delete Sequence").executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void testAddPerson() throws NotFoundException {
        Person person = facade.addPersonFromGson(gson.toJson(new Person("Hans", "Hansen", "12345678", "hh@test.dk")));
        String expected = gson.toJson(person);
        String actual = facade.getPersonAsJson(person.getId());
        assertEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testDeletePerson() throws Exception {
        Person person = facade.addPersonFromGson(gson.toJson(new Person("Dorte", "Dorthesen", "13591113", "dd@test.dk")));
        facade.delete(person.getId());
        facade.getPersonAsJson(person.getId());
    }

    @Test
    public void testGetPerson() throws Exception {
        testAddPerson();
    }

    @Test
    public void testGetPersons() {
        Person p1 = new Person("Grete", "Gretesen", "11223344", "gg@test.dk");
        Person p2 = new Person("Michelle", "Michellesen", "55667788", "mm@test.dk");
        Person person1 = facade.addPersonFromGson(gson.toJson(p1));
        Person person2 = facade.addPersonFromGson(gson.toJson(p2));

        Map<Integer, Person> test = new HashMap();
        test.put(person1.getId(), person1);
        test.put(person2.getId(), person2);

        String expected = gson.toJson(test.values());
        String actual = facade.getPersonsAsJSON();
        assertEquals(expected, actual);

    }

    @Test(expected = NotFoundException.class)
    public void testGetNonExistingPerson() throws Exception {
        facade.getPersonAsJson(5);
    }

    @Test
    public void testEditPerson() throws NotFoundException, InterruptedException {
        Person person = facade.addPersonFromGson(gson.toJson(new Person("Theo", "Thorsen", "98765432", "tt@test.dk")));
        String original = gson.toJson(person);
        String changed = original.replace("98765432", "11111122");
        gson.toJson(facade.editPerson(changed));

        String newValue = facade.getPersonAsJson(person.getId());
        assertEquals(changed, newValue);
        assertNotSame(original, newValue);
    }
// Sorry for the hack
    @Test
    public void testAddRole() throws NotFoundException {
        System.out.println("In");
        Person person = facade.addPersonFromGson(gson.toJson(new Person("Henrik", "Ørvald", "40474793", "hoe@gmail.com")));
        String strTeacher = "{'discription':'d-1', 'roleName':'Teacher'}";
        String strStudent = "{'discription':'3rd-b', 'roleName':'Student'}";
        String strAss = "{'roleName':'AssistantTeacher'}";
        facade.addRoleFromGson(strTeacher, person.getId());
        facade.addRoleFromGson(strStudent, person.getId());
        facade.addRoleFromGson(strAss, person.getId());
        String expected = "{\"id\":1000,\"fName\":\"Henrik\",\"lName\":\"Ørvald\",\"phone\":\"40474793\",\"email\":\"hoe@gmail.com\",\"roles\":[{\"degree\":\"d-1\",\"id\":1,\"rolename\":\"Teacher\"},{\"semester\":\"3rd-b\",\"id\":2,\"rolename\":\"Student\"},{\"id\":3,\"rolename\":\"AssistantTeacher\"}]}";
        
        String actual = facade.getPersonAsJson(person.getId());
        assertEquals(expected, actual);
        System.out.println("out");
    }
    
}
