package com.cookbook.tutorial.service;

import com.cookbook.tutorial.internal.dsql.*;
import com.cookbook.tutorial.internal.dsql.Constants;
import org.junit.Before;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by Mulesoft.
 */
public class DsqlParserTest {
    DsqlParser parser;
    @Before
    public void setup(){
        parser = Parboiled.createParser(DsqlParser.class);
    }

    @Test
    public void validExpressionIngredient(){
        String input = "GET ALL FROM INGREDIENT";
        ParsingResult<?> result = new ReportingParseRunner(parser.Statement()).run(input);
        assertFalse(result.hasErrors());
    }

    @Test
    public void validExpressionRecipe(){
        String input = "GET ALL FROM RECIPE";
        ParsingResult<?> result = new ReportingParseRunner(parser.Statement()).run(input);
        assertFalse(result.hasErrors());
    }

    @Test
    public void validInvalidExpression(){
        String input = "GET id FROM FOO";
        ParsingResult<?> result = new ReportingParseRunner(parser.Statement()).run(input);
        assertTrue("Foo is not a valid Entity Type", result.hasErrors());
    }

    @Test
    public void validFieldExpression(){
        String input = "GET id,created,lastModified,name,quantity,unit,prepTime,cookTime,ingredients FROM RECIPE";
        ParsingResult<?> result = new ReportingParseRunner(parser.Statement()).run(input);
        assertFalse("Foo is not a valid Entity Type", result.hasErrors());
    }

    @Test
    public void validInvalidFieldExpression(){
        String input = "GET foo FROM INGREDIENT";
        ParsingResult<?> result = new ReportingParseRunner(parser.Statement()).run(input);
        assertTrue("foo is not a valid field", result.hasErrors());
    }

    @Test
    public void validWhereExpression(){
        String input = "GET name FROM INGREDIENT MATCHING id==1";
        ParsingResult<?> result = new ReportingParseRunner(parser.Statement()).run(input);
        assertFalse("Fail to process query", result.hasErrors());
    }

    @Test
    public void validWhereExpressionContains(){
        String input = "GET ALL FROM RECIPE MATCHING id contains 1";
        ParsingResult<?> result = new ReportingParseRunner(parser.Statement()).run(input);
        assertFalse("Fail to process query", result.hasErrors());
    }

    @Test
    public void getBasicQuery(){
        String input = "GET ALL FROM INGREDIENT";
        ParsingResult<?> result = new ReportingParseRunner(parser.Statement()).run(input);
        Dsql dsql = Dsql.newInstance(input);
        assertEquals(Constants.INGREDIENT,dsql.getEntity());
        assertTrue(dsql.getAllFields());
        assertEquals(1, dsql.getFields().size());
        assertEquals(0, dsql.getCriteria().size());
    }

    @Test
    public void getComplex(){
        String input = "GET id,created,lastModified,name,quantity,unit,prepTime,cookTime,ingredients FROM RECIPE MATCHING id==1";
        ParsingResult<?> result = new ReportingParseRunner(parser.Statement()).run(input);
        Dsql dsql = Dsql.newInstance(input);
        assertEquals(Constants.RECIPE,dsql.getEntity());
        assertFalse(dsql.getAllFields());
        assertEquals(9,dsql.getFields().size());
        assertEquals(1,dsql.getCriteria().size());
    }

    @Test(expected = RuntimeException.class)
    public void invalid(){
        String input = "Not Matching syntax";
        Dsql.newInstance(input);
    }
}
