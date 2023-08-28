package servlets;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dto.ResultDto;
import entity.Comment;
import entity.User;
import util.MyJDBCUtil;

/**
 * Servlet implementation class post_comment
 */
@WebServlet("/post_comment")
public class post_comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public post_comment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("resource")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int rows=0;
		
		int uid=Integer.parseInt(request.getParameter("userID")); 
		String content=request.getParameter("content");
		int aid=Integer.parseInt(request.getParameter("arendID"));
		
		//Comment com=new Comment();
		 ResultDto dto=new ResultDto();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MyJDBCUtil.getConnection();

			String sql = "select * from user_table where userID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uid);
			rs = pstmt.executeQuery();
			
			rs.next();
			
			String userav=rs.getString("avatarUrl");  
			String username=rs.getString("name");
			
			

			Timestamp time=new Timestamp(System.currentTimeMillis());
			
			sql="insert into comment_table(creatorID,arendID,content,createTime,creatorAvatar,creatorName) values(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,uid);
			pstmt.setInt(2,aid);
			pstmt.setString(3,content);
			pstmt.setTimestamp(4, time);
			pstmt.setString(5,userav);
			pstmt.setString(6,username);
			
			/*com.setArendID(aid);
			com.setContent(content);
			com.setCreatorAvatar(userav);
			com.setCreatorID(uid);
			com.setCreatorName(username);
			com.setCreatTime(time);*/
			
			
			rows = pstmt.executeUpdate();
			
			//System.out.println(rows);
			
			if(rows>0) {
				//插入成功
				dto.setMsg("发布成功");
				dto.setResult(201);
				sql="select commentNum from arend_table where arendID=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, aid);
				rs = pstmt.executeQuery();
				rs.next();
				
				int number=rs.getInt("commentNum");
				number++;
				//System.out.println(number);
				sql="update arend_table set commentNum=? where arendID=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,number);
				pstmt.setInt(2,aid);
				pstmt.execute();
			
			}else {
				//插入失败
				dto.setMsg("发布失败");
				dto.setResult(202);
			}
	        
		
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

	
	
	/*public static void main(String[] args) {
		String json = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MyJDBCUtil.getConnection();

			String sql = "select * from user_table where userID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			rs = pstmt.executeQuery();
			rs.next();
			
			String userav=rs.getString("avatarUrl");
			String username=rs.getString("name");

			Timestamp time=new Timestamp(System.currentTimeMillis());
			
			sql="insert into comment_table(creatorID,arendID,content,createTime,creatorAvatar,creatorName) values(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,12);
			pstmt.setInt(2,13);
			pstmt.setString(3,"你是猪吗");
			pstmt.setTimestamp(4, time);
			pstmt.setString(5,userav);
			pstmt.setString(6,username);
			
			
			int rows = pstmt.executeUpdate();
			if(rows>0) {
				//插入成功
				System.out.println("插入成功");
	
			}else {
				//插入失败
				System.out.println("插入失败");
			
			}
			
		
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
