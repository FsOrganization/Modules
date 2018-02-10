package com.fla.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.base.ServiceSaveDelegate;
import com.fla.common.base.SuperServiceAdapter;
import com.fla.common.dao.interfaces.MenuDaoInterface;
import com.fla.common.entity.SysMenu;
import com.fla.common.service.interfaces.MenuServiceInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
@Transactional
public class MenuService extends SuperServiceAdapter<MenuDaoInterface> implements MenuServiceInterface{

	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	@Override
	public void setMapper(MenuDaoInterface mapper) {
		this.mapper = mapper;
	}

	public MenuService() {
	}

	@Override
	public List<SysMenu> getSysMenuList(Map<String, Object> params,PageBounds pageBounds) {
		return mapper.getSysMenuList(params, pageBounds);
	}
	
	@Override
	public List<SysMenu> findEntityList(Map<String, Object> params) {
		return mapper.findEntityList(params);
	}

	@Override
	public SysMenu getSysMenuById(Map<String, Object> params) {
		return mapper.getSysMenuById(params);
	}

	@Override
	public void insert(SysMenu sysMenu) {
		mapper.insert(sysMenu);
	}

	@Override
	public void delete(SysMenu sysMenu) {
		mapper.delete(sysMenu);
	}

	@Override
	public void update(SysMenu sysMenu) {
		mapper.update(sysMenu);
	}

	@Override
	public List<SysMenu> getMainLevelMenuList(SysMenu sysMenu) {
		return mapper.getMainLevelMenuList(sysMenu);
	}

	@Override
	public List<SysMenu> getAbstractMenuList(SysMenu sysMenu) {
		return mapper.getAbstractMenuList(sysMenu);
	}

	@Override
	public List<SysMenu> getMenuListByParentId(Map<String,Object> params) {
		return mapper.getMenuListByParentId(params);
	}

	@Override
	public List<SysMenu> getMainLevelMenuList(SysMenu sysMenu,
			PageBounds pageBounds) {
		return mapper.getMainLevelMenuList(sysMenu, pageBounds);
	}

	@Override
	public List<SysMenu> getMenuListByParentId(Map<String, Object> params,
			PageBounds pageBounds) {
		return mapper.getMenuListByParentId(params, pageBounds);
	}

	@Override
	public <A> void saveEntity(A entity, ServiceSaveDelegate... delegate) {
		super.saveEntity(entity, delegate);
	}
	
}
