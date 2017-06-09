package com.asofdate.dispatch;

import com.asofdate.dispatch.dao.*;
import com.asofdate.dispatch.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DMTests {
    @Autowired
    public TaskDefineDao taskDefineDao;

    @Autowired
    public ArgumentDefineDao argumentDefineDao;

    @Autowired
    public BatchArgumentDao batchArgumentDao;

    @Autowired
    public BatchGroupDao batchGroupDao;

    @Autowired
    public GroupDefineDao groupDefineDao;

    @Autowired
    public BatchDefineDao batchDefineDao;

    @Autowired
    public GroupTaskDao groupTaskDao;

    @Autowired
    public TaskArgumentDao taskArgumentDao;


    @Test
    public void testTaskDefineDao(){
        List<TaskDefineModel> list = taskDefineDao.findAll("mas");
        for (TaskDefineModel m: list){
            System.out.print("code number is :" + m.getCodeNumber());
            System.out.print("code number is :" + m.getCreateUser());
            System.out.print("code number is :" + m.getCreateDate());
            System.out.print("code number is :" + m.getTaskId());
            System.out.print("code number is :" + m.getTaskDesc());
            System.out.print("code number is :" + m.getTaskType());
            System.out.print("code number is :" + m.getTaskTypeDesc());
            System.out.println("");
        }
    }

    @Test
    public void testArgumentDefineDao(){
        List<ArgumentDefineModel> list = argumentDefineDao.findAll("mas");
        for (ArgumentDefineModel m:list){
            System.out.println("argument id is :" + m.getArgId());
        }
    }

    @Test
    public void testBatchArgumentDao(){
        List<BatchArgumentModel> list = batchArgumentDao.findAll("mas");
        for (BatchArgumentModel m : list){
            System.out.println("batch id is :" + m.getBatchId()+", argument is :" + m.getArgId());
        }
    }

    @Test
    public void testBatchGroupDao(){
        List<BatchGroupModel> list = batchGroupDao.findAll("mas");
        for (BatchGroupModel m:list){
            System.out.println("Batch is :" + m.getBatchId()+", group id is :" + m.getGroupId());
        }
    }

    @Test
    public void testGroupDefineDao(){
        List<GroupDefineModel> list = groupDefineDao.findAll("mas");
        for (GroupDefineModel m:list){
            System.out.println("Group id is: "+m.getGroupId());
        }
    }

    @Test
    public void testGroupTaskDao(){
        List<GroupTaskModel> list = groupTaskDao.findAll("mas");
        for (GroupTaskModel m:list){
            System.out.println("group id is:" + m.getGroupId()+",task id is:" + m.getTaskId());
        }
    }

    @Test
    public void testTaskArgumentDao(){
        List<TaskArgumentModel> list = taskArgumentDao.findAll("mas");
        for (TaskArgumentModel m: list){
            System.out.println("task id is :" + m.getTaskId()+",argument id is:"+m.getArgId());
        }
    }

    @Test
    public void testBatchDefineDao(){
        List<BatchDefineModel> list = batchDefineDao.findAll("mas");
        for (BatchDefineModel m:list){
            System.out.println("batch id is:" + m.getBatchDesc());
        }
    }

}
