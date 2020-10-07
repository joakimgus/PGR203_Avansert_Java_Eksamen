package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberDaoTest {
    @Test
    void shouldListInsertedMembers() {
        MemberDao memberDao = new MemberDao();
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