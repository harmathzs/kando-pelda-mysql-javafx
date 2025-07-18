import org.example.kandopeldamysqljavafx.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.util.*;

public class DogsOwnersTest {
    @Test
    public void testonHelloButtonClick() {
        DogsOwnersController controller = new DogsOwnersController();
        controller.onHelloButtonClick();
    }

    @Test
    public void testinitialize() {
        DogsOwnersController controller = new DogsOwnersController();
        controller.isRunningTest = true;
        controller.initialize(null, null);
        controller.isRunningTest = false;
    }
}
