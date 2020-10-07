package no.kristiania.http;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MemberDao {

    public static void main(String[] args) throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/kristianiaproject");
        dataSource.setUser("joakimtina");
        dataSource.setPassword("project2020");

        System.out.println("Please enter member name:");
        Scanner scanner = new Scanner(System.in);
        String memberName = scanner.nextLine();

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO members (member_name) VALUES (?)")) {
                statement.setString(1, memberName);
                statement.executeUpdate();
            }
        }

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from members")){
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        System.out.println(rs.getString("name"));
                        System.out.println(rs.getString("email"));
                    }
                }
            }
        }
    }

}
