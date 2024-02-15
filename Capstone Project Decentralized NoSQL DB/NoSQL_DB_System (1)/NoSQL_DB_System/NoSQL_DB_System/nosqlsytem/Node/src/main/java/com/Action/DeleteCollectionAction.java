package com.Action;

import com.FileSystemManegers.DatabaseFileManager;

public class DeleteCollectionAction extends Action{
    private DatabaseFileManager manager;
    public DeleteCollectionAction(String databaseName ) {
        super(databaseName);
        this.manager = new DatabaseFileManager();
    }

    @Override
    public String doAction() throws Exception {
        manager.deleteDataBase(getDatabaseName());
        return "OK";
    }
}