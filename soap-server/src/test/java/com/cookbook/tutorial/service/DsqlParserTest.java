package com.cookbook.tutorial.service;

import com.cookbook.tutorial.internal.dsql.DsqlParser;
import org.junit.Before;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    public void parserItemsTest(){
        Pattern p = Pattern.compile("GET (.*) FROM ([\\w]*)( MATCHING (.*))?");
        Matcher m = p.matcher("GET ALL FROM RECIPE MATCHING id contains 1");
        boolean b = m.matches();
        assertTrue(b);

        System.out.println(m.group(1).trim());
        System.out.println(m.group(2).trim());
        System.out.println(m.group(4).trim());

        m = p.matcher("GET ALL FROM RECIPE");
        b = m.matches();
        assertTrue(b);
        System.out.println(m.group(1));
        System.out.println(m.group(2));

        m = p.matcher("GET id,created,lastModified,name,quantity,unit,prepTime,cookTime,ingredients FROM RECIPE");
        b = m.matches();
        assertTrue(b);
        System.out.println(m.group(1).split(",").length);
        System.out.println(m.group(2));
    }
}
