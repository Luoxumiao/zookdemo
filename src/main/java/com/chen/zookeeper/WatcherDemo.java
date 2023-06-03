package com.chen.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

/**
 * @Author: LXM
 * @CreateTime: 2023/6/3-12:19
 * @Description: Watcher原理解析
 */
public class WatcherDemo implements Watcher {

    static ZooKeeper zooKeeper;

    static {
        try {
            zooKeeper = new ZooKeeper("", 4000, new WatcherDemo());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("eventType:"+watchedEvent.getType());
        if (watchedEvent.getType() == Event.EventType.NodeDataChanged){
            try {
                zooKeeper.exists(watchedEvent.getPath(),true);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String path = "/watcher";

        if (zooKeeper.exists(path,false) == null){
            zooKeeper.create(path,"hello world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }
        TimeUnit.SECONDS.sleep(1);

        System.out.println("====================");
        Stat stat = zooKeeper.exists(path, true);
        System.in.read();
    }
}
