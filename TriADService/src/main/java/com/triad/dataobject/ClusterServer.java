package com.triad.dataobject;

import java.io.Serializable;

public class ClusterServer implements Serializable {
    private static final long serialVersionUID = 5752825957009424339L;
    
    public static String MASTER = "Master";
    
    public static String SLAVE = "slave";
    
    private String addr;

    private Integer port;

    private String state;

    private String role;

    

    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getAddr() {
        return addr;
    }
	
	public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
