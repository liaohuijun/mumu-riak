package com.lovecws.mumu.riak.quickstart;

import com.basho.riak.client.api.RiakClient;
import org.junit.Test;

public class RiakQuickstartClientTest {

    @Test
    public void client(){
        RiakQuickstartClient riakQuickstartClient=new RiakQuickstartClient();
        RiakClient client = riakQuickstartClient.client();
        System.out.println("riak建立客户端连接"+client);
    }

    @Test
    public void clientCluster(){
        RiakQuickstartClient riakQuickstartClient=new RiakQuickstartClient();
        RiakClient cluster = riakQuickstartClient.cluster();
        System.out.println("riak建立客户端集群连接"+cluster);
    }
}
