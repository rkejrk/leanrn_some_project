package com.example.demo.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * DBの操作方法
 * @author tanaka
 *
 */
public interface ServiceUserRepository extends CrudRepository<ServiceUser, Long> {

  List<ServiceUser> findByPhone(String phone);

  ServiceUser findById(long id);

  
}