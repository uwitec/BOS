package cn.guwei.bos.utils;

import java.util.Random;

public class RandStringUtil {

	public static String getCode(){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<4; i++){
			sb.append(new Random().nextInt(10));
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		System.out.println(getCode());
	}
}
