package com.gjing.projects.excel.demo.repository;

import com.gjing.projects.excel.demo.entity.SingleHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Gjing
 **/
@Repository
public interface UserRepository extends JpaRepository<SingleHead,Integer> {
}
