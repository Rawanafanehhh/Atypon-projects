package com.Queries.QueriesManegers.Validators.syntax;

public class DocReadByIndexValidator implements QueryValidator {
    private String[] queryAttributes;

    public DocReadByIndexValidator(String query) throws Exception {
        try {
            this.queryAttributes = query.split(" ");
        }catch (Exception e){throw new Exception("Invalid Query");}

    }
    private boolean isValidLength() {
        if (queryAttributes.length!=5)
            return false;
        return true;
    }

    @Override
    public boolean isValidQuery() {
        if (!isValidLength())
            return false;
        if (!queryAttributes[0].equals("read"))
            return false;
        if (!queryAttributes[1].equals("from"))
            return false;
        if (!queryAttributes[3].equals("where"))
            return false;
        return true;
    }

}
