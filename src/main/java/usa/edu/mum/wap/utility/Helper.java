package usa.edu.mum.wap.utility;

import usa.edu.mum.wap.model.Task;
import usa.edu.mum.wap.model.Team;

public class Helper {

    public String insertTask(Task task , int Priority , int userID){
        return "'INSERT INTO task (TaskName,TaskPriority,TaskDate,TaskCategory,UserID)  " + "VALUES ("
                +task.getTask()+","
                +Priority+","
                +task.getDueDate()+","
                +task.getCategory()+","
                +userID+")'";
    }

    public String retrieveTasks(int userID){
        return "select * from task where UserID = "+userID + " order by TaskPriority DESC"  ;
    }


    /*public String insertTeam(Team team){
        return    "'INSERT INTO team (TeamName)  " + "VALUES ("+team.getTeamName()+")'";
    }*/

}
