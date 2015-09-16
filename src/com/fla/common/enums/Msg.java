package com.fla.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum Msg {

	success("成功","0"),
	error("错误","-1"),
	failure("失败","--2");

	private String name;
	private String index;

	private Msg() {
	}
	


	private Msg(String name, String index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * 返回一个名称的数组.
	 * 
	 * @return
	 */
	public static String[] getValuesArray(){
		List <String> list = new ArrayList<String>();
		for (Msg c : Msg.values()) {
			list.add(c.name);
		}
		String [] str = new String[list.size()];
		return list.toArray(str);
	}
	
	
	
	public  static String getName(String index) {
		for (Msg c : Msg.values()) {
			if (c.getIndex().equals(index)) {
				return c.name;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
}