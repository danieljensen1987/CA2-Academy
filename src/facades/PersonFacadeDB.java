package facades;

import com.google.gson.Gson;
import entities.Person;
import entities.RoleSchool;
import entities.Teacher;
import exceptions.NotFoundException;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
        System.out.println(json);
        Person person = gson.fromJson(json, Person.class);
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        return person;
    }

    @Override
    public Person deletePerson(int id) throws NotFoundException
    {
        Person person = em.find(Person.class, id);
        if(person == null){
            throw new NotFoundException("No person exist with the given id");
        }
        em.getTransaction().begin();
        em.remove(person);
        em.getTransaction().commit();
        return person;
    }

    @Override
    public String getPerson(int id) throws NotFoundException
    {
        Person person = em.find(Person.class, id);
        if (person == null){
            throw new NotFoundException("No person exist with the given id");
        }
        return gson.toJson(person);
    }

    @Override
    public String getPersons()
    {
        Query q = em.createQuery("SELECT p FROM Person p");
        List persons = q.getResultList();
        Collections.reverse(persons);
        return gson.toJson(persons);
    }

    @Override
    public Person editPerson(String json) throws NotFoundException
    {
        Person person = gson.fromJson(json, Person.class);
        Person oldValue = em.find(Person.class, person.getId());
        if (oldValue == null){
            throw new NotFoundException("No person exist with the given id");
        }
        em.getTransaction().begin();
        em.merge(person);
        em.getTransaction().commit();
        Person newValue = em.find(Person.class, person.getId());
        return newValue;
    }
    
    public String getRoles(Person person){
      List roleList = em.createQuery("SELECT s.rolename FROM RoleSchool s WHERE s.person = :id").setParameter("id", person).getResultList();
      String roles = roleList.toString();
      return roles;
    }
    
    public RoleSchool addRole(String json, int id){
        Person person = em.find(Person.class, id);
        RoleSchool role = gson.fromJson(json, RoleSchool.class);
        role.setPerson(person);
        return role;
           
    }
    
    
    
    public void createTestData() {
        addPerson(gson.toJson(new Person("Daniel", "Jensen", "20212021", "dj@cph.dk")));
        addPerson(gson.toJson(new Person("David", "Worblewski", "10111011", "dw@cph.dk")));
        addPerson(gson.toJson(new Person("Henrik", "Ørvald", "30313031", "hoe@cph.dk")));
        addPerson(gson.toJson(new Person("Tobias", "Hansen", "40414041", "th@cph.dk")));
        addPerson(gson.toJson(new Person("Martin", "Weber", "50515051", "mw@cph.dk")));
    }

}
