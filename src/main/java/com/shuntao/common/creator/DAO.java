package com.shuntao.common.creator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.shuntao.common.OptionBean;

/**
 * 开发调试，映射管理，DAO
 * @author AllenZhang
 * @version 0.1 (2009.11.21)
 * @modify AllenZhang (2009.11.21)
 */
public class DAO {
	
	private DatabaseMetaData dmd = null;
	
	public Connection getConnection() {
		Connection conn = null;
		try {   
            Class.forName("oracle.jdbc.OracleDriver");   
            conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.11:1521:yuanben", "yuanben", "yuanben");   
        } catch (Exception e) {   
            e.printStackTrace();   
        }
        return conn;
	}
	
	public synchronized void getMetaData() {
		try {
			if (dmd == null) {
				 dmd = this.getConnection().getMetaData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void closeConnection() {
		try {
			dmd = null;
			this.getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前用户数据表（选择数据表）
	 * @return
	 * @throws Exception
	 * @author AllenZhang
	 */
	public synchronized ArrayList<String> findCurrentUserTableMetaData() throws Exception {
		ArrayList<String> result = new ArrayList<String>();
		String[] types = {"TABLE"};
		String userName = null;
		try {
			this.getMetaData();
			if (dmd != null) {
				if ("Oracle".equalsIgnoreCase(dmd.getDatabaseProductName())) {
					userName = dmd.getUserName();
				}
				ResultSet rs = dmd.getTables(null, userName, "%", types);
				if (rs != null) {
					while (rs.next()) {
						String tableName = rs.getString("TABLE_NAME");
						if (tableName != null && !tableName.toUpperCase().startsWith("BIN")) {
							result.add(tableName); 
						}
					}
					rs.close();
					rs = null;
				}
			}
			result.trimToSize();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		finally {
			this.closeConnection();
		}
	}	
	
	public synchronized ColumnBean findMetaDataByTableName(String tableName, String... strings) throws Exception {
		ColumnBean columnBean = new ColumnBean();
		ArrayList<OptionBean> resultDataList = new ArrayList<OptionBean>();
		columnBean.setColumns(resultDataList);
		OptionBean primaryKey = new OptionBean();
		columnBean.setPrimaryKey(primaryKey);
		if (dmd == null) {
			this.getMetaData();
		}
		if (dmd != null) {
			String userName = null;
			if ("Oracle".equalsIgnoreCase(dmd.getDatabaseProductName())) {
				userName = dmd.getUserName();
			}
			ResultSet rs = null;
			try {
				rs = dmd.getPrimaryKeys(null, userName, tableName);
				if (rs != null) {
					while (rs.next()) {
						String columnName = rs.getString("COLUMN_NAME");
						if (columnName != null && !"".equals(columnName.trim())) {
							primaryKey.setListKey(handleColumnName(columnName));
							primaryKey.setListValue("String");
							break;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				rs.close();
				rs = null;
			}

			try {
				rs = dmd.getColumns(null, userName, tableName, null);
				if (rs != null) {
					while (rs.next()) {
						String columnName = rs.getString("COLUMN_NAME");
						String columnType = rs.getString("TYPE_NAME");
						if (columnName != null && !"".equals(columnName.trim()) && columnType != null && !"".equals(columnType.trim())) {
							if (primaryKey.getListKey() != null && primaryKey.getListKey().equalsIgnoreCase(columnName)) {
								if (strings != null && strings.length > 0) {
									primaryKey.setListValue(columnType);
								}
								else {
									primaryKey.setListValue(dbTypeToJavaType(columnType));
								}
							}
							if (strings != null && strings.length > 0) {
								resultDataList.add(new OptionBean(handleColumnName(columnName), columnType));
							}
							else {
								resultDataList.add(new OptionBean(handleColumnName(columnName), dbTypeToJavaType(columnType)));
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				rs.close();
				rs = null;
			}
		}
		return columnBean;
	}
	
	public String handleTableName(String tableName) throws Exception {
		String tableNameKey = tableName;
		String[] tableNameArray = tableName.split("_");
		if (tableNameArray != null && tableNameArray.length > 0) {
			tableNameKey = "";
			for (int i = 0 ; i < tableNameArray.length ; i ++) {
				String tempStr = tableNameArray[i];
				tableNameKey += tempStr.substring(0,1).toUpperCase() + tempStr.substring(1).toLowerCase();
			}
		}
		return tableNameKey;
	}

	public String handleColumnName(String columnName) throws Exception {
		//return columnName.substring(0, 3).toLowerCase() + columnName.substring(3);
		
		/*if (columnName != null && !"".equals(columnName.trim())) {
			columnName = columnName.trim();
			return columnName.substring(0, 1).toLowerCase() + columnName.substring(1);
		} else {
			return columnName;
		}*/
		
		//如果是oracle数据库，字段名称统一使用小写
		return columnName.toLowerCase();
	}
	
	public String dbTypeToJavaType (String dbType) throws Exception {
		//System.out.println(dbType);
		if (dbType == null) {
			dbType = "";
		}
		String javaType = "String";
		if ("uniqueidentifier".equalsIgnoreCase(dbType)) {
			javaType = "String";
		}
		else if ("varchar".equalsIgnoreCase(dbType)) {
			javaType = "String";
		}
		else if ("varchar2".equalsIgnoreCase(dbType)) {
			javaType = "String";
		}
		else if ("nvarchar".equalsIgnoreCase(dbType)) {
			javaType = "String";
		}
		else if ("nvarchar2".equalsIgnoreCase(dbType)) {
			javaType = "String";
		}
		else if ("char".equalsIgnoreCase(dbType)) {
			javaType = "String";
		}
		else if ("datetime".equalsIgnoreCase(dbType)) {
			javaType = "java.util.Date";
		}
		else if ("TIMESTAMP".equalsIgnoreCase(dbType)) {
			javaType = "java.util.Date";
		}
		else if ("DATE".equalsIgnoreCase(dbType)) {
			javaType = "java.util.Date";
		}
		else if ("int".equalsIgnoreCase(dbType)) {
			javaType = "Integer";
		}
		else if ("integer".equalsIgnoreCase(dbType)) {
			javaType = "Integer";
		}
		else if ("numeric".equalsIgnoreCase(dbType)) {
			javaType = "Double";
		}
		else if ("NUMBER".equalsIgnoreCase(dbType)) {
			javaType = "Double";
		}
		else if ("float".equalsIgnoreCase(dbType)) {
			javaType = "Double";
		}
		else if ("tinyint".equalsIgnoreCase(dbType)) {
			javaType = "Integer";
		}
		else if ("bigint".equalsIgnoreCase(dbType)) {
			javaType = "Long";
		}
		else if ("long".equalsIgnoreCase(dbType)) {
			javaType = "Long";
		}
		else if ("decimal".equalsIgnoreCase(dbType)) {
			javaType = "Double";
		}
		else {
			javaType = "String";
		}
		return javaType;
	}
}