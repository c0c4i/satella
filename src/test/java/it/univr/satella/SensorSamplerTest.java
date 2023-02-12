package it.univr.satella;

import it.univr.satella.sensors.Sample;
import it.univr.satella.sensors.SampleUnit;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SensorSamplerTest {

    /*
    private static final int BLOCK_SIZE = 100;
    private static SensorSampler sampler;

    @BeforeClass
    public static void initialize()
    {
        sampler = new SensorSampler(null, BLOCK_SIZE);

        // Only sample with a windspeed value
        List<Sample> samples = new ArrayList<>();
        samples.add(new Sample(SampleUnit.WindSpeed, -100.0f));
        sampler.addSamples(samples);

        // Add other samples, enough to fill a block
        for (int i = 0; i <= BLOCK_SIZE; i++) {

            // Create simple measurement list
            samples = new ArrayList<>();
            samples.add(new Sample(SampleUnit.Temperature, (float)(i + 0)));
            samples.add(new Sample(SampleUnit.Humidity,    (float)(i + 1)));

            sampler.addSamples(samples);
        }
    }

    @Test
    public void testGetLastSample() {

        assertEquals((float)(BLOCK_SIZE + 0), sampler.getLastSampleOfUnit(SampleUnit.Temperature), 0.0f);
        assertEquals((float)(BLOCK_SIZE + 1), sampler.getLastSampleOfUnit(SampleUnit.Humidity),    0.0f);

        // There are no samples of humidity so the result shuld be zero
        assertEquals(0.0f, sampler.getLastSampleOfUnit(SampleUnit.Pressure), 0.0f);

        // This it's present only in the first sample
        assertEquals(-100.0f, (int)sampler.getLastSampleOfUnit(SampleUnit.WindSpeed), 0.0f);
    }
     */
}
