package no.kristiania.database;

public class MemberTask {

    // Task
    private String title;
    private Integer id;

    // Description of task
    private String description;

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
}
