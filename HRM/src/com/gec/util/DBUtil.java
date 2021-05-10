package com.gec.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;


public abstract class DBUtil<T> {
	private static DataSource ds;
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private ResultSetMetaData rsmd = null;
	
	//连接池
	static {
		Properties pro = new Properties();
		try(InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
			pro.load(in);
			//将属性注入到连接池工厂中，创建出连接池来
			ds = DruidDataSourceFactory.createDataSource(pro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Connection getConn() throws Exception {
		conn = ds.getConnection();
		return conn;
	}
	
	public boolean update(String sql, Object...obj) {
		try {
			pst = getConn().prepareStatement(sql);
			for(int i = 0; i < obj.length; i++) {
				pst.setObject(i+1, obj[i]);
			}
			int row = pst.executeUpdate();
			if(row > 0) 
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			getClose(conn, pst, rs);
		}
		return false;
	}
	
	public int getCount(String sql,Object...obj){
		try {
			pst = getConn().prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				pst.setObject(i+1, obj[i]);				
			}
			rs = pst.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			getClose(conn, pst, rs);
		}
		return 0;
	}
	
	public List<T> query(String sql, Class<T> clazz, Object...obj){
		List<T> list = new ArrayList<>();
		try {
			pst = getConn().prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				pst.setObject(i+1, obj[i]);
			}
			rs = pst.executeQuery();
			rsmd = rs.getMetaData();
			int cnum = rsmd.getColumnCount();
			while(rs.next()) {
				T t = clazz.newInstance();
				for (int j = 1; j <= cnum; j++) {
					String name = rsmd.getColumnName(j);
					Field field = clazz.getDeclaredField(name);
					field.setAccessible(true);
					field.set(t, rs.getObject(j));
				}
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			getClose(conn, pst, rs);
		}
		return list;
	}
	
	private void getClose(Connection conn, PreparedStatement pst, ResultSet rs) {
		
			try {
				if(rs!=null)
					rs.close();
				if(pst!=null)
					pst.close();
				if(conn!=null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
