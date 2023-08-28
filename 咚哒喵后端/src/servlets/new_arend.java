package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * Servlet implementation class new_arend
 */
@WebServlet("/new_arend")
public class new_arend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public new_arend() {
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
		int creatorID = Integer.parseInt(request.getParameter("creatorID"));
		String arendTag = request.getParameter("arendTag");
		String arendContent=request.getParameter("arendContent");
		String imageUrl = request.getParameter("imageUrl");
		String type = request.getParameter("type");
		String creatorAvatar=request.getParameter("creatorAvatar");
		String creatorName=request.getParameter("creatorName");
		
		//2.jdbc
		String sql = "insert into arend_table(creatorID,arendTag,"
				+ "arendContent,ifBoutique,createTime,imageUrl,likeNum,"
				+ "commentNum,type,creatorAvatar,creatorName) values(?,?,?,?,?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Timestamp time=new Timestamp(System.currentTimeMillis());
			conn = MyJDBCUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,creatorID);
			pstmt.setString(2, arendTag);
			pstmt.setString(3,arendContent);
			pstmt.setBoolean(4,false);
			pstmt.setTimestamp(5, time);
			pstmt.setString(6,imageUrl);
			pstmt.setInt(7,0);
			pstmt.setInt(8,0);
			pstmt.setString(9, type);
			pstmt.setString(10,creatorAvatar);
			pstmt.setString(11,creatorName);
			
			int rows = pstmt.executeUpdate();
			//用来封装结果的对象
			
			if(rows>0) {
				//插入成功
				rd.setMsg("发布成功");
				rd.setResult(201);
			}else {
				//插入失败
				rd.setMsg("发布失败");
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
