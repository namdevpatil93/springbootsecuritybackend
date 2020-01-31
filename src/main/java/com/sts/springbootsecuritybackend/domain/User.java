package com.sts.springbootsecuritybackend.domain;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "users")
public class User implements Comparable<User> {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; 
    
    @Column(name = "user_name", length = 50)
    @NotNull
    @Size(max = 50)
    private String username;
    
    @Column(name = "first_name", length = 50)
    @NotNull
    @Size(max = 50)
    private String firstName;
    
    @Column(name = "middle_name", length = 50)
    @Size(max = 50)
    private String middleName;
    
    @Column(name = "last_name", length = 50)
    @NotNull
    @Size(max = 50)
    private String lastName;

    @Column(name = "email", length = 50)
    @NotNull
    @Size(max = 50)
    private String email;    

    @Column(name = "ip_address", length = 50)
    @Size(max = 15)
    private String ipAddress;

    @Column(name = "token", length = 1000)
    @Size(max = 1000)
    private String token;
    
    @Column(name = "email_status", length = 1)
    private Integer emailStatus=0;
    
    @Column(name = "phone_number", length = 15)
    @Size(max = 15)
    private String phoneNumber;    
    
    @Column(name = "password", length = 100)
    @NotNull
    @Size(max = 100)
    private String password;
    
    @Column(name = "approve")
    private boolean approve;
    
    @Column(name = "active")
    private boolean active;
    
    @Column(name = "approved_date")
    private Date approvedDate;
    
    
    @Column(name = "last_access_time")
    private Date lastAccessTime;
        
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;
    

	@Transient
	private String socialMediaTokenFromApp;    

	@Transient
	private Long socialMediaIdFromApp;    	
	
	@Transient
	private String socialMediaUserIdFromApp;    
	
	@Transient
	private String socialMediaEmailIdFromApp;    
	
	@Transient
	private String socialMediaUserIdFromSite;    
	
	@Transient
	private String socialMediaEmailIdFromSite;   
			
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
     
    public String getUsername() {

		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(Integer emailStatus) {
		this.emailStatus = emailStatus;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


    
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isApprove() {
		return approve;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}	
	
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}	
	
	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	

	public String getSocialMediaTokenFromApp() {
		return socialMediaTokenFromApp;
	}

	public void setSocialMediaTokenFromApp(String socialMediaTokenFromApp) {
		this.socialMediaTokenFromApp = socialMediaTokenFromApp;
	}

	public Long getSocialMediaIdFromApp() {
		return socialMediaIdFromApp;
	}

	public void setSocialMediaIdFromApp(Long socialMediaIdFromApp) {
		this.socialMediaIdFromApp = socialMediaIdFromApp;
	}

	public String getSocialMediaUserIdFromApp() {
		return socialMediaUserIdFromApp;
	}

	public void setSocialMediaUserIdFromApp(String socialMediaUserIdFromApp) {
		this.socialMediaUserIdFromApp = socialMediaUserIdFromApp;
	}

	public String getSocialMediaEmailIdFromApp() {
		return socialMediaEmailIdFromApp;
	}

	public void setSocialMediaEmailIdFromApp(String socialMediaEmailIdFromApp) {
		this.socialMediaEmailIdFromApp = socialMediaEmailIdFromApp;
	}

	public String getSocialMediaUserIdFromSite() {
		return socialMediaUserIdFromSite;
	}

	public void setSocialMediaUserIdFromSite(String socialMediaUserIdFromSite) {
		this.socialMediaUserIdFromSite = socialMediaUserIdFromSite;
	}

	public String getSocialMediaEmailIdFromSite() {
		return socialMediaEmailIdFromSite;
	}

	public void setSocialMediaEmailIdFromSite(String socialMediaEmailIdFromSite) {
		this.socialMediaEmailIdFromSite = socialMediaEmailIdFromSite;
	}

	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", email=" + email + ", ipAddress=" + ipAddress + ", token=" + token
				+ ", emailStatus=" + emailStatus + ", phoneNumber=" + phoneNumber + ", password=" + password
				+ ", approve=" + approve + ", active=" + active + ", approvedDate=" + approvedDate + ", lastAccessTime="
				+ lastAccessTime + " + "
				+ ", socialMediaTokenFromApp=" + socialMediaTokenFromApp + ", socialMediaIdFromApp="
				+ socialMediaIdFromApp + ", socialMediaUserIdFromApp=" + socialMediaUserIdFromApp
				+ ", socialMediaEmailIdFromApp=" + socialMediaEmailIdFromApp + ", socialMediaUserIdFromSite="
				+ socialMediaUserIdFromSite + ", socialMediaEmailIdFromSite=" + socialMediaEmailIdFromSite + "]";
	}

	@Override
	public int compareTo(User user) {		
		return (this.getUsername().compareTo(user.getUsername()));
	}
	
	
	
}