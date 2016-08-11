package com.near.demo;

import com.near.IAction;

public class CustomAction implements IAction {

	private String words;

	public CustomAction(String words) {
		this.words = words;
	}

	@Override
	public void execute() {
		System.out.println(words);
	}

}
