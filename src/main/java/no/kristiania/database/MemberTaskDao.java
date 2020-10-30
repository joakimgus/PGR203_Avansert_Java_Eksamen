package no.kristiania.database;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberTaskDao extends AbstractDao<MemberTask> {

    public MemberTaskDao(DataSource dataSource) {
        super(dataSource);
    }

    public void insert(MemberTask task) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO member_tasks (title) values (?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, task.getTitle());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    task.setId(generatedKeys.getInt("id"));
                }
            }
        }
    }

    public void update(MemberTask memberTask) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE member_tasks SET status_id = ? WHERE id = ?")) {
                statement.setInt(1, memberTask.getId());
                statement.setInt(2, memberTask.getStatusId());
                statement.executeUpdate();
            }
        }
    }

    public MemberTask retrieve(Integer id) throws SQLException {
        return retrieve(id, "SELECT * FROM member_tasks WHERE id = ?");
    }

    @Override
    protected MemberTask mapRow(ResultSet rs) throws SQLException {
        MemberTask task = new MemberTask();
        task.setId(rs.getInt("id"));
        task.setStatusId((Integer) rs.getObject("status_id"));
        String title = task.setTitle(rs.getString("title"));
        try {
            task.setTitle(URLDecoder.decode(title, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String statusName = task.setStatusName(rs.getString("status_name"));
        try {
            task.setStatusName(URLDecoder.decode(statusName, "UTF-8"));
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return task;
    }

    public List <MemberTask> list() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM member_tasks")) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<MemberTask> members = new ArrayList<>();
                    while (rs.next()) {
                        members.add(mapRow(rs));
                    }
                    return members;
                }
            }
        }
    }
}
