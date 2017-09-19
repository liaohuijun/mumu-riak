# mumu-riak riak非关系缓存数据库
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/mumucache/mumu-riak/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.weibo/motan.svg?label=Maven%20Central)](https://github.com/mumucache/mumu-riak)
[![Build Status](https://travis-ci.org/mumucache/mumu-riak.svg?branch=master)](https://travis-ci.org/mumucache/mumu-riak)
[![codecov](https://codecov.io/gh/mumucache/mumu-riak/branch/master/graph/badge.svg)](https://codecov.io/gh/mumucache/mumu-riak)
[![OpenTracing-1.0 Badge](https://img.shields.io/badge/OpenTracing--1.0-enabled-blue.svg)](http://opentracing.io)

## riak 简介
***Riak是以 Erlang 编写的一个高度可扩展的分布式数据存储，Riak的实现是基于Amazon的Dynamo论文，Riak的设计目标之一就是高可用。Riak支持多节点构建的系统，每次读写请求不需要集群内所有节点参与也能胜任。提供一个灵活的 map/reduce 引擎，一个友好的 HTTP/JSON 查询接口。Riak 非常易于部署和扩展。可以无缝地向群集添加额外的节点。link walking 之类的特性以及对 Map/Reduce 的支持允许实现更加复杂的查询。除了 HTTP API 外，Riak 还提供了一个原生 Erlang API 以及对 Protocol Buffer 的支持。***
## riak KV缓存系统
Riak KV is a distributed NoSQL database designed to deliver maximum data availability by distributing data across multiple servers. As long as your Riak KV client can reach one Riak server, it should be able to write data.
## riak TS
Riak TS is a distributed NoSQL key/value store optimized for time series data. With TS, you can associate a number of data points with a specific point in time. TS uses discrete slices of time to co-locate data. For example, humidity and temperature readings from a meter reported during the same slice of time will be stored together on disk.

Riak TS uses tables defined according to a schema for each coherent group of data. This allows for both flexibility, in terms of collecting many different sets of data, and enough structure to make the experience of working with the collected data better.
## riak CS
Riak CS (Cloud Storage) is easy-to-use object storage software built on top of Riak KV, Basho’s distributed database. Riak CS is designed to provide simple, available, distributed cloud storage at any scale, and can be used to build cloud architectures—be they public or private—or as storage infrastructure for heavy-duty applications and services. Riak CS’s API is Amazon S3 compatible and supports per-tenant reporting for use cases involving billing and metering.

## riak安装
真心没有见过比riak还难安装的软件了，找了N中办法，都没有安装成功，总是报错。终于今天使用rpm安装成功。
```
curl -s https://packagecloud.io/install/repositories/basho/riak/script.rpm.sh | sudo bash
sudo yum install riak-2.2.3-1.el7.centos.x86_64
```
**安装完成后 需要修改配置文件:/etc/riak/riak.conf**
```
listener.http.internal = 192.168.11.25:8098  
listener.protobuf.internal = 192.168.11.25:8087
```
**以上为centos7安装步骤，如果其他系统，请点击下面的链接**  
[https://packagecloud.io/basho/riak/packages/el/7/riak-2.2.3-1.el7.centos.x86_64.rpm](https://packagecloud.io/basho/riak/packages/el/7/riak-2.2.3-1.el7.centos.x86_64.rpm)

## 测试


## 相关阅读  
[riak源码](https://github.com/basho/riak)   
[riak-java-client源码](https://github.com/basho/riak-java-client)    
[riak docs文档](https://www.tiot.jp/riak-docs/)  

## 联系方式
**以上观点纯属个人看法，如有不同，欢迎指正。  
email:<babymm@aliyun.com>  
github:[https://github.com/babymm](https://github.com/babymm)**
