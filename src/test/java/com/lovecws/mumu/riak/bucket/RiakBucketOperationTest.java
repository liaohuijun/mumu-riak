package com.lovecws.mumu.riak.bucket;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class RiakBucketOperationTest {
    private static final RiakBucketOperation operation=new RiakBucketOperation();

    @Test
    public void bucket(){
        try {
            operation.bucket("default");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fetchProperties(){
        try {
            operation.fetchProperties("default","my_bucket");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void storeProperties(){
        try {
            operation.storeProperties("default","lovecws");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
