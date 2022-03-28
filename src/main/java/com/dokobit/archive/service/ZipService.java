package com.dokobit.archive.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.dokobit.archive.exception.ValidationErrorCodes.*;

@Service
public class ZipService extends CommonService {

    public void zipFiles(OutputStream out, List<MultipartFile> files) {
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(out);
            files.forEach(file -> {
                try {
                    validateFileName(file.getOriginalFilename());
                    validateFileSize(file.getSize());

                    BufferedInputStream bufferedInputStream = new BufferedInputStream(file.getInputStream());
                    ZipEntry entry = new ZipEntry(Objects.requireNonNull(file.getOriginalFilename()));
                    zipOutputStream.putNextEntry(entry);
                    int length;
                    byte[] buffer = new byte[1024];
                    while((length = bufferedInputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, length);
                    }
                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    FILE_ARCHIVE_WENT_WRONG.throwError();
                }
            });
            zipOutputStream.close();
        } catch (IOException e) {
            FILE_ARCHIVE_WENT_WRONG.throwError();
        }
    }
}
