package no.kristiania.database;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusDao extends AbstractDao<Status>{

    public StatusDao(DataSource dataSource) {
        super(dataSource);
    }

    public void insert(Status status) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO status (status_name) values (?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, status.getName());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    status.setId(generatedKeys.getInt("id"));
                }
            }
        }
    }

    public Status retrieve(Integer id) throws SQLException {
        return retrieve(id, "SELECT * FROM status WHERE id = ?");
    }

    @Override
    protected Status mapRow(ResultSet rs) throws SQLException {
        Status status = new Status();
        status.setId(rs.getInt("id"));
        status.setName(rs.getString("status_name"));
        return status;
    }

    public List <Status> list() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM status")) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<Status> statuses = new ArrayList<>();
                    while (rs.next()) {
                        statuses.add(mapRow(rs));
                    }
                    return statuses;
                }
            }
        }
    }
}
