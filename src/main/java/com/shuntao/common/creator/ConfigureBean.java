package com.shuntao.common.creator;

import java.io.Serializable;
public class ConfigureBean implements Serializable {

	private static final long serialVersionUID = 5386603942230097093L;
	
	public String tableName;
	
	public String selectSql;

	public boolean hasSql;
	
	public ConfigureBean() {
	};
	
	public ConfigureBean(String tableName, String selectSql, boolean hasSql) {
		this.tableName = tableName;
		this.selectSql = selectSql;
		this.hasSql = hasSql;
	}

	public String getSelectSql() {
		return selectSql;
	}

	public void setSelectSql(String selectSql) {
		this.selectSql = selectSql;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isHasSql() {
		return hasSql;
	}
	
	public void setHasSql(boolean hasSql) {
		this.hasSql = hasSql;
	}
	
}