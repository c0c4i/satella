package it.univr.satella.drivers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * All sensor drivers with this annotation are
 * automatically loaded by the system at startup
 */
@Qualifier
public @interface SensorDriverPublish { }
