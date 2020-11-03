
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
                    "insert into members_with_tasks (member_id,task_id) values (?,?)"
            )) {
                statement.setInt(1, memberTask.getMemberId());
                statement.setInt(2, memberTask.getTaskId());
                statement.executeUpdate();
                }
            }
        }


    public MemberTask retrieve(Integer id) throws SQLException {
        return retrieve(id, "SELECT * FROM members_with_tasks WHERE memberTaskId = ?");
    }

    @Override
    protected MemberTask mapRow(ResultSet rs) throws SQLException {
        MemberTask memberTask = new MemberTask();
        memberTask.setMemberName((String) rs.getObject("name"));
        memberTask.setTaskTitle((String) rs.getObject("title"));
        memberTask.setTaskDescription((String) rs.getObject("description"));
        memberTask.setStatusName((String) rs.getObject("status_name"));
        return memberTask;
    }

    public List<MemberTask> list() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("Select m.name, t.title, t.description, s.status_name from status s, members m, tasks t, members_with_tasks mwt where mwt.member_id = m.id and t.id = mwt.task_id and t.status_id = s.id order by title")) {
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