package com.fla.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.base.SuperServiceAdapter;
import com.fla.common.dao.interfaces.RoleDaoInterface;
import com.fla.common.entity.SysMenu;
import com.fla.common.entity.SysRoleMenu;
import com.fla.common.entity.SystemRole;
import com.fla.common.service.interfaces.RoleServiceInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
@Transactional
public class RoleService extends SuperServiceAdapter<RoleDaoInterface> implements RoleServiceInterface{
	
	@Autowired
	@Override
	public void setMapper(RoleDaoInterface mapper) {
		this.mapper = mapper;
	}

	public RoleService() {
	}

	@Override
	public List<SystemRole> getSysRoleList(Map<String, Object> params,
			PageBounds pageBounds) {
		return mapper.getSysRoleList(params, pageBounds);
	}

	@Override
	public List<SystemRole> findEntityList(Map<String, Object> params) {
		return mapper.findEntityList(params);
	}

	@Override
	public SystemRole getSysRoleById(Map<String, Object> params) {
		return mapper.getSysRoleById(params);
	}

	@Override
	public void insert(SystemRole role) {
		mapper.insert(role);
	}

	@Override
	public void delete(SystemRole role) {
		mapper.delete(role);		
	}

	@Override
	public void update(SystemRole role) {
		mapper.update(role);
	}

	@Override
	public void addMenuInRole(SysRoleMenu sysRoleMenu) {
		mapper.addMenuInRole(sysRoleMenu);
	}

	@Override
	public void addMenusByParentInRole(SysRoleMenu sysRoleMenu) {
		mapper.addMenusByParentInRole(sysRoleMenu);
	}

	@Override
	public void addMenusBySelectInRole(SysRoleMenu sysRoleMenu) {
		mapper.addMenusBySelectInRole(sysRoleMenu);
	}

	@Override
	public SysMenu getMenuByParentId(SysMenu sysMenu) {
		return mapper.getMenuByParentId(sysMenu);
	}

	@Override
	public List<SysMenu> getAllMenus(Map<String, Object> params) {
		return mapper.getAllMenus(params);
	}

	@Override
	public List<Integer> getMenuListByRoleId(Map<String, Object> params) {
		return mapper.getMenuListByRoleId(params);
	}

	@Override
	public void menuOutRole(SysRoleMenu sysRoleMenu) {
		mapper.menuOutRole(sysRoleMenu);
		
	}
	
	
	
}
