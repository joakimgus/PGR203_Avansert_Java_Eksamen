package no.kristiania.database;

public class Task {

    // Task
    private String title;
    private Integer id;
    private String description;
    private Integer statusId;
    private String statusName;

    // SET & GET TASK
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

    // SET & GET DESCRIPTION
    public String getDescription() { return description; }
    public String setDescription(String description) {
        this.description = description;
        return description;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }
}
