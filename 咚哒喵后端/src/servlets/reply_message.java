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

import entity.Applymessage;
import entity.Arend;
import util.MyJDBCUtil;

/**
 * Servlet implementation class reply_message
 */
@WebServlet("/reply_message")
public class reply_message extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public reply_message() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// 1.去数据库查询所有的动态贴
				String json = null;
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				int userID = Integer.parseInt(request.getParameter("userID"));
				
				try {
					conn = MyJDBCUtil.getConnection();
					String sql = "select * from applymessage_table where creatorID  = ? order by createTime desc";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1,userID);
					rs = pstmt.executeQuery();
					// 2.封装成装着多个arend对象的集合
					List<Applymessage> apps = new ArrayList<Applymessage>();
					while (rs.next()) {
						
						Applymessage app=new Applymessage();
						// 封装对象
						
						app.setApplyMessageID(rs.getInt("applyMessageID"));
						app.setArendID(rs.getInt("arendID"));
						app.setContent(rs.getString("content"));
						app.setCreateTime(rs.getTimestamp("createTime"));
						app.setCreatorID(rs.getInt("creatorID"));
						app.setResult(rs.getString("result"));
						app.setState(rs.getBoolean("state"));
						app.setType(rs.getString("type"));
						// 将对象添加到集合中
						apps.add(app);

					}

					// 3.转换成json字符串
					Gson gson = new Gson();
					json = gson.toJson(apps);

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
