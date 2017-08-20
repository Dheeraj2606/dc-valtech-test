/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.valtech.common.test;

import java.awt.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.valtech.file.exception.StorageException;
import com.valtech.file.exception.StorageProperties;
import com.valtech.file.object.StreetReport;
import com.valtech.file.serviceImpl.FileSystemServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dave Syer
 *
 */
public class FileSystemServiceTests {

    private StorageProperties properties = new StorageProperties();
    private FileSystemServiceImpl service;

    @Before
    public void init() {
        properties.setLocation("target/files/" + Math.abs(new Random().nextLong()));
        service = new FileSystemServiceImpl(properties);
        service.init();
    }

    @Test
    public void loadNonExistent() {
        assertThat(service.load("foo.txt")).doesNotExist();
    }

    @Test
    public void saveAndLoad() {
        service.processFile(new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
                "1 2 3".getBytes()));
        assertThat(service.load("foo.txt")).exists();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void saveNotPermittedBlankFile() {
        service.processFile(new MockMultipartFile("foo", "../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, " ".getBytes()));
    }
    @Test(expected = NumberFormatException.class)
    public void saveNotPermitted() {
        service.processFile(new MockMultipartFile("foo", "../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes()));
    }
    
    @Test(expected = StorageException.class)
    public void fileProcessFailDuplciateValues() {
        service.processFile(new MockMultipartFile("foo", "../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "1 1 2 3".getBytes()));
    }
    
    @Test(expected = StorageException.class)
    public void fileProcessFailOneMissing() {
        service.processFile(new MockMultipartFile("foo", "../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "2 3".getBytes()));
    }
    
    @Test(expected = NumberFormatException.class)
    public void fileProcessFailSpecialChars() {
        service.processFile(new MockMultipartFile("foo", "../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "2 3 &".getBytes()));
    }
    
    @Test(expected = NumberFormatException.class)
    public void fileProcessFailChars() {
        service.processFile(new MockMultipartFile("foo", "../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "a b c d".getBytes()));
    }
    
    
    @Test(expected = StorageException.class)
    public void fileProcessFailNegativeNumber() {
        service.processFile(new MockMultipartFile("foo", "../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "1 2 -3".getBytes()));
    }
    
    @Test(expected = NumberFormatException.class)
    public void fileProcessFailDecimal() {
        service.processFile(new MockMultipartFile("foo", "../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "1 2 3.3".getBytes()));
    }
    
    @Test(expected = StorageException.class)
    public void fileProcessFailSequenceMissing() {
        service.processFile(new MockMultipartFile("foo", "../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "1 3 5 2 6".getBytes()));
    }
    
    @Test
    public void savePermitted() {
        service.processFile(new MockMultipartFile("foo", "bar/../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "1 2 3".getBytes()));
    }

}
