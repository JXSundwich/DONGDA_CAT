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
import util.MyJDBCUtil;

/**
 * Servlet implementation class get_new_catlist
 */
@WebServlet("/get_new_catlist")
public class get_new_catlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public get_new_catlist() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String json = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String type = request.getParameter("type");
		
		try {
			conn = MyJDBCUtil.getConnection();
			String sql = "select * from temp_cat_table order by catID desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// 2.封装成装着多个arend对象的集合
			List<Cat> cats = new ArrayList<Cat>();
			while (rs.next()) {
				Cat cat = new Cat();
				// 封装对象
				cat.setCatID(rs.getInt("catID"));
				cat.setCharacterristicValue(rs.getString("characterristicValue"));
				cat.setColor(rs.getString("color"));
				cat.setName(rs.getString("name"));
				cat.setSex(rs.getBoolean("sex"));
				cat.setSpecies(rs.getString("species"));
				cat.setZone(rs.getString("zone"));
				
				// 将对象添加到集合中
				cats.add(cat);

			}

			// 3.转换成json字符串
			Gson gson = new Gson();
			json = gson.toJson(cats);

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
