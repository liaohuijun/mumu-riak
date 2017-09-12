package com.lovecws.mumu.riak.query;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.indexes.*;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.util.BinaryValue;
import com.lovecws.mumu.riak.quickstart.RiakQuickstartClient;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class RiakQueryOperation {

    private static final RiakClient client = new RiakQuickstartClient().client();

    public void rawIndexQuery() throws ExecutionException, InterruptedException {
        byte[] bytes = new byte[] { 1,2,3,4};
        BinaryValue key = BinaryValue.create(bytes);
        Namespace ns = new Namespace("index", "my_bucket");
        RawIndexQuery q = new RawIndexQuery.Builder(ns, "my_index", SecondaryIndexQuery.Type._BIN, key).build();
        RawIndexQuery.Response response = client.execute(q);
        response.forEach(new Consumer<SecondaryIndexQuery.Response.Entry<BinaryValue>>() {
            @Override
            public void accept(SecondaryIndexQuery.Response.Entry<BinaryValue> binaryValueEntry) {
                System.out.println(binaryValueEntry);
            }
        });
    }

    public void binIndexQuery() throws ExecutionException, InterruptedException {
        Namespace ns = new Namespace("my_type", "my_bucket");
        String key = "some_key";
        BinIndexQuery q = new BinIndexQuery.Builder(ns, "my_index", key).build();
        BinIndexQuery.Response response = client.execute(q);
        System.out.println(response);
    }

    public void intIndexQuery() throws ExecutionException, InterruptedException {
        Namespace ns = new Namespace("index", "my_bucket");
        long key = 1234L;
        IntIndexQuery q = new IntIndexQuery.Builder(ns, "my_index", key).build();
        IntIndexQuery.Response resp = client.execute(q);
        System.out.println(resp);
    }

    public void bigIntIndexQuery() throws ExecutionException, InterruptedException {
        Namespace ns = new Namespace("my_type", "my_bucket");
        BigInteger key = new BigInteger("123".getBytes());
        BigIntIndexQuery q = new BigIntIndexQuery.Builder(ns, "my_index", key).build();
        BigIntIndexQuery.Response resp = client.execute(q);
        System.out.println(resp);
    }
}
