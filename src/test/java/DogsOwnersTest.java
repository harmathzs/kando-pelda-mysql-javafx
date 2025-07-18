import org.example.kandopeldamysqljavafx.*;
import org.junit.*;

import java.util.*;

public class DogsOwnersTest {
    DogsOwnersController controller;

    @Before
    public void testBeforeEach() {
        controller = new DogsOwnersController();
        controller.isRunningTest = true;
        controller.initialize(null, null);
    }

    @Test
    public void testonHelloButtonClick() {
        controller.onHelloButtonClick();
    }

    @Test
    public void testButton1() {
        controller.onButton1Click();
    }
}
