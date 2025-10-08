
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import controller.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Create processing engine
            controller.ProcessingEngine engine = new controller.ProcessingEngine(null, new HashMap<>());

            // Paths to the data files
            Path xmlFile = Paths.get("src/data.xml");
            Path jsonFile = Paths.get("src/data.json");
            Path csvFile = Paths.get("src/data.csv");
            
            // Print info about processing
            System.out.println("Starting concurrent processing of 3 files:");
            System.out.println("- " + xmlFile.toAbsolutePath());
            System.out.println("- " + jsonFile.toAbsolutePath());
            System.out.println("- " + csvFile.toAbsolutePath());
            
            // Create completable futures for all three files
            CompletableFuture<ProcessingResult> xmlFuture = engine.processFile(xmlFile, null);
            CompletableFuture<ProcessingResult> jsonFuture = engine.processFile(jsonFile, null);
            CompletableFuture<ProcessingResult> csvFuture = engine.processFile(csvFile, null);
            
            // Combine all futures into a list
            List<CompletableFuture<ProcessingResult>> allFutures = 
                List.of(xmlFuture, jsonFuture, csvFuture);
            
            // Create a combined future that completes when all tasks are done
            CompletableFuture<Void> allDone = CompletableFuture.allOf(
                xmlFuture, jsonFuture, csvFuture
            );
            
            // Wait for all processing to complete
            allDone.join();
            
            // Get and print all results
            System.out.println("\nResults from all processed files:");
            
            // Print XML result
            System.out.println("\nXML Result:");
            System.out.println(xmlFuture.get());
            
            // Print JSON result
            System.out.println("\nJSON Result:");
            System.out.println(jsonFuture.get());
            
            // Print CSV result
            System.out.println("\nCSV Result:");
            System.out.println(csvFuture.get());
            
            // Shutdown the engine
            System.out.println("\nAll files processed successfully. Shutting down...");
            engine.shutdown();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}