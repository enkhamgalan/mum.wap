package usa.edu.mum.wap.controller;

import com.google.gson.Gson;
import usa.edu.mum.wap.model.Team;
import usa.edu.mum.wap.model.TeamMember;
import usa.edu.mum.wap.utility.TaskDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: Enkh A Erdenebat
 * Date: 2017-11-20
 * Time: 21:37
 */
@WebServlet(name = "TeamMemberServlet", urlPatterns = {"/member"})
public class TeamMemberServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            TaskDB taskDB = new TaskDB();
            Gson gson = new Gson();
            TeamMember teamMember = gson.fromJson(request.getReader(), TeamMember.class);
            if (taskDB.addMemberToTeam(teamMember)) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("Failed to add the team member.");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write(e.getMessage());
        }
    }
}
