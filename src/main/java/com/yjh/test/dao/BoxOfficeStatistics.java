package com.yjh.test.dao;

import com.yjh.test.model.Statistics;

import java.sql.SQLException;
import java.util.List;

public interface BoxOfficeStatistics {
    List<Statistics> analysis() throws SQLException;
}
