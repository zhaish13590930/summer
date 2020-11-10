package com.zhaish.parallel;

import com.lmax.disruptor.WorkHandler;

/**
 * @datetime:2020/10/30 16:14
 * @author: zhaish
 * @desc:
 **/
public class LongEventWorkHandler implements WorkHandler<LongEvent> {
    private String prefix;
    @Override
    public void onEvent(LongEvent event) throws Exception {
        System.out.println(prefix+"消费:"+  event.getNum());
    }

    public LongEventWorkHandler(String prefix) {
        this.prefix = prefix;
    }
}
