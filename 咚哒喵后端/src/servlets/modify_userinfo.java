package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * Servlet implementation class modify_userinfo
 */
@WebServlet("/modify_userinfo")
public class modify_userinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public modify_userinfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		// TODO Auto-generated method stub

		request.setCharacterEncoding("utf-8");
		
		ResultDto rd = new ResultDto();
		
		int userID = Integer.parseInt(request.getParameter("userID"));
		String name = request.getParameter("name");
		boolean sex = Boolean.parseBoolean(request.getParameter("sex"));
		int age = Integer.parseInt(request.getParameter("age"));;
		String avatarUrl = request.getParameter("avatarUrl");
		int limit = Integer.parseInt(request.getParameter("limit"));
		String openID = request.getParameter("openID");
		//jdbc
		String sql = "replace into user_table values(?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = MyJDBCUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			pstmt.setString(2, name);
			pstmt.setBoolean(3, sex);
			pstmt.setInt(4, age);
			pstmt.setString(5, avatarUrl);
			pstmt.setInt(6, limit);
			pstmt.setString(7, openID);
					
			int rows = pstmt.executeUpdate();
			//用来封装结果的对象
	
			if(rows>0) {
				rd.setMsg("修改成功");
				rd.setResult(201);
			}else {
				//插入失败
				rd.setMsg("修改失败");
				rd.setResult(202);
			}
			//向前端发送json {result: true,msg:"成功"}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				util.MyJDBCUtil.releaseResource(conn, pstmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Gson gson = new Gson();
		String json = gson.toJson(rd);
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
