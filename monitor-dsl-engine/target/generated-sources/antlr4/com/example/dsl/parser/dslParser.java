// Generated from com/example/dsl/parser/dsl.g4 by ANTLR 4.13.1
package com.example.dsl.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class dslParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, METRIC=7, ON=8, SERVICE=9, 
		COMPONENT=10, INSTANCE=11, CLUSTER=12, AS=13, BOOLEAN=14, IDENTIFIER=15, 
		STRING_LITERAL=16, NUMBER=17, WS=18;
	public static final int
		RULE_metricStmt = 0, RULE_targetScope = 1, RULE_expr = 2, RULE_functionCallExpr = 3, 
		RULE_argumentList = 4, RULE_argument = 5, RULE_value = 6;
	private static String[] makeRuleNames() {
		return new String[] {
			"metricStmt", "targetScope", "expr", "functionCallExpr", "argumentList", 
			"argument", "value"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'->'", "'('", "')'", "','", "'='", "'METRIC'", "'ON'", 
			"'SERVICE'", "'COMPONENT'", "'INSTANCE'", "'CLUSTER'", "'AS'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, "METRIC", "ON", "SERVICE", 
			"COMPONENT", "INSTANCE", "CLUSTER", "AS", "BOOLEAN", "IDENTIFIER", "STRING_LITERAL", 
			"NUMBER", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "dsl.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public dslParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MetricStmtContext extends ParserRuleContext {
		public Token name;
		public TerminalNode METRIC() { return getToken(dslParser.METRIC, 0); }
		public TerminalNode ON() { return getToken(dslParser.ON, 0); }
		public TargetScopeContext targetScope() {
			return getRuleContext(TargetScopeContext.class,0);
		}
		public TerminalNode AS() { return getToken(dslParser.AS, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(dslParser.IDENTIFIER, 0); }
		public MetricStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_metricStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterMetricStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitMetricStmt(this);
		}
	}

	public final MetricStmtContext metricStmt() throws RecognitionException {
		MetricStmtContext _localctx = new MetricStmtContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_metricStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(14);
			match(METRIC);
			setState(15);
			((MetricStmtContext)_localctx).name = match(IDENTIFIER);
			setState(16);
			match(ON);
			setState(17);
			targetScope();
			setState(18);
			match(AS);
			setState(19);
			expr();
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(20);
				match(T__0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TargetScopeContext extends ParserRuleContext {
		public Token type;
		public Token name;
		public TerminalNode STRING_LITERAL() { return getToken(dslParser.STRING_LITERAL, 0); }
		public TerminalNode SERVICE() { return getToken(dslParser.SERVICE, 0); }
		public TerminalNode COMPONENT() { return getToken(dslParser.COMPONENT, 0); }
		public TerminalNode INSTANCE() { return getToken(dslParser.INSTANCE, 0); }
		public TerminalNode CLUSTER() { return getToken(dslParser.CLUSTER, 0); }
		public TargetScopeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_targetScope; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterTargetScope(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitTargetScope(this);
		}
	}

	public final TargetScopeContext targetScope() throws RecognitionException {
		TargetScopeContext _localctx = new TargetScopeContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_targetScope);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			((TargetScopeContext)_localctx).type = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 7680L) != 0)) ) {
				((TargetScopeContext)_localctx).type = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(24);
			((TargetScopeContext)_localctx).name = match(STRING_LITERAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public List<FunctionCallExprContext> functionCallExpr() {
			return getRuleContexts(FunctionCallExprContext.class);
		}
		public FunctionCallExprContext functionCallExpr(int i) {
			return getRuleContext(FunctionCallExprContext.class,i);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			functionCallExpr();
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(27);
				match(T__1);
				setState(28);
				functionCallExpr();
				}
				}
				setState(33);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionCallExprContext extends ParserRuleContext {
		public Token functionName;
		public TerminalNode IDENTIFIER() { return getToken(dslParser.IDENTIFIER, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public FunctionCallExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCallExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterFunctionCallExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitFunctionCallExpr(this);
		}
	}

	public final FunctionCallExprContext functionCallExpr() throws RecognitionException {
		FunctionCallExprContext _localctx = new FunctionCallExprContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_functionCallExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			((FunctionCallExprContext)_localctx).functionName = match(IDENTIFIER);
			setState(35);
			match(T__2);
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 245760L) != 0)) {
				{
				setState(36);
				argumentList();
				}
			}

			setState(39);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentListContext extends ParserRuleContext {
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitArgumentList(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			argument();
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(42);
				match(T__4);
				setState(43);
				argument();
				}
				}
				setState(48);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentContext extends ParserRuleContext {
		public Token argName;
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(dslParser.IDENTIFIER, 0); }
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitArgument(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_argument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(49);
				((ArgumentContext)_localctx).argName = match(IDENTIFIER);
				setState(50);
				match(T__5);
				}
			}

			setState(53);
			value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(dslParser.STRING_LITERAL, 0); }
		public TerminalNode NUMBER() { return getToken(dslParser.NUMBER, 0); }
		public TerminalNode BOOLEAN() { return getToken(dslParser.BOOLEAN, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 212992L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0012:\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000\u0016"+
		"\b\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0005\u0002\u001e\b\u0002\n\u0002\f\u0002!\t\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0003\u0003&\b\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004-\b\u0004\n\u0004\f\u0004"+
		"0\t\u0004\u0001\u0005\u0001\u0005\u0003\u00054\b\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0000\u0000\u0007\u0000\u0002"+
		"\u0004\u0006\b\n\f\u0000\u0002\u0001\u0000\t\f\u0002\u0000\u000e\u000e"+
		"\u0010\u00117\u0000\u000e\u0001\u0000\u0000\u0000\u0002\u0017\u0001\u0000"+
		"\u0000\u0000\u0004\u001a\u0001\u0000\u0000\u0000\u0006\"\u0001\u0000\u0000"+
		"\u0000\b)\u0001\u0000\u0000\u0000\n3\u0001\u0000\u0000\u0000\f7\u0001"+
		"\u0000\u0000\u0000\u000e\u000f\u0005\u0007\u0000\u0000\u000f\u0010\u0005"+
		"\u000f\u0000\u0000\u0010\u0011\u0005\b\u0000\u0000\u0011\u0012\u0003\u0002"+
		"\u0001\u0000\u0012\u0013\u0005\r\u0000\u0000\u0013\u0015\u0003\u0004\u0002"+
		"\u0000\u0014\u0016\u0005\u0001\u0000\u0000\u0015\u0014\u0001\u0000\u0000"+
		"\u0000\u0015\u0016\u0001\u0000\u0000\u0000\u0016\u0001\u0001\u0000\u0000"+
		"\u0000\u0017\u0018\u0007\u0000\u0000\u0000\u0018\u0019\u0005\u0010\u0000"+
		"\u0000\u0019\u0003\u0001\u0000\u0000\u0000\u001a\u001f\u0003\u0006\u0003"+
		"\u0000\u001b\u001c\u0005\u0002\u0000\u0000\u001c\u001e\u0003\u0006\u0003"+
		"\u0000\u001d\u001b\u0001\u0000\u0000\u0000\u001e!\u0001\u0000\u0000\u0000"+
		"\u001f\u001d\u0001\u0000\u0000\u0000\u001f \u0001\u0000\u0000\u0000 \u0005"+
		"\u0001\u0000\u0000\u0000!\u001f\u0001\u0000\u0000\u0000\"#\u0005\u000f"+
		"\u0000\u0000#%\u0005\u0003\u0000\u0000$&\u0003\b\u0004\u0000%$\u0001\u0000"+
		"\u0000\u0000%&\u0001\u0000\u0000\u0000&\'\u0001\u0000\u0000\u0000\'(\u0005"+
		"\u0004\u0000\u0000(\u0007\u0001\u0000\u0000\u0000).\u0003\n\u0005\u0000"+
		"*+\u0005\u0005\u0000\u0000+-\u0003\n\u0005\u0000,*\u0001\u0000\u0000\u0000"+
		"-0\u0001\u0000\u0000\u0000.,\u0001\u0000\u0000\u0000./\u0001\u0000\u0000"+
		"\u0000/\t\u0001\u0000\u0000\u00000.\u0001\u0000\u0000\u000012\u0005\u000f"+
		"\u0000\u000024\u0005\u0006\u0000\u000031\u0001\u0000\u0000\u000034\u0001"+
		"\u0000\u0000\u000045\u0001\u0000\u0000\u000056\u0003\f\u0006\u00006\u000b"+
		"\u0001\u0000\u0000\u000078\u0007\u0001\u0000\u00008\r\u0001\u0000\u0000"+
		"\u0000\u0005\u0015\u001f%.3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}