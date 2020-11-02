package no.kristiania.database;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberTaskDao extends AbstractDao<MemberTask> {

    public MemberTaskDao(DataSource dataSource) {
        super(dataSource);
    }

    public void insert(MemberTask memberTask) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO members_with_tasks (name, title, description) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, memberTask.getMemberName());
                statement.setString(2, memberTask.getTaskTitle());
                statement.setString(3, memberTask.getTaskDescription());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    memberTask.setMemberTaskId(generatedKeys.getInt("memberTaskId"));
                }
            }
        }
    }

    public void update(MemberTask memberTask) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO member_task (name, title, description) ")) {
                statement.setInt(1, memberTask.getMemberTaskId());
                statement.executeUpdate();
            }
        }
    }

    public MemberTask retrieve(Integer id) throws SQLException {
        return retrieve(id, "SELECT * FROM member_task WHERE memberTaskId = ?");
    }

    @Override
    protected MemberTask mapRow(ResultSet rs) throws SQLException {
        MemberTask memberTask = new MemberTask();
        memberTask.setMemberTaskId(rs.getInt("memberTaskId"));
        memberTask.setMemberName(rs.getString("memberName"));
        memberTask.setTaskTitle(rs.getString("taskTitle"));
        memberTask.setTaskDescription("taskDescription");
        return memberTask;
    }

    public List<MemberTask> list() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM member_task")) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<MemberTask> memberTasks = new ArrayList<>();
                    while (rs.next()) {
                        memberTasks.add(mapRow(rs));
                    }
                    return memberTasks;
                }
            }
        }
    }
}