package com.sts.springbootsecuritybackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sts.springbootsecuritybackend.domain.Module;
import com.sts.springbootsecuritybackend.domain.ModuleOperation;
import com.sts.springbootsecuritybackend.repository.ModuleOperationRepository;
import com.sts.springbootsecuritybackend.repository.ModuleRepository;
import com.sts.springbootsecuritybackend.service.ModuleService;

@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	private ModuleRepository moduleRepository;
	
    @Autowired
    private ModuleOperationRepository moduleOperationRepository;

	@Override
	public List<Module> findAllModules() {
 		return moduleRepository.findAllModules();
	}
    @Override
    public List<ModuleOperation> findModuleOperationsByRoleId(Long roleId){
        return moduleOperationRepository.findByRoleId(roleId);
    }
}
