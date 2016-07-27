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

import shadow.interpreter.ShadowValue;
import shadow.tac.nodes.TACOperand;
import java.util.List;
import java.util.ArrayList;
import shadow.doctool.DocumentationBuilder;
import shadow.doctool.Documentation;
}

//@lexer::members{ public static DocumentationBuilder docBuilder = null; }

options {contextSuperClass=shadow.parse.Context;}

compilationUnit	
    :   importDeclaration* modifiers (classOrInterfaceDeclaration | enumDeclaration) EOF
    ;
    
importDeclaration
    :   'import' name versionRange? ';'
    ;
    
versionRange
	: VersionLiteral
	| '[' VersionLiteral ',' ']'
	| '[' ',' VersionLiteral ']'
	| '[' VersionLiteral ',' VersionLiteral ']'
	;
	
modifiers
	: modifier*
	;

modifier throws ParseException
	: 'public'
	| 'protected' 
	| 'private' 
	| 'abstract' 
	| 'readonly' 
	| 'native' 
	| 'weak' 
	| 'immutable'
	| 'nullable' 
	| 'get' 
	| 'set' 
	| 'constant' 
	| 'locked' 
	;

classOrInterfaceDeclaration
	:
	('class' | 'singleton' | 'exception'| 'interface' )
	( unqualifiedName '@' )? Identifier typeParameters? VersionLiteral? isList?
	classOrInterfaceBody
	;
	
enumDeclaration
	:
	'enum' ( unqualifiedName '@' )? Identifier	isList?
	enumBody
	;
	
enumBody
	:
   '{'
		enumConstant (',' enumConstant)*
   		( ';' classOrInterfaceBodyDeclaration* )?
   '}'
	;
	
enumConstant
	: Identifier  arguments? classOrInterfaceBody?
	;
	
typeParameters
	: '<' typeParameter( ',' typeParameter )* '>'
	;

typeParameter
	: Identifier isList?
	;

isList
	: 'is' classOrInterfaceType( 'and' classOrInterfaceType )*
	;

classOrInterfaceBody
	:
  	'{'
  		classOrInterfaceBodyDeclaration*
  	'}'
	;	

classOrInterfaceBodyDeclaration
//locals [ Documentation documentation = ShadowLexer.docBuilder.process()   ]
	: modifiers 
	  	( classOrInterfaceDeclaration /*{$ctx.classOrInterfaceDeclaration().setDocumentation($documentation);}*/
	    | enumDeclaration 
	    | createDeclaration 
	    | destroyDeclaration 
	    | fieldDeclaration 
	    | methodDeclaration 
	  	)
	;
	
fieldDeclaration
	: type variableDeclarator (',' variableDeclarator )* ';'
	;
	
variableDeclarator
	: Identifier ( '=' conditionalExpression )?
	;
	
arrayInitializer
	: '{' arrayInitializer ( ',' arrayInitializer )*  (',')?  '}'
  	| '{' conditionalExpression ( ',' conditionalExpression )*  (',')?  '}'
	;

methodDeclarator
	: Identifier formalParameters '=>' resultTypes
	;

inlineResults
	: '(' ( conditionalExpression ( ',' conditionalExpression() )* )? ')'
 	;
 	
formalParameters
	: '(' ( formalParameter ( ',' formalParameter )* )? ')'
	;
	
formalParameter
	: modifiers type Identifier
	;

methodDeclaration
	: methodDeclarator (block | ';' )
	;
	
createDeclaration
	: createDeclarator (createBlock | ';' )
	;

createDeclarator
	: 'create' formalParameters
	;

createBlock
	:
	'{'
		explicitCreateInvocation? 
    	blockStatement*  
    '}'
    ;

destroyDeclaration
	: 'destroy' ( block | ';' )
	;

explicitCreateInvocation
	: ( 'this' | 'super' ) 
	'(' ( conditionalExpression ( ',' conditionalExpression )* )? ')' ';'
	;

type
	: referenceType 
    | primitiveType
    | functionType
	;

referenceType
	: primitiveType ( '[' ( ',' )* ']' )+
  	| classOrInterfaceType ( '[' ( ',' )* ']' )*
	;

functionType
	: resultTypes() '=>' resultTypes
	;
	
classOrInterfaceType
	: (unqualifiedName '@')? classOrInterfaceTypeSuffix ( ':' classOrInterfaceTypeSuffix )*
	;
	
classOrInterfaceTypeSuffix
	:  Identifier typeArguments?
   	;

typeArguments
	: '<' type ( ',' type )* '>'
	;

primitiveType
	:
	( 'boolean'
	| 'byte'
	| 'code'
	| 'double'
	| 'float'
	| 'int'
	| 'long'
	| 'short'
	| 'ubyte'
	| 'uint' 
	| 'ulong' 
	| 'ushort' 
	)
	;

resultType
	: modifiers type Identifier? // Optional identifier for documentation only.
	;

resultTypes
	: '(' ( resultType ( ',' resultType )* )? ')'
	;
	
name
	: (unqualifiedName '@')? Identifier ( ':' Identifier )*
	;

unqualifiedName
	: 'default' 
	| Identifier ( ':' Identifier )*
	;
	
//Expression syntax

assignmentOperator
	: '='    
	| '#='   
	| '*='   
	| '/='   
	| '%='   
	| '+='   
	| '-='   
	| '<<='  
	| rightShiftAssign  
	| rightRotateAssign 
	| '<<<=' 
	| '&='   
	| '^='   
	| '|='
	;

conditionalExpression
	: coalesceExpression ( '?' conditionalExpression ',' conditionalExpression )?
	;

coalesceExpression
	:  conditionalOrExpression  ('??' conditionalOrExpression )*
	;

conditionalOrExpression
  	: conditionalExclusiveOrExpression ( 'or' conditionalExclusiveOrExpression )*
	;

conditionalExclusiveOrExpression
	: conditionalAndExpression ( 'xor' conditionalAndExpression )*
	;

conditionalAndExpression
	: bitwiseOrExpression ( 'and' bitwiseOrExpression )*
	;

bitwiseOrExpression
	: bitwiseExclusiveOrExpression ( '|' bitwiseExclusiveOrExpression )*
	;

bitwiseExclusiveOrExpression
	: bitwiseAndExpression ( '^' bitwiseAndExpression )*
	;

bitwiseAndExpression
	: equalityExpression ( '&' equalityExpression )*
	;

equalityExpression
	: isExpression (( '==' | '===' | '!=' | '!==' ) isExpression )*
	;
	
isExpression
	: relationalExpression ( 'is' type )?
	;

relationalExpression
	: concatenationExpression (( '<' | '>' | '<=' | '>='  ) concatenationExpression )*
	;


concatenationExpression
	: shiftExpression ( '#' shiftExpression )*
	;
	
rightShift
	: first='>' second='>' {$first.index + 1 == $second.index}?
	;
	
rightRotate
	: first='>' second='>' third='>' {$first.index + 1 == $second.index && $second.index + 1 == $third.index}?
	;
	
rightShiftAssign
	: first='>' second='>=' {$first.index + 1 == $second.index}?
	;

rightRotateAssign
	: first='>' second='>' third='>=' {$first.index + 1 == $second.index && $second.index + 1 == $third.index}?
	;


shiftExpression
	: rotateExpression (( '<<' | rightShift ) rotateExpression )*
	;

rotateExpression
	: additiveExpression ( ( '<<<' | rightRotate ) additiveExpression )*
	;

additiveExpression
	: multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )*
	;

multiplicativeExpression
	: unaryExpression (( '*' | '/' | '%' ) unaryExpression )*
	;

//| '++' { throw new ParseException('++ is not a legal Shadow operator', jjtThis);  }
// Strangely, -- is allowed, because it's the negation of a negation.
// Unary + is not in Shadow since it's useless.
unaryExpression
	:
	 '#' unaryExpression
	| '~' unaryExpression
	| '!' unaryExpression
	| '-'unaryExpression
	| inlineMethodDefinition
	| primaryExpression
	;

inlineMethodDefinition
	: formalParameters '=>' inlineResults // Inline method definition
	;
	
castExpression
	: 'cast' '<' type '>' '(' conditionalExpression ')'
 	;

checkExpression
	: 'check' '(' conditionalExpression ')'
 	;

copyExpression
	: ('copy' | 'freeze' ) '(' conditionalExpression ')'
  	;

primaryExpression
locals [boolean action = false]
	: primaryPrefix primarySuffix* 
	;

rightSide
	: sequenceRightSide
 	| conditionalExpression
 	;

sequenceRightSide
	: '(' conditionalExpression (','  conditionalExpression )+ ')'
	;

sequenceLeftSide
	: '(' ( sequenceVariable | primaryExpression )? (',' (sequenceVariable | primaryExpression)? )* ')'
	;
	
sequenceVariable
	: modifiers ( 'var' | type )  Identifier
	;
	
primaryPrefix
	: literal
	| 'this'
	| 'super'
	| checkExpression
	| copyExpression
	| castExpression
	| '(' conditionalExpression ')'
	| primitiveType
	| functionType
	| arrayInitializer
	| (unqualifiedName '@' )? Identifier //catches class types and identifiers
	;

primarySuffix
	: qualifiedKeyword
	| brackets
	| subscript
	| destroy
	| method
	| methodCall
	| property
	| allocation
	| scopeSpecifier
	//| "++" {throw new ParseException("++ is not a legal Shadow operator", jjtThis);  }
	//| "--" {throw new ParseException("-- is not a legal Shadow operator", jjtThis);  }
	;	

allocation
	: arrayCreate
	| create
	;

qualifiedKeyword
	: ':' ( 'this' | 'super')	
	// When you have inner classes, you sometimes need to know Outer:this or Inner:this
	// same for Outer:super or Inner:super
	// Prefix must be a type name that you're currently inside of
	;

brackets
	: ( '[' (',')* ']')+
	;
	
arrayCreate
	: typeArguments? ':' ('create' | 'null' ) arrayDimensions
  	( arrayCreateCall | arrayDefault )?
	;
	
arrayCreateCall
	: ':' '(' ( conditionalExpression ( ',' conditionalExpression )* )? ')'
	;

arrayDefault
	: ':' 'default' '(' conditionalExpression ')'
	;

arrayDimensions
	: ( '[' conditionalExpression (',' conditionalExpression )* ']' )+
	;

subscript
	: '[' conditionalExpression (',' conditionalExpression )* ']'
	;

create
	: typeArguments? ':' 'create' '(' ( conditionalExpression ( ',' conditionalExpression)* )? ')'
	;

scopeSpecifier
	: typeArguments? ':' (Identifier | 'class') //field, constant, or class
	;
	
destroy
	: '.' 'destroy'
	;

method
	: '.' Identifier
	;
	
property
	: '->' Identifier
	;
	
methodCall
	: '(' ( conditionalExpression ( ',' conditionalExpression )* )? ')'
	;

literal
locals [ShadowValue value]
	: ByteLiteral
	| CodeLiteral
	| ShortLiteral
	| IntLiteral
	| LongLiteral
	| FloatLiteral
	| DoubleLiteral
	| StringLiteral
	| UByteLiteral
	| UShortLiteral
	| UIntLiteral
	| ULongLiteral
	| BooleanLiteral
	| NullLiteral
	;
	//| <BAD_STRING_LITERAL > { throw new ParseException("String literals cannot contain newline characters", jjtThis); }

arguments
	: '(' ( conditionalExpression ( ',' conditionalExpression )* )? ')'
  	;
  	
/*
 * Statement syntax follows.
 */

statement
	: assertStatement
	| emptyStatement
	| switchStatement
	| ifStatement
	| whileStatement
	| doStatement
	| forStatement
	| foreachStatement
	| breakOrContinueStatement
	| returnStatement
	| throwStatement
	| finallyStatement // Includes try/catch/recover/finally.
	| statementExpression ';'
	| block
	;
	
assertStatement
	: 'assert' conditionalExpression ( ',' conditionalExpression )? ';'
	;
	
block
	: '{' blockStatement* '}'
	;

blockStatement
	: localDeclaration    
	| statement
	// No local classes in Shadow
	;
	
localDeclaration
	: modifiers ( localMethodDeclaration | localVariableDeclaration ';' )  
	;

localMethodDeclaration
	: methodDeclarator block
	;

localVariableDeclaration
	: ('var' | type) variableDeclarator (',' variableDeclarator )*
	;
	
emptyStatement
	: ';' // actually an error in Shadow, caught in the ParseChecker 
	| 'skip' ';'
	;
	
statementExpression
	: sequenceAssignment 	
	| expression  //method call, assignment, or allocation
	;

sequenceAssignment
	: sequenceLeftSide '=' rightSide
	;

expression
	: primaryExpression ( assignmentOperator conditionalExpression )?
	;

switchStatement
locals [boolean hasDefault = false]
	: 'switch' '(' conditionalExpression ')'
	'{'
    ( switchLabel statement )+
  	'}'
	;

switchLabel
	: 'case' '(' primaryExpression (',' primaryExpression)* ')'
	| 'default'
	;

ifStatement
	: 'if' '(' conditionalExpression ')'
		statement
	( 'else' statement )?
	;

whileStatement
	: 'while' '(' conditionalExpression ')'
		statement
	;

doStatement
	: 'do'
		statement
	'while' '(' conditionalExpression ')' ';'
  	;
  	
foreachStatement
	: 'foreach' '(' foreachInit ')'
		statement
	;
	
foreachInit
	: modifiers ( 'var' | type ) Identifier 'in' conditionalExpression
 	;

forStatement
	: 'for' '(' forInit? ';' conditionalExpression ';' forUpdate? ')'
		statement
	;

forInit
	: modifiers localVariableDeclaration
	| statementExpressionList
	;

statementExpressionList
	: statementExpression ( ',' statementExpression )*
	;

forUpdate
	: statementExpressionList
	;

//no labeled break in Shadow
breakOrContinueStatement
	: ('break' | 'continue') ';'
	;
	
returnStatement
	: 'return' ( ';' | rightSide ';')
	;

throwStatement
	: 'throw' conditionalExpression ';'
	;
	
/* For a simpler back end, a finally statement holds
 * the try, the catches, and the recover (even if no finally is used)
 * Semantic check required here to make sure that at least one
 * finally/recover/catch is present.
 */
finallyStatement
	: recoverStatement
  	('finally' block)?
  	;

recoverStatement
	: catchStatements
  	('recover' block )?
  	;

catchStatements
	: tryStatement
	catchStatement*
	;

catchStatement
	: 'catch' '(' formalParameter ')'
		block
	;

tryStatement
	: 'try' block
	;

// LEXER

//
// Whitespace and comments
//

DOCUMENTATION_COMMENT
	:  '/**' .*? '*/' -> skip //may need to send to another channel
	;
	
	
LINE_DOCUMENTATION_COMMENT
	:  '///' ~[\r\n]* -> skip
	;

WS  :  [ \t\r\n\u000C]+ -> skip
    ;
    

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;


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
	  : DecimalLiteral [yY]
      | HexLiteral [yY]
      | BinaryLiteral [yY]
      | OctalLiteral [yY]    
	  ;
UByteLiteral // Can't end with b or B because of hex digits.
	  : DecimalLiteral [uU][yY]
      | HexLiteral [uU][yY]
      | BinaryLiteral [uU][yY]
      | OctalLiteral [uU][yY]
	  ;

ShortLiteral
	  : DecimalLiteral [sS]
      | HexLiteral [sS]
      | BinaryLiteral [sS]
      | OctalLiteral [sS]    
	  ;
	  
UShortLiteral
	  : DecimalLiteral [uU][sS]
      | HexLiteral [uU][sS]
      | BinaryLiteral [uU][sS]
      | OctalLiteral [uU][sS]
      ;
	  
IntLiteral
	  : DecimalLiteral [iI]?
      | HexLiteral [iI]?
      | BinaryLiteral [iI]?
      | OctalLiteral [iI]?
      ;
	  	  
UIntLiteral
	  : DecimalLiteral [uU][iI]?
      | HexLiteral [uU][iI]?
      | BinaryLiteral [uU][iI]?
      | OctalLiteral [uU][iI]?
	  ;
	  
LongLiteral
	  : DecimalLiteral [lL]
      | HexLiteral [lL]
      | BinaryLiteral [lL]
      | OctalLiteral [lL]      
	  ;
	  
ULongLiteral
	  : DecimalLiteral [uU][lL]
      | HexLiteral [uU][lL]
      | BinaryLiteral [uU][lL]
      | OctalLiteral [uU][lL]	
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

BooleanLiteral
	: 'true'
	| 'false'
	;
	
NullLiteral
	: 'null'
	;
    
VersionLiteral
	:  '$' [0-9]+ '.' [0-9]+ '.' [0-9]+
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
//RIGHTROTATE		: '>>>';
//RIGHTSHIFT		: '>>';
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
//RIGHTSHIFTASSIGN	: '>>=';
//RIGHTROTATEASSIGN	: '>>>=';
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