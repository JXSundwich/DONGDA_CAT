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

import entity.Collect;
import entity.Collectset;
import util.MyJDBCUtil;

/**
 * Servlet implementation class collect
 */
@WebServlet("/collect")
public class collect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public collect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				// TODO Auto-generated method stub
				//response.getWriter().append("Served at: ").append(request.getContextPath());
				//String creatorId = request.getParameter("creatorID");
				// 1.去数据库查询
						String json = null;
						Connection conn = null;
						PreparedStatement pstmt = null;
						ResultSet rs = null;
						PreparedStatement pstmt2 = null;
						ResultSet rs2 = null;
						int userID = Integer.parseInt(request.getParameter("userID"));
						try {
							conn = MyJDBCUtil.getConnection();
							String sql = "select * from collect_table where userID = ? order by createTime desc";
							pstmt = conn.prepareStatement(sql);
							pstmt.setInt(1,userID);
							rs = pstmt.executeQuery();
							// 2.封装成装着多个对象的集合
							List<Collect> css = new ArrayList<Collect>();
							while (rs.next()) {
								Collect cs = new Collect();
								// 封装对象
								cs.setCollectID(rs.getInt("collectID"));
								cs.setArendID(rs.getInt("arendID"));
								int arendID = rs.getInt("arendID");
								cs.setCreateTime(rs.getTimestamp("createTime"));
								cs.setUserID(rs.getInt("userID"));
								String sql2 = "select * from arend_table where arendID = ? ";
								pstmt2 = conn.prepareStatement(sql2);
								pstmt2.setInt(1,arendID);
								rs2= pstmt2.executeQuery();
								if(rs2.next())
								{
									cs.setCreatorID(rs2.getInt("creatorID"));
									cs.setCreatorAvatar(rs2.getString("creatorAvatar"));
									cs.setCreatorName(rs2.getString("creatorName"));
									String tags = rs2.getString("arendTag");
									String[] arendTag = tags.split(",");
									//System.out.println(arendTag[0]);
									cs.setArendTag(arendTag);
									cs.setArendContent(rs2.getString("arendContent"));
									String urls = rs2.getString("imageUrl");
									String[] imageurl = urls.split(",");
									cs.setImageUrl(imageurl);
									cs.setLike(rs2.getInt("likeNum"));
									cs.setArendcreateTime(rs2.getTimestamp("createTime"));
									cs.setType(rs2.getString("type"));
									cs.setIfBoutique(rs2.getInt("ifBoutique"));
									cs.setCommentNum(rs2.getInt("commentNum"));
								}
								// 将对象添加到集合中
								css.add(cs);

							}

							// 3.转换成json字符串
							Gson gson = new Gson();
							json = gson.toJson(css);

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
