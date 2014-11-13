package com.shuntao.common.creator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.shuntao.common.DateTime;
import com.shuntao.common.OptionBean;


/**
 * 开发调试，映射管理，创建文件工具
 * @author AllenZhang
 * @version 0.1 (2009.11.21)
 * @modify AllenZhang (2009.11.21)
 */
public class CreateFileUtil {

	/**
	 * 创建文件
	 * @param selectedTableNames 文件名
	 * @param configure 
	 * @throws Exception
	 * @author AllenZhang
	 */
	public static void createFiles(ArrayList<String> selectedTableNameArray, Configure configure) throws Exception {
		if (selectedTableNameArray != null && selectedTableNameArray.size() > 0) {
			String[] result = new String[selectedTableNameArray.size()];
			for (int i = 0 ; i < selectedTableNameArray.size() ; i ++) {
				result[i] = selectedTableNameArray.get(i);
			}
			createFiles(result, configure);
		}
	}
		
	/**
	 * 创建文件
	 * @param selectedTableNames 文件名
	 * @param configure 
	 * @throws Exception
	 * @author AllenZhang
	 */
	public synchronized static void createFiles(String[] selectedTableNameArray, Configure configure) throws Exception {
		if (selectedTableNameArray != null && !"".equals(selectedTableNameArray.length > 0)) {
			if (selectedTableNameArray != null && selectedTableNameArray.length > 0) {
				/**
				 * 创建目录
				 */
				CreateFileUtil.createDirectories(configure);
				
				/**
				 * 配置文件路径 DAO 配置文件
				 */
				/*String daoConfigureFileName = "";
				if (configure.SPRINGDAOXMLFILEPATH.endsWith("/") || configure.SPRINGDAOXMLFILEPATH.endsWith("\\")) {
					daoConfigureFileName = configure.SPRINGDAOXMLFILEPATH+"spring_daos_"+DateTime.getCurrentDate_YYYYMMDDHHMMSSWithOutSeparator()+".xml";
				}
				else {
					daoConfigureFileName = configure.SPRINGDAOXMLFILEPATH+"/spring_daos_"+DateTime.getCurrentDate_YYYYMMDDHHMMSSWithOutSeparator()+".xml";
				}
				OutputStreamWriter daoConfigureFileWriter = new OutputStreamWriter(new FileOutputStream(daoConfigureFileName), "UTF-8");*/
				
				/**
				 * 配置文件路径 Service 配置文件
				 */
				/*String serviceConfigureFileName = "";
				if (configure.SPRINGSERVICEXMLFILEPATH.endsWith("/") || configure.SPRINGSERVICEXMLFILEPATH.endsWith("\\")) {
					serviceConfigureFileName = configure.SPRINGSERVICEXMLFILEPATH+"spring_service_"+DateTime.getCurrentDate_YYYYMMDDHHMMSSWithOutSeparator()+".xml";
				}
				else {
					serviceConfigureFileName = configure.SPRINGSERVICEXMLFILEPATH+"/spring_service_"+DateTime.getCurrentDate_YYYYMMDDHHMMSSWithOutSeparator()+".xml";
				}
				OutputStreamWriter serviceConfigureFileWriter = new OutputStreamWriter(new FileOutputStream(serviceConfigureFileName), "UTF-8");*/				
				
				try {
					configure.getDao().getMetaData();
					for (int i = 0 ; i < selectedTableNameArray.length ; i ++) {
						/**
						 * 数据表名称
						 */
						String tableName = selectedTableNameArray[i];
						if (tableName != null && !"".equals(tableName.trim())) {
							tableName = tableName.trim();
							String originalTableName = tableName;
							tableName = configure.getDao().handleTableName(tableName);
							if (configure.TABLENAMEPREFIX != null &&
								!"".equals(configure.TABLENAMEPREFIX.trim())) {
								if (tableName.startsWith(configure.TABLENAMEPREFIX) &&
									tableName.length() > configure.TABLENAMEPREFIX.length()) {
									tableName = tableName.substring(configure.TABLENAMEPREFIX.length());
								}
							}
							
							/**
							 * 生成DAO接口文件
							 */
							if (configure.DAOFILEDESTPATH != null &&
								!"".equals(configure.DAOFILEDESTPATH.trim())) {
								/**
								 * 生成DAO接口文件名
								 */
								String daoFileTableName = "";
								if (configure.DAOFILEDESTPATH.endsWith("/") || configure.DAOFILEDESTPATH.endsWith("\\")) {
									daoFileTableName = configure.DAOFILEDESTPATH + tableName + "DAO.java" ;
								}
								else {
									daoFileTableName = configure.DAOFILEDESTPATH + "/" + tableName + "DAO.java" ;
								}
								if (daoFileTableName != null && !"".equals(daoFileTableName.trim())) {
									CreateFileUtil.deleteFile(daoFileTableName);
									OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(daoFileTableName), "UTF-8");									
									try {
										/**
										 * 写DAO文件内容
										 */
										CreateFileUtil.write(fileWriter, "package "+configure.getDaoPackagePath()+";");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "import java.util.ArrayList;");
										/******************* add by caojian *******************/
										//CreateFileUtil.write(fileWriter, "import java.util.Map;");
										//CreateFileUtil.write(fileWriter, "import com.shuntao.common.easygrid.EasyGridRequest;");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "import com.shuntao.common.db.dao.ToolTemplate;");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "import "+configure.getDtoPackagePath() + "." + tableName + "Dto;");
										CreateFileUtil.write(fileWriter, "import "+configure.getObjectPackagePath() + "." + tableName + "PO;");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "/**");
										CreateFileUtil.write(fileWriter, " * 数据访问接口："+tableName+"DAO，请将访问数据库的直接方法写到此接口实现类里。");
										CreateFileUtil.write(fileWriter, " * ");
										CreateFileUtil.write(fileWriter, " * 温馨提示：");
										CreateFileUtil.write(fileWriter, " * 1、如果您准备访问数据库，请使用：this.getDbTemplate()...");
										CreateFileUtil.write(fileWriter, " *     A、DbTemplate里的void saveData(...)方法让您无需写SQL语句即能把数据存入到数据库，并包含批量处理！");
										CreateFileUtil.write(fileWriter, " *     B、DbTemplate里的void saveDataAndReturnKey(...)方法让您无需写SQL语句即能把数据存入到数据库，并返回数据库自动产生的键值！");
										CreateFileUtil.write(fileWriter, " *     C、DbTemplate里的findForXXX(...)方法让您随心所欲的查询您需要的内容！");
										CreateFileUtil.write(fileWriter, " *     D、DbTemplate里的void updateData(...)方法让您对象化方式修改数据库的内容！");
										CreateFileUtil.write(fileWriter, " *     E、DbTemplate里的int updateWithParams(...)方法让您随心所欲的修改数据库的内容！");
										CreateFileUtil.write(fileWriter, " * 2、如果您准备使用某工具，请使用：this.getTT()...");
										CreateFileUtil.write(fileWriter, " *     A、ToolTemplate里的dt工具提供了丰富的日期处理方法！");
										CreateFileUtil.write(fileWriter, " *     B、ToolTemplate里的int loadPropertiesToBeanFormMap(...)方法能方便的将Map里的值拷贝到POJO对象中！");
										CreateFileUtil.write(fileWriter, " *     C、ToolTemplate里的getXXXFromMap(Map<String, Object> map, String key)方法能方便的从Map里获取指定键的值 ！");
										CreateFileUtil.write(fileWriter, " * 3、如果DbTemplate里所封装的功能不能达到您要求，请使用this.getJdbc()...");
										CreateFileUtil.write(fileWriter, " * ");
										CreateFileUtil.write(fileWriter, " * 创建日期："+DateTime.getCurrentDate_YYYYMMDDHHMM_CN());
										CreateFileUtil.write(fileWriter, " * @author ShunTaoFrameWork");
										CreateFileUtil.write(fileWriter, " */");
										CreateFileUtil.write(fileWriter, "public interface "+tableName+"DAO {");
										CreateFileUtil.write(fileWriter, "");
										
										ColumnBean columnBean = configure.getDao().findMetaDataByTableName(originalTableName);
										OptionBean optionBean = columnBean.getPrimaryKey();
										if (optionBean != null && optionBean.getListKey() != null && optionBean.getListValue() != null ) {
											String selectField = "";
											ArrayList<OptionBean> resultDataList = columnBean.getColumns();
											if (resultDataList != null && !resultDataList.isEmpty()) {
												for (OptionBean bean : resultDataList) {
													if (!"byte[]".equalsIgnoreCase(bean.getListValue()) && !"long".equalsIgnoreCase(bean.getListValue())) {
														selectField += " tableAlias." + bean.getListKey() + ", "; 
													}
												}
											}
											selectField = selectField.trim();
											if ("".equals(selectField)) {
												selectField = "tableAlias.*";
											}
											else {
												if (selectField.endsWith(",")) {
													selectField = selectField.substring(0, selectField.length() - 1);
												}
											}

											/**
											 * 生成工具访问模板
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 取得 工具访问模板");
											CreateFileUtil.write(fileWriter, "    * @return 工具访问模板");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    ToolTemplate getTT();");
											CreateFileUtil.write(fileWriter, "");
											
											/**
											 * 根据主键查询对应数据库内容信息
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 根据主键查询对应数据库内容信息");
											CreateFileUtil.write(fileWriter, "    * @param "+optionBean.getListKey()+" 主键值");
											CreateFileUtil.write(fileWriter, "    * @throws Exception");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    "+tableName+"PO findByPrimaryKey("+optionBean.getListValue()+" "+optionBean.getListKey()+") throws Exception;");
											CreateFileUtil.write(fileWriter, "");

											
											/**
											 * 将值对象保存到数据库，并返回主键字段对应的值
											 * 如果主键字段不为空（null），则将Bean里的主键字段写入到数据库
											 * 如果主键字段为空（null），则主键由数据库生成，并将生成的主键返回
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 将值对象保存到数据库，并返回主键字段对应的值");
											CreateFileUtil.write(fileWriter, "    * 如果Bean里的主键字段值不为空（null），则将Bean里的主键字段写入到数据库");
											CreateFileUtil.write(fileWriter, "    * 如果Bean里的主键字段值为空（null），则主键由数据库生成，并将生成的主键返回");
											CreateFileUtil.write(fileWriter, "    * @param obj 值对象");
											CreateFileUtil.write(fileWriter, "    * @throws Exception");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    String save("+tableName+"PO obj) throws Exception;");
											CreateFileUtil.write(fileWriter, "");
											
											/**
											 * 将值对象保存到数据库（批量保存）
											 * 如果主键字段不为空（null），则将Bean里的主键字段写入到数据库
											 * 如果主键字段为空（null），则主键由数据库生成
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 将值对象保存到数据库（批量保存）");
											CreateFileUtil.write(fileWriter, "    * 如果Bean里的主键字段值不为空（null），则将Bean里的主键字段写入到数据库");
											CreateFileUtil.write(fileWriter, "    * 如果Bean里的主键字段值为空（null），则主键由数据库生成");
											CreateFileUtil.write(fileWriter, "    * @param objs 值对象组");
											CreateFileUtil.write(fileWriter, "    * @throws Exception");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    void save(ArrayList<"+tableName+"PO> objs) throws Exception;");
											CreateFileUtil.write(fileWriter, "");

											/**
											 * 更新数据库内容，由conditionFieldNames指定条件
											 * conditionFieldNames可以0个或N个，但如果有那它们应都是Bean里的属性名称
											 * 这些名称将提供SQL语句中的where的字段的条件字段，其值由Bean提供
											 * 特别提示：自动生成的SQL语句的where条件只支持等于符号，即: = 符号
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 更新数据库内容，由conditionFieldNames指定条件");
											CreateFileUtil.write(fileWriter, "    * conditionFieldNames可以0个或N个，但如果有那它们应都是Bean里的属性名称");
											CreateFileUtil.write(fileWriter, "    * 这些名称将提供SQL语句中的where的字段的条件字段，其值由Bean提供");
											CreateFileUtil.write(fileWriter, "    * 特别提示：自动生成的SQL语句的where条件只支持等于符号，即: = 符号");
											CreateFileUtil.write(fileWriter, "    * @param obj 值对象");
											CreateFileUtil.write(fileWriter, "    * @param conditionFieldNames 指定条件");
											CreateFileUtil.write(fileWriter, "    * @throws Exception");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    void update("+tableName+"PO obj, String... conditionFieldNames) throws Exception;");
											CreateFileUtil.write(fileWriter, "");

											/**
											 * 根据主键删除数据库内容
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 根据主键删除数据库内容");
											CreateFileUtil.write(fileWriter, "    * @param "+optionBean.getListKey()+" 主键值");
											CreateFileUtil.write(fileWriter, "    * @throws Exception");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    void deleteByPrimaryKey("+optionBean.getListValue()+" "+optionBean.getListKey()+") throws Exception;");
											CreateFileUtil.write(fileWriter, "");

											/**
											 * 根据主键（s）删除数据库内容
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 根据主键（s）删除数据库内容");
											CreateFileUtil.write(fileWriter, "    * @param "+optionBean.getListKey()+" 主键值");
											CreateFileUtil.write(fileWriter, "    * @throws Exception");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    void deleteByPrimaryKeys("+optionBean.getListValue()+" "+optionBean.getListKey()+"s) throws Exception;");
											CreateFileUtil.write(fileWriter, "");
											
											/******************* add by caojian *******************/
											/**
											 * 分页查询
											 */
											//CreateFileUtil.write(fileWriter, "    /**");
											//CreateFileUtil.write(fileWriter, "    * 分页查询数据");
											//CreateFileUtil.write(fileWriter, "    * @param EasyGridRequest request");
											//CreateFileUtil.write(fileWriter, "    * @throws Exception");
											//CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											//CreateFileUtil.write(fileWriter, "    */");
											//CreateFileUtil.write(fileWriter, "    Map<String, Object> queryForPage(EasyGridRequest request) throws Exception;");
											//CreateFileUtil.write(fileWriter, "");*/
											
											/**
											 * 根据PO对象生成Dto对象
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "     * 根据PO对象生成Dto对象");
											CreateFileUtil.write(fileWriter, "     * @param "+tableName+"PO PO对象");
											CreateFileUtil.write(fileWriter, "     * @return Dto 对象");
											CreateFileUtil.write(fileWriter, "     * @throws Exception");
											CreateFileUtil.write(fileWriter, "     * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "     */");
											CreateFileUtil.write(fileWriter, "    "+tableName+"Dto poToDto("+tableName+"PO po) throws Exception;");
											CreateFileUtil.write(fileWriter, "");
										
											/**
											 * 根据Dto对象生成PO对象
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "     * 根据Dto对象生成PO对象");
											CreateFileUtil.write(fileWriter, "     * @param "+tableName+"Dto Dto对象");
											CreateFileUtil.write(fileWriter, "     * @return PO 对象");
											CreateFileUtil.write(fileWriter, "     * @throws Exception");
											CreateFileUtil.write(fileWriter, "     * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "     */");
											CreateFileUtil.write(fileWriter, "    "+tableName+"PO dtoToPo("+tableName+"Dto dto) throws Exception;");
											CreateFileUtil.write(fileWriter, "");
										}

										CreateFileUtil.write(fileWriter, "}");
										
									} 
									catch (Exception e) {
										e.printStackTrace();
									}
									finally {
										fileWriter.close();
									}
								}
							}
							
							/**
							 * 生成DAO文件
							 */
							if (configure.DAOIMPLFILEDESTPATH != null &&
								!"".equals(configure.DAOIMPLFILEDESTPATH.trim())) {
								/**
								 * 生成DAO文件名
								 */
								String daoFileTableName = "";
								if (configure.DAOIMPLFILEDESTPATH.endsWith("/") || configure.DAOIMPLFILEDESTPATH.endsWith("\\")) {
									daoFileTableName = configure.DAOIMPLFILEDESTPATH + tableName + "DAOImpl.java" ;
								}
								else {
									daoFileTableName = configure.DAOIMPLFILEDESTPATH + "/" + tableName + "DAOImpl.java" ;
								}
								if (daoFileTableName != null && !"".equals(daoFileTableName.trim())) {
									CreateFileUtil.deleteFile(daoFileTableName);
									OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(daoFileTableName), "UTF-8");	
									String tableNameSmall  = tableName.substring(0,1).toLowerCase() + tableName.substring(1);
									try {
										/**
										 * 写DAOImpl文件内容
										 */
										CreateFileUtil.write(fileWriter, "package "+configure.getDaoImplPackagePath()+";");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "import java.util.Map;");
										CreateFileUtil.write(fileWriter, "import java.util.List;");
										CreateFileUtil.write(fileWriter, "import java.util.ArrayList;");
										/******************* add by caojian *******************/
										//CreateFileUtil.write(fileWriter, "import com.shuntao.common.easygrid.EasyGridRequest;");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "import com.shuntao.common.db.dao.ToolTemplate;");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "import org.springframework.stereotype.Repository;");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "import javax.annotation.Resource;");
										CreateFileUtil.write(fileWriter, "import javax.sql.DataSource;");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "import "+configure.getDaoPackagePath() + "." +tableName + "DAO;");
										CreateFileUtil.write(fileWriter, "import "+configure.getBaseDao() + ";");
										CreateFileUtil.write(fileWriter, "import "+configure.getDtoPackagePath() + "." + tableName + "Dto;");
										CreateFileUtil.write(fileWriter, "import "+configure.getObjectPackagePath() + "." + tableName + "PO;");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "/**");
										CreateFileUtil.write(fileWriter, " * 数据访问对象："+tableName+"DAOImpl，请将访问数据库的直接方法写到此对象里。");
										CreateFileUtil.write(fileWriter, " * ");
										CreateFileUtil.write(fileWriter, " * 温馨提示：");
										CreateFileUtil.write(fileWriter, " * 1、如果您准备访问数据库，请使用：this.getDbTemplate()...");
										CreateFileUtil.write(fileWriter, " *     A、DbTemplate里的void saveData(...)方法让您无需写SQL语句即能把数据存入到数据库，并包含批量处理！");
										CreateFileUtil.write(fileWriter, " *     B、DbTemplate里的void saveDataAndReturnKey(...)方法让您无需写SQL语句即能把数据存入到数据库，并返回数据库自动产生的键值！");
										CreateFileUtil.write(fileWriter, " *     C、DbTemplate里的findForXXX(...)方法让您随心所欲的查询您需要的内容！");
										CreateFileUtil.write(fileWriter, " *     D、DbTemplate里的void updateData(...)方法让您对象化方式修改数据库的内容！");
										CreateFileUtil.write(fileWriter, " *     E、DbTemplate里的int updateWithParams(...)方法让您随心所欲的修改数据库的内容！");
										CreateFileUtil.write(fileWriter, " * 2、如果您准备使用某工具，请使用：this.getTT()...");
										CreateFileUtil.write(fileWriter, " *     A、ToolTemplate里的dt工具提供了丰富的日期处理方法！");
										CreateFileUtil.write(fileWriter, " *     B、ToolTemplate里的int loadPropertiesToBeanFormMap(...)方法能方便的将Map里的值拷贝到POJO对象中！");
										CreateFileUtil.write(fileWriter, " *     C、ToolTemplate里的getXXXFromMap(Map<String, Object> map, String key)方法能方便的从Map里获取指定键的值 ！");
										CreateFileUtil.write(fileWriter, " * 3、如果DbTemplate里所封装的功能不能达到您要求，请使用this.getJdbc()...");
										CreateFileUtil.write(fileWriter, " * ");
										CreateFileUtil.write(fileWriter, " * 创建日期："+DateTime.getCurrentDate_YYYYMMDDHHMM_CN());
										CreateFileUtil.write(fileWriter, " * @author ShunTaoFrameWork");
										CreateFileUtil.write(fileWriter, " */");
										String extendsClass= configure.getBaseDao().substring(configure.getBaseDao().lastIndexOf(".")+1);
										//add by caojian 增加spring注解
										CreateFileUtil.write(fileWriter, "@Repository(\""+tableNameSmall+"DAO\")");
										CreateFileUtil.write(fileWriter, "public class "+tableName+"DAOImpl extends "+extendsClass+" implements "+tableName+"DAO {");
										CreateFileUtil.write(fileWriter, "");
										
										CreateFileUtil.write(fileWriter, "    private final String realTableNameForInsertActor = \""+originalTableName+"\";");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "    /**");
										CreateFileUtil.write(fileWriter, "    * 获取当前操作的实际表名");
										CreateFileUtil.write(fileWriter, "    * @return 当前操作的实际表名");
										CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
										CreateFileUtil.write(fileWriter, "    */");
										CreateFileUtil.write(fileWriter, "    public String getRealTableNameForInsertActor() {");
										CreateFileUtil.write(fileWriter, "        return this.realTableNameForInsertActor;");
										CreateFileUtil.write(fileWriter, "    }");
										
										CreateFileUtil.write(fileWriter, "");
										
										/**
										 * 设置数据源DataSource
										 */
										CreateFileUtil.write(fileWriter, "    /**");
										CreateFileUtil.write(fileWriter, "    * 为模板设置数据库源");
										CreateFileUtil.write(fileWriter, "    * @param dataSource");
										CreateFileUtil.write(fileWriter, "    */");
										CreateFileUtil.write(fileWriter, "    @Resource(name=\"dataSource\")");
										CreateFileUtil.write(fileWriter, "    public void setDataSource(DataSource dataSource) {");
										CreateFileUtil.write(fileWriter, "        super.setDataSource(dataSource);");
										CreateFileUtil.write(fileWriter, "    }");
										
										CreateFileUtil.write(fileWriter, "");
										
										ColumnBean columnBean = configure.getDao().findMetaDataByTableName(originalTableName);
										OptionBean optionBean = columnBean.getPrimaryKey();
										if (optionBean != null && optionBean.getListKey() != null && optionBean.getListValue() != null ) {
											String selectField = "";
											ArrayList<OptionBean> resultDataList = columnBean.getColumns();
											if (resultDataList != null && !resultDataList.isEmpty()) {
												for (OptionBean bean : resultDataList) {
													if (!"byte[]".equalsIgnoreCase(bean.getListValue()) && !"long".equalsIgnoreCase(bean.getListValue())) {
														selectField += " tableAlias." + bean.getListKey() + ", "; 
													}
												}
											}
											selectField = selectField.trim();
											if ("".equals(selectField)) {
												selectField = "tableAlias.*";
											}
											else {
												if (selectField.endsWith(",")) {
													selectField = selectField.substring(0, selectField.length() - 1);
												}
											}
											
											/**
											 * 生成工具访问模板
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 取得 工具访问模板");
											CreateFileUtil.write(fileWriter, "    * @return 工具访问模板");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    public ToolTemplate getTT() {");
											CreateFileUtil.write(fileWriter, "        return super.getTT();");											
											CreateFileUtil.write(fileWriter, "    }");											
											CreateFileUtil.write(fileWriter, "");											
											
											/**
											 * 根据主键查询对应数据库内容信息
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 根据主键查询对应数据库内容信息");
											CreateFileUtil.write(fileWriter, "    * @param "+optionBean.getListKey()+" 主键值");
											CreateFileUtil.write(fileWriter, "    * @throws Exception");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    public "+tableName+"PO findByPrimaryKey("+optionBean.getListValue()+" "+optionBean.getListKey()+") throws Exception {");
											CreateFileUtil.write(fileWriter, "        String selectSql = \" select "+selectField+" from "+originalTableName+" tableAlias where tableAlias."+optionBean.getListKey()+" = ? \";");
											CreateFileUtil.write(fileWriter, "        Object[] params = {"+optionBean.getListKey()+"};");
											CreateFileUtil.write(fileWriter, "        List<Map<String, Object>> list = this.getDbTemplate().findForList(selectSql, params);");
											CreateFileUtil.write(fileWriter, "        if (list != null && !list.isEmpty()) {");
											CreateFileUtil.write(fileWriter, "            "+tableName+"PO obj = new "+tableName+"PO();");
										    CreateFileUtil.write(fileWriter, "            this.getTT().populateBeanFormMap(obj, list.get(0));");
											CreateFileUtil.write(fileWriter, "            return obj;");
											CreateFileUtil.write(fileWriter, "        }");
											CreateFileUtil.write(fileWriter, "        else {");
											CreateFileUtil.write(fileWriter, "            return null;");
											CreateFileUtil.write(fileWriter, "        }");
											CreateFileUtil.write(fileWriter, "    }");
											CreateFileUtil.write(fileWriter, "");

											
											/**
											 * 将值对象保存到数据库，并返回主键字段对应的值
											 * 如果主键字段不为空（null），则将Bean里的主键字段写入到数据库
											 * 如果主键字段为空（null），则主键由数据库生成，并将生成的主键返回
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 将值对象保存到数据库，并返回主键字段对应的值");
											CreateFileUtil.write(fileWriter, "    * 如果Bean里的主键字段值不为空（null），则将Bean里的主键字段写入到数据库");
											CreateFileUtil.write(fileWriter, "    * 如果Bean里的主键字段值为空（null），则主键由数据库生成，并将生成的主键返回");
											CreateFileUtil.write(fileWriter, "    * @param obj 值对象");
											CreateFileUtil.write(fileWriter, "    * @throws Exception");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    public String save("+tableName+"PO obj) throws Exception {");
											CreateFileUtil.write(fileWriter, "        Object primaryKeyValue = this.getTT().getProperty(obj, \""+optionBean.getListKey()+"\");");
											CreateFileUtil.write(fileWriter, "        if (primaryKeyValue != null) {");
											CreateFileUtil.write(fileWriter, "            this.getDbTemplate().saveData(obj);");
											CreateFileUtil.write(fileWriter, "            return String.valueOf(primaryKeyValue);");
											CreateFileUtil.write(fileWriter, "        }");
											CreateFileUtil.write(fileWriter, "        else {");
											CreateFileUtil.write(fileWriter, "            return this.getDbTemplate().saveData(obj, \""+optionBean.getListKey()+"\");");
											CreateFileUtil.write(fileWriter, "        }");
											CreateFileUtil.write(fileWriter, "    }");
											CreateFileUtil.write(fileWriter, "");
											
											/**
											 * 将值对象保存到数据库（批量保存）
											 * 如果主键字段不为空（null），则将Bean里的主键字段写入到数据库
											 * 如果主键字段为空（null），则主键由数据库生成
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 将值对象保存到数据库（批量保存）");
											CreateFileUtil.write(fileWriter, "    * 如果Bean里的主键字段值不为空（null），则将Bean里的主键字段写入到数据库");
											CreateFileUtil.write(fileWriter, "    * 如果Bean里的主键字段值为空（null），则主键由数据库生成");
											CreateFileUtil.write(fileWriter, "    * @param objs 值对象组");
											CreateFileUtil.write(fileWriter, "    * @throws Exception");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    public void save(ArrayList<"+tableName+"PO> objs) throws Exception {");
											CreateFileUtil.write(fileWriter, "        Object obj = objs.get(0);");
											CreateFileUtil.write(fileWriter, "        Object primaryKeyValue = this.getTT().getProperty(obj, \""+optionBean.getListKey()+"\");");
											CreateFileUtil.write(fileWriter, "        if (primaryKeyValue != null) {");
											CreateFileUtil.write(fileWriter, "            this.getDbTemplate().saveData(objs);");
											CreateFileUtil.write(fileWriter, "        }");
											CreateFileUtil.write(fileWriter, "        else {");
											CreateFileUtil.write(fileWriter, "            this.getDbTemplate().saveData(objs, \""+optionBean.getListKey()+"\");");
											CreateFileUtil.write(fileWriter, "        }");
											CreateFileUtil.write(fileWriter, "    }");
											CreateFileUtil.write(fileWriter, "");

											/**
											 * 更新数据库内容，由conditionFieldNames指定条件
											 * conditionFieldNames可以0个或N个，但如果有那它们应都是Bean里的属性名称
											 * 这些名称将提供SQL语句中的where的字段的条件字段，其值由Bean提供
											 * 特别提示：自动生成的SQL语句的where条件只支持等于符号，即: = 符号
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 更新数据库内容，由conditionFieldNames指定条件");
											CreateFileUtil.write(fileWriter, "    * conditionFieldNames可以0个或N个，但如果有那它们应都是Bean里的属性名称");
											CreateFileUtil.write(fileWriter, "    * 这些名称将提供SQL语句中的where的字段的条件字段，其值由Bean提供");
											CreateFileUtil.write(fileWriter, "    * 特别提示：自动生成的SQL语句的where条件只支持等于符号，即: = 符号");
											CreateFileUtil.write(fileWriter, "    * @param obj 值对象");
											CreateFileUtil.write(fileWriter, "    * @param conditionFieldNames 指定条件");
											CreateFileUtil.write(fileWriter, "    * @throws Exception");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    public void update("+tableName+"PO obj, String... conditionFieldNames) throws Exception {");
											CreateFileUtil.write(fileWriter, "        this.getDbTemplate().updateData(obj, conditionFieldNames);");
											CreateFileUtil.write(fileWriter, "    }");
											CreateFileUtil.write(fileWriter, "");

											/**
											 * 根据主键删除数据库内容
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 根据主键删除数据库内容");
											CreateFileUtil.write(fileWriter, "    * @param "+optionBean.getListKey()+" 主键值");
											CreateFileUtil.write(fileWriter, "    * @throws Exception");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    public void deleteByPrimaryKey("+optionBean.getListValue()+" "+optionBean.getListKey()+") throws Exception {");
											CreateFileUtil.write(fileWriter, "        String deleteSql = \" delete from "+originalTableName+" where "+optionBean.getListKey()+" = ? \";");
											CreateFileUtil.write(fileWriter, "        Object[] params = {"+optionBean.getListKey()+"};");
											CreateFileUtil.write(fileWriter, "        this.getDbTemplate().updateWithParams(deleteSql, params);");
											CreateFileUtil.write(fileWriter, "    }");
											CreateFileUtil.write(fileWriter, "");

											/**
											 * 根据主键（s）删除数据库内容
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "    * 根据主键（s）删除数据库内容");
											CreateFileUtil.write(fileWriter, "    * @param "+optionBean.getListKey()+" 主键值");
											CreateFileUtil.write(fileWriter, "    * @throws Exception");
											CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "    */");
											CreateFileUtil.write(fileWriter, "    public void deleteByPrimaryKeys("+optionBean.getListValue()+" "+optionBean.getListKey()+"s) throws Exception {");
											CreateFileUtil.write(fileWriter, "        String deleteSql = \" delete from "+originalTableName+" where "+optionBean.getListKey()+" in (\"+"+optionBean.getListKey()+"s+\") \";");
											CreateFileUtil.write(fileWriter, "        this.getDbTemplate().updateWithParams(deleteSql, null);");
											CreateFileUtil.write(fileWriter, "    }");
											CreateFileUtil.write(fileWriter, "");
											
											/******************* add by caojian *******************/
											/**
											 * 分页查询
											 */
											//CreateFileUtil.write(fileWriter, "    /**");
											//CreateFileUtil.write(fileWriter, "    * 分页查询数据");
											//CreateFileUtil.write(fileWriter, "    * @param EasyGridRequest request");
											//CreateFileUtil.write(fileWriter, "    * @throws Exception");
											//CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
											//CreateFileUtil.write(fileWriter, "    */");
											//CreateFileUtil.write(fileWriter, "    public Map<String, Object> queryForPage(EasyGridRequest request) throws Exception {");
											//CreateFileUtil.write(fileWriter, "        String keyWord = request.getParameters().get(\"keyWord\");");
											//CreateFileUtil.write(fileWriter, "        String sort = request.getParameters().get(\"sort\");");
											//CreateFileUtil.write(fileWriter, "        String order = request.getParameters().get(\"order\");");
											//CreateFileUtil.write(fileWriter, "        if (null == sort || \"\".equals(sort.trim())) {");
											//CreateFileUtil.write(fileWriter, "            sort = \""+optionBean.getListKey()+"\";");
											//CreateFileUtil.write(fileWriter, "        }");
											//CreateFileUtil.write(fileWriter, "        if (null == order || \"\".equals(order.trim())) {");
											//CreateFileUtil.write(fileWriter, "            order = \"desc\";");
											//CreateFileUtil.write(fileWriter, "        }");
											//CreateFileUtil.write(fileWriter, "        String sql = \"select * from "+originalTableName+" where "+optionBean.getListKey()+" like ? order by \"+sort+\" \"+order;");
											//CreateFileUtil.write(fileWriter, "        return this.findPageForSTGrid(sql, new Object[]{\"%\"+keyWord+\"%\"}, request.getPageObj());");
											//CreateFileUtil.write(fileWriter, "    }");
											//CreateFileUtil.write(fileWriter, "");
											
											/**
											 * 根据PO对象生成Dto对象
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "     * 根据PO对象生成Dto对象");
											CreateFileUtil.write(fileWriter, "     * @param "+tableName+"PO PO对象");
											CreateFileUtil.write(fileWriter, "     * @return Dto 对象");
											CreateFileUtil.write(fileWriter, "     * @throws Exception");
											CreateFileUtil.write(fileWriter, "     * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "     */");
											CreateFileUtil.write(fileWriter, "    public "+tableName+"Dto poToDto("+tableName+"PO po) throws Exception {");
											CreateFileUtil.write(fileWriter, "        "+tableName+"Dto dto = new "+tableName+"Dto();");
											CreateFileUtil.write(fileWriter, "        this.getTT().copyProperties(dto, po);");
											CreateFileUtil.write(fileWriter, "        return dto;");
											CreateFileUtil.write(fileWriter, "    }");
											CreateFileUtil.write(fileWriter, "");
										
											/**
											 * 根据Dto对象生成PO对象
											 */
											CreateFileUtil.write(fileWriter, "    /**");
											CreateFileUtil.write(fileWriter, "     * 根据Dto对象生成PO对象");
											CreateFileUtil.write(fileWriter, "     * @param "+tableName+"Dto Dto对象");
											CreateFileUtil.write(fileWriter, "     * @return PO 对象");
											CreateFileUtil.write(fileWriter, "     * @throws Exception");
											CreateFileUtil.write(fileWriter, "     * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, "     */");
											CreateFileUtil.write(fileWriter, "    public "+tableName+"PO dtoToPo("+tableName+"Dto dto) throws Exception {");
											CreateFileUtil.write(fileWriter, "        "+tableName+"PO po = new "+tableName+"PO();");
											CreateFileUtil.write(fileWriter, "        this.getTT().copyProperties(po, dto);");
											CreateFileUtil.write(fileWriter, "        return po;");
											CreateFileUtil.write(fileWriter, "    }");
											CreateFileUtil.write(fileWriter, "");
										}

										CreateFileUtil.write(fileWriter, "}");
										
										/**
										 * 生成DAO 配置文件
										 */
										//String tableNameSmall  = tableName.substring(0,1).toLowerCase() + tableName.substring(1);
										//CreateFileUtil.write(daoConfigureFileWriter, "    <bean id=\""+tableNameSmall+"DAO\"");
										//CreateFileUtil.write(daoConfigureFileWriter, "          class=\""+configure.getDaoImplPackagePath()+"."+tableName+"DAOImpl\">");
										//CreateFileUtil.write(daoConfigureFileWriter, "        <property name=\"dataSource\">");
										//CreateFileUtil.write(daoConfigureFileWriter, "            <ref bean=\"dataSource\" />");
										//CreateFileUtil.write(daoConfigureFileWriter, "    	</property>");
										//CreateFileUtil.write(daoConfigureFileWriter, "    </bean>");
										//CreateFileUtil.write(daoConfigureFileWriter, "");
									} 
									catch (Exception e) {
										e.printStackTrace();
									}
									finally {
										fileWriter.close();
									}
								}
							}

							/**
							 * 生成Service文件
							 */
							if (configure.SERVICEFILEDESTPATH != null &&
								!"".equals(configure.SERVICEFILEDESTPATH.trim())) {
								/**
								 * 生成Service文件名
								 */
								String serviceFileTableName = "";
								if (configure.SERVICEFILEDESTPATH.endsWith("/") || configure.SERVICEFILEDESTPATH.endsWith("\\")) {
									serviceFileTableName = configure.SERVICEFILEDESTPATH + tableName + "Service.java" ;
								}
								else {
									serviceFileTableName = configure.SERVICEFILEDESTPATH + "/" + tableName + "Service.java" ;
								}
								if (serviceFileTableName != null && !"".equals(serviceFileTableName.trim())) {
									CreateFileUtil.deleteFile(serviceFileTableName);
									OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(serviceFileTableName), "UTF-8");									
									try {
										/**
										 * 写Service文件内容
										 */
										CreateFileUtil.write(fileWriter, "package "+configure.getServicePackagePath()+";");
										CreateFileUtil.write(fileWriter, "");
										String extendsInterface = configure.getBaseService().substring(configure.getBaseService().lastIndexOf(".")+1);
										if (extendsInterface != null && !"".equals(extendsInterface.trim())) {
											CreateFileUtil.write(fileWriter, "import "+configure.getBaseService() + ";");
											/******************* add by caojian *******************/
											//CreateFileUtil.write(fileWriter, "import java.util.Map;");
											//CreateFileUtil.write(fileWriter, "import com.shuntao.common.easygrid.EasyGridRequest;");
											CreateFileUtil.write(fileWriter, "import "+configure.getDtoPackagePath() + "." + tableName + "Dto;");
											CreateFileUtil.write(fileWriter, "");
											CreateFileUtil.write(fileWriter, "/**");
											CreateFileUtil.write(fileWriter, " * 业务接口："+tableName+"Service，请在处理动作里直接调用业务接口，而不是它的实现类。");
											CreateFileUtil.write(fileWriter, " * 创建日期："+DateTime.getCurrentDate_YYYYMMDDHHMM_CN());
											CreateFileUtil.write(fileWriter, " * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, " */");
											CreateFileUtil.write(fileWriter, "public interface "+tableName+"Service extends "+extendsInterface+" {");
										}
										else {
											/******************* add by caojian *******************/
											//CreateFileUtil.write(fileWriter, "import java.util.Map;");											
											//CreateFileUtil.write(fileWriter, "import com.shuntao.common.easygrid.EasyGridRequest;");
											CreateFileUtil.write(fileWriter, "import "+configure.getDtoPackagePath() + "." + tableName + "Dto;");
											CreateFileUtil.write(fileWriter, "");
											CreateFileUtil.write(fileWriter, "/**");
											CreateFileUtil.write(fileWriter, " * 业务接口："+tableName+"Service，请在处理动作里直接调用业务接口，而不是它的实现类。");
											CreateFileUtil.write(fileWriter, " * 创建日期："+DateTime.getCurrentDate_YYYYMMDDHHMM_CN());
											CreateFileUtil.write(fileWriter, " * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, " */");
											CreateFileUtil.write(fileWriter, "public interface "+tableName+"Service {");
										}
										
										/******************* add by caojian *******************/
										ColumnBean columnBean = configure.getDao().findMetaDataByTableName(originalTableName);
										OptionBean optionBean = columnBean.getPrimaryKey();

										/**
										 * 分页查询
										 */
										//CreateFileUtil.write(fileWriter, "    /**");
										//CreateFileUtil.write(fileWriter, "    * 分页查询数据");
										//CreateFileUtil.write(fileWriter, "    * @param EasyGridRequest request");
										//CreateFileUtil.write(fileWriter, "    * @throws Exception");
										//CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
										//CreateFileUtil.write(fileWriter, "    */");
										//CreateFileUtil.write(fileWriter, "    Map<String, Object> queryForPage(EasyGridRequest request) throws Exception;");
										//CreateFileUtil.write(fileWriter, "");
										
										/**
										 * 保存对象
										 */
										CreateFileUtil.write(fileWriter, "    /**");
										CreateFileUtil.write(fileWriter, "    * 保存对象");
										CreateFileUtil.write(fileWriter, "    * @param "+tableName+"Dto Dto对象");
										CreateFileUtil.write(fileWriter, "    * @throws Exception");
										CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
										CreateFileUtil.write(fileWriter, "    */");
										CreateFileUtil.write(fileWriter, "    void saveData("+tableName+"Dto dto) throws Exception;");
										CreateFileUtil.write(fileWriter, "");

										/**
										 * 根据ID加载对象
										 */
										CreateFileUtil.write(fileWriter, "    /**");
										CreateFileUtil.write(fileWriter, "    * 根据ID加载对象");
										CreateFileUtil.write(fileWriter, "    * @param "+optionBean.getListKey());
										CreateFileUtil.write(fileWriter, "    * @throws Exception");
										CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
										CreateFileUtil.write(fileWriter, "    */");
										CreateFileUtil.write(fileWriter, "    "+tableName+"Dto loadDataById("+optionBean.getListValue()+" "+optionBean.getListKey()+") throws Exception;");
										CreateFileUtil.write(fileWriter, "");

										/**
										 * 根据ID编辑对象
										 */
										CreateFileUtil.write(fileWriter, "    /**");
										CreateFileUtil.write(fileWriter, "    * 根据ID编辑对象");
										CreateFileUtil.write(fileWriter, "    * @param "+tableName+"Dto Dto对象");
										CreateFileUtil.write(fileWriter, "    * @throws Exception");
										CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
										CreateFileUtil.write(fileWriter, "    */");
										CreateFileUtil.write(fileWriter, "    void editData("+tableName+"Dto dto) throws Exception;");
										CreateFileUtil.write(fileWriter, "");

										/**
										 * 根据ID删除数据
										 */
										CreateFileUtil.write(fileWriter, "    /**");
										CreateFileUtil.write(fileWriter, "    * 根据ID删除数据");
										CreateFileUtil.write(fileWriter, "    * @param "+optionBean.getListKey());
										CreateFileUtil.write(fileWriter, "    * @throws Exception");
										CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
										CreateFileUtil.write(fileWriter, "    */");
										CreateFileUtil.write(fileWriter, "    void deleteByPrimaryKeys("+optionBean.getListValue()+" "+optionBean.getListKey()+"s) throws Exception;");
										CreateFileUtil.write(fileWriter, "");
										
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "}");
									} 
									catch (Exception e) {
										e.printStackTrace();
									}
									finally {
										fileWriter.close();
									}
								}
							}						
							
							
							/**
							 * 生成ServiceImpl文件
							 */
							if (configure.SERVICEIMPLFILEDESTPATH != null &&
								!"".equals(configure.SERVICEIMPLFILEDESTPATH.trim())) {
								/**
								 * 生成ServiceImpl文件名
								 */
								String serviceImplFileTableName = "";
								if (configure.SERVICEIMPLFILEDESTPATH.endsWith("/") || configure.SERVICEIMPLFILEDESTPATH.endsWith("\\")) {
									serviceImplFileTableName = configure.SERVICEIMPLFILEDESTPATH + tableName + "ServiceImpl.java" ;
								}
								else {
									serviceImplFileTableName = configure.SERVICEIMPLFILEDESTPATH + "/" + tableName + "ServiceImpl.java" ;
								}
								if (serviceImplFileTableName != null && !"".equals(serviceImplFileTableName.trim())) {
									CreateFileUtil.deleteFile(serviceImplFileTableName);
									OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(serviceImplFileTableName), "UTF-8");
									String tableNameSmall  = tableName.substring(0,1).toLowerCase() + tableName.substring(1);
									try {
										/**
										 * 写ServiceImpl文件内容
										 */
										CreateFileUtil.write(fileWriter, "package "+configure.getServiceImplPackagePath()+";");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "import org.springframework.stereotype.Service;");
										CreateFileUtil.write(fileWriter, "import javax.annotation.Resource;");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "import "+configure.getServicePackagePath() +"."+ tableName+"Service ;");
										CreateFileUtil.write(fileWriter, "import "+configure.getDaoPackagePath() +"."+ tableName+"DAO ;");
										/******************* add by caojian *******************/
										//CreateFileUtil.write(fileWriter, "import java.util.Map;");											
										//CreateFileUtil.write(fileWriter, "import com.shuntao.common.easygrid.EasyGridRequest;");
										CreateFileUtil.write(fileWriter, "import "+configure.getDtoPackagePath() + "." + tableName + "Dto;");
										CreateFileUtil.write(fileWriter, "");
										String extendsClass = configure.getBaseServiceImpl().substring(configure.getBaseServiceImpl().lastIndexOf(".")+1);
										if (extendsClass != null && !"".equals(extendsClass.trim())) {
											if (configure.getBaseServiceImpl().indexOf(configure.getServiceImplPackagePath()) == -1) {
												CreateFileUtil.write(fileWriter, "import "+configure.getBaseServiceImpl()+ ";");
												CreateFileUtil.write(fileWriter, "");
											}
											CreateFileUtil.write(fileWriter, "/**");
											CreateFileUtil.write(fileWriter, " * 业务对象："+tableName+"ServiceImpl，此对象应实现对应业务接口"+tableName+"Service的所有方法。");
											CreateFileUtil.write(fileWriter, " * 创建日期："+DateTime.getCurrentDate_YYYYMMDDHHMM_CN());
											CreateFileUtil.write(fileWriter, " * @author ShunTaoFrameWork");
											CreateFileUtil.write(fileWriter, " */");
											//add by caojian 增加spring注解
											CreateFileUtil.write(fileWriter, "@Service(\""+tableNameSmall+"Service\")");
											CreateFileUtil.write(fileWriter, "public class "+tableName+"ServiceImpl extends "+extendsClass+" implements "+tableName+"Service {");
										}
										else {
											CreateFileUtil.write(fileWriter, "public class "+tableName+"ServiceImpl implements "+tableName+"Service {");
										}
										CreateFileUtil.write(fileWriter, "");
										
										CreateFileUtil.write(fileWriter, "    private "+tableName+"DAO dao;");
										CreateFileUtil.write(fileWriter, "");
										//add by caojian 增加spring注解
										CreateFileUtil.write(fileWriter, "    @Resource(name=\""+tableNameSmall+"DAO\")");
										CreateFileUtil.write(fileWriter, "    public void setDao("+tableName+"DAO dao) {");
										CreateFileUtil.write(fileWriter, "        this.dao = dao;");
										CreateFileUtil.write(fileWriter, "    }");
										CreateFileUtil.write(fileWriter, "");

										/******************* add by caojian *******************/
										ColumnBean columnBean = configure.getDao().findMetaDataByTableName(originalTableName);
										OptionBean optionBean = columnBean.getPrimaryKey();

										/**
										 * 分页查询
										 */
										//CreateFileUtil.write(fileWriter, "    /**");
										//CreateFileUtil.write(fileWriter, "    * 分页查询数据");
										//CreateFileUtil.write(fileWriter, "    * @param EasyGridRequest request");
										//CreateFileUtil.write(fileWriter, "    * @throws Exception");
										//CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
										//CreateFileUtil.write(fileWriter, "    */");
										//CreateFileUtil.write(fileWriter, "    public Map<String, Object> queryForPage(EasyGridRequest request) throws Exception {");
										//CreateFileUtil.write(fileWriter, "        return dao.queryForPage(request);");
										//CreateFileUtil.write(fileWriter, "    }");
										//CreateFileUtil.write(fileWriter, "");
										
										/**
										 * 保存对象
										 */
										CreateFileUtil.write(fileWriter, "    /**");
										CreateFileUtil.write(fileWriter, "    * 保存对象");
										CreateFileUtil.write(fileWriter, "    * @param "+tableName+"Dto Dto对象");
										CreateFileUtil.write(fileWriter, "    * @throws Exception");
										CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
										CreateFileUtil.write(fileWriter, "    */");
										CreateFileUtil.write(fileWriter, "    public void saveData("+tableName+"Dto dto) throws Exception {");
										CreateFileUtil.write(fileWriter, "        dao.save(dao.dtoToPo(dto));");
										CreateFileUtil.write(fileWriter, "    }");
										CreateFileUtil.write(fileWriter, "");

										/**
										 * 根据ID加载对象
										 */
										CreateFileUtil.write(fileWriter, "    /**");
										CreateFileUtil.write(fileWriter, "    * 根据ID加载对象");
										CreateFileUtil.write(fileWriter, "    * @param "+optionBean.getListKey());
										CreateFileUtil.write(fileWriter, "    * @throws Exception");
										CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
										CreateFileUtil.write(fileWriter, "    */");
										CreateFileUtil.write(fileWriter, "    public "+tableName+"Dto loadDataById("+optionBean.getListValue()+" "+optionBean.getListKey()+") throws Exception {");
										CreateFileUtil.write(fileWriter, "        return dao.poToDto(dao.findByPrimaryKey("+optionBean.getListKey()+"));");
										CreateFileUtil.write(fileWriter, "    }");
										CreateFileUtil.write(fileWriter, "");

										/**
										 * 根据ID编辑对象
										 */
										CreateFileUtil.write(fileWriter, "    /**");
										CreateFileUtil.write(fileWriter, "    * 根据ID编辑对象");
										CreateFileUtil.write(fileWriter, "    * @param "+tableName+"Dto Dto对象");
										CreateFileUtil.write(fileWriter, "    * @throws Exception");
										CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
										CreateFileUtil.write(fileWriter, "    */");
										CreateFileUtil.write(fileWriter, "    public void editData("+tableName+"Dto dto) throws Exception {");
										CreateFileUtil.write(fileWriter, "        dao.update(dao.dtoToPo(dto), \""+optionBean.getListKey()+"\");");
										CreateFileUtil.write(fileWriter, "    }");
										CreateFileUtil.write(fileWriter, "");

										/**
										 * 根据ID删除数据
										 */
										CreateFileUtil.write(fileWriter, "    /**");
										CreateFileUtil.write(fileWriter, "    * 根据ID删除数据");
										CreateFileUtil.write(fileWriter, "    * @param "+optionBean.getListKey());
										CreateFileUtil.write(fileWriter, "    * @throws Exception");
										CreateFileUtil.write(fileWriter, "    * @author ShunTaoFrameWork");
										CreateFileUtil.write(fileWriter, "    */");
										CreateFileUtil.write(fileWriter, "    public void deleteByPrimaryKeys("+optionBean.getListValue()+" "+optionBean.getListKey()+"s) throws Exception {");
										CreateFileUtil.write(fileWriter, "        dao.deleteByPrimaryKeys("+optionBean.getListKey()+"s);");
										CreateFileUtil.write(fileWriter, "    }");
										CreateFileUtil.write(fileWriter, "");
										
										CreateFileUtil.write(fileWriter, "}");
										
										/**
										 * Service 配置文件
										 */
										//String tableNameSmall  = tableName.substring(0,1).toLowerCase() + tableName.substring(1);
										//CreateFileUtil.write(serviceConfigureFileWriter, "    <bean id=\""+tableNameSmall+"Service\"");
										//CreateFileUtil.write(serviceConfigureFileWriter, "          class=\""+configure.getServiceImplPackagePath()+"."+tableName+"ServiceImpl\">");
										//CreateFileUtil.write(serviceConfigureFileWriter, "        <property name=\"dao\">");
										//CreateFileUtil.write(serviceConfigureFileWriter, "            <ref bean=\""+tableNameSmall+"DAO\" />");
										//CreateFileUtil.write(serviceConfigureFileWriter, "        </property>");
										//CreateFileUtil.write(serviceConfigureFileWriter, "    </bean>");
										//CreateFileUtil.write(serviceConfigureFileWriter, "");
										
									} 
									catch (Exception e) {
										e.printStackTrace();
									}
									finally {
										fileWriter.close();
									}
								}
							}
							
							final String dateFormat = "_DateFormat";
							/**
							 * 生成Object文件（数据传输Bean）
							 */
							if (configure.DTOFILEDESTPATH != null &&
									!"".equals(configure.DTOFILEDESTPATH.trim())) {
								/**
								 * 生成Object文件名
								 */
								String objFileTableName = "";
								if (configure.DTOFILEDESTPATH.endsWith("/") || configure.DTOFILEDESTPATH.endsWith("\\")) {
									objFileTableName = configure.DTOFILEDESTPATH + tableName + "Dto.java" ;
								}
								else {
									objFileTableName = configure.DTOFILEDESTPATH + "/" + tableName + "Dto.java" ;
								}
								if (objFileTableName != null && !"".equals(objFileTableName.trim())) {
									CreateFileUtil.deleteFile(objFileTableName);
									OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(objFileTableName), "UTF-8");									
									try {
										/**
										 * 写Object文件内容
										 */
										CreateFileUtil.write(fileWriter, "package "+configure.getDtoPackagePath()+";");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "/**");
										CreateFileUtil.write(fileWriter, " * 数据对象："+tableName+"Dto，此对象作为业务POJO对象。");
										CreateFileUtil.write(fileWriter, " * 创建日期："+DateTime.getCurrentDate_YYYYMMDDHHMM_CN());
										CreateFileUtil.write(fileWriter, " * @author ShunTaoFrameWork");
										CreateFileUtil.write(fileWriter, " */");
										CreateFileUtil.write(fileWriter, "public class "+tableName+"Dto implements java.io.Serializable {");
										CreateFileUtil.write(fileWriter, "");
										
										CreateFileUtil.write(fileWriter, "    private static final long serialVersionUID = 1L;");
										CreateFileUtil.write(fileWriter, "");

										ColumnBean columnBean = configure.getDao().findMetaDataByTableName(originalTableName);
										ArrayList<OptionBean> resultDataList = columnBean.getColumns();
										if (resultDataList != null && !resultDataList.isEmpty()) {
											for (OptionBean bean : resultDataList) {
												CreateFileUtil.write(fileWriter, "    private String "+bean.getListKey()+";");
												if ("java.util.Date".equalsIgnoreCase(bean.getListValue()) || "java.sql.Date".equalsIgnoreCase(bean.getListValue())) {
													CreateFileUtil.write(fileWriter, "    private String "+bean.getListKey() + dateFormat + " = \"yyyy-MM-dd\";");
												}
												CreateFileUtil.write(fileWriter, "");
											}
										}
										CreateFileUtil.write(fileWriter, "");
										if (resultDataList != null && !resultDataList.isEmpty()) {
											for (OptionBean bean : resultDataList) {
												String fieldName = bean.getListKey();
												/**
												 * setter
												 */
												CreateFileUtil.write(fileWriter, "    public void set"+fieldName.substring(0,1).toUpperCase() + fieldName.substring(1) +"(String "+fieldName+") {");
												CreateFileUtil.write(fileWriter, "        if ("+fieldName+" != null) {"+fieldName+" = "+fieldName+".trim();}");
												CreateFileUtil.write(fileWriter, "        this."+fieldName +" = "+fieldName+";");
												CreateFileUtil.write(fileWriter, "    }");
												CreateFileUtil.write(fileWriter, "");

												/**
												 * getter
												 */
												CreateFileUtil.write(fileWriter, "    public String get"+fieldName.substring(0,1).toUpperCase() + fieldName.substring(1) +"() {");
												CreateFileUtil.write(fileWriter, "        return this."+fieldName +";");
												CreateFileUtil.write(fileWriter, "    }");
												CreateFileUtil.write(fileWriter, "");
												
												if ("java.util.Date".equalsIgnoreCase(bean.getListValue()) || "java.sql.Date".equalsIgnoreCase(bean.getListValue())) {
													/**
													 * setter for dateFormat 
													 */
													CreateFileUtil.write(fileWriter, "    public void set"+fieldName.substring(0,1).toUpperCase() + fieldName.substring(1)+dateFormat +"(String "+fieldName+dateFormat+") {");
													CreateFileUtil.write(fileWriter, "        this."+fieldName +dateFormat +" = "+fieldName+dateFormat+";");
													CreateFileUtil.write(fileWriter, "    }");
													CreateFileUtil.write(fileWriter, "");

													/**
													 * getter for dateFormat
													 */
													CreateFileUtil.write(fileWriter, "    public String get"+fieldName.substring(0,1).toUpperCase() + fieldName.substring(1)+dateFormat +"() {");
													CreateFileUtil.write(fileWriter, "        return this."+fieldName+dateFormat +";");
													CreateFileUtil.write(fileWriter, "    }");
													CreateFileUtil.write(fileWriter, "");
												}
											}
										}
										
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "}");
									} 
									catch (Exception e) {
										e.printStackTrace();
									}
									finally {
										fileWriter.close();
									}
								}
							}

							/**
							 * 生成Object文件（PO持久对象Bean）
							 */
							if (configure.OBJECTFILEDESTPATH != null &&
									!"".equals(configure.OBJECTFILEDESTPATH.trim())) {
								/**
								 * 生成Object文件名
								 */
								String objFileTableName = "";
								if (configure.OBJECTFILEDESTPATH.endsWith("/") || configure.OBJECTFILEDESTPATH.endsWith("\\")) {
									objFileTableName = configure.OBJECTFILEDESTPATH + tableName + "PO.java" ;
								}
								else {
									objFileTableName = configure.OBJECTFILEDESTPATH + "/" + tableName + "PO.java" ;
								}
								if (objFileTableName != null && !"".equals(objFileTableName.trim())) {
									CreateFileUtil.deleteFile(objFileTableName);
									OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(objFileTableName), "UTF-8");									
									try {
										/**
										 * 写Object文件内容
										 */
										CreateFileUtil.write(fileWriter, "package "+configure.getObjectPackagePath()+";");
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "/**");
										CreateFileUtil.write(fileWriter, " * 数据对象："+tableName+"PO，此对象作为持久POJO对象。");
										CreateFileUtil.write(fileWriter, " * 创建日期："+DateTime.getCurrentDate_YYYYMMDDHHMM_CN());
										CreateFileUtil.write(fileWriter, " * @author ShunTaoFrameWork");
										CreateFileUtil.write(fileWriter, " */");
										CreateFileUtil.write(fileWriter, "public class "+tableName+"PO implements java.io.Serializable {");
										CreateFileUtil.write(fileWriter, "");
										
										CreateFileUtil.write(fileWriter, "    private static final long serialVersionUID = 1L;");
										CreateFileUtil.write(fileWriter, "");

										ColumnBean columnBean = configure.getDao().findMetaDataByTableName(originalTableName);
										ArrayList<OptionBean> resultDataList = columnBean.getColumns();
										if (resultDataList != null && !resultDataList.isEmpty()) {
											for (OptionBean bean : resultDataList) {
												CreateFileUtil.write(fileWriter, "    private "+bean.getListValue()+" "+bean.getListKey()+";");
												CreateFileUtil.write(fileWriter, "");
											}
										}
										CreateFileUtil.write(fileWriter, "");
										if (resultDataList != null && !resultDataList.isEmpty()) {
											for (OptionBean bean : resultDataList) {
												String fieldName = bean.getListKey();
												/**
												 * setter
												 */
												CreateFileUtil.write(fileWriter, "    public void set"+fieldName.substring(0,1).toUpperCase() + fieldName.substring(1) +"("+bean.getListValue()+" "+fieldName+") {");
												CreateFileUtil.write(fileWriter, "        this."+fieldName +" = "+fieldName+";");
												CreateFileUtil.write(fileWriter, "    }");
												CreateFileUtil.write(fileWriter, "");

												/**
												 * getter
												 */
												CreateFileUtil.write(fileWriter, "    public "+bean.getListValue()+" get"+fieldName.substring(0,1).toUpperCase() + fieldName.substring(1) +"() {");
												CreateFileUtil.write(fileWriter, "        return this."+fieldName +";");
												CreateFileUtil.write(fileWriter, "    }");
												CreateFileUtil.write(fileWriter, "");
											}
										}
										
										CreateFileUtil.write(fileWriter, "");
										CreateFileUtil.write(fileWriter, "}");
									} 
									catch (Exception e) {
										e.printStackTrace();
									}
									finally {
										fileWriter.close();
									}
								}
							}
							
						}
					}
					configure.getDao().closeConnection();
				} catch (Exception e) {
					System.out.println("温馨提示：创建映射文件失败，错误信息如下：");
					e.printStackTrace();
				}
				finally {
					//daoConfigureFileWriter.close();
					//serviceConfigureFileWriter.close();
				}
			}
		}
		System.out.println("温馨提示：创建映射文件完毕！");
	}
	
	/**
	 * 写行
	 * @param writer
	 * @param lineString 行信息
	 * @throws Exception
	 * @author AllenZhang
	 */
	private static void write(OutputStreamWriter writer, String lineString) throws Exception {
		if (writer != null) {
			writer.write(lineString);
			writer.write(13);
			writer.write(10);
		}
	}
	
	/**
	 * 删除文件
	 * @param fileName
	 * @throws Exception
	 * @author AllenZhang
	 */
	private static void deleteFile(String fileName) throws Exception {
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 创建所有目录
	 * @throws Exception
	 * @author AllenZhang
	 */
	private static void createDirectories(Configure configure) throws Exception {
		CreateFileUtil.createDirectory(configure.DAOFILEDESTPATH);
		CreateFileUtil.createDirectory(configure.DAOIMPLFILEDESTPATH);
		CreateFileUtil.createDirectory(configure.SERVICEFILEDESTPATH);
		CreateFileUtil.createDirectory(configure.SERVICEIMPLFILEDESTPATH);
		//CreateFileUtil.createDirectory(configure.SPRINGDAOXMLFILEPATH);
		//CreateFileUtil.createDirectory(configure.SPRINGSERVICEXMLFILEPATH);
		CreateFileUtil.createDirectory(configure.DTOFILEDESTPATH);
		CreateFileUtil.createDirectory(configure.OBJECTFILEDESTPATH);
	}
	
	/**
	 * 创建目录
	 * @param directory
	 * @throws Exception
	 * @author AllenZhang
	 */
	private static void createDirectory(String directory) throws Exception {
		File file = new File(directory);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
}