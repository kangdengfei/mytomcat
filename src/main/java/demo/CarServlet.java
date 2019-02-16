package demo;

import java.io.IOException;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-15 18:45
 **/
public class CarServlet extends MyServlet {

    @Override
    public void doGet(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("[get] car...");
        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("[post] car...");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }




}


