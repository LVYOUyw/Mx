grammar Mx;

program
        : (functiondec | classdec | variabledec)*
        ;

functiondec
        : type Identifier? '(' parameterlist? ')' block
        ;

type
        : Int                                                  #inttype
        | String                                               #stringtype
        | Bool                                                 #booltype
        | Void                                                 #voidtype
        | Identifier                                           #classtype
        | type '[' ']'                                         #arraytype
        ;

parameterlist
        : type Identifier (',' parameterlist)*;

statement
        : block                                                                     #blockstmt
        | variabledec                                                               #varstmt
        | If '(' expression ')' statement (Else statement)?                         #ifstmt
        | While '(' expression ')' statement                                        #whilestmt
        | For '(' expression? ';' expression? ';' expression? ')' statement         #forstmt
        | Return expression? ';'                                                    #retstmt
        | Break ';'                                                                 #breakstmt
        | Continue ';'                                                              #continuestmt
        | expression ';'                                                            #exprstmt
        | ';'                                                                       #nullstmt
        ;

block
        : '{' statement* '}';

variabledec
        : type Identifier (',' Identifier)* ';'                #vardec1
        | type Identifier '=' expression ';'                   #vardec2
        ;

expression
        :
        '(' expression ')'                                                              #subexpr
        | This                                                                          #thisexpr
        | Numconst                                                                      #intexpr
        | Stringconst                                                                   #stringexpr
        | Logicconst                                                                    #boolexpr
        | Nullconst                                                                     #nullexpr
        | Identifier                                                                    #idenexpr
        | expression '.' Identifier                                                     #dotexpr
        | expression '[' expression ']'                                                 #indexexpr
        | New Identifier ('[' expression? ']')+                                         #newexpr
        | New Identifier ('(' ')')?                                                     #constructexpr
        | New type ('[' expression? ']')*                                               #newexpr
        | expression '(' (expression (',' expression)*)? ')'                            #functionexpr
        | expression op=('++' | '--')                                                   #postfixexpr
        | op=('+' | '-' | '++' | '--' | '~' | '!') expression                           #prefixexpr
        | expression op=('*' | '/' | '%') expression                                    #mulexpr
        | expression op=('+' | '-') expression                                          #addexpr
        | expression op=('<<' | '>>') expression                                        #shiftexpr
        | expression op=('<=' | '>=' | '>' | '<') expression                            #compexpr
        | expression op=('==' | '!=') expression                                        #equalexpr
        | expression op=('&' | '^' | '|') expression                                    #bitexpr
        | expression op=('&&' | '||') expression                                        #logicexpr
        | <assoc=right> expression '=' expression                                       #assignexpr
        ;

classdec
        : Class Identifier '{' (variabledec | functiondec)* '}' ';'
        ;


// terminal

//key words
Int : 'int';
Bool : 'bool';
String : 'string';
Void : 'void';
If : 'if';
Else : 'else';
For : 'for';
While : 'while';
Break : 'break';
Continue : 'continue';
Return : 'return';
New : 'new';
Class : 'class';
This : 'this';

Numconst : [0-9]+;
Logicconst : 'false' | 'true';
Stringconst: '"' ('\\"' | '\\n' | '\\\\' | .)*? '"';
Nullconst : 'null';
Identifier : [a-zA-Z]+[a-zA-Z_0-9]*;
WS : [ \t\n\r] -> skip;
Linecomment : '//' ~[\r\n]* -> skip;
Blockcomment : '/*' .*? '*/' -> skip;







