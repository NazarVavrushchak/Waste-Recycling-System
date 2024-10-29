package com.ecowaste.recycling.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadUtil {
    private static final String UPLOAD_DIR = "E:\\Waste-Recycling-System";

    public static String saveFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!uploadPath.toFile().exists()) {
            uploadPath.toFile().mkdirs();
        }

        File destinationFile = uploadPath.resolve(fileName).toFile();
        file.transferTo(destinationFile);

        return "E:\\Waste-Recycling-System" + fileName;
    }
}