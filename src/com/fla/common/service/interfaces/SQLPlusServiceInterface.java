package com.fla.common.service.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.SuperService;
import com.fla.common.dao.interfaces.SQLPlusDaoInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface SQLPlusServiceInterface extends SuperService<SQLPlusDaoInterface> {
	public List<Map<String,Object>> execute(Map<String,Object> params,PageBounds pageBounds); 
	public void executeDML(Map<String,Object> params);
	public void executeDDL(Map<String,Object> params);
	public void executeDCL(Map<String,Object> params);
	public void executeTCL(Map<String,Object> params);
	
}
