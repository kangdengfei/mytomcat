package com.mytomcat.filter;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 17:25
 **/
public class LinkedProcessorChain<T> {

    /**
     * 头节点,头节点是个空节点，真正的节点将添加到头节点的next节点上去
     */
    private AbstractLinkedProcessor first = new AbstractLinkedProcessor(){
        @Override
        public void doProcess(Object content) {
            this.getNext().process(content);
        }
//        @Override
//        public void process(Object content) {
//            this.getNext().process(content);
//        }

    };


    /**
     * 尾节点 ,用来指向当前添加的节点，下次添加节点时，从尾节点开始添加
     */
    private AbstractLinkedProcessor last = first.getNext();


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

    public void process(T content){
        first.doProcess(content);
    }
}



