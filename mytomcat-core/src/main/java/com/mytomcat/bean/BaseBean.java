package com.mytomcat.bean;




import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-17 21:26
 **/
public class BaseBean implements Serializable {
    private static final long serialVersionUID = -4976516540408695147L;

    public BaseBean() {
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}



