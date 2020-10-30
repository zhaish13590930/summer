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

        EventHandler<LongEvent> eventHandler = new LongEventHandler();
        disruptor.handleEventsWith(eventHandler);
        disruptor.start();
        long begin = System.currentTimeMillis();
        LongEventProducer producer = new LongEventProducer(disruptor.getRingBuffer());
        long index = 0;
        while(index < 100){
            producer.onData(index++);
        }
        disruptor.shutdown(1, TimeUnit.SECONDS);
        System.out.println(System.currentTimeMillis() - begin);
    }
}
