package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CSVProcessor implements FileProcessor {


    @Override
    public ProcessingResult process(Path inputFile, Path outputDir) throws IOException {
        int count = 0;
        try(BufferedReader reader = Files.newBufferedReader(inputFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                count++;
            }
            return new ProcessingResult(inputFile, count);
        }
    }
}
