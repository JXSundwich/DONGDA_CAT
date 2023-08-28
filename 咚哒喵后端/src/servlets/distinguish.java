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
import util.Distinguish;

/**
 * Servlet implementation class distinguish
 */
@WebServlet("/distinguish")
public class distinguish extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public distinguish() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//String photoFolder1 ="D:/Eclipse/mycat1/src/python";
		//String photoFolder2="D:/Eclipse/mycat1/src/python/resualt.log";
		
		
		String photoFolder1="C:/distinguish";
		String photoFolder2="C:/distinguish/resualt.log";
		
		System.out.println("文件路径："+photoFolder1+photoFolder2);
		
		String pypath = photoFolder1;//py文件的路径
		String logpath = photoFolder2;//logpath文件的路径（在发来的压缩包里面）
		
		String imgurl = request.getParameter("imgurl");
		String json = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = MyJDBCUtil.getConnection();
			String sql = "select * from cat_table ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// 2.封装成装着多个对象的集合
			List<Cat> cts = new ArrayList<Cat>();
			while (rs.next()) {
				String imgFromDb = rs.getString("characterristicValue");
				
				Distinguish.docmd(pypath,imgurl,imgFromDb);
				
				String result = Distinguish.getlog(logpath);
				
				System.out.println(result);
				
				if(result.equals("1"))//识别正确
				{
					Cat ct = new Cat();
					ct.setCatID(rs.getInt("catID"));
					ct.setCharacterristicValue(imgFromDb);
					ct.setColor(rs.getString("color"));
					ct.setName(rs.getString("name"));
					ct.setSpecies(rs.getString("species"));
					ct.setZone(rs.getString("zone"));
					cts.add(ct);
				}
				
			}
			// 3.转换成json字符串
			Gson gson = new Gson();
			json = gson.toJson(cts);

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
