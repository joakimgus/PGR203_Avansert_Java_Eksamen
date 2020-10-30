package no.kristiania.database;

import no.kristiania.http.TaskOptionsController;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskDaoTest {

    private TaskDao taskDao;
    private static Random random = new Random();

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        taskDao = new TaskDao(dataSource);
    }

    @Test
    void shouldListAllTasks() throws SQLException {
        Task task1 = exampleTask();
        Task task2 = exampleTask();
        taskDao.insert(task1);
        taskDao.insert(task2);
        assertThat(taskDao.list())
                .extracting(Task::getTitle)
                .contains(task1.getTitle(), task2.getTitle());
    }

    @Test
    void shouldRetrieveAllTaskProperties() throws SQLException {
        taskDao.insert(exampleTask());
        taskDao.insert(exampleTask());

        Task task = exampleTask();
        taskDao.insert(task);
        assertThat(task).hasNoNullFieldsOrProperties();

        assertThat(taskDao.retrieve(task.getId()))
                .usingRecursiveComparison()
                .isEqualTo(task);
    }

    @Test
    void shouldReturnTasksAsOptions() throws SQLException, UnsupportedEncodingException {
        TaskOptionsController controller = new TaskOptionsController(taskDao);
        Task task = exampleTask();
        taskDao.insert(task);

        assertThat(controller.getBody())
                .contains("<option value=" + task.getId() + ">" + task.getTitle() + "</option>");
    }

    public static Task exampleTask() {
        Task task = new Task();
        task.setTitle(exampleTaskTitle());
        return task;
    }

    private static String exampleTaskTitle() {
        String[] options = {"Oppgave 1", "Oppgave 2", "Oppgave 3"};
        return options[random.nextInt(options.length)];
    }
}
