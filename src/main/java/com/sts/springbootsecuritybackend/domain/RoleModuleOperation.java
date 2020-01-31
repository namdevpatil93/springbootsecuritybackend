
package com.sts.springbootsecuritybackend.domain;

import javax.persistence.*;


import java.io.Serializable;

@Entity
@Table(name = "role_module_operation_assoc")
public class RoleModuleOperation implements Serializable {

    private static final long serialVersionUID = 6236369194306737681L;
    
    @Id
    @Column(name = "role_module_operation_id", nullable = false)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long roleModuleOperationId;
    
    @Column(name = "role_id")
    private Long roleId;
    
    @Column(name="module_operation_id")
    private Long moduleOperationId;
    
    @OneToOne
    @JoinColumn(name="module_operation_id",insertable=false,updatable=false)
    private ModuleOperation moduleOperation;

    public Long getRoleModuleOperationId() {
        return roleModuleOperationId;
    }

    public void setRoleModuleOperationId(Long roleModuleOperationId) {
        this.roleModuleOperationId = roleModuleOperationId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getModuleOperationId() {
        return moduleOperationId;
    }

    public void setModuleOperationId(Long moduleOperationId) {
        this.moduleOperationId = moduleOperationId;
    }

    public ModuleOperation getModuleOperation() {
        return moduleOperation;
    }

    public void setModuleOperation(ModuleOperation moduleOperation) {
        this.moduleOperation = moduleOperation;
    }
}
