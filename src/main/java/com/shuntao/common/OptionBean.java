package com.shuntao.common;

import java.io.Serializable;


public class OptionBean implements Serializable {
	
	private static final long serialVersionUID = -7381403261462602850L;

	/**
	 * 代码
	 */
	public String listKey;
	
	/**
	 * 显示文字
	 */
	public String listValue;
	
	public OptionBean() {
	};
	
	public OptionBean(String listKey, String listValue) {
		this.listKey = listKey;
		this.listValue = listValue;
	}
	
	public String getListKey() {
		return listKey;
	}

	public void setListKey(String listKey) {
		this.listKey = listKey;
	}

	public String getListValue() {
		return listValue;
	}

	public void setListValue(String listValue) {
		this.listValue = listValue;
	}

}