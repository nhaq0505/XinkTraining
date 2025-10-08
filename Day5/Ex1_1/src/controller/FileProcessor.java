package controller;

import java.io.IOException;
import java.nio.file.Path;

interface FileProcessor {
    ProcessingResult process(Path inputFile,Path outputDir) throws IOException;
}
