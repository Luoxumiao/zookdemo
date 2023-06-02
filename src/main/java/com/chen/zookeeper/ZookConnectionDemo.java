package com.chen.zookeeper;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

public class ZookConnectionDemo {
    public static void main(String[] args) {
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper("ip+port", 4000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                        //收到连接事件并响应成功
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            //CONNECTED
            System.out.println(zooKeeper.getState());
            zooKeeper.create("/runnoob","Hello World".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
