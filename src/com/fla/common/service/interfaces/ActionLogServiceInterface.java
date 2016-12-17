package com.fla.common.service.interfaces;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import com.fla.common.base.SuperService;
import com.fla.common.dao.interfaces.ActionLogDaoInterface;

public interface ActionLogServiceInterface extends SuperService<ActionLogDaoInterface> {
	public void saveActionLog(JoinPoint joinpoint);
	public void whenThrowingExceptions(JoinPoint joinpoint, Exception e);
	public Object aroundLogMethod(ProceedingJoinPoint point);
	
}
