package controller;

import java.nio.file.Path;

public class ProcessingResult {
    private final Path inputFile;
    private final int recordCount;

    public ProcessingResult(Path inputFile, int recordCount) {
        this.inputFile = inputFile;
        this.recordCount = recordCount;
    }

    @Override
    public String toString() {
        return String.format("Processed %d records from %s", recordCount, inputFile);
    }

}
