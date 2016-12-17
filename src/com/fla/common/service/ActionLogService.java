package com.fla.common.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fla.common.base.SuperServiceAdapter;
import com.fla.common.dao.interfaces.ActionLogDaoInterface;
import com.fla.common.entity.ActionLog;
import com.fla.common.service.interfaces.ActionLogServiceInterface;

@Service
@Transactional
public class ActionLogService extends SuperServiceAdapter<ActionLogDaoInterface> implements ActionLogServiceInterface{

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	@Override
	public void setMapper(ActionLogDaoInterface mapper) {
		this.mapper = mapper;
	}

	public ActionLogService() {
	}

	@Override
	public void saveActionLog(JoinPoint joinpoint) {
		ActionLog actionLog = getData(joinpoint);
		actionLog.setStatus("success");
		mapper.saveActionLog(actionLog);
	}

	@Override
	public void whenThrowingExceptions(JoinPoint joinpoint, Exception e) {
		ActionLog actionLog = getData(joinpoint);
		String methodName = joinpoint.getSignature().getName();
		actionLog.setActionMsg("异常通知：The Method "+ methodName +", occurs exception " + e);
		actionLog.setStatus("failure");
		mapper.saveActionLog(actionLog);
	}
	
	
	
	@SuppressWarnings("static-access")
	@Override
	public Object aroundLogMethod(ProceedingJoinPoint point) {
        Object result = null;
        ActionLog actionLog = getData(point);
        Long sTime = getCurrentTimeMillis();
        try 
        {
        	actionLog.setStatus("success");
            result = point.proceed();
            actionLog.setActionMsg(point.SYNCHRONIZATION_UNLOCK);
        } catch (Throwable e) {
        	actionLog.setStatus("failure");
        	actionLog.setActionMsg(e.getMessage());
        } finally {
        	Long eTime = getCurrentTimeMillis();
        	actionLog.setExecuteTime(eTime - sTime);
        	mapper.saveActionLog(actionLog);
        }
        
        return result;
    }

	@SuppressWarnings("static-access")
	private ActionLog getData(JoinPoint joinpoint){
		ActionLog actionLog = new ActionLog();
		String dateStr = sdf.format(new Date());
		try 
		{
			actionLog.setActionDate(sdf.parse(dateStr));
		} catch (ParseException e) {
			actionLog.setActionDate(new Date());
		}
		actionLog.setActionMethod(joinpoint.getSignature().getName());
		actionLog.setAreaCode(null);
		actionLog.setClassName(joinpoint.getKind());
		actionLog.setCode(joinpoint.getSignature().getDeclaringType().getSimpleName());
		actionLog.setRemark(null);
		actionLog.setUserId(null);
		actionLog.setUserName(null);
//		actionLog.setRemark(joinpoint.METHOD_CALL);
		return actionLog;
	}
	
	private Long getCurrentTimeMillis(){
		return System.currentTimeMillis();
	}
	
}
