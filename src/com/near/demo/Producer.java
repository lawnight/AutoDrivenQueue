package com.near.demo;

import com.near.AutoDrivenActionQueue;

public class Producer {

	// if all producer'a action need execute by order just add static
	private AutoDrivenActionQueue selfDrivenActionQueue = new AutoDrivenActionQueue();

	public void producer() {
		String words = "hello world!";
		CustomAction action = new CustomAction(words);
		selfDrivenActionQueue.add(action);
	}

}
