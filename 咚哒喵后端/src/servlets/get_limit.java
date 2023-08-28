package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entity.User;
import util.MyJDBCUtil;

/**
 * Servlet implementation class modify_userinfo
 */
@WebServlet("/get_limit")
public class get_limit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public get_limit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		// 1.去数据库查询
				String json = null;
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				boolean limit=false;
				int userID=Integer.parseInt(request.getParameter("userID"));
				try {
					conn = MyJDBCUtil.getConnection();
					String sql = "select * from user_table where userID = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1,userID);
					rs = pstmt.executeQuery();
					// 2.封装成装着多个对象的集合
					while(rs.next()) {
					if(rs.getBoolean("limit"))
						limit=true;
					else
						limit=false;
					}

					// 3.转换成json字符串
					Gson gson = new Gson();
					json = gson.toJson(limit);

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

				// 4.响应给前端
				
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json");
				response.getWriter().write(json);
				System.out.println(json.toString());
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
