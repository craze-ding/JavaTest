import java.util.concurrent.locks.*; 
class Resource{
	private String name;
	private int count=1;
	private boolean flag=false;
	Lock lock=new ReentrantLock();//创建锁对象
	Condition pro_con=lock.newCondition();//通过已有的锁获取该锁上监视器对象
	Condition con_con=lock.newCondition();	
	public  void set(String name){
		lock.lock();
		try{
			while(flag)//while解决线程唤醒是否去运行?
				try{pro_con.await();}catch(InterruptedException i){}
			this.name=name+count;
            count++;
            System.out.println(Thread.currentThread().getName()+"--生产者puls--"+this.name);			
			flag=true;
			con_con.signal();//解决保证一定会唤醒对方线程	
		}
		finally{
			lock.unlock();//释放锁必须做
		}				
	}
	public  void get(){
		lock.lock();
		try{
			while(!flag)
				try{con_con.await();}catch(InterruptedException i){}            
			System.out.println(Thread.currentThread().getName()+"----消费者puls----"+this.name);
			flag=false;
			pro_con.signal();
		}
		finally{
			lock.unlock();
		}				
	}
}
class Producers implements Runnable{
	Resource r;	
	Producers(Resource r){
		this.r=r;
	}
	public void run(){
		int x=0;
		while(true){
			if(x==0){
				r.set("汉堡");
			}
			else{
				r.set("烤鸭");
			}
			x=(++x)%2;
		}
	}
}
class Consumers implements Runnable{
	Resource r;
	Consumers(Resource r){
		this.r=r;
	}
	public void run(){
		while(true){
			r.get();
		}
	}
}
class ProducersAndConsumersTest{
	public static void main(String[] args) {
		Resource r=new Resource();//创建资源		
		Producers in=new Producers(r);//创建写任务
		Consumers ou=new Consumers(r);//创建取任务
		Thread t0=new Thread(in);//创建生产者线程0
        Thread t1=new Thread(in);//创建生产者线程1
		Thread t2=new Thread(ou);//创建消费者线程2
        Thread t3=new Thread(ou);//创建消费者线程3
		t0.start();
		t1.start();
		t2.start();
		t3.start();
	}
}