package com.fla.common.base;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface SuperMapper {

	/**
	 * 查询实体
	 * @return
	 */
	public <T>T findEntity();
    
    /**
     * 查询实体
     * @param params
     * @return
     */
	public <T, A>T findEntity(A params);

    /**
     * 查询实体集合
     * @return
     */
	public <T>List<T> findEntityList();
    
    /**
     * 查询实体集合
     * @param params
     * @return
     */
	public <T, A> List<T> findEntityList(A params);

    /**
     * 查询实体集合,分页
     * @param params
     * @param pageBounds
     * @return
     */
	public <T, A> List<T> findEntityListForPages(A params, PageBounds pageBounds);

    /**
     * 新增
     * @param entity
     */
	public <T> void insert(T entity);
    
    /**
     * 修改
     * @param entity
     */
	public <T> void update(T entity);
    
    /**
     * 删除
     * @param keyOrEntity
     */
	public <T> void delete(T keyOrEntity);

}
