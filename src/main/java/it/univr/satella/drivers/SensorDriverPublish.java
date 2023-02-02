package it.univr.satella.drivers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * All sensor drivers implementations with this annotation are
 * automatically loaded by the system at startup and added to the
 * sensor driver repository.
 */
@Qualifier
public @interface SensorDriverPublish { }
