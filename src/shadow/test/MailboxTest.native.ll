; MailboxTest.native.ll
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

; Mailbox
%shadow.standard..Mailbox = type opaque

; MailboxTest
%shadow.test..MailboxTest = type opaque

;---------
; Globals
;---------
@STATIC_mailbox = private global %shadow.standard..Mailbox* null

;---------------------
; Method Declarations
;---------------------
declare %shadow.standard..Mailbox* @shadow.test..MailboxTest_McreateMailboxNative_int(%shadow.test..MailboxTest*, %int)

;---------------------------
; Shadow Method Definitions
;---------------------------

define void @shadow.test..MailboxTest_MinitMailbox_int(%shadow.test..MailboxTest*, %int %count) {
entry:
	%mailbox = call %shadow.standard..Mailbox* @shadow.test..MailboxTest_McreateMailboxNative_int(%shadow.test..MailboxTest* %0, %int %count)
	store %shadow.standard..Mailbox* %mailbox, %shadow.standard..Mailbox** @STATIC_mailbox
	
	ret void
}

define %shadow.standard..Mailbox* @shadow.test..MailboxTest_MstaticMailbox(%shadow.test..MailboxTest*) {
entry:
	%mailbox = load %shadow.standard..Mailbox*, %shadow.standard..Mailbox** @STATIC_mailbox
	ret %shadow.standard..Mailbox* %mailbox
}