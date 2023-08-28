package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.google.gson.Gson;

import entity.Comment;

public class MyJDBCUtil {
	
	static String url;
	static String user;
	static String password;
	static {
		Properties p = new Properties();
		try {
			//获得src下的db.properties文件
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
			p.load(is);
			String driverClass = p.getProperty("driverClass");
			url = p.getProperty("url");
			user = p.getProperty("user");
			password = p.getProperty("password");
			Class.forName(driverClass);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public  static  void releaseResource(Connection conn,Statement stmt,ResultSet rs) throws SQLException {
		
		
		if(rs!=null) {
			rs.close();
		}
		if(stmt!=null) {
			stmt.close();
		}
		if(conn!=null) {
			conn.close();
		}
		
	}
	
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
	
	/*public static void main(String[] args)
	{
		String json = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MyJDBCUtil.getConnection();

			String sql = "select * from comment_table where arendID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 12);
			rs = pstmt.executeQuery();
			// 2.封装成装着多个Comment对象的集合
			List<Comment> coms = new ArrayList<Comment>();
			while (rs.next()) {
				Comment com = new Comment();
				// 封装对象
				com.setCommentID(rs.getInt("commentID"));
				com.setArendID(rs.getInt("arendID"));
			    com.setContent(rs.getString("content"));
			    com.setCreatorAvatar(rs.getString("creatorAvatar"));
			    com.setCreatorID(rs.getInt("creatorID"));
			    com.setCreatorName(rs.getString("creatorName"));
			    com.setCreatTime(rs.getTimestamp("createTime"));

				// 将对象添加到集合中
				coms.add(com);

			}

			// 3.转换成json字符串
			Gson gson = new Gson();
			json = gson.toJson(coms);
			System.out.println(json);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				MyJDBCUtil.releaseResource(conn, pstmt, rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		
	}*/
	}
