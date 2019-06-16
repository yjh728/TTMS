package com.yjh.test.dao;

import com.yjh.test.model.Sale;

import java.sql.SQLException;
import java.util.List;

public interface SaleDao {
    List<Sale> queryByUserID(int userID) throws SQLException;
}
