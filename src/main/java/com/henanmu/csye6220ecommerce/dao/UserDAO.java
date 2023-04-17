package com.henanmu.csye6220ecommerce.dao;

import com.henanmu.csye6220ecommerce.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends DAO {
    public void saveUser(User user) {
        begin(); //inherited from super class DAO
        getSession().save(user);
        commit();
    }

    public User readUserById(Integer uid) {
        User user = getSession().get(User.class, uid);
        return user;
    }

    public User readUserByName(String name) {
        User user = (User) getSession().createQuery("FROM User WHERE name = :name")
                    .setParameter("name", name)
                    .uniqueResult();
        return user;
    }

    public void updateUser(User user) {
        begin();
        getSession().update(user);
        commit();
    }

}
