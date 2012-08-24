; shadow.io@Console native methods

%boolean = type i1
%byte = type i8
%ubyte = type i8
%short = type i16
%ushort = type i16
%int = type i32
%uint = type i32
%code = type i32
%long = type i64
%ulong = type i64
%float = type float
%double = type double

%"shadow.standard@Object" = type opaque
%"shadow.standard@Class" = type opaque
%"shadow.standard@String$methods" = type opaque
%"shadow.standard@String" = type { %"shadow.standard@String$methods"*, { %ubyte*, [1 x %long] } }

%FILE = type opaque
@stdout = external global %FILE*

declare i32 @printf(i8*, ...) nounwind
declare i64 @fwrite(%ubyte* nocapture, %long, %long, %FILE* nocapture) nounwind

@str.int = private unnamed_addr constant [3 x i8] c"%i\00"
define void @"shadow.io@Console$static$print$int"(%int) {
	call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @str.int, i32 0, i32 0), %int %0)
	ret void
}

@str.code = private unnamed_addr constant [4 x i8] c"%lc\00"
define void @"shadow.io@Console$static$print$code"(%code) {
	call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @str.code, i32 0, i32 0), %code %0)
	ret void
}

@str.long = private unnamed_addr constant [5 x i8] c"%lli\00"
define void @"shadow.io@Console$static$print$long"(%long) {
	call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([5 x i8]* @str.long, i32 0, i32 0), %long %0)
	ret void
}

define void @"shadow.io@Console$static$print$shadow.standard@String"(%"shadow.standard@String"*) {
	%2 = getelementptr %"shadow.standard@String"* %0, i32 0, i32 1
	%3 = load { %ubyte*, [1 x %long] }* %2
	%4 = extractvalue { %ubyte*, [1 x %long] } %3, 0
	%5 = extractvalue { %ubyte*, [1 x %long] } %3, 1, 0
	%6 = load %FILE** @stdout
	%7 = call i64 @fwrite(%ubyte* %4, %long 1, %long %5, %FILE* %6)
	ret void
}
