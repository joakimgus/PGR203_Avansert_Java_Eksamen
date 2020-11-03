package no.kristiania.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskMemberDao extends AbstractDao<MemberTask>{

    public TaskMemberDao(DataSource dataSource) {
        super(dataSource);
    }

    public void insert(MemberTask taskMember) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into members_with_tasks (member_id,task_id) values (?,?)"
            )) {
                statement.setInt(1, taskMember.getMemberId());
                statement.setInt(2, taskMember.getTaskId());
                statement.executeUpdate();
            }
        }
    }


    public MemberTask retrieve(Integer id) throws SQLException {
        return retrieve(id, "SELECT * FROM members_with_tasks WHERE memberTaskId = ?");
    }

    @Override
    protected MemberTask mapRow(ResultSet rs) throws SQLException {
        MemberTask taskMember = new MemberTask();
        taskMember.setMemberName((String) rs.getObject("name"));
        taskMember.setTaskTitle((String) rs.getObject("title"));
        taskMember.setTaskDescription((String) rs.getObject("description"));
        taskMember.setStatusName((String) rs.getObject("status_name"));
        return taskMember;
    }

    public List<MemberTask> list() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("Select m.name, t.title, t.description, s.status_name from status s, members m, tasks t, members_with_tasks mwt where mwt.member_id = m.id and t.id = mwt.task_id and t.status_id = s.id order by title")) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<MemberTask> taskMembers = new ArrayList<>();
                    while (rs.next()) {
                        taskMembers.add(mapRow(rs));
                    }
                    return taskMembers;
                }
            }
        }
    }
}