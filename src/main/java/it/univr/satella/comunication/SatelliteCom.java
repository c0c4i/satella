package it.univr.satella.comunication;

import org.springframework.stereotype.Component;

/**
 * Fake satellite com link that always succeeds
 */
@Component
public class SatelliteCom implements ISatelliteCom {
    @Override
    public boolean send(Object obj, String url) {
        return true;
    }
}
