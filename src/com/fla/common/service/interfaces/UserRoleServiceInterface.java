package com.fla.common.service.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.SuperService;
import com.fla.common.dao.interfaces.UserRoleDaoInterface;
import com.fla.common.entity.SysMenu;
import com.fla.common.entity.SysUserRole;
import com.fla.common.entity.SystemRole;
import com.fla.common.entity.SystemUser;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface UserRoleServiceInterface extends SuperService<UserRoleDaoInterface> {
	public List<SysMenu> getRoleMenuListByUserId(Map<String, Object> params);

	public List<SysMenu> getRoleMenuListByParentId(Map<String, Object> params);

	public List<SysUserRole> getUserListByRoleId(Map<String, Object> params, PageBounds pageBounds);

	public List<SysUserRole> getRoleListByUserId(Map<String, Object> params, PageBounds pageBounds);

	public List<SysUserRole> getUserRoleList(Map<String, Object> params, PageBounds pageBounds);
	
	public void deleteUserRole(Map<String, Object> params);
	
	public List<SystemRole> getRoleList(Map<String, Object> params, PageBounds pageBounds);
	
	public List<SystemUser> getUserList(Map<String, Object> params, PageBounds pageBounds);
	
	public void addUserRole(Map<String, Object> params);
	
	public Integer checkUserRoleCount(Map<String, Object> params);
	
}
