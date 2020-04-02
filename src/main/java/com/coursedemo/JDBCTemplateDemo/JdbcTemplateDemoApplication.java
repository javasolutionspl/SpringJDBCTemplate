package com.coursedemo.JDBCTemplateDemo;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class JdbcTemplateDemoApplication {

    @Autowired
    private JdbcTemplate initialJdbcTemplate;

    private static JdbcTemplate jdbcTemplate;

    private static Logger logger = LoggerFactory.getLogger(JdbcTemplateDemoApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(JdbcTemplateDemoApplication.class, args);
        List<UserVO> users = getUsers();
        //users.forEach(user -> logger.info(user.toString()));
        insertUser("2", "fds", 100);
        //deleteUser();
        //batchUpdate(users);
    }

    private static void batchUpdate(List<UserVO> users) {

        jdbcTemplate.batchUpdate("UPDATE public.users SET \"account_value\" = ? WHERE \"USER_ID\"=?", new UserBatchPreparedStatementSetter(users));
    }

    private static void deleteUser(String id) {

        jdbcTemplate.update("DELETE FROM public.users WHERE \"USER_ID\" = ?", ps -> ps.setString(1, id));
    }

    private static void insertUser(String id, String userName, int accountValue) {

        jdbcTemplate.update("INSERT INTO public.users(\"USER_ID\", \"USER_NAME\", account_value)VALUES (?, ?, ?)",
                ps -> preparedStatementSetter(ps, id, userName, accountValue));
    }

    private static void preparedStatementSetter(PreparedStatement preparedStatement, String id, String userName, int accountValue) throws SQLException {

        preparedStatement.setString(1, id);
        preparedStatement.setString(2, userName);
        preparedStatement.setInt(3, accountValue);
    }

    private static List<UserVO> getUsers() {

        return jdbcTemplate.query("SELECT \"USER_ID\", \"USER_NAME\", account_value FROM public.users",
                JdbcTemplateDemoApplication::mapUserFromResultset);
    }

    private static UserVO mapUserFromResultset(ResultSet resultSet, int rowNumber) throws SQLException {

        UserVO user = new UserVO();
        String userID = resultSet.getString("USER_ID");
        String userName = resultSet.getString("USER_NAME");
        int accountValue = resultSet.getInt("account_value");
        user.setUserId(userID);
        user.setUserName(userName);
        user.setAccountValue(accountValue);
        return user;
    }

    @PostConstruct
    public void init() {

        JdbcTemplateDemoApplication.setJdbcTemplate(initialJdbcTemplate);
    }

    public static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        JdbcTemplateDemoApplication.jdbcTemplate = jdbcTemplate;
    }
}
