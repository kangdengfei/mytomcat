package com.mytomcat.filter;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 17:18
 **/
public class AbstractLinkedProcessor implements Processor {

    private AbstractLinkedProcessor next = null;

    public AbstractLinkedProcessor getNext(){
        return next;
    }

    public void setNext(AbstractLinkedProcessor next) {
        this.next = next;
    }

    @Override
    public void process(String content) {
        //调用下一个processor 进行处理
        if (next != null){
            next.process(content);
        }

    }
}



