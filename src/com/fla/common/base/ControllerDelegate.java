package com.fla.common.base;

import java.util.List;

public interface ControllerDelegate<T> {

    void afterFindEntityOver(T entity);

    void afterPagingQuery(List<T> entities);

    void afterSave(T entity);

    void afterDrop(T entity);
}
