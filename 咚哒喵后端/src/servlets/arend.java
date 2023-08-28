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

import entity.Arend;
import util.MyJDBCUtil;

/**
 * Servlet implementation class arend
 */
@WebServlet("/arend")
public class arend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public arend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		// 1.去数据库查询所有的动态贴
		String json = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		
		String type = request.getParameter("type");
		
		try {
			conn = MyJDBCUtil.getConnection();
			String sql = "select * from arend_table where type = ? order by createTime desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, type);
			rs = pstmt.executeQuery();
			// 2.封装成装着多个arend对象的集合
			List<Arend> ards = new ArrayList<Arend>();
			while (rs.next()) {
				Arend ard = new Arend();
				// 封装对象
				ard.setArendID(rs.getInt("arendID"));;
				ard.setCreatorID(rs.getInt("creatorID"));
				ard.setCreatorAvatar(rs.getString("creatorAvatar"));
				ard.setCreatorName(rs.getString("creatorName"));
				String tags = rs.getString("arendTag");
				String[] arendTag = tags.split(",");
				//System.out.println(arendTag[0]);
				ard.setArendTag(arendTag);
				ard.setArendContent(rs.getString("arendContent"));
				String urls = rs.getString("imageUrl");
				String[] imageurl = urls.split(",");
				ard.setImageUrl(imageurl);
				ard.setLike(rs.getInt("likeNum"));
				ard.setCreateTime(rs.getTimestamp("createTime"));
				ard.setType(rs.getString("type"));
				ard.setIfBoutique(rs.getBoolean("ifBoutique"));
				ard.setCommentNum(rs.getInt("commentNum"));
				// 将对象添加到集合中
				ards.add(ard);

			}

			// 3.转换成json字符串
			Gson gson = new Gson();
			json = gson.toJson(ards);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				MyJDBCUtil.releaseResource(conn, pstmt, rs);
				MyJDBCUtil.releaseResource(conn, pstmt2, rs2);
				MyJDBCUtil.releaseResource(conn, pstmt3, rs3);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// 4.响应给前端
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		response.getWriter().write(json);
		//System.out.println(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
