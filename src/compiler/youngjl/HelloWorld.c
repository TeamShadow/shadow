/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "/home/jacob/Programming/build/shadow/compiler/youngjl/HelloWorld.meta"

int test_Puint_Puint_Rint(unsigned int start, unsigned int end) {
     int temp_0;                        /* (45:36) */
                                        /* (45:36) */
     unsigned int i;                    /* (45:21) */
                                        /* (45:21) */
     i = start;                         /* (45:21) */
     
     if ( i <= end ) {                  /* (45:36) */
          temp_0 = 1;                   /* (45:36) */
     }                                  /* (45:36) */
     else {
          temp_0 = 0;                   /* (45:36) */
     }                                  /* (45:36) */
     while ( temp_0 == 1 ) {            /* (44:9) */
          print_Puint(i);               /* (46:32) */
          print_PString(": ");          /* (47:32) */
          unsigned int temp_1;          /* (48:42) */
                                        /* (48:42) */
          temp_1 = factorial_Puint_Ruint(i);/* (48:42) */
          printLine_Puint(temp_1);      /* (48:32) */
          i = i + 1u;                   /* (45:46) */
          
          if ( i <= end ) {             /* (45:36) */
               temp_0 = 1;              /* (45:36) */
          }                             /* (45:36) */
          else {
               temp_0 = 0;              /* (45:36) */
          }                             /* (45:36) */
     } 
}

unsigned int factorial_Puint_Ruint(unsigned int a) {
     int temp_2;                        /* (53:20) */
                                        /* (53:20) */
     int temp_3;                        /* (53:29) */
                                        /* (53:29) */
     int temp_4;                        /* (53:20) */
                                        /* (53:20) */
     unsigned int temp_5;               /* (54:33) */
                                        /* (54:33) */
     unsigned int temp_7;               /* (54:17) */
                                        /* (54:17) */
     temp_4 = 1;                        /* (53:20) */
     
     if ( a == 1 ) {                    /* (53:20) */
          temp_2 = 1;                   /* (53:20) */
     }                                  /* (53:20) */
     else {
          temp_2 = 0;                   /* (53:20) */
     }                                  /* (53:20) */
     
     if ( temp_2 == 1 ) {               /* (53:20) */
     }                                  /* (53:20) */
     else {
          
          if ( a == 0 ) {               /* (53:29) */
               temp_3 = 1;              /* (53:29) */
          }                             /* (53:29) */
          else {
               temp_3 = 0;              /* (53:29) */
          }                             /* (53:29) */
          
          if ( temp_3 == 1 ) {          /* (53:20) */
          }                             /* (53:20) */
          else {
               temp_4 = 0;              /* (53:20) */
          }                             /* (53:20) */
     }                                  /* (53:20) */
     
     if ( temp_4 == 1 ) {               /* (52:51) */
          return 1u;                    /* (53:39) */
     }                                  /* (52:51) */
     unsigned int temp_6;               /* (54:17) */
                                        /* (54:17) */
     temp_5 = a - 1u;                   /* (54:33) */
     temp_6 = factorial_Puint_Ruint(temp_5);/* (54:17) */
     temp_7 = temp_6 * a;               /* (54:17) */
     return temp_7;                     /* (53:50) */
}

void main_PString_A1(char ** args) {
     printLine_PString("Hello World!"); /* (29:24) */
     int temp_8;                        /* (29:50) */
                                        /* (29:50) */
     temp_8 = test_Puint_Puint_Rint(0u, 10u);/* (29:50) */
}

void constructor(struct youngjl_CHelloWorld* this) {
     printLine_PString("object Hello World initialized with no arguments.");/* (8:24) */
}

void constructor_Pint(struct youngjl_CHelloWorld* this, int a) {
     print_PString("object Hello World initialized with one argument: ");/* (13:24) */
     print_Pint(a);                     /* (14:24) */
     printLine_PString(".");            /* (15:24) */
}

void constructor_Pint_Pint(struct youngjl_CHelloWorld* this, int a, int b) {
     print_PString("object Hello World initialized with two arguments: ");/* (20:24) */
     print_Pint(a);                     /* (21:24) */
     print_PString(" and ");            /* (22:24) */
     print_Pint(b);                     /* (23:24) */
     printLine_PString(".");            /* (24:24) */
}

int main(int argc, char **argv) {
     main_PString_A1(argv);
     return 0;
}

