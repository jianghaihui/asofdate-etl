package com.asofdate.dispatch;

import com.asofdate.dispatch.service.SysConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hzwy23 on 2017/6/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysConfigServiceTest {
    @Autowired
    private SysConfigService sysConfigService;

    @Test
    public void testGetValue(){
        System.out.println(sysConfigService.getValue("mas","CONF0001"));
    }
}
