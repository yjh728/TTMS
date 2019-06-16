package com.yjh.test.service;

import com.yjh.test.dao.DaoFactory;
import com.yjh.test.dao.PlanDao;
import com.yjh.test.model.Plan;

import java.sql.SQLException;
import java.util.List;

public class PlanService {
    private PlanDao planDao = DaoFactory.getPlanDao();
    public int add(Plan plan) throws SQLException {
        return planDao.insert(plan);
    }
    public int delete(int id) throws SQLException {
        return planDao.delete(id);
    }
    public int update(Plan plan) throws SQLException {
        return planDao.update(plan);
    }
    public List<Plan> query(int id) throws SQLException {
        return planDao.query(id);
    }

    public List<Plan> queryByPlayID(int id) throws SQLException {
        return planDao.queryByPlayID(id);
    }
}
