package com.yjh.test.dao.impl;

import com.yjh.test.dao.BoxOfficeStatistics;
import com.yjh.test.model.Statistics;
import com.yjh.test.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoxOfficeStatisticsImpl implements BoxOfficeStatistics {
    @Override
    public List<Statistics> analysis() throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String sql = "select * from salesanalysis";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        List<Statistics> list = new ArrayList<>();
        while (set.next()) {
            Statistics statistics = new Statistics();
            statistics.setPlayID(set.getInt("play_id"));
            statistics.setTotalTickets(set.getInt("tickets"));
            statistics.setSales(set.getLong("sales"));
            list.add(statistics);
        }
        JDBCUtil.close(set, statement, connection);
        return list;
    }
}
