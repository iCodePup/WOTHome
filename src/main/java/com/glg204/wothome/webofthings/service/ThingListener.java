package com.glg204.wothome.webofthings.service;

import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.webofthings.dao.ThingDAO;
import com.glg204.wothome.webofthings.domain.Thing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.util.Optional;

public class ThingListener implements ServiceListener {

    private Logger logger = LoggerFactory.getLogger(ThingListener.class);

    private final JmDNS jmDNS;

    @Autowired
    ThingDAO thingDAO;

    public ThingListener(JmDNS jmDNS) {
        this.jmDNS = jmDNS;
        logger.info("ThingListener loaded");
    }

    @Override
    public void serviceAdded(ServiceEvent serviceEvent) {
        ServiceInfo info = jmDNS.getServiceInfo(serviceEvent.getType(), serviceEvent.getName());
        if (info != null) {
            String url =
                    String.format("http://%s:%s",
                            info.getHostAddresses()[0],
                            info.getPort()
                    );
            String name = serviceEvent.getName();
            logger.info("Service added {} {}", name, url);
            Optional<Thing> thing = thingDAO.findByURL(url);
            if (thing.isPresent()) {
                thingDAO.setThingAlive(url, true);
            } else {
                thingDAO.save(new Thing(name, url, true));
            }
        }
    }

    @Override
    public void serviceRemoved(ServiceEvent serviceEvent) {
        ServiceInfo info = jmDNS.getServiceInfo(serviceEvent.getType(), serviceEvent.getName());
        if (info != null) {
            String url =
                    String.format("http://%s:%s",
                            info.getHostAddresses()[0],
                            info.getPort()
                    );

            thingDAO.setThingAlive(url, false);
            logger.info("Service removed {}", url);
        }
    }

    @Override
    public void serviceResolved(ServiceEvent serviceEvent) {
    }
}
