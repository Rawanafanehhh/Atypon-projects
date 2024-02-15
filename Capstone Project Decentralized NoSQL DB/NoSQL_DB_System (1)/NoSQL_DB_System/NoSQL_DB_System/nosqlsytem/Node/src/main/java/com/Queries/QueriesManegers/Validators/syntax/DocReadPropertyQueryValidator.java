package com.Queries.QueriesManegers.Validators.syntax;

public class DocReadPropertyQueryValidator implements QueryValidator {
    private String[] queryAttributes;
    public DocReadPropertyQueryValidator(String query)throws Exception {
        try {
            this.queryAttributes = query.split(" ");
        }catch (Exception e){throw new Exception("Invalid Query");}

    }
    private boolean isValidLength() {
        if (queryAttributes.length!=7)
            return false;
        return true;
    }

    @Override
    public boolean isValidQuery() {
        if (!isValidLength())
            return false;
        if (!queryAttributes[0].equals("read"))
            return false;
        if (!queryAttributes[1].equals("property"))
            return false;
        if (!queryAttributes[3].equals("on"))
            return false;
        if (!queryAttributes[5].equals("from"))
            return false;
        return true;
    }

}
