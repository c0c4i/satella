package it.univr.satella.sampler;

import it.univr.satella.sensors.Sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SampleBlock {

    private int id;

    private final int blockSize;
    private final List<List<Sample>> samplesList;
    private int writeIndex;

    public SampleBlock(int id, int blockSize) {

        this.blockSize = blockSize;
        this.samplesList = new ArrayList<>();

        this.writeIndex = 0;
        this.id = id;
    }

    public void addSamples(List<Sample> samples) {
        if (writeIndex != blockSize) {
            samplesList.add(samples);
            writeIndex += 1;
        }
    }

    public Optional<List<Sample>> getLastSamples() {
        if (writeIndex != 0)
            return Optional.of(samplesList.get(writeIndex - 1));
        return Optional.empty();
    }

    public int getCapacity() {
        return samplesList.size();
    }

    public int getSize() {
        return writeIndex;
    }

    public int getId() {
        return id;
    }
}
