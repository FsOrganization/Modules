package com.fla.common.dao.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.fla.common.entity.SentExpressInfo;


public interface ExpressDaoInterface {
	/**
	 * 获取寄件信息
	 * @param rowSize
	 * @param pageSize
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getSentExpressInfo(final int rowSize, final int pageSize, Map<String,String> params);
	
	public void insertSendExpressInfo(SentExpressInfo sei) throws Exception;
	
	public void modifySentExpressInfo(SentExpressInfo sei) throws Exception;
	
	
	}
