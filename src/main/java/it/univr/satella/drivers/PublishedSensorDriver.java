package it.univr.satella.drivers;

import org.springframework.stereotype.Component;

/**
 * All sensor drivers with this annotation are
 * automatically loaded by the system at startup
 */
@Component
public @interface PublishedSensorDriver { }
