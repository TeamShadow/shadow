	.file	"<stdin>"
	.text
	.globl	main
	.align	16, 0x90
	.type	main,@function
main:                                   # @main
	.cfi_startproc
# BB#0:
	pushq	%rax
.Ltmp1:
	.cfi_def_cfa_offset 16
	xorl	%edi, %edi
	callq	_Pshadow_Ptest_CTest_Mmain
	xorl	%eax, %eax
	popq	%rdx
	ret
.Ltmp2:
	.size	main, .Ltmp2-main
	.cfi_endproc

	.globl	shadow.generic.load
	.align	16, 0x90
	.type	shadow.generic.load,@function
shadow.generic.load:                    # @shadow.generic.load
	.cfi_startproc
# BB#0:
	xorl	%eax, %eax
	ret
.Ltmp3:
	.size	shadow.generic.load, .Ltmp3-shadow.generic.load
	.cfi_endproc

	.globl	shadow.generic.store
	.align	16, 0x90
	.type	shadow.generic.store,@function
shadow.generic.store:                   # @shadow.generic.store
	.cfi_startproc
# BB#0:
	ret
.Ltmp4:
	.size	shadow.generic.store, .Ltmp4-shadow.generic.store
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mdims
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mdims,@function
_Pshadow_Pstandard_CArray_Mdims:        # @_Pshadow_Pstandard_CArray_Mdims
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp9:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp10:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp11:
	.cfi_def_cfa_offset 32
	subq	$16, %rsp
.Ltmp12:
	.cfi_def_cfa_offset 48
.Ltmp13:
	.cfi_offset %rbx, -32
.Ltmp14:
	.cfi_offset %r14, -24
.Ltmp15:
	.cfi_offset %rbp, -16
	movq	%rdi, 8(%rsp)
	movq	16(%rdi), %r14
	movl	24(%rdi), %ebp
	movl	$40, %edi
	callq	malloc
	movq	%rax, %rbx
	movl	$4, %edi
	callq	malloc
	movl	%ebp, (%rax)
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movq	%rax, %rdx
	movl	$1, %ecx
	callq	_Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*120(%rcx)
	addq	$16, %rsp
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp16:
	.size	_Pshadow_Pstandard_CArray_Mdims, .Ltmp16-_Pshadow_Pstandard_CArray_Mdims
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mlength
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mlength,@function
_Pshadow_Pstandard_CArray_Mlength:      # @_Pshadow_Pstandard_CArray_Mlength
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp19:
	.cfi_def_cfa_offset 16
	subq	$16, %rsp
.Ltmp20:
	.cfi_def_cfa_offset 32
.Ltmp21:
	.cfi_offset %rbx, -16
	movq	%rdi, 8(%rsp)
	movq	16(%rdi), %rax
	movl	(%rax), %eax
	movl	%eax, 4(%rsp)
	movl	$1, (%rsp)
	jmp	.LBB4_2
	.align	16, 0x90
.LBB4_1:                                # %_label0
                                        #   in Loop: Header=BB4_2 Depth=1
	movq	8(%rsp), %rax
	movq	16(%rax), %rax
	movslq	(%rsp), %rcx
	movl	4(%rsp), %edx
	imull	(%rax,%rcx,4), %edx
	movl	%edx, 4(%rsp)
	incl	(%rsp)
.LBB4_2:                                # %_label1
                                        # =>This Inner Loop Header: Depth=1
	movq	8(%rsp), %rdi
	movq	(%rdi), %rax
	movl	(%rsp), %ebx
	callq	*96(%rax)
	cmpl	%eax, %ebx
	jl	.LBB4_1
# BB#3:                                 # %_label2
	movl	4(%rsp), %eax
	addq	$16, %rsp
	popq	%rbx
	ret
.Ltmp22:
	.size	_Pshadow_Pstandard_CArray_Mlength, .Ltmp22-_Pshadow_Pstandard_CArray_Mlength
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mlengths
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mlengths,@function
_Pshadow_Pstandard_CArray_Mlengths:     # @_Pshadow_Pstandard_CArray_Mlengths
	.cfi_startproc
# BB#0:
	movq	%rdi, -8(%rsp)
	movq	16(%rdi), %rax
	movl	24(%rdi), %edx
	ret
.Ltmp23:
	.size	_Pshadow_Pstandard_CArray_Mlengths, .Ltmp23-_Pshadow_Pstandard_CArray_Mlengths
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1,@function
_Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1: # @_Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp26:
	.cfi_def_cfa_offset 16
	subq	$32, %rsp
.Ltmp27:
	.cfi_def_cfa_offset 48
.Ltmp28:
	.cfi_offset %rbx, -16
	movq	%rdi, 24(%rsp)
	movq	%rsi, 16(%rsp)
	movl	%ecx, 8(%rsp)
	movq	%rdx, (%rsp)
	movq	24(%rsp), %rax
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	24(%rsp), %rax
	movq	$0, 8(%rax)
	movq	24(%rsp), %rbx
	xorl	%edi, %edi
	movl	$4, %esi
	callq	calloc
	movq	%rax, 16(%rbx)
	movl	$0, 24(%rbx)
	movq	16(%rsp), %rax
	movq	24(%rsp), %rcx
	movq	%rax, 8(%rcx)
	movq	(%rsp), %rax
	movl	8(%rsp), %ecx
	movq	24(%rsp), %rdx
	movl	%ecx, 24(%rdx)
	movq	%rax, 16(%rdx)
	movq	24(%rsp), %rax
	addq	$32, %rsp
	popq	%rbx
	ret
.Ltmp29:
	.size	_Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1, .Ltmp29-_Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mindex_Rint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mindex_Rint,@function
_Pshadow_Pstandard_CArray_Mindex_Rint:  # @_Pshadow_Pstandard_CArray_Mindex_Rint
	.cfi_startproc
# BB#0:
	xorl	%eax, %eax
	ret
.Ltmp30:
	.size	_Pshadow_Pstandard_CArray_Mindex_Rint, .Ltmp30-_Pshadow_Pstandard_CArray_Mindex_Rint
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mindex_Rint_RT
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mindex_Rint_RT,@function
_Pshadow_Pstandard_CArray_Mindex_Rint_RT: # @_Pshadow_Pstandard_CArray_Mindex_Rint_RT
	.cfi_startproc
# BB#0:
	ret
.Ltmp31:
	.size	_Pshadow_Pstandard_CArray_Mindex_Rint_RT, .Ltmp31-_Pshadow_Pstandard_CArray_Mindex_Rint_RT
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass,@function
_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass: # @_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass
	.cfi_startproc
# BB#0:
	subq	$24, %rsp
.Ltmp33:
	.cfi_def_cfa_offset 32
	movq	%rdi, 16(%rsp)
	movq	%rsi, 8(%rsp)
	cmpq	%rsi, 16(%rsp)
	je	.LBB9_3
# BB#1:                                 # %_label2
	movq	16(%rsp), %rax
	cmpq	$0, 32(%rax)
	je	.LBB9_2
# BB#4:                                 # %_label5
	movq	32(%rax), %rdi
	movq	(%rdi), %rax
	movq	8(%rsp), %rsi
	callq	*96(%rax)
	addq	$24, %rsp
	ret
.LBB9_3:                                # %_label0
	movb	$1, %al
	addq	$24, %rsp
	ret
.LBB9_2:                                # %_label4
	xorb	%al, %al
	addq	$24, %rsp
	ret
.Ltmp34:
	.size	_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass, .Ltmp34-_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface,@function
_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface: # @_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp40:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp41:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp42:
	.cfi_def_cfa_offset 32
	pushq	%rbx
.Ltmp43:
	.cfi_def_cfa_offset 40
	subq	$24, %rsp
.Ltmp44:
	.cfi_def_cfa_offset 64
.Ltmp45:
	.cfi_offset %rbx, -40
.Ltmp46:
	.cfi_offset %r14, -32
.Ltmp47:
	.cfi_offset %r15, -24
.Ltmp48:
	.cfi_offset %rbp, -16
	movq	%rdi, 16(%rsp)
	movq	%rsi, 8(%rsp)
	movq	$0, (%rsp)
	jmp	.LBB10_6
	.align	16, 0x90
.LBB10_5:                               # %_label10
                                        #   in Loop: Header=BB10_6 Depth=1
	incq	(%rsp)
.LBB10_6:                               # %_label7
                                        # =>This Inner Loop Header: Depth=1
	movq	16(%rsp), %rax
	movq	8(%rax), %r14
	movl	16(%rax), %ebp
	movq	(%rsp), %r15
	movl	$8, %edi
	callq	malloc
	movq	%rax, %rbx
	movl	$4, %edi
	callq	malloc
	movl	%ebp, (%rax)
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movq	%rax, %rdx
	movl	$1, %ecx
	callq	_Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*120(%rcx)
	movslq	%eax, %rax
	cmpq	%rax, %r15
	jge	.LBB10_7
# BB#1:                                 # %_label6
                                        #   in Loop: Header=BB10_6 Depth=1
	movq	16(%rsp), %rax
	movq	8(%rax), %rax
	movq	(%rsp), %rcx
	cmpq	$0, (%rax,%rcx,8)
	je	.LBB10_5
# BB#2:                                 # %_label14
                                        #   in Loop: Header=BB10_6 Depth=1
	movq	(%rax,%rcx,8), %rdi
	movq	(%rdi), %rax
	movq	8(%rsp), %rsi
	callq	*96(%rax)
	testb	$1, %al
	je	.LBB10_5
# BB#3:                                 # %_label11
	movb	$1, %al
	jmp	.LBB10_4
.LBB10_7:                               # %_label8
	xorb	%al, %al
.LBB10_4:                               # %_label11
	addq	$24, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp49:
	.size	_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface, .Ltmp49-_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CClass_Mparent
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_Mparent,@function
_Pshadow_Pstandard_CClass_Mparent:      # @_Pshadow_Pstandard_CClass_Mparent
	.cfi_startproc
# BB#0:
	movq	%rdi, -8(%rsp)
	movq	32(%rdi), %rax
	ret
.Ltmp50:
	.size	_Pshadow_Pstandard_CClass_Mparent, .Ltmp50-_Pshadow_Pstandard_CClass_Mparent
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CClass_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_MtoString,@function
_Pshadow_Pstandard_CClass_MtoString:    # @_Pshadow_Pstandard_CClass_MtoString
	.cfi_startproc
# BB#0:
	movq	%rdi, -8(%rsp)
	movq	24(%rdi), %rax
	ret
.Ltmp51:
	.size	_Pshadow_Pstandard_CClass_MtoString, .Ltmp51-_Pshadow_Pstandard_CClass_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CClass_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_Mcreate,@function
_Pshadow_Pstandard_CClass_Mcreate:      # @_Pshadow_Pstandard_CClass_Mcreate
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp54:
	.cfi_def_cfa_offset 16
	subq	$16, %rsp
.Ltmp55:
	.cfi_def_cfa_offset 32
.Ltmp56:
	.cfi_offset %rbx, -16
	movq	%rdi, 8(%rsp)
	movq	$_Pshadow_Pstandard_CClass_Mclass, (%rdi)
	movq	8(%rsp), %rbx
	xorl	%edi, %edi
	movl	$8, %esi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$0, 16(%rbx)
	movq	8(%rsp), %rax
	movq	$.L_string1, 24(%rax)
	movq	8(%rsp), %rax
	movl	$0, 40(%rax)
	movq	8(%rsp), %rax
	movl	$0, 44(%rax)
	movq	8(%rsp), %rax
	movq	$0, 32(%rax)
	movq	8(%rsp), %rax
	addq	$16, %rsp
	popq	%rbx
	ret
.Ltmp57:
	.size	_Pshadow_Pstandard_CClass_Mcreate, .Ltmp57-_Pshadow_Pstandard_CClass_Mcreate
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface,@function
_Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface: # @_Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp63:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp64:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp65:
	.cfi_def_cfa_offset 32
	pushq	%rbx
.Ltmp66:
	.cfi_def_cfa_offset 40
	subq	$24, %rsp
.Ltmp67:
	.cfi_def_cfa_offset 64
.Ltmp68:
	.cfi_offset %rbx, -40
.Ltmp69:
	.cfi_offset %r14, -32
.Ltmp70:
	.cfi_offset %r15, -24
.Ltmp71:
	.cfi_offset %rbp, -16
	movq	%rdi, 16(%rsp)
	movq	%rsi, 8(%rsp)
	cmpq	%rsi, 16(%rsp)
	je	.LBB14_1
# BB#3:                                 # %_label2
	movq	$0, (%rsp)
	jmp	.LBB14_7
	.align	16, 0x90
.LBB14_6:                               # %_label7
                                        #   in Loop: Header=BB14_7 Depth=1
	incq	(%rsp)
.LBB14_7:                               # %_label4
                                        # =>This Inner Loop Header: Depth=1
	movq	16(%rsp), %rax
	movq	8(%rax), %r14
	movl	16(%rax), %ebp
	movq	(%rsp), %r15
	movl	$8, %edi
	callq	malloc
	movq	%rax, %rbx
	movl	$4, %edi
	callq	malloc
	movl	%ebp, (%rax)
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movq	%rax, %rdx
	movl	$1, %ecx
	callq	_Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*120(%rcx)
	movslq	%eax, %rax
	cmpq	%rax, %r15
	jge	.LBB14_8
# BB#4:                                 # %_label3
                                        #   in Loop: Header=BB14_7 Depth=1
	movq	16(%rsp), %rax
	movq	8(%rax), %rax
	movq	(%rsp), %rcx
	cmpq	$0, (%rax,%rcx,8)
	je	.LBB14_6
# BB#5:                                 # %_label11
                                        #   in Loop: Header=BB14_7 Depth=1
	movq	(%rax,%rcx,8), %rdi
	movq	(%rdi), %rax
	movq	8(%rsp), %rsi
	callq	*96(%rax)
	testb	$1, %al
	je	.LBB14_6
.LBB14_1:                               # %_label0
	movb	$1, %al
	jmp	.LBB14_2
.LBB14_8:                               # %_label5
	xorb	%al, %al
.LBB14_2:                               # %_label0
	addq	$24, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp72:
	.size	_Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface, .Ltmp72-_Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CInterface_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CInterface_MtoString,@function
_Pshadow_Pstandard_CInterface_MtoString: # @_Pshadow_Pstandard_CInterface_MtoString
	.cfi_startproc
# BB#0:
	movq	%rdi, -8(%rsp)
	movq	24(%rdi), %rax
	ret
.Ltmp73:
	.size	_Pshadow_Pstandard_CInterface_MtoString, .Ltmp73-_Pshadow_Pstandard_CInterface_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CInterface_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CInterface_Mcreate,@function
_Pshadow_Pstandard_CInterface_Mcreate:  # @_Pshadow_Pstandard_CInterface_Mcreate
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp76:
	.cfi_def_cfa_offset 16
	subq	$16, %rsp
.Ltmp77:
	.cfi_def_cfa_offset 32
.Ltmp78:
	.cfi_offset %rbx, -16
	movq	%rdi, 8(%rsp)
	movq	$_Pshadow_Pstandard_CInterface_Mclass, (%rdi)
	movq	8(%rsp), %rbx
	xorl	%edi, %edi
	movl	$8, %esi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$0, 16(%rbx)
	movq	8(%rsp), %rax
	movq	$.L_string124, 24(%rax)
	movq	8(%rsp), %rax
	addq	$16, %rsp
	popq	%rbx
	ret
.Ltmp79:
	.size	_Pshadow_Pstandard_CInterface_Mcreate, .Ltmp79-_Pshadow_Pstandard_CInterface_Mcreate
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject,@function
_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	subq	$24, %rsp
.Ltmp81:
	.cfi_def_cfa_offset 32
	movq	%rdi, 16(%rsp)
	movq	%rsi, 8(%rsp)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*80(%rax)
	addq	$24, %rsp
	ret
.Ltmp82:
	.size	_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject, .Ltmp82-_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CObject_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_MtoString,@function
_Pshadow_Pstandard_CObject_MtoString:   # @_Pshadow_Pstandard_CObject_MtoString
	.cfi_startproc
# BB#0:
	pushq	%rax
.Ltmp84:
	.cfi_def_cfa_offset 16
	movq	%rdi, (%rsp)
	movq	(%rdi), %rax
	callq	*72(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*88(%rcx)
	popq	%rdx
	ret
.Ltmp85:
	.size	_Pshadow_Pstandard_CObject_MtoString, .Ltmp85-_Pshadow_Pstandard_CObject_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CObject_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_Mcreate,@function
_Pshadow_Pstandard_CObject_Mcreate:     # @_Pshadow_Pstandard_CObject_Mcreate
	.cfi_startproc
# BB#0:
	movq	%rdi, -8(%rsp)
	movq	$_Pshadow_Pstandard_CObject_Mclass, (%rdi)
	movq	-8(%rsp), %rax
	ret
.Ltmp86:
	.size	_Pshadow_Pstandard_CObject_Mcreate, .Ltmp86-_Pshadow_Pstandard_CObject_Mcreate
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject,@function
_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	cmpq	%rsi, %rdi
	sete	%al
	ret
.Ltmp87:
	.size	_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject, .Ltmp87-_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CObject_MgetClass
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_MgetClass,@function
_Pshadow_Pstandard_CObject_MgetClass:   # @_Pshadow_Pstandard_CObject_MgetClass
	.cfi_startproc
# BB#0:
	movq	(%rdi), %rax
	ret
.Ltmp88:
	.size	_Pshadow_Pstandard_CObject_MgetClass, .Ltmp88-_Pshadow_Pstandard_CObject_MgetClass
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CObject_Mcopy
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_Mcopy,@function
_Pshadow_Pstandard_CObject_Mcopy:       # @_Pshadow_Pstandard_CObject_Mcopy
	.cfi_startproc
# BB#0:
	movq	%rdi, %rax
	ret
.Ltmp89:
	.size	_Pshadow_Pstandard_CObject_Mcopy, .Ltmp89-_Pshadow_Pstandard_CObject_Mcopy
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CObject_Mfreeze
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_Mfreeze,@function
_Pshadow_Pstandard_CObject_Mfreeze:     # @_Pshadow_Pstandard_CObject_Mfreeze
	.cfi_startproc
# BB#0:
	movq	%rdi, %rax
	ret
.Ltmp90:
	.size	_Pshadow_Pstandard_CObject_Mfreeze, .Ltmp90-_Pshadow_Pstandard_CObject_Mfreeze
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString,@function
_Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString: # @_Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp94:
	.cfi_def_cfa_offset 16
	pushq	%rbx
.Ltmp95:
	.cfi_def_cfa_offset 24
	subq	$24, %rsp
.Ltmp96:
	.cfi_def_cfa_offset 48
.Ltmp97:
	.cfi_offset %rbx, -24
.Ltmp98:
	.cfi_offset %rbp, -16
	movq	%rsi, %rbx
	movq	%rdi, 16(%rsp)
	movq	%rbx, 8(%rsp)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*120(%rax)
	movl	%eax, %ebp
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*120(%rax)
	cmpl	%eax, %ebp
	jne	.LBB24_1
# BB#3:                                 # %_label2
	movl	$0, 4(%rsp)
	jmp	.LBB24_6
	.align	16, 0x90
.LBB24_5:                               # %_label8
                                        #   in Loop: Header=BB24_6 Depth=1
	incl	4(%rsp)
.LBB24_6:                               # %_label4
                                        # =>This Inner Loop Header: Depth=1
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	movl	4(%rsp), %ebx
	callq	*120(%rax)
	cmpl	%eax, %ebx
	jge	.LBB24_7
# BB#4:                                 # %_label3
                                        #   in Loop: Header=BB24_6 Depth=1
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	movl	4(%rsp), %esi
	callq	*112(%rax)
	movb	%al, %bl
	movq	8(%rsp), %rdi
	movq	(%rdi), %rax
	movl	4(%rsp), %esi
	callq	*112(%rax)
	cmpb	%al, %bl
	je	.LBB24_5
.LBB24_1:                               # %_label0
	xorb	%al, %al
	jmp	.LBB24_2
.LBB24_7:                               # %_label5
	movb	$1, %al
.LBB24_2:                               # %_label0
	addq	$24, %rsp
	popq	%rbx
	popq	%rbp
	ret
.Ltmp99:
	.size	_Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString, .Ltmp99-_Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_MgetByte_Rint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_MgetByte_Rint,@function
_Pshadow_Pstandard_CString_MgetByte_Rint: # @_Pshadow_Pstandard_CString_MgetByte_Rint
	.cfi_startproc
# BB#0:
	movq	%rdi, -8(%rsp)
	movl	%esi, -12(%rsp)
	movq	-8(%rsp), %rax
	movq	8(%rax), %rax
	movslq	-12(%rsp), %rcx
	movb	(%rax,%rcx), %al
	ret
.Ltmp100:
	.size	_Pshadow_Pstandard_CString_MgetByte_Rint, .Ltmp100-_Pshadow_Pstandard_CString_MgetByte_Rint
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString,@function
_Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString: # @_Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp105:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp106:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp107:
	.cfi_def_cfa_offset 32
	subq	$32, %rsp
.Ltmp108:
	.cfi_def_cfa_offset 64
.Ltmp109:
	.cfi_offset %rbx, -32
.Ltmp110:
	.cfi_offset %r14, -24
.Ltmp111:
	.cfi_offset %rbp, -16
	movq	%rdi, 24(%rsp)
	movq	%rsi, 16(%rsp)
	movl	$0, 12(%rsp)
	jmp	.LBB26_4
	.align	16, 0x90
.LBB26_3:                               # %_label14
                                        #   in Loop: Header=BB26_4 Depth=1
	incl	12(%rsp)
.LBB26_4:                               # %_label10
                                        # =>This Inner Loop Header: Depth=1
	movq	24(%rsp), %rdi
	movq	(%rdi), %rax
	movl	12(%rsp), %r14d
	callq	*120(%rax)
	movl	%eax, %ebx
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	movl	12(%rsp), %ebp
	callq	*120(%rax)
	cmpl	%ebx, %r14d
	jge	.LBB26_6
# BB#5:                                 # %_label10
                                        #   in Loop: Header=BB26_4 Depth=1
	cmpl	%eax, %ebp
	jge	.LBB26_6
# BB#1:                                 # %_label9
                                        #   in Loop: Header=BB26_4 Depth=1
	movq	24(%rsp), %rdi
	movq	(%rdi), %rax
	movl	12(%rsp), %esi
	callq	*112(%rax)
	movb	%al, %bl
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	movl	12(%rsp), %esi
	callq	*112(%rax)
	cmpb	%al, %bl
	je	.LBB26_3
# BB#2:                                 # %_label12
	movq	24(%rsp), %rdi
	movq	(%rdi), %rax
	movl	12(%rsp), %esi
	callq	*112(%rax)
	movb	%al, %bl
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	movl	12(%rsp), %esi
	callq	*112(%rax)
	subb	%al, %bl
	movsbl	%bl, %eax
	jmp	.LBB26_7
.LBB26_6:                               # %_label11
	movq	24(%rsp), %rdi
	movq	(%rdi), %rax
	movq	16(%rsp), %r14
	callq	*120(%rax)
	movl	%eax, %ebx
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*120(%rax)
	subl	%eax, %ebx
	movl	%ebx, %eax
.LBB26_7:                               # %_label11
	addq	$32, %rsp
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp112:
	.size	_Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString, .Ltmp112-_Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_Mlength
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mlength,@function
_Pshadow_Pstandard_CString_Mlength:     # @_Pshadow_Pstandard_CString_Mlength
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp117:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp118:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp119:
	.cfi_def_cfa_offset 32
	subq	$16, %rsp
.Ltmp120:
	.cfi_def_cfa_offset 48
.Ltmp121:
	.cfi_offset %rbx, -32
.Ltmp122:
	.cfi_offset %r14, -24
.Ltmp123:
	.cfi_offset %rbp, -16
	movq	%rdi, 8(%rsp)
	movq	8(%rdi), %r14
	movl	16(%rdi), %ebp
	movl	$8, %edi
	callq	malloc
	movq	%rax, %rbx
	movl	$4, %edi
	callq	malloc
	movl	%ebp, (%rax)
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movq	%rax, %rdx
	movl	$1, %ecx
	callq	_Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*120(%rcx)
	addq	$16, %rsp
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp124:
	.size	_Pshadow_Pstandard_CString_Mlength, .Ltmp124-_Pshadow_Pstandard_CString_Mlength
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_MtoString,@function
_Pshadow_Pstandard_CString_MtoString:   # @_Pshadow_Pstandard_CString_MtoString
	.cfi_startproc
# BB#0:
	movq	%rdi, -8(%rsp)
	movq	%rdi, %rax
	ret
.Ltmp125:
	.size	_Pshadow_Pstandard_CString_MtoString, .Ltmp125-_Pshadow_Pstandard_CString_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mcreate,@function
_Pshadow_Pstandard_CString_Mcreate:     # @_Pshadow_Pstandard_CString_Mcreate
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp128:
	.cfi_def_cfa_offset 16
	subq	$16, %rsp
.Ltmp129:
	.cfi_def_cfa_offset 32
.Ltmp130:
	.cfi_offset %rbx, -16
	movq	%rdi, 8(%rsp)
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rdi)
	movq	8(%rsp), %rbx
	xorl	%edi, %edi
	movl	$1, %esi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$0, 16(%rbx)
	movq	8(%rsp), %rax
	addq	$16, %rsp
	popq	%rbx
	ret
.Ltmp131:
	.size	_Pshadow_Pstandard_CString_Mcreate, .Ltmp131-_Pshadow_Pstandard_CString_Mcreate
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_MprintLine_Rboolean
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintLine_Rboolean,@function
_Pshadow_Pio_CConsole_MprintLine_Rboolean: # @_Pshadow_Pio_CConsole_MprintLine_Rboolean
	.cfi_startproc
# BB#0:
	subq	$24, %rsp
.Ltmp133:
	.cfi_def_cfa_offset 32
	movq	%rdi, 16(%rsp)
	andl	$1, %esi
	movb	%sil, 15(%rsp)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	movzbl	15(%rsp), %esi
	callq	*96(%rax)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*192(%rax)
	addq	$24, %rsp
	ret
.Ltmp134:
	.size	_Pshadow_Pio_CConsole_MprintLine_Rboolean, .Ltmp134-_Pshadow_Pio_CConsole_MprintLine_Rboolean
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_MprintLine_Rint
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintLine_Rint,@function
_Pshadow_Pio_CConsole_MprintLine_Rint:  # @_Pshadow_Pio_CConsole_MprintLine_Rint
	.cfi_startproc
# BB#0:
	subq	$24, %rsp
.Ltmp136:
	.cfi_def_cfa_offset 32
	movq	%rdi, 16(%rsp)
	movl	%esi, 12(%rsp)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*112(%rax)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*192(%rax)
	addq	$24, %rsp
	ret
.Ltmp137:
	.size	_Pshadow_Pio_CConsole_MprintLine_Rint, .Ltmp137-_Pshadow_Pio_CConsole_MprintLine_Rint
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_MprintLine_Rcode
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintLine_Rcode,@function
_Pshadow_Pio_CConsole_MprintLine_Rcode: # @_Pshadow_Pio_CConsole_MprintLine_Rcode
	.cfi_startproc
# BB#0:
	subq	$24, %rsp
.Ltmp139:
	.cfi_def_cfa_offset 32
	movq	%rdi, 16(%rsp)
	movl	%esi, 12(%rsp)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*120(%rax)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*192(%rax)
	addq	$24, %rsp
	ret
.Ltmp140:
	.size	_Pshadow_Pio_CConsole_MprintLine_Rcode, .Ltmp140-_Pshadow_Pio_CConsole_MprintLine_Rcode
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_MprintLine_Rlong
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintLine_Rlong,@function
_Pshadow_Pio_CConsole_MprintLine_Rlong: # @_Pshadow_Pio_CConsole_MprintLine_Rlong
	.cfi_startproc
# BB#0:
	subq	$24, %rsp
.Ltmp142:
	.cfi_def_cfa_offset 32
	movq	%rdi, 16(%rsp)
	movq	%rsi, 8(%rsp)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*128(%rax)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*192(%rax)
	addq	$24, %rsp
	ret
.Ltmp143:
	.size	_Pshadow_Pio_CConsole_MprintLine_Rlong, .Ltmp143-_Pshadow_Pio_CConsole_MprintLine_Rlong
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString,@function
_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString: # @_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	subq	$24, %rsp
.Ltmp145:
	.cfi_def_cfa_offset 32
	movq	%rdi, 16(%rsp)
	movq	%rsi, 8(%rsp)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*136(%rax)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*192(%rax)
	addq	$24, %rsp
	ret
.Ltmp146:
	.size	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString, .Ltmp146-_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject,@function
_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject: # @_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	subq	$24, %rsp
.Ltmp148:
	.cfi_def_cfa_offset 32
	movq	%rdi, 16(%rsp)
	movq	%rsi, 8(%rsp)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*104(%rax)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*192(%rax)
	addq	$24, %rsp
	ret
.Ltmp149:
	.size	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject, .Ltmp149-_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_MprintLine
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintLine,@function
_Pshadow_Pio_CConsole_MprintLine:       # @_Pshadow_Pio_CConsole_MprintLine
	.cfi_startproc
# BB#0:
	pushq	%rax
.Ltmp151:
	.cfi_def_cfa_offset 16
	movq	%rdi, (%rsp)
	movq	(%rdi), %rax
	movl	$10, %esi
	callq	*120(%rax)
	popq	%rax
	ret
.Ltmp152:
	.size	_Pshadow_Pio_CConsole_MprintLine, .Ltmp152-_Pshadow_Pio_CConsole_MprintLine
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_Mcreate,@function
_Pshadow_Pio_CConsole_Mcreate:          # @_Pshadow_Pio_CConsole_Mcreate
	.cfi_startproc
# BB#0:
	movq	%rdi, -8(%rsp)
	movq	$_Pshadow_Pio_CConsole_Mclass, (%rdi)
	movq	-8(%rsp), %rax
	ret
.Ltmp153:
	.size	_Pshadow_Pio_CConsole_Mcreate, .Ltmp153-_Pshadow_Pio_CConsole_Mcreate
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_Mprint_Rboolean
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_Mprint_Rboolean,@function
_Pshadow_Pio_CConsole_Mprint_Rboolean:  # @_Pshadow_Pio_CConsole_Mprint_Rboolean
	.cfi_startproc
# BB#0:
	subq	$24, %rsp
.Ltmp155:
	.cfi_def_cfa_offset 32
	movq	%rdi, 16(%rsp)
	andb	$1, %sil
	movb	%sil, 15(%rsp)
	je	.LBB38_2
# BB#1:                                 # %_label0
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	movl	$.L_string168, %esi
	jmp	.LBB38_3
.LBB38_2:                               # %_label1
	movq	16(%rsp), %rdi
	movq	(%rdi), %rax
	movl	$.L_string2, %esi
.LBB38_3:                               # %_label1
	callq	*136(%rax)
	addq	$24, %rsp
	ret
.Ltmp156:
	.size	_Pshadow_Pio_CConsole_Mprint_Rboolean, .Ltmp156-_Pshadow_Pio_CConsole_Mprint_Rboolean
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject,@function
_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject: # @_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	subq	$24, %rsp
.Ltmp158:
	.cfi_def_cfa_offset 32
	movq	%rdi, 16(%rsp)
	movq	%rsi, 8(%rsp)
	movq	(%rsi), %rax
	movq	%rsi, %rdi
	callq	*88(%rax)
	movq	16(%rsp), %rdi
	movq	(%rdi), %rcx
	movq	%rax, %rsi
	callq	*136(%rcx)
	addq	$24, %rsp
	ret
.Ltmp159:
	.size	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject, .Ltmp159-_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject
	.cfi_endproc

	.align	16, 0x90
	.type	.Lout,@function
.Lout:                                  # @out
	.cfi_startproc
# BB#0:
	movl	$stdout, %eax
	testq	%rax, %rax
	je	.LBB40_2
# BB#1:
	movq	stdout(%rip), %rax
	ret
.LBB40_2:
	movq	_imp___iob(%rip), %rax
	addq	$48, %rax
	ret
.Ltmp160:
	.size	.Lout, .Ltmp160-.Lout
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_Mprint_Rint
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_Mprint_Rint,@function
_Pshadow_Pio_CConsole_Mprint_Rint:      # @_Pshadow_Pio_CConsole_Mprint_Rint
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp163:
	.cfi_def_cfa_offset 16
.Ltmp164:
	.cfi_offset %rbx, -16
	movl	%esi, %ebx
	callq	.Lout
	movq	%rax, %rdi
	movl	$.Lstr.int, %esi
	movl	%ebx, %edx
	xorb	%al, %al
	callq	fprintf
	popq	%rbx
	ret
.Ltmp165:
	.size	_Pshadow_Pio_CConsole_Mprint_Rint, .Ltmp165-_Pshadow_Pio_CConsole_Mprint_Rint
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_Mprint_Rcode
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_Mprint_Rcode,@function
_Pshadow_Pio_CConsole_Mprint_Rcode:     # @_Pshadow_Pio_CConsole_Mprint_Rcode
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp168:
	.cfi_def_cfa_offset 16
.Ltmp169:
	.cfi_offset %rbx, -16
	movl	%esi, %ebx
	callq	.Lout
	movq	%rax, %rdi
	movl	$.Lstr.code, %esi
	movl	%ebx, %edx
	xorb	%al, %al
	callq	fprintf
	popq	%rbx
	ret
.Ltmp170:
	.size	_Pshadow_Pio_CConsole_Mprint_Rcode, .Ltmp170-_Pshadow_Pio_CConsole_Mprint_Rcode
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_Mprint_Rlong
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_Mprint_Rlong,@function
_Pshadow_Pio_CConsole_Mprint_Rlong:     # @_Pshadow_Pio_CConsole_Mprint_Rlong
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp173:
	.cfi_def_cfa_offset 16
.Ltmp174:
	.cfi_offset %rbx, -16
	movq	%rsi, %rbx
	callq	.Lout
	movq	%rax, %rdi
	movl	$.Lstr.long, %esi
	movq	%rbx, %rdx
	xorb	%al, %al
	callq	fprintf
	popq	%rbx
	ret
.Ltmp175:
	.size	_Pshadow_Pio_CConsole_Mprint_Rlong, .Ltmp175-_Pshadow_Pio_CConsole_Mprint_Rlong
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString,@function
_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString: # @_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp179:
	.cfi_def_cfa_offset 16
	pushq	%rbx
.Ltmp180:
	.cfi_def_cfa_offset 24
	pushq	%rax
.Ltmp181:
	.cfi_def_cfa_offset 32
.Ltmp182:
	.cfi_offset %rbx, -24
.Ltmp183:
	.cfi_offset %rbp, -16
	movl	16(%rsi), %ebp
	movq	8(%rsi), %rbx
	callq	.Lout
	movq	%rbx, %rdi
	movl	$1, %esi
	movl	%ebp, %edx
	movq	%rax, %rcx
	callq	fwrite
	addq	$8, %rsp
	popq	%rbx
	popq	%rbp
	ret
.Ltmp184:
	.size	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString, .Ltmp184-_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString
	.cfi_endproc

	.globl	_Pshadow_Ptest_CTest_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Ptest_CTest_Mcreate,@function
_Pshadow_Ptest_CTest_Mcreate:           # @_Pshadow_Ptest_CTest_Mcreate
	.cfi_startproc
# BB#0:
	movq	%rdi, -8(%rsp)
	movq	$_Pshadow_Ptest_CTest_Mclass, (%rdi)
	movq	-8(%rsp), %rax
	movq	%rax, 8(%rax)
	movq	-8(%rsp), %rax
	ret
.Ltmp185:
	.size	_Pshadow_Ptest_CTest_Mcreate, .Ltmp185-_Pshadow_Ptest_CTest_Mcreate
	.cfi_endproc

	.globl	_Pshadow_Ptest_CTest_Mmain
	.align	16, 0x90
	.type	_Pshadow_Ptest_CTest_Mmain,@function
_Pshadow_Ptest_CTest_Mmain:             # @_Pshadow_Ptest_CTest_Mmain
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp188:
	.cfi_def_cfa_offset 16
	subq	$32, %rsp
.Ltmp189:
	.cfi_def_cfa_offset 48
.Ltmp190:
	.cfi_offset %rbx, -16
	movq	%rdi, 24(%rsp)
	movl	$16, %edi
	callq	malloc
	movq	%rax, %rbx
	movq	%rbx, %rdi
	callq	_Pshadow_Ptest_CTest_Mcreate
	movq	%rbx, 16(%rsp)
	cmpq	$0, _Pshadow_Pio_CConsole_Minstance(%rip)
	jne	.LBB46_2
# BB#1:                                 # %_label2
	movl	$8, %edi
	callq	malloc
	movq	%rax, %rdi
	callq	_Pshadow_Pio_CConsole_Mcreate
	movq	%rax, _Pshadow_Pio_CConsole_Minstance(%rip)
.LBB46_2:                               # %_label3
	cmpq	$0, 16(%rsp)
	je	.LBB46_5
# BB#3:                                 # %_label5
	movq	16(%rsp), %rax
	cmpq	$0, 8(%rax)
	je	.LBB46_5
# BB#4:                                 # %_label4
	movq	_Pshadow_Pio_CConsole_Minstance(%rip), %rdi
	movq	(%rdi), %rcx
	movq	8(%rax), %rsi
	callq	*184(%rcx)
	jmp	.LBB46_8
.LBB46_5:                               # %_label0
	cmpq	$0, _Pshadow_Pio_CConsole_Minstance(%rip)
	jne	.LBB46_7
# BB#6:                                 # %_label6
	movl	$8, %edi
	callq	malloc
	movq	%rax, %rdi
	callq	_Pshadow_Pio_CConsole_Mcreate
	movq	%rax, _Pshadow_Pio_CConsole_Minstance(%rip)
.LBB46_7:                               # %_label7
	movq	_Pshadow_Pio_CConsole_Minstance(%rip), %rdi
	movq	(%rdi), %rax
	movl	$1, %esi
	callq	*152(%rax)
.LBB46_8:                               # %_label1
	cmpq	$0, _Pshadow_Pio_CConsole_Minstance(%rip)
	jne	.LBB46_10
# BB#9:                                 # %_label8
	movl	$8, %edi
	callq	malloc
	movq	%rax, %rdi
	callq	_Pshadow_Pio_CConsole_Mcreate
	movq	%rax, _Pshadow_Pio_CConsole_Minstance(%rip)
.LBB46_10:                              # %_label9
	movq	_Pshadow_Pio_CConsole_Minstance(%rip), %rax
	movq	%rax, 8(%rsp)
	cmpq	$0, 16(%rsp)
	je	.LBB46_12
# BB#11:                                # %_label12
	movq	16(%rsp), %rax
	movq	$0, 8(%rax)
	jmp	.LBB46_13
.LBB46_12:                              # %_label10
	movq	8(%rsp), %rdi
	movq	(%rdi), %rax
	movl	$2, %esi
	callq	*152(%rax)
.LBB46_13:                              # %_label11
	cmpq	$0, 16(%rsp)
	je	.LBB46_16
# BB#14:                                # %_label16
	movq	16(%rsp), %rax
	cmpq	$0, 8(%rax)
	je	.LBB46_16
# BB#15:                                # %_label15
	movq	8(%rsp), %rdi
	movq	(%rdi), %rcx
	movq	8(%rax), %rsi
	callq	*184(%rcx)
	jmp	.LBB46_17
.LBB46_16:                              # %_label13
	movq	8(%rsp), %rdi
	movq	(%rdi), %rax
	movl	$3, %esi
	callq	*152(%rax)
.LBB46_17:                              # %_label14
	movq	8(%rsp), %rdi
	movq	(%rdi), %rax
	callq	*192(%rax)
	addq	$32, %rsp
	popq	%rbx
	ret
.Ltmp191:
	.size	_Pshadow_Ptest_CTest_Mmain, .Ltmp191-_Pshadow_Ptest_CTest_Mmain
	.cfi_endproc

	.type	_Pshadow_Pstandard_CArray_Mclass,@object # @_Pshadow_Pstandard_CArray_Mclass
	.data
	.globl	_Pshadow_Pstandard_CArray_Mclass
	.align	16
_Pshadow_Pstandard_CArray_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Pstandard_CArray_Mdims
	.quad	_Pshadow_Pstandard_CArray_Mindex_Rint
	.quad	_Pshadow_Pstandard_CArray_Mindex_Rint_RT
	.quad	_Pshadow_Pstandard_CArray_Mlength
	.quad	_Pshadow_Pstandard_CArray_Mlengths
	.size	_Pshadow_Pstandard_CArray_Mclass, 136

	.type	.L_array0,@object       # @_array0
	.section	.rodata,"a",@progbits
	.align	16
.L_array0:
	.ascii	 "shadow.standard@Array<T>"
	.size	.L_array0, 24

	.type	.L_string0,@object      # @_string0
	.align	16
.L_string0:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0
	.long	24                      # 0x18
	.zero	4
	.size	.L_string0, 24

	.type	_Pshadow_Pstandard_CClass_Mclass,@object # @_Pshadow_Pstandard_CClass_Mclass
	.data
	.globl	_Pshadow_Pstandard_CClass_Mclass
	.align	16
_Pshadow_Pstandard_CClass_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string08
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CClass_MtoString
	.quad	_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass
	.quad	_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface
	.quad	_Pshadow_Pstandard_CClass_Mparent
	.size	_Pshadow_Pstandard_CClass_Mclass, 120

	.type	.L_array07,@object      # @_array07
	.section	.rodata,"a",@progbits
	.align	16
.L_array07:
	.ascii	 "shadow.standard@Class"
	.size	.L_array07, 21

	.type	.L_string08,@object     # @_string08
	.align	16
.L_string08:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array07
	.long	21                      # 0x15
	.zero	4
	.size	.L_string08, 24

	.type	.L_array1,@object       # @_array1
.L_array1:
	.size	.L_array1, 0

	.type	.L_string1,@object      # @_string1
	.align	16
.L_string1:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array1
	.zero	4
	.zero	4
	.size	.L_string1, 24

	.type	_Pshadow_Pstandard_CInterface_Mclass,@object # @_Pshadow_Pstandard_CInterface_Mclass
	.data
	.globl	_Pshadow_Pstandard_CInterface_Mclass
	.align	16
_Pshadow_Pstandard_CInterface_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string022
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CInterface_MtoString
	.quad	_Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface
	.size	_Pshadow_Pstandard_CInterface_Mclass, 104

	.type	.L_array021,@object     # @_array021
	.section	.rodata,"a",@progbits
	.align	16
.L_array021:
	.ascii	 "shadow.standard@Interface"
	.size	.L_array021, 25

	.type	.L_string022,@object    # @_string022
	.align	16
.L_string022:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array021
	.long	25                      # 0x19
	.zero	4
	.size	.L_string022, 24

	.type	.L_array123,@object     # @_array123
.L_array123:
	.size	.L_array123, 0

	.type	.L_string124,@object    # @_string124
	.align	16
.L_string124:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array123
	.zero	4
	.zero	4
	.size	.L_string124, 24

	.type	_Pshadow_Pstandard_CObject_Mclass,@object # @_Pshadow_Pstandard_CObject_Mclass
	.data
	.globl	_Pshadow_Pstandard_CObject_Mclass
	.align	16
_Pshadow_Pstandard_CObject_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string034
	.quad	0
	.long	1                       # 0x1
	.long	1                       # 0x1
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.size	_Pshadow_Pstandard_CObject_Mclass, 96

	.type	.L_array033,@object     # @_array033
	.section	.rodata,"a",@progbits
	.align	16
.L_array033:
	.ascii	 "shadow.standard@Object"
	.size	.L_array033, 22

	.type	.L_string034,@object    # @_string034
	.align	16
.L_string034:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array033
	.long	22                      # 0x16
	.zero	4
	.size	.L_string034, 24

	.type	_Pshadow_Pstandard_CString_Mclass,@object # @_Pshadow_Pstandard_CString_Mclass
	.data
	.globl	_Pshadow_Pstandard_CString_Mclass
	.align	16
_Pshadow_Pstandard_CString_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string052
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CString_MtoString
	.quad	_Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString
	.quad	_Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString
	.quad	_Pshadow_Pstandard_CString_MgetByte_Rint
	.quad	_Pshadow_Pstandard_CString_Mlength
	.size	_Pshadow_Pstandard_CString_Mclass, 128

	.type	.L_array051,@object     # @_array051
	.section	.rodata,"a",@progbits
	.align	16
.L_array051:
	.ascii	 "shadow.standard@String"
	.size	.L_array051, 22

	.type	.L_string052,@object    # @_string052
	.align	16
.L_string052:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array051
	.long	22                      # 0x16
	.zero	4
	.size	.L_string052, 24

	.type	_Pshadow_Pio_CConsole_Mclass,@object # @_Pshadow_Pio_CConsole_Mclass
	.data
	.globl	_Pshadow_Pio_CConsole_Mclass
	.align	16
_Pshadow_Pio_CConsole_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string066
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Pio_CConsole_Mprint_Rboolean
	.quad	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pio_CConsole_Mprint_Rint
	.quad	_Pshadow_Pio_CConsole_Mprint_Rcode
	.quad	_Pshadow_Pio_CConsole_Mprint_Rlong
	.quad	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString
	.quad	_Pshadow_Pio_CConsole_MprintLine_Rboolean
	.quad	_Pshadow_Pio_CConsole_MprintLine_Rint
	.quad	_Pshadow_Pio_CConsole_MprintLine_Rcode
	.quad	_Pshadow_Pio_CConsole_MprintLine_Rlong
	.quad	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString
	.quad	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pio_CConsole_MprintLine
	.size	_Pshadow_Pio_CConsole_Mclass, 200

	.type	_Pshadow_Pio_CConsole_Minstance,@object # @_Pshadow_Pio_CConsole_Minstance
	.bss
	.globl	_Pshadow_Pio_CConsole_Minstance
	.align	8
_Pshadow_Pio_CConsole_Minstance:
	.quad	0
	.size	_Pshadow_Pio_CConsole_Minstance, 8

	.type	.L_array065,@object     # @_array065
	.section	.rodata,"a",@progbits
	.align	16
.L_array065:
	.ascii	 "shadow.io@Console"
	.size	.L_array065, 17

	.type	.L_string066,@object    # @_string066
	.align	16
.L_string066:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array065
	.long	17                      # 0x11
	.zero	4
	.size	.L_string066, 24

	.type	.L_array167,@object     # @_array167
	.section	.rodata.cst4,"aM",@progbits,4
.L_array167:
	.ascii	 "true"
	.size	.L_array167, 4

	.type	.L_string168,@object    # @_string168
	.section	.rodata,"a",@progbits
	.align	16
.L_string168:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array167
	.long	4                       # 0x4
	.zero	4
	.size	.L_string168, 24

	.type	.L_array2,@object       # @_array2
.L_array2:
	.ascii	 "false"
	.size	.L_array2, 5

	.type	.L_string2,@object      # @_string2
	.align	16
.L_string2:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array2
	.long	5                       # 0x5
	.zero	4
	.size	.L_string2, 24

	.type	.Lstr.int,@object       # @str.int
	.section	.rodata.str1.1,"aMS",@progbits,1
.Lstr.int:
	.asciz	 "%i"
	.size	.Lstr.int, 3

	.type	.Lstr.code,@object      # @str.code
.Lstr.code:
	.asciz	 "%lc"
	.size	.Lstr.code, 4

	.type	.Lstr.long,@object      # @str.long
.Lstr.long:
	.asciz	 "%lli"
	.size	.Lstr.long, 5

	.type	_Pshadow_Ptest_CTest_Mclass,@object # @_Pshadow_Ptest_CTest_Mclass
	.data
	.globl	_Pshadow_Ptest_CTest_Mclass
	.align	16
_Pshadow_Ptest_CTest_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string078
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Ptest_CTest_Mmain
	.size	_Pshadow_Ptest_CTest_Mclass, 104

	.type	.L_array077,@object     # @_array077
	.section	.rodata.cst16,"aM",@progbits,16
.L_array077:
	.ascii	 "shadow.test@Test"
	.size	.L_array077, 16

	.type	.L_string078,@object    # @_string078
	.section	.rodata,"a",@progbits
	.align	16
.L_string078:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array077
	.long	16                      # 0x10
	.zero	4
	.size	.L_string078, 24

	.weak	stdin
	.weak	stdout
	.weak	stderr
	.weak	_imp___iob

	.section	".note.GNU-stack","",@progbits
