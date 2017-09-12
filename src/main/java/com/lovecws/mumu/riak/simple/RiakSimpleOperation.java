package com.lovecws.mumu.riak.simple;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.cap.Quorum;
import com.basho.riak.client.api.commands.datatypes.MapUpdate;
import com.basho.riak.client.api.commands.datatypes.RegisterUpdate;
import com.basho.riak.client.api.commands.datatypes.UpdateMap;
import com.basho.riak.client.api.commands.kv.*;
import com.basho.riak.client.core.RiakFuture;
import com.basho.riak.client.core.RiakFutureListener;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.basho.riak.client.core.util.BinaryValue;
import com.lovecws.mumu.riak.quickstart.RiakQuickstartClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * riak 基本操作
 */
public class RiakSimpleOperation {

    private static final RiakClient client = new RiakQuickstartClient().client();

    /**
     * 存储数据
     *
     * @param key   键
     * @param value 值
     */
    public boolean set(String key, String value) {
        Namespace ns = new Namespace("default", "my_bucket");
        Location location = new Location(ns, key);
        RiakObject riakObject = new RiakObject();
        riakObject.setValue(BinaryValue.create(value));
        StoreValue store = new StoreValue.Builder(riakObject)
                .withLocation(location)
                .withOption(StoreValue.Option.W, new Quorum(3)).build();
        try {
            StoreValue.Response response = client.execute(store);
            System.out.println(response);
            return response.hasGeneratedKey();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 异步获取存储的值
     *
     * @param key   键
     * @param value 值
     */
    public void setAsync(String key, String value) {
        Namespace ns = new Namespace("default", "my_bucket");
        Location location = new Location(ns, key);
        RiakObject riakObject = new RiakObject();
        riakObject.setValue(BinaryValue.create(value));
        StoreValue store = new StoreValue.Builder(riakObject)
                .withLocation(location)
                .withOption(StoreValue.Option.W, new Quorum(3)).build();
        RiakFuture<StoreValue.Response, Location> future = client.executeAsync(store);
        //添加监听器
        future.addListener(new RiakFutureListener<StoreValue.Response, Location>() {
            @Override
            public void handle(RiakFuture<StoreValue.Response, Location> riakFuture) {
                if (riakFuture.isSuccess()) {
                    try {
                        System.out.println(new String(riakFuture.get().getGeneratedKey().getValue()));
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 获取存储的值
     *
     * @param key 键
     * @return
     */
    public RiakObject get(String key) {
        Namespace ns = new Namespace("default", "my_bucket");
        Location location = new Location(ns, key);
        FetchValue fv = new FetchValue.Builder(location).build();

        FetchValue.Response response = null;
        try {
            response = client.execute(fv);
            RiakObject obj = response.getValue(RiakObject.class);
            return obj;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步获取存储的值
     *
     * @param key 键
     */
    public void getAsync(String key) {
        Namespace ns = new Namespace("default", "my_bucket");
        Location location = new Location(ns, key);
        FetchValue fv = new FetchValue.Builder(location).build();
        RiakFuture<FetchValue.Response, Location> future = client.executeAsync(fv);
        //添加监听器
        future.addListener(new RiakFutureListener<FetchValue.Response, Location>() {
            @Override
            public void handle(RiakFuture<FetchValue.Response, Location> riakFuture) {
                if (future.isSuccess()) {
                    try {
                        FetchValue.Response response = riakFuture.get();
                        System.out.println(response.getValue(RiakObject.class));
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 批量获取
     * @param key
     * @return
     */
    public List<String> getBatch(String key) {
        try {
            Namespace ns = new Namespace("default", "my_bucket");
            Location location = new Location(ns, key);
            MultiFetch multifetch = new MultiFetch.Builder().build();

            MultiFetch.Response response = client.execute(multifetch);
            List<String> myResults = new ArrayList<String>();
            for (RiakFuture<FetchValue.Response, Location> f : response) {
                myResults.add(f.get().getValue(String.class));
            }
            return myResults;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
        /**
         * 更新
         * @param key 键
         * @param value 值
         */

    public boolean updateBatch(String key, String value) {
        Namespace ns = new Namespace("default", "my_bucket");

        //Location location = new Location(ns, key);
        MapUpdate mu = new MapUpdate();
        mu.update(key, new RegisterUpdate(BinaryValue.create(value)));
        UpdateMap update = new UpdateMap.Builder(ns, mu).build();
        try {
            UpdateMap.Response response = client.execute(update);
            return response.hasGeneratedKey();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更新
     *
     * @param key   key
     * @param value 值
     */
    public void update(String key, String value) {
        Namespace ns = new Namespace("default", "my_bucket");
        Location loc = new Location(ns, key);
        UpdateValue.Update<String> update = new UpdateValue.Update<String>() {
            @Override
            public String apply(String o) {
                return value;
            }
        };
        UpdateValue uv = new UpdateValue.Builder(loc).withUpdate(update).build();

        try {
            UpdateValue.Response response = client.execute(uv);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除key
     * @param key
     */
    public void delete(String key){
        Namespace ns = new Namespace("default", "my_bucket");
        Location loc = new Location(ns, key);
        DeleteValue dv = new DeleteValue.Builder(loc).build();
        try {
            Void execute = client.execute(dv);
        } catch (ExecutionException|InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取bucket下的k/v集合
     */
    public ListKeys.Response list(String bucketName) {
        Namespace ns = new Namespace("default", bucketName);
        ListKeys lk = new ListKeys.Builder(ns).build();

        ListKeys.Response response = null;
        try {
            return client.execute(lk);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
