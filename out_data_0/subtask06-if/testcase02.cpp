INT_T
IDENT		main
'('
')'
'{'
INT_T
IDENT		a
VAL_ASSIGN
INT_LIT		0
','
IDENT		b
VAL_ASSIGN
INT_LIT		0
','
IDENT		c
VAL_ASSIGN
INT_LIT		10
';'
IF
'('
IDENT		a
EQL
INT_LIT		0
LAN
IDENT		b
EQL
INT_LIT		0
')'
'{'
COUT
SHL
IDENT		a
SHL
IDENT		b
SHL
IDENT		c
SHL
IDENT		endl
';'
'}'
IF
'('
IDENT		c
SUB
IDENT		b
GEQ
INT_LIT		0
')'
'{'
COUT
SHL
STRING_LIT	"OK!"
SHL
IDENT		endl
';'
'}'
IF
'('
'('
IDENT		a
EQL
INT_LIT		0
LAN
IDENT		b
NEQ
INT_LIT		0
')'
LOR
IDENT		c
EQL
INT_LIT		10
')'
'{'
COUT
SHL
STRING_LIT	"test1"
SHL
IDENT		endl
';'
'}'
ELSE
'{'
IF
'('
'('
IDENT		c
EQL
INT_LIT		10
LOR
IDENT		a
NEQ
INT_LIT		0
')'
LAN
'('
'('
IDENT		a
EQL
INT_LIT		0
LAN
IDENT		b
EQL
INT_LIT		0
LAN
IDENT		c
EQL
INT_LIT		0
LAN
'('
IDENT		c
EQL
INT_LIT		10
LOR
'('
IDENT		a
')'
EQL
INT_LIT		0
')'
')'
')'
')'
'{'
COUT
SHL
STRING_LIT	"test2"
SHL
IDENT		endl
';'
'}'
ELSE
'{'
COUT
SHL
STRING_LIT	"test3"
SHL
IDENT		endl
';'
IF
'('
BOOL_LIT	true
')'
'{'
COUT
SHL
STRING_LIT	"test4"
SHL
IDENT		endl
';'
'}'
'}'
'}'
IF
'('
BOOL_LIT	true
')'
'{'
COUT
SHL
STRING_LIT	"test6"
SHL
IDENT		endl
';'
'}'
RETURN
INT_LIT		0
';'
'}'
