package com.fla.common.dao.interfaces;

import java.sql.SQLException;
import java.util.Map;

import com.fla.common.entity.SentExpressInfo;
import com.fla.common.util.Pagination;


public interface ExpressDaoInterface {
	/**
	 * 获取寄件信息
	 * @param rowSize
	 * @param pageSize
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Pagination getSentExpressInfo(final int rowSize, final int pageSize, Map<String,String> params);
	
	public void insertSendExpressInfo(SentExpressInfo sei) throws Exception;
	
	public void modifySentExpressInfo(SentExpressInfo sei) throws Exception;
	
	
	}
