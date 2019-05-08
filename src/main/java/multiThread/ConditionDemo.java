package multiThread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: yiqiang.liu
 * @Date: 2019/3/13
 */
public class ConditionDemo {

    private Lock lock = new ReentrantLock();
    private Condition con1 = lock.newCondition();
    private Condition con2 = lock.newCondition();
    StringBuffer sb = new StringBuffer();


    class Myrun1 implements Runnable {

        public void run() {

        }
    }
}


class PrintTask<T> implements Runnable {
    private List<T> charList = new ArrayList<T>();	//需打印的队列。由于打印项目既包含字符也包含递增的数字，此处使用泛型
    private int period;		//每次打印的个数
    private int priority;	//优先级，即在多个队列中的打印次序
    private int total;		//打印队列总数
    public static int sequence = 0;		//当前打印的队列序号
    private static final Lock lock = new ReentrantLock();		//由于需要锁定的sequence是类成员，创建一个static锁，保证该类不同线程实例能够感知到signalAll()
    private static final Condition condition = lock.newCondition();	//类Condition成员
    //private final Lock lock = new ReentrantLock();----------->当不使用static定义Lock和Condition时，由于不同线程为不同的实例，相互之间
    //private final Condition condition = lock.newCondition();	无法感知其他类发出的signalAll()，导致线程之间相互等待却无法得到响应

    public PrintTask(List<T> charList, int period, int priority, int total) {
        this.charList = charList;
        this.period = period;
        this.priority = priority;
        this.total = total;
    }

    public void run() {
        Iterator<T> iter = charList.iterator();
        while(true) {
            lock.lock();	//锁定
            try {
                if (sequence % total == priority) {
                    for (int i = 0; i < period; i++) {
                        if(iter.hasNext()) {
                            System.out.print(iter.next());
                        }
                        else {
                            break;
                        }
                    }
                    ++sequence;
                    condition.signalAll();	//该类其他实例均能接受到该signalAll()方法
                }
                else {
                    condition.await();	//类成员condition的await()方法，告知类的所有实例均感知signalAll()
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {
                lock.unlock();
            }
        }
    }
}
