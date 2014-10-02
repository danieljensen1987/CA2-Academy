package facades;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.AssistantTeacher;
import entities.Person;
import entities.RoleSchool;
import entities.Student;
import entities.Teacher;
import exceptions.NotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import utilities.ClosableManager;

public class PersonFacadeDB implements IPersonFacade
{

    private static PersonFacadeDB facade = new PersonFacadeDB();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("KA2JPADB");
    EntityManager em = emf.createEntityManager();
    private final Gson gson = new Gson();

    public static PersonFacadeDB getFacade(boolean b)
    {
        if (true) {
            facade = new PersonFacadeDB();
        }
        return facade;
    }

    @Override
    public Person addPerson(String json)
    {
        try (ClosableManager em = new ClosableManager(emf)) {

            Person person = gson.fromJson(json, Person.class);
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return person;
        }
    }

    @Override
    public Person deletePerson(int id) throws NotFoundException
    {
        try (ClosableManager em = new ClosableManager(emf)) {
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new NotFoundException("No person exist with the given id");
            }
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
            return person;
        }
    }

    @Override
    public String getPerson(int id) throws NotFoundException
    {
        try (ClosableManager em = new ClosableManager(emf)) {
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new NotFoundException("No person exist with the given id");
            }
            return gson.toJson(person);
        }
    }

    @Override
    public String getPersons()
    {
        try (ClosableManager em = new ClosableManager(emf)) {
            Query q = em.createQuery("SELECT p FROM Person p");
            List persons = q.getResultList();
            //Collections.reverse(persons);
            return gson.toJson(persons);
        }
    }

    @Override
    public Person editPerson(String json) throws NotFoundException
    {
        try (ClosableManager em = new ClosableManager(emf)) {
            Person person = gson.fromJson(json, Person.class);
            Person oldValue = em.find(Person.class, person.getId());
            if (oldValue == null) {
                throw new NotFoundException("No person exist with the given id");
            }
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
            Person newValue = em.find(Person.class, person.getId());
            return newValue;
        }
    }

    public RoleSchool addRole(String json, int id) throws NotFoundException
    {
        try (ClosableManager em = new ClosableManager(emf)) {
            Person person = em.find(Person.class, id);
            RoleSchool r = null;
            JsonElement jelement = new JsonParser().parse(json);
            JsonObject jobject = jelement.getAsJsonObject();
            String roleName = jobject.get("roleName").getAsString();
            System.out.println("roleName = " + roleName);
            switch (roleName.toUpperCase()) {

                case "TEACHER":
                    String degree = jobject.get("discription").getAsString();
                    r = new Teacher(degree);
                    person.addRole(r);
                    break;

                case "STUDENT":
                    String semester = jobject.get("discription").getAsString();
                    r = new Student(semester);
                    person.addRole(r);
                    break;

                case "ASSISTANTTEACHER":
                    r = new AssistantTeacher();
                    person.addRole(r);
                    break;
                default:
                    throw new NotFoundException("String rolename not found");

            }
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
            return r;

        }
    }

    public void createTestData() throws NotFoundException
    {
        addPerson(gson.toJson(new Person("Daniel", "Jensen", "20212021", "dj@cph.dk")));
        addRole("{'discription':'3rd-b', 'roleName':'Student'}", 1000);
        addRole("{'roleName':'AssistantTeacher'}", 1000);
        addPerson(gson.toJson(new Person("David", "Worblewski", "10111011", "dw@cph.dk")));
        addPerson(gson.toJson(new Person("Henrik", "Ørvald", "30313031", "hoe@cph.dk")));
        addPerson(gson.toJson(new Person("Tobias", "Hansen", "40414041", "th@cph.dk")));
        addPerson(gson.toJson(new Person("Martin", "Weber", "50515051", "mw@cph.dk")));
        addPerson(gson.toJson(new Person("Lars", "Mortensen", "60616061", "lam@cph.dk")));
        addRole("{'discription':'BCS', 'roleName':'Teacher'}", 1005);
        addRole("{'discription':'3rd-b', 'roleName':'Student'}", 1005);
        addPerson(gson.toJson(new Person("Anders", "Kalhauge", "70717071", "aka@cph.dk")));
    }

    public void test()
    {
        String str = "{'degree':'d-1', 'roleName':'Teacher'}";
        // addRole(str, 1);

    }

    public static void main(String[] args)
    {
        new PersonFacadeDB().test();
    }

}
