package test;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-16 14:44
 **/

public class Person {

    public String name = "Tom";

    public int age = 18;

    public Student student;

    public void sayOk(){
        System.out.println("I am ok");
    }

    public void setStudent(Student student){
        this.student = student;
    }
}



