package com.yjh.test.service;

import com.yjh.test.dao.DaoFactory;
import com.yjh.test.dao.StudioDao;
import com.yjh.test.model.Studio;

import java.sql.SQLException;
import java.util.List;

public class StudioService {
    private StudioDao studioDao = DaoFactory.getStudioDao();
    public int add(Studio studio) throws SQLException {
        return studioDao.insert(studio);
    }
    public int delete(int id) throws SQLException {
        return studioDao.delete(id);
    }
    public int update(int studioID, String newName) throws SQLException {
        return studioDao.update(studioID, newName);
    }
    public List<Studio> quary(int id) throws SQLException {
        return studioDao.quary(id);
    }
}
