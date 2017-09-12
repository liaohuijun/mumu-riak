package com.lovecws.mumu.riak.datatypes;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.datatypes.*;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.crdt.types.RiakCounter;
import com.basho.riak.client.core.query.crdt.types.RiakDatatype;
import com.basho.riak.client.core.query.crdt.types.RiakMap;
import com.basho.riak.client.core.query.crdt.types.RiakSet;
import com.basho.riak.client.core.util.BinaryValue;
import com.lovecws.mumu.riak.quickstart.RiakQuickstartClient;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class RiakDataTypesOperation {

    private static final RiakClient client = new RiakQuickstartClient().client();

    public Long counter(String key) throws ExecutionException, InterruptedException {
        Namespace ns = new Namespace("default", "my_bucket");
        Location loc = new Location(ns, key);
        FetchCounter fc = new FetchCounter.Builder(loc).build();
        FetchCounter.Response resp = client.execute(fc);
        Long counter = resp.getDatatype().view();
        System.out.println(counter);
        return counter;
    }

    public RiakCounter updateCounter(String key) throws ExecutionException, InterruptedException {
        Namespace ns = new Namespace("default", "my_bucket");
        Location loc = new Location(ns, key);
        CounterUpdate update = new CounterUpdate(10L);

        UpdateCounter uc = new UpdateCounter.Builder(loc, update).withReturnDatatype(true).build();
        UpdateCounter.Response resp = client.execute(uc);
        RiakCounter counter = resp.getDatatype();
        System.out.println(counter);
        return counter;
    }

    public Set<BinaryValue> set(String key) throws ExecutionException, InterruptedException {
        Namespace ns = new Namespace("hlls", "my_bucket");
        Location loc = new Location(ns, key);
        FetchSet fs = new FetchSet.Builder(loc).build();
        FetchSet.Response resp = client.execute(fs);
        RiakSet rSet = resp.getDatatype();
        Set<BinaryValue> set = rSet.view();
        System.out.println(set);
        return set;
    }

    public RiakSet updateSet(String key,String value) throws ExecutionException, InterruptedException {
        Namespace ns = new Namespace("hlls", "my_bucket");
        Location loc = new Location(ns, key);
        SetUpdate update = new SetUpdate().add(value);

        UpdateSet us = new UpdateSet.Builder(loc, update).withReturnDatatype(true).build();
        UpdateSet.Response resp = client.execute(us);
        RiakSet datatype = resp.getDatatype();
        return datatype;
    }

    public Map<BinaryValue, List<RiakDatatype>> map(String key) throws ExecutionException, InterruptedException {
        Namespace ns = new Namespace("default", "my_bucket");
        Location loc = new Location(ns, key);
        FetchMap fm = new FetchMap.Builder(loc).build();
        FetchMap.Response resp = client.execute(fm);
        RiakMap rMap = resp.getDatatype();
        Map<BinaryValue, List<RiakDatatype>> map = rMap.view();
        return map;
    }

    public RiakMap updateMap(String key) throws ExecutionException, InterruptedException {
        Namespace ns = new Namespace("default", "my_bucket");
        Location loc = new Location(ns, key);

        CounterUpdate cUpdate = new CounterUpdate(10L);
        MapUpdate update = new MapUpdate().update(key, cUpdate);

        UpdateMap um = new UpdateMap.Builder(loc, update).withReturnDatatype(true).build();
        UpdateMap.Response resp = client.execute(um);
        RiakMap map = resp.getDatatype();
        return map;
    }
}
