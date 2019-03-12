package com.mytomcat.filter;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 17:25
 **/
public class LinkedProcessorChain {

    /**
     * 头节点
     */
    private AbstractLinkedProcessor first = new AbstractLinkedProcessor();


    /**
     * 尾节点
     */
    private AbstractLinkedProcessor last = new AbstractLinkedProcessor();


    public void addLast(AbstractLinkedProcessor processor){
        if (processor == null){
            return;
        }
        if (last != null){
            last.setNext(processor);
        }else
            first.setNext(processor);

        last = processor;
    }

    public void process(String content){
        first.process(content);
    }
}



