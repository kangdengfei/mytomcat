package com.mytomcat.filter;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 17:18
 **/
public abstract class AbstractLinkedProcessor<T ,R> implements Processor<T ,R> {

    public AbstractLinkedProcessor next = null;

    public AbstractLinkedProcessor getNext(){
        return next;
    }

    public void setNext(AbstractLinkedProcessor next) {
        this.next = next;
    }

    @Override
    public void process(T content,R object) {

        doProcess(content,object);
        //调用下一个processor 进行处理
        if (next != null){
            next.process(content,object);
        }

    }
    //具体业务方法
    public abstract void doProcess(T t,R r);


}



