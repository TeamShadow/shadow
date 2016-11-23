; Signaler.native.ll
; 
; Author:
; Claude Abounegm

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

; Thread
%shadow.standard..Thread = type opaque

; Pointer
%shadow.natives..ShadowPointer = type opaque

; SignalToken
%shadow.natives..SignalToken = type opaque

; Signaler
%shadow.natives..Signaler = type opaque

;---------------------
; Method Declarations
;---------------------
declare %shadow.natives..ShadowPointer* @__ShadowSignaler_Initialize()
declare %boolean @__ShadowSignaler_Destroy(%shadow.natives..ShadowPointer*)
declare %boolean @__ShadowSignaler_Wait(%shadow.natives..ShadowPointer*)
declare %boolean @__ShadowSignaler_WaitTimeout(%shadow.natives..ShadowPointer*, %long)
declare %boolean @__ShadowSignaler_Broadcast(%shadow.natives..ShadowPointer*)

declare void @shadow.standard..Thread_MsetInterruptTokenNative_shadow.natives..SignalToken(%shadow.standard..Thread*, %shadow.natives..SignalToken*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; initSignaler() => (ShadowPointer);
define %shadow.natives..ShadowPointer* @shadow.natives..Signaler_MinitSignaler(%shadow.natives..Signaler*) {
entry:
	%call = call %shadow.natives..ShadowPointer* @__ShadowSignaler_Initialize()
	ret %shadow.natives..ShadowPointer* %call
}

; destroySignaler(ShadowPointer handle) => (boolean);
define %boolean @shadow.natives..Signaler_MdestroySignaler_shadow.natives..ShadowPointer(%shadow.natives..Signaler*, %shadow.natives..ShadowPointer*) {
entry:
	%call = call %boolean @__ShadowSignaler_Destroy(%shadow.natives..ShadowPointer* %1)
	ret %boolean %call
}

; wait(ShadowPointer handle) => (boolean);
define %boolean @shadow.natives..Signaler_Mwait_shadow.natives..ShadowPointer(%shadow.natives..Signaler*, %shadow.natives..ShadowPointer*) {
entry:
	%call = call %boolean @__ShadowSignaler_Wait(%shadow.natives..ShadowPointer* %1)
	ret %boolean %call
}

; timedWait(ShadowPointer handle, long timeout) => (boolean);
define %boolean @shadow.natives..Signaler_MtimedWait_shadow.natives..ShadowPointer_long(%shadow.natives..Signaler*, %shadow.natives..ShadowPointer*, %long) {
entry:
	%call = call %boolean @__ShadowSignaler_WaitTimeout(%shadow.natives..ShadowPointer* %1, %long %2)
	ret %boolean %call
}

; broadcast(ShadowPointer handle) => (boolean);
define %boolean @shadow.natives..Signaler_Mbroadcast_shadow.natives..ShadowPointer(%shadow.natives..Signaler*, %shadow.natives..ShadowPointer*) {
entry:
	%call = call %boolean @__ShadowSignaler_Broadcast(%shadow.natives..ShadowPointer* %1)
	ret %boolean %call
}

; setInterruptToken(nullable SignalToken interruptToken) => ();
define void @shadow.natives..Signaler_MsetInterruptToken_shadow.standard..Thread_shadow.natives..SignalToken(%shadow.natives..Signaler*, %shadow.standard..Thread*, %shadow.natives..SignalToken*) {
entry:
	call void @shadow.standard..Thread_MsetInterruptTokenNative_shadow.natives..SignalToken(%shadow.standard..Thread* %1, %shadow.natives..SignalToken* %2)

	ret void
}