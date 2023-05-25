package com.example.demo.property;

import org.springframework.data.repository.CrudRepository;

/**
 * DBの操作方法
 * @author tanaka
 *
 */
public interface PropertyRepository extends CrudRepository<Property, Long> {

}