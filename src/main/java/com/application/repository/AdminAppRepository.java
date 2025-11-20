package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.AdminApp;

@Repository
public interface AdminAppRepository extends JpaRepository<AdminApp, Integer>{

}
