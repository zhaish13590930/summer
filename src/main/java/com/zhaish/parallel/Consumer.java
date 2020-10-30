package com.zhaish.parallel;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.BasicExecutor;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @datetime:2020/10/29 10:25
 * @author: zhaish
 * @desc:
 **/
public class Consumer {
    public static void main(String[] args) throws TimeoutException {
        EventFactory<LongEvent> eventFactory = new LongEventFactory();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ThreadFactory threadFactory = new ThreadFactory() {
            private String prefix = "basicThread-";
            private int num = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,prefix+(num++));
            }
        };
        int ringBufferSize = 1<<4; // RingBuffer 大小，必须是 2 的 N 次方；

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(eventFactory,
                ringBufferSize, threadFactory,ProducerType.SINGLE,
                new YieldingWaitStrategy());

        EventHandler<LongEvent> eventHandler = new LongEventHandler("A");
        EventHandler<LongEvent> eventHandler1 = new LongEventHandler("B");

        disruptor.handleEventsWith(eventHandler,eventHandler1);
        // 问题1: handler 菱形执行怎么写?  可以整合guava包里的future with then来处理
        // 问题2: 生产生产的速度超过了消费者的速度,会覆盖没消费掉的数据么?  不会
        disruptor.start();
        long begin = System.currentTimeMillis();
        LongEventProducer producer = new LongEventProducer(disruptor.getRingBuffer());
        long index = 0;
        while(index < 100){
            producer.onData(index++);
        }
        System.out.println(System.currentTimeMillis() - begin);
        disruptor.shutdown(20, TimeUnit.SECONDS);
        //生产完之后关闭,如果消费者还没消费完全,会抛出com.lmax.disruptor.TimeoutException,然后不会关闭掉disruptor
        System.out.println(System.currentTimeMillis() - begin);
    }
}
