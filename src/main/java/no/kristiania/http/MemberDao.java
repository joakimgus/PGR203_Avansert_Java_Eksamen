package no.kristiania.http;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberDao {

    private final DataSource dataSource;

    public MemberDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/kristianiaproject");
        dataSource.setUser("joakimtina");
        dataSource.setPassword("project2020");

        MemberDao memberDao = new MemberDao(dataSource);

        System.out.println("Please enter member name:");
        Scanner scanner = new Scanner(System.in);
        String memberName = scanner.nextLine();

        Member member = new Member();
        member.setName(memberName);
        memberDao.insert(member);
        for (Member m : memberDao.list()) {
            System.out.println(m);
        }
    }

    public void insert(Member member) throws SQLException {
        // To get member_name from database
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO members (member_name) VALUES (?)")) {
                statement.setString(1, member.getName());
                statement.executeUpdate();
            }
        }
    }

    public List<Member> list() throws SQLException {
        List<Member> members = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from members")){
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        Member member = new Member();
                        member.setName(rs.getString("member_name"));
                        members.add(member);
                        //System.out.println(rs.getString("email"));
                    }
                }
            }
        }
        return members;
    }
}
