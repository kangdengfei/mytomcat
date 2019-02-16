package demo;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-15 18:44
 **/
public class ServletMapping {
    private String servletName;
    private String url;
    private String className;

    public ServletMapping(String servletName, String url, String className) {
        super();
        this.servletName = servletName;
        this.url = url;
        this.className = className;
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}



