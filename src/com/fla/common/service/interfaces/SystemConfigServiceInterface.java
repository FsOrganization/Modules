/**
 * @author John
 */
package com.fla.common.service.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.SuperService;
import com.fla.common.dao.interfaces.SystemConfigDaoInterface;
import com.fla.common.entity.SystemConfig;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/**
 * @author John
 *
 */
public interface SystemConfigServiceInterface extends SuperService<SystemConfigDaoInterface>{
	
	public List<SystemConfig> getSystemConfigList(Map<String,Object> params,PageBounds pageBounds);
	
	public SystemConfig getSystemConfigById(SystemConfig config);
	
	public SystemConfig getSystemConfigByCode(Map<String,Object> params);
	
}
