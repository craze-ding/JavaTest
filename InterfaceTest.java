interface DemoA{
	public abstract int show(int x,int y);
}
interface DemoB{
	int   show(int x,int y);
}
class Demo implements DemoA,DemoB{
	public int  show(int x,int y){
		System.out.println("全部覆盖");
		return x+y+3;
	}
}
class InterfaceTest{
	public static void main(String[] args) {
		Demo d=new Demo();
		System.out.println(d.show(1,2));
	}
}