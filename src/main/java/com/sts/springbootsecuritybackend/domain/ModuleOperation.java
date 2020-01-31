
package com.sts.springbootsecuritybackend.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "module_operations")
public class ModuleOperation implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 4879958589936398227L;

    @Id
    @Column(name = "module_operation_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long moduleOperationId;

    @Column(name = "module_operation_name", nullable = false)
    private String moduleOperationName;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "module_id", nullable = false)
    private Long moduleId;

    @Transient
    private String moduleName;

    @Transient
    private boolean roleHasAccess = false;

    public Long getModuleOperationId() {
        return moduleOperationId;
    }

    public void setModuleOperationId(Long moduleOperationId) {
        this.moduleOperationId = moduleOperationId;
    }

    public String getModuleOperationName() {
        return moduleOperationName;
    }

    public void setModuleOperationName(String moduleOperationName) {
        this.moduleOperationName = moduleOperationName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public boolean isRoleHasAccess() {
        return roleHasAccess;
    }

    public void setRoleHasAccess(boolean roleHasAccess) {
        this.roleHasAccess = roleHasAccess;
    }

    @Override
    public String getAuthority() {
        return this.displayName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public String toString() {
        return "ModuleOperation{" +
                "moduleOperationId=" + moduleOperationId +
                ", moduleOperationName='" + moduleOperationName + '\'' +
                ", moduleId=" + moduleId +
                ", moduleName='" + moduleName + '\'' +
                '}';
    }
}