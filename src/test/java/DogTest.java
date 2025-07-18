import org.example.kandopeldamysqljavafx.Dog;
import org.junit.Assert;
import org.junit.Test;

public class DogTest {
    @Test
    public void testDog() {
        Dog fido = new Dog(
                1, "Fido",
                3, true,
                null
        );

        //Assert.assertEquals(1, fido.getId());
        int fidoId = fido.getId();
        Assert.assertEquals(1, fidoId);

        Assert.assertEquals("Fido", fido.getName());
        Assert.assertEquals(3, fido.getAge(), 0.2);
        Assert.assertTrue(fido.isMale());
        Assert.assertNull(fido.getOwner());
    }
}
