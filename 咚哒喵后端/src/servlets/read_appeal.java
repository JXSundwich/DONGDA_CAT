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
import entity.Cat;
import util.MyJDBCUtil;

/**
 * Servlet implementation class read_appeal
 */
@WebServlet("/read_appeal")
public class read_appeal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public read_appeal() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String json = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = MyJDBCUtil.getConnection();
			String sql = "select * from applymessage_table where state = 0 order by createTime desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// 2.封装成装着多个arend对象的集合
			List<Applymessage> amgs = new ArrayList<Applymessage>();
			while (rs.next()) {
				Applymessage amg = new Applymessage();
				// 封装对象
				amg.setApplyMessageID(rs.getInt("applyMessageID"));
				amg.setArendID(rs.getInt("arendID"));
				amg.setContent(rs.getString("content"));
				amg.setCreateTime(rs.getTimestamp("createTime"));
				amg.setCreatorID(rs.getInt("creatorID"));
				amg.setState(rs.getBoolean("state"));
				amg.setType(rs.getString("type"));
				amg.setResult(rs.getString("result"));
				// 将对象添加到集合中
				amgs.add(amg);

			}

			// 3.转换成json字符串
			Gson gson = new Gson();
			json = gson.toJson(amgs);

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
