package com.DAO;

import java.sql.SQLException;

public abstract class AbstractDAO {
	public DataConnection dbAccess=null;
	
	public AbstractDAO(){
		// TODO Auto-generated constructor stub
		try {
			dbAccess=DataConnection.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
