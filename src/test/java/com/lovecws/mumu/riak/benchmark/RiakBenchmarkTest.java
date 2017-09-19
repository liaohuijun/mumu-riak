package com.lovecws.mumu.riak.benchmark;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.cap.Quorum;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.ListKeys;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.basho.riak.client.core.util.BinaryValue;
import com.lovecws.mumu.riak.quickstart.RiakQuickstartClient;
import com.lovecws.mumu.riak.simple.RiakSimpleOperation;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ExecutionException;

public class RiakBenchmarkTest {

    private static final RiakClient client = new RiakQuickstartClient().client();
    private static final RiakSimpleOperation operation=new RiakSimpleOperation();

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void set() throws ExecutionException, InterruptedException {
        Namespace ns = new Namespace("default", "benchmark_bucket");
        Location location = new Location(ns, String.valueOf(System.nanoTime()));
        RiakObject riakObject = new RiakObject();
        riakObject.setValue(BinaryValue.create("123"));
        StoreValue store = new StoreValue.Builder(riakObject)
                .withLocation(location)
                .withOption(StoreValue.Option.W, new Quorum(1)).build();
        client.execute(store);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void get() throws ExecutionException, InterruptedException {
        Namespace ns = new Namespace("default", "benchmark_bucket");
        Location location = new Location(ns, "lovecws");
        FetchValue fv = new FetchValue.Builder(location).build();

        FetchValue.Response response = client.execute(fv);
        response.getValue(RiakObject.class);
    }

    @Test
    public void testSet() throws RunnerException {
        System.out.println("jmh before:");
        ListKeys.Response responseBefore = operation.list("benchmark_bucket");
        for (Location location : responseBefore) {
            System.out.println(location.getKeyAsString());
        }
        Options Options= new OptionsBuilder().
                include(RiakBenchmarkTest.class.getSimpleName()+".set$").
                measurementIterations(10).
                warmupIterations(10).
                forks(1).
                threads(1).
                build();
        new Runner(Options).run();
        System.out.println("jmh after:");
        ListKeys.Response responseAfter = operation.list("benchmark_bucket");
        for (Location location : responseAfter) {
            System.out.println(location.getKeyAsString());
        }
    }

    @Test
    public void testGet() throws RunnerException {
        Options Options= new OptionsBuilder().
                include(RiakBenchmarkTest.class.getSimpleName()+".get").
                measurementIterations(10).
                warmupIterations(10).
                forks(1).
                threads(1).
                build();
        new Runner(Options).run();
    }
}
