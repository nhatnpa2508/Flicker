/*
 *************************************
 * Created by IntelliJ IDEA          *
 * User: Nhat                     *
 * Email: nhatnpa2508@gmail.com      *
 * Date: 7/29/2019                     *
 * Time: 9:32 PM                     *
 *************************************
 */

package com.codegym.flicker.service;

import com.codegym.flicker.model.Category;

public interface ICategoryService {

    Iterable<Category> findAll();

    Category findById(Long id);

    void save(Category category);

    void remove(Long id);
}
