package com.asofdate.dispatch.support;


import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * Created by hzwy23 on 2017/5/22.
 */
public class CustomItemReader implements ItemReader<Object> {
    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("CustomItemReader");
        Thread.sleep(2000);
        return null;
    }
}
