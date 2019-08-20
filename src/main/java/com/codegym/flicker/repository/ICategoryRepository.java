/*
 *************************************
 * Created by IntelliJ IDEA          *
 * User: Nhat                     *
 * Email: nhatnpa2508@gmail.com      *
 * Date: 7/29/2019                     *
 * Time: 9:31 PM                     *
 *************************************
 */

package com.codegym.flicker.repository;

import com.codegym.flicker.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICategoryRepository extends PagingAndSortingRepository<Category, Long> {
}
