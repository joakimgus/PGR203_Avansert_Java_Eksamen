package no.kristiania.database;

public class MemberTask {
    private String title;
    private String statusName;
    private Integer id;
    private Integer statusId;

    public String getTitle() {
        return title;
    }

    public String setTitle(String title) {
        this.title = title;
        return title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public String setStatusName(String statusName) {
        this.statusName = statusName;
        return statusName;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }
}
