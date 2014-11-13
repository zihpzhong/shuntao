package com.shuntao.common.creator;

import com.shuntao.common.StaticValue;

/**
 * 开发调试，映射管理，配置
 * @author AllenZhang
 * @version 0.1 (2009.11.21)
 * @modify AllenZhang (2009.11.21)
 */
public class Configure  {

	/**
	 * 表名前缀
	 */
	public String TABLENAMEPREFIX = "";
	
	/**
	 * 基础路径
	 */
	public String BASEPATH = "d:/autoCreate/";
	
	/**
	 * DAO文件输出目标路径
	 */
	public String DAOFILEDESTPATH = BASEPATH + "dao/";

	/**
	 * DAO文件输出目标路径
	 */
	public String DAOIMPLFILEDESTPATH = BASEPATH + "dao/impl/";
	
	/**
	 * Service文件输出目标路径
	 */
	public String SERVICEFILEDESTPATH = BASEPATH + "service/";
	
	/**
	 * ServiceImpl文件输出目标路径
	 */
	public String SERVICEIMPLFILEDESTPATH = BASEPATH + "service/impl/";
	
	/**
	 * 配置文件路径 DAO 配置文件
	 */
	public String SPRINGDAOXMLFILEPATH = BASEPATH + "xml/";
	/**
	 * 配置文件路径 Service 配置文件
	 */
	public String SPRINGSERVICEXMLFILEPATH = BASEPATH + "xml/";

	/**
	 * Object文件输出目标路径（数据传输Bean）
	 */
	public String DTOFILEDESTPATH = BASEPATH + "pojo/dto/";
	
	/**
	 * Object文件输出目标路径（持久对象Bean）
	 */
	public String OBJECTFILEDESTPATH = BASEPATH + "pojo/po/";
	
	private DAO dao;
	
	//****************************************************************************
	/**
	 * DAO文件的基类
	 */
	private String baseDao = "com.shuntao.common.db.dao.OracleBaseDAO";
	/**
	 * DAO 包名
	 */
	private String daoPackagePath = "com.shuntao."+StaticValue.projectId+".dao";

	/**
	 * DAO 包名
	 */
	private String daoImplPackagePath = "com.shuntao."+StaticValue.projectId+".dao.impl";
	//****************************************************************************
	

	//****************************************************************************
	/**
	 * Service文件的基接口
	 */
	private String baseService = "";
	/**
	 * Service 包名
	 */
	private String servicePackagePath = "com.shuntao."+StaticValue.projectId+".service";
	//****************************************************************************


	//****************************************************************************
	/**
	 * ServiceImpl文件的基类
	 */
	private String baseServiceImpl = "com.shuntao."+StaticValue.projectId+".service.impl.BaseServiceImpl";
	/**
	 * ServiceImpl 包名
	 */
	private String serviceImplPackagePath = "com.shuntao."+StaticValue.projectId+".service.impl";
	//****************************************************************************

	/**
	 * Object 包名（数据传输对象Bean）
	 */
	private String dtoPackagePath = "com.shuntao."+StaticValue.projectId+".pojo.dto";
	
	/**
	 * Object 包名（持久对象Bean）
	 */
	private String objectPackagePath = "com.shuntao."+StaticValue.projectId+".pojo.po";
	
	/**
	 * 处理结果
	 * 0 成功
	 * 1 失败
	 */
	private String handleResult;
	
	public Configure(DAO dao) {
		this.dao = dao;
	}
	
	public String getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(String baseDao) {
		this.baseDao = baseDao;
	}

	public String getBaseService() {
		return baseService;
	}

	public void setBaseService(String baseService) {
		this.baseService = baseService;
	}

	public String getBaseServiceImpl() {
		return baseServiceImpl;
	}

	public void setBaseServiceImpl(String baseServiceImpl) {
		this.baseServiceImpl = baseServiceImpl;
	}
	
	public String getDaoPackagePath() {
		return daoPackagePath;
	}

	public void setDaoPackagePath(String daoPackagePath) {
		this.daoPackagePath = daoPackagePath;
	}

	public String getServicePackagePath() {
		return servicePackagePath;
	}

	public void setServicePackagePath(String servicePackagePath) {
		this.servicePackagePath = servicePackagePath;
	}

	public String getServiceImplPackagePath() {
		return serviceImplPackagePath;
	}

	public void setServiceImplPackagePath(String serviceImplPackagePath) {
		this.serviceImplPackagePath = serviceImplPackagePath;
	}

	public String getHandleResult() {
		return handleResult;
	}

	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}

	public String getObjectPackagePath() {
		return objectPackagePath;
	}

	public void setObjectPackagePath(String objectPackagePath) {
		this.objectPackagePath = objectPackagePath;
	}

	public DAO getDao() {
		return dao;
	}

	public void setDao(DAO dao) {
		this.dao = dao;
	}

	public String getDtoPackagePath() {
		return dtoPackagePath;
	}

	public void setDtoPackagePath(String dtoPackagePath) {
		this.dtoPackagePath = dtoPackagePath;
	}

	public String getDaoImplPackagePath() {
		return daoImplPackagePath;
	}

	public void setDaoImplPackagePath(String daoImplPackagePath) {
		this.daoImplPackagePath = daoImplPackagePath;
	}
}