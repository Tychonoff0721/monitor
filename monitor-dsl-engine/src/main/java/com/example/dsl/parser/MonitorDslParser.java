package com.example.dsl.parser;

import com.example.dsl.ast.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class MonitorDslParser {

    public MetricStmt parse(String code) {
        dslLexer lexer = new dslLexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        dslParser parser = new dslParser(tokens);
        
        dslParser.MetricStmtContext tree = parser.metricStmt();
        
        return new AstBuilder().visitMetricStmt(tree);
    }
}
