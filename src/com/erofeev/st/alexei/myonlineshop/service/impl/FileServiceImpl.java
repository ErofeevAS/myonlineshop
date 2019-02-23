package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.service.FileService;

import java.io.*;
import java.nio.Buffer;

public class FileServiceImpl implements FileService {
    private static volatile FileService instance = null;

    private FileServiceImpl() {
    }

    public static FileService getInstance() {
        if (instance == null) {
            synchronized (FileService.class) {
                if (instance == null) {
                    instance = new FileServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public String[] getQueryFromFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                while (line != null) {
                    if (line.length() > 0) {
                        stringBuilder.append(line);
                    }
                    line = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not exist: " + file);
                e.getMessage();
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString().split(";");
    }
}
