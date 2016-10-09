package com.fla.common.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.MapperAnnotation;
import com.fla.common.base.SuperMapper;
import com.fla.common.entity.SystemConfig;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@MapperAnnotation
public interface SystemConfigDaoInterface extends SuperMapper {
	
	public List<SystemConfig> getSystemConfigList(Map<String,Object> params,PageBounds pageBounds);
	
	public SystemConfig getSystemConfigById(SystemConfig config);
	
	
}
