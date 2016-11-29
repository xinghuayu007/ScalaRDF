package com.triad.service.impl;

import java.util.ArrayList;
import java.util.HashSet;

import com.triad.dataobject.ClusterServer;
import com.triad.service.RegisterService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class RegisterServiceImpl implements RegisterService {

    private static Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);
    
    Jedis jedis = new Jedis("localhost", ScalaRDFConfig.redisPort);
    
	@Override
	public boolean addNode(String ip, String port, String role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean startNode(String ip) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stopNode(String ip) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ArrayList<ClusterServer> getCluster() {
		HashSet<String> hosts = (HashSet<String>) jedis.smembers("hosts");
		//HashSet<String> hosts = new HashSet<String>();
		hosts.add("192.168.7.1");
		ArrayList<ClusterServer> cluster = new ArrayList<ClusterServer>();
		for(String host:hosts){
			ClusterServer server = new ClusterServer();
			server.setAddr(host);
			server.setPort(Integer.parseInt(ScalaRDFConfig.socketPort));
			if(host.equals(ScalaRDFConfig.master))
				server.setRole("master");
			else 
				server.setRole("slave");
			server.setState("ready");
			cluster.add(server);
		}
		return cluster;
	}


}
