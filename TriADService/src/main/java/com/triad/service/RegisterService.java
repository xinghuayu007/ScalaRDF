package com.triad.service;

import com.triad.dataobject.ClusterServer;
import com.triad.tools.RegState;

import java.util.ArrayList;
import java.util.List;

public interface RegisterService {
	boolean addNode(String ip, String port, String role);
	boolean startNode(String ip);
	boolean stopNode(String ip);
	ArrayList<ClusterServer> getCluster();
}
