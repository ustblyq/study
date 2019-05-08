package multiThread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞队列实现（阻塞队列内部也是lock condition）
 * @Author: yiqiang.liu
 * @Date: 2019/3/12
 */
public class ProducerConsumer3 {

    private static final int MAX_SIZE = 5;
    BlockingQueue<String> products = new LinkedBlockingQueue<String>(MAX_SIZE);

    class Producer implements Runnable {
        public void run() {
            while (true) {
                if(products.size() == MAX_SIZE) {
                    System.out.println("生产者" + Thread.currentThread().getName() +"仓库已满");
                }
                try {
                    products.put("product");
                    System.out.println("生产者" + Thread.currentThread().getName() + "容量： " + products.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer implements Runnable {

        public void run() {
            while (true) {
                if(products.size() == 0) {
                    System.out.println("消费者" + Thread.currentThread().getName() +"仓库已空");
                }
                try {
                    products.take();
                    System.out.println("消费者" + Thread.currentThread().getName() + "容量： " + products.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        ProducerConsumer3 producerConsumer = new ProducerConsumer3();
        Producer producer = producerConsumer.new Producer();
        Consumer consumer = producerConsumer.new Consumer();
        for (int i = 0; i < 10; i++) {
            new Thread(producer).start();
            new Thread(consumer).start();
        }

    }
}
