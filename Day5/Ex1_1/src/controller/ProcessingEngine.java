package controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class ProcessingEngine {
    private final ExecutorService executor;
    private final Map<String,FileProcessor> processors;
// tạo thread pool
public ProcessingEngine(ExecutorService executor, Map<String,FileProcessor> processors) {
    this.executor = Executors.newFixedThreadPool(4);
    this.processors = new HashMap<>();

    //đăng ký processor
    this.processors.put("json", new JSONProcessor());
    this.processors.put("csv", new CSVProcessor());
    this.processors.put("xml", new XMLProcessor());
}

    //Lấy đuôi file
    private String getExtension(Path inputFile){
        String name = inputFile.getFileName().toString();
        int index = name.lastIndexOf('.');
        if(index == -1) return "";
        return name.substring(index+1);
    }
//xử lý file
//xử lý file
public CompletableFuture<ProcessingResult> processFile(Path file, Path outputDir) {
    return CompletableFuture.supplyAsync(()->{
        try{
            String ext = getExtension(file);
            FileProcessor process = processors.get(ext);
            if (process==null){
                throw new IllegalArgumentException("Unsupported file format: " +ext);
            }
            return process.process(file,outputDir);
        }catch(IOException e){
            throw new CompletionException(e);
        }


    },executor);
}


    public void shutdown(){
        executor.shutdown();
    }

}
