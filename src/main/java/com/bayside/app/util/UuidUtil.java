package com.bayside.app.util;

import java.util.UUID;

public class UuidUtil {
	/**
	 * 获取32为的id
	 * @return
	 */
	public static String getUUID(){
		  UUID uuid = UUID.randomUUID();
		  return uuid.toString().replace("-", "");
	}
	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			System.out.println(UuidUtil.getUUID());
		}
		
	}
}
