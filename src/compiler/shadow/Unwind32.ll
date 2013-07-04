; Exception related native methods

%_Pshadow_Pstandard_CClass_Mclass = type opaque
%_Pshadow_Pstandard_CClass = type { %_Pshadow_Pstandard_CClass_Mclass* }
%_Pshadow_Pstandard_CObject_Mclass = type { %_Pshadow_Pstandard_CClass }
%_Pshadow_Pstandard_CObject = type { %_Pshadow_Pstandard_CObject_Mclass* }
%_Pshadow_Pstandard_CException = type opaque

declare i1 @_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass(%_Pshadow_Pstandard_CClass*, %_Pshadow_Pstandard_CClass*)
declare void @_Pshadow_Pstandard_CException_Mterminate(%_Pshadow_Pstandard_CException*)

; _URC_NO_REASON = 0
; _URC_FOREIGN_EXCEPTION_CAUGHT = 1
; _URC_FATAL_PHASE2_ERROR = 2
; _URC_FATAL_PHASE1_ERROR = 3
; _URC_NORMAL_STOP = 4
; _URC_END_OF_STACK = 5
; _URC_HANDLER_FOUND = 6
; _URC_INSTALL_CONTEXT = 7
; _URC_CONTINUE_UNWIND = 8

; _UA_SEARCH_PHASE = 1
; _UA_CLEANUP_PHASE = 2
; _UA_HANDLER_FRAME = 3
; _UA_FORCE_UNWIND = 8
; _UA_END_OF_STACK = 16

%_Unwind_Ptr = type i8*
%_Unwind_Word = type i32
%_Unwind_Sword = type i32
%_Unwind_Action = type i32
%_Unwind_Reason_Code = type i32
%_Unwind_Exception_Class = type i64
%_Unwind_Stop_Fn = type %_Unwind_Reason_Code (i32, %_Unwind_Action, %_Unwind_Exception_Class, %struct._Unwind_Exception*, %struct._Unwind_Context*, i8*)*
%_Unwind_Trace_Fn = type %_Unwind_Reason_Code (%struct._Unwind_Context*, i8*)*
%_Unwind_Personality_Fn = type %_Unwind_Reason_Code (i32, %_Unwind_Action, %_Unwind_Exception_Class, %struct._Unwind_Exception*, %struct._Unwind_Context*)*
%_Unwind_Exception_Cleanup_Fn = type void (%_Unwind_Reason_Code, %struct._Unwind_Exception*)*
%struct._Unwind_Context = type opaque
%struct._Unwind_Exception = type { %_Unwind_Exception_Class, %_Unwind_Exception_Cleanup_Fn, i64, i64 }

declare %_Unwind_Reason_Code @_Unwind_RaiseException(%struct._Unwind_Exception*)
declare %_Unwind_Reason_Code @_Unwind_ForcedUnwind(%struct._Unwind_Exception*, %_Unwind_Stop_Fn, i8*)
declare void @_Unwind_DeleteException(%struct._Unwind_Exception*)
declare void @_Unwind_Resume(%struct._Unwind_Exception*)
declare %_Unwind_Reason_Code @_Unwind_Resume_or_Rethrow(%struct._Unwind_Exception)
declare %_Unwind_Reason_Code @_Unwind_Backtrace(%_Unwind_Trace_Fn, i8*)
declare %_Unwind_Word @_Unwind_GetGR(%struct._Unwind_Context*, i32) nounwind readonly
declare void @_Unwind_SetGR(%struct._Unwind_Context*, i32, %_Unwind_Word) nounwind
declare %_Unwind_Ptr @_Unwind_GetIP(%struct._Unwind_Context*) nounwind readonly
declare %_Unwind_Ptr @_Unwind_GetIPInfo(%struct._Unwind_Context*, i32*) nounwind readonly
declare void @_Unwind_SetIP(%struct._Unwind_Context*, %_Unwind_Ptr) nounwind
declare %_Unwind_Word @_Unwind_GetCFA(%struct._Unwind_Context*) nounwind readonly
declare i8* @_Unwind_GetLanguageSpecificData(%struct._Unwind_Context*) nounwind readonly
declare %_Unwind_Ptr @_Unwind_GetRegionStart(%struct._Unwind_Context*) nounwind readonly
declare %_Unwind_Ptr @_Unwind_GetDataRelBase(%struct._Unwind_Context*) nounwind readonly
declare %_Unwind_Ptr @_Unwind_GetTextRelBase(%struct._Unwind_Context*) nounwind readonly
declare i8* @_Unwind_FindEnclosingFunction(i8*) nounwind readonly

declare noalias i8* @malloc(i32) nounwind
declare void @free(i8*) nounwind
declare void @abort() noreturn nounwind
declare void @exit(i32) noreturn nounwind

@shadow.exception.class = private unnamed_addr constant [8 x i8] c"Shadow\00\00", align 8
define private void @shadow.exception.cleanup(%_Unwind_Reason_Code, %struct._Unwind_Exception*) {
entry:
	%2 = bitcast %struct._Unwind_Exception* %1 to i8*
	tail call void @free(i8* %2) nounwind
	ret void
}

define private i32 @size_of_encoded_value(i8 %encoding) nounwind readnone {
entry:
	%omit = icmp eq i8 %encoding, -1 ; omit = 0xff
	br i1 %omit, label %return.0, label %continue
continue:
	%masked = and i8 %encoding, 7 ; 0x07
	switch i8 %masked, label %abort [
		i8 0, label %return.sizeof.ptr ; absptr = 0x00
		i8 2, label %return.2 ; udata2 = 0x02
		i8 3, label %return.4 ; udata4 = 0x03
		i8 4, label %return.8 ; udata8 = 0x04
	]
return.sizeof.ptr: ret i32 ptrtoint (i1** getelementptr (i1** null, i32 1) to i32)
return.0: ret i32 0
return.2: ret i32 2
return.4: ret i32 4
return.8: ret i32 8
abort: tail call void @abort() noreturn nounwind unreachable
}

define private %_Unwind_Ptr @base_of_encoded_value(i8 %encoding, %struct._Unwind_Context* %context) nounwind readonly {
entry:
	%omit = icmp eq i8 %encoding, -1 ; omit = 0xff
	br i1 %omit, label %ret.0, label %continue
continue:
	%masked = and i8 %encoding, 112 ; 0x70
	switch i8 %masked, label %abort [
		i8 0, label %ret.0 ; absptr = 0x00
		i8 16, label %ret.0 ; pcrel = 0x10
		i8 32, label %ret.text ; textrel = 0x20
		i8 48, label %ret.data ; datarel = 0x30
		i8 64, label %ret.func ; funcrel = 0x40
		i8 80, label %ret.0 ; aligned = 0x50
	]
ret.text:
	%text = call %_Unwind_Ptr @_Unwind_GetTextRelBase(%struct._Unwind_Context* %context) nounwind readonly
	ret %_Unwind_Ptr %text
ret.data:
	%data = call %_Unwind_Ptr @_Unwind_GetDataRelBase(%struct._Unwind_Context* %context) nounwind readonly
	ret %_Unwind_Ptr %data
ret.func:
	%func = call %_Unwind_Ptr @_Unwind_GetRegionStart(%struct._Unwind_Context* %context) nounwind readonly
	ret %_Unwind_Ptr %func
ret.0: ret %_Unwind_Ptr inttoptr (i32 0 to %_Unwind_Ptr)
abort: tail call void @abort() noreturn nounwind unreachable
}

define private i8* @read_uleb128(i8* %p, %_Unwind_Word* %val) nounwind {
entry:
	br label %loop
loop:
	%p.phi = phi i8* [ %p, %entry], [ %p.inc, %loop ]
	%shift.phi = phi i32 [ 0, %entry ], [ %shift, %loop ]
	%result.phi = phi i32 [ 0, %entry ], [ %result, %loop ]
	%byte = load i8* %p.phi, align 1
	%p.inc = getelementptr i8* %p.phi, i32 1
	%byte.and = and i8 %byte, 127 ; 0x7f
	%byte.zext = zext i8 %byte.and to i32
	%byte.shl = shl i32 %byte.zext, %shift.phi
	%result = or i32 %result.phi, %byte.shl
	%shift = add i32 %shift.phi, 7
	%sign = and i8 %byte, -128 ; 0x80
	%negative = icmp ne i8 %sign, 0
	br i1 %negative, label %loop, label %loop.end
loop.end:
	store %_Unwind_Word %result, %_Unwind_Word* %val, align 4
	ret i8* %p.inc
}

define private i8* @read_sleb128(i8* %p, %_Unwind_Word* %val) nounwind {
entry:
	br label %loop
loop:
	%p.phi = phi i8* [ %p, %entry ], [ %p.inc, %loop ]
	%shift.phi = phi i32 [ 0, %entry ], [ %shift, %loop ]
	%result.phi = phi i32 [ 0, %entry ], [ %result, %loop ]
	%byte = load i8* %p.phi, align 1
	%p.inc = getelementptr i8* %p.phi, i32 1
	%byte.and = and i8 %byte, 127 ; 0x7f
	%byte.zext = zext i8 %byte.and to i32
	%byte.shl = shl i32 %byte.zext, %shift.phi
	%result = or i32 %result.phi, %byte.shl
	%shift = add i32 %shift.phi, 7
	%sign = and i8 %byte, -128 ; 0x80
	%negative = icmp ne i8 %sign, 0
	br i1 %negative, label %loop, label %loop.end
loop.end:
	%bitsleft = icmp ult i32 %shift, 32
	br i1 %bitsleft, label %continue, label %return
continue:
	%extendbit = and i8 %byte, 64 ; 0x40
	%extend = icmp ne i8 %extendbit, 0
	br i1 %extend, label %negate, label %return
negate:
	%bit = shl i32 1, %shift
	%bits = sub i32 0, %bit
	%negated = or i32 %result, %bits
	br label %return
return:
	%final = phi i32 [ %result, %loop.end ], [ %result, %continue ], [ %negated, %negate ]
	store %_Unwind_Word %final, %_Unwind_Word* %val, align 4
	ret i8* %p.inc
}

define private i8* @read_encoded_value_with_base(i8 %encoding, %_Unwind_Ptr %base, i8* %p, %_Unwind_Ptr* %val) nounwind {
entry:
	%ptr = alloca i8*, align 4
	%result = alloca %_Unwind_Ptr, align 4
	%isaligned = icmp eq i8 %encoding, 80 ; aligned = 0x50
	br i1 %isaligned, label %aligned, label %unaligned
aligned:
	%p.int = ptrtoint i8* %p to i32
	%p.up = add i32 %p.int, sub (i32 ptrtoint (i1** getelementptr (i1** null, i32 1) to i32), i32 1)
	%p.round = and i32 %p.up, sub (i32 0, i32 ptrtoint (i1** getelementptr (i1** null, i32 1) to i32))
	%p.ptr = inttoptr i32 %p.round to %_Unwind_Ptr*
	%p.ptr.load = load %_Unwind_Ptr* %p.ptr, align 4
	store %_Unwind_Ptr %p.ptr.load, %_Unwind_Ptr* %result, align 4
	%p.inc = getelementptr %_Unwind_Ptr* %p.ptr, i32 1
	%p.cast = bitcast %_Unwind_Ptr* %p.inc to i8*
	store i8* %p.cast, i8** %ptr, align 4
	br label %return
unaligned:
	%masked = and i8 %encoding, 15
	switch i8 %masked, label %abort [
		i8 0, label %abs ; absptr = 0x00
		i8 1, label %uleb ; uleb128 = 0x01
		i8 2, label %ushort ; udata2 = 0x02
		i8 3, label %uint ; udata4 = 0x03
		i8 4, label %ulong ; udata8 = 0x04
		i8 9, label %sleb ; sleb128 = 0x09
		i8 10, label %sshort ; sdata2 = 0x0A
		i8 11, label %sint ; sdata4 = 0x0B
		i8 12, label %slong ; sdata8 = 0x0C
	]
abs:
	%p.abs = bitcast i8* %p to %_Unwind_Ptr*
	%p.abs.load = load %_Unwind_Ptr* %p.abs, align 1
	store %_Unwind_Ptr %p.abs.load, %_Unwind_Ptr* %result, align 4
	%p.abs.inc = getelementptr %_Unwind_Ptr* %p.abs, i32 1
	%p.abs.cast = bitcast %_Unwind_Ptr* %p.abs.inc to i8*
	store i8* %p.abs.cast, i8** %ptr, align 4
	br label %continue
uleb:
	%result.uleb = bitcast %_Unwind_Ptr* %result to %_Unwind_Word*
	%p.uleb = call i8* @read_uleb128(i8* %p, %_Unwind_Word* %result.uleb) nounwind
	store i8* %p.uleb, i8** %ptr, align 4
	br label %continue
sleb:
	%result.sleb = bitcast %_Unwind_Ptr* %result to %_Unwind_Sword*
	%p.sleb = call i8* @read_sleb128(i8* %p, %_Unwind_Sword* %result.sleb) nounwind
	store i8* %p.sleb, i8** %ptr, align 4
	br label %continue
ushort:
	%p.ushort = bitcast i8* %p to i16*
	%p.ushort.load = load i16* %p.ushort, align 1
	%result.ushort.ptr = inttoptr i16 %p.ushort.load to %_Unwind_Ptr
	store %_Unwind_Ptr %result.ushort.ptr, %_Unwind_Ptr* %result, align 4
	%p.ushort.inc = getelementptr i16* %p.ushort, i32 1
	%p.ushort.cast = bitcast i16* %p.ushort.inc to i8*
	store i8* %p.ushort.cast, i8** %ptr, align 4
	br label %continue
uint:
	%p.uint = bitcast i8* %p to i32*
	%p.uint.load = load i32* %p.uint, align 1
	%result.uint.ptr = inttoptr i32 %p.uint.load to %_Unwind_Ptr
	store %_Unwind_Ptr %result.uint.ptr, %_Unwind_Ptr* %result, align 4
	%p.uint.inc = getelementptr i32* %p.uint, i32 1
	%p.uint.cast = bitcast i32* %p.uint.inc to i8*
	store i8* %p.uint.cast, i8** %ptr, align 4
	br label %continue
ulong:
	%p.ulong = bitcast i8* %p to i64*
	%p.ulong.load = load i64* %p.ulong, align 1
	%result.ulong.ptr = inttoptr i64 %p.ulong.load to %_Unwind_Ptr
	store %_Unwind_Ptr %result.ulong.ptr, %_Unwind_Ptr* %result, align 4
	%p.ulong.inc = getelementptr i64* %p.ulong, i32 1
	%p.ulong.cast = bitcast i64* %p.ulong.inc to i8*
	store i8* %p.ulong.cast, i8** %ptr, align 4
	br label %continue
sshort:
	%p.sshort = bitcast i8* %p to i16*
	%p.sshort.load = load i16* %p.sshort, align 1
	%result.sshort.sext = sext i16 %p.sshort.load to i32
	%result.sshort.ptr = inttoptr i32 %result.sshort.sext to %_Unwind_Ptr
	store %_Unwind_Ptr %result.sshort.ptr, %_Unwind_Ptr* %result, align 4
	%p.sshort.inc = getelementptr i16* %p.sshort, i32 1
	%p.sshort.cast = bitcast i16* %p.sshort.inc to i8*
	store i8* %p.sshort.cast, i8** %ptr, align 4
	br label %continue
sint:
	%p.sint = bitcast i8* %p to i32*
	%p.sint.load = load i32* %p.sint, align 1
	%result.sint.ptr = inttoptr i32 %p.sint.load to %_Unwind_Ptr
	store %_Unwind_Ptr %result.sint.ptr, %_Unwind_Ptr* %result, align 4
	%p.sint.inc = getelementptr i32* %p.sint, i32 1
	%p.sint.cast = bitcast i32* %p.sint.inc to i8*
	store i8* %p.sint.cast, i8** %ptr, align 4
	br label %continue
slong:
	%p.slong = bitcast i8* %p to i64*
	%p.slong.load = load i64* %p.slong, align 1
	%result.slong.ptr = inttoptr i64 %p.slong.load to %_Unwind_Ptr
	store %_Unwind_Ptr %result.slong.ptr, %_Unwind_Ptr* %result, align 4
	%p.slong.inc = getelementptr i64* %p.slong, i32 1
	%p.slong.cast = bitcast i64* %p.slong.inc to i8*
	store i8* %p.slong.cast, i8** %ptr, align 4
	br label %continue
continue:
	%result.load = load %_Unwind_Ptr* %result, align 4
	%isnull = icmp eq %_Unwind_Ptr %result.load, null
	br i1 %isnull, label %return, label %relative
relative:
	%result.int = ptrtoint %_Unwind_Ptr %result.load to i32
	%masked2 = and i8 %encoding, 112 ; 0x70
	%isrelative = icmp eq i8 %masked2, 16 ; pcrel = 0x10
	%offset = select i1 %isrelative, i8* %p, %_Unwind_Ptr %base
	%offset.int = ptrtoint %_Unwind_Ptr %offset to i32
	%result.abs = add i32 %result.int, %offset.int
	%result.abs.ptr = inttoptr i32 %result.abs to %_Unwind_Ptr
	store %_Unwind_Ptr %result.abs.ptr, %_Unwind_Ptr* %result, align 4
	%masked3 = and i8 %encoding, -128 ; indirect = 0x80
	%isindirect = icmp ne i8 %masked3, 0
	br i1 %isindirect, label %indirect, label %return
indirect:
	%result.ptr = bitcast %_Unwind_Ptr %result.abs.ptr to %_Unwind_Ptr*
	%final = load %_Unwind_Ptr* %result.ptr, align 4
	store %_Unwind_Ptr %final, %_Unwind_Ptr* %result, align 4
	br label %return
return:
	%result.load2 = load %_Unwind_Ptr* %result, align 4
	store %_Unwind_Ptr %result.load2, %_Unwind_Ptr* %val
	%ptr.load = load %_Unwind_Ptr* %ptr, align 4
	ret i8* %ptr.load
abort: tail call void @abort() noreturn nounwind unreachable
}

define private i8* @read_encoded_value(%struct._Unwind_Context* %context, i8 %encoding, i8* %p, %_Unwind_Ptr* %val) nounwind {
entry:
	%base = tail call i8* @base_of_encoded_value(i8 %encoding, %struct._Unwind_Context* %context) nounwind readonly
	%ptr = tail call i8* @read_encoded_value_with_base(i8 %encoding, i8* %base, i8* %p, %_Unwind_Ptr* %val) nounwind
	ret i8* %ptr
}

define %_Unwind_Reason_Code @__shadow_personality_v0(i32 %version, %_Unwind_Action %actions, %_Unwind_Exception_Class %exception.class, %struct._Unwind_Exception* %exception.object, %struct._Unwind_Context* %context) {
entry:
	%temp = alloca %_Unwind_Ptr, align 4
	%temp.word = bitcast %_Unwind_Ptr* %temp to %_Unwind_Word*
	%temp.sword = bitcast %_Unwind_Ptr* %temp to %_Unwind_Sword*
	%action.search_phase.masked = and %_Unwind_Action %actions, 1 ; _UA_SEARCH_PHASE = 1
	%action.search_phase = icmp ne %_Unwind_Action %action.search_phase.masked, 0
	%action.force_unwind.masked = and %_Unwind_Action %actions, 8 ; _UA_FORCE_UNWIND = 8
	%action.force_unwind = icmp ne %_Unwind_Action %action.force_unwind.masked, 0
	%version.is_invalid = icmp ne i32 %version, 1
	br i1 %version.is_invalid, label %return.error, label %version.valid
version.valid:
	%lsda = tail call i8* @_Unwind_GetLanguageSpecificData(%struct._Unwind_Context* %context) nounwind readonly
	%lsda.is_null = icmp eq i8* %lsda, null
	br i1 %lsda.is_null, label %return.continue, label %lsda.valid
lsda.valid:
	%start = tail call %_Unwind_Ptr @_Unwind_GetRegionStart(%struct._Unwind_Context* %context) nounwind readonly
	%landing_pad.start.encoding = load i8* %lsda, align 1
	%p = getelementptr i8* %lsda, i32 1
	%landing_pad.start.is_omitted = icmp eq i8 %landing_pad.start.encoding, -1 ; omit = 0xff
	br i1 %landing_pad.start.is_omitted, label %landing_pad.start.done, label %landing_pad.start.read
landing_pad.start.read:
	%p.1 = call i8* @read_encoded_value(%struct._Unwind_Context* %context, i8 %landing_pad.start.encoding, i8* %p, %_Unwind_Ptr* %temp) nounwind
	%landing_pad.start.value = load %_Unwind_Ptr* %temp, align 4
	br label %landing_pad.start.done
landing_pad.start.done:
	%p.2 = phi i8* [ %p.1, %landing_pad.start.read ], [ %p, %lsda.valid ]
	%landing_pad.start = phi %_Unwind_Ptr [ %landing_pad.start.value, %landing_pad.start.read ], [ %start, %lsda.valid ]
	%ttype.encoding = load i8* %p.2
	%p.3 = getelementptr i8* %p.2, i32 1
	%ttype.is_omitted = icmp eq i8 %ttype.encoding, -1 ; omit = 0xff
	br i1 %ttype.is_omitted, label %ttype.done, label %ttype.read
ttype.read:
	%p.4 = call i8* @read_uleb128(i8* %p.3, %_Unwind_Word* %temp.word) nounwind
	%ttype.offset = load %_Unwind_Word* %temp.word, align 4
	%ttype.value = getelementptr i8* %p.4, i32 %ttype.offset
	br label %ttype.done
ttype.done:
	%p.5 = phi i8* [ %p.4, %ttype.read ], [ %p.3, %landing_pad.start.done ]
	%ttype = phi i8* [ %ttype.value, %ttype.read ], [ null, %landing_pad.start.done ]
	%call_site.encoding = load i8* %p.5, align 1
	%p.6 = getelementptr i8* %p.5, i32 1
	%p.7 = call i8* @read_uleb128(i8* %p.6, %_Unwind_Word* %temp.word) nounwind
	%action_table.offset = load %_Unwind_Word* %temp.word, align 4
	%action_table.base = getelementptr i8* %p.7, i32 %action_table.offset
	%action_table = getelementptr i8* %action_table.base, i32 -1
	%ttype_base = tail call %_Unwind_Ptr @base_of_encoded_value(i8 %ttype.encoding, %struct._Unwind_Context* %context) nounwind readonly
	%ip = tail call %_Unwind_Ptr @_Unwind_GetIP(%struct._Unwind_Context* %context) nounwind readonly
	br label %find.action.cond
find.action.cond:
	%p.8 = phi i8* [ %p.7, %ttype.done ], [ %p.12, %find.action.check ]
	%find.action.continue = icmp ule i8* %p.8, %action_table
	br i1 %find.action.continue, label %find.action.loop, label %return.error
find.action.loop:
	%p.9 = call i8* @read_encoded_value(%struct._Unwind_Context* null, i8 %call_site.encoding, i8* %p.8, %_Unwind_Ptr* %temp) nounwind
	%code_section.start.offset = load %_Unwind_Ptr* %temp, align 4
	%p.10 = call i8* @read_encoded_value(%struct._Unwind_Context* null, i8 %call_site.encoding, i8* %p.9, %_Unwind_Ptr* %temp) nounwind
	%code_section.end.offset = load %_Unwind_Ptr* %temp, align 4
	%p.11 = call i8* @read_encoded_value(%struct._Unwind_Context* null, i8 %call_site.encoding, i8* %p.10, %_Unwind_Ptr* %temp) nounwind
	%landing_pad.offset = load %_Unwind_Ptr* %temp, align 4
	%p.12 = call i8* @read_uleb128(i8* %p.11, %_Unwind_Word* %temp.word) nounwind
	%action_record.offset = load %_Unwind_Word* %temp.word, align 4
	%code_section.start.int_offset = ptrtoint %_Unwind_Ptr %code_section.start.offset to i32
	%code_section.start = getelementptr %_Unwind_Ptr %start, i32 %code_section.start.int_offset
	%find.action.passed = icmp ule %_Unwind_Ptr %ip, %code_section.start
	br i1 %find.action.passed, label %return.error, label %find.action.check
find.action.check:
	%code_section.end.int_offset = ptrtoint %_Unwind_Ptr %code_section.end.offset to i32
	%code_section.end = getelementptr %_Unwind_Ptr %code_section.start, i32 %code_section.end.int_offset
	%find.action.found = icmp ule %_Unwind_Ptr %ip, %code_section.end
	br i1 %find.action.found, label %find.action.success, label %find.action.cond
find.action.success:
	%landing_pad.is_null = icmp eq %_Unwind_Ptr %landing_pad.offset, null
	br i1 %landing_pad.is_null, label %return.continue, label %landing_pad.valid
landing_pad.valid:
	%landing_pad.int_offset = ptrtoint %_Unwind_Ptr %landing_pad.offset to i32
	%landing_pad = getelementptr %_Unwind_Ptr %landing_pad.start, i32 %landing_pad.int_offset
	%action_record.is_null = icmp eq %_Unwind_Word %action_record.offset, 0
	br i1 %action_record.is_null, label %found.cleanup, label %action_record.valid
action_record.valid:
	%action_record = getelementptr i8* %action_table, i32 %action_record.offset
	%ttype.size = tail call i32 @size_of_encoded_value(i8 %ttype.encoding) nounwind readnone
	%ttype.negative_size = sub i32 0, %ttype.size
	br i1 %action.force_unwind, label %find.catch, label %exception.class.check
exception.class.check:
	%shadow.exception.class = load %_Unwind_Exception_Class* bitcast ([8 x i8]* @shadow.exception.class to %_Unwind_Exception_Class*), align 8
	%exception.class.matches = icmp eq %_Unwind_Exception_Class %exception.class, %shadow.exception.class
	br i1 %exception.class.matches, label %thrown.class.get, label %find.catch
thrown.class.get:
	%thrown.pointer = getelementptr %struct._Unwind_Exception* %exception.object, i32 1
	%thrown.pointer.cast = bitcast %struct._Unwind_Exception* %thrown.pointer to %_Pshadow_Pstandard_CObject**
	%thrown = load %_Pshadow_Pstandard_CObject** %thrown.pointer.cast
	%thrown.class.pointer = getelementptr inbounds %_Pshadow_Pstandard_CObject* %thrown, i32 0, i32 0
	%thrown.class.load = load %_Pshadow_Pstandard_CObject_Mclass** %thrown.class.pointer
	%thrown.class.value = getelementptr inbounds %_Pshadow_Pstandard_CObject_Mclass* %thrown.class.load, i32 0, i32 0
	br label %find.catch
find.catch:
	%thrown.class = phi %_Pshadow_Pstandard_CClass* [ %thrown.class.value, %thrown.class.get ], [ null, %exception.class.check ], [ null, %action_record.valid ], [ %thrown.class, %action_record.next.valid ]
	%p.13 = phi i8* [ %action_record, %action_record.valid ], [ %action_record, %thrown.class.get ], [ %action_record, %exception.class.check ], [ %p.17, %action_record.next.valid ]
	%p.14 = call i8* @read_sleb128(i8* %p.13, %_Unwind_Sword* %temp.sword) nounwind
	%filter = load %_Unwind_Sword* %temp.sword, align 4
	%filter.is_cleanup = icmp eq %_Unwind_Sword %filter, 0
	br i1 %filter.is_cleanup, label %found.cleanup, label %find.catch.check
find.catch.check:
	%catch.type.offset = mul nsw %_Unwind_Sword %filter, %ttype.negative_size
	%catch.type.pointer = getelementptr i8* %ttype, i32 %catch.type.offset
	%p.15 = call i8* @read_encoded_value_with_base(i8 %ttype.encoding, %_Unwind_Ptr %ttype_base, i8* %catch.type.pointer, %_Unwind_Ptr* %temp) nounwind
	%catch.class.pointer = load %_Unwind_Ptr* %temp, align 4
	%catch.class = bitcast %_Unwind_Ptr %catch.class.pointer to %_Pshadow_Pstandard_CClass*
	%thrown.is_subtype = call i1 @_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass(%_Pshadow_Pstandard_CClass* %thrown.class, %_Pshadow_Pstandard_CClass* %catch.class)
	br i1 %thrown.is_subtype, label %found.catch, label %find.catch.next
find.catch.next:
	%p.16 = call i8* @read_sleb128(i8* %p.14, %_Unwind_Sword* %temp.sword) nounwind
	%action_record.next = load %_Unwind_Sword* %temp.sword, align 4
	%action_record.next.is_null = icmp eq %_Unwind_Sword %action_record.next, 0
	br i1 %action_record.next.is_null, label %return.continue, label %action_record.next.valid
action_record.next.valid:
	%p.17 = getelementptr i8* %p.14, %_Unwind_Sword %action_record.next
	br label %find.catch
found.catch:
	br i1 %action.search_phase, label %return.handler, label %install.context
found.cleanup:
	br i1 %action.search_phase, label %return.continue, label %install.context
return.error: ret %_Unwind_Reason_Code 3 ; _URC_FATAL_PHASE1_ERROR = 3
return.handler: ret %_Unwind_Reason_Code 6 ; _URC_HANDLER_FOUND = 6
return.continue: ret %_Unwind_Reason_Code 8 ; _URC_CONTINUE_UNWIND = 8
install.context:
	%switch.value = phi %_Unwind_Word [ %filter, %found.catch ], [ 0, %found.cleanup ]
	%exception.object.address = ptrtoint %struct._Unwind_Exception* %exception.object to %_Unwind_Word
	call void @_Unwind_SetGR(%struct._Unwind_Context* %context, i32 0, %_Unwind_Word %exception.object.address) nounwind
	call void @_Unwind_SetGR(%struct._Unwind_Context* %context, i32 2, %_Unwind_Word %switch.value) nounwind
	call void @_Unwind_SetIP(%struct._Unwind_Context* %context, %_Unwind_Ptr %landing_pad) nounwind
	ret %_Unwind_Reason_Code 7 ; _URC_INSTALL_CONTEXT = 7
abort: tail call void @abort() noreturn nounwind unreachable
}

define void @__shadow_throw(%_Pshadow_Pstandard_CObject*) cold noreturn {
entry:
	%1 = tail call noalias i8* @malloc(i32 add (i32 ptrtoint (%struct._Unwind_Exception* getelementptr (%struct._Unwind_Exception* null, i32 1) to i32), i32 ptrtoint (i1** getelementptr (i1** null, i32 1) to i32))) nounwind
	%2 = bitcast i8* %1 to %struct._Unwind_Exception*
	%3 = load %_Unwind_Exception_Class* bitcast ([8 x i8]* @shadow.exception.class to %_Unwind_Exception_Class*)
	%4 = getelementptr %struct._Unwind_Exception* %2, i32 0, i32 0
	store %_Unwind_Exception_Class %3, %_Unwind_Exception_Class* %4
	%5 = getelementptr %struct._Unwind_Exception* %2, i32 0, i32 1
	store %_Unwind_Exception_Cleanup_Fn @shadow.exception.cleanup, %_Unwind_Exception_Cleanup_Fn* %5
	%6 = getelementptr %struct._Unwind_Exception* %2, i32 1
	%7 = bitcast %struct._Unwind_Exception* %6 to %_Pshadow_Pstandard_CObject**
	store %_Pshadow_Pstandard_CObject* %0, %_Pshadow_Pstandard_CObject** %7
	%8 = tail call %_Unwind_Reason_Code @_Unwind_RaiseException(%struct._Unwind_Exception* %2)
	tail call void @abort() noreturn nounwind unreachable
}

define %_Pshadow_Pstandard_CException* @__shadow_catch(i8* nocapture) nounwind {
entry:
	%1 = bitcast i8* %0 to %struct._Unwind_Exception*
	%2 = getelementptr %struct._Unwind_Exception* %1, i32 1
	%3 = bitcast %struct._Unwind_Exception* %2 to %_Pshadow_Pstandard_CException**
	%4 = load %_Pshadow_Pstandard_CException** %3
	tail call void @free(i8* %0) nounwind
	ret %_Pshadow_Pstandard_CException* %4
}
