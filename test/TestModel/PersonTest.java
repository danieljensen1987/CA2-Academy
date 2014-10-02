package TestModel;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 * @author David
 */
public class PersonTest {

    private Person person;

    @Before
    public void setup() {
        Student student = new StudentMock();
        person = new SimplePerson(1, "David", "Wroblewski", "123456789", "dw@cph.dk");

    }

    @Test
    public void testGetfName() {
        assertThat(person.getFname(), is("David"));
    }

}
