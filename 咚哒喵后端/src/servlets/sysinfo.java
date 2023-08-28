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
import entity.Sysinfo;
import util.MyJDBCUtil;

/**
 * Servlet implementation class sysinfo
 */
@SuppressWarnings("unused")
@WebServlet("/sysinfo")
public class sysinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sysinfo() {
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
				ResultSet rs = null;
				ResultSet rs2 = null;

				
				try {
					// 1.链接数据库
					conn = MyJDBCUtil.getConnection();
					String sql = "select * from sysinfo_table order by createTime desc";
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					// 2.封装成装着多个对象的集合
					List<Sysinfo> sifs = new ArrayList<Sysinfo>();
					while (rs.next()) {
						Sysinfo sif =new Sysinfo();
						// 封装对象
						sif.setSysinfoID(rs.getInt("sysinfoID"));
						sif.setCreatorID(rs.getInt("creatorID"));
						sif.setCreateTime(rs.getString("createTime"));
						sif.setContent(rs.getString("content"));
						
						// 将对象添加到集合中
						sifs.add(sif);

					}

					// 3.转换成json字符串
					Gson gson = new Gson();
					json = gson.toJson(sifs);

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
