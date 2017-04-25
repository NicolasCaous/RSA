package main;

public class main {

	public static void main(String[] args) {
		
		RSA rsa = new RSA();
		
		System.out.println("belle");
		System.out.println(rsa.encript("belle"));
		System.out.println(rsa.decript(rsa.encript("belle")));
		
		rsa.changePrimes();
		System.out.println();
		
		System.out.println("belle");
		System.out.println(rsa.encript("belle"));
		System.out.println(rsa.decript(rsa.encript("belle")));
		
		rsa.changePrimes();
		System.out.println();
		
		System.out.println("belle");
		System.out.println(rsa.encript("belle"));
		System.out.println(rsa.decript(rsa.encript("belle")));
	
	}

}
