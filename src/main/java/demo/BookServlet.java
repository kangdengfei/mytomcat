package demo;

import com.annotation.Bean;

import java.io.IOException;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-15 18:44
 **/

public class BookServlet extends MyServlet implements Contex {

    @Override
    public void doGet(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("[get] book...");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("[post] book...");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}



