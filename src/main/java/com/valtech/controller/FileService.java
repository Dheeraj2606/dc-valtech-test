package com.valtech.controller;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.valtech.file.object.StreetReport;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileService {

    void init();

    StreetReport processFile(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);
    
    boolean isTextFile(String filename);
          
    String readOnlyFirstLine(InputStream stream) throws IOException;
    
    void deleteAll();

}
