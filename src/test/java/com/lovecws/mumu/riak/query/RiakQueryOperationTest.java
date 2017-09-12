package com.lovecws.mumu.riak.query;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class RiakQueryOperationTest {
    private static final RiakQueryOperation operation=new RiakQueryOperation();

    @Test
    public void rawIndexQuery() throws ExecutionException, InterruptedException {
        operation.rawIndexQuery();
    }

    @Test
    public void binIndexQuery() throws ExecutionException, InterruptedException {
        operation.binIndexQuery();
    }

    @Test
    public void intIndexQuery() throws ExecutionException, InterruptedException {
        operation.intIndexQuery();
    }

    @Test
    public void bigIntIndexQuery() throws ExecutionException, InterruptedException {
        operation.bigIntIndexQuery();
    }
}
