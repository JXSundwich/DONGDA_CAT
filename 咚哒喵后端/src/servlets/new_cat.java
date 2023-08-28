package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dto.ResultDto;
import util.MyJDBCUtil;

/**
 * Servlet implementation class new_cat
 */
@WebServlet("/new_cat")
public class new_cat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public new_cat() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				request.setCharacterEncoding("utf-8");
				ResultDto rd = new ResultDto();
				// 1.获取传到后端的参数
				String species = request.getParameter("species");
				String name = request.getParameter("name");
				Boolean sex = Boolean.parseBoolean(request.getParameter("sex"));
				String color = request.getParameter("color");
				String zone = request.getParameter("zone");
				String characterristicValue=request.getParameter("characterristicValue");
				
				//2.jdbc
				String sql = "insert into temp_cat_table(species,name,sex,color,zone,characterristicValue) values(?,?,?,?,?,?)";
				Connection conn = null;
				PreparedStatement pstmt = null;
				
				try {
					conn = MyJDBCUtil.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, species);
					pstmt.setString(2,name);
					pstmt.setBoolean(3,sex);
					pstmt.setString(4,color);
					pstmt.setString(5,zone);
					pstmt.setString(6, characterristicValue);
					
					int rows = pstmt.executeUpdate();
					//用来封装结果的对象
					
					if(rows>0) {
						//插入成功
						rd.setMsg("提交成功");
						rd.setResult(201);
					}else {
						//插入失败
						rd.setMsg("提交失败");
						rd.setResult(202);
					}
					//向前端发送json {result: true,msg:"成功"}
	
					
					
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
				
				Gson gson = new Gson();
				String json = gson.toJson(rd);
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
