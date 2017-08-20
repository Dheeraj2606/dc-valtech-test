package com.valtech.file.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.valtech.common.util;
import com.valtech.controller.FileService;
import com.valtech.file.exception.StorageException;
import com.valtech.file.exception.StorageFileNotFoundException;
import com.valtech.file.exception.StorageProperties;
import com.valtech.file.object.StreetReport;;

@Service
public class FileSystemServiceImpl implements FileService {

    private final Path rootLocation;

    @Autowired
    public FileSystemServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public StreetReport processFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        StreetReport streetReport = new StreetReport();
        try {
        	/*
        	isTextFile : This check is to make sure only .txt file are processed 
        	isEmpty : This check if file has data
        	*/
        	if(file.isEmpty() || !isTextFile(filename) )
        		throw new StorageException("Invalid input file: " + filename);    
        	ArrayList<Integer> houseNumbers = util.splitString(readOnlyFirstLine(file.getInputStream()).toString(), " ");
        	
        	//check if contains 1 & no duplicates
        	if(util.hasDuplicate(houseNumbers))
        		throw new StorageException("Has Duplicates " + filename); 
        	else
        		if(!util.hasFirstHouseNumberOne(houseNumbers))
        			throw new StorageException("1 missing " + filename); 
        	
        	//Setting StreetPlan Object for report
        	streetReport.setAllHouseNumber(houseNumbers);
        	streetReport.setTotalCount(houseNumbers.size());
        	util.generateEvenOddList(streetReport);
        	streetReport.setDeliveryOrderOpt2(util.setDeliverySequenceOpt2(streetReport.getLeftOddNorthSideHouses(), streetReport.getRightEvenSouthSideHouses()));
        	streetReport.setCrossoverCount(util.getCrossoverCount(streetReport.getLeftOddNorthCount(),streetReport.getRightEvenSouthCount()));
        	
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
      
  }
		return streetReport;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            
          
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

	/**
	 * This methods checks if uploaded file is .txt or not
	 */
    @Override
	public boolean isTextFile(String filename) {
		 String[] fileFrags = filename.split("\\.");
         String extension = fileFrags[fileFrags.length-1];
         if(extension.equals("txt"))
        	 return true;
		return false;
	}

	@Override
	public String readOnlyFirstLine(InputStream stream) throws IOException {
		return new BufferedReader(new InputStreamReader(stream)).readLine();
	}
	
	
}
