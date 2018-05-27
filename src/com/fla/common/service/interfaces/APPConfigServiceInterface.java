package com.fla.common.service.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.SuperService;
import com.fla.common.dao.interfaces.APPConfigDaoInterface;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.entity.ExpressProviderContacts;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface APPConfigServiceInterface extends SuperService<APPConfigDaoInterface> {
	public List<CustomerInfo> getRegisteredCustomers(Map<String, Object> params, PageBounds pageBounds);
	public List<ExpressProviderContacts> getProviderContacts(Map<String, Object> params);
	public void setCustomerBeProviderContacts(ExpressProviderContacts expressProviderContacts);
//	public ExpressProviderContacts getCustomerBeProviderContacts(Map<String, Object> params);
}
