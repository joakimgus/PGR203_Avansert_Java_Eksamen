package no.kristiania.database;

public class MemberTask {
    private String memberName;
    private String taskTitle;
    private Integer memberTaskId;
    private String title;

    public String getMemberName() {
        return memberName;
    }

    public String setMemberName(String memberName) {
        this.memberName = memberName;
        return memberName;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
        return taskTitle;
    }

    public Integer getMemberTaskId() {return memberTaskId;}

    public void setMemberTaskId(Integer memberTaskId) {
        this.memberTaskId = memberTaskId;
    }

    public String getTitle() {
        return title;
    }

    public String setTitle(String title) {
        this.title = title;
        return title;
    }
}
