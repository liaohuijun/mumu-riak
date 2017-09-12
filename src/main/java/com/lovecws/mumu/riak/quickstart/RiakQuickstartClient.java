package com.lovecws.mumu.riak.quickstart;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.core.RiakCluster;
import com.basho.riak.client.core.RiakNode;
import com.lovecws.mumu.riak.RiakConfiguration;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

/**
 * 构建 riakClient对象 该对象报错了riak的链接
 */
public class RiakQuickstartClient {

    private static RiakClient riakClient=null;
    private static volatile boolean init=false;

    /**
     * 创建客户端链接
     * @return
     */
    public synchronized RiakClient client() {
        try {
            riakClient = RiakClient.newClient(RiakConfiguration.RIAK_PORT, RiakConfiguration.RIAK_HOST.split(";"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return riakClient;
    }

    /**
     * 集群创建客户端方式
     * @return
     */
    public RiakClient cluster() {
        RiakNode.Builder builder = new RiakNode.Builder();
        builder.withMinConnections(10);
        builder.withMaxConnections(50);
        List<String> addresses = new LinkedList<String>();

        String riakHost = RiakConfiguration.RIAK_HOST;
        for(String host:riakHost.split(";")){
            addresses.add(host);
        }

        List<RiakNode> nodes = RiakNode.Builder.buildNodes(builder, addresses);
        RiakCluster cluster = new RiakCluster.Builder(nodes).build();
        cluster.start();
        RiakClient client = new RiakClient(cluster);
        return client;
    }
}
