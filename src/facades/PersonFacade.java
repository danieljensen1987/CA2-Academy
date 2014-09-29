package facades;

import com.google.gson.Gson;
import exceptions.NotFoundException;
import java.util.HashMap;
import java.util.Map;
import model.Person;

public class PersonFacade implements IPersonFacade
{

    Map<Integer, Person> persons = new HashMap();
    private int nextId;
    private final Gson gson = new Gson();
    private static PersonFacade facade = new PersonFacade();

    private PersonFacade()
    {
    }

    public static PersonFacade getFacade(boolean reset)
    {
        if (true) {
            facade = new PersonFacade();
        }
        return facade;
    }

    public void createTestData()
    {
        addPerson(gson.toJson(new Person("Daniel", "Jensen", "20212021", "dj@cph.dk")));
        addPerson(gson.toJson(new Person("David", "Worblewski", "10111011", "dw@cph.dk")));
        addPerson(gson.toJson(new Person("Henrik", "Ørvald", "30313031", "hoe@cph.dk")));
        addPerson(gson.toJson(new Person("Tobias", "Hansen", "40414041", "th@cph.dk")));
        addPerson(gson.toJson(new Person("Martin", "Weber", "50515051", "mw@cph.dk")));
    }

    @Override
    public Person addPerson(String json)
    {
        Person p = gson.fromJson(json, Person.class);
        p.setId(nextId);
        persons.put(nextId, p);
        nextId++;
        return p;
    }

    @Override
    public Person deletePerson(int id) throws NotFoundException
    {
        Person p = persons.remove(id);
        if (p == null) {
            throw new NotFoundException("No person exists for the given id");
        }
        return p;
    }

    @Override
    public String getPerson(int id) throws NotFoundException
    {
        Person p = persons.get(id);
        if (p == null) {
            throw new NotFoundException("No person exists for the given id");
        }
        return gson.toJson(p);
    }

    @Override
    public String getPersons()
    {
        if (persons.isEmpty()) {
            return null;
        }
        return gson.toJson(persons.values());
    }

    @Override
    public Person editPerson(String json) throws NotFoundException
    {
        Person p = gson.fromJson(json, Person.class);
        Person oldValue = persons.get(p.getId());
        if (oldValue == null) {
            throw new NotFoundException("No person exists for the given id");
        }
        persons.put(p.getId(), p);
        return oldValue;
    }

}
