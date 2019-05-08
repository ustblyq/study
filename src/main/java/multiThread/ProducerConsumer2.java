package multiThread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock 和 condition 实现
 * @Author: yiqiang.liu
 * @Date: 2019/3/12
 */
public class ProducerConsumer2 {

    private static final int MAX_SIZE = 5;
    List<String> products = new LinkedList<String>();
    Lock lock = new ReentrantLock();
    Condition full = lock.newCondition();
    Condition empty = lock.newCondition();

    class Producer implements Runnable {
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (products.size() == MAX_SIZE) {
                        System.out.println("生产者" + Thread.currentThread().getName() + "仓库已满！");
                        full.await();
                    }
                    products.add("product");
                    System.out.println("生产者" + Thread.currentThread().getName() + " 容量：" + products.size());
                    empty.signalAll();
                } catch (Exception e) {
                    System.out.println("...");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class Consumer implements Runnable {

        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (products.size() == 0) {
                        System.out.println("消费者" + Thread.currentThread().getName() + "仓库已空！");
                        empty.await();
                    }
                    products.remove(products.size() - 1);
                    System.out.println("消费者" + Thread.currentThread().getName() + " 容量：" + products.size());
                    full.signalAll();
                } catch (Exception e) {
                    System.out.println("...");
                } finally {
                    lock.unlock();
                }
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer2 producerConsumer = new ProducerConsumer2();
        Producer producer = producerConsumer.new Producer();
        Consumer consumer = producerConsumer.new Consumer();
        for (int i = 0; i < 10; i++) {
            new Thread(producer).start();
            new Thread(consumer).start();
        }

    }
}
