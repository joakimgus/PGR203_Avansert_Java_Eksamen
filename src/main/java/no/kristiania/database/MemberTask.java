package no.kristiania.database;

public class MemberTask {
    private String memberName, taskTitle, taskDescription;
    private Integer memberTaskId;

    public void setMemberTaskId(Integer memberTaskId) {
        this.memberTaskId = memberTaskId;
    }

    public Integer getMemberTaskId() {
        return memberTaskId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

}
