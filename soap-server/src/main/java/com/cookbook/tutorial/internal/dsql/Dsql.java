package com.cookbook.tutorial.internal.dsql;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mulesoft.
 */
public class Dsql implements CookBookQuery{
    private final Pattern queryPattern = Pattern.compile("GET (.*) FROM ([\\w]*)( MATCHING (.*))?");

    private final Matcher matcher;

    public static Dsql newInstance(String query){
        return new Dsql(query);
    }

    private Dsql(String query){
        matcher=queryPattern.matcher(query);
        if(!matcher.matches()){
            throw new RuntimeException("Don't know how to process:"+query);
        }
    }

    @Override
    public boolean getAllFields() {
        return matcher.group(1).equals(Constants.ALL);
    }

    @Override
    public List<String> getFields() {
        return Arrays.asList(matcher.group(1).split(","));
    }

    @Override
    public String getEntity() {
        return matcher.group(2);
    }

    @Override
    public List<String> getCriteria() {
        if(matcher.groupCount()==4 && matcher.group(4)!=null) {
            return Arrays.asList(matcher.group(4).split(","));
        }
        return Collections.EMPTY_LIST;
    }
}
