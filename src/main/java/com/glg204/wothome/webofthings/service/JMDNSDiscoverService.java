package com.glg204.wothome.webofthings.service;

import javax.jmdns.JmDNS;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class JMDNSDiscoverService {

    public void getDisoveredThing() {

        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostname = InetAddress.getByName(addr.getHostName()).toString();
            JmDNS jmDNS = JmDNS.create(addr, hostname); // throws IOException
            ThingListener thingListener = new ThingListener();
            String serviceDNS = "_webthing._tcp.local.";
            jmDNS.addServiceListener(serviceDNS, thingListener);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
