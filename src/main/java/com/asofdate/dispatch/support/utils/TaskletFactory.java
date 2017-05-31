package com.asofdate.dispatch.support.utils;

import org.springframework.batch.core.step.tasklet.Tasklet;

/**
 * Created by hzwy23 on 2017/5/31.
 */
public class TaskletFactory {
    // shell 脚本
    private final String SHELL_TYPE = "1";
    // 存储过程
    private final String PROC_TYPE = "2";
    // cmd 脚本
    private final String CMD_TYPE = "3";
    // jar包
    private final String JAR_TYPE = "4";
    // 二进制
    private final String BINARY_TYPE = "5";

    public Tasklet getTasklet(String typeId,String scritpFile){
        switch (typeId){
            case CMD_TYPE:
                return new CmdTasklet(scritpFile);
        }
        return null;
    }

}
