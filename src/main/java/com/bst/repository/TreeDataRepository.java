package com.bst.repository;

import com.bst.model.TreeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeDataRepository extends JpaRepository<TreeData, Long> {

    @Query("SELECT t FROM TreeData t ORDER BY t.createdAt DESC")
    List<TreeData> findAllOrderByCreatedAtDesc();

    @Query("SELECT t FROM TreeData t WHERE t.isBalanced = true ORDER BY t.createdAt DESC")
    List<TreeData> findBalancedTreesOrderByCreatedAtDesc();
}