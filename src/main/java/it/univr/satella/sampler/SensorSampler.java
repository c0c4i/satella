package it.univr.satella.sampler;

import it.univr.satella.sensors.Sample;
import it.univr.satella.sensors.SampleUnit;
import it.univr.satella.station.StationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SensorSampler {
    private static Logger log = LoggerFactory.getLogger(SensorSampler.class);

    private StationManager stationManager;
    private int currentTimeSlot;
    private final int blockSize;
    private int currentBlock;

    /**
     * Block containing the samples
     */
    private List<SampleBlock> blocks;

    /**
     * Map for storing the last sample of a specific unit
     */
    private HashMap<SampleUnit, Float> lastSampleValue = new HashMap<>();

    @Autowired
    public SensorSampler(
            StationManager stationManager,
            @Value("${sampler.block_size}") int blockSize)
    {
        this.stationManager = stationManager;
        this.blocks = new ArrayList<>();
        this.blocks.add(new SampleBlock(currentBlock, blockSize));
        this.blockSize = blockSize;
        this.currentTimeSlot = 0;
        this.currentBlock = 0;
    }

    /**
     * Sample sensors every 1s
     */
    @Scheduled(fixedDelay = 1000)
    public void sample() {
        List<Sample> samples = stationManager.measure();
        log.info("Collected " + samples.size() + " samples at timeslot " + currentTimeSlot + " for block " + currentBlock);
        addSamples(samples);
        currentTimeSlot += 1;
    }

    /**
     * Insert a collection of samples in the current block
     * and updates the last sample of unit map
     * @param samples samples of the current timeslot
     */
    public void addSamples(List<Sample> samples) {
        for (Sample sample : samples) {
            lastSampleValue.put(sample.unit, sample.value);
        }

        if (currentTimeSlot / blockSize != currentBlock) {
            blocks.add(new SampleBlock(++currentBlock, blockSize));
            log.info("Added a new block to sampler with id " + currentBlock);
        }

        blocks.get(currentBlock).addSamples(samples);
        currentTimeSlot += 1;
    }

    /**
     * Retrieves the last value of the specified unit
     */
    public float getLastSampleOfUnit(SampleUnit unit) {
        if (lastSampleValue.containsKey(unit))
            return lastSampleValue.get(unit);
        return 0.0f;
    }

    public int getBlocksCount() {
        return blocks.size();
    }

    public SampleBlock getCurrentBlock() {
        return blocks.get(currentBlock);
    }
}
