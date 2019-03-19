import com.mytomcat.utils.PropertiesUtil;
import org.junit.Test;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Properties;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-15
 **/
public class PropertiesutilTest {

    @Test
    public void test1() throws Exception{
        PropertiesUtil instance = PropertiesUtil.getInstance("/mytomcat.properties");
        Integer property = instance.getInt("sever.port",8888);
        System.out.println(property);
    }
}



