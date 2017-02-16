import java.util.List;

/**
 * Created by Maximilian on 16/02/2017.
 */
public abstract class Position
{
    protected String positionName;
    protected User manager;
    protected User director;
    protected List<User> subordinates;
}
