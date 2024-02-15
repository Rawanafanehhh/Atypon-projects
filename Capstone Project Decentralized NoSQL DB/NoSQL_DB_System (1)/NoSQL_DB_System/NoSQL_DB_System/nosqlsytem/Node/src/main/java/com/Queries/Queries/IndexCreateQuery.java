package com.Queries.Queries;
import com.Action.CreateIndexAction;

public class IndexCreateQuery extends Query{
    public IndexCreateQuery(String databaseName , String indexingParam){
        addActionToQueue(new CreateIndexAction(databaseName , indexingParam));
    }
}