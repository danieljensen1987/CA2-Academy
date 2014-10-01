package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ROLESCHOOL")
@DiscriminatorColumn(name = "ROLENAME")
public abstract class RoleSchool implements Serializable
{
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;


@Column(name = "ROLENAME", nullable = false, length = 40)
private String rolename;

@JoinColumn(name = "PERSON_ID")
@ManyToOne(optional = false)
private Person person;

public RoleSchool()
{
}

public RoleSchool(Long id)
{
this.id = id;
}

public RoleSchool(Long id, String rolename)
{
this.id = id;
this.rolename = rolename;
}

public Long getId()
{
return id;
}

public String getRolename()
{
return rolename;
}

public void setRolename(String rolename)
{
this.rolename = rolename;
}

public Person getPerson()
{
return person;
}

public void setPerson(Person person)
{
this.person = person;
}


@Override
public String toString()
{
return "entites.RoleSchool[ id=" + person + " ]";
}

}