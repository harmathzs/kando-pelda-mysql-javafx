import org.example.kandopeldamysqljavafx.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.util.*;

public class FXTest {
    @Test
    public void testApplication() {
        DogsOwnersApplication.isRunningTest = true;
        DogsOwnersApplication.main(null);
        DogsOwnersApplication.isRunningTest = false;
    }

    @Test
    public void testController() {
        DogsOwnersController controller = new DogsOwnersController();
    }
}
