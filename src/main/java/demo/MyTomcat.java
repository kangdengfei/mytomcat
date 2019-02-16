package demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-15 18:51
 **/
public class MyTomcat {
    private int port;
    //保存请求url和处理请求servlet的对应关系
    private Map<String, String> urlServletMap = new HashMap<String, String>();

    public MyTomcat(int port) {
        this.port = port;
    }

    public void start() {
        initServletMapping();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("MyTomcat is start...\nport：" + port);
            while(true) {
                System.out.println("等待请求...");
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                MyRequest myRequest = new MyRequest(inputStream);
                MyResponse myResponse = new MyResponse(outputStream);
                //请求分发
                disPatch(myRequest, myResponse);
                socket.close();
            }
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //初始化url和处理的servlet的对应关系
    private void initServletMapping() {
        for(ServletMapping servletMapping: ServletMappingConfig.servletMappingList) {
            urlServletMap.put(servletMapping.getUrl(), servletMapping.getClassName());
        }
    }

    //分发处理请求
    private void disPatch(MyRequest myRequest, MyResponse myResponse) {
        String className = urlServletMap.get(myRequest.getUrl());

        //反射
        try {
            Class<MyServlet> myServletClass = (Class<MyServlet>) Class.forName(className);
            MyServlet myServlet = myServletClass.newInstance();

            myServlet.service(myRequest, myResponse);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyTomcat myTomcat = new MyTomcat(8880);
        myTomcat.start();
    }
}




