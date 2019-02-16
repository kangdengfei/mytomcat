package test;

import com.annotation.Bean;
import com.mytomac.Person;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-16 17:09
 **/
@Bean
public class Student {
    public Person person;

    public void setPerson(Person person){
        this.person = person;
    }
}



