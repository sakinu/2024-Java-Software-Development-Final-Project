INT_T
IDENT		main
'('
')'
'{'
INT_T
IDENT		counter
VAL_ASSIGN
INT_LIT		0
';'
WHILE
'('
IDENT		counter
LES
INT_LIT		100000
')'
'{'
IDENT		counter
ADD_ASSIGN
INT_LIT		1
';'
IF
'('
IDENT		counter
EQL
INT_LIT		99999
')'
'{'
BREAK
';'
'}'
IF
'('
IDENT		counter
MOD
INT_LIT		91
EQL
INT_LIT		0
')'
'{'
COUT
SHL
STRING_LIT	"Hello World "
SHL
IDENT		counter
MOD
INT_LIT		91
SHL
IDENT		endl
';'
'}'
COUT
SHL
INT_LIT		998244353
MOD
IDENT		counter
SHL
IDENT		endl
';'
'}'
COUT
SHL
IDENT		counter
SHL
IDENT		endl
';'
'}'
