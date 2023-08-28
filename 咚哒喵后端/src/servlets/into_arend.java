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

import entity.Arend;
import util.MyJDBCUtil;

/**
 * Servlet implementation class into_arend
 */
@WebServlet("/into_arend")
public class into_arend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public into_arend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		// 1.获取传到后端的参数
		int userID = Integer.parseInt(request.getParameter("userID"));
		int arendID = Integer.parseInt(request.getParameter("arendID"));
		
		Arend ard=new Arend();
		
		//2.jdbc
		String sql = "select * from arend_table where arendID=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		try {
			conn = MyJDBCUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,arendID);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
			ard.setArendID(rs.getInt("arendID"));
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
			//rs.last();//对rs的操作应马上操作,操作完后再从数据库得到rst,再对rst操作
			
			String sql2 = "select * from like_table where userID = ? and arendID =?";
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setInt(1,userID);
			pstmt2.setInt(2, arendID);
			rs2 = pstmt2.executeQuery();
			if(rs2.next()){
				ard.setIfLike(true);
				}
			else {
				ard.setIfLike(false);
			}
			System.out.println("是否点赞："+ard.getIfLike());
			//rs2.last();
			
			String sql3 = "select * from collect_table where userID = ? and arendID = ?";
			pstmt3 = conn.prepareStatement(sql3);
			pstmt3.setInt(1, userID);
			pstmt3.setInt(2, arendID);
			rs3 = pstmt3.executeQuery();
			if(rs3.next()){
				ard.setIfCollect(true);
				}
			else {
				ard.setIfCollect(false);
			}
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
		//相应给前端
		Gson gson = new Gson();
		String json = gson.toJson(ard);
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
