package no.kristiania.database;

import no.kristiania.http.StatusOptionsController;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class StatusDaoTest {
    private StatusDao statusDao;
    private static final Random random = new Random();

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        statusDao = new StatusDao(dataSource);
    }

    @Test
    void shouldListAllStatuses() throws SQLException {
        Status status1 = exampleStatus();
        Status status2 = exampleStatus();
        statusDao.insert(status1);
        statusDao.insert(status2);
        assertThat(statusDao.list())
                .extracting(Status::getName)
                .contains(status1.getName(), status2.getName());
    }

    @Test
    void shouldRetrieveAllStatusProperties() throws SQLException {
        statusDao.insert(exampleStatus());
        statusDao.insert(exampleStatus());

        Status status = exampleStatus();
        statusDao.insert(status);
        assertThat(status).hasNoNullFieldsOrProperties();

        assertThat(statusDao.retrieve(status.getId()))
                .usingRecursiveComparison()
                .isEqualTo(status);
    }

    @Test
    void shouldReturnStatusAsOptions() throws SQLException, UnsupportedEncodingException {
        StatusOptionsController controller = new StatusOptionsController(statusDao);
        Status status = exampleStatus();
        statusDao.insert(status);

        assertThat(controller.getBody())
                .contains("<option value=" + status.getId() + ">" + status.getName() + "</option>");
    }

    public static Status exampleStatus() {
        Status status = new Status();
        status.setName(exampleStatusName());
        return status;
    }

    private static String exampleStatusName() {
        String[] options = {"Status 1", "Status 2", "Status 3"};
        return options[random.nextInt(options.length)];
    }
}
