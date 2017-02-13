import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by bramreth on 2/13/17.
 */
public class Yuconz_project_app_Test {

    private Yuconz_project_app app;

    @Before
    public void setupTest(){
        app = new Yuconz_project_app();
    }
    @After
    public void tearDownTest(){
        app = null;
    }
    @Test
    public void testValidLogin(){
        boolean result = app.login("andy","example");
        assertTrue(result);
    }

    @Test
    public void testInValidLogin(){
        boolean result = app.login("notARealName","INVALIDPASSWORD");
        assertFalse(result);
    }

    @Test
    public void noLogin(){
        boolean result = app.login("","");
        assertFalse(result);
    }

    @Test
    public void testInValidLogout(){
        boolean result = app.logout();
        assertFalse(result);
    }

    @Test
    public void testValidLogout(){
        boolean resultA = app.login("andy","example");
        boolean resultB = app.logout();
        assertTrue(resultB);
    }

}