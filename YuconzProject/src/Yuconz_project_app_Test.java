import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by bramreth on 2/13/17.
 */
public class Yuconz_project_app_Test {

    @Test
    public void testLogin(){
        Yuconz_project_app app = new Yuconz_project_app();
        boolean result = app.login("andy","example");
        assertTrue(result);
    }

    @Test
    public void testLogout(){
        Yuconz_project_app app = new Yuconz_project_app();
        boolean result = app.logout();
        assertTrue(result);
    }

}