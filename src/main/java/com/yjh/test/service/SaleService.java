package com.yjh.test.service;

import com.yjh.test.dao.DaoFactory;
import com.yjh.test.dao.SaleDao;
import com.yjh.test.model.Sale;

import java.sql.SQLException;
import java.util.List;

public class SaleService {

    public List<Sale> queryByUserID(int userID) throws SQLException {
        SaleDao saleDao = DaoFactory.getSaleDao();
        return saleDao.queryByUserID(userID);
    }
}
