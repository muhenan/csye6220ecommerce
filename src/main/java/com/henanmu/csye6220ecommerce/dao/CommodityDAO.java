package com.henanmu.csye6220ecommerce.dao;

import com.henanmu.csye6220ecommerce.pojo.Commodity;
import org.springframework.stereotype.Repository;

@Repository
public class CommodityDAO extends DAO {
    public void createCommodity (Commodity commodity) {
        begin();
        getSession().persist(commodity);
        close();
    }

    public Commodity readCommodityById (Integer commodityId) {
        return getSession().get(Commodity.class, commodityId);
    }
}
