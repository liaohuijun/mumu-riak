package com.lovecws.mumu.riak.simple;

import com.basho.riak.client.api.commands.kv.ListKeys;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.RiakObject;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class RiakSimpleOperationTest {

    private static final RiakSimpleOperation operation=new RiakSimpleOperation();

    @Test
    public void set(){
        boolean success = operation.set("lovecws", "cws");
        System.out.println(success);
    }

    @Test
    public void setAsync(){
        operation.setAsync("love", "cws");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void get(){
        RiakObject riakObject = operation.get("lover");
        System.out.println(riakObject);
    }



    @Test
    public void getAsync(){
        operation.getAsync("lover");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBatch(){
        operation.getBatch("lovecws");
    }

    @Test
    public void update(){
        String key="lover";
        String value="lovercws"+System.currentTimeMillis();
        System.out.println("update before:key:"+key+",value:"+operation.get(key).getValue());
        operation.update(key,value);
        System.out.println("update after:key:"+key+",value:"+operation.get(key).getValue());
    }
    @Test
    public void updateBatch(){
        operation.updateBatch("lovecws","123456");
    }
    @Test
    public void delete(){
        operation.set("delete","测试删除");
        operation.delete("delete");
        RiakObject riakObject = operation.get("delete");
        System.out.println(riakObject);
    }

    @Test
    public void list(){
        ListKeys.Response list = operation.list("my_bucket");
        for (Location l : list) {
            System.out.println(l.getKeyAsString());
        }
    }
}
