package no.kristiania.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskStatusDao extends AbstractDao<MemberTask>{

    public TaskStatusDao(DataSource dataSource) {
        super(dataSource);
    }

    public void insert(MemberTask taskStatus) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into members_with_tasks (member_id,task_id) values (?,?)"
            )) {
                statement.setInt(1, taskStatus.getMemberId());
                statement.setInt(2, taskStatus.getTaskId());
                statement.executeUpdate();
            }
        }
    }


    public MemberTask retrieve(Integer id) throws SQLException {
        return retrieve(id, "SELECT * FROM members_with_tasks WHERE memberTaskId = ?");
    }

    @Override
    protected MemberTask mapRow(ResultSet rs) throws SQLException {
        MemberTask taskStatus = new MemberTask();
        taskStatus.setMemberName((String) rs.getObject("name"));
        taskStatus.setTaskTitle((String) rs.getObject("title"));
        taskStatus.setTaskDescription((String) rs.getObject("description"));
        taskStatus.setStatusName((String) rs.getObject("status_name"));
        return taskStatus;
    }

    public List<MemberTask> list() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("Select m.name, t.title, t.description, s.status_name from status s, members m, tasks t, members_with_tasks mwt where mwt.member_id = m.id and t.id = mwt.task_id and t.status_id = s.id order by status_name")) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<MemberTask> taskStatus = new ArrayList<>();
                    while (rs.next()) {
                        taskStatus.add(mapRow(rs));
                    }
                    return taskStatus;
                }
            }
        }
    }
}