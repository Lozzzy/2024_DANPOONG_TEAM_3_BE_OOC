package com.outofcity.server.global.exception.dto;


import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class CustomMultipartFile implements MultipartFile {
    private byte[] input;
    private String filename;
    private String contentType;

    public CustomMultipartFile(byte[] input,String filename) {
        this.input = input;
        this.filename = filename;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return filename;
    }

    @Override
    public String getContentType() {
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".JPG") || filename.endsWith(".JPEG")) {
            return "image/jpeg";
        } else if (filename.endsWith(".png") || filename.endsWith("PNG")) {
            return "image/png";
        } else {
            return null;
        }
    }

    @Override
    public boolean isEmpty() {
        return input == null || input.length == 0;
    }

    @Override
    public long getSize() {
        return input.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return input;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(input);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try(FileOutputStream fos = new FileOutputStream(dest)){
            fos.write(input);
        }
    }
}
