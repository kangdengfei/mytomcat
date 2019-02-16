package demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-15 18:44
 **/


/**
 * 配置请求url和处理的servlet的对应关系
 */
public class ServletMappingConfig {
    public static List<ServletMapping> servletMappingList = new ArrayList<>();;

    static {
        servletMappingList.add(new ServletMapping("Book", "/book", "demo.BookServlet"));
        servletMappingList.add(new ServletMapping("Car", "/car", "demo.CarServlet"));
    }
}



