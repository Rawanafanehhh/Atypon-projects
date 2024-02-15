package com.Action;

import com.Control.IndexingController;
import com.FileSystemManegers.DatabaseFileManager;
import com.Entities.Document;
import com.Entities.Indexing;
import com.FileSystemManegers.FileReader;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.File;

public class ReadByIndexAction extends Action {
    private String indexingName;
    private String value;
    private DatabaseFileManager manager;

    public ReadByIndexAction(String databaseName, String indexingName, String value) {
        super(databaseName);
        this.indexingName = indexingName;
        this.value = value;
        this.manager = new DatabaseFileManager();
    }

    @Override
    public String doAction() throws Exception {
        IndexingController pool = IndexingController.getInstance();
        Indexing indexing = pool.getIndexingFile(getDatabaseName(), indexingName);

        if (indexing == null) {
            throw new Exception("Indexing information not found for the specified database and indexingName.");
        }

        LinkedList<String> matchingIds = indexing.getMatchingIds(value);
        if (matchingIds == null) {
            return "There is no matching";
        }
        List<Document> matchingDocuments = new LinkedList<>();
        for (String id : matchingIds) {
            FileReader reader =new FileReader();
            String jsonDocument = reader.readFileAsString(manager.getDocumentFile(getDatabaseName(), id));
            Document document = new Document(getDatabaseName(), id, jsonDocument);
            matchingDocuments.add(document);
        }
        List<String> matchingDocumentStrings = new LinkedList<>();
        for (Document document : matchingDocuments) {
            matchingDocumentStrings.add(document.toString());
        }

        return String.join("\n", matchingDocumentStrings);
    }
}
