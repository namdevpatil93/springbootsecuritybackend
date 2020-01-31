package com.sts.springbootsecuritybackend.service;

import java.util.List;

import com.sts.springbootsecuritybackend.domain.Module;
import com.sts.springbootsecuritybackend.domain.ModuleOperation;


public interface ModuleService {

	List<Module> findAllModules();

	List<ModuleOperation> findModuleOperationsByRoleId(Long roleId);
}
