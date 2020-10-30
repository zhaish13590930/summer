package com.zhaish.parallel;

import com.lmax.disruptor.EventFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @datetime:2020/10/30 13:43
 * @author: zhaish
 * @desc:
 **/
public class LongEventFactory implements EventFactory<LongEvent> {
    private AtomicLong N = new AtomicLong(0);
    @Override
    public LongEvent newInstance() {
        return new LongEvent(N.getAndIncrement());
    }
}
