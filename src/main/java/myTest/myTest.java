package myTest;

import jdk.jshell.EvalException;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class myTest implements Runnable{
    public static void main(String[] args) {
        long time = new Date().getTime();
        System.out.println(time);
    }
    @Override
    public void run() {
        System.out.println(System.currentTimeMillis()+" ID: "+Thread.currentThread().getId());
    }
}
