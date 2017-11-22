package usa.edu.mum.wap.controller;

import com.google.gson.Gson;
import usa.edu.mum.wap.model.Task;
import usa.edu.mum.wap.utility.DBconnector;
import usa.edu.mum.wap.utility.Helper;
import usa.edu.mum.wap.utility.TaskDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@WebServlet(value = "/task")
public class TaskServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String msg="" ;

		PrintWriter out = response.getWriter();

		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null) {
			json = br.readLine();
		}

		Helper helper = new Helper();
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Task task = null;
		TaskDB db = new TaskDB();
		PreparedStatement query = null;
		try {

			String select = " TaskName=? and TaskDate = ? and TaskCategory = ? and user_UserID = ? and TaskPriority =? ";
			try {
				System.out.println(helper.selectTasks(select));
				query = DBconnector.getconnector().getconnection().prepareStatement(helper.selectTasks(select));
				query.setString(1, jsonObj.getString("task"));
				query.setString(2, jsonObj.getString("requiredBy"));
				query.setString(3, jsonObj.getString("category"));
				query.setString(4, jsonObj.getString("userID"));
				query.setString(5, jsonObj.getString("priority"));

			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (!db.checkTask(query)) {
				task = new Task(0, jsonObj.getString("task"), jsonObj.getString("requiredBy"),
						jsonObj.getString("category"), jsonObj.getString("userID"),
						Integer.parseInt(jsonObj.getString("priority")));
				db.insertTask(helper.insertTask(task));
				
				msg = "Task inserted";

			} else {
				msg= "there are tasks already saved!" ;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}


		out.print(msg);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			TaskDB taskDB = new TaskDB();
			Gson gson = new Gson();
			String teamId = request.getParameter("teamId");
			String userId = request.getParameter("userId");
			if (teamId != null && !teamId.isEmpty()) {
				Integer id = Integer.parseInt(teamId);
				List<Task> taskList = taskDB.getTaskListByTeamId(id);
				out.write(gson.toJson(taskList));
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
			} else if (userId != null && !userId.isEmpty()) {
				Integer id = Integer.parseInt(userId);
				List<Task> taskList = taskDB.getTaskListByUserId(id);
				out.write(gson.toJson(taskList));
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.write("'teamId' or 'userId' is missing.");
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.write(e.getMessage());
		}
	}

	@Override
	public void destroy() {
		DBconnector.getconnector().closeConnection();
		System.out.println("TaskServlet was destroying");
		super.destroy();
	}
}
