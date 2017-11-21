package usa.edu.mum.wap.model;

public class Task {

    private int id;
    private String task;
    private String requiredBy;
    private String category;
    private String userID;
    private int priority;

    public Task(int id, String task, String dueDate, String category, String userId, int priority) {
        this.id = id;
        this.task = task;
        this.requiredBy = dueDate;
        this.category = category;
        this.userID = userId;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDueDate() {
        return requiredBy;
    }

    public void setDueDate(String dueDate) {
        this.requiredBy = dueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getUserId() {
        return userID;
    }

    public void setUserId(String userId) {
        this.userID = userId;
    }
}
