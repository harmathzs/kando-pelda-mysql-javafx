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

    @Test
    public void testButton2() {
        controller.onButton2Click();
    }

    @Test
    public void testButton3() {
        controller.onButton3Click();
    }

    @Test
    public void testButton4() {
        controller.onButton4Click();
    }

    @Test
    public void testButton5() {
        controller.onButton5Click();
    }

    @Test
    public void testButton6() {
        controller.onButton6Click();
    }

    @Test
    public void testButton7() {
        controller.onButton7Click();
    }
}
