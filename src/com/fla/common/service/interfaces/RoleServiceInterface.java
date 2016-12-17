package com.fla.common.service.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.SuperService;
import com.fla.common.dao.interfaces.RoleDaoInterface;
import com.fla.common.entity.SysMenu;
import com.fla.common.entity.SysRoleMenu;
import com.fla.common.entity.SystemRole;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;


public interface RoleServiceInterface extends SuperService<RoleDaoInterface> {
	public List<SystemRole> getSysRoleList(Map<String,Object> params,PageBounds pageBounds);
	public List<SystemRole> findEntityList(Map<String,Object> params);
	public SystemRole getSysRoleById(Map<String,Object> params);
	public void insert(SystemRole role);
	public void delete(SystemRole role);
	public void update(SystemRole role);
	
	public void addMenuInRole(SysRoleMenu sysRoleMenu);
	public void menuOutRole(SysRoleMenu sysRoleMenu);
	public void addMenusByParentInRole(SysRoleMenu sysRoleMenu);
	public void addMenusBySelectInRole(SysRoleMenu sysRoleMenu);
	public SysMenu getMenuByParentId(SysMenu sysMenu);
	public List<SysMenu> getAllMenus(Map<String,Object> params);
	
	public List<Integer> getMenuListByRoleId(Map<String,Object> params);
}
