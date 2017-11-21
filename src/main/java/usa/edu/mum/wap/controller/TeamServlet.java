package usa.edu.mum.wap.controller;

import com.google.gson.Gson;
import usa.edu.mum.wap.model.Team;
import usa.edu.mum.wap.utility.DBconnector;
import usa.edu.mum.wap.utility.TaskDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * User: Enkh A Erdenebat
 * Date: 2017-11-20
 * Time: 19:50
 */
@WebServlet(value = "/team")
public class TeamServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            TaskDB taskDB = new TaskDB();
            Gson gson = new Gson();
            List<Team> teamList = taskDB.getAllTeamList();
            out.write(gson.toJson(teamList));
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            TaskDB taskDB = new TaskDB();
            Gson gson = new Gson();
            Team team = gson.fromJson(request.getReader(), Team.class);
            Integer id = taskDB.createTeam(team);
            if (id != null) {
                team.setId(id);
                out.write(gson.toJson(team));
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("Failed to create the team.");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Integer id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
            Team team = new Team();
            team.setId(id);
            TaskDB taskDB = new TaskDB();
            if (taskDB.deleteTeam(team)) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("Failed to delete the team.");
            }
        } catch (NullPointerException npe) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("'id' is missing.");
        } catch (NumberFormatException nfe) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("'id' is missing.");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Gson gson = new Gson();
            Team team = gson.fromJson(request.getReader(), Team.class);
            TaskDB taskDB = new TaskDB();
            if (taskDB.editTeam(team)) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                out.write("Failed to edit the team.");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write(e.getMessage());
        }
    }

    @Override
    public void destroy() {
        DBconnector.getconnector().closeConnection();
        System.out.println("TeamServlet is destroying");
        super.destroy();
    }
}
