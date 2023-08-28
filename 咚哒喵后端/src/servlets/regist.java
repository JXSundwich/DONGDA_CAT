package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dto.ResultDto;
import util.MyJDBCUtil;

/**
 * Servlet implementation class regist
 */
@SuppressWarnings("unused")
@WebServlet("/regist")
public class regist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public regist() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//1.获取传递到后端的参数
		 ResultDto dto=new ResultDto();
		 boolean check=false;
		 
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		Boolean sex = Boolean.parseBoolean(request.getParameter("sex"));
		int age = Integer.parseInt(request.getParameter("age"));
		String avatarUrl = request.getParameter("avatarUrl");
		String password = request.getParameter("password");
	    String account = request.getParameter("account");
		response.setCharacterEncoding("utf-8");
		ResultDto rd = new ResultDto();
		String db_account =null;
		//查询acconut是否已经被注册
		String sql2 =null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		Connection conn2 = null;
		sql2 = "select * from user_table where account = ?";
		try {
			conn2 = MyJDBCUtil.getConnection();
			pstmt2 = conn2.prepareStatement(sql2);
			pstmt2.setString(1,account);
			rs2 = pstmt2.executeQuery();
			while(rs2.next()) {				
					//返回前端已经被注册
				dto.setMsg("此账户已经被注册");
				dto.setResult(203);
			    check=true;
			}
			
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}finally {
			try {
				MyJDBCUtil.releaseResource(conn2, pstmt2, rs2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//2.jdbc
		
		if(!check) {
		
		String sql = "insert into user_table values(0,?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = MyJDBCUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setBoolean(2, sex);
			pstmt.setInt(3, age);
			pstmt.setString(4, avatarUrl);
			pstmt.setBoolean(5, false);
			pstmt.setString(6, account);
			pstmt.setString(7, password);
			
			int rows = pstmt.executeUpdate();
			//用来封装结果的对象
			//ResultDto rd = new ResultDto();
			if(rows>0) {
				//插入成功
				dto.setMsg("注册成功");
				dto.setResult(201);
			}else {
				//插入失败
				dto.setMsg("注册失败");
				dto.setResult(202);
			}
			//向前端发送json {result: true,msg:"成功"}
//			Gson gson = new Gson();
//			response.setCharacterEncoding("utf-8");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				MyJDBCUtil.releaseResource(conn, pstmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(dto);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
