BOOL_T
IDENT		check
'('
INT_T
IDENT		a
','
INT_T
IDENT		b
','
STR_T
IDENT		s
','
BOOL_T
IDENT		c
')'
'{'
COUT
SHL
IDENT		s
SHL
IDENT		endl
';'
COUT
SHL
IDENT		a
SHL
STRING_LIT	" "
SHL
IDENT		b
SHL
STRING_LIT	" "
SHL
IDENT		s
SHL
IDENT		endl
';'
RETURN
'('
IDENT		c
EQL
BOOL_LIT	true
')'
';'
'}'
INT_T
IDENT		main
'('
')'
'{'
BOOL_T
IDENT		result
VAL_ASSIGN
IDENT		check
'('
INT_LIT		1
','
INT_LIT		2
','
STRING_LIT	"apple"
','
BOOL_LIT	true
')'
';'
INT_T
IDENT		num
VAL_ASSIGN
IDENT		result
ADD
INT_LIT		7
';'
COUT
SHL
IDENT		num
SHL
IDENT		endl
';'
RETURN
INT_LIT		0
';'
'}'
