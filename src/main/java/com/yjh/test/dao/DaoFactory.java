package com.yjh.test.dao;

import com.yjh.test.dao.impl.PlayDaoImpl;
import com.yjh.test.dao.impl.SeatDaoImpl;
import com.yjh.test.dao.impl.StudioDaoImpl;
import com.yjh.test.dao.impl.UserDaoImpl;

public class DaoFactory {
    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }
    public static StudioDao getStudioDao(){
        return new StudioDaoImpl();
    }

    public static SeatDao getSeatDao() {
        return new SeatDaoImpl();
    }

    public static PlayDao getPlayDao() {
        return new PlayDaoImpl();
    }
}