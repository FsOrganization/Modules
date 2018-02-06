/**
 * 
 */
package com.fla.common.service.interfaces;

import java.util.List;
import java.util.Map;
import com.fla.common.base.SuperService;
import com.fla.common.dao.interfaces.ICustomerDao;
import com.fla.common.entity.CustomerInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/**
 * @author Administrator
 *
 */
public interface ICustomerService extends SuperService<ICustomerDao> {
	
	public List<CustomerInfo> getCustomerInfoList(Map<String,Object> params,PageBounds pageBounds);

}
