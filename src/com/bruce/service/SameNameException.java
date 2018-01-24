package com.bruce.service;

public class SameNameException extends Exception {
	public SameNameException() {
		super();
	}
	
	public SameNameException(String msg) {
		super(msg);
	}
}
