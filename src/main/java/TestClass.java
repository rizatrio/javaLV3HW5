import annotations.AfterSuite;
import annotations.BeforeSuite;
import annotations.Test;

public class TestClass {

    @BeforeSuite
    public void before() {
        System.out.println("Methods start");
    }

    @Test(priority = 3)
    public void firstMethod() {
        System.out.println("firstMethod");
    }

    @Test(priority = 2)
    public void secondMethod() {
        System.out.println("secondMethod");
    }

    @Test(priority = 1)
    public void thirdMethod() {
        System.out.println("thirdMethod");
    }

    @Test(priority = 7)
    public void seventhMethod() {
        System.out.println("seventhMethod");
    }

    @AfterSuite
    public void after() {
        System.out.println("Methods finish");
    }
}
