package com.lovecws.mumu.riak;

/**
 * kafka基本配置信息
 */
public class RiakConfiguration {

    public static String RIAK_HOST="";//多个以';'号分割
    public static final int RIAK_PORT=8087;

    static {
        //从环境变量中 获取
        String RIAK_HOST_ENV = System.getenv("RIAK_HOST");
        if(RIAK_HOST_ENV!=null){
            RIAK_HOST=RIAK_HOST_ENV;
        }else{
            RIAK_HOST="192.168.11.25";
        }
    }
}
