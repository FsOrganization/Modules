package com.fla.common.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class BaseJdbcDaoSupport extends JdbcDaoSupport {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	protected BaseJdbcDaoSupport(JdbcTemplate jdbcTemplate)  {
		setJdbcTemplate(jdbcTemplate);
	}
	protected BaseJdbcDaoSupport()  {
	}

	@Override protected void checkDaoConfig() {
		super.checkDaoConfig();
	}

	@Override protected void initTemplateConfig() {
		super.initTemplateConfig();
	}

	@Override protected void initDao() throws Exception {
		super.initDao();
	}
	
}
