package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dto.ResultDto;
import util.MyJDBCUtil;

/**
 * Servlet implementation class collect_arend
 */
@WebServlet("/collect_arend")
public class collect_arend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public collect_arend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				//response.getWriter().append("Served at: ").append(request.getContextPath());
				//获取传到后端的数据
		        ResultDto dto=new ResultDto();
		        
				int userID = Integer.parseInt(request.getParameter("userID"));
				int arendID = Integer.parseInt(request.getParameter("arendID"));
				Timestamp createTime =new Timestamp(System.currentTimeMillis());
				//jdbc
				String sql = "insert into collect_table(userID,arendID,createTime) values(?,?,?)";
				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = MyJDBCUtil.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, userID);
					pstmt.setInt(2, arendID);
					pstmt.setTimestamp(3, createTime);
					int rows = pstmt.executeUpdate();
					//用来封装结果的对象
					if(rows>0) {				
						
						
						//插入成功
						//response.setStatus(201);
						dto.setMsg("收藏成功");
						dto.setResult(201);
						
					}else {
						//插入失败
						dto.setMsg("收藏失败");
						dto.setResult(202);
					}
					
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
