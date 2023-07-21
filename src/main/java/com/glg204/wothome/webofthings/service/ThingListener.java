package com.glg204.wothome.webofthings.service;

import com.glg204.wothome.webofthings.dao.ThingDAO;
import com.glg204.wothome.webofthings.domain.Thing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.util.Optional;

public class ThingListener implements ServiceListener {

    private Logger logger = LoggerFactory.getLogger(ThingListener.class);

    private final JmDNS jmDNS;
    private ThingDAO thingDAO;


    public ThingListener(ThingDAO thingDAO, JmDNS jmDNS) {
        this.jmDNS = jmDNS;
        this.thingDAO = thingDAO;
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

            Optional<Thing> thing = thingDAO.getByURL(url);
            if (thing.isPresent()) {
                thingDAO.setThingAlive(url, true);
            } else {
                thingDAO.save(new Thing(name, url, true));
            }
            logger.info("Service added {} {}", name, url);

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
        } else {//jmDNS lib bug...sometimes cant resolve getServiceInfo...
            Optional<Thing> thing = thingDAO.getByName(serviceEvent.getName());
            thing.ifPresent(value -> {
                thingDAO.setThingAlive(value.getUrl(), false);
                logger.info("- Service removed {}", value.getUrl());
            });

        }
    }

    @Override
    public void serviceResolved(ServiceEvent serviceEvent) {
        logger.info(" Service resolved {}", serviceEvent.getInfo().toString());
    }
}
