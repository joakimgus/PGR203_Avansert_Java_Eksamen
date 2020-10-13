package no.kristiania.http;

import org.flywaydb.core.Flyway;
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
        Flyway.configure().dataSource(dataSource).load().migrate();

        MemberDao memberDao = new MemberDao(dataSource);
        Member member = exampleMember();
        memberDao.insert(member);
        assertThat(memberDao.list())
                .extracting(Member::getName)
                .contains(member.getName());
    }

    private Member exampleMember() {
        Member member = new Member();
        member.setName(exampleMemberName());
        return member;
    }

    private String exampleMemberName() {
        String[] members = {"Test","Joakim"};
        Random random = new Random();
        return members[random.nextInt(members.length)];
    }
}