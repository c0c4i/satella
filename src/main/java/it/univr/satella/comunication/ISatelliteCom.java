package it.univr.satella.comunication;

/**
 * Interface for all comunicators, that is objects that send information
 * to the control center
 */
public interface ISatelliteCom {

    /**
     * Send an object to the the specified url.
     * Return true on success
     */
    boolean send(Object obj, String url);
}
