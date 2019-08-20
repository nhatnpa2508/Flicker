/*
 *************************************
 * Created by IntelliJ IDEA          *
 * User: Nhat                     *
 * Email: nhatnpa2508@gmail.com      *
 * Date: 7/29/2019                     *
 * Time: 3:12 PM                     *
 *************************************
 */

package com.codegym.flicker.repository;

import com.codegym.flicker.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IUserRepository extends PagingAndSortingRepository<User, Long> {

    User getUserByEmail(String email);

}
