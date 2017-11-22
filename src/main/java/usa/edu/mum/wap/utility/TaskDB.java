package usa.edu.mum.wap.utility;

import usa.edu.mum.wap.model.Task;
import usa.edu.mum.wap.model.Team;
import usa.edu.mum.wap.model.TeamMember;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Enkh A Erdenebat
 * Date: 2017-11-20
 * Time: 21:15
 */
public class TaskDB extends Database {

    private String url;
    private String username;
    private String password;

    public TaskDB(String url, String username, String password) {
        super();
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public TaskDB() {
        super();
    }

    @Override
    void connect() throws Exception {
        conn = DBconnector.getconnector().getconnection();
    }

    private void checkConn() throws Exception {
        if (conn == null || conn.isClosed()) {
            connect();
        }
    }

    public Integer createTeam(Team team) {
        Integer ret = null;
        final String sql = "INSERT INTO team (TeamName) VALUES (?)";
        PreparedStatement ps = null;
        try {
            checkConn();
            ps = preparedStatement(Statement.RETURN_GENERATED_KEYS, sql, team.getName());
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    ret = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
        }
        return ret;
    }

    public boolean deleteTeam(Team team) {
        boolean ret = false;
        final String sql = "DELETE FROM team WHERE TeamID = ?";
        PreparedStatement ps = null;
        try {
            checkConn();
            ps = preparedStatement(sql, team.getId());
            if (ps.executeUpdate() > 0) {
                ret = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
        }
        return ret;
    }

    public boolean editTeam(Team team) {
        boolean ret = false;
        final String sql = "UPDATE team SET TeamName = ? WHERE TeamID = ? ";
        PreparedStatement ps = null;
        try {
            checkConn();
            ps = preparedStatement(sql, team.getName(), team.getId());
            if (ps.executeUpdate() > 0) {
                ret = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
        }
        return ret;
    }

    public boolean addMemberToTeam(TeamMember teamMember) {
        boolean ret = false;
        final String sql = "INSERT INTO teammembers (Team_TeamID, User_id) VALUES (?, ?)";
        PreparedStatement ps = null;
        try {
            checkConn();
            ps = preparedStatement(sql, teamMember.getId(), teamMember.getUserId());
            if (ps.executeUpdate() > 0) {
                ret = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
        }
        return ret;
    }
    
    public void insertTask(String query) {
    	 try {
             
             PreparedStatement  ps = DBconnector.getconnector().getconnection().prepareStatement(query);
             ps.execute();
           
         } catch (Exception e) {
             e.printStackTrace();
         }
    	
    }


    public boolean checkTask(PreparedStatement query){
        List<Task> ret = null;
        boolean result = false;
        ResultSet rs = null;
        try {
            checkConn();
            rs = query.executeQuery();
            if(rs.first()){
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //close(rs);
        }
        return result;
    }



    public List<Task> getAlltasks(){
        List<Task> ret = null;
        final String sql = "SELECT * FROM task ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                LocalDate dueDate = rs.getDate("TaskDate").toLocalDate();
                Task task = new Task(
                        rs.getInt("TaskID"),
                        rs.getString("TaskName"),
                        dueDate.toString(),
                        rs.getString("TaskCategory"),
                        rs.getString("user_UserID"),
                        rs.getInt("TaskPriority")
                );
                ret.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }
    
    public List<Task> getTaskListByUserId(Integer userId) {
        List<Task> ret = null;
        final String sql = "SELECT * FROM task WHERE user_UserID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                LocalDate dueDate = rs.getDate("TaskDate").toLocalDate();
                Task task = new Task(
                        rs.getInt("TaskID"),
                        rs.getString("TaskName"),
                        dueDate.toString(),
                        rs.getString("TaskCategory"),
                        rs.getString("user_UserID"),
                        rs.getInt("TaskPriority")
                );
                ret.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public List<Task> getTaskListByTeamId(Integer teamId) {
        List<Task> ret = null;
        final String sql = "SELECT A.* FROM task A RIGHT JOIN teammembers B on A.user_UserID = B.User_id " +
                "WHERE B.Team_TeamID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, teamId);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                LocalDate dueDate = rs.getDate("TaskDate").toLocalDate();
                Task task = new Task(
                        rs.getInt("TaskID"),
                        rs.getString("TaskName"),
                        dueDate.toString(),
                        rs.getString("TaskCategory"),
                        rs.getString("user_UserID"),
                        rs.getInt("TaskPriority")
                );
                ret.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public List<Team> getAllTeamList() {
        List<Team> ret = null;
        final String sql = "SELECT * FROM team";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                Team team = new Team();
                team.setId(rs.getInt("TeamID"));
                team.setName(rs.getString("TeamName"));
                team.setTeamMemberList(getAllTeamMemberList(rs.getInt("TeamID")));
                ret.add(team);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    private List<TeamMember> getAllTeamMemberList(int teamId) {
        List<TeamMember> ret = null;
        final String sql = "SELECT * FROM teammembers WHERE Team_TeamID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, teamId);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                TeamMember teamMember = new TeamMember();
                teamMember.setId(rs.getInt("Team_TeamId"));
                teamMember.setUserId(rs.getInt("User_id"));
                ret.add(teamMember);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

}
