package main;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
	
	private BigInteger e, d, p, q, n, psi;
	private String[] primes;
	private static Random rand;
	
	public RSA() {
		
		this.primes = new String[] {
			//primes	
		};
		
		changePrimes();
		
	}
	
	public RSA(String[] primes) {
		
		this.primes = primes;
		
		changePrimes();
		
	}
	
	private BigInteger setDecript(BigInteger a, BigInteger b, BigInteger c, BigInteger d) {
		if(a.equals(BigInteger.ONE)) {
			return b;
		}  else {
			return setDecript(c, d, a.subtract(a.divide(c).multiply(c)).mod(this.psi), b.subtract(a.divide(c).multiply(d)).mod(this.psi));
		}
	}
	
	private BigInteger nextPrime(BigInteger x) {
		BigInteger y = x.add(BigInteger.ONE);
		while(!isPrime(y)) {
			y = y.add(BigInteger.ONE);
		}
		return y;
	}
	
	private boolean isPrime(BigInteger x) {
		BigInteger y = new BigInteger("2");
		while(y.compareTo(x) == -1) {
			if(x.remainder(y).equals(BigInteger.ZERO)) {
				return false;
			}
			y = y.add(BigInteger.ONE);
		}
		return true;
	}
	
	public String encript(String m) {
		String bin_s = StringToBinary(m);
		BigInteger bin_d = BinaryToDecimal(bin_s);
		return DecimalToHex(bin_d.modPow(this.e, this.n));
	}
	
	public String encript(String m, String e, String n) {
		String bin_s = StringToBinary(m);
		BigInteger bin_d = BinaryToDecimal(bin_s);
		return DecimalToHex(bin_d.modPow(new BigInteger(e), new BigInteger(n)));
	}
	
	public String decript(String m) {
		BigInteger bin_d = HexToDecimal(m);
		return DecimalToString(bin_d.modPow(this.d, this.n));
	}
	
	public String decript(String m, String d, String n) {
		BigInteger bin_d = HexToDecimal(m);
		return DecimalToString(bin_d.modPow(new BigInteger(d), new BigInteger(n)));
	}
	
	public void changePrimes() {
		rand = new Random(System.currentTimeMillis());
		
		this.p = new BigInteger(this.primes[rand.nextInt(this.primes.length)]);
		do {
			this.q = new BigInteger(this.primes[rand.nextInt(this.primes.length)]);
		} while(this.p.equals(this.q));
		
		this.n = new BigInteger(this.p.multiply(this.q).toString());
		this.psi = new BigInteger(this.p.subtract(BigInteger.ONE).multiply(this.q.subtract(BigInteger.ONE)).toString());
		
		this.e = new BigInteger("2");
		while(this.psi.mod(this.e).equals(BigInteger.ZERO)) {
			this.e = nextPrime(this.e);
		}
		this.d = setDecript(this.psi, this.psi, this.e, BigInteger.ONE);
	}
	
	public String[] getValues() {
		return new String[] {this.e.toString(), this.d.toString(), this.p.toString(), this.q.toString(), this.n.toString(), this.psi.toString()};
	}
	
	private String StringToBinary(String s) {
		byte[] b = s.getBytes();
		StringBuffer buff = new StringBuffer();
		for(int i=0; i<b.length; ++i) {
			buff.append(Integer.toBinaryString((b[i] & 0xFF) + 0x100).substring(1));
		}
		return buff.toString();
	}
	
	private String BinaryToHex(String s) {
		StringBuffer buff = new StringBuffer();
		for(int i=0; i<s.length(); i=i+4) {
			int sum = Integer.parseInt(s.substring(i, i+1)) * 8 + Integer.parseInt(s.substring(i+1, i+2)) * 4 + Integer.parseInt(s.substring(i+2, i+3)) * 2 + Integer.parseInt(s.substring(i+3, i+4));
			if(sum > 9) {
				switch(sum) {
					case 10:
						buff.append("a");
						break;
					case 11:
						buff.append("b");
						break;
					case 12:
						buff.append("c");
						break;
					case 13:
						buff.append("d");
						break;
					case 14:
						buff.append("e");
						break;
					case 15:
						buff.append("f");
						break;
				}
			} else {
				buff.append(sum);
			}
		}
		return buff.toString();
	}
	
	private String StringToHex(String s) {
		return BinaryToHex(StringToBinary(s));
	}
	
	private BigInteger HexToDecimal(String s) {
		return new BigInteger(s, 16);
	}
	
	private BigInteger BinaryToDecimal(String s) {
		return new BigInteger(s, 2);
	}
	
	private String DecimalToHex(BigInteger bi) {
		String ret = bi.toString(16);
		if(ret.length()%2 != 0) {
			StringBuffer buff = new StringBuffer();
			for(int i=0; i<2 - ret.length() % 2; ++i) {
				buff.append("0");
			}
			buff.append(ret);
			return buff.toString();
		} else {
			return ret;
		}
	}
	
	private String DecimalToBinary(BigInteger bi) {
		String ret = bi.toString(2);
		if(ret.length()%8 != 0) {
			StringBuffer buff = new StringBuffer();
			for(int i=0; i<8 - ret.length() % 8; ++i) {
				buff.append("0");
			}
			buff.append(ret);
			return buff.toString();
		} else {
			return ret;
		}
	}
	
	private String DecimalToString(BigInteger bi) {
		String binary = DecimalToBinary(bi);
		StringBuffer buff = new StringBuffer();
		for(int i=0; i<binary.length(); i = i+8) {
			char c = (char) (Integer.parseInt(binary.substring(i, i+1)) * 128 + Integer.parseInt(binary.substring(i+1, i+2)) * 64 + Integer.parseInt(binary.substring(i+2, i+3)) * 32 + Integer.parseInt(binary.substring(i+3, i+4)) * 16 + Integer.parseInt(binary.substring(i+4, i+5)) * 8 + Integer.parseInt(binary.substring(i+5, i+6)) * 4 + Integer.parseInt(binary.substring(i+6, i+7)) * 2 + Integer.parseInt(binary.substring(i+7, i+8)));
			buff.append(c);
		}
		return buff.toString();
	}

}
