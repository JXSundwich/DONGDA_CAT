package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dto.ResultDto;
import util.MyJDBCUtil;

/**
 * Servlet implementation class post_sysinfo
 */
@WebServlet("/post_sysinfo")
public class post_sysinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public post_sysinfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
        int rows=0;
		
		String content=request.getParameter("content");
		
		//Comment com=new Comment();
		 ResultDto dto=new ResultDto();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MyJDBCUtil.getConnection();
						
			Timestamp time=new Timestamp(System.currentTimeMillis());
			
			String sql="insert into sysinfo_table(content,createTime) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,content);
			pstmt.setTimestamp(2,time);
			
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

}
