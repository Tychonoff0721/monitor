grammar dsl;

// Parser Rules
metricStmt
    : 'METRIC' name=IDENTIFIER
      'ON' targetScope
      'AS' expr ';'?
    ;

targetScope
    : type=('SERVICE' | 'COMPONENT' | 'INSTANCE' | 'CLUSTER') name=STRING_LITERAL
    ;

expr
    : functionCallExpr ( '->' functionCallExpr )*
    ;

functionCallExpr
    : functionName=IDENTIFIER '(' (argumentList)? ')'
    ;

argumentList
    : argument (',' argument)*
    ;

argument
    : (argName=IDENTIFIER '=')? value
    ;

value
    : STRING_LITERAL
    | NUMBER
    | BOOLEAN
    ;

// Lexer Rules
METRIC: 'METRIC';
ON: 'ON';
SERVICE: 'SERVICE';
COMPONENT: 'COMPONENT';
INSTANCE: 'INSTANCE';
CLUSTER: 'CLUSTER';
AS: 'AS';

BOOLEAN: 'true' | 'false';

IDENTIFIER: [a-zA-Z_] [a-zA-Z0-9_]*;
STRING_LITERAL: '"' (~["\r\n] | '\\"')* '"' | '\'' (~['\r\n] | '\\\'')* '\'';
NUMBER: [0-9]+ ('.' [0-9]+)?;

WS: [ \t\r\n]+ -> skip;
