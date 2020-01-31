package com.sts.springbootsecuritybackend.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "roles")
public class Role implements Serializable {

    private static final long serialVersionUID = 5354017907353198316L;
    
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "is_enabled")
    private boolean enabled = true;

    @Column(name = "is_system_defined")
    private boolean systemDefined = false;

    @Column(name = "parent_role_id")
    private Long parentRoleId;

	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="role_id")
	private List<RoleModuleOperation> roleModuleOperationList;

    public Role(){}

    public Role(Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSystemDefined() {
        return systemDefined;
    }

    public void setSystemDefined(boolean systemDefined) {
        this.systemDefined = systemDefined;
    }

    public Long getParentRoleId() {
        return parentRoleId;
    }

    public void setParentRoleId(Long parentRoleId) {
        this.parentRoleId = parentRoleId;
    }

    public List<RoleModuleOperation> getRoleModuleOperationList() {
        return roleModuleOperationList;
    }

    public void setRoleModuleOperationList(List<RoleModuleOperation> roleModuleOperationList) {
        this.roleModuleOperationList = roleModuleOperationList;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                ", systemDefined=" + systemDefined +
                ", parentRoleId=" + parentRoleId +
                ", roleModuleOperationList=" + roleModuleOperationList +
                '}';
    }

}
