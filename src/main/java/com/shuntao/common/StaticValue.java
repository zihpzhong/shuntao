package com.shuntao.common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;



/**
 * 
 * @ProjectName：yuanben   
 * @ClassName：StaticValue   
 * @ClassDescription：   共有静态对象区
 * @Author：Johnny 
 * @CreateTime：2014年11月18日 下午2:08:26   
 * @Modifier：Johnny  
 * @ModifyTime：2014年11月18日 下午2:08:26   
 * @ModifyInfo：   
 * @version  0.1
 */
public class StaticValue {
	
	//项目ID--包命名的参考
	public static final String projectId = "yuanben";
	
	//品种根节点
	public static final String PDT_TYPE_ROOT = "00000000000000000000000000000000";
	
	//资源服务器URL
	public static final String RES_URL = "http://192.168.1.10:8080/";
	
	//区域根节点
	public static final String AREA_ROOT = "0";
	
	public static  ApplicationContext ac ;
	
	
	
}
