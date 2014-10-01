package facades;

import com.google.gson.Gson;
import entities.Person;
import entities.RoleSchool;
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
//        em.createNativeQuery("truncate table Teachers").executeUpdate();
//        em.createNativeQuery("truncate table Students").executeUpdate();
//        em.createNativeQuery("truncate table RoleSchool").executeUpdate();
        em.createNativeQuery("delete from Persons").executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void testAddPerson() throws NotFoundException {
        Person person = facade.addPerson(gson.toJson(new Person("Hans", "Hansen", "12345678", "hh@test.dk")));
        String expected = gson.toJson(person);
        String actual = facade.getPerson(person.getId());
        assertEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testDeletePerson() throws Exception {
        Person person = facade.addPerson(gson.toJson(new Person("Dorte", "Dorthesen", "13591113", "dd@test.dk")));
        facade.deletePerson(person.getId());
        facade.getPerson(person.getId());
    }

    @Test
    public void testGetPerson() throws Exception {
        testAddPerson();
    }

    @Test
    public void testGetPersons() {
        Person p1 = new Person("Grete", "Gretesen", "11223344", "gg@test.dk");
        Person p2 = new Person("Michelle", "Michellesen", "55667788", "mm@test.dk");
        Person person1 = facade.addPerson(gson.toJson(p1));
        Person person2 = facade.addPerson(gson.toJson(p2));

        Map<Integer, Person> test = new HashMap();
        test.put(person1.getId(), person1);
        test.put(person2.getId(), person2);

        String expected = gson.toJson(test.values());
        String actual = facade.getPersons();
        assertEquals(expected, actual);

    }

    @Test(expected = NotFoundException.class)
    public void testGetNonExistingPerson() throws Exception {
        facade.getPerson(5);
    }

    @Test
    public void testEditPerson() throws NotFoundException, InterruptedException {
        Person person = facade.addPerson(gson.toJson(new Person("Theo", "Thorsen", "98765432", "tt@test.dk")));
        String original = gson.toJson(person);
        String changed = original.replace("98765432", "11111122");
        gson.toJson(facade.editPerson(changed));

//        assertEquals(original, oldValue);
        String newValue = facade.getPerson(person.getId());
        assertEquals(changed, newValue);
        assertNotSame(original, newValue);
    }
//
//    @Test
//    public void testAddRole() throws NotFoundException {
//        
//        Person person = facade.addPerson(gson.toJson(new Person("Henrik", "Ørvald", "40474793", "hoe@gmail.com")));
//        facade.addRole(gson.toJson(RoleSchool("Teacher")));
//        String expected = gson.toJson(person);
//        String actual = facade.getPerson(person.getId());
//        assertEquals(expected, actual);
//    }
}
