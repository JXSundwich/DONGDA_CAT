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
import entity.Cat;
import entity.Comment;
import util.MyJDBCUtil;

/**
 * Servlet implementation class search
 * 用户通过标签搜索动态帖或者猫咪
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type=request.getParameter("type");
		String keyword="%"+request.getParameter("keyword")+"%";
		
				
		// 1.去数据库查询
		String json = null;
		Connection conn = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		String sql =null;
		try {
			conn = MyJDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		if(type.equals("A"))//代表搜索动态帖
			{try {
				 sql="select * from arend_table where arendTag like ?";
				 pstmt = conn.prepareStatement(sql);
				 pstmt.setString(1, keyword);
				 rs = pstmt.executeQuery();
				 // 2.封装成装着多个Comment对象的集合
				 List<Arend> list = new ArrayList<Arend>();
				 while (rs.next()) {
					Arend arend = new Arend();
					// 封装对象
					arend.setArendContent(rs.getString("arendContent"));
					arend.setArendID(rs.getInt("arendID"));
					
					String tags = rs.getString("arendTag");
					String[] arendTag = tags.split(",");
					arend.setArendTag(arendTag);
					
					String urls = rs.getString("imageUrl");
					String[] imageurl = urls.split(",");
					arend.setImageUrl(imageurl);
					
					arend.setCommentNum(rs.getInt("commentNum"));
					arend.setCreateTime(rs.getTimestamp("createTime"));
					arend.setCreatorAvatar(rs.getString("creatorAvatar"));
					arend.setCreatorID(rs.getInt("creatorID"));
					arend.setCreatorName(rs.getString("creatorName"));
					arend.setIfBoutique(rs.getBoolean("ifBoutique"));
					arend.setLike(rs.getInt("likeNum"));
					arend.setType(rs.getString("type"));
				
					// 将对象添加到集合中
					list.add(arend);
	
				}
				
				 
				// 3.转换成json字符串
				Gson gson = new Gson();
				json = gson.toJson(list);
	
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
			}
		
		
		else {
			try {
				 sql="select * from cat_table where zone like ?";
				 pstmt = conn.prepareStatement(sql);
				 pstmt.setString(1, keyword);
				 rs = pstmt.executeQuery();
				 // 2.封装成装着多个Cat对象的集合
				 List<Cat> list = new ArrayList<Cat>();
				 while (rs.next()) {
					Cat cat= new Cat();
					// 封装对象
					cat.setCatID(rs.getInt("catID"));
					cat.setColor(rs.getString("color"));
					cat.setName(rs.getString("name"));
					cat.setSex(rs.getBoolean("sex"));
					cat.setSpecies(rs.getString("species"));
					cat.setZone(rs.getString("zone"));
					cat.setCharacterristicValue(rs.getString("characterristicValue"));
				
					// 将对象添加到集合中
					list.add(cat);
	
				}
				
				 
				// 3.转换成json字符串
				Gson gson = new Gson();
				json = gson.toJson(list);
	
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
