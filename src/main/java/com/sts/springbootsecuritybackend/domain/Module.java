
package com.sts.springbootsecuritybackend.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "modules")
public class Module implements Serializable {

    private static final long serialVersionUID = -19027628511627182L;
    
    @Id
    @Column(name = "module_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long moduleId;
    
    @Column(name = "module_name", nullable = false)
    private String moduleName;
    
    @Column(name = "display_name", nullable = false)
    private String displayName;
    
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "module_id")
    private List<ModuleOperation> moduleOperationList;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<ModuleOperation> getModuleOperationList() {
        return moduleOperationList;
    }

    public void setModuleOperationList(List<ModuleOperation> moduleOperationList) {
        this.moduleOperationList = moduleOperationList;
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleId=" + moduleId +
                ", moduleName='" + moduleName + '\'' +
                '}';
    }
}
