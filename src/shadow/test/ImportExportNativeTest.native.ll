;-------------
; Definitions
;-------------
; Primitives
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
%void = type i8

; Object
%shadow.standard..Object = type opaque

; Class
%shadow.standard..Class = type opaque

; ImportExportNativeTest
%shadow.test..ImportExportNativeTest = type opaque
;---------------------
; Method Declarations
;---------------------
; add(int a, int b) => (int);
declare %int @shadow.test..ImportExportNativeTest..add.int.int(%shadow.test..ImportExportNativeTest*, %int, %int)

;---------------------------
; Shadow Method Definitions
;---------------------------
define %int @shadow.test..ImportExportNativeTest..subtract.int.int(%shadow.test..ImportExportNativeTest*, %int, %int) {
    %neg = sub %int 0, %2
    %return = call %int @shadow.test..ImportExportNativeTest..add.int.int(%shadow.test..ImportExportNativeTest* %0, %int %1, %int %neg)
    ret %int %return
}
