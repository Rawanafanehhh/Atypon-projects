package com.Entities;


import com.FileSystemManegers.FileReader;
import com.FileSystemManegers.FileWriter;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

public class Indexing {
    private TreeMap<String , ArrayList<String>> indexing;
    private File file;

    public Indexing(File indexingFile) throws IOException {
        FileReader reader = new FileReader();
        String jsonTree = reader.readFileAsString(indexingFile);
        Gson gson = new Gson();
        this.indexing = gson.fromJson(jsonTree , TreeMap.class);
        this.file = indexingFile;
    }


    public synchronized void  addToTheTree(String indexingValue , String id) throws IOException {
        if (indexing.get(indexingValue) == null)
            indexing.put(indexingValue, new ArrayList<>());
        indexing.get(indexingValue).add(id);
        save();
    }
    public synchronized void removeFromTheTree(String indexingValue , String id) throws IOException {
        if (indexing.get(indexingValue).size() <= 1)
            indexing.remove(indexingValue);
        else indexing.get(indexingValue).remove(id);
        save();
    }

    private synchronized void save() throws IOException {
        FileWriter writer = new FileWriter();
        writer.writeFile(file , new Gson().toJson(indexing));
    }

    public LinkedList<String> getMatchingIds(String value) {
        LinkedList<String> matchingIds = new LinkedList<>();

        for (String indexingValue : indexing.keySet()) {
            if (indexingValue.equals(value)) {
                matchingIds.addAll(indexing.get(indexingValue));
            }
        }

        return matchingIds;
    }
}