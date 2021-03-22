import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {

    final Lock lock = new ReentrantLock();
    final Condition notFull  = lock.newCondition(); 
    final Condition notEmpty = lock.newCondition(); 
    
    final String[] items = new  String[100];
    int putptr, takeptr, count;
 
    public void put(String x) throws InterruptedException {
      lock.lock(); try {
        while (count == items.length)
          notFull.await();
        items[putptr] = x;
        if (++putptr == items.length) putptr = 0;
        ++count;
        System.out.println(Thread.currentThread().getName()+"--生产者puls--"+items[putptr]+count);	
        notEmpty.signal();
      } finally { lock.unlock(); }
    }
 
    public String take() throws InterruptedException {
      lock.lock(); try {
        while (count == 0)
          notEmpty.await();
          String x = items[takeptr];
        if (++takeptr == items.length) takeptr = 0;
        --count;
        System.out.println(Thread.currentThread().getName()+"--生产者puls--"+items[takeptr]+count);
        notFull.signal();
        return (String) x;
      } finally { lock.unlock(); }
    }
}
class Producers1 implements Runnable{
	BoundedBuffer b;	
	Producers1(BoundedBuffer b){
		this.b=b;
	}
	public void run(){
		int x=0;
		while(true){
			if(x==0){
				try {
                    b.put("汉堡");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
			}
			else{
				try {
                    b.put("烤鸭");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
			}
			x=(++x)%2;
		}
	}
}

class Consumers1 implements Runnable{
	BoundedBuffer b;
	Consumers1(BoundedBuffer b){
		this.b=b;
	}
	public void run(){
		while(true){
			try {
                b.take();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		}
	}
}

public class Massproductionconsumption {
    public static void main(String[] args) {
        BoundedBuffer r=new BoundedBuffer();    		
		Producers1 in=new Producers1(r);//创建写任务
		Consumers1 ou=new Consumers1(r);//创建取任务
		Thread t0=new Thread(in);//创建生产者线程0
        Thread t1=new Thread(in);//创建生产者线程1
		Thread t2=new Thread(ou);//创建消费者线程2
        Thread t3=new Thread(ou);//创建消费者线程3
		{
        t0.start();
		t1.start();
		t2.start();
		t3.start();
        } 
    }       
        
}
