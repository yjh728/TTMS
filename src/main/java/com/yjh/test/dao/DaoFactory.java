package com.yjh.test.dao;

import com.yjh.test.dao.impl.*;

public class DaoFactory {
    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }

    public static StudioDao getStudioDao() {
        return new StudioDaoImpl();
    }

    public static SeatDao getSeatDao() {
        return new SeatDaoImpl();
    }

    public static PlayDao getPlayDao() {
        return new PlayDaoImpl();
    }

    public static PlanDao getPlanDao() {
        return new PlanDaoImpl();
    }

    public static TicketDao getTicketDao() {
        return new TicketDaoImpl();
    }

    public static SaleDao getSaleDao() {
        return new SaleDaoImpl();
    }
}