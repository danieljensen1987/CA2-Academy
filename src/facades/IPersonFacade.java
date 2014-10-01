package facades;

import exceptions.NotFoundException;
import entities.Person;

public interface IPersonFacade 
{
    public Person addPerson(String json);
    public Person deletePerson(int id) throws NotFoundException;
    public String getPerson(int id) throws NotFoundException;
    public String getPersons();
    public Person editPerson(String json) throws NotFoundException;
}
