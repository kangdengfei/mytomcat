package com.mytomcat.utils;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 13:54
 **/

import java.util.concurrent.atomic.AtomicLong;

/**
 * 全局id生成器
 */
public class IDFactoryUtil {

    private static AtomicLong generator = new AtomicLong(0);

    /**
     * 生成全局唯一id
     * @return
     */
    public static long getNextId(){
        //----------------id格式 -------------------------
        //----------long类型8个字节64个比特位----------------
        // 高32位          | 低32位
        // 系统毫秒数        自增长号

        return (((System.currentTimeMillis()/1000) & 0xFFFFFFFF) << 32) |(generator.getAndIncrement() & 0xFFFFFFFF);

    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(IDFactoryUtil.getNextId());
        System.out.println(IDFactoryUtil.getNextId());
        System.out.println(IDFactoryUtil.getNextId());
        System.out.println(IDFactoryUtil.getNextId());
        Thread.sleep(1000);
        System.out.println(IDFactoryUtil.getNextId());
        System.out.println(IDFactoryUtil.getNextId());



    }
}



