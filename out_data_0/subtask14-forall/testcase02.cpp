INT_T
IDENT main
'('
STR_T
IDENT argv
'['
']'
')'
'{'
INT_T
IDENT a
'['
INT_LIT 3
']'
'['
INT_LIT 3
']'
';'
FOR
'('
INT_T
IDENT i
VAL_ASSIGN
INT_LIT 0
';'
IDENT i
LES
INT_LIT 3
';'
IDENT i
ADD
ADD
')'
'{'
FOR
'('
INT_T
IDENT j
VAL_ASSIGN
INT_LIT 0
';'
IDENT j
LES
INT_LIT 3
';'
IDENT j
ADD
ADD
')'
'{'
IDENT a
'['
IDENT i
']'
'['
IDENT j
']'
VAL_ASSIGN
'('
IDENT i
ADD
IDENT j
ADD
INT_LIT 998244353
')'
MOD
INT_LIT 82781
';'
'}'
'}'
FOR
'('
INT_T
IDENT i
VAL_ASSIGN
INT_LIT 2
';'
IDENT i
GTR
VAL_ASSIGN
INT_LIT 0
';'
IDENT i
SUB
SUB
')'
'{'
FOR
'('
INT_T
IDENT j
VAL_ASSIGN
INT_LIT 0
';'
IDENT j
LES
INT_LIT 3
';'
IDENT j
ADD
ADD
')'
'{'
COUT
SHL
IDENT a
'['
IDENT i
']'
'['
IDENT j
']'
SHL
IDENT endl
';'
'}'
'}'
RETURN
INT_LIT 0
';'
'}'
