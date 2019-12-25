package model;

import java.io.Serializable;

public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username, password;
	private int typeAcc;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getTypeAcc() {
		return typeAcc;
	}
	public void setTypeAcc(int typeAcc) {
		this.typeAcc = typeAcc;
	}
	
}
