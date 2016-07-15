/*
 * Copyright 2016 Team Shadow
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 	
 * 	    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */ 

/** A Shadow 0.7 grammar for ANTLR v4.
 */
grammar Shadow;

@header {
	package shadow.parse;
}

@lexer::members {    
    public static final int DOCUMENTATION = HIDDEN + 1;
}

options {contextSuperClass=shadow.parse.Context;}

// starting point for parsing a shadow file
compilationUnit
    :   packageDeclaration? importDeclaration* typeDeclaration* EOF
    ;

packageDeclaration
    :   annotation* 'package' qualifiedName ';'
    ;

importDeclaration
    :   'import' 'static'? qualifiedName ('.' '*')? ';'
    ;

typeDeclaration
    :   classOrInterfaceModifier* classDeclaration
    |   classOrInterfaceModifier* enumDeclaration
    |   classOrInterfaceModifier* interfaceDeclaration
    |   classOrInterfaceModifier* annotationTypeDeclaration
    |   ';'
    ;

modifier
    :   classOrInterfaceModifier
    |   (   'native'
        |   'synchronized'
        |   'transient'
        |   'volatile'
        )
    ;

classOrInterfaceModifier
    :   annotation       // class or interface
    |   (   'public'     // class or interface
        |   'protected'  // class or interface
        |   'private'    // class or interface
        |   'static'     // class or interface
        |   'abstract'   // class or interface
        |   'final'      // class only -- does not apply to interfaces
        |   'strictfp'   // class or interface
        )
    ;

variableModifier
    :   'final'
    |   annotation
    ;

classDeclaration
    :   'class' Identifier typeParameters?
        ('extends' type)?
        ('implements' typeList)?
        classBody
    ;

typeParameters
    :   '<' typeParameter (',' typeParameter)* '>'
    ;

typeParameter
    :   Identifier ('extends' typeBound)?
    ;

typeBound
    :   type ('&' type)*
    ;

enumDeclaration
    :   ENUM Identifier ('implements' typeList)?
        '{' enumConstants? ','? enumBodyDeclarations? '}'
    ;

enumConstants
    :   enumConstant (',' enumConstant)*
    ;

enumConstant
    :   annotation* Identifier arguments? classBody?
    ;

enumBodyDeclarations
    :   ';' classBodyDeclaration*
    ;

interfaceDeclaration
    :   'interface' Identifier typeParameters? ('extends' typeList)? interfaceBody
    ;

typeList
    :   type (',' type)*
    ;

classBody
    :   '{' classBodyDeclaration* '}'
    ;

interfaceBody
    :   '{' interfaceBodyDeclaration* '}'
    ;

classBodyDeclaration
    :   ';'
    |   'static'? block
    |   modifier* memberDeclaration
    ;

memberDeclaration
    :   methodDeclaration
    |   genericMethodDeclaration
    |   fieldDeclaration
    |   constructorDeclaration
    |   genericConstructorDeclaration
    |   interfaceDeclaration
    |   annotationTypeDeclaration
    |   classDeclaration
    |   enumDeclaration
    ;

/* We use rule this even for void methods which cannot have [] after parameters.
   This simplifies grammar and we can consider void to be a type, which
   renders the [] matching as a context-sensitive issue or a semantic check
   for invalid return type after parsing.
 */
methodDeclaration
    :   (type|'void') Identifier formalParameters ('[' ']')*
        ('throws' qualifiedNameList)?
        (   methodBody
        |   ';'
        )
    ;

genericMethodDeclaration
    :   typeParameters methodDeclaration
    ;

constructorDeclaration
    :   Identifier formalParameters ('throws' qualifiedNameList)?
        constructorBody
    ;

genericConstructorDeclaration
    :   typeParameters constructorDeclaration
    ;

fieldDeclaration
    :   type variableDeclarators ';'
    ;

interfaceBodyDeclaration
    :   modifier* interfaceMemberDeclaration
    |   ';'
    ;

interfaceMemberDeclaration
    :   constDeclaration
    |   interfaceMethodDeclaration
    |   genericInterfaceMethodDeclaration
    |   interfaceDeclaration
    |   annotationTypeDeclaration
    |   classDeclaration
    |   enumDeclaration
    ;

constDeclaration
    :   type constantDeclarator (',' constantDeclarator)* ';'
    ;

constantDeclarator
    :   Identifier ('[' ']')* '=' variableInitializer
    ;

// see matching of [] comment in methodDeclaratorRest
interfaceMethodDeclaration
    :   (type|'void') Identifier formalParameters ('[' ']')*
        ('throws' qualifiedNameList)?
        ';'
    ;

genericInterfaceMethodDeclaration
    :   typeParameters interfaceMethodDeclaration
    ;

variableDeclarators
    :   variableDeclarator (',' variableDeclarator)*
    ;

variableDeclarator
    :   variableDeclaratorId ('=' variableInitializer)?
    ;

variableDeclaratorId
    :   Identifier ('[' ']')*
    ;

variableInitializer
    :   arrayInitializer
    |   expression
    ;

arrayInitializer
    :   '{' (variableInitializer (',' variableInitializer)* (',')? )? '}'
    ;

enumConstantName
    :   Identifier
    ;

type
    :   classOrInterfaceType ('[' ']')*
    |   primitiveType ('[' ']')*
    ;

classOrInterfaceType
    :   Identifier typeArguments? ('.' Identifier typeArguments? )*
    ;

primitiveType
    :   'boolean'
    |   'char'
    |   'byte'
    |   'short'
    |   'int'
    |   'long'
    |   'float'
    |   'double'
    ;

typeArguments
    :   '<' typeArgument (',' typeArgument)* '>'
    ;

typeArgument
    :   type
    |   '?' (('extends' | 'super') type)?
    ;

qualifiedNameList
    :   qualifiedName (',' qualifiedName)*
    ;

formalParameters
    :   '(' formalParameterList? ')'
    ;

formalParameterList
    :   formalParameter (',' formalParameter)* (',' lastFormalParameter)?
    |   lastFormalParameter
    ;

formalParameter
    :   variableModifier* type variableDeclaratorId
    ;

lastFormalParameter
    :   variableModifier* type '...' variableDeclaratorId
    ;

methodBody
    :   block
    ;

constructorBody
    :   block
    ;

qualifiedName
    :   Identifier ('.' Identifier)*
    ;

literal
    :   IntegerLiteral
    |   FloatingPointLiteral    
    |   StringLiteral
    |   BooleanLiteral
    |   'null'
    ;

// ANNOTATIONS

annotation
    :   '@' annotationName ( '(' ( elementValuePairs | elementValue )? ')' )?
    ;

annotationName : qualifiedName ;

elementValuePairs
    :   elementValuePair (',' elementValuePair)*
    ;

elementValuePair
    :   Identifier '=' elementValue
    ;

elementValue
    :   expression
    |   annotation
    |   elementValueArrayInitializer
    ;

elementValueArrayInitializer
    :   '{' (elementValue (',' elementValue)*)? (',')? '}'
    ;

annotationTypeDeclaration
    :   '@' 'interface' Identifier annotationTypeBody
    ;

annotationTypeBody
    :   '{' (annotationTypeElementDeclaration)* '}'
    ;

annotationTypeElementDeclaration
    :   modifier* annotationTypeElementRest
    |   ';' // this is not allowed by the grammar, but apparently allowed by the actual compiler
    ;

annotationTypeElementRest
    :   type annotationMethodOrConstantRest ';'
    |   classDeclaration ';'?
    |   interfaceDeclaration ';'?
    |   enumDeclaration ';'?
    |   annotationTypeDeclaration ';'?
    ;

annotationMethodOrConstantRest
    :   annotationMethodRest
    |   annotationConstantRest
    ;

annotationMethodRest
    :   Identifier '(' ')' defaultValue?
    ;

annotationConstantRest
    :   variableDeclarators
    ;

defaultValue
    :   'default' elementValue
    ;

// STATEMENTS / BLOCKS

block
    :   '{' blockStatement* '}'
    ;

blockStatement
    :   localVariableDeclarationStatement
    |   statement
    |   typeDeclaration
    ;

localVariableDeclarationStatement
    :    localVariableDeclaration ';'
    ;

localVariableDeclaration
    :   variableModifier* type variableDeclarators
    ;

statement
    :   block
    |   ASSERT expression (':' expression)? ';'
    |   'if' parExpression statement ('else' statement)?
    |   'for' '(' forControl ')' statement
    |   'while' parExpression statement
    |   'do' statement 'while' parExpression ';'
    |   'try' block (catchClause+ finallyBlock? | finallyBlock)
    |   'try' resourceSpecification block catchClause* finallyBlock?
    |   'switch' parExpression '{' switchBlockStatementGroup* switchLabel* '}'
    |   'synchronized' parExpression block
    |   'return' expression? ';'
    |   'throw' expression ';'
    |   'break' Identifier? ';'
    |   'continue' Identifier? ';'
    |   ';'
    |   statementExpression ';'
    |   Identifier ':' statement
    ;

catchClause
    :   'catch' '(' variableModifier* catchType Identifier ')' block
    ;

catchType
    :   qualifiedName ('|' qualifiedName)*
    ;

finallyBlock
    :   'finally' block
    ;

resourceSpecification
    :   '(' resources ';'? ')'
    ;

resources
    :   resource (';' resource)*
    ;

resource
    :   variableModifier* classOrInterfaceType variableDeclaratorId '=' expression
    ;

/** Matches cases then statements, both of which are mandatory.
 *  To handle empty cases at the end, we add switchLabel* to statement.
 */
switchBlockStatementGroup
    :   switchLabel+ blockStatement+
    ;

switchLabel
    :   'case' constantExpression ':'
    |   'case' enumConstantName ':'
    |   'default' ':'
    ;

forControl
    :   enhancedForControl
    |   forInit? ';' expression? ';' forUpdate?
    ;

forInit
    :   localVariableDeclaration
    |   expressionList
    ;

enhancedForControl
    :   variableModifier* type variableDeclaratorId ':' expression
    ;

forUpdate
    :   expressionList
    ;

// EXPRESSIONS

parExpression
    :   '(' expression ')'
    ;

expressionList
    :   expression (',' expression)*
    ;

statementExpression
    :   expression
    ;

constantExpression
    :   expression
    ;

expression
    :   primary
    |   expression '.' Identifier
    |   expression '.' 'this'
    |   expression '.' 'new' nonWildcardTypeArguments? innerCreator
    |   expression '.' 'super' superSuffix
    |   expression '.' explicitGenericInvocation
    |   expression '[' expression ']'
    |   expression '(' expressionList? ')'
    |   'new' creator
    |   '(' type ')' expression
    |   expression ('++' | '--')
    |   ('+'|'-'|'++'|'--') expression
    |   ('~'|'!') expression
    |   expression ('*'|'/'|'%') expression
    |   expression ('+'|'-') expression
    |   expression ('<' '<' | '>' '>' '>' | '>' '>') expression
    |   expression ('<=' | '>=' | '>' | '<') expression
    |   expression 'instanceof' type
    |   expression ('==' | '!=') expression
    |   expression '&' expression
    |   expression '^' expression
    |   expression '|' expression
    |   expression '&&' expression
    |   expression '||' expression
    |   expression '?' expression ':' expression
    |   <assoc=right> expression
        (   '='
        |   '+='
        |   '-='
        |   '*='
        |   '/='
        |   '&='
        |   '|='
        |   '^='
        |   '>>='
        |   '>>>='
        |   '<<='
        |   '%='
        )
        expression
    ;

primary
    :   '(' expression ')'
    |   'this'
    |   'super'
    |   literal
    |   Identifier
    |   type '.' 'class'
    |   'void' '.' 'class'
    |   nonWildcardTypeArguments (explicitGenericInvocationSuffix | 'this' arguments)
    ;

creator
    :   nonWildcardTypeArguments createdName classCreatorRest
    |   createdName (arrayCreatorRest | classCreatorRest)
    ;

createdName
    :   Identifier typeArgumentsOrDiamond? ('.' Identifier typeArgumentsOrDiamond?)*
    |   primitiveType
    ;

innerCreator
    :   Identifier nonWildcardTypeArgumentsOrDiamond? classCreatorRest
    ;

arrayCreatorRest
    :   '['
        (   ']' ('[' ']')* arrayInitializer
        |   expression ']' ('[' expression ']')* ('[' ']')*
        )
    ;

classCreatorRest
    :   arguments classBody?
    ;

explicitGenericInvocation
    :   nonWildcardTypeArguments explicitGenericInvocationSuffix
    ;

nonWildcardTypeArguments
    :   '<' typeList '>'
    ;

typeArgumentsOrDiamond
    :   '<' '>'
    |   typeArguments
    ;

nonWildcardTypeArgumentsOrDiamond
    :   '<' '>'
    |   nonWildcardTypeArguments
    ;

superSuffix
    :   arguments
    |   '.' Identifier arguments?
    ;

explicitGenericInvocationSuffix
    :   'super' superSuffix
    |   Identifier arguments
    ;

arguments
    :   '(' expressionList? ')'
    ;

// LEXER

// Keywords
ABSTRACT	: 'abstract';
AND			: 'and';
ASSERT		: 'assert';
BOOLEAN		: 'boolean';
BREAK		: 'break';
BYTE		: 'byte';
CASE		: 'case';
CAST		: 'cast';
CATCH		: 'catch';
CHECK		: 'check';
CLASS		: 'class';
CODE		: 'code';
COPY		: 'copy';
CREATE		: 'create';
CONSTANT	: 'constant';
CONTINUE	: 'continue';
DEFAULT		: 'default';
DESTROY		: 'destroy';
DO			: 'do';
DOUBLE		: 'double';
ELSE		: 'else';
ENUM		: 'enum';
EXCEPTION	: 'exception';
FALSE		: 'false';
FINALLY		: 'finally';
FLOAT		: 'float';
FOR			: 'for';
FOREACH		: 'foreach';
FREEZE		: 'freeze';
GET			: 'get';
IF			: 'if';
IMMUTABLE	: 'immutable';
IMPORT		: 'import';
IN			: 'in';
IS			: 'is';
INT			: 'int';
INTERFACE	: 'interface';
LOCKED		: 'locked';
LONG		: 'long';
NATIVE		: 'native';
NULL		: 'null';
NULLABLE	: 'nullable';
OR			: 'or';
PRIVATE		: 'private';
PROTECTED	: 'protected';
PUBLIC		: 'public';
READONLY	: 'readonly';
RECOVER		: 'recover';
RETURN		: 'return';
SET			: 'set';
SHORT		: 'short';
SINGLETON	: 'singleton';
SKIP_		: 'skip';
SUPER		: 'super';
SWITCH		: 'switch';
THIS		: 'this';
THROW		: 'throw';
TRUE		: 'true';
TRY			: 'try';
UBYTE		: 'ubyte';
UINT		: 'uint';
ULONG		: 'ulong';
USHORT		: 'ushort';
VAR			: 'var';
WEAK		: 'weak';
WHILE		: 'while';
XOR			: 'xor';

// Literals

ByteLiteral // Can't end with b or B because of hex digits.
	  : DecimalLiteral ('y'|'Y')
      | HexLiteral ('y'|'Y')
      | BinaryLiteral ('y'|'Y')
      | OctalLiteral ('y'|'Y')    
	  ;
UByteLiteral // Can't end with b or B because of hex digits.
	  : DecimalLiteral ('u'|'U')('y'|'Y')
      | HexLiteral ('u'|'U')('y'|'Y')
      | BinaryLiteral ('u'|'U')('y'|'Y')
      | OctalLiteral ('u'|'U')('y'|'Y')
	  ;

ShortLiteral
	  : DecimalLiteral ('s'|'S')
      | HexLiteral ('s'|'S')
      | BinaryLiteral ('s'|'S')
      | OctalLiteral ('s'|'S')    
	  ;
	  
UShortLiteral
	  : DecimalLiteral ('u'|'U')('s'|'S')
      | HexLiteral ('u'|'U')('s'|'S')
      | BinaryLiteral ('u'|'U')('s'|'S')
      | OctalLiteral ('u'|'U')('s'|'S')
      ;
	  
IntLiteral
	  : DecimalLiteral ('i'|'I')?
      | HexLiteral ('i'|'I')?
      | BinaryLiteral ('i'|'I')?
      | OctalLiteral ('i'|'I')?
      ;
	  	  
UIntLiteral
	  : DecimalLiteral ('u'|'U')('i'|'I')?
      | HexLiteral ('u'|'U')('i'|'I')?
      | BinaryLiteral ('u'|'U')('i'|'I')?
      | OctalLiteral ('u'|'U')('i'|'I')?
	  ;
	  
LongLiteral
	  : DecimalLiteral ('l'|'L')
      | HexLiteral ('l'|'L')
      | BinaryLiteral ('l'|'L')
      | OctalLiteral ('l'|'L')      
	  ;
	  
ULongLiteral
	  : DecimalLiteral ('u'|'U')('l'|'L')
      | HexLiteral ('u'|'U')('l'|'L')
      | BinaryLiteral ('u'|'U')('l'|'L')
      | OctalLiteral ('u'|'U')('l'|'L')	
	  ;

fragment
BinaryLiteral: '0'[bB][0-1]+;

fragment
DecimalLiteral: [0-9]+;

fragment
HexLiteral: '0'[xX][0-9a-fA-F]+;

fragment
OctalLiteral: '0'[cC][0-7]+; //doesn't use 0o or 0O because it's hard to read

// Floating-Point Literals

FloatLiteral:
       [0-9]+ '.' [0-9]+ Exponent? [fF]
      | '.' ([0-9])+ Exponent? [fF]
      | ([0-9])+ Exponent [fF]
      | ([0-9])+ Exponent? [fF];
	  
DoubleLiteral:
       [0-9]+ '.' [0-9]+ Exponent? [dD]?
      | '.' [0-9]+ Exponent? [dD]?
      | [0-9]+ Exponent [dD]?
      | [0-9]+ Exponent? [dD];

fragment
  Exponent: [eE] [+-]? [0-9]+;

// Code Literals

CodeLiteral
    :   '\'' SingleCharacter '\''
    |   '\'' EscapeSequence '\''
    ;

fragment
SingleCharacter
    :   ~['\\]
    ;

// String Literals

StringLiteral
    :   '"' StringCharacter* '"'
    ;

fragment
StringCharacter
    :   ~["\\]
    |   EscapeSequence
    ;

// Escape Sequences for Code and String Literals

fragment
EscapeSequence
    :   '\\' [btnfr"'\\]
    |   '\\' [0-7] [0-7]?
    |   '\\' [0-3] [0-7] [0-7]
    ;

// Separators

LPAREN			: '(';
RPAREN			: ')';
LBRACE			: '{';
RBRACE			: '}';
LBRACKET		: '[';
RBRACKET		: ']';
SEMICOLON		: ';';
COMMA			: ',';
DOT				: '.';
COLON			: ':';
AT				: '@';
ARROW			: '->';
YIELDS			: '=>';

// Operators

ASSIGN			: '=';
LT				: '<';
BANG			: '!';
TILDE			: '~';
HOOK			: '?';
COALESCE		: '??';
EQ				: '==';
REF_EQ			: '===';
LE				: '<=';
GE				: '>=';
NE				: '!=';
REF_NE			: '!==';
PLUS			: '+';
MINUS			: '-';
STAR			: '*';
SLASH			: '/';
BIT_AND			: '&';
BIT_OR			: '|';
BIT_XOR			: '^';
REM				: '%';
LEFTROTATE		: '<<<';
LEFTSHIFT		: '<<';
RIGHTROTATE		: '>>>';
RIGHTSHIFT		: '>>';
CAT				: '#';
VERSION			: '$';

PLUSASSIGN		: '+=';
MINUSASSIGN		: '-=';
STARASSIGN		: '*=';
SLASHASSIGN		: '/=';
ANDASSIGN		: '&=';
ORASSIGN		: '|=';
XORASSIGN		: '^=';
REMASSIGN		: '%=';
CATASSIGN		: '#=';
LEFTSHIFTASSIGN	: '<<=';
RIGHTSHIFTASSIGN	: '>>=';
RIGHTROTATEASSIGN	: '>>>=';
LEFTROTATEASSIGN	: '<<<=';

// Identifiers (must appear after all keywords in the grammar)

Identifier
    :   Letter (Letter|Nonletter)*
    ;

fragment
Letter
    :   [\u0041-\u005a\u0061-\u007a\u00c0-\u00d6\u00d8-\u00f6\u00f8-\u00ff\u0100-\u1fff\u3040-\u318f\u3300-\u337f\u3400-\u3d2d\u4e00-\u9fff\uf900-\ufaff]
    ;

fragment
Nonletter
    :   [\u0030-\u0039\u005f\u0660-\u0669\u06f0-\u06f9\u0966-\u096f\u09e6-\u09ef\u0a66-\u0a6f\u0ae6-\u0aef\u0b66-\u0b6f\u0be7-\u0bef\u0c66-\u0c6f\u0ce6-\u0cef\u0d66-\u0d6f\u0e50-\u0e59\u0ed0-\u0ed9\u1040-\u1049]
    ;

//
// Whitespace and comments
//

WS  :  [ \t\r\n\u000C]+ -> skip
    ;
    
DOCUMENTATION_COMMENT
	:  '/**' .*? '*/' -> channel(DOCUMENTATION)
	;
	
LINE_DOCUMENTATION_COMMENT
	:  '///' ~[\r\n]* -> channel(DOCUMENTATION)
	;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;
