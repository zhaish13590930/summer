package com.zhaish.parallel;

import com.lmax.disruptor.RingBuffer;

/**
 * @datetime:2020/10/30 14:10
 * @author: zhaish
 * @desc:
 **/
public class LongEventProducer {
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(long data){
        long seq = ringBuffer.next();
        try {
            LongEvent event = ringBuffer.get(seq);
            event.setNum(data);
            System.out.println(Thread.currentThread().getName()+ " produce " + data);
        }finally {
            ringBuffer.publish(seq);
        }
    }
}
