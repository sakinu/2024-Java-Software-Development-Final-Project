INT_T
IDENT		main
'('
')'
'{'
INT_T
IDENT		apple
VAL_ASSIGN
INT_LIT		0
';'
IF
'('
IDENT		apple
EQL
INT_LIT		0
')'
'{'
COUT
SHL
STRING_LIT	"Hello"
SHL
IDENT		endl
';'
'}'
IF
'('
IDENT		apple
NEQ
INT_LIT		0
')'
'{'
COUT
SHL
STRING_LIT	"Hello"
SHL
IDENT		endl
';'
'}'
ELSE
'{'
COUT
SHL
STRING_LIT	"Bye"
SHL
IDENT		endl
';'
'}'
'}'
