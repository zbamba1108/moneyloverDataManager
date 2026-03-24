package dev.boog.moneyloverdatamanager.configs.datasource;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Monitor {

    private final HikariDataSource dataSource;

    private final ThreadPoolTaskExecutor dbExecutor;

    private final AtomicInteger saturatedDbCounter = new AtomicInteger(0);

    private final ConcurrentMap<Integer, Integer> threadsWaitingMap = new ConcurrentHashMap<>();

    public Monitor(HikariDataSource dataSource, @Qualifier("taskExecutor") Executor dbExecutor) {
        this.dataSource = dataSource;
        this.dbExecutor = (ThreadPoolTaskExecutor) dbExecutor;
    }

    public void logHikariPoolStats(int schedulerFrequency) {
        HikariPoolMXBean poolProxy = dataSource.getHikariPoolMXBean();
        System.out.println(
                "-------------------------------------------------------------\n" +
                "DB connection pool \n" +
                "Active: " + poolProxy.getActiveConnections() +
                " | Idle: " + poolProxy.getIdleConnections() +
                " | Total: " + poolProxy.getTotalConnections() +
                " | Threads Awaiting: " + poolProxy.getThreadsAwaitingConnection() +
                "\n-------------------------------------------------------------") ;
        if (poolProxy.getActiveConnections() == poolProxy.getTotalConnections()) {
            saturatedDbCounter.getAndAdd(schedulerFrequency);
        }

        threadsWaitingMap.merge(poolProxy.getThreadsAwaitingConnection(), schedulerFrequency, Integer::sum);
    }

    public void logDbExecutorStats(StopWatch stopWatch) {
        ThreadPoolExecutor executor = dbExecutor.getThreadPoolExecutor();

        System.out.println(
                "Thread pool \n" +
                "Active: " + executor.getActiveCount() +
                        " | Pool: " + executor.getPoolSize() +
                        " | Queue: " + executor.getQueue().size() +
                        " | Completed: " + executor.getCompletedTaskCount() +
                        " | time: " + stopWatch.getTime(TimeUnit.MILLISECONDS)
        );
    }

    public int getSaturatedDbCounter() {
        return saturatedDbCounter.get();
    }

    public ConcurrentMap<Integer, Integer> getMaxThreadsWaiting() {
        return threadsWaitingMap;
    }
}
