package com.fla.common.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.MapperAnnotation;
import com.fla.common.base.SuperMapper;
import com.fla.common.entity.CustomerInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@MapperAnnotation
public interface ICustomerDao extends SuperMapper{
	public List<CustomerInfo> getCustomerInfoList(Map<String,Object> params,PageBounds pageBounds);
	
}
