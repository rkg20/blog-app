package com.blogapp.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="User")
public class User  implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int userId;
	String userName;
	String userEmail;
	String userPassword;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts=new ArrayList<>();

	// @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	// @JoinTable(name = "user_role",
	// 	joinColumns = @JoinColumn(name="User",referencedColumnName = "userId"),
	// 	inverseJoinColumns = @JoinColumn(name="Role",referencedColumnName = "roleId")
	// )

	/**
	 *
	 */
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "user_role_table",joinColumns = @JoinColumn(name="user",referencedColumnName = "userId"),inverseJoinColumns =@JoinColumn(name="role",referencedColumnName = "roleId"))
	private Set<Role> userRole=new HashSet<>();


	// @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	// private List<Comment> comments=new ArrayList<>();



	// public User(int userId, String userName, String userEmail, String userPassword,
	// 		List<Post> posts) {
	// 	this.userId = userId;
	// 	this.userName = userName;
	// 	this.userEmail = userEmail;
	// 	this.userPassword = userPassword;
	// 	this.posts = posts;
	// }

	public User(int userId, String userName, String userEmail, String userPassword, List<Post> posts,
			Set<Role> userRole) {
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.posts = posts;
		this.userRole = userRole;
	}
	


	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}


	public User() {
		super();
	}
	
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Set<Role> getUserRole() {
		return userRole;
	}
	public void setUserRole(Set<Role> userRole) {
		this.userRole = userRole;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities=this.userRole.stream().map((role)-> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());

		return authorities;
	}
	@Override
	public String getPassword() {
		return this.userPassword;
	}



	@Override
	public String getUsername() {
		return this.userEmail;
	}



	@Override
	public boolean isAccountNonExpired() {
		return true;
	}



	@Override
	public boolean isAccountNonLocked() {
		return true;
	}



	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}



    public static Object withDefaultPasswordEncoder() {
        return null;
    }	
	
}
