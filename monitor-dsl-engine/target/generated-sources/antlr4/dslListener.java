// Generated from dsl.g4 by ANTLR 4.13.1

package com.example.dsl.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link dslParser}.
 */
public interface dslListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link dslParser#metricStmt}.
	 * @param ctx the parse tree
	 */
	void enterMetricStmt(dslParser.MetricStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#metricStmt}.
	 * @param ctx the parse tree
	 */
	void exitMetricStmt(dslParser.MetricStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#targetScope}.
	 * @param ctx the parse tree
	 */
	void enterTargetScope(dslParser.TargetScopeContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#targetScope}.
	 * @param ctx the parse tree
	 */
	void exitTargetScope(dslParser.TargetScopeContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(dslParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(dslParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#functionCallExpr}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpr(dslParser.FunctionCallExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#functionCallExpr}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpr(dslParser.FunctionCallExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(dslParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(dslParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(dslParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(dslParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(dslParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(dslParser.ValueContext ctx);
}