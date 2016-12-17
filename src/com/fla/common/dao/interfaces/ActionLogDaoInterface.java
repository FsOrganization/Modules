package com.fla.common.dao.interfaces;

import org.aspectj.lang.JoinPoint;

import com.fla.common.base.MapperAnnotation;
import com.fla.common.base.SuperMapper;
import com.fla.common.entity.ActionLog;

@MapperAnnotation
public interface ActionLogDaoInterface extends SuperMapper{
	public void saveActionLog(ActionLog actionLog);
	public void afterThrowing(JoinPoint joinpoint, Exception e);
}
