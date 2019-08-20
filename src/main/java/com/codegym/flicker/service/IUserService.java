/*
 *************************************
 * Created by IntelliJ IDEA          *
 * User: Nhat                     *
 * Email: nhatnpa2508@gmail.com      *
 * Date: 7/29/2019                     *
 * Time: 3:13 PM                     *
 *************************************
 */

package com.codegym.flicker.service;

import com.codegym.flicker.model.User;

public interface IUserService {
    Iterable<User> findAll();

    User findById(Long id);

    void save(User user);

    void remove(Long id);

    User getUserByEmail(String email);

}
