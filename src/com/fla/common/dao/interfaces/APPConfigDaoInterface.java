package com.fla.common.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.MapperAnnotation;
import com.fla.common.base.SuperMapper;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.entity.ExpressProviderContacts;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@MapperAnnotation
public interface APPConfigDaoInterface extends SuperMapper {

	public List<CustomerInfo> getRegisteredCustomers(Map<String, Object> params, PageBounds pageBounds);

	public List<ExpressProviderContacts> getProviderContacts(Map<String, Object> params);

	public void setCustomerBeProviderContacts(ExpressProviderContacts expressProviderContacts);

	public ExpressProviderContacts getCustomerBeProviderContacts(Map<String, Object> params);

}
