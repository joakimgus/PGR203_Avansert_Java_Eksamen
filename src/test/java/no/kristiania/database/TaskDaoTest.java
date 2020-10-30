package no.kristiania.database;

import no.kristiania.http.MemberTaskOptionsController;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskDaoTest {

    private MemberTaskDao taskDao;
    private static Random random = new Random();

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        taskDao = new MemberTaskDao(dataSource);
    }

    @Test
    void shouldListAllTasks() throws SQLException {
        MemberTask task1 = exampleTask();
        MemberTask task2 = exampleTask();
        taskDao.insert(task1);
        taskDao.insert(task2);
        assertThat(taskDao.list())
                .extracting(MemberTask::getTitle)
                .contains(task1.getTitle(), task2.getTitle());
    }

    @Test
    void shouldRetrieveAllTaskProperties() throws SQLException {
        taskDao.insert(exampleTask());
        taskDao.insert(exampleTask());

        MemberTask task = exampleTask();
        taskDao.insert(task);
        assertThat(task).hasNoNullFieldsOrProperties();

        assertThat(taskDao.retrieve(task.getId()))
                .usingRecursiveComparison()
                .isEqualTo(task);
    }

    @Test
    void shouldReturnTasksAsOptions() throws SQLException, UnsupportedEncodingException {
        MemberTaskOptionsController controller = new MemberTaskOptionsController(taskDao);
        MemberTask memberTask = exampleTask();
        taskDao.insert(memberTask);

        assertThat(controller.getBody())
                .contains("<option value=" + memberTask.getId() + ">" + memberTask.getTitle() + "</option>");
    }

    public static MemberTask exampleTask() {
        MemberTask task = new MemberTask();
        task.setTitle(exampleTaskTitle());
        return task;
    }

    private static String exampleTaskTitle() {
        String[] options = {"Oppgave 1", "Oppgave 2", "Oppgave 3"};
        return options[random.nextInt(options.length)];
    }
}
