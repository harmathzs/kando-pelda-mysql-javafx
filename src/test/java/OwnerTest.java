import org.example.kandopeldamysqljavafx.Owner;
import org.junit.Assert;
import org.junit.Test;

public class OwnerTest {
    @Test
    public void testOwner() {
        Owner janeDoe = new Owner(1, "Jane Doe");

        //Assert.assertEquals(1, janeDoe.getId());
        int ownerId = janeDoe.getId();
        Assert.assertEquals(1, ownerId);

        Assert.assertEquals("Jane Doe", janeDoe.getName());
    }
}
