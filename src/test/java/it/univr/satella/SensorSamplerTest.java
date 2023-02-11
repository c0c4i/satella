package it.univr.satella;

import it.univr.satella.sampler.SampleBlock;
import it.univr.satella.sampler.SensorSampler;
import it.univr.satella.sensors.Sample;
import it.univr.satella.sensors.SampleUnit;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class SensorSamplerTest {

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

    @Test
    public void testGetBlocks() {

        assertEquals(2, sampler.getBlocksCount());
        SampleBlock block = sampler.getCurrentBlock();

        assertEquals(1, block.getId());
        assertEquals(BLOCK_SIZE, block.getCapacity());
        assertEquals(2, block.getSize());

        Optional<List<Sample>> lastSamplesOpt = block.getLastSamples();
        assertTrue(lastSamplesOpt.isPresent());
        List<Sample> lastSamples = lastSamplesOpt.get();

        assertTrue(lastSamples.stream().anyMatch(x -> x.unit == SampleUnit.Temperature && x.value == (float)(BLOCK_SIZE + 0)));
        assertTrue(lastSamples.stream().anyMatch(x -> x.unit == SampleUnit.Humidity    && x.value == (float)(BLOCK_SIZE + 1)));
        assertFalse(lastSamples.stream().anyMatch(x -> x.unit == SampleUnit.WindSpeed));
    }
}
