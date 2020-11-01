package no.kristiania.database;

public class MemberTask {
    private String member_name, task_title, task_description;
    private Integer memberTaskId;

    public void setMemberTaskId(Integer memberTaskId) {
        this.memberTaskId = memberTaskId;
    }

    public Integer getMemberTaskId() {
        return memberTaskId;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }

}
