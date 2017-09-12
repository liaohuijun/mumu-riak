package com.lovecws.mumu.riak.bucket;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.buckets.FetchBucketProperties;
import com.basho.riak.client.api.commands.buckets.ListBuckets;
import com.basho.riak.client.api.commands.buckets.StoreBucketProperties;
import com.basho.riak.client.api.commands.datatypes.RegisterUpdate;
import com.basho.riak.client.core.operations.FetchBucketPropsOperation;
import com.basho.riak.client.core.query.BucketProperties;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.util.BinaryValue;
import com.lovecws.mumu.riak.quickstart.RiakQuickstartClient;

import java.util.concurrent.ExecutionException;

public class RiakBucketOperation {

    private static final RiakClient client = new RiakQuickstartClient().client();

    public void bucket(String bucketType) throws ExecutionException, InterruptedException {
        ListBuckets lb = new ListBuckets.Builder(bucketType).build();
        ListBuckets.Response response = client.execute(lb);
        for (Namespace ns : response) {
            System.out.println(ns.getBucketName());
        }
    }

    public void fetchProperties(String bucketType, String bucketName) throws ExecutionException, InterruptedException {
        Namespace ns = new Namespace(bucketType, bucketName);
        FetchBucketProperties fbp = new FetchBucketProperties.Builder(ns).build();
        FetchBucketPropsOperation.Response resp = client.execute(fbp);
        BucketProperties props = resp.getBucketProperties();
        System.out.println(props);
    }

    public void storeProperties(String bucketType, String bucketName) throws ExecutionException, InterruptedException {
        Namespace ns = new Namespace(bucketType, bucketName);
        StoreBucketProperties sbp =
                new StoreBucketProperties.Builder(ns)
                        .withAllowMulti(true)
                        .build();
        client.execute(sbp);
    }

    public void datatypes(){
        BinaryValue binaryValue = new RegisterUpdate("lovecws").get();

    }
}
