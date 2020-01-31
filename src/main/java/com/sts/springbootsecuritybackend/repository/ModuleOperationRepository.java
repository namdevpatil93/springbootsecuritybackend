package com.sts.springbootsecuritybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sts.springbootsecuritybackend.domain.ModuleOperation;

import java.util.List;

public interface ModuleOperationRepository extends JpaRepository<ModuleOperation, Long> {

    List<ModuleOperation> findByModuleId(Integer moduleId);

    @Query("SELECT DISTINCT(mo) FROM ModuleOperation mo, RoleModuleOperation rmo where mo.moduleOperationId = rmo.moduleOperationId and rmo.roleId = :roleId")
    List<ModuleOperation> findByRoleId(@Param("roleId") Long roleId);
}
