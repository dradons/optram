package com.jetsen.pack.optram.netty;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteOper {
	public static final String characterEncoding = "gbk";

	/**
	 * int转换为长度为4的字节数组(小端在前)
	 * @param i
	 * @return
	 */
	public static byte[] intToByte4(int i) {
		byte[] targets = new byte[4];
		targets[0] = (byte) (i & 0xFF);
		targets[1] = (byte) (i >> 8 & 0xFF);
		targets[2] = (byte) (i >> 16 & 0xFF);
		targets[3] = (byte) (i >> 24 & 0xFF);
		return targets;
	}
	/**
	 * int转换为长度为4的字节数组(小端在前)
	 * @param i
	 * @return
	 */
	public static byte[] int2ByteArray(int i){
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array();
	}

	/**
	 * 字节数组转为int(小端在前)
	 * @param array
	 * @return
	 */
	public static int byteArray2Int(byte[] array){
		return ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}

	/**
	 * 字节数组转为int(小端在前)
	 * @param bytes
	 * @param off
	 * @return
	 */
	public static int byte4ToInt(byte[] bytes, int off) {
		int b0 = bytes[off] & 0xFF;
		int b1 = bytes[off + 1] & 0xFF;
		int b2 = bytes[off + 2] & 0xFF;
		int b3 = bytes[off + 3] & 0xFF;
		return (b3 << 24) | (b2 << 16) | (b1 << 8) | b0;
	}

	/**
	 * 把字节数组转为某进制
	 * @param bytes 字节数组
	 * @param radix 进制
	 * @return 进制字符串
	 */
	public static String binary(byte[] bytes, int radix){
		return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
	}

	/**
	 * 字节数据合并
	 * @param byte_1
	 * @param byte_2
	 * @return
	 */
	public static byte[] bytesMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}

	/*public static void main(String[] args) {
		 byte[] ints = ByteOper.intToByte4(10);
		 byte[] by = new byte[4];
		 by[0] = -64;
		 by[1] = 0;
		 by[2] = 0;
		 by[3] = 0;
		System.out.println(ByteOper.byte4ToInt(by, 0)); ;

	}*/
}
