package com.fla.common.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.MapperAnnotation;
import com.fla.common.base.SuperMapper;
import com.fla.common.entity.SysMenu;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@MapperAnnotation
public interface MenuDaoInterface extends SuperMapper{
	public List<SysMenu> getSysMenuList(Map<String,Object> params,PageBounds pageBounds);
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
