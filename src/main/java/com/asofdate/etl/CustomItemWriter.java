package com.asofdate.etl;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/22.
 */
public class CustomItemWriter implements ItemWriter<Object> {
    @Override
    public void write(List<?> list) throws Exception {
        System.out.println("CustomItemWriter");
    }
}
