package com.mytomcat.example;

import com.mytomcat.annotation.*;
import com.mytomcat.common.enums.RequestMethod;
import com.mytomcat.common.enums.ResponseType;
import com.mytomcat.pojo.MyRquest;
import com.mytomcat.pojo.Student;
import com.mytomcat.pojo.UserService;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-06 10:43
 **/
@Bean
@Controller(path = "/student")
public class StudentController {
    @Autowired
    private UserService userService;
    @RequestMapping(path = "/getStudent")
    public Student getStudent(@Param(key="id", notNull=true) Integer id){
        return userService.getStudent(id);
    }

    @RequestMapping(path = "/addStudent",requestMethod = RequestMethod.PSOT,responseType = ResponseType.JSON)
    public Student queryStudent(MyRquest myRequest){
        return new Student(myRequest.getAge(),myRequest.getName());
    }


}



