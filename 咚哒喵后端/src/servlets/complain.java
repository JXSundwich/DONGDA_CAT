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
import entity.Applymessage;
import util.MyJDBCUtil;

/**
 * Servlet implementation class complain
 */
@WebServlet("/complain")
public class complain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public complain() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int uid=Integer.parseInt(request.getParameter("userID")); 
		String content=request.getParameter("content");
		int aid=Integer.parseInt(request.getParameter("arendID"));
		
		ResultDto dto=new ResultDto();
		int rows=-1;
		
		String json = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MyJDBCUtil.getConnection();
			
			Timestamp time=new Timestamp(System.currentTimeMillis());
			
			String sql="insert into applymessage_table(creatorID,arendID,type,content,state,createTime,result) values(?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,uid);
			pstmt.setInt(2,aid);
			pstmt.setString(3,"complain");
			pstmt.setString(4, content);
			pstmt.setBoolean(5,false);
			pstmt.setTimestamp(6,time);
			pstmt.setString(7,"审核中");
			
			/*msg.setArendID(aid);
			msg.setCreatorID(uid);
			msg.setContent(content);
			msg.setCreateTime(time);
			msg.setState(false);
			msg.setType("complain");*/
			
			
			rows = pstmt.executeUpdate();
			if(rows>0) {
				dto.setMsg("提交成功");
				dto.setResult(201);
	
			}else {
				dto.setMsg("提交失败");
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
		
		  Gson gson = new Gson(); json = gson.toJson(dto);
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
