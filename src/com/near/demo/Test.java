package com.near.demo;

public class Test {

	public static void main(String[] args) {
		// all produce will auto be custom
		Producer producer = new Producer();
		for (int i = 0; i < 100; i++) {
			producer.producer();
		}
	}

}
