package com.fla.common.dao.interfaces;

import java.util.List;
import java.util.Map;
<<<<<<< HEAD

=======
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
import com.fla.common.base.MapperAnnotation;
import com.fla.common.base.SuperMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@MapperAnnotation
public interface SQLPlusDaoInterface extends SuperMapper{
<<<<<<< HEAD
	public List<Map<String,Object>> execute(Map<String,Object> params,PageBounds pageBounds);
	public void executeDML(Map<String,Object> params);
	public void executeDDL(Map<String,Object> params);
	public void executeDCL(Map<String,Object> params);
	public void executeTCL(Map<String,Object> params);
=======
	public List<Map<String,Object>> execute(Map<String,Object> params,PageBounds pageBounds); 
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
}
