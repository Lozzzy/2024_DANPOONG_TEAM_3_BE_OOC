package com.outofcity.server.utils;

import com.outofcity.server.global.exception.BusinessException;
import com.outofcity.server.global.exception.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class MultipartFileResource extends InputStreamResource {

    private final String filename;

    public MultipartFileResource(MultipartFile file) {
        super(getInputStream(file));
        this.filename = file.getOriginalFilename();
    }

    public static InputStream getInputStream(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            throw new BusinessException(ErrorMessage.BAD_IMAGE_FILE);
        }
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() {
        try {
            return getInputStream().available();
        } catch (IOException e) {
            log.error(e.getMessage());
            return -1;
        }
    }
}
