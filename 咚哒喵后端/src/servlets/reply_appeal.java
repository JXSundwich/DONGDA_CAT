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
import entity.Applymessage;
import util.MyJDBCUtil;

/**
 * Servlet implementation class reply_appeal
 */
@WebServlet("/reply_appeal")
public class reply_appeal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public reply_appeal() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("resource")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//获取传到后端的数据
		 ResultDto dto=new ResultDto();
		
		int applyMessageID = Integer.parseInt(request.getParameter("applyMessageID"));
		String result = request.getParameter("result");
		String type = request.getParameter("type");
		int ifagree = Integer.parseInt(request.getParameter("ifagree"));
		Applymessage amg = new Applymessage();
		//jdbc
		String sql = "update applymessage_table set result = ?, state = 1 where applyMessageID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql2 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		String sql3 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs3 = null;
		try {
			conn = MyJDBCUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, result);
			pstmt.setInt(2, applyMessageID);
			int rows = pstmt.executeUpdate();
			//用来封装结果的对象
			if(rows>0) {				
				//插入成功
				dto.setMsg("已成功处理");
				dto.setResult(201);
				if(type.equals("0") & ifagree==0) {
					
				}
				
				if(type.equals("0") & ifagree==1) {
					sql2 = "select * from applymessage_table where applyMessageID =?";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setInt(1, applyMessageID);
					rs2 = pstmt2.executeQuery();
					while(rs2.next()) {
						amg.setArendID(rs2.getInt("arendID"));
					}
					
					sql3 = "update arend_table set ifBoutique = ? where arendID = ?";
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setInt(1,1);
					pstmt3.setInt(2, amg.getArendID());
					pstmt3.execute();
					}
				}
				if(type.equals("1") & ifagree==0) {
					
				}
					
				if(type.equals("1") & ifagree==1) {
					sql2 = "select * from applymessage_table where applyMessageID = ?";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setInt(1,applyMessageID);
					rs2 = pstmt2.executeQuery();
					while(rs2.next()) {
						amg.setArendID(rs2.getInt("arendID"));
					}
					
					sql3 = "delete from arend_table where arendID = ?";
					pstmt3 = conn.prepareStatement(sql3);
					
					
					System.out.println("ArendID:"+amg.getArendID());
					
					pstmt3.setInt(1, amg.getArendID());
				    pstmt3.execute();
				}
				
			else {
				//插入失败
				//response.setStatus(202);
				dto.setMsg("处理失败");
				dto.setResult(202);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				MyJDBCUtil.releaseResource(conn, pstmt, null);
				MyJDBCUtil.releaseResource(conn, pstmt2, rs2);
				MyJDBCUtil.releaseResource(conn, pstmt3, rs3);
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
