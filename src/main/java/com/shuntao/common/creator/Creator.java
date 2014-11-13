package com.shuntao.common.creator;

import org.junit.Test;

/**
 * 
 * 项目名称：shuntao   
 * 类名称：Creator   
 * 类描述：   
 * 创建人：Ss 
 * 创建时间：2014年11月19日 下午8:41:06   
 * 修改人：Ss 
 * 修改时间：2014年11月19日 下午8:41:06   
 * 修改备注：   
 * @version
 */
public class Creator {
	
	@Test
	public void create () {
		try {
			DAO dao = new DAO();
			CreateFileUtil.createFiles(dao.findCurrentUserTableMetaData(), new Configure(dao));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}