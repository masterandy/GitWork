package net.wendal.nutzbook.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("lppz_admin")
public class MyUserDemo {
	@Id
	private Long id;
    @Name
    @Column
	private String username;//用户名
    @Column("name")
	private String name;//姓名
    @Column("password")
	private String password;//密码
    @Column("department")
	private String department;//部门
    @Column("email")
	private String email;//邮箱地址
    @Column("is_enabled")
	private Boolean is_enabled;//是否启动 // 删除后变成0
    @Column("is_locked")
	private Boolean is_locked = Boolean.FALSE;//是否锁定
	@Column("create_date")
	private Date create_date;//创建时间
	@Column("modify_date")
	private Date modify_date;//修改时间
	@Column("login_date")
	private Date login_date;//最后登录日期 
	@Column("login_ip")
	private String login_ip;//最后登录IP
	@Column("roles")
	private String roles;//所属角色(仅做列表查询用)
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getIs_enabled() {
		return is_enabled;
	}
	public void setIs_enabled(Boolean is_enabled) {
		this.is_enabled = is_enabled;
	}
	public Boolean getIs_locked() {
		return is_locked;
	}
	public void setIs_locked(Boolean is_locked) {
		this.is_locked = is_locked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getLogin_date() {
		return login_date;
	}
	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}
	public String getLogin_ip() {
		return login_ip;
	}
	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
}
