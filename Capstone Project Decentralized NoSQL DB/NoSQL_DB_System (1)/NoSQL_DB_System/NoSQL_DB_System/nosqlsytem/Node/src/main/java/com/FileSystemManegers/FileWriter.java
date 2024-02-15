package com.FileSystemManegers;

import java.io.*;
import java.util.List;

public class FileWriter {


    public void writeFile(File file , String data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        writer.write(data);
        writer.flush();
        writer.close();
    }

}