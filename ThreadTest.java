class Ticket implements Runnable //extends Thread
{
	private int num=100;
	public void sale(){
		while(true){
			if(num >0){
				try{
					Thread.sleep(10);	
				}
				catch(InterruptedException in ){					
				}
				
				System.out.println(Thread.currentThread().getName()+"....."+num--);
			}
		}
	}
	public void run(){
		sale();
	}
	
}
class ThreadTest{
	public static void main(String[] args) {		
		Ticket T=new Ticket();//创建线程任务对象
		Thread t1=new Thread(T);
		Thread t2=new Thread(T);
		Thread t3=new Thread(T);
		/* Ticket t2=new Ticket();
		Ticket t3=new Ticket(); */
		t1.start();
		t2.start();
		t3.start();

	}
}