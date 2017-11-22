package usa.edu.mum.wap.utility;

import usa.edu.mum.wap.model.Task;
import usa.edu.mum.wap.model.Team;

public class Helper {

    public static String insertTask(Task task){
        return "INSERT INTO task (TaskName,TaskPriority,TaskDate,TaskCategory,user_UserID)  " + "VALUES ("
        		+'"'+task.getTask()+'"'+","
        		+'"'+task.getPriority()+'"'+","
        		+'"'+task.getDueDate()+'"'+","
        		+'"'+task.getCategory()+'"'+","
        		+'"'+task.getUserId()+'"'+")";
    }

    public String selectTasks(String condition){
        return "select * from task where " + condition;
    }

    public String retrieveTask(int userID){
        return "select * from task where UserID = "+userID + " or true order by TaskPriority DESC"  ;
    }

    public String updateTask(Task task){
        return  "update task set TaskName="+task.getTask()
                +" ,TaskPriority="+task.getPriority()
                +" ,TaskDate="+task.getDueDate()
                +" ,TaskCategory="+task.getCategory()
                +" where TaskID="+task.getId();
    }



}
