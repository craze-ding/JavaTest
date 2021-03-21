interface Demo{
	void show1();
	void show2();
}
class InterClassTest{
	static class Animal{

	}
	public static void main(String[] args) {
		show(new Demo(){
			public void show1(){
					System.out.println("this is show1");
			}
			public void show2(){
					System.out.println("thos is show2");
			}
		}
		);
		new Animal();//static不允许持有this!!
	}
	public static void show(Demo d){
		d.show1();
		d.show2();

	}
	void look(){//自动持有本类的this
		new Animal();
	} 
}
