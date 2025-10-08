package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSONProcessor implements FileProcessor {


    @Override
    public ProcessingResult process(Path inputFile, Path outputDir) throws IOException {
     int count = 0;
        try(BufferedReader reader = Files.newBufferedReader(inputFile)) {
         String line;
         while ((line =reader.readLine())!=null) {
             count += line.split("\\{").length-1;
         }
         return new ProcessingResult(inputFile,count);
     }
    }
}
