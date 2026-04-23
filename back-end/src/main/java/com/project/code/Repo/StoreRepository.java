package com.project.code.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.project.code.Model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    // Find store by id
    Store findByid(Long id);

    // Find stores by name substring (case-insensitive)
    @Query("SELECT s FROM Store s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :pname, '%'))")
    List<Store> findBySubName(String pname);
}