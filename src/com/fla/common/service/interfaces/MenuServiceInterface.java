package com.fla.common.service.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.SuperService;
import com.fla.common.dao.interfaces.MenuDaoInterface;
import com.fla.common.entity.SysMenu;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;


public interface MenuServiceInterface extends SuperService<MenuDaoInterface> {
	
	public List<SysMenu> getSysMenuList(Map<String,Object> params,PageBounds pageBounds);
	public List<SysMenu> findEntityList(Map<String,Object> params);
	public SysMenu getSysMenuById(Map<String,Object> params);
	public void insert(SysMenu sysMenu);
	public void delete(SysMenu sysMenu);
	public void update(SysMenu sysMenu);
	public List<SysMenu> getMainLevelMenuList(SysMenu sysMenu);
	public List<SysMenu> getAbstractMenuList(SysMenu sysMenu);
	public List<SysMenu> getMainLevelMenuList(SysMenu sysMenu,PageBounds pageBounds);
	public List<SysMenu> getMenuListByParentId(Map<String,Object> params); 
	public List<SysMenu> getMenuListByParentId(Map<String,Object> params,PageBounds pageBounds); 
	
}
