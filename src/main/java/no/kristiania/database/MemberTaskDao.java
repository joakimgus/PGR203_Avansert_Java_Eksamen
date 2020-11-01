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
                    "INSERT INTO member_task (member_name, task_title, task_description) SELECT members.member_name, title, description FROM members Inner Join tasks t on t.id = members.task_id",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, memberTask.getMember_name());
                statement.setString(2, memberTask.getTask_title());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    memberTask.setMemberTaskId(generatedKeys.getInt("member_task_id"));
                }
            }
        }
    }

    public void update(MemberTask memberTask) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO member_task (member_name, task_title, task_description) SELECT members.member_name, title, description FROM members Inner Join tasks t on t.id = members.task_id")) {
                statement.setString(1, memberTask.getMember_name());
                statement.setString(2, memberTask.getTask_title());
                statement.setString(3, memberTask.getTask_description());
                statement.executeUpdate();
            }
        }
    }

    public MemberTask retrieve(Integer id) throws SQLException {
        return retrieve(id, "SELECT * FROM member_task WHERE id = ?");
    }

    @Override
    protected MemberTask mapRow(ResultSet rs) throws SQLException {
        MemberTask memberTask = new MemberTask();
        Member member = new Member();
        Task task = new Task();
        memberTask.setMemberTaskId(rs.getInt("member_task_id"));
        memberTask.setMember_name((String) rs.getObject(member.setName(rs.getString("member_name"))));
        memberTask.setTask_title((String) rs.getObject(task.setTitle(rs.getString("title"))));
        memberTask.setTask_description((String) rs.getObject(task.setDescription(rs.getString("description"))));
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
