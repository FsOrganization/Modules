package com.fla.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.base.SuperServiceAdapter;
import com.fla.common.dao.interfaces.UserRoleDaoInterface;
import com.fla.common.entity.SysMenu;
import com.fla.common.entity.SysUserRole;
import com.fla.common.entity.SystemRole;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.UserRoleServiceInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
@Transactional
public class UserRoleService extends SuperServiceAdapter<UserRoleDaoInterface> implements UserRoleServiceInterface{
	
	@Autowired
	@Override
	public void setMapper(UserRoleDaoInterface mapper) {
		this.mapper = mapper;
	}

	public UserRoleService() {
	}

	@Override
	public List<SysMenu> getRoleMenuListByUserId(Map<String,Object> params) {
		return mapper.getRoleMenuListByUserId(params);
	}

	@Override
	public List<SysMenu> getRoleMenuListByParentId(Map<String, Object> params) {
		return mapper.getRoleMenuListByParentId(params);
	}

	@Override
	public List<SysUserRole> getUserListByRoleId(Map<String, Object> params, PageBounds pageBounds) {
		return mapper.getUserListByRoleId(params, pageBounds);
	}

	@Override
	public List<SysUserRole> getRoleListByUserId(Map<String, Object> params, PageBounds pageBounds) {
		return mapper.getRoleListByUserId(params, pageBounds);
	}

	@Override
	public List<SysUserRole> getUserRoleList(Map<String, Object> params, PageBounds pageBounds) {
		return mapper.getUserRoleList(params, pageBounds);
	}

	@Override
	public void deleteUserRole(Map<String, Object> params) {
		mapper.deleteUserRole(params);
	}

	@Override
	public List<SystemRole> getRoleList(Map<String, Object> params,
			PageBounds pageBounds) {
		return mapper.getRoleList(params, pageBounds);
	}

	@Override
	public List<SystemUser> getUserList(Map<String, Object> params,
			PageBounds pageBounds) {
		return mapper.getUserList(params, pageBounds);
	}

	@Override
	public void addUserRole(Map<String, Object> params) {
		mapper.addUserRole(params);
	}

	@Override
	public Integer checkUserRoleCount(Map<String, Object> params) {
		return mapper.checkUserRoleCount(params);
	}

}
