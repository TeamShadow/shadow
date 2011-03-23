/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include <stdio.h>
#include "E:\Documents\Shadow\src\shadow\TAC\test\Basic.meta"

static 
void __init__R_(struct Basic* this) {
     this->b = 0;                       /* (4:5) */
     this->a = 0;                       /* (3:5) */
}

static 
void thrower__R_(struct Basic* this) {
}

static 
void test__R_(struct Basic* this) {
     label_14: TRY                      /* (10:25) */
     thrower(                           /* (11:13) */
     label_15: CATCH                    /* (13:16) */
     int i;                             /* (13:26) */
     i = 0;                             /* (13:26) */
     i = i + 2;                         /* (14:18) */
}

