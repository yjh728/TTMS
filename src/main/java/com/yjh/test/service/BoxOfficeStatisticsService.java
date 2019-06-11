package com.yjh.test.service;

import com.yjh.test.dao.BoxOfficeStatistics;
import com.yjh.test.dao.impl.BoxOfficeStatisticsImpl;
import com.yjh.test.model.Statistics;

import java.sql.SQLException;
import java.util.List;

public class BoxOfficeStatisticsService {
    public List<Statistics> analysis() throws SQLException {
        BoxOfficeStatistics boxOfficeStatistics = new BoxOfficeStatisticsImpl();
        return boxOfficeStatistics.analysis();
    }
}
