package com.cookbook.tutorial.internal.dsql;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

/**
 * Created by Mulesoft.
 */
@BuildParseTree
public class DsqlParser extends BaseParser<Object>{

    public Rule Statement(){
        return Sequence(
                String("GET"),
                Ch(' '),
                Fields(),
                Ch(' '),
                String("FROM"),
                Ch(' '),
                Entities(),
                Clause(),
                EOI
        );
    }

    public Rule Fields() {
        return FirstOf(
                String("ALL"), OneOrMore(
                        Sequence(
                                Field(), ZeroOrMore(Ch(','), Field()))
                )
        );
    }

    public Rule Entities(){
        return FirstOf(String(Constants.INGREDIENT),String(Constants.RECIPE));
    }

    public Rule Clause(){
        return Optional(
                Sequence(
                        Ch(' '),
                        String("MATCHING"),
                        Ch(' '),
                        Sequence(
                                Comparison(),ZeroOrMore(Ch(','),Comparison())
                        )
                )
        );
    }

    public Rule Field(){
        return FirstOf(
                String("id"),
                String("created"),
                String("lastModified"),
                String("name"),
                String("quantity"),
                String("unit"),
                String("prepTime"),
                String("cookTime"),
                String("ingredients")
        );
    }


    public Rule Comparison(){
        return FirstOf(
                Sequence(AlphaNumeric(), Spacing(), Operator(),Spacing(), Field()), Sequence(Field(),Spacing(), Operator(),Spacing(), AlphaNumeric()));
    }

    public Rule Operator(){
        return FirstOf(
                String("=="),
                String("<>"),
                String(">"),
                String("<"),
                String(">="),
                String("<="),
                String("contains")
        );
    }

    public Rule AlphaNumeric(){
        return OneOrMore(FirstOf(CharRange('a','z'),CharRange('0','9'))
        );
    }

    public Rule Spacing(){
        return ZeroOrMore(Ch(' '));
    }

}
