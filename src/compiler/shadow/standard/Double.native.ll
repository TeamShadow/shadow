; shadow.standard@Double native methods

%_Pshadow_Pstandard_CObject = type opaque
%_Pshadow_Pstandard_CClass = type {}
%_Pshadow_Pstandard_Cdouble_Mclass = type opaque
%_Pshadow_Pstandard_Cdouble = type { %_Pshadow_Pstandard_Cdouble_Mclass*, double }
%_Pshadow_Pstandard_CString_Mclass = type { %_Pshadow_Pstandard_CClass }
@_Pshadow_Pstandard_CString_Mclass = external constant %_Pshadow_Pstandard_CString_Mclass
%_Pshadow_Pstandard_CString = type { %_Pshadow_Pstandard_CString_Mclass*, { i8*, [1 x i32] }, i1 }

declare %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*)
declare %_Pshadow_Pstandard_CString* @_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1(%_Pshadow_Pstandard_CString*, { i8*, [1 x i32] })

declare i32 @sprintf(i8* nocapture, i8* nocapture, ...) nounwind
declare i32 @strlen(i8* nocapture) nounwind

@format = private unnamed_addr constant [3 x i8] c"%f\00"

define %_Pshadow_Pstandard_CString* @_Pshadow_Pstandard_Cdouble_MtoString(%_Pshadow_Pstandard_Cdouble*) {
	%2 = alloca i8, i32 24
	%3 = getelementptr inbounds %_Pshadow_Pstandard_Cdouble* %0, i32 0, i32 1
	%4 = load double* %3
	%5 = call i32 (i8*, i8*, ...)* @sprintf(i8* nocapture %2, i8* nocapture getelementptr inbounds ([3 x i8]* @format, i32 0, i32 0), double %4) nounwind
	%6 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* getelementptr inbounds (%_Pshadow_Pstandard_CString_Mclass* @_Pshadow_Pstandard_CString_Mclass, i32 0, i32 0))
	%7 = bitcast %_Pshadow_Pstandard_CObject* %6 to %_Pshadow_Pstandard_CString*
	%8 = call i32 @strlen(i8* nocapture %2)
	%9 = insertvalue { i8*, [1 x i32] } undef, i8* %2, 0
	%10 = insertvalue { i8*, [1 x i32] } %9, i32 %8, 1, 0
	%11 = call %_Pshadow_Pstandard_CString* @_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1(%_Pshadow_Pstandard_CString* %7, { i8*, [1 x i32] } %10)
	ret %_Pshadow_Pstandard_CString* %11
}
