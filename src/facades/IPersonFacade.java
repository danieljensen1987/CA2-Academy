package facades;

import exceptions.NotFoundException;
import entities.Person;

public interface IPersonFacade 
{
    public Person addPersonFromGson(String json);
    public Person delete(int id) throws NotFoundException;
    public String getPersonAsJson(int id) throws NotFoundException;
    public String getPersonsAsJSON();
    public Person editPerson(String json) throws NotFoundException;
}
