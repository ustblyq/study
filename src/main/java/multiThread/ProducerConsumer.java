package multiThread;

import java.util.LinkedList;
import java.util.List;

/**
 * wait() 和 notify() 实现
 * @Author: yiqiang.liu
 * @Date: 2019/3/12
 */
public class ProducerConsumer {

    private static final int MAX_SIZE = 5;
    List<String> products = new LinkedList<String>();
    class Producer implements Runnable{
        public void run() {
            synchronized (products) {
                while(true) {
                    while(products.size() == MAX_SIZE) {
                        System.out.println("生产者" + Thread.currentThread().getName() + "仓库已满！");
                        try {
                            products.wait();
                        } catch (Exception e) {
                            System.out.println("...");
                        }
                    }
                    products.add("product");
                    System.out.println("生产者" + Thread.currentThread().getName() + " 容量：" + products.size());
                    products.notify();
                }
            }

        }
    }

    class Consumer implements Runnable {

        public void run() {
            while(true) {
                synchronized (products) {
                    while(products.size() == 0) {
                        System.out.println("消费者"+ Thread.currentThread().getName()+ "仓库为空");
                        try {
                            products.wait();
                        } catch (Exception e) {
                            System.out.println(".....");
                        }
                    }
                    products.remove(products.size() -1);
                    System.out.println("消费者" + Thread.currentThread().getName() + "剩余：" + products.size());
                    products.notify();
                }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer producerConsumer = new ProducerConsumer();
        Producer producer = producerConsumer.new Producer();
        Consumer consumer = producerConsumer.new Consumer();
        for(int i = 0; i < 10; i++) {
            new Thread(producer).start();
            new Thread(consumer).start();
        }

    }
}
