INT_T
IDENT		main
'('
')'
'{'
INT_T
IDENT		a
'['
INT_LIT		3
']'
'['
INT_LIT		3
']'
';'
FOR
'('
INT_T
IDENT		i
VAL_ASSIGN
INT_LIT		0
';'
IDENT		i
LES
INT_LIT		3
';'
IDENT		i
INC_ASSIGN
')'
'{'
FOR
'('
INT_T
IDENT		j
VAL_ASSIGN
INT_LIT		0
';'
IDENT		j
LES
INT_LIT		3
';'
IDENT		j
INC_ASSIGN
')'
'{'
IDENT		a
'['
IDENT		i
']'
'['
IDENT		j
']'
VAL_ASSIGN
'('
IDENT		i
ADD
IDENT		j
ADD
INT_LIT		998244353
')'
MOD
INT_LIT		82781
';'
'}'
'}'
FOR
'('
INT_T
IDENT		i
VAL_ASSIGN
INT_LIT		2
';'
IDENT		i
GEQ
INT_LIT		0
';'
IDENT		i
DEC_ASSIGN
')'
'{'
FOR
'('
INT_T
IDENT		j
VAL_ASSIGN
INT_LIT		0
';'
IDENT		j
LES
INT_LIT		3
';'
IDENT		j
INC_ASSIGN
')'
'{'
COUT
SHL
IDENT		a
'['
IDENT		i
']'
'['
IDENT		j
']'
SHL
IDENT		endl
';'
'}'
'}'
RETURN
INT_LIT		0
';'
'}'
