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
	
	/* 快递滞纳金收费限制 */
	public String getLateDayLimit(Map<String,Object> params);
	
	/* 延期天数限制 */
	public String getLateFeeLimitUpper(Map<String,Object> params);
	
	/* 会员快递滞纳金收费限制优惠 */
	public String getMemberLateFeeAddition(Map<String,Object> params);
	
	/* 会员延期天数限制优惠 */
	public String getMemberLateDayAddition(Map<String,Object> params);
	
	public SystemConfig getConfigValueByCode(Map<String,Object> params);
	
	
}
