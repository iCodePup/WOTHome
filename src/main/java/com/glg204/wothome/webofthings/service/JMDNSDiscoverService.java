package com.glg204.wothome.webofthings.service;

import org.springframework.stereotype.Service;

import javax.jmdns.JmDNS;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public interface JMDNSDiscoverService {
    public void getDisoveredThings();
}
