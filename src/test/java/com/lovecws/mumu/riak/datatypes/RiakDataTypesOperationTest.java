package com.lovecws.mumu.riak.datatypes;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class RiakDataTypesOperationTest {

    private static final RiakDataTypesOperation operation=new RiakDataTypesOperation();

    @Test
    public void counter() throws ExecutionException, InterruptedException {
        operation.counter("lovecws");
    }

    @Test
    public void updateCounter() throws ExecutionException, InterruptedException {
        operation.updateCounter("lovecws");
    }

    @Test
    public void set() throws ExecutionException, InterruptedException {
        operation.set("lovecws");
    }

    @Test
    public void updateSet() throws ExecutionException, InterruptedException {
        operation.updateSet("lovecws","123456");
    }

    @Test
    public void map() throws ExecutionException, InterruptedException {
        operation.map("lovecws");
    }

    @Test
    public void updateMap() throws ExecutionException, InterruptedException {
        operation.updateMap("lovecws");
    }
}
