package com.sts.springbootsecuritybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sts.springbootsecuritybackend.domain.Module;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {

    @Query("SELECT m FROM Module m ORDER BY m.moduleName ASC")
    List<Module> findAllModules();

}
