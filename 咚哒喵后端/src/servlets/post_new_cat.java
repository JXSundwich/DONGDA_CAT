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

import dto.ResultDto;
import util.MyJDBCUtil;

/**
 * Servlet implementation class post_new_cat
 */
@WebServlet("/post_new_cat")
public class post_new_cat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public post_new_cat() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int catID=Integer.parseInt(request.getParameter("catID"));
		String ifPass=request.getParameter("ifPass");
		ResultDto dto=new ResultDto();
		
		String json = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		
		String sql=null;
		try {
			conn = MyJDBCUtil.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(ifPass.equals("Y")) {
			
			try {
				sql = "select * from temp_cat_table where catID=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, catID);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					sql="insert into cat_table(species,name,sex,color,zone,characterristicValue) values(?,?,?,?,?,?)";
					pstmt1=conn.prepareStatement(sql);
					pstmt1.setString(1,rs.getString("species"));
					pstmt1.setString(2,rs.getString("name"));
					pstmt1.setBoolean(3,rs.getBoolean("sex"));
					pstmt1.setString(4,rs.getString("color"));
					pstmt1.setString(5,rs.getString("zone"));
					pstmt1.setString(6,rs.getString("characterristicValue"));
					
					int rows=pstmt1.executeUpdate();
					
					if(rows>0)
					{
						dto.setMsg("提交成功");
						dto.setResult(201);
					}
					else {
						dto.setMsg("提交失败");
						dto.setResult(202);
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		else {}
		
		//删除temp表里面的内容
		
		try {
			sql = "delete from temp_cat_table where catID = ?";
			pstmt2=conn.prepareStatement(sql);
			pstmt2.setInt(1,catID);
			pstmt2.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				MyJDBCUtil.releaseResource(conn, pstmt, rs);
				MyJDBCUtil.releaseResource(conn, pstmt2, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Gson gson = new Gson();
		json = gson.toJson(dto);
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
