package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PERSONS")
@NamedQueries({
@NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
@NamedQuery(name = "Person.findPersonsRoles", query = "SELECT s.rolename FROM RoleSchool s WHERE s.person = :id")
})
public class Person implements Serializable
{
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s1")
@SequenceGenerator(name = "s1", sequenceName = "PERSON_SEQ", initialValue = 1000, allocationSize = 1)
@Column(name = "ID")
private int id;

@Column(name = "FNAME", nullable = false, length = 40)
private String fName;

@Column(name = "LNAME", nullable = false, length = 40)
private String lName;

@Column(name = "PHONE", nullable = false, length = 40)
private String phone;

@Column(name = "EMAIL", unique = true , nullable = false, length = 40)
private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private Collection<RoleSchool> roles = new ArrayList();

public Person()
{
}
public void addRole(RoleSchool role){
    roles.add(role);
}
public Person(int id)
{
this.id = id;
}

public Person(String fname, String lname, String phone, String email)
{
this.fName = fname;
this.lName = lname;
this.phone = phone;
this.email = email;
}

public int getId()
{
return id;
}

public String getfName()
{
return fName;
}

public void setfName(String fName)
{
this.fName = fName;
}

public String getlName()
{
return lName;
}

public void setlName(String lName)
{
this.lName = lName;
}

public String getPhone()
{
return phone;
}

public void setPhone(String phone)
{
this.phone = phone;
}

public String getEmail()
{
return email;
}

public void setEmail(String email)
{
this.email = email;
}

public Collection<RoleSchool> getRoles()
{
return roles;
}

public void setRoles(Collection<RoleSchool> roles)
{
this.roles = roles;
}



@Override
public String toString()
{
return "Person{" + "fName=" + fName
+ ", lName=" + lName
+ ", roles={" + roles + "}"
+ ", phone=" + phone
+ ", id=" + id
+ ", email=" + email + '}';
}

}