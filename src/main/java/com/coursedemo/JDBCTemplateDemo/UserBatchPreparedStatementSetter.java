package com.coursedemo.JDBCTemplateDemo;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserBatchPreparedStatementSetter implements BatchPreparedStatementSetter {

    private List<UserVO> users;

    public UserBatchPreparedStatementSetter(List<UserVO> users) {
        this.users = users;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {

        UserVO user = users.get(i);
        int actal = user.getAccountValue();
        int valueToSet = actal + 100;
        preparedStatement.setInt(1, valueToSet);
        preparedStatement.setString(2, user.getUserId());
    }

    @Override
    public int getBatchSize() {

        return 4;
    }
}
