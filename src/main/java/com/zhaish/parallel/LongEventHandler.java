package com.zhaish.parallel;

import com.lmax.disruptor.EventHandler;

/**
 * @datetime:2020/10/30 13:52
 * @author: zhaish
 * @desc:
 **/
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("消费:" + event.getNum() + "-"+sequence+"--"+endOfBatch);
    }
}
