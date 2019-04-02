import com.mytomcat.Router.RouterContext;
import com.mytomcat.Router.imp.DefaultRouterContext;
import org.junit.Test;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-18 22:00
 **/
public class DefaultRouterContextTest {
    @Test
    public void test1(){
        DefaultRouterContext instance = (DefaultRouterContext) DefaultRouterContext.getInstance();
        instance.init();
    }
}



