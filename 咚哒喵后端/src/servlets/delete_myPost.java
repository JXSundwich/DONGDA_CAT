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
 * Servlet implementation class delete_myPost
 */
@WebServlet("/delete_myPost")
public class delete_myPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public delete_myPost() {
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
				//jdbc
				String sql = "delete from arend_table where creatorID = ? and arendID = ?";
				Connection conn = null;
				PreparedStatement pstmt = null;
				String sql2 = "delete from collect_table where arendID = ?";
				PreparedStatement pstmt2 = null;
				try {
					conn = MyJDBCUtil.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, userID);
					pstmt.setInt(2, arendID);
					int rows = pstmt.executeUpdate();
					//用来封装结果的对象
					if(rows>0) {
						pstmt2 = conn.prepareStatement(sql2);
						pstmt2.setInt(1, arendID);
						pstmt2.executeUpdate();
						dto.setMsg("删除成功");
						dto.setResult(201);
					}else {
						//删除失败
						//插入成功
						dto.setMsg("删除失败");
						dto.setResult(202);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						MyJDBCUtil.releaseResource(conn, pstmt, null);
						MyJDBCUtil.releaseResource(conn, pstmt2, null);
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
