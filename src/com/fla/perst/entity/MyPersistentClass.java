package com.fla.perst.entity;

import org.garret.perst.Persistent;

public class MyPersistentClass extends Persistent {
	public int intKey; // integer key
	public String strKey; // string key
	public String body; // non-indexed field

	public String toString() {
		return intKey + ":" + strKey + ":" + body;
	}
}
