package com.glg204.wothome.webofthings.service;

import com.glg204.wothome.webofthings.dao.ThingDAO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jmdns.JmDNS;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class JMDNSDiscoverService {

    @Autowired
    ThingDAO thingDAO;

    @PostConstruct
    public void init() {
        System.setProperty("java.net.preferIPv4Stack", "true");
        getDisoveredThings();
    }

    public void getDisoveredThings() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostname = InetAddress.getByName("localhost").toString();
            JmDNS jmDNS = JmDNS.create(addr, hostname); // throws IOException
            ThingListener thingListener = new ThingListener(thingDAO, jmDNS);
            String serviceDNS = "_webthing._tcp.local.";
            jmDNS.addServiceListener(serviceDNS, thingListener);
            Runtime.getRuntime().addShutdownHook(new Thread(jmDNS::unregisterAllServices));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
