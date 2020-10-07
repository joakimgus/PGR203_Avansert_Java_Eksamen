package no.kristiania.http;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberDaoTest {
    @Test
    void shouldListInsertedMembers() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:testdatabase;DB_CLOSE_DELAY=-1");
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("create table members (member_name varchar)").executeUpdate();
        }

        MemberDao memberDao = new MemberDao(dataSource);
        String member = exampleMember();
        memberDao.insert(member);
        assertThat(memberDao.list()).contains(member);
    }

    private String exampleMember() {
        String[] members = {"Test","Joakim"};
        Random random = new Random();
        return members[random.nextInt(members.length)];
    }
}