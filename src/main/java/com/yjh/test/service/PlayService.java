package com.yjh.test.service;

import com.yjh.test.dao.DaoFactory;
import com.yjh.test.dao.PlayDao;
import com.yjh.test.model.Play;

import java.sql.SQLException;
import java.util.List;

public class PlayService {
    private PlayDao playDao = DaoFactory.getPlayDao();

    public int add(Play play) throws SQLException {
        return playDao.insert(play);
    }

    public int delete(int id) throws SQLException {
        return playDao.delete(id);
    }

    public int update(Play play) throws SQLException {
        return playDao.update(play);
    }

    public List<Play> quary(int id) throws SQLException {
        return playDao.quary(id);
    }
    public List<Play> quary(String name) throws SQLException {
        return playDao.quary(name);
    }
    public List<Play> queryNowHot()throws SQLException{
        return playDao.queryNowHot();
    }

    public List<Play> queryReleased()throws SQLException{
        return playDao.queryReleased();
    }
}
