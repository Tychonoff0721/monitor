// Generated from dsl.g4 by ANTLR 4.13.1

package com.example.dsl.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class dslLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, METRIC=7, ON=8, SERVICE=9, 
		COMPONENT=10, INSTANCE=11, CLUSTER=12, AS=13, BOOLEAN=14, IDENTIFIER=15, 
		STRING_LITERAL=16, NUMBER=17, WS=18;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "METRIC", "ON", "SERVICE", 
			"COMPONENT", "INSTANCE", "CLUSTER", "AS", "BOOLEAN", "IDENTIFIER", "STRING_LITERAL", 
			"NUMBER", "WS"
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


	public dslLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "dsl.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0012\u009e\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\rl\b\r\u0001\u000e\u0001\u000e"+
		"\u0005\u000ep\b\u000e\n\u000e\f\u000es\t\u000e\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0005\u000fy\b\u000f\n\u000f\f\u000f|\t\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f"+
		"\u0083\b\u000f\n\u000f\f\u000f\u0086\t\u000f\u0001\u000f\u0003\u000f\u0089"+
		"\b\u000f\u0001\u0010\u0004\u0010\u008c\b\u0010\u000b\u0010\f\u0010\u008d"+
		"\u0001\u0010\u0001\u0010\u0004\u0010\u0092\b\u0010\u000b\u0010\f\u0010"+
		"\u0093\u0003\u0010\u0096\b\u0010\u0001\u0011\u0004\u0011\u0099\b\u0011"+
		"\u000b\u0011\f\u0011\u009a\u0001\u0011\u0001\u0011\u0000\u0000\u0012\u0001"+
		"\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007"+
		"\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d"+
		"\u000f\u001f\u0010!\u0011#\u0012\u0001\u0000\u0006\u0003\u0000AZ__az\u0004"+
		"\u000009AZ__az\u0003\u0000\n\n\r\r\"\"\u0003\u0000\n\n\r\r\'\'\u0001\u0000"+
		"09\u0003\u0000\t\n\r\r  \u00a8\u0000\u0001\u0001\u0000\u0000\u0000\u0000"+
		"\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000"+
		"\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b"+
		"\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001"+
		"\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001"+
		"\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001"+
		"\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001"+
		"\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001"+
		"\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000"+
		"\u0000\u0001%\u0001\u0000\u0000\u0000\u0003\'\u0001\u0000\u0000\u0000"+
		"\u0005*\u0001\u0000\u0000\u0000\u0007,\u0001\u0000\u0000\u0000\t.\u0001"+
		"\u0000\u0000\u0000\u000b0\u0001\u0000\u0000\u0000\r2\u0001\u0000\u0000"+
		"\u0000\u000f9\u0001\u0000\u0000\u0000\u0011<\u0001\u0000\u0000\u0000\u0013"+
		"D\u0001\u0000\u0000\u0000\u0015N\u0001\u0000\u0000\u0000\u0017W\u0001"+
		"\u0000\u0000\u0000\u0019_\u0001\u0000\u0000\u0000\u001bk\u0001\u0000\u0000"+
		"\u0000\u001dm\u0001\u0000\u0000\u0000\u001f\u0088\u0001\u0000\u0000\u0000"+
		"!\u008b\u0001\u0000\u0000\u0000#\u0098\u0001\u0000\u0000\u0000%&\u0005"+
		";\u0000\u0000&\u0002\u0001\u0000\u0000\u0000\'(\u0005-\u0000\u0000()\u0005"+
		">\u0000\u0000)\u0004\u0001\u0000\u0000\u0000*+\u0005(\u0000\u0000+\u0006"+
		"\u0001\u0000\u0000\u0000,-\u0005)\u0000\u0000-\b\u0001\u0000\u0000\u0000"+
		"./\u0005,\u0000\u0000/\n\u0001\u0000\u0000\u000001\u0005=\u0000\u0000"+
		"1\f\u0001\u0000\u0000\u000023\u0005M\u0000\u000034\u0005E\u0000\u0000"+
		"45\u0005T\u0000\u000056\u0005R\u0000\u000067\u0005I\u0000\u000078\u0005"+
		"C\u0000\u00008\u000e\u0001\u0000\u0000\u00009:\u0005O\u0000\u0000:;\u0005"+
		"N\u0000\u0000;\u0010\u0001\u0000\u0000\u0000<=\u0005S\u0000\u0000=>\u0005"+
		"E\u0000\u0000>?\u0005R\u0000\u0000?@\u0005V\u0000\u0000@A\u0005I\u0000"+
		"\u0000AB\u0005C\u0000\u0000BC\u0005E\u0000\u0000C\u0012\u0001\u0000\u0000"+
		"\u0000DE\u0005C\u0000\u0000EF\u0005O\u0000\u0000FG\u0005M\u0000\u0000"+
		"GH\u0005P\u0000\u0000HI\u0005O\u0000\u0000IJ\u0005N\u0000\u0000JK\u0005"+
		"E\u0000\u0000KL\u0005N\u0000\u0000LM\u0005T\u0000\u0000M\u0014\u0001\u0000"+
		"\u0000\u0000NO\u0005I\u0000\u0000OP\u0005N\u0000\u0000PQ\u0005S\u0000"+
		"\u0000QR\u0005T\u0000\u0000RS\u0005A\u0000\u0000ST\u0005N\u0000\u0000"+
		"TU\u0005C\u0000\u0000UV\u0005E\u0000\u0000V\u0016\u0001\u0000\u0000\u0000"+
		"WX\u0005C\u0000\u0000XY\u0005L\u0000\u0000YZ\u0005U\u0000\u0000Z[\u0005"+
		"S\u0000\u0000[\\\u0005T\u0000\u0000\\]\u0005E\u0000\u0000]^\u0005R\u0000"+
		"\u0000^\u0018\u0001\u0000\u0000\u0000_`\u0005A\u0000\u0000`a\u0005S\u0000"+
		"\u0000a\u001a\u0001\u0000\u0000\u0000bc\u0005t\u0000\u0000cd\u0005r\u0000"+
		"\u0000de\u0005u\u0000\u0000el\u0005e\u0000\u0000fg\u0005f\u0000\u0000"+
		"gh\u0005a\u0000\u0000hi\u0005l\u0000\u0000ij\u0005s\u0000\u0000jl\u0005"+
		"e\u0000\u0000kb\u0001\u0000\u0000\u0000kf\u0001\u0000\u0000\u0000l\u001c"+
		"\u0001\u0000\u0000\u0000mq\u0007\u0000\u0000\u0000np\u0007\u0001\u0000"+
		"\u0000on\u0001\u0000\u0000\u0000ps\u0001\u0000\u0000\u0000qo\u0001\u0000"+
		"\u0000\u0000qr\u0001\u0000\u0000\u0000r\u001e\u0001\u0000\u0000\u0000"+
		"sq\u0001\u0000\u0000\u0000tz\u0005\"\u0000\u0000uy\b\u0002\u0000\u0000"+
		"vw\u0005\\\u0000\u0000wy\u0005\"\u0000\u0000xu\u0001\u0000\u0000\u0000"+
		"xv\u0001\u0000\u0000\u0000y|\u0001\u0000\u0000\u0000zx\u0001\u0000\u0000"+
		"\u0000z{\u0001\u0000\u0000\u0000{}\u0001\u0000\u0000\u0000|z\u0001\u0000"+
		"\u0000\u0000}\u0089\u0005\"\u0000\u0000~\u0084\u0005\'\u0000\u0000\u007f"+
		"\u0083\b\u0003\u0000\u0000\u0080\u0081\u0005\\\u0000\u0000\u0081\u0083"+
		"\u0005\'\u0000\u0000\u0082\u007f\u0001\u0000\u0000\u0000\u0082\u0080\u0001"+
		"\u0000\u0000\u0000\u0083\u0086\u0001\u0000\u0000\u0000\u0084\u0082\u0001"+
		"\u0000\u0000\u0000\u0084\u0085\u0001\u0000\u0000\u0000\u0085\u0087\u0001"+
		"\u0000\u0000\u0000\u0086\u0084\u0001\u0000\u0000\u0000\u0087\u0089\u0005"+
		"\'\u0000\u0000\u0088t\u0001\u0000\u0000\u0000\u0088~\u0001\u0000\u0000"+
		"\u0000\u0089 \u0001\u0000\u0000\u0000\u008a\u008c\u0007\u0004\u0000\u0000"+
		"\u008b\u008a\u0001\u0000\u0000\u0000\u008c\u008d\u0001\u0000\u0000\u0000"+
		"\u008d\u008b\u0001\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000\u0000"+
		"\u008e\u0095\u0001\u0000\u0000\u0000\u008f\u0091\u0005.\u0000\u0000\u0090"+
		"\u0092\u0007\u0004\u0000\u0000\u0091\u0090\u0001\u0000\u0000\u0000\u0092"+
		"\u0093\u0001\u0000\u0000\u0000\u0093\u0091\u0001\u0000\u0000\u0000\u0093"+
		"\u0094\u0001\u0000\u0000\u0000\u0094\u0096\u0001\u0000\u0000\u0000\u0095"+
		"\u008f\u0001\u0000\u0000\u0000\u0095\u0096\u0001\u0000\u0000\u0000\u0096"+
		"\"\u0001\u0000\u0000\u0000\u0097\u0099\u0007\u0005\u0000\u0000\u0098\u0097"+
		"\u0001\u0000\u0000\u0000\u0099\u009a\u0001\u0000\u0000\u0000\u009a\u0098"+
		"\u0001\u0000\u0000\u0000\u009a\u009b\u0001\u0000\u0000\u0000\u009b\u009c"+
		"\u0001\u0000\u0000\u0000\u009c\u009d\u0006\u0011\u0000\u0000\u009d$\u0001"+
		"\u0000\u0000\u0000\f\u0000kqxz\u0082\u0084\u0088\u008d\u0093\u0095\u009a"+
		"\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}