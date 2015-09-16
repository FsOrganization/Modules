package com.fla.common.util.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConnectionManager {

	
	@Autowired
	private  JdbcTemplate jdbcTemplate;
	
	private ConnectionManager() {
	}  
	private static ConnectionManager connectionManager=null;  
	
	 public static ConnectionManager getInstance() {  
         synchronized (ConnectionManager.class) {
			if (connectionManager == null) {
				connectionManager = new ConnectionManager();
			}
		}
		return connectionManager;  
    } 
	
    public void closeConnection(Connection conn){
        try 
        {
            if(conn!=null&&!(conn.isClosed()))
            {
                conn.close();               
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void closeResultSet(ResultSet rs){
        if(rs!=null)
        {
            try
            {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs=null;
        }
    }
    public void closeStatement(PreparedStatement pStatement){
        if(pStatement!=null)
        {
            try 
            {
                pStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            pStatement=null;
        }
    }

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public Connection getConnection() {
		Connection con = null;
		try 
		{
			con = jdbcTemplate.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
    
}
