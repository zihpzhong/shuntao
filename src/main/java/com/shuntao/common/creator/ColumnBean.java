package com.shuntao.common.creator;

import java.util.ArrayList;

import com.shuntao.common.OptionBean;

/**
 * 开发调试，映射管理，字段Bean
 * @author AllenZhang
 * @version 0.1 (2009.11.21)
 * @modify AllenZhang (2009.11.21)
 */
public class ColumnBean {

	private OptionBean primaryKey;
	
	private ArrayList<OptionBean> columns;

	public OptionBean getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(OptionBean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public ArrayList<OptionBean> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<OptionBean> columns) {
		this.columns = columns;
	}
}
