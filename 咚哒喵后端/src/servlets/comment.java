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
import entity.*;
import util.MyJDBCUtil;

/**
 * Servlet implementation class comment
 */
@WebServlet("/comment")
public class comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public comment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int cid=Integer.parseInt(request.getParameter("arendID")); 
		// 1.去数据库查询所有的评论
		String json = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MyJDBCUtil.getConnection();

			String sql = "select * from comment_table where arendID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
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

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
