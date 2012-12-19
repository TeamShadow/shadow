	.file	"<stdin>"
	.text
	.globl	_Pshadow_Pstandard_CSystem_MnanoTime
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CSystem_MnanoTime,@function
_Pshadow_Pstandard_CSystem_MnanoTime:   # @_Pshadow_Pstandard_CSystem_MnanoTime
# BB#0:
	subq	$24, %rsp
	leaq	8(%rsp), %rsi
	movl	$1, %edi
	callq	clock_gettime
	testl	%eax, %eax
	je	.LBB0_1
# BB#2:
	xorl	%eax, %eax
	addq	$24, %rsp
	ret
.LBB0_1:
	imulq	$1000000000, 8(%rsp), %rax # imm = 0x3B9ACA00
	addq	16(%rsp), %rax
	addq	$24, %rsp
	ret
.Ltmp0:
	.size	_Pshadow_Pstandard_CSystem_MnanoTime, .Ltmp0-_Pshadow_Pstandard_CSystem_MnanoTime

	.globl	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString,@function
_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString: # @_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp3:
	.cfi_def_cfa_offset 16
.Ltmp4:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	movl	16(%rsi), %edx
	movq	8(%rsi), %rsi
	movl	$1, %edi
	callq	write
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp5:
	.size	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString, .Ltmp5-_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_MprintLine
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintLine,@function
_Pshadow_Pio_CConsole_MprintLine:       # @_Pshadow_Pio_CConsole_MprintLine
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp8:
	.cfi_def_cfa_offset 16
.Ltmp9:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	movl	$1, %edi
	movl	$.Lnewline, %esi
	movl	$1, %edx
	callq	write
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp10:
	.size	_Pshadow_Pio_CConsole_MprintLine, .Ltmp10-_Pshadow_Pio_CConsole_MprintLine
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CString,@function
_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CString: # @_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp13:
	.cfi_def_cfa_offset 16
.Ltmp14:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	movl	16(%rsi), %edx
	movq	8(%rsi), %rsi
	movl	$2, %edi
	callq	write
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp15:
	.size	_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CString, .Ltmp15-_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CString
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_MprintErrorLine
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintErrorLine,@function
_Pshadow_Pio_CConsole_MprintErrorLine:  # @_Pshadow_Pio_CConsole_MprintErrorLine
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp18:
	.cfi_def_cfa_offset 16
.Ltmp19:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	movl	$2, %edi
	movl	$.Lnewline, %esi
	movl	$1, %edx
	callq	write
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp20:
	.size	_Pshadow_Pio_CConsole_MprintErrorLine, .Ltmp20-_Pshadow_Pio_CConsole_MprintErrorLine
	.cfi_endproc

	.globl	main
	.align	16, 0x90
	.type	main,@function
main:                                   # @main
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp28:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp29:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp30:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp31:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp32:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp33:
	.cfi_def_cfa_offset 56
	pushq	%rax
.Ltmp34:
	.cfi_def_cfa_offset 64
.Ltmp35:
	.cfi_offset %rbx, -56
.Ltmp36:
	.cfi_offset %r12, -48
.Ltmp37:
	.cfi_offset %r13, -40
.Ltmp38:
	.cfi_offset %r14, -32
.Ltmp39:
	.cfi_offset %r15, -24
.Ltmp40:
	.cfi_offset %rbp, -16
	movq	%rsi, %r12
	movl	%edi, %r15d
	movl	_Pshadow_Pstandard_CString_Mclass+48(%rip), %esi
	decl	%r15d
	movl	%r15d, %edi
	callq	calloc
	movq	%rax, %r14
	movq	8(%r12), %r13
	testq	%r13, %r13
	je	.LBB5_3
# BB#1:                                 # %.lr.ph.preheader
	addq	$16, %r12
	movq	%r14, %rbp
	.align	16, 0x90
.LBB5_2:                                # %.lr.ph
                                        # =>This Inner Loop Header: Depth=1
	movl	_Pshadow_Pstandard_CString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rbx)
	movq	%r13, 8(%rbx)
	movq	%r13, %rdi
	callq	strlen
	movl	%eax, 16(%rbx)
	movq	%rbx, (%rbp)
	addq	$8, %rbp
	movq	(%r12), %r13
	addq	$8, %r12
	testq	%r13, %r13
	jne	.LBB5_2
.LBB5_3:                                # %._crit_edge
	movl	_Pshadow_Ptest_CSortMain_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Ptest_CSortMain_Mclass, (%rax)
	movq	$_Pshadow_Ptest_CSortMain_Mclass, (%rax)
	movq	%rax, %rdi
	movq	%r14, %rsi
	movl	%r15d, %edx
	addq	$8, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	jmp	_Pshadow_Ptest_CSortMain_Mmain_Pshadow_Pstandard_CString_A1 # TAILCALL
.Ltmp41:
	.size	main, .Ltmp41-main
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mdims
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mdims,@function
_Pshadow_Pstandard_CArray_Mdims:        # @_Pshadow_Pstandard_CArray_Mdims
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp46:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp47:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp48:
	.cfi_def_cfa_offset 32
.Ltmp49:
	.cfi_offset %rbx, -32
.Ltmp50:
	.cfi_offset %r14, -24
.Ltmp51:
	.cfi_offset %rbp, -16
	movq	8(%rdi), %r14
	movl	16(%rdi), %ebp
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbx
	movl	%ebp, (%rbx)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Cint_Mclass, 24(%rax)
	movq	%rbx, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r14, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*152(%rcx)
	movl	(%rax), %eax
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp52:
	.size	_Pshadow_Pstandard_CArray_Mdims, .Ltmp52-_Pshadow_Pstandard_CArray_Mdims
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mlength
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mlength,@function
_Pshadow_Pstandard_CArray_Mlength:      # @_Pshadow_Pstandard_CArray_Mlength
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp60:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp61:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp62:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp63:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp64:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp65:
	.cfi_def_cfa_offset 56
	pushq	%rax
.Ltmp66:
	.cfi_def_cfa_offset 64
.Ltmp67:
	.cfi_offset %rbx, -56
.Ltmp68:
	.cfi_offset %r12, -48
.Ltmp69:
	.cfi_offset %r13, -40
.Ltmp70:
	.cfi_offset %r14, -32
.Ltmp71:
	.cfi_offset %r15, -24
.Ltmp72:
	.cfi_offset %rbp, -16
	movq	%rdi, %r15
	movq	8(%r15), %rax
	movl	(%rax), %r14d
	movl	$1, %r13d
	jmp	.LBB7_2
	.align	16, 0x90
.LBB7_1:                                # %_label0
                                        #   in Loop: Header=BB7_2 Depth=1
	movslq	%r13d, %rax
	movq	8(%r15), %rcx
	imull	(%rcx,%rax,4), %r14d
	incl	%r13d
.LBB7_2:                                # %_label1
                                        # =>This Inner Loop Header: Depth=1
	movq	8(%r15), %r12
	movl	16(%r15), %ebx
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbp
	movl	%ebx, (%rbp)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Cint_Mclass, 24(%rax)
	movq	%rbp, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r12, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*152(%rcx)
	cmpl	(%rax), %r13d
	jl	.LBB7_1
# BB#3:                                 # %_label2
	movl	%r14d, %eax
	addq	$8, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp73:
	.size	_Pshadow_Pstandard_CArray_Mlength, .Ltmp73-_Pshadow_Pstandard_CArray_Mlength
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mlengths
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mlengths,@function
_Pshadow_Pstandard_CArray_Mlengths:     # @_Pshadow_Pstandard_CArray_Mlengths
# BB#0:
	movq	8(%rdi), %rax
	movl	16(%rdi), %edx
	ret
.Ltmp74:
	.size	_Pshadow_Pstandard_CArray_Mlengths, .Ltmp74-_Pshadow_Pstandard_CArray_Mlengths

	.globl	_Pshadow_Pstandard_CArray_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_MtoString,@function
_Pshadow_Pstandard_CArray_MtoString:    # @_Pshadow_Pstandard_CArray_MtoString
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp80:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp81:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp82:
	.cfi_def_cfa_offset 32
	pushq	%rbx
.Ltmp83:
	.cfi_def_cfa_offset 40
	pushq	%rax
.Ltmp84:
	.cfi_def_cfa_offset 48
.Ltmp85:
	.cfi_offset %rbx, -40
.Ltmp86:
	.cfi_offset %r14, -32
.Ltmp87:
	.cfi_offset %r15, -24
.Ltmp88:
	.cfi_offset %rbp, -16
	movq	%rdi, %r15
	movq	(%r15), %rax
	callq	*144(%rax)
	testl	%eax, %eax
	je	.LBB9_1
# BB#3:                                 # %_label5
	movl	_Pshadow_Pstandard_CMutableString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbp
	movq	$_Pshadow_Pstandard_CMutableString_Mclass, (%rbp)
	movq	%rbp, %rdi
	movl	$.L_string2, %esi
	callq	*_Pshadow_Pstandard_CMutableString_Mclass+136(%rip)
	movq	(%r15), %rax
	movq	%r15, %rdi
	xorl	%esi, %esi
	callq	*128(%rax)
	movq	(%rbp), %rcx
	movq	%rbp, %rdi
	movq	%rax, %rsi
	callq	*112(%rcx)
	movq	%rax, %r14
	movq	(%r15), %rax
	movq	%r15, %rdi
	callq	*144(%rax)
	movq	(%r14), %rcx
	movq	112(%rcx), %rcx
	cmpl	$2, %eax
	jl	.LBB9_6
# BB#4:
	movl	$1, %ebp
	.align	16, 0x90
.LBB9_5:                                # %_label6
                                        # =>This Inner Loop Header: Depth=1
	movq	%r14, %rdi
	movl	$.L_string3, %esi
	callq	*%rcx
	movq	%rax, %rbx
	movq	(%r15), %rax
	movq	%r15, %rdi
	movl	%ebp, %esi
	callq	*128(%rax)
	movq	(%rbx), %rcx
	movq	%rbx, %rdi
	movq	%rax, %rsi
	callq	*112(%rcx)
	movq	(%r15), %rax
	movq	%r15, %rdi
	callq	*144(%rax)
	incl	%ebp
	movq	(%r14), %rcx
	movq	112(%rcx), %rcx
	cmpl	%eax, %ebp
	jl	.LBB9_5
.LBB9_6:                                # %_label8
	movq	%r14, %rdi
	movl	$.L_string4, %esi
	callq	*%rcx
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*104(%rcx)
	jmp	.LBB9_2
.LBB9_1:                                # %_label3
	movl	$.L_string1, %eax
.LBB9_2:                                # %_label3
	addq	$8, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp89:
	.size	_Pshadow_Pstandard_CArray_MtoString, .Ltmp89-_Pshadow_Pstandard_CArray_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject,@function
_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject
# BB#0:
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rdi)
	movq	%rsi, 24(%rdi)
	movl	%ecx, 16(%rdi)
	movq	%rdx, 8(%rdi)
	movq	%r8, 32(%rdi)
	movq	%rdi, %rax
	ret
.Ltmp90:
	.size	_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject, .Ltmp90-_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject

	.globl	_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1,@function
_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1: # @_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp96:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp97:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp98:
	.cfi_def_cfa_offset 32
	pushq	%rbx
.Ltmp99:
	.cfi_def_cfa_offset 40
	pushq	%rax
.Ltmp100:
	.cfi_def_cfa_offset 48
.Ltmp101:
	.cfi_offset %rbx, -40
.Ltmp102:
	.cfi_offset %r14, -32
.Ltmp103:
	.cfi_offset %r15, -24
.Ltmp104:
	.cfi_offset %rbp, -16
	movl	%ecx, %r15d
	movq	%rdx, %r14
	movq	%rdi, %rbx
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbx)
	movq	%rsi, 24(%rbx)
	movq	$0, 32(%rbx)
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	xorl	%edi, %edi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$0, 16(%rbx)
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbp
	movl	%r15d, (%rbp)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Cint_Mclass, 24(%rax)
	movq	%rbp, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r14, 32(%rax)
	movq	%rax, %rdi
	callq	_Pshadow_Pstandard_CArray_Mcopy
	movq	8(%rax), %rcx
	movq	32(%rax), %rax
	movl	(%rcx), %ecx
	movl	%ecx, 16(%rbx)
	movq	%rax, 8(%rbx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*144(%rax)
	movq	24(%rbx), %rcx
	movl	48(%rcx), %esi
	movl	%eax, %edi
	callq	calloc
	movq	%rax, 32(%rbx)
	movq	%rbx, %rax
	addq	$8, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp105:
	.size	_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1, .Ltmp105-_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mcopy
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mcopy,@function
_Pshadow_Pstandard_CArray_Mcopy:        # @_Pshadow_Pstandard_CArray_Mcopy
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp111:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp112:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp113:
	.cfi_def_cfa_offset 32
	pushq	%rbx
.Ltmp114:
	.cfi_def_cfa_offset 40
	pushq	%rax
.Ltmp115:
	.cfi_def_cfa_offset 48
.Ltmp116:
	.cfi_offset %rbx, -40
.Ltmp117:
	.cfi_offset %r14, -32
.Ltmp118:
	.cfi_offset %r15, -24
.Ltmp119:
	.cfi_offset %rbp, -16
	movq	%rdi, %rbx
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %r14
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%r14)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*144(%rax)
	movq	24(%rbx), %rcx
	movl	48(%rcx), %esi
	movl	%eax, %edi
	callq	calloc
	movq	%rax, %r15
	movq	(%rbx), %rax
	movq	24(%rbx), %rbp
	movq	%rbx, %rdi
	callq	*152(%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%r14)
	movq	%rbp, 24(%r14)
	movl	%edx, 16(%r14)
	movq	%rax, 8(%r14)
	movq	%r15, 32(%r14)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*144(%rax)
	testl	%eax, %eax
	jle	.LBB12_3
# BB#1:
	xorl	%ebp, %ebp
	.align	16, 0x90
.LBB12_2:                               # %_label9
                                        # =>This Inner Loop Header: Depth=1
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	movl	%ebp, %esi
	callq	*128(%rax)
	movq	(%r14), %rcx
	movq	%r14, %rdi
	movl	%ebp, %esi
	movq	%rax, %rdx
	callq	*136(%rcx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*144(%rax)
	incl	%ebp
	cmpl	%eax, %ebp
	jl	.LBB12_2
.LBB12_3:                               # %_label11
	movq	%r14, %rax
	addq	$8, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp120:
	.size	_Pshadow_Pstandard_CArray_Mcopy, .Ltmp120-_Pshadow_Pstandard_CArray_Mcopy
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_Cint
# BB#0:
	movq	24(%rdi), %rax
	movl	48(%rax), %eax
	movl	%esi, %edi
	movl	%eax, %esi
	jmp	calloc                  # TAILCALL
.Ltmp121:
	.size	_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_Cint, .Ltmp121-_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint
# BB#0:
	pushq	%r15
	pushq	%r14
	pushq	%r12
	pushq	%rbx
	pushq	%rax
	movq	24(%rdi), %rbx
	movl	48(%rbx), %r14d
	imull	%r14d, %esi
	movslq	%esi, %r15
	addq	32(%rdi), %r15
	testb	$1, 40(%rbx)
	je	.LBB14_1
# BB#3:
	movl	44(%rbx), %edi
	callq	malloc
	movq	%rax, %r12
	movq	%rbx, (%r12)
	leaq	8(%r12), %rdi
	movq	%r15, %rsi
	movq	%r14, %rdx
	callq	memcpy
	movq	%r12, %rax
	jmp	.LBB14_2
.LBB14_1:
	movq	(%r15), %rax
.LBB14_2:
	addq	$8, %rsp
	popq	%rbx
	popq	%r12
	popq	%r14
	popq	%r15
	ret
.Ltmp122:
	.size	_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint, .Ltmp122-_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_CT
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_CT,@function
_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_CT: # @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_CT
# BB#0:
	pushq	%rax
	movq	%rdi, %rcx
	movq	24(%rcx), %rax
	movl	48(%rax), %r8d
	imull	%r8d, %esi
	movslq	%esi, %rdi
	addq	32(%rcx), %rdi
	testb	$1, 40(%rax)
	je	.LBB15_1
# BB#2:
	addq	$8, %rdx
	movq	%rdx, %rsi
	movq	%r8, %rdx
	callq	memcpy
	popq	%rax
	ret
.LBB15_1:
	movq	%rdx, (%rdi)
	popq	%rax
	ret
.Ltmp123:
	.size	_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_CT, .Ltmp123-_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_CT

	.globl	_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
# BB#0:
	pushq	%rbp
	pushq	%r15
	pushq	%r14
	pushq	%r13
	pushq	%r12
	pushq	%rbx
	pushq	%rax
	movl	%edx, %ebp
	movl	%esi, %r14d
	movq	%rdi, %r12
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbx)
	movq	24(%r12), %rax
	movq	%rax, (%rsp)            # 8-byte Spill
	movl	16(%r12), %r15d
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	%r15d, %edi
	callq	calloc
	movq	%rax, %r13
	subl	%r14d, %ebp
	movl	%ebp, (%r13)
	leaq	4(%r13), %rdi
	movq	8(%r12), %rsi
	leal	-1(%r15), %edx
	addq	$4, %rsi
	callq	memcpy
	movq	(%rsp), %rcx            # 8-byte Reload
	imull	48(%rcx), %r14d
	movq	32(%r12), %rax
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbx)
	movq	%rcx, 24(%rbx)
	movl	%r15d, 16(%rbx)
	movq	%r13, 8(%rbx)
	movslq	%r14d, %rcx
	addq	%rax, %rcx
	movq	%rcx, 32(%rbx)
	movq	%rbx, %rax
	addq	$8, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp124:
	.size	_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint, .Ltmp124-_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cboolean_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cboolean_Mcreate,@function
_Pshadow_Pstandard_Cboolean_Mcreate:    # @_Pshadow_Pstandard_Cboolean_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cboolean_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp125:
	.size	_Pshadow_Pstandard_Cboolean_Mcreate, .Ltmp125-_Pshadow_Pstandard_Cboolean_Mcreate

	.globl	_Pshadow_Pstandard_Cbyte_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cbyte_Mcreate,@function
_Pshadow_Pstandard_Cbyte_Mcreate:       # @_Pshadow_Pstandard_Cbyte_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cbyte_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp126:
	.size	_Pshadow_Pstandard_Cbyte_Mcreate, .Ltmp126-_Pshadow_Pstandard_Cbyte_Mcreate

	.globl	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass,@function
_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass: # @_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass
# BB#0:
	.align	16, 0x90
.LBB19_1:                               # %tailrecurse
                                        # =>This Inner Loop Header: Depth=1
	movb	$1, %al
	cmpq	%rsi, %rdi
	je	.LBB19_3
# BB#2:                                 # %_label2
                                        #   in Loop: Header=BB19_1 Depth=1
	movq	32(%rdi), %rdi
	xorb	%al, %al
	testq	%rdi, %rdi
	jne	.LBB19_1
.LBB19_3:                               # %_label0
	ret
.Ltmp127:
	.size	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass, .Ltmp127-_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass

	.globl	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CInterface
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CInterface,@function
_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CInterface: # @_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CInterface
# BB#0:
	pushq	%rbp
	pushq	%r15
	pushq	%r14
	pushq	%rbx
	pushq	%rax
	movq	%rsi, %r14
	movl	16(%rdi), %r15d
	testl	%r15d, %r15d
	jle	.LBB20_5
# BB#1:
	movq	8(%rdi), %rbx
	xorl	%ebp, %ebp
	.align	16, 0x90
.LBB20_2:                               # %_label6
                                        # =>This Inner Loop Header: Depth=1
	movq	(%rbx,%rbp,8), %rdi
	testq	%rdi, %rdi
	je	.LBB20_4
# BB#3:                                 # %_label14
                                        #   in Loop: Header=BB20_2 Depth=1
	movq	%r14, %rsi
	callq	_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface
	movb	%al, %cl
	movb	$1, %al
	testb	$1, %cl
	jne	.LBB20_6
.LBB20_4:                               # %_label10
                                        #   in Loop: Header=BB20_2 Depth=1
	incq	%rbp
	cmpl	%r15d, %ebp
	jl	.LBB20_2
.LBB20_5:
	xorb	%al, %al
.LBB20_6:                               # %_label11
	addq	$8, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp128:
	.size	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CInterface, .Ltmp128-_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CInterface

	.globl	_Pshadow_Pstandard_CClass_Mparent
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_Mparent,@function
_Pshadow_Pstandard_CClass_Mparent:      # @_Pshadow_Pstandard_CClass_Mparent
# BB#0:
	movq	32(%rdi), %rax
	ret
.Ltmp129:
	.size	_Pshadow_Pstandard_CClass_Mparent, .Ltmp129-_Pshadow_Pstandard_CClass_Mparent

	.globl	_Pshadow_Pstandard_CClass_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_MtoString,@function
_Pshadow_Pstandard_CClass_MtoString:    # @_Pshadow_Pstandard_CClass_MtoString
# BB#0:
	movq	24(%rdi), %rax
	ret
.Ltmp130:
	.size	_Pshadow_Pstandard_CClass_MtoString, .Ltmp130-_Pshadow_Pstandard_CClass_MtoString

	.globl	_Pshadow_Pstandard_CClass_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_Mcreate,@function
_Pshadow_Pstandard_CClass_Mcreate:      # @_Pshadow_Pstandard_CClass_Mcreate
# BB#0:
	pushq	%rbx
	movq	%rdi, %rbx
	movq	$_Pshadow_Pstandard_CClass_Mclass, (%rbx)
	movl	$0, 40(%rbx)
	movl	$0, 48(%rbx)
	movq	$.L_string128, 24(%rbx)
	movl	_Pshadow_Pstandard_CInterface_Mclass+48(%rip), %esi
	xorl	%edi, %edi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$0, 16(%rbx)
	movq	$0, 32(%rbx)
	movl	$0, 44(%rbx)
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp131:
	.size	_Pshadow_Pstandard_CClass_Mcreate, .Ltmp131-_Pshadow_Pstandard_CClass_Mcreate

	.globl	_Pshadow_Pstandard_CClass_Mallocate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_Mallocate,@function
_Pshadow_Pstandard_CClass_Mallocate:    # @_Pshadow_Pstandard_CClass_Mallocate
# BB#0:
	pushq	%rbx
	movq	%rdi, %rbx
	movl	44(%rbx), %edi
	callq	malloc
	movq	%rbx, (%rax)
	popq	%rbx
	ret
.Ltmp132:
	.size	_Pshadow_Pstandard_CClass_Mallocate, .Ltmp132-_Pshadow_Pstandard_CClass_Mallocate

	.globl	_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint
# BB#0:
	movl	48(%rdi), %eax
	movl	%esi, %edi
	movl	%eax, %esi
	jmp	calloc                  # TAILCALL
.Ltmp133:
	.size	_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint, .Ltmp133-_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Ccode_MtoUpperCase
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Ccode_MtoUpperCase,@function
_Pshadow_Pstandard_Ccode_MtoUpperCase:  # @_Pshadow_Pstandard_Ccode_MtoUpperCase
# BB#0:
	movl	8(%rdi), %eax
	leal	-97(%rax), %ecx
	cmpl	$25, %ecx
	ja	.LBB26_2
# BB#1:                                 # %_label0
	addl	$-32, %eax
.LBB26_2:                               # %_label2
	ret
.Ltmp134:
	.size	_Pshadow_Pstandard_Ccode_MtoUpperCase, .Ltmp134-_Pshadow_Pstandard_Ccode_MtoUpperCase

	.globl	_Pshadow_Pstandard_Ccode_MtoLowerCase
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Ccode_MtoLowerCase,@function
_Pshadow_Pstandard_Ccode_MtoLowerCase:  # @_Pshadow_Pstandard_Ccode_MtoLowerCase
# BB#0:
	movl	8(%rdi), %eax
	leal	-65(%rax), %ecx
	cmpl	$25, %ecx
	ja	.LBB27_2
# BB#1:                                 # %_label5
	addl	$32, %eax
.LBB27_2:                               # %_label7
	ret
.Ltmp135:
	.size	_Pshadow_Pstandard_Ccode_MtoLowerCase, .Ltmp135-_Pshadow_Pstandard_Ccode_MtoLowerCase

	.globl	_Pshadow_Pstandard_Ccode_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Ccode_MtoString,@function
_Pshadow_Pstandard_Ccode_MtoString:     # @_Pshadow_Pstandard_Ccode_MtoString
	.cfi_startproc
# BB#0:
	pushq	%r14
.Ltmp139:
	.cfi_def_cfa_offset 16
	pushq	%rbx
.Ltmp140:
	.cfi_def_cfa_offset 24
	pushq	%rax
.Ltmp141:
	.cfi_def_cfa_offset 32
.Ltmp142:
	.cfi_offset %rbx, -24
.Ltmp143:
	.cfi_offset %r14, -16
	movl	8(%rdi), %ebx
	movl	_Pshadow_Pstandard_Ccode_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %r14
	movl	%ebx, (%r14)
	movl	_Pshadow_Pstandard_CString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rbx)
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movl	$1, %edx
	callq	*_Pshadow_Pstandard_CString_Mclass+144(%rip)
	movq	%rbx, %rax
	addq	$8, %rsp
	popq	%rbx
	popq	%r14
	ret
.Ltmp144:
	.size	_Pshadow_Pstandard_Ccode_MtoString, .Ltmp144-_Pshadow_Pstandard_Ccode_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_Ccode_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Ccode_Mcreate,@function
_Pshadow_Pstandard_Ccode_Mcreate:       # @_Pshadow_Pstandard_Ccode_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Ccode_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp145:
	.size	_Pshadow_Pstandard_Ccode_Mcreate, .Ltmp145-_Pshadow_Pstandard_Ccode_Mcreate

	.globl	_Pshadow_Pstandard_Cdouble_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_Mcreate,@function
_Pshadow_Pstandard_Cdouble_Mcreate:     # @_Pshadow_Pstandard_Cdouble_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cdouble_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp146:
	.size	_Pshadow_Pstandard_Cdouble_Mcreate, .Ltmp146-_Pshadow_Pstandard_Cdouble_Mcreate

	.globl	_Pshadow_Pstandard_Cdouble_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_MtoString,@function
_Pshadow_Pstandard_Cdouble_MtoString:   # @_Pshadow_Pstandard_Cdouble_MtoString
# BB#0:
	pushq	%rbp
	pushq	%r15
	pushq	%r14
	pushq	%r12
	pushq	%rbx
	subq	$48, %rsp
	vmovsd	8(%rdi), %xmm0
	leaq	8(%rsp), %rbx
	movb	$1, %r12b
	movq	%rbx, %rdi
	movl	$.Lformat, %esi
	movb	$1, %al
	callq	sprintf
	movl	_Pshadow_Pstandard_CString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %r14
	movq	$_Pshadow_Pstandard_CString_Mclass, (%r14)
	movq	%rbx, %rdi
	callq	strlen
	movl	%eax, %r15d
	movq	$_Pshadow_Pstandard_CString_Mclass, (%r14)
	movl	%r15d, 40(%rsp)
	movq	%rbx, 32(%rsp)
	movb	$1, 24(%r14)
	movl	40(%rsp), %ebp
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	%ebp, %edi
	callq	calloc
	testl	%r15d, %r15d
	movl	%ebp, 16(%r14)
	movq	%rax, 8(%r14)
	jle	.LBB31_4
# BB#1:                                 # %_label16.lr.ph.i
	movq	8(%r14), %rax
	xorl	%edx, %edx
	xorb	%cl, %cl
	.align	16, 0x90
.LBB31_2:                               # %_label16.i
                                        # =>This Inner Loop Header: Depth=1
	movslq	%edx, %rdx
	movb	(%rbx,%rdx), %bl
	movb	%bl, (%rax,%rdx)
	movq	32(%rsp), %rbx
	orb	(%rbx,%rdx), %cl
	incl	%edx
	cmpl	40(%rsp), %edx
	jl	.LBB31_2
# BB#3:                                 # %_label17._label18_crit_edge.i
	testb	%cl, %cl
	setns	%r12b
.LBB31_4:                               # %_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1.exit
	movb	%r12b, 24(%r14)
	movq	%r14, %rax
	addq	$48, %rsp
	popq	%rbx
	popq	%r12
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp147:
	.size	_Pshadow_Pstandard_Cdouble_MtoString, .Ltmp147-_Pshadow_Pstandard_Cdouble_MtoString

	.globl	_Pshadow_Pstandard_Cfloat_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cfloat_Mcreate,@function
_Pshadow_Pstandard_Cfloat_Mcreate:      # @_Pshadow_Pstandard_Cfloat_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cfloat_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp148:
	.size	_Pshadow_Pstandard_Cfloat_Mcreate, .Ltmp148-_Pshadow_Pstandard_Cfloat_Mcreate

	.globl	_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface,@function
_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface: # @_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface
# BB#0:
	pushq	%rbp
	pushq	%r15
	pushq	%r14
	pushq	%r12
	pushq	%rbx
	movq	%rsi, %r15
	movb	$1, %r14b
	cmpq	%r15, %rdi
	je	.LBB33_3
# BB#1:                                 # %_label4.preheader
	movl	16(%rdi), %r12d
	testl	%r12d, %r12d
	jle	.LBB33_2
# BB#4:
	movq	8(%rdi), %rbx
	xorl	%ebp, %ebp
	.align	16, 0x90
.LBB33_5:                               # %_label3
                                        # =>This Inner Loop Header: Depth=1
	movq	(%rbx,%rbp,8), %rdi
	testq	%rdi, %rdi
	je	.LBB33_7
# BB#6:                                 # %_label11
                                        #   in Loop: Header=BB33_5 Depth=1
	movq	%r15, %rsi
	callq	_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface
	testb	$1, %al
	jne	.LBB33_3
.LBB33_7:                               # %_label7
                                        #   in Loop: Header=BB33_5 Depth=1
	incq	%rbp
	cmpl	%r12d, %ebp
	jl	.LBB33_5
.LBB33_2:
	xorb	%r14b, %r14b
.LBB33_3:                               # %_label0
	movb	%r14b, %al
	popq	%rbx
	popq	%r12
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp149:
	.size	_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface, .Ltmp149-_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface

	.globl	_Pshadow_Pstandard_CInterface_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CInterface_MtoString,@function
_Pshadow_Pstandard_CInterface_MtoString: # @_Pshadow_Pstandard_CInterface_MtoString
# BB#0:
	movq	24(%rdi), %rax
	ret
.Ltmp150:
	.size	_Pshadow_Pstandard_CInterface_MtoString, .Ltmp150-_Pshadow_Pstandard_CInterface_MtoString

	.globl	_Pshadow_Pstandard_CInterface_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CInterface_Mcreate,@function
_Pshadow_Pstandard_CInterface_Mcreate:  # @_Pshadow_Pstandard_CInterface_Mcreate
# BB#0:
	pushq	%rbx
	movq	%rdi, %rbx
	movq	$_Pshadow_Pstandard_CInterface_Mclass, (%rbx)
	movq	$.L_string172, 24(%rbx)
	movl	_Pshadow_Pstandard_CInterface_Mclass+48(%rip), %esi
	xorl	%edi, %edi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$0, 16(%rbx)
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp151:
	.size	_Pshadow_Pstandard_CInterface_Mcreate, .Ltmp151-_Pshadow_Pstandard_CInterface_Mcreate

	.globl	_Pshadow_Pstandard_Cint_Mmin_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Mmin_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_Cint_Mmin_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mmin_Pshadow_Pstandard_Cint
# BB#0:                                 # %_label2
	movl	8(%rdi), %eax
	cmpl	%esi, %eax
	cmovgl	%esi, %eax
	ret
.Ltmp152:
	.size	_Pshadow_Pstandard_Cint_Mmin_Pshadow_Pstandard_Cint, .Ltmp152-_Pshadow_Pstandard_Cint_Mmin_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint
# BB#0:                                 # %_label5
	movl	8(%rdi), %eax
	cmpl	%esi, %eax
	cmovll	%esi, %eax
	ret
.Ltmp153:
	.size	_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint, .Ltmp153-_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cint_Mabs
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Mabs,@function
_Pshadow_Pstandard_Cint_Mabs:           # @_Pshadow_Pstandard_Cint_Mabs
# BB#0:                                 # %_label8
	movl	8(%rdi), %ecx
	movl	%ecx, %eax
	negl	%eax
	cmovll	%ecx, %eax
	ret
.Ltmp154:
	.size	_Pshadow_Pstandard_Cint_Mabs, .Ltmp154-_Pshadow_Pstandard_Cint_Mabs

	.globl	_Pshadow_Pstandard_Cint_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_MtoString,@function
_Pshadow_Pstandard_Cint_MtoString:      # @_Pshadow_Pstandard_Cint_MtoString
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp157:
	.cfi_def_cfa_offset 16
.Ltmp158:
	.cfi_offset %rbx, -16
	movl	8(%rdi), %ebx
	movl	_Pshadow_Pstandard_Cint_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Cint_Mclass, (%rax)
	movl	%ebx, 8(%rax)
	movq	%rax, %rdi
	movl	$10, %esi
	popq	%rbx
	jmp	_Pshadow_Pstandard_Cint_MtoString_Pshadow_Pstandard_Cuint # TAILCALL
.Ltmp159:
	.size	_Pshadow_Pstandard_Cint_MtoString, .Ltmp159-_Pshadow_Pstandard_Cint_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_Cint_MtoString_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_MtoString_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cint_MtoString_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cint_MtoString_Pshadow_Pstandard_Cuint
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp166:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp167:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp168:
	.cfi_def_cfa_offset 32
	pushq	%r12
.Ltmp169:
	.cfi_def_cfa_offset 40
	pushq	%rbx
.Ltmp170:
	.cfi_def_cfa_offset 48
.Ltmp171:
	.cfi_offset %rbx, -48
.Ltmp172:
	.cfi_offset %r12, -40
.Ltmp173:
	.cfi_offset %r14, -32
.Ltmp174:
	.cfi_offset %r15, -24
.Ltmp175:
	.cfi_offset %rbp, -16
	movl	%esi, %r14d
	movl	8(%rdi), %r15d
	movl	_Pshadow_Pstandard_CMutableString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_Pshadow_Pstandard_CMutableString_Mclass, (%rbx)
	movq	%rbx, %rdi
	callq	*_Pshadow_Pstandard_CMutableString_Mclass+64(%rip)
	movl	%r15d, %ebp
	negl	%ebp
	cmovll	%r15d, %ebp
	movl	$48, %r12d
	.align	16, 0x90
.LBB40_1:                               # %_label9.i
                                        # =>This Inner Loop Header: Depth=1
	movl	_Pshadow_Pstandard_Ccode_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rcx
	movq	$_Pshadow_Pstandard_Ccode_Mclass, (%rcx)
	movl	%ebp, %eax
	xorl	%edx, %edx
	divl	%r14d
	movl	%eax, %ebp
	cmpl	$10, %edx
	movl	$87, %eax
	cmovbl	%r12d, %eax
	addl	%edx, %eax
	movl	%eax, 8(%rcx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	movq	%rcx, %rsi
	callq	*112(%rax)
	testl	%ebp, %ebp
	jne	.LBB40_1
# BB#2:                                 # %_Pshadow_Pstandard_Cuint_MtoString_Pshadow_Pstandard_Cuint.exit
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*160(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*104(%rcx)
	movq	%rax, %rbx
	testl	%r15d, %r15d
	jns	.LBB40_6
# BB#3:                                 # %_label10
	movl	_Pshadow_Pstandard_Ccode_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Ccode_Mclass, (%rax)
	movl	$45, 8(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*104(%rcx)
	movq	%rax, %rbp
	movl	$.L_string1370, %eax
	testq	%rbx, %rbx
	je	.LBB40_5
# BB#4:                                 # %_label13
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*104(%rax)
.LBB40_5:                               # %_label14
	movq	(%rbp), %rcx
	movq	%rbp, %rdi
	movq	%rax, %rsi
	callq	*128(%rcx)
	movq	%rax, %rbx
.LBB40_6:                               # %_label11
	movq	%rbx, %rax
	popq	%rbx
	popq	%r12
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp176:
	.size	_Pshadow_Pstandard_Cint_MtoString_Pshadow_Pstandard_Cuint, .Ltmp176-_Pshadow_Pstandard_Cint_MtoString_Pshadow_Pstandard_Cuint
	.cfi_endproc

	.globl	_Pshadow_Pstandard_Cint_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Mcreate,@function
_Pshadow_Pstandard_Cint_Mcreate:        # @_Pshadow_Pstandard_Cint_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cint_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp177:
	.size	_Pshadow_Pstandard_Cint_Mcreate, .Ltmp177-_Pshadow_Pstandard_Cint_Mcreate

	.globl	_Pshadow_Pstandard_Clong_Mmin_Pshadow_Pstandard_Clong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Mmin_Pshadow_Pstandard_Clong,@function
_Pshadow_Pstandard_Clong_Mmin_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Mmin_Pshadow_Pstandard_Clong
# BB#0:                                 # %_label2
	movq	8(%rdi), %rax
	cmpq	%rsi, %rax
	cmovgq	%rsi, %rax
	ret
.Ltmp178:
	.size	_Pshadow_Pstandard_Clong_Mmin_Pshadow_Pstandard_Clong, .Ltmp178-_Pshadow_Pstandard_Clong_Mmin_Pshadow_Pstandard_Clong

	.globl	_Pshadow_Pstandard_Clong_Mmax_Pshadow_Pstandard_Clong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Mmax_Pshadow_Pstandard_Clong,@function
_Pshadow_Pstandard_Clong_Mmax_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Mmax_Pshadow_Pstandard_Clong
# BB#0:                                 # %_label5
	movq	8(%rdi), %rax
	cmpq	%rsi, %rax
	cmovlq	%rsi, %rax
	ret
.Ltmp179:
	.size	_Pshadow_Pstandard_Clong_Mmax_Pshadow_Pstandard_Clong, .Ltmp179-_Pshadow_Pstandard_Clong_Mmax_Pshadow_Pstandard_Clong

	.globl	_Pshadow_Pstandard_Clong_Mabs
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Mabs,@function
_Pshadow_Pstandard_Clong_Mabs:          # @_Pshadow_Pstandard_Clong_Mabs
# BB#0:                                 # %_label8
	movq	8(%rdi), %rcx
	movq	%rcx, %rax
	negq	%rax
	cmovlq	%rcx, %rax
	ret
.Ltmp180:
	.size	_Pshadow_Pstandard_Clong_Mabs, .Ltmp180-_Pshadow_Pstandard_Clong_Mabs

	.globl	_Pshadow_Pstandard_Clong_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_MtoString,@function
_Pshadow_Pstandard_Clong_MtoString:     # @_Pshadow_Pstandard_Clong_MtoString
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp183:
	.cfi_def_cfa_offset 16
.Ltmp184:
	.cfi_offset %rbx, -16
	movq	8(%rdi), %rbx
	movl	_Pshadow_Pstandard_Clong_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Clong_Mclass, (%rax)
	movq	%rbx, 8(%rax)
	movq	%rax, %rdi
	movl	$10, %esi
	popq	%rbx
	jmp	_Pshadow_Pstandard_Clong_MtoString_Pshadow_Pstandard_Culong # TAILCALL
.Ltmp185:
	.size	_Pshadow_Pstandard_Clong_MtoString, .Ltmp185-_Pshadow_Pstandard_Clong_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_Clong_MtoString_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_MtoString_Pshadow_Pstandard_Culong,@function
_Pshadow_Pstandard_Clong_MtoString_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Clong_MtoString_Pshadow_Pstandard_Culong
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp193:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp194:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp195:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp196:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp197:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp198:
	.cfi_def_cfa_offset 56
	pushq	%rax
.Ltmp199:
	.cfi_def_cfa_offset 64
.Ltmp200:
	.cfi_offset %rbx, -56
.Ltmp201:
	.cfi_offset %r12, -48
.Ltmp202:
	.cfi_offset %r13, -40
.Ltmp203:
	.cfi_offset %r14, -32
.Ltmp204:
	.cfi_offset %r15, -24
.Ltmp205:
	.cfi_offset %rbp, -16
	movq	%rsi, %r14
	movq	8(%rdi), %r13
	movl	_Pshadow_Pstandard_CMutableString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %r15
	movq	$_Pshadow_Pstandard_CMutableString_Mclass, (%r15)
	movq	%r15, %rdi
	callq	*_Pshadow_Pstandard_CMutableString_Mclass+64(%rip)
	movq	%r13, %rbx
	negq	%rbx
	cmovlq	%r13, %rbx
	movl	$48, %ebp
	.align	16, 0x90
.LBB46_1:                               # %_label3.i
                                        # =>This Inner Loop Header: Depth=1
	movq	%rbx, %rax
	xorl	%edx, %edx
	divq	%r14
	movq	%rax, %rbx
	movq	%rdx, %r12
	movl	_Pshadow_Pstandard_Ccode_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Ccode_Mclass, (%rax)
	cmpq	$10, %r12
	movl	$87, %ecx
	cmovbq	%rbp, %rcx
	addl	%r12d, %ecx
	movl	%ecx, 8(%rax)
	movq	(%r15), %rcx
	movq	%r15, %rdi
	movq	%rax, %rsi
	callq	*112(%rcx)
	testq	%rbx, %rbx
	jne	.LBB46_1
# BB#2:                                 # %_Pshadow_Pstandard_Culong_MtoString_Pshadow_Pstandard_Culong.exit
	movq	(%r15), %rax
	movq	%r15, %rdi
	callq	*160(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*104(%rcx)
	movq	%rax, %rbx
	testq	%r13, %r13
	jns	.LBB46_6
# BB#3:                                 # %_label10
	movl	_Pshadow_Pstandard_Ccode_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Ccode_Mclass, (%rax)
	movl	$45, 8(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*104(%rcx)
	movq	%rax, %r14
	movl	$.L_string1370, %eax
	testq	%rbx, %rbx
	je	.LBB46_5
# BB#4:                                 # %_label13
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*104(%rax)
.LBB46_5:                               # %_label14
	movq	(%r14), %rcx
	movq	%r14, %rdi
	movq	%rax, %rsi
	callq	*128(%rcx)
	movq	%rax, %rbx
.LBB46_6:                               # %_label11
	movq	%rbx, %rax
	addq	$8, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp206:
	.size	_Pshadow_Pstandard_Clong_MtoString_Pshadow_Pstandard_Culong, .Ltmp206-_Pshadow_Pstandard_Clong_MtoString_Pshadow_Pstandard_Culong
	.cfi_endproc

	.globl	_Pshadow_Pstandard_Clong_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Mcreate,@function
_Pshadow_Pstandard_Clong_Mcreate:       # @_Pshadow_Pstandard_Clong_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Clong_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp207:
	.size	_Pshadow_Pstandard_Clong_Mcreate, .Ltmp207-_Pshadow_Pstandard_Clong_Mcreate

	.globl	_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject,@function
_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:                                 # %_label2
	pushq	%rbp
.Ltmp212:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp213:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp214:
	.cfi_def_cfa_offset 32
.Ltmp215:
	.cfi_offset %rbx, -32
.Ltmp216:
	.cfi_offset %r14, -24
.Ltmp217:
	.cfi_offset %rbp, -16
	movq	%rdi, %r14
	movl	$.L_string1370, %edi
	testq	%rsi, %rsi
	cmovneq	%rsi, %rdi
	movq	(%rdi), %rax
	callq	*104(%rax)
	movq	%rax, %rbx
	movq	(%rbx), %rax
	movl	24(%r14), %ebp
	movq	%rbx, %rdi
	callq	*176(%rax)
	addl	%eax, %ebp
	movq	(%r14), %rax
	movq	%r14, %rdi
	movl	%ebp, %esi
	callq	*144(%rax)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*176(%rax)
	testl	%eax, %eax
	jle	.LBB48_3
# BB#1:                                 # %_label3.lr.ph
	xorl	%ebp, %ebp
	.align	16, 0x90
.LBB48_2:                               # %_label3
                                        # =>This Inner Loop Header: Depth=1
	movslq	%ebp, %rbp
	movq	8(%rbx), %rax
	movb	(%rax,%rbp), %al
	movslq	24(%r14), %rcx
	movq	8(%r14), %rdx
	movb	%al, (%rdx,%rcx)
	incl	24(%r14)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*176(%rax)
	incl	%ebp
	cmpl	%eax, %ebp
	jl	.LBB48_2
.LBB48_3:                               # %_label5
	movq	%r14, %rax
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp218:
	.size	_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject, .Ltmp218-_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CMutableString_Mreverse
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_Mreverse,@function
_Pshadow_Pstandard_CMutableString_Mreverse: # @_Pshadow_Pstandard_CMutableString_Mreverse
# BB#0:
	movl	24(%rdi), %r9d
	leal	-1(%r9), %ecx
	testl	%ecx, %ecx
	jle	.LBB49_3
# BB#1:                                 # %_label6.preheader
	addl	$-2, %r9d
	xorl	%ecx, %ecx
	.align	16, 0x90
.LBB49_2:                               # %_label6
                                        # =>This Inner Loop Header: Depth=1
	movslq	%ecx, %rcx
	movq	8(%rdi), %rdx
	movb	(%rdx,%rcx), %r8b
	leal	1(%r9), %esi
	movslq	%esi, %rsi
	movb	(%rdx,%rsi), %al
	movb	%al, (%rdx,%rcx)
	movb	%r8b, (%rdx,%rsi)
	incl	%ecx
	cmpl	%r9d, %ecx
	leal	-1(%r9), %r9d
	jl	.LBB49_2
.LBB49_3:                               # %_label8
	movq	%rdi, %rax
	ret
.Ltmp219:
	.size	_Pshadow_Pstandard_CMutableString_Mreverse, .Ltmp219-_Pshadow_Pstandard_CMutableString_Mreverse

	.globl	_Pshadow_Pstandard_CMutableString_Mlength
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_Mlength,@function
_Pshadow_Pstandard_CMutableString_Mlength: # @_Pshadow_Pstandard_CMutableString_Mlength
# BB#0:
	movl	24(%rdi), %eax
	ret
.Ltmp220:
	.size	_Pshadow_Pstandard_CMutableString_Mlength, .Ltmp220-_Pshadow_Pstandard_CMutableString_Mlength

	.globl	_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint
# BB#0:
	pushq	%rbp
	pushq	%rbx
	subq	$24, %rsp
	movl	%esi, %ebp
	movq	%rdi, %rbx
	cmpl	%ebp, 16(%rbx)
	jge	.LBB51_6
# BB#1:                                 # %_label9
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	%ebp, %edi
	callq	calloc
	movl	%ebp, 16(%rsp)
	movq	%rax, 8(%rsp)
	movl	24(%rbx), %ecx
	cmpl	%ebp, %ecx
	cmovgl	%ebp, %ecx
	testl	%ecx, %ecx
	jle	.LBB51_5
# BB#2:                                 # %_label12.lr.ph
	xorl	%eax, %eax
	movq	8(%rsp), %rcx
	.align	16, 0x90
.LBB51_3:                               # %_label12
                                        # =>This Inner Loop Header: Depth=1
	movslq	%eax, %rax
	movq	8(%rbx), %rdx
	movb	(%rdx,%rax), %dl
	movb	%dl, (%rcx,%rax)
	movl	24(%rbx), %edx
	cmpl	%ebp, %edx
	cmovgl	%ebp, %edx
	incl	%eax
	cmpl	%edx, %eax
	jl	.LBB51_3
# BB#4:                                 # %_label13._label14_crit_edge
	movl	16(%rsp), %ebp
	movq	8(%rsp), %rax
.LBB51_5:                               # %_label14
	movq	%rax, 8(%rbx)
	movl	%ebp, 16(%rbx)
.LBB51_6:                               # %_label11
	movq	%rbx, %rax
	addq	$24, %rsp
	popq	%rbx
	popq	%rbp
	ret
.Ltmp221:
	.size	_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint, .Ltmp221-_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_CMutableString_Mcapacity
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_Mcapacity,@function
_Pshadow_Pstandard_CMutableString_Mcapacity: # @_Pshadow_Pstandard_CMutableString_Mcapacity
# BB#0:
	movl	16(%rdi), %eax
	ret
.Ltmp222:
	.size	_Pshadow_Pstandard_CMutableString_Mcapacity, .Ltmp222-_Pshadow_Pstandard_CMutableString_Mcapacity

	.globl	_Pshadow_Pstandard_CMutableString_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_MtoString,@function
_Pshadow_Pstandard_CMutableString_MtoString: # @_Pshadow_Pstandard_CMutableString_MtoString
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp227:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp228:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp229:
	.cfi_def_cfa_offset 32
	subq	$32, %rsp
.Ltmp230:
	.cfi_def_cfa_offset 64
.Ltmp231:
	.cfi_offset %rbx, -32
.Ltmp232:
	.cfi_offset %r14, -24
.Ltmp233:
	.cfi_offset %rbp, -16
	movq	%rdi, %r14
	movq	%r14, 24(%rsp)
	movl	24(%r14), %ebp
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	%ebp, %edi
	callq	calloc
	movq	%rax, %rbx
	movl	%ebp, 16(%rsp)
	movq	%rbx, 8(%rsp)
	movl	$0, 4(%rsp)
	testl	%ebp, %ebp
	jle	.LBB53_4
# BB#1:
	xorl	%eax, %eax
	jmp	.LBB53_2
	.align	16, 0x90
.LBB53_3:                               # %_label15._label15_crit_edge
                                        #   in Loop: Header=BB53_2 Depth=1
	movq	24(%rsp), %r14
.LBB53_2:                               # %_label15
                                        # =>This Inner Loop Header: Depth=1
	movslq	%eax, %rax
	movq	8(%r14), %rcx
	movb	(%rcx,%rax), %cl
	movb	%cl, (%rbx,%rax)
	movl	4(%rsp), %eax
	incl	%eax
	movl	%eax, 4(%rsp)
	movl	16(%rsp), %ebp
	movq	8(%rsp), %rbx
	cmpl	%ebp, %eax
	jl	.LBB53_3
.LBB53_4:                               # %_label17
	movl	_Pshadow_Pstandard_CString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %r14
	movq	$_Pshadow_Pstandard_CString_Mclass, (%r14)
	movq	%r14, %rdi
	movq	%rbx, %rsi
	movl	%ebp, %edx
	callq	*_Pshadow_Pstandard_CString_Mclass+136(%rip)
	movq	%r14, %rax
	addq	$32, %rsp
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp234:
	.size	_Pshadow_Pstandard_CMutableString_MtoString, .Ltmp234-_Pshadow_Pstandard_CMutableString_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CMutableString_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_Mcreate,@function
_Pshadow_Pstandard_CMutableString_Mcreate: # @_Pshadow_Pstandard_CMutableString_Mcreate
# BB#0:
	pushq	%rbx
	movq	%rdi, %rbx
	movq	$_Pshadow_Pstandard_CMutableString_Mclass, (%rbx)
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	$10, %edi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$10, 16(%rbx)
	movl	$0, 24(%rbx)
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp235:
	.size	_Pshadow_Pstandard_CMutableString_Mcreate, .Ltmp235-_Pshadow_Pstandard_CMutableString_Mcreate

	.globl	_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_Cint
# BB#0:
	pushq	%rbp
	pushq	%rbx
	pushq	%rax
	movl	%esi, %ebp
	movq	%rdi, %rbx
	movq	$_Pshadow_Pstandard_CMutableString_Mclass, (%rbx)
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	$10, %edi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$10, 16(%rbx)
	movl	$0, 24(%rbx)
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	%ebp, %edi
	callq	calloc
	movl	%ebp, 16(%rbx)
	movq	%rax, 8(%rbx)
	movq	%rbx, %rax
	addq	$8, %rsp
	popq	%rbx
	popq	%rbp
	ret
.Ltmp236:
	.size	_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_Cint, .Ltmp236-_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject,@function
_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	pushq	%r14
.Ltmp240:
	.cfi_def_cfa_offset 16
	pushq	%rbx
.Ltmp241:
	.cfi_def_cfa_offset 24
	pushq	%rax
.Ltmp242:
	.cfi_def_cfa_offset 32
.Ltmp243:
	.cfi_offset %rbx, -24
.Ltmp244:
	.cfi_offset %r14, -16
	movq	%rsi, %r14
	movq	%rdi, %rbx
	movq	$_Pshadow_Pstandard_CMutableString_Mclass, (%rbx)
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	$10, %edi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$10, 16(%rbx)
	movl	$0, 24(%rbx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	movq	%r14, %rsi
	callq	*112(%rax)
	movq	%rbx, %rax
	addq	$8, %rsp
	popq	%rbx
	popq	%r14
	ret
.Ltmp245:
	.size	_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject, .Ltmp245-_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte,@function
_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte
# BB#0:
	addb	%dil, %sil
	movb	%sil, %al
	ret
.Ltmp246:
	.size	_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte, .Ltmp246-_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte

	.globl	_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte,@function
_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte
# BB#0:
	subb	%sil, %dil
	movb	%dil, %al
	ret
.Ltmp247:
	.size	_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte, .Ltmp247-_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte

	.globl	_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte,@function
_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte
# BB#0:
	movb	%sil, %al
	mulb	%dil
	ret
.Ltmp248:
	.size	_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte, .Ltmp248-_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte

	.globl	_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte,@function
_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte
# BB#0:
	movb	%dil, %al
	cbtw
	idivb	%sil
	ret
.Ltmp249:
	.size	_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte, .Ltmp249-_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte

	.globl	_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte,@function
_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte
# BB#0:
	movb	%dil, %al
	cbtw
	idivb	%sil
	shrw	$8, %ax
                                        # kill: AL<def> AL<kill> AX<kill>
	ret
.Ltmp250:
	.size	_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte, .Ltmp250-_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte

	.globl	_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte,@function
_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte
# BB#0:
	cmpb	%sil, %dil
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovgel	%ecx, %eax
	ret
.Ltmp251:
	.size	_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte, .Ltmp251-_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte

	.globl	_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte,@function
_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte
# BB#0:
	addb	%dil, %sil
	movb	%sil, %al
	ret
.Ltmp252:
	.size	_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte, .Ltmp252-_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte

	.globl	_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte,@function
_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte
# BB#0:
	subb	%sil, %dil
	movb	%dil, %al
	ret
.Ltmp253:
	.size	_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte, .Ltmp253-_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte

	.globl	_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte,@function
_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte
# BB#0:
	movb	%sil, %al
	mulb	%dil
	ret
.Ltmp254:
	.size	_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte, .Ltmp254-_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte

	.globl	_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte,@function
_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte
# BB#0:
	movzbl	%dil, %eax
                                        # kill: EAX<def> EAX<kill> AX<def>
	divb	%sil
	ret
.Ltmp255:
	.size	_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte, .Ltmp255-_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte

	.globl	_Pshadow_Pstandard_Cubyte_Mmodulus_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cubyte_Mmodulus_Pshadow_Pstandard_Cubyte,@function
_Pshadow_Pstandard_Cubyte_Mmodulus_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Mmodulus_Pshadow_Pstandard_Cubyte
# BB#0:
	movzbl	%dil, %eax
                                        # kill: EAX<def> EAX<kill> AX<def>
	divb	%sil
	shrw	$8, %ax
                                        # kill: AL<def> AL<kill> AX<kill>
	ret
.Ltmp256:
	.size	_Pshadow_Pstandard_Cubyte_Mmodulus_Pshadow_Pstandard_Cubyte, .Ltmp256-_Pshadow_Pstandard_Cubyte_Mmodulus_Pshadow_Pstandard_Cubyte

	.globl	_Pshadow_Pstandard_Cubyte_Mcompare_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cubyte_Mcompare_Pshadow_Pstandard_Cubyte,@function
_Pshadow_Pstandard_Cubyte_Mcompare_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Mcompare_Pshadow_Pstandard_Cubyte
# BB#0:
	cmpb	%sil, %dil
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovael	%ecx, %eax
	ret
.Ltmp257:
	.size	_Pshadow_Pstandard_Cubyte_Mcompare_Pshadow_Pstandard_Cubyte, .Ltmp257-_Pshadow_Pstandard_Cubyte_Mcompare_Pshadow_Pstandard_Cubyte

	.globl	_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort,@function
_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort
# BB#0:
	addl	%esi, %edi
	movw	%di, %ax
	ret
.Ltmp258:
	.size	_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort, .Ltmp258-_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort

	.globl	_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort,@function
_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort
# BB#0:
	subl	%esi, %edi
	movw	%di, %ax
	ret
.Ltmp259:
	.size	_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort, .Ltmp259-_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort

	.globl	_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort,@function
_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort
# BB#0:
	imull	%esi, %edi
	movw	%di, %ax
	ret
.Ltmp260:
	.size	_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort, .Ltmp260-_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort

	.globl	_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort,@function
_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort
# BB#0:
	movw	%di, %ax
	cwtd
	idivw	%si
	ret
.Ltmp261:
	.size	_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort, .Ltmp261-_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort

	.globl	_Pshadow_Pstandard_Cshort_Mmodulus_Pshadow_Pstandard_Cshort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cshort_Mmodulus_Pshadow_Pstandard_Cshort,@function
_Pshadow_Pstandard_Cshort_Mmodulus_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Mmodulus_Pshadow_Pstandard_Cshort
# BB#0:
	movw	%di, %ax
	cwtd
	idivw	%si
	movw	%dx, %ax
	ret
.Ltmp262:
	.size	_Pshadow_Pstandard_Cshort_Mmodulus_Pshadow_Pstandard_Cshort, .Ltmp262-_Pshadow_Pstandard_Cshort_Mmodulus_Pshadow_Pstandard_Cshort

	.globl	_Pshadow_Pstandard_Cshort_Mcompare_Pshadow_Pstandard_Cshort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cshort_Mcompare_Pshadow_Pstandard_Cshort,@function
_Pshadow_Pstandard_Cshort_Mcompare_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Mcompare_Pshadow_Pstandard_Cshort
# BB#0:
	cmpw	%si, %di
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovgel	%ecx, %eax
	ret
.Ltmp263:
	.size	_Pshadow_Pstandard_Cshort_Mcompare_Pshadow_Pstandard_Cshort, .Ltmp263-_Pshadow_Pstandard_Cshort_Mcompare_Pshadow_Pstandard_Cshort

	.globl	_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort,@function
_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort
# BB#0:
	addl	%esi, %edi
	movw	%di, %ax
	ret
.Ltmp264:
	.size	_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort, .Ltmp264-_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort

	.globl	_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort,@function
_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort
# BB#0:
	subl	%esi, %edi
	movw	%di, %ax
	ret
.Ltmp265:
	.size	_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort, .Ltmp265-_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort

	.globl	_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort,@function
_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort
# BB#0:
	imull	%esi, %edi
	movw	%di, %ax
	ret
.Ltmp266:
	.size	_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort, .Ltmp266-_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort

	.globl	_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort,@function
_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort
# BB#0:
	movw	%di, %ax
	xorl	%edx, %edx
	divw	%si
	ret
.Ltmp267:
	.size	_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort, .Ltmp267-_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort

	.globl	_Pshadow_Pstandard_Cushort_Mmodulus_Pshadow_Pstandard_Cushort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cushort_Mmodulus_Pshadow_Pstandard_Cushort,@function
_Pshadow_Pstandard_Cushort_Mmodulus_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Mmodulus_Pshadow_Pstandard_Cushort
# BB#0:
	movw	%di, %ax
	xorl	%edx, %edx
	divw	%si
	movw	%dx, %ax
	ret
.Ltmp268:
	.size	_Pshadow_Pstandard_Cushort_Mmodulus_Pshadow_Pstandard_Cushort, .Ltmp268-_Pshadow_Pstandard_Cushort_Mmodulus_Pshadow_Pstandard_Cushort

	.globl	_Pshadow_Pstandard_Cushort_Mcompare_Pshadow_Pstandard_Cushort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cushort_Mcompare_Pshadow_Pstandard_Cushort,@function
_Pshadow_Pstandard_Cushort_Mcompare_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Mcompare_Pshadow_Pstandard_Cushort
# BB#0:
	cmpw	%si, %di
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovael	%ecx, %eax
	ret
.Ltmp269:
	.size	_Pshadow_Pstandard_Cushort_Mcompare_Pshadow_Pstandard_Cushort, .Ltmp269-_Pshadow_Pstandard_Cushort_Mcompare_Pshadow_Pstandard_Cushort

	.globl	_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint
# BB#0:
	addl	%esi, %edi
	movl	%edi, %eax
	ret
.Ltmp270:
	.size	_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint, .Ltmp270-_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint
# BB#0:
	subl	%esi, %edi
	movl	%edi, %eax
	ret
.Ltmp271:
	.size	_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint, .Ltmp271-_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint
# BB#0:
	imull	%esi, %edi
	movl	%edi, %eax
	ret
.Ltmp272:
	.size	_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint, .Ltmp272-_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint
# BB#0:
	movl	%edi, %eax
	cltd
	idivl	%esi
	ret
.Ltmp273:
	.size	_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint, .Ltmp273-_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cint_Mmodulus_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Mmodulus_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_Cint_Mmodulus_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mmodulus_Pshadow_Pstandard_Cint
# BB#0:
	movl	%edi, %eax
	cltd
	idivl	%esi
	movl	%edx, %eax
	ret
.Ltmp274:
	.size	_Pshadow_Pstandard_Cint_Mmodulus_Pshadow_Pstandard_Cint, .Ltmp274-_Pshadow_Pstandard_Cint_Mmodulus_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cint_Mcompare_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Mcompare_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_Cint_Mcompare_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mcompare_Pshadow_Pstandard_Cint
# BB#0:
	cmpl	%esi, %edi
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovgel	%ecx, %eax
	ret
.Ltmp275:
	.size	_Pshadow_Pstandard_Cint_Mcompare_Pshadow_Pstandard_Cint, .Ltmp275-_Pshadow_Pstandard_Cint_Mcompare_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint
# BB#0:
	addl	%esi, %edi
	movl	%edi, %eax
	ret
.Ltmp276:
	.size	_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint, .Ltmp276-_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint
# BB#0:
	subl	%esi, %edi
	movl	%edi, %eax
	ret
.Ltmp277:
	.size	_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint, .Ltmp277-_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint
# BB#0:
	imull	%esi, %edi
	movl	%edi, %eax
	ret
.Ltmp278:
	.size	_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint, .Ltmp278-_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint
# BB#0:
	movl	%edi, %eax
	xorl	%edx, %edx
	divl	%esi
	ret
.Ltmp279:
	.size	_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint, .Ltmp279-_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint
# BB#0:
	movl	%edi, %eax
	xorl	%edx, %edx
	divl	%esi
	movl	%edx, %eax
	ret
.Ltmp280:
	.size	_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint, .Ltmp280-_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint
# BB#0:
	cmpl	%esi, %edi
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovael	%ecx, %eax
	ret
.Ltmp281:
	.size	_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint, .Ltmp281-_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong,@function
_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong
# BB#0:
	addq	%rsi, %rdi
	movq	%rdi, %rax
	ret
.Ltmp282:
	.size	_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong, .Ltmp282-_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong

	.globl	_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong,@function
_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong
# BB#0:
	subq	%rsi, %rdi
	movq	%rdi, %rax
	ret
.Ltmp283:
	.size	_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong, .Ltmp283-_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong

	.globl	_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong,@function
_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong
# BB#0:
	imulq	%rsi, %rdi
	movq	%rdi, %rax
	ret
.Ltmp284:
	.size	_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong, .Ltmp284-_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong

	.globl	_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong,@function
_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong
# BB#0:
	movq	%rdi, %rax
	cqto
	idivq	%rsi
	ret
.Ltmp285:
	.size	_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong, .Ltmp285-_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong

	.globl	_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong,@function
_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong
# BB#0:
	movq	%rdi, %rax
	cqto
	idivq	%rsi
	movq	%rdx, %rax
	ret
.Ltmp286:
	.size	_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong, .Ltmp286-_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong

	.globl	_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong,@function
_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong
# BB#0:
	cmpq	%rsi, %rdi
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovgel	%ecx, %eax
	ret
.Ltmp287:
	.size	_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong, .Ltmp287-_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong

	.globl	_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong,@function
_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong
# BB#0:
	addq	%rsi, %rdi
	movq	%rdi, %rax
	ret
.Ltmp288:
	.size	_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong, .Ltmp288-_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong

	.globl	_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong,@function
_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong
# BB#0:
	subq	%rsi, %rdi
	movq	%rdi, %rax
	ret
.Ltmp289:
	.size	_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong, .Ltmp289-_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong

	.globl	_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong,@function
_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong
# BB#0:
	imulq	%rsi, %rdi
	movq	%rdi, %rax
	ret
.Ltmp290:
	.size	_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong, .Ltmp290-_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong

	.globl	_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong,@function
_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong
# BB#0:
	movq	%rdi, %rax
	xorl	%edx, %edx
	divq	%rsi
	ret
.Ltmp291:
	.size	_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong, .Ltmp291-_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong

	.globl	_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong,@function
_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong
# BB#0:
	movq	%rdi, %rax
	xorl	%edx, %edx
	divq	%rsi
	movq	%rdx, %rax
	ret
.Ltmp292:
	.size	_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong, .Ltmp292-_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong

	.globl	_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong,@function
_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong
# BB#0:
	cmpq	%rsi, %rdi
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovael	%ecx, %eax
	ret
.Ltmp293:
	.size	_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong, .Ltmp293-_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong

	.globl	_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat,@function
_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat
# BB#0:
	vaddss	%xmm1, %xmm0, %xmm0
	ret
.Ltmp294:
	.size	_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat, .Ltmp294-_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat

	.globl	_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat,@function
_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat
# BB#0:
	vsubss	%xmm1, %xmm0, %xmm0
	ret
.Ltmp295:
	.size	_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat, .Ltmp295-_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat

	.globl	_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat,@function
_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat
# BB#0:
	vmulss	%xmm1, %xmm0, %xmm0
	ret
.Ltmp296:
	.size	_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat, .Ltmp296-_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat

	.globl	_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat,@function
_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat
# BB#0:
	vdivss	%xmm1, %xmm0, %xmm0
	ret
.Ltmp297:
	.size	_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat, .Ltmp297-_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat

	.globl	_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat,@function
_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat
# BB#0:
	jmp	fmodf                   # TAILCALL
.Ltmp298:
	.size	_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat, .Ltmp298-_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat

	.globl	_Pshadow_Pstandard_Cfloat_Mcompare_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cfloat_Mcompare_Pshadow_Pstandard_Cfloat,@function
_Pshadow_Pstandard_Cfloat_Mcompare_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Mcompare_Pshadow_Pstandard_Cfloat
# BB#0:
	vcmpneqss	%xmm1, %xmm0, %xmm2
	vmovd	%xmm2, %ecx
	andl	$1, %ecx
	movl	$-1, %eax
	vucomiss	%xmm0, %xmm1
	cmovbel	%ecx, %eax
	ret
.Ltmp299:
	.size	_Pshadow_Pstandard_Cfloat_Mcompare_Pshadow_Pstandard_Cfloat, .Ltmp299-_Pshadow_Pstandard_Cfloat_Mcompare_Pshadow_Pstandard_Cfloat

	.globl	_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble,@function
_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble
# BB#0:
	vaddsd	%xmm1, %xmm0, %xmm0
	ret
.Ltmp300:
	.size	_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble, .Ltmp300-_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble

	.globl	_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble,@function
_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble
# BB#0:
	vsubsd	%xmm1, %xmm0, %xmm0
	ret
.Ltmp301:
	.size	_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble, .Ltmp301-_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble

	.globl	_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble,@function
_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble
# BB#0:
	vmulsd	%xmm1, %xmm0, %xmm0
	ret
.Ltmp302:
	.size	_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble, .Ltmp302-_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble

	.globl	_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble,@function
_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble
# BB#0:
	vdivsd	%xmm1, %xmm0, %xmm0
	ret
.Ltmp303:
	.size	_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble, .Ltmp303-_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble

	.globl	_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble,@function
_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble
# BB#0:
	jmp	fmod                    # TAILCALL
.Ltmp304:
	.size	_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble, .Ltmp304-_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble

	.globl	_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble,@function
_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble
# BB#0:
	vcmpneqsd	%xmm1, %xmm0, %xmm2
	vmovd	%xmm2, %ecx
	andl	$1, %ecx
	movl	$-1, %eax
	vucomisd	%xmm0, %xmm1
	cmovbel	%ecx, %eax
	ret
.Ltmp305:
	.size	_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble, .Ltmp305-_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble

	.globl	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject,@function
_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	movq	(%rdi), %rax
	movq	96(%rax), %rax
	jmpq	*%rax  # TAILCALL
.Ltmp306:
	.size	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject, .Ltmp306-_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CObject_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_MtoString,@function
_Pshadow_Pstandard_CObject_MtoString:   # @_Pshadow_Pstandard_CObject_MtoString
	.cfi_startproc
# BB#0:
	pushq	%rax
.Ltmp308:
	.cfi_def_cfa_offset 16
	movq	(%rdi), %rax
	callq	*88(%rax)
	movq	24(%rax), %rax
	popq	%rdx
	ret
.Ltmp309:
	.size	_Pshadow_Pstandard_CObject_MtoString, .Ltmp309-_Pshadow_Pstandard_CObject_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CObject_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_Mcreate,@function
_Pshadow_Pstandard_CObject_Mcreate:     # @_Pshadow_Pstandard_CObject_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_CObject_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp310:
	.size	_Pshadow_Pstandard_CObject_Mcreate, .Ltmp310-_Pshadow_Pstandard_CObject_Mcreate

	.globl	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject,@function
_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
# BB#0:
	cmpq	%rsi, %rdi
	sete	%al
	ret
.Ltmp311:
	.size	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject, .Ltmp311-_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject

	.globl	_Pshadow_Pstandard_CObject_MgetClass
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_MgetClass,@function
_Pshadow_Pstandard_CObject_MgetClass:   # @_Pshadow_Pstandard_CObject_MgetClass
# BB#0:
	movq	(%rdi), %rax
	ret
.Ltmp312:
	.size	_Pshadow_Pstandard_CObject_MgetClass, .Ltmp312-_Pshadow_Pstandard_CObject_MgetClass

	.globl	_Pshadow_Pstandard_CObject_Mcopy
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_Mcopy,@function
_Pshadow_Pstandard_CObject_Mcopy:       # @_Pshadow_Pstandard_CObject_Mcopy
# BB#0:
	movq	%rdi, %rax
	ret
.Ltmp313:
	.size	_Pshadow_Pstandard_CObject_Mcopy, .Ltmp313-_Pshadow_Pstandard_CObject_Mcopy

	.globl	_Pshadow_Pstandard_CObject_Mfreeze
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_Mfreeze,@function
_Pshadow_Pstandard_CObject_Mfreeze:     # @_Pshadow_Pstandard_CObject_Mfreeze
# BB#0:
	movq	%rdi, %rax
	ret
.Ltmp314:
	.size	_Pshadow_Pstandard_CObject_Mfreeze, .Ltmp314-_Pshadow_Pstandard_CObject_Mfreeze

	.globl	_Pshadow_Pstandard_CReference_Mvalue
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CReference_Mvalue,@function
_Pshadow_Pstandard_CReference_Mvalue:   # @_Pshadow_Pstandard_CReference_Mvalue
# BB#0:
	movq	16(%rdi), %rax
	ret
.Ltmp315:
	.size	_Pshadow_Pstandard_CReference_Mvalue, .Ltmp315-_Pshadow_Pstandard_CReference_Mvalue

	.globl	_Pshadow_Pstandard_CReference_Mvalue_CT
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CReference_Mvalue_CT,@function
_Pshadow_Pstandard_CReference_Mvalue_CT: # @_Pshadow_Pstandard_CReference_Mvalue_CT
# BB#0:
	movq	%rsi, 16(%rdi)
	ret
.Ltmp316:
	.size	_Pshadow_Pstandard_CReference_Mvalue_CT, .Ltmp316-_Pshadow_Pstandard_CReference_Mvalue_CT

	.globl	_Pshadow_Pstandard_CReference_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CReference_MtoString,@function
_Pshadow_Pstandard_CReference_MtoString: # @_Pshadow_Pstandard_CReference_MtoString
	.cfi_startproc
# BB#0:                                 # %_label2
	movq	16(%rdi), %rax
	movl	$.L_string1370, %edi
	testq	%rax, %rax
	cmovneq	%rax, %rdi
	movq	(%rdi), %rax
	jmpq	*104(%rax)  # TAILCALL
.Ltmp317:
	.size	_Pshadow_Pstandard_CReference_MtoString, .Ltmp317-_Pshadow_Pstandard_CReference_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CReference_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CReference_Mcreate,@function
_Pshadow_Pstandard_CReference_Mcreate:  # @_Pshadow_Pstandard_CReference_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_CReference_Mclass, (%rdi)
	movq	%rsi, 8(%rdi)
	movq	$0, 16(%rdi)
	movq	%rdi, %rax
	ret
.Ltmp318:
	.size	_Pshadow_Pstandard_CReference_Mcreate, .Ltmp318-_Pshadow_Pstandard_CReference_Mcreate

	.globl	_Pshadow_Pstandard_CReference_Mcreate_CT
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CReference_Mcreate_CT,@function
_Pshadow_Pstandard_CReference_Mcreate_CT: # @_Pshadow_Pstandard_CReference_Mcreate_CT
# BB#0:
	movq	$_Pshadow_Pstandard_CReference_Mclass, (%rdi)
	movq	%rsi, 8(%rdi)
	movq	%rdx, 16(%rdi)
	movq	%rdi, %rax
	ret
.Ltmp319:
	.size	_Pshadow_Pstandard_CReference_Mcreate_CT, .Ltmp319-_Pshadow_Pstandard_CReference_Mcreate_CT

	.globl	_Pshadow_Pstandard_Cshort_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cshort_Mcreate,@function
_Pshadow_Pstandard_Cshort_Mcreate:      # @_Pshadow_Pstandard_Cshort_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cshort_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp320:
	.size	_Pshadow_Pstandard_Cshort_Mcreate, .Ltmp320-_Pshadow_Pstandard_Cshort_Mcreate

	.globl	_Pshadow_Pstandard_CString_MtoUpperCase
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_MtoUpperCase,@function
_Pshadow_Pstandard_CString_MtoUpperCase: # @_Pshadow_Pstandard_CString_MtoUpperCase
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp326:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp327:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp328:
	.cfi_def_cfa_offset 32
	pushq	%rbx
.Ltmp329:
	.cfi_def_cfa_offset 40
	subq	$24, %rsp
.Ltmp330:
	.cfi_def_cfa_offset 64
.Ltmp331:
	.cfi_offset %rbx, -40
.Ltmp332:
	.cfi_offset %r14, -32
.Ltmp333:
	.cfi_offset %r15, -24
.Ltmp334:
	.cfi_offset %rbp, -16
	movq	%rdi, %r15
	movq	(%r15), %rax
	callq	*176(%rax)
	movl	%eax, %ebp
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	%ebp, %edi
	callq	calloc
	movq	%rax, %r14
	movl	%ebp, 16(%rsp)
	movq	%r14, 8(%rsp)
	movq	(%r15), %rax
	movq	%r15, %rdi
	callq	*176(%rax)
	testl	%eax, %eax
	jle	.LBB130_4
# BB#1:                                 # %_label0.lr.ph
	xorl	%ebp, %ebp
	movq	8(%rsp), %rbx
	.align	16, 0x90
.LBB130_2:                              # %_label0
                                        # =>This Inner Loop Header: Depth=1
	movslq	%ebp, %rbp
	movsbl	(%rbx,%rbp), %ecx
	leal	224(%rcx), %eax
	leal	-97(%rcx), %edx
	cmpl	$26, %edx
	cmovael	%ecx, %eax
	movb	%al, (%rbx,%rbp)
	movq	(%r15), %rax
	movq	%r15, %rdi
	callq	*176(%rax)
	incl	%ebp
	cmpl	%eax, %ebp
	jl	.LBB130_2
# BB#3:                                 # %_label1._label2_crit_edge
	movl	16(%rsp), %ebp
	movq	8(%rsp), %r14
.LBB130_4:                              # %_label2
	movl	_Pshadow_Pstandard_CString_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rax)
	movb	24(%r15), %cl
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rax)
	movl	%ebp, 16(%rax)
	movq	%r14, 8(%rax)
	movb	%cl, 24(%rax)
	addq	$24, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp335:
	.size	_Pshadow_Pstandard_CString_MtoUpperCase, .Ltmp335-_Pshadow_Pstandard_CString_MtoUpperCase
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString,@function
_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString: # @_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp340:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp341:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp342:
	.cfi_def_cfa_offset 32
.Ltmp343:
	.cfi_offset %rbx, -32
.Ltmp344:
	.cfi_offset %r14, -24
.Ltmp345:
	.cfi_offset %rbp, -16
	movq	%rsi, %r14
	movq	%rdi, %rbx
	movq	(%rbx), %rax
	callq	*176(%rax)
	movl	%eax, %ebp
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*176(%rax)
	movl	%eax, %ecx
	xorb	%al, %al
	cmpl	%ecx, %ebp
	je	.LBB131_8
# BB#1:                                 # %_label4
	xorl	%ebp, %ebp
	.align	16, 0x90
.LBB131_2:                              # %_label9.i
                                        # =>This Inner Loop Header: Depth=1
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*176(%rax)
	cmpl	%eax, %ebp
	jge	.LBB131_6
# BB#3:                                 # %_label14.i
                                        #   in Loop: Header=BB131_2 Depth=1
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*176(%rax)
	cmpl	%eax, %ebp
	jge	.LBB131_6
# BB#4:                                 # %_label8.i
                                        #   in Loop: Header=BB131_2 Depth=1
	movslq	%ebp, %rax
	movq	8(%r14), %rcx
	movb	(%rcx,%rax), %cl
	movq	8(%rbx), %rdx
	movb	(%rdx,%rax), %al
	incl	%ebp
	subb	%cl, %al
	je	.LBB131_2
# BB#5:                                 # %_label11.i
	movsbl	%al, %ebx
	jmp	.LBB131_7
.LBB131_6:                              # %_label10.i
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*176(%rax)
	movl	%eax, %ebx
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*176(%rax)
	subl	%eax, %ebx
.LBB131_7:                              # %_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString.exit
	testl	%ebx, %ebx
	sete	%al
.LBB131_8:                              # %_label3
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp346:
	.size	_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString, .Ltmp346-_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_MtoLowerCase
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_MtoLowerCase,@function
_Pshadow_Pstandard_CString_MtoLowerCase: # @_Pshadow_Pstandard_CString_MtoLowerCase
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp352:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp353:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp354:
	.cfi_def_cfa_offset 32
	pushq	%rbx
.Ltmp355:
	.cfi_def_cfa_offset 40
	subq	$24, %rsp
.Ltmp356:
	.cfi_def_cfa_offset 64
.Ltmp357:
	.cfi_offset %rbx, -40
.Ltmp358:
	.cfi_offset %r14, -32
.Ltmp359:
	.cfi_offset %r15, -24
.Ltmp360:
	.cfi_offset %rbp, -16
	movq	%rdi, %r15
	movq	(%r15), %rax
	callq	*176(%rax)
	movl	%eax, %ebp
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	%ebp, %edi
	callq	calloc
	movq	%rax, %r14
	movl	%ebp, 16(%rsp)
	movq	%r14, 8(%rsp)
	movq	(%r15), %rax
	movq	%r15, %rdi
	callq	*176(%rax)
	testl	%eax, %eax
	jle	.LBB132_4
# BB#1:                                 # %_label5.lr.ph
	xorl	%ebp, %ebp
	movq	8(%rsp), %rbx
	.align	16, 0x90
.LBB132_2:                              # %_label5
                                        # =>This Inner Loop Header: Depth=1
	movslq	%ebp, %rbp
	movsbl	(%rbx,%rbp), %ecx
	leal	32(%rcx), %eax
	leal	-65(%rcx), %edx
	cmpl	$26, %edx
	cmovael	%ecx, %eax
	movb	%al, (%rbx,%rbp)
	movq	(%r15), %rax
	movq	%r15, %rdi
	callq	*176(%rax)
	incl	%ebp
	cmpl	%eax, %ebp
	jl	.LBB132_2
# BB#3:                                 # %_label6._label7_crit_edge
	movl	16(%rsp), %ebp
	movq	8(%rsp), %r14
.LBB132_4:                              # %_label7
	movl	_Pshadow_Pstandard_CString_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rax)
	movb	24(%r15), %cl
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rax)
	movl	%ebp, 16(%rax)
	movq	%r14, 8(%rax)
	movb	%cl, 24(%rax)
	addq	$24, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp361:
	.size	_Pshadow_Pstandard_CString_MtoLowerCase, .Ltmp361-_Pshadow_Pstandard_CString_MtoLowerCase
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString,@function
_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString: # @_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp366:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp367:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp368:
	.cfi_def_cfa_offset 32
.Ltmp369:
	.cfi_offset %rbx, -32
.Ltmp370:
	.cfi_offset %r14, -24
.Ltmp371:
	.cfi_offset %rbp, -16
	movq	%rsi, %r14
	movq	%rdi, %rbx
	xorl	%ebp, %ebp
	.align	16, 0x90
.LBB133_1:                              # %_label9
                                        # =>This Inner Loop Header: Depth=1
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*176(%rax)
	cmpl	%eax, %ebp
	jge	.LBB133_6
# BB#2:                                 # %_label14
                                        #   in Loop: Header=BB133_1 Depth=1
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*176(%rax)
	cmpl	%eax, %ebp
	jge	.LBB133_6
# BB#3:                                 # %_label8
                                        #   in Loop: Header=BB133_1 Depth=1
	movslq	%ebp, %rax
	movq	8(%r14), %rcx
	movb	(%rcx,%rax), %cl
	movq	8(%rbx), %rdx
	movb	(%rdx,%rax), %al
	incl	%ebp
	subb	%cl, %al
	je	.LBB133_1
# BB#4:                                 # %_label11
	movsbl	%al, %eax
	jmp	.LBB133_5
.LBB133_6:                              # %_label10
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*176(%rax)
	movl	%eax, %ebx
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*176(%rax)
	subl	%eax, %ebx
	movl	%ebx, %eax
.LBB133_5:                              # %_label11
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp372:
	.size	_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString, .Ltmp372-_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
# BB#0:
	pushq	%rbp
	pushq	%r15
	pushq	%r14
	pushq	%r13
	pushq	%r12
	pushq	%rbx
	subq	$24, %rsp
	movl	%edx, 12(%rsp)          # 4-byte Spill
	movl	%esi, 8(%rsp)           # 4-byte Spill
	movq	%rdi, %rbx
	movq	%rbx, 16(%rsp)          # 8-byte Spill
	movl	_Pshadow_Pstandard_CString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %r13
	movq	$_Pshadow_Pstandard_CString_Mclass, (%r13)
	movq	8(%rbx), %r15
	movl	16(%rbx), %ebp
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	movl	%esi, %r12d
	callq	calloc
	movq	%rax, %rbx
	movl	%ebp, (%rbx)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %r14d
	movl	%r14d, %edi
	callq	malloc
	movq	%rax, %rbp
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbp)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbp)
	movq	$_Pshadow_Pstandard_Cubyte_Mclass, 24(%rbp)
	movq	%rbx, 8(%rbp)
	movl	$1, 16(%rbp)
	movq	%r15, 32(%rbp)
	movl	%r14d, %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbx)
	movq	24(%rbp), %rax
	movq	%rax, (%rsp)            # 8-byte Spill
	movl	16(%rbp), %r14d
	movl	%r14d, %edi
	movl	%r12d, %esi
	callq	calloc
	movq	%rax, %r15
	movl	8(%rsp), %r12d          # 4-byte Reload
	movl	12(%rsp), %eax          # 4-byte Reload
	subl	%r12d, %eax
	movl	%eax, (%r15)
	leaq	4(%r15), %rdi
	movq	8(%rbp), %rsi
	leal	-1(%r14), %edx
	addq	$4, %rsi
	callq	memcpy
	movl	%r12d, %eax
	movq	(%rsp), %rdx            # 8-byte Reload
	imull	48(%rdx), %eax
	movq	32(%rbp), %rcx
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbx)
	movq	%rdx, 24(%rbx)
	movl	%r14d, 16(%rbx)
	movq	%r15, 8(%rbx)
	movslq	%eax, %rax
	addq	%rcx, %rax
	movq	%rax, 32(%rbx)
	movq	16(%rsp), %rcx          # 8-byte Reload
	movb	24(%rcx), %cl
	movq	8(%rbx), %rdx
	movl	(%rdx), %edx
	movq	$_Pshadow_Pstandard_CString_Mclass, (%r13)
	movl	%edx, 16(%r13)
	movq	%rax, 8(%r13)
	movb	%cl, 24(%r13)
	movq	%r13, %rax
	addq	$24, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp373:
	.size	_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint, .Ltmp373-_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_CString_Mlength
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mlength,@function
_Pshadow_Pstandard_CString_Mlength:     # @_Pshadow_Pstandard_CString_Mlength
# BB#0:
	movl	16(%rdi), %eax
	ret
.Ltmp374:
	.size	_Pshadow_Pstandard_CString_Mlength, .Ltmp374-_Pshadow_Pstandard_CString_Mlength

	.globl	_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint
# BB#0:
	movslq	%esi, %rax
	movq	8(%rdi), %rcx
	movb	(%rcx,%rax), %al
	ret
.Ltmp375:
	.size	_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint, .Ltmp375-_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_CString_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_MtoString,@function
_Pshadow_Pstandard_CString_MtoString:   # @_Pshadow_Pstandard_CString_MtoString
# BB#0:
	movq	%rdi, %rax
	ret
.Ltmp376:
	.size	_Pshadow_Pstandard_CString_MtoString, .Ltmp376-_Pshadow_Pstandard_CString_MtoString

	.globl	_Pshadow_Pstandard_CString_Mchars
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mchars,@function
_Pshadow_Pstandard_CString_Mchars:      # @_Pshadow_Pstandard_CString_Mchars
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp381:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp382:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp383:
	.cfi_def_cfa_offset 32
.Ltmp384:
	.cfi_offset %rbx, -32
.Ltmp385:
	.cfi_offset %r14, -24
.Ltmp386:
	.cfi_offset %rbp, -16
	movq	8(%rdi), %r14
	movl	16(%rdi), %ebp
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbx
	movl	%ebp, (%rbx)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Cubyte_Mclass, 24(%rax)
	movq	%rbx, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r14, 32(%rax)
	movq	%rax, %rdi
	callq	_Pshadow_Pstandard_CArray_Mcopy
	movq	8(%rax), %rcx
	movq	32(%rax), %rax
	movl	(%rcx), %edx
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp387:
	.size	_Pshadow_Pstandard_CString_Mchars, .Ltmp387-_Pshadow_Pstandard_CString_Mchars
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mcreate,@function
_Pshadow_Pstandard_CString_Mcreate:     # @_Pshadow_Pstandard_CString_Mcreate
# BB#0:
	pushq	%rbx
	movq	%rdi, %rbx
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rbx)
	movb	$1, 24(%rbx)
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	xorl	%edi, %edi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$0, 16(%rbx)
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp388:
	.size	_Pshadow_Pstandard_CString_Mcreate, .Ltmp388-_Pshadow_Pstandard_CString_Mcreate

	.globl	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1,@function
_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1: # @_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1
# BB#0:
	pushq	%rbp
	pushq	%r15
	pushq	%r14
	pushq	%r12
	pushq	%rbx
	subq	$16, %rsp
	movl	%edx, %r15d
	movq	%rsi, %rbx
	movq	%rdi, %r14
	movq	$_Pshadow_Pstandard_CString_Mclass, (%r14)
	movl	%r15d, 8(%rsp)
	movq	%rbx, (%rsp)
	movb	$1, 24(%r14)
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	xorl	%ebp, %ebp
	xorl	%edi, %edi
	callq	calloc
	movq	%rax, 8(%r14)
	movl	$0, 16(%r14)
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	8(%rsp), %r12d
	movl	%r12d, %edi
	callq	calloc
	movb	$1, %cl
	testl	%r15d, %r15d
	movl	%r12d, 16(%r14)
	movq	%rax, 8(%r14)
	jle	.LBB140_4
# BB#1:                                 # %_label16.lr.ph
	xorb	%al, %al
	.align	16, 0x90
.LBB140_2:                              # %_label16
                                        # =>This Inner Loop Header: Depth=1
	movslq	%ebp, %rbp
	movb	(%rbx,%rbp), %cl
	movq	8(%r14), %rdx
	movb	%cl, (%rdx,%rbp)
	movq	(%rsp), %rbx
	orb	(%rbx,%rbp), %al
	incl	%ebp
	cmpl	8(%rsp), %ebp
	jl	.LBB140_2
# BB#3:                                 # %_label17._label18_crit_edge
	testb	%al, %al
	setns	%cl
.LBB140_4:                              # %_label18
	movb	%cl, 24(%r14)
	movq	%r14, %rax
	addq	$16, %rsp
	popq	%rbx
	popq	%r12
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp389:
	.size	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1, .Ltmp389-_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1

	.globl	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1,@function
_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1: # @_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1
# BB#0:
	pushq	%rbp
	pushq	%r15
	pushq	%r14
	pushq	%rbx
	subq	$24, %rsp
	movl	%edx, %ebp
	movq	%rsi, %r14
	movq	%rdi, %rbx
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rbx)
	movl	%ebp, 16(%rsp)
	movq	%r14, 8(%rsp)
	movb	$1, 24(%rbx)
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	xorl	%edi, %edi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$0, 16(%rbx)
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	16(%rsp), %r15d
	movl	%r15d, %edi
	callq	calloc
	testl	%ebp, %ebp
	movl	%r15d, 16(%rbx)
	movq	%rax, 8(%rbx)
	jle	.LBB141_3
# BB#1:
	xorl	%eax, %eax
	.align	16, 0x90
.LBB141_2:                              # %_label19
                                        # =>This Inner Loop Header: Depth=1
	movb	(%r14,%rax,4), %cl
	movq	8(%rbx), %rdx
	movb	%cl, (%rdx,%rax)
	incq	%rax
	cmpl	%ebp, %eax
	jl	.LBB141_2
.LBB141_3:                              # %_label21
	movq	%rbx, %rax
	addq	$24, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp390:
	.size	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1, .Ltmp390-_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1

	.globl	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString,@function
_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString: # @_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString
# BB#0:
	pushq	%r14
	pushq	%rbx
	pushq	%rax
	movq	%rsi, %r14
	movq	%rdi, %rbx
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rbx)
	movb	$1, 24(%rbx)
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	xorl	%edi, %edi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$0, 16(%rbx)
	movq	8(%r14), %rax
	movl	16(%r14), %ecx
	movl	%ecx, 16(%rbx)
	movq	%rax, 8(%rbx)
	movb	24(%r14), %al
	movb	%al, 24(%rbx)
	movq	%rbx, %rax
	addq	$8, %rsp
	popq	%rbx
	popq	%r14
	ret
.Ltmp391:
	.size	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString, .Ltmp391-_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString

	.globl	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1_Pshadow_Pstandard_Cboolean
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1_Pshadow_Pstandard_Cboolean,@function
_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1_Pshadow_Pstandard_Cboolean: # @_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1_Pshadow_Pstandard_Cboolean
# BB#0:
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rdi)
	movl	%edx, 16(%rdi)
	movq	%rsi, 8(%rdi)
	andl	$1, %ecx
	movb	%cl, 24(%rdi)
	movq	%rdi, %rax
	ret
.Ltmp392:
	.size	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1_Pshadow_Pstandard_Cboolean, .Ltmp392-_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1_Pshadow_Pstandard_Cboolean

	.globl	_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString,@function
_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString: # @_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp399:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp400:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp401:
	.cfi_def_cfa_offset 32
	pushq	%r12
.Ltmp402:
	.cfi_def_cfa_offset 40
	pushq	%rbx
.Ltmp403:
	.cfi_def_cfa_offset 48
	subq	$16, %rsp
.Ltmp404:
	.cfi_def_cfa_offset 64
.Ltmp405:
	.cfi_offset %rbx, -48
.Ltmp406:
	.cfi_offset %r12, -40
.Ltmp407:
	.cfi_offset %r14, -32
.Ltmp408:
	.cfi_offset %r15, -24
.Ltmp409:
	.cfi_offset %rbp, -16
	movq	%rsi, %r14
	movq	%rdi, %r12
	movq	(%r12), %rax
	callq	*176(%rax)
	movl	%eax, %r15d
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*176(%rax)
	movl	%eax, %ebp
	addl	%r15d, %ebp
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	%ebp, %edi
	callq	calloc
	movl	%ebp, 8(%rsp)
	movq	%rax, (%rsp)
	movq	(%r12), %rax
	movq	%r12, %rdi
	callq	*176(%rax)
	testl	%eax, %eax
	jle	.LBB144_3
# BB#1:                                 # %_label22.lr.ph
	xorl	%ebp, %ebp
	movq	(%rsp), %r15
	.align	16, 0x90
.LBB144_2:                              # %_label22
                                        # =>This Inner Loop Header: Depth=1
	movslq	%ebp, %rbp
	movq	8(%r12), %rax
	movb	(%rax,%rbp), %al
	movb	%al, (%r15,%rbp)
	movq	(%r12), %rax
	movq	%r12, %rdi
	callq	*176(%rax)
	incl	%ebp
	cmpl	%eax, %ebp
	jl	.LBB144_2
.LBB144_3:                              # %_label26.preheader
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*176(%rax)
	testl	%eax, %eax
	jle	.LBB144_6
# BB#4:                                 # %_label25.lr.ph
	xorl	%ebp, %ebp
	movq	(%rsp), %r15
	.align	16, 0x90
.LBB144_5:                              # %_label25
                                        # =>This Inner Loop Header: Depth=1
	movslq	%ebp, %rbp
	movq	8(%r14), %rax
	movb	(%rax,%rbp), %bl
	movq	(%r12), %rax
	movq	%r12, %rdi
	callq	*176(%rax)
	movl	%ebp, %ecx
	addl	%eax, %ecx
	movslq	%ecx, %rax
	movb	%bl, (%r15,%rax)
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*176(%rax)
	incl	%ebp
	cmpl	%eax, %ebp
	jl	.LBB144_5
.LBB144_6:                              # %_label27
	movl	_Pshadow_Pstandard_CString_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rax)
	addq	$24, %r14
	movb	24(%r12), %cl
	addq	$24, %r12
	testb	%cl, %cl
	cmovneq	%r14, %r12
	movb	(%r12), %cl
	movq	(%rsp), %rdx
	movl	8(%rsp), %esi
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rax)
	movl	%esi, 16(%rax)
	movq	%rdx, 8(%rax)
	movb	%cl, 24(%rax)
	addq	$16, %rsp
	popq	%rbx
	popq	%r12
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp410:
	.size	_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString, .Ltmp410-_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CSystem_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CSystem_Mcreate,@function
_Pshadow_Pstandard_CSystem_Mcreate:     # @_Pshadow_Pstandard_CSystem_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_CSystem_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp411:
	.size	_Pshadow_Pstandard_CSystem_Mcreate, .Ltmp411-_Pshadow_Pstandard_CSystem_Mcreate

	.globl	_Pshadow_Pstandard_Cubyte_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cubyte_Mcreate,@function
_Pshadow_Pstandard_Cubyte_Mcreate:      # @_Pshadow_Pstandard_Cubyte_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cubyte_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp412:
	.size	_Pshadow_Pstandard_Cubyte_Mcreate, .Ltmp412-_Pshadow_Pstandard_Cubyte_Mcreate

	.globl	_Pshadow_Pstandard_Cuint_Mdigit_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Mdigit_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Mdigit_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mdigit_Pshadow_Pstandard_Cuint
# BB#0:
	movl	%esi, %eax
	cmpl	$9, %eax
	ja	.LBB147_2
# BB#1:                                 # %_label0
	addl	$48, %eax
	ret
.LBB147_2:                              # %_label2
	addl	$87, %eax
	ret
.Ltmp413:
	.size	_Pshadow_Pstandard_Cuint_Mdigit_Pshadow_Pstandard_Cuint, .Ltmp413-_Pshadow_Pstandard_Cuint_Mdigit_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint
# BB#0:                                 # %_label5
	movl	8(%rdi), %eax
	cmpl	%esi, %eax
	cmovael	%esi, %eax
	ret
.Ltmp414:
	.size	_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint, .Ltmp414-_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint
# BB#0:                                 # %_label8
	movl	8(%rdi), %eax
	cmpl	%esi, %eax
	cmovbel	%esi, %eax
	ret
.Ltmp415:
	.size	_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint, .Ltmp415-_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Cuint_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_MtoString,@function
_Pshadow_Pstandard_Cuint_MtoString:     # @_Pshadow_Pstandard_Cuint_MtoString
	.cfi_startproc
# BB#0:
	pushq	%r15
.Ltmp421:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp422:
	.cfi_def_cfa_offset 24
	pushq	%r12
.Ltmp423:
	.cfi_def_cfa_offset 32
	pushq	%rbx
.Ltmp424:
	.cfi_def_cfa_offset 40
	pushq	%rax
.Ltmp425:
	.cfi_def_cfa_offset 48
.Ltmp426:
	.cfi_offset %rbx, -40
.Ltmp427:
	.cfi_offset %r12, -32
.Ltmp428:
	.cfi_offset %r14, -24
.Ltmp429:
	.cfi_offset %r15, -16
	movl	8(%rdi), %ebx
	movl	_Pshadow_Pstandard_CMutableString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %r14
	movq	$_Pshadow_Pstandard_CMutableString_Mclass, (%r14)
	movq	%r14, %rdi
	callq	*_Pshadow_Pstandard_CMutableString_Mclass+64(%rip)
	movl	$3435973837, %r15d      # imm = 0xCCCCCCCD
	.align	16, 0x90
.LBB150_1:                              # %_label9.i
                                        # =>This Inner Loop Header: Depth=1
	movl	_Pshadow_Pstandard_Ccode_Mclass+44(%rip), %edi
	callq	malloc
	movl	%ebx, %r12d
	movq	$_Pshadow_Pstandard_Ccode_Mclass, (%rax)
	movq	%r12, %rbx
	imulq	%r15, %rbx
	shrq	$35, %rbx
	imull	$10, %ebx, %edx
	movl	%r12d, %ecx
	subl	%edx, %ecx
	orl	$48, %ecx
	movl	%ecx, 8(%rax)
	movq	(%r14), %rcx
	movq	%r14, %rdi
	movq	%rax, %rsi
	callq	*112(%rcx)
	cmpl	$9, %r12d
	ja	.LBB150_1
# BB#2:                                 # %_Pshadow_Pstandard_Cuint_MtoString_Pshadow_Pstandard_Cuint.exit
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*160(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	addq	$8, %rsp
	popq	%rbx
	popq	%r12
	popq	%r14
	popq	%r15
	jmpq	*104(%rcx)  # TAILCALL
.Ltmp430:
	.size	_Pshadow_Pstandard_Cuint_MtoString, .Ltmp430-_Pshadow_Pstandard_Cuint_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_Cuint_MtoString_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_MtoString_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_MtoString_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_MtoString_Pshadow_Pstandard_Cuint
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp436:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp437:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp438:
	.cfi_def_cfa_offset 32
	pushq	%rbx
.Ltmp439:
	.cfi_def_cfa_offset 40
	pushq	%rax
.Ltmp440:
	.cfi_def_cfa_offset 48
.Ltmp441:
	.cfi_offset %rbx, -40
.Ltmp442:
	.cfi_offset %r14, -32
.Ltmp443:
	.cfi_offset %r15, -24
.Ltmp444:
	.cfi_offset %rbp, -16
	movl	%esi, %r14d
	movl	8(%rdi), %ebp
	movl	_Pshadow_Pstandard_CMutableString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_Pshadow_Pstandard_CMutableString_Mclass, (%rbx)
	movq	%rbx, %rdi
	callq	*_Pshadow_Pstandard_CMutableString_Mclass+64(%rip)
	movl	$48, %r15d
	.align	16, 0x90
.LBB151_1:                              # %_label9
                                        # =>This Inner Loop Header: Depth=1
	movl	_Pshadow_Pstandard_Ccode_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rcx
	movq	$_Pshadow_Pstandard_Ccode_Mclass, (%rcx)
	movl	%ebp, %eax
	xorl	%edx, %edx
	divl	%r14d
	movl	%eax, %ebp
	cmpl	$10, %edx
	movl	$87, %eax
	cmovbl	%r15d, %eax
	addl	%edx, %eax
	movl	%eax, 8(%rcx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	movq	%rcx, %rsi
	callq	*112(%rax)
	testl	%ebp, %ebp
	jne	.LBB151_1
# BB#2:                                 # %_label11
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*160(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*104(%rcx)
	addq	$8, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp445:
	.size	_Pshadow_Pstandard_Cuint_MtoString_Pshadow_Pstandard_Cuint, .Ltmp445-_Pshadow_Pstandard_Cuint_MtoString_Pshadow_Pstandard_Cuint
	.cfi_endproc

	.globl	_Pshadow_Pstandard_Cuint_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Mcreate,@function
_Pshadow_Pstandard_Cuint_Mcreate:       # @_Pshadow_Pstandard_Cuint_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cuint_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp446:
	.size	_Pshadow_Pstandard_Cuint_Mcreate, .Ltmp446-_Pshadow_Pstandard_Cuint_Mcreate

	.globl	_Pshadow_Pstandard_Culong_Mdigit_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_Mdigit_Pshadow_Pstandard_Culong,@function
_Pshadow_Pstandard_Culong_Mdigit_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Mdigit_Pshadow_Pstandard_Culong
# BB#0:
	movq	%rsi, %rax
	cmpq	$9, %rax
	ja	.LBB153_2
# BB#1:                                 # %_label0
	addl	$48, %eax
                                        # kill: EAX<def> EAX<kill> RAX<kill>
	ret
.LBB153_2:                              # %_label2
	addl	$87, %eax
                                        # kill: EAX<def> EAX<kill> RAX<kill>
	ret
.Ltmp447:
	.size	_Pshadow_Pstandard_Culong_Mdigit_Pshadow_Pstandard_Culong, .Ltmp447-_Pshadow_Pstandard_Culong_Mdigit_Pshadow_Pstandard_Culong

	.globl	_Pshadow_Pstandard_Culong_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_MtoString,@function
_Pshadow_Pstandard_Culong_MtoString:    # @_Pshadow_Pstandard_Culong_MtoString
	.cfi_startproc
# BB#0:
	pushq	%r15
.Ltmp453:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp454:
	.cfi_def_cfa_offset 24
	pushq	%r12
.Ltmp455:
	.cfi_def_cfa_offset 32
	pushq	%rbx
.Ltmp456:
	.cfi_def_cfa_offset 40
	pushq	%rax
.Ltmp457:
	.cfi_def_cfa_offset 48
.Ltmp458:
	.cfi_offset %rbx, -40
.Ltmp459:
	.cfi_offset %r12, -32
.Ltmp460:
	.cfi_offset %r14, -24
.Ltmp461:
	.cfi_offset %r15, -16
	movq	8(%rdi), %r15
	movl	_Pshadow_Pstandard_CMutableString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %r14
	movq	$_Pshadow_Pstandard_CMutableString_Mclass, (%r14)
	movq	%r14, %rdi
	callq	*_Pshadow_Pstandard_CMutableString_Mclass+64(%rip)
	movabsq	$-3689348814741910323, %r12 # imm = 0xCCCCCCCCCCCCCCCD
	.align	16, 0x90
.LBB154_1:                              # %_label3.i
                                        # =>This Inner Loop Header: Depth=1
	movq	%r15, %rax
	mulq	%r12
	movq	%rdx, %rbx
	movl	_Pshadow_Pstandard_Ccode_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Ccode_Mclass, (%rax)
	shrq	$3, %rbx
	imull	$10, %ebx, %edx
	movl	%r15d, %ecx
	subl	%edx, %ecx
	orl	$48, %ecx
	movl	%ecx, 8(%rax)
	movq	(%r14), %rcx
	movq	%r14, %rdi
	movq	%rax, %rsi
	callq	*112(%rcx)
	cmpq	$9, %r15
	movq	%rbx, %r15
	ja	.LBB154_1
# BB#2:                                 # %_Pshadow_Pstandard_Culong_MtoString_Pshadow_Pstandard_Culong.exit
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*160(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	addq	$8, %rsp
	popq	%rbx
	popq	%r12
	popq	%r14
	popq	%r15
	jmpq	*104(%rcx)  # TAILCALL
.Ltmp462:
	.size	_Pshadow_Pstandard_Culong_MtoString, .Ltmp462-_Pshadow_Pstandard_Culong_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_Culong_MtoString_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_MtoString_Pshadow_Pstandard_Culong,@function
_Pshadow_Pstandard_Culong_MtoString_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_MtoString_Pshadow_Pstandard_Culong
	.cfi_startproc
# BB#0:
	pushq	%r15
.Ltmp469:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp470:
	.cfi_def_cfa_offset 24
	pushq	%r13
.Ltmp471:
	.cfi_def_cfa_offset 32
	pushq	%r12
.Ltmp472:
	.cfi_def_cfa_offset 40
	pushq	%rbx
.Ltmp473:
	.cfi_def_cfa_offset 48
.Ltmp474:
	.cfi_offset %rbx, -48
.Ltmp475:
	.cfi_offset %r12, -40
.Ltmp476:
	.cfi_offset %r13, -32
.Ltmp477:
	.cfi_offset %r14, -24
.Ltmp478:
	.cfi_offset %r15, -16
	movq	%rsi, %r14
	movq	8(%rdi), %r12
	movl	_Pshadow_Pstandard_CMutableString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %r15
	movq	$_Pshadow_Pstandard_CMutableString_Mclass, (%r15)
	movq	%r15, %rdi
	callq	*_Pshadow_Pstandard_CMutableString_Mclass+64(%rip)
	movl	$48, %r13d
	.align	16, 0x90
.LBB155_1:                              # %_label3
                                        # =>This Inner Loop Header: Depth=1
	movq	%r12, %rax
	xorl	%edx, %edx
	divq	%r14
	movq	%rax, %r12
	movq	%rdx, %rbx
	movl	_Pshadow_Pstandard_Ccode_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Ccode_Mclass, (%rax)
	cmpq	$10, %rbx
	movl	$87, %ecx
	cmovbq	%r13, %rcx
	addl	%ebx, %ecx
	movl	%ecx, 8(%rax)
	movq	(%r15), %rcx
	movq	%r15, %rdi
	movq	%rax, %rsi
	callq	*112(%rcx)
	testq	%r12, %r12
	jne	.LBB155_1
# BB#2:                                 # %_label5
	movq	(%r15), %rax
	movq	%r15, %rdi
	callq	*160(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*104(%rcx)
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	ret
.Ltmp479:
	.size	_Pshadow_Pstandard_Culong_MtoString_Pshadow_Pstandard_Culong, .Ltmp479-_Pshadow_Pstandard_Culong_MtoString_Pshadow_Pstandard_Culong
	.cfi_endproc

	.globl	_Pshadow_Pstandard_Culong_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_Mcreate,@function
_Pshadow_Pstandard_Culong_Mcreate:      # @_Pshadow_Pstandard_Culong_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Culong_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp480:
	.size	_Pshadow_Pstandard_Culong_Mcreate, .Ltmp480-_Pshadow_Pstandard_Culong_Mcreate

	.globl	_Pshadow_Pstandard_Cushort_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cushort_Mcreate,@function
_Pshadow_Pstandard_Cushort_Mcreate:     # @_Pshadow_Pstandard_Cushort_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cushort_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp481:
	.size	_Pshadow_Pstandard_Cushort_Mcreate, .Ltmp481-_Pshadow_Pstandard_Cushort_Mcreate

	.globl	_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CObject,@function
_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CObject: # @_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp484:
	.cfi_def_cfa_offset 16
.Ltmp485:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	movl	$.L_string1370, %eax
	testq	%rsi, %rsi
	je	.LBB158_2
# BB#1:                                 # %_label1
	movq	(%rsi), %rax
	movq	%rsi, %rdi
	callq	*104(%rax)
.LBB158_2:                              # %_label2
	movl	16(%rax), %edx
	movq	8(%rax), %rsi
	movl	$2, %edi
	callq	write
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp486:
	.size	_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CObject, .Ltmp486-_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject,@function
_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject: # @_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp489:
	.cfi_def_cfa_offset 16
.Ltmp490:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	movl	$.L_string1370, %eax
	testq	%rsi, %rsi
	je	.LBB159_2
# BB#1:                                 # %_label4.i
	movq	(%rsi), %rax
	movq	%rsi, %rdi
	callq	*104(%rax)
.LBB159_2:                              # %_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject.exit
	movl	16(%rax), %edx
	movq	8(%rax), %rsi
	movl	$1, %edi
	callq	write
	movl	$1, %edi
	movl	$.Lnewline, %esi
	movl	$1, %edx
	callq	write
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp491:
	.size	_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject, .Ltmp491-_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject,@function
_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject: # @_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp494:
	.cfi_def_cfa_offset 16
.Ltmp495:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	movl	$.L_string1370, %eax
	testq	%rsi, %rsi
	je	.LBB160_2
# BB#1:                                 # %_label1.i
	movq	(%rsi), %rax
	movq	%rsi, %rdi
	callq	*104(%rax)
.LBB160_2:                              # %_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CObject.exit
	movl	16(%rax), %edx
	movq	8(%rax), %rsi
	movl	$2, %edi
	callq	write
	movl	$2, %edi
	movl	$.Lnewline, %esi
	movl	$1, %edx
	callq	write
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp496:
	.size	_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject, .Ltmp496-_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_Mcreate,@function
_Pshadow_Pio_CConsole_Mcreate:          # @_Pshadow_Pio_CConsole_Mcreate
# BB#0:
	movq	$_Pshadow_Pio_CConsole_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp497:
	.size	_Pshadow_Pio_CConsole_Mcreate, .Ltmp497-_Pshadow_Pio_CConsole_Mcreate

	.globl	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject,@function
_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject: # @_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp500:
	.cfi_def_cfa_offset 16
.Ltmp501:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	movl	$.L_string1370, %eax
	testq	%rsi, %rsi
	je	.LBB162_2
# BB#1:                                 # %_label4
	movq	(%rsi), %rax
	movq	%rsi, %rdi
	callq	*104(%rax)
.LBB162_2:                              # %_label5
	movl	16(%rax), %edx
	movq	8(%rax), %rsi
	movl	$1, %edi
	callq	write
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp502:
	.size	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject, .Ltmp502-_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Putility_CRandom_MnextUInt_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Putility_CRandom_MnextUInt_Pshadow_Pstandard_Cuint,@function
_Pshadow_Putility_CRandom_MnextUInt_Pshadow_Pstandard_Cuint: # @_Pshadow_Putility_CRandom_MnextUInt_Pshadow_Pstandard_Cuint
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp505:
	.cfi_def_cfa_offset 16
.Ltmp506:
	.cfi_offset %rbx, -16
	movl	%esi, %ebx
	movq	(%rdi), %rax
	callq	*136(%rax)
	xorl	%edx, %edx
	divl	%ebx
	movl	%edx, %eax
	popq	%rbx
	ret
.Ltmp507:
	.size	_Pshadow_Putility_CRandom_MnextUInt_Pshadow_Pstandard_Cuint, .Ltmp507-_Pshadow_Putility_CRandom_MnextUInt_Pshadow_Pstandard_Cuint
	.cfi_endproc

	.globl	_Pshadow_Putility_CRandom_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Putility_CRandom_Mcreate,@function
_Pshadow_Putility_CRandom_Mcreate:      # @_Pshadow_Putility_CRandom_Mcreate
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp510:
	.cfi_def_cfa_offset 16
	subq	$16, %rsp
.Ltmp511:
	.cfi_def_cfa_offset 32
.Ltmp512:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	movq	$_Pshadow_Putility_CRandom_Mclass, (%rbx)
	cmpq	$0, _Pshadow_Pstandard_CSystem_Minstance(%rip)
	jne	.LBB164_2
# BB#1:                                 # %_label0
	movl	_Pshadow_Pstandard_CSystem_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CSystem_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pstandard_CSystem_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pstandard_CSystem_Minstance(%rip)
.LBB164_2:                              # %_label1
	leaq	(%rsp), %rsi
	movl	$1, %edi
	callq	clock_gettime
	xorl	%esi, %esi
	testl	%eax, %eax
	jne	.LBB164_4
# BB#3:
	imulq	$1000000000, (%rsp), %rsi # imm = 0x3B9ACA00
	addq	8(%rsp), %rsi
.LBB164_4:                              # %_Pshadow_Pstandard_CSystem_MnanoTime.exit
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*152(%rax)
	movq	%rbx, %rax
	addq	$16, %rsp
	popq	%rbx
	ret
.Ltmp513:
	.size	_Pshadow_Putility_CRandom_Mcreate, .Ltmp513-_Pshadow_Putility_CRandom_Mcreate
	.cfi_endproc

	.globl	_Pshadow_Putility_CRandom_Mcreate_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Putility_CRandom_Mcreate_Pshadow_Pstandard_Culong,@function
_Pshadow_Putility_CRandom_Mcreate_Pshadow_Pstandard_Culong: # @_Pshadow_Putility_CRandom_Mcreate_Pshadow_Pstandard_Culong
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp516:
	.cfi_def_cfa_offset 16
.Ltmp517:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	movq	$_Pshadow_Putility_CRandom_Mclass, (%rbx)
	callq	*_Pshadow_Putility_CRandom_Mclass+152(%rip)
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp518:
	.size	_Pshadow_Putility_CRandom_Mcreate_Pshadow_Pstandard_Culong, .Ltmp518-_Pshadow_Putility_CRandom_Mcreate_Pshadow_Pstandard_Culong
	.cfi_endproc

	.globl	_Pshadow_Putility_CRandom_MnextInt
	.align	16, 0x90
	.type	_Pshadow_Putility_CRandom_MnextInt,@function
_Pshadow_Putility_CRandom_MnextInt:     # @_Pshadow_Putility_CRandom_MnextInt
	.cfi_startproc
# BB#0:
	movq	(%rdi), %rax
	jmpq	*136(%rax)  # TAILCALL
.Ltmp519:
	.size	_Pshadow_Putility_CRandom_MnextInt, .Ltmp519-_Pshadow_Putility_CRandom_MnextInt
	.cfi_endproc

	.globl	_Pshadow_Putility_CRandom_MnextInt_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Putility_CRandom_MnextInt_Pshadow_Pstandard_Cint,@function
_Pshadow_Putility_CRandom_MnextInt_Pshadow_Pstandard_Cint: # @_Pshadow_Putility_CRandom_MnextInt_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	movq	(%rdi), %rax
	movq	144(%rax), %rax
	jmpq	*%rax  # TAILCALL
.Ltmp520:
	.size	_Pshadow_Putility_CRandom_MnextInt_Pshadow_Pstandard_Cint, .Ltmp520-_Pshadow_Putility_CRandom_MnextInt_Pshadow_Pstandard_Cint
	.cfi_endproc

	.globl	_Pshadow_Putility_CRandom_Mseed_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Putility_CRandom_Mseed_Pshadow_Pstandard_Culong,@function
_Pshadow_Putility_CRandom_Mseed_Pshadow_Pstandard_Culong: # @_Pshadow_Putility_CRandom_Mseed_Pshadow_Pstandard_Culong
# BB#0:
	xorl	%edi, %edi
	jmp	srand                   # TAILCALL
.Ltmp521:
	.size	_Pshadow_Putility_CRandom_Mseed_Pshadow_Pstandard_Culong, .Ltmp521-_Pshadow_Putility_CRandom_Mseed_Pshadow_Pstandard_Culong

	.globl	_Pshadow_Putility_CRandom_MnextUInt
	.align	16, 0x90
	.type	_Pshadow_Putility_CRandom_MnextUInt,@function
_Pshadow_Putility_CRandom_MnextUInt:    # @_Pshadow_Putility_CRandom_MnextUInt
# BB#0:
	jmp	rand                    # TAILCALL
.Ltmp522:
	.size	_Pshadow_Putility_CRandom_MnextUInt, .Ltmp522-_Pshadow_Putility_CRandom_MnextUInt

	.globl	_P_CSortTest_McheckArray_Pshadow_Pstandard_Cint_A1
	.align	16, 0x90
	.type	_P_CSortTest_McheckArray_Pshadow_Pstandard_Cint_A1,@function
_P_CSortTest_McheckArray_Pshadow_Pstandard_Cint_A1: # @_P_CSortTest_McheckArray_Pshadow_Pstandard_Cint_A1
	.cfi_startproc
# BB#0:
	pushq	%r14
.Ltmp526:
	.cfi_def_cfa_offset 16
	pushq	%rbx
.Ltmp527:
	.cfi_def_cfa_offset 24
	pushq	%rax
.Ltmp528:
	.cfi_def_cfa_offset 32
.Ltmp529:
	.cfi_offset %rbx, -24
.Ltmp530:
	.cfi_offset %r14, -16
	movl	%edx, %ebx
	movq	%rsi, %r14
	movq	(%rdi), %rax
	callq	*168(%rax)
	testl	%ebx, %ebx
	jle	.LBB170_4
# BB#1:
	xorl	%edx, %edx
	xorb	%cl, %cl
	.align	16, 0x90
.LBB170_2:                              # %_label0
                                        # =>This Inner Loop Header: Depth=1
	movl	(%r14,%rdx,4), %esi
	cmpl	%eax, %esi
	jl	.LBB170_5
# BB#3:                                 # %_label5
                                        #   in Loop: Header=BB170_2 Depth=1
	incq	%rdx
	cmpl	%ebx, %edx
	movl	%esi, %eax
	jl	.LBB170_2
.LBB170_4:
	movb	$1, %cl
.LBB170_5:                              # %_label3
	movb	%cl, %al
	addq	$8, %rsp
	popq	%rbx
	popq	%r14
	ret
.Ltmp531:
	.size	_P_CSortTest_McheckArray_Pshadow_Pstandard_Cint_A1, .Ltmp531-_P_CSortTest_McheckArray_Pshadow_Pstandard_Cint_A1
	.cfi_endproc

	.globl	_P_CSortTest_Mstop_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_P_CSortTest_Mstop_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CString,@function
_P_CSortTest_Mstop_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CString: # @_P_CSortTest_Mstop_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp538:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp539:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp540:
	.cfi_def_cfa_offset 32
	pushq	%r12
.Ltmp541:
	.cfi_def_cfa_offset 40
	pushq	%rbx
.Ltmp542:
	.cfi_def_cfa_offset 48
	subq	$16, %rsp
.Ltmp543:
	.cfi_def_cfa_offset 64
.Ltmp544:
	.cfi_offset %rbx, -48
.Ltmp545:
	.cfi_offset %r12, -40
.Ltmp546:
	.cfi_offset %r14, -32
.Ltmp547:
	.cfi_offset %r15, -24
.Ltmp548:
	.cfi_offset %rbp, -16
	movq	%rcx, %r12
	movl	%edx, %ebp
	movq	%rsi, %rbx
	movq	%rdi, %r14
	movq	(%r14), %rax
	callq	*176(%rax)
	vmovsd	%xmm0, 8(%rsp)          # 8-byte Spill
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*168(%rax)
	testl	%ebp, %ebp
	jle	.LBB171_4
# BB#1:
	xorl	%ecx, %ecx
	.align	16, 0x90
.LBB171_2:                              # %_label0.i
                                        # =>This Inner Loop Header: Depth=1
	movl	(%rbx,%rcx,4), %edx
	cmpl	%eax, %edx
	jl	.LBB171_11
# BB#3:                                 # %_label5.i
                                        #   in Loop: Header=BB171_2 Depth=1
	incq	%rcx
	cmpl	%ebp, %ecx
	movl	%edx, %eax
	jl	.LBB171_2
.LBB171_4:                              # %_label6
	cmpq	$0, _Pshadow_Pio_CConsole_Minstance(%rip)
	jne	.LBB171_6
# BB#5:                                 # %_label9
	movl	_Pshadow_Pio_CConsole_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pio_CConsole_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pio_CConsole_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pio_CConsole_Minstance(%rip)
.LBB171_6:                              # %_label10
	movl	$.L_string1370, %r15d
	testq	%r12, %r12
	movl	$.L_string1370, %ebx
	je	.LBB171_8
# BB#7:                                 # %_label12
	movq	(%r12), %rax
	movq	%r12, %rdi
	callq	*104(%rax)
	movq	%rax, %rbx
.LBB171_8:                              # %_label15
	movq	.L_string2372(%rip), %rax
	movl	$.L_string2372, %edi
	callq	*104(%rax)
	movq	(%rbx), %rcx
	movq	%rbx, %rdi
	movq	%rax, %rsi
	callq	*128(%rcx)
	movq	%rax, %rbx
	movl	_Pshadow_Pstandard_Cdouble_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Cdouble_Mclass, (%rax)
	vmovsd	8(%rsp), %xmm0          # 8-byte Reload
	vmovsd	%xmm0, 8(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*104(%rcx)
	movq	(%rbx), %rcx
	movq	%rbx, %rdi
	movq	%rax, %rsi
	callq	*128(%rcx)
	movq	%rax, %rbx
	movq	.L_string3374(%rip), %rax
	movl	$.L_string3374, %edi
	callq	*104(%rax)
	movq	(%rbx), %rcx
	movq	%rbx, %rdi
	movq	%rax, %rsi
	callq	*128(%rcx)
	testq	%rax, %rax
	je	.LBB171_10
# BB#9:                                 # %_label4.i.i
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*104(%rcx)
	movq	%rax, %r15
.LBB171_10:                             # %_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject.exit
	movl	16(%r15), %edx
	movq	8(%r15), %rsi
	movl	$1, %edi
	callq	write
	movl	$1, %edi
	jmp	.LBB171_18
.LBB171_11:                             # %_label7
	cmpq	$0, _Pshadow_Pio_CConsole_Minstance(%rip)
	jne	.LBB171_13
# BB#12:                                # %_label20
	movl	_Pshadow_Pio_CConsole_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pio_CConsole_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pio_CConsole_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pio_CConsole_Minstance(%rip)
.LBB171_13:                             # %_label21
	movl	$.L_string1370, %r15d
	testq	%r12, %r12
	movl	$.L_string1370, %ebx
	je	.LBB171_15
# BB#14:                                # %_label23
	movq	(%r12), %rax
	movq	%r12, %rdi
	callq	*104(%rax)
	movq	%rax, %rbx
.LBB171_15:                             # %_label26
	movq	.L_string2372(%rip), %rax
	movl	$.L_string2372, %edi
	callq	*104(%rax)
	movq	(%rbx), %rcx
	movq	%rbx, %rdi
	movq	%rax, %rsi
	callq	*128(%rcx)
	movq	%rax, %rbx
	movl	_Pshadow_Pstandard_Cdouble_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Cdouble_Mclass, (%rax)
	vmovsd	8(%rsp), %xmm0          # 8-byte Reload
	vmovsd	%xmm0, 8(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*104(%rcx)
	movq	(%rbx), %rcx
	movq	%rbx, %rdi
	movq	%rax, %rsi
	callq	*128(%rcx)
	movq	%rax, %rbx
	movq	.L_string3374(%rip), %rax
	movl	$.L_string3374, %edi
	callq	*104(%rax)
	movq	(%rbx), %rcx
	movq	%rbx, %rdi
	movq	%rax, %rsi
	callq	*128(%rcx)
	testq	%rax, %rax
	je	.LBB171_17
# BB#16:                                # %_label1.i.i
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*104(%rcx)
	movq	%rax, %r15
.LBB171_17:                             # %_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject.exit
	movl	16(%r15), %edx
	movq	8(%r15), %rsi
	movl	$2, %edi
	callq	write
	movl	$2, %edi
.LBB171_18:                             # %_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject.exit
	movl	$.Lnewline, %esi
	movl	$1, %edx
	callq	write
	movq	$0, 24(%r14)
	addq	$16, %rsp
	popq	%rbx
	popq	%r12
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp549:
	.size	_P_CSortTest_Mstop_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CString, .Ltmp549-_P_CSortTest_Mstop_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CString
	.cfi_endproc

	.globl	_P_CSortTest_MgetMaximum
	.align	16, 0x90
	.type	_P_CSortTest_MgetMaximum,@function
_P_CSortTest_MgetMaximum:               # @_P_CSortTest_MgetMaximum
# BB#0:
	movl	32(%rdi), %eax
	ret
.Ltmp550:
	.size	_P_CSortTest_MgetMaximum, .Ltmp550-_P_CSortTest_MgetMaximum

	.globl	_P_CSortTest_MgetArrayLength
	.align	16, 0x90
	.type	_P_CSortTest_MgetArrayLength,@function
_P_CSortTest_MgetArrayLength:           # @_P_CSortTest_MgetArrayLength
# BB#0:
	movl	16(%rdi), %eax
	ret
.Ltmp551:
	.size	_P_CSortTest_MgetArrayLength, .Ltmp551-_P_CSortTest_MgetArrayLength

	.globl	_P_CSortTest_MgetArrayStart
	.align	16, 0x90
	.type	_P_CSortTest_MgetArrayStart,@function
_P_CSortTest_MgetArrayStart:            # @_P_CSortTest_MgetArrayStart
# BB#0:
	xorl	%eax, %eax
	ret
.Ltmp552:
	.size	_P_CSortTest_MgetArrayStart, .Ltmp552-_P_CSortTest_MgetArrayStart

	.globl	_P_CSortTest_Mrun
	.align	16, 0x90
	.type	_P_CSortTest_Mrun,@function
_P_CSortTest_Mrun:                      # @_P_CSortTest_Mrun
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp557:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp558:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp559:
	.cfi_def_cfa_offset 32
	subq	$32, %rsp
.Ltmp560:
	.cfi_def_cfa_offset 64
.Ltmp561:
	.cfi_offset %rbx, -32
.Ltmp562:
	.cfi_offset %r14, -24
.Ltmp563:
	.cfi_offset %rbp, -16
	movq	%rdi, %rbx
	cmpq	$0, _Pshadow_Pio_CConsole_Minstance(%rip)
	jne	.LBB175_2
# BB#1:                                 # %_label31
	movl	_Pshadow_Pio_CConsole_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pio_CConsole_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pio_CConsole_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pio_CConsole_Minstance(%rip)
.LBB175_2:                              # %_label32
	movq	.L_string4376(%rip), %rax
	movl	$.L_string4376, %edi
	callq	*104(%rax)
	movq	%rax, %rbp
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*144(%rax)
	movl	%eax, %r14d
	movl	_Pshadow_Pstandard_Cint_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Cint_Mclass, (%rax)
	movl	%r14d, 8(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*104(%rcx)
	movq	(%rbp), %rcx
	movq	%rbp, %rdi
	movq	%rax, %rsi
	callq	*128(%rcx)
	movq	%rax, %rcx
	movl	$.L_string1370, %eax
	testq	%rcx, %rcx
	je	.LBB175_4
# BB#3:                                 # %_label4.i.i
	movq	(%rcx), %rax
	movq	%rcx, %rdi
	callq	*104(%rax)
.LBB175_4:                              # %_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject.exit
	movl	16(%rax), %edx
	movq	8(%rax), %rsi
	movl	$1, %edi
	callq	write
	movl	$1, %edi
	movl	$.Lnewline, %esi
	movl	$1, %edx
	callq	write
	movq	%rbx, %rdi
	callq	_P_CSortTest_MquickSort
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*128(%rax)
	movq	%rax, %r14
	movl	%edx, %ebp
	cmpq	$0, _Pshadow_Pstandard_CSystem_Minstance(%rip)
	jne	.LBB175_6
# BB#5:                                 # %_label93.i.i
	movl	_Pshadow_Pstandard_CSystem_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CSystem_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pstandard_CSystem_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pstandard_CSystem_Minstance(%rip)
.LBB175_6:                              # %_label94.i.i
	leaq	(%rsp), %rsi
	movl	$1, %edi
	callq	clock_gettime
	xorl	%ecx, %ecx
	testl	%eax, %eax
	jne	.LBB175_8
# BB#7:
	imulq	$1000000000, (%rsp), %rcx # imm = 0x3B9ACA00
	addq	8(%rsp), %rcx
.LBB175_8:                              # %_P_CSortTest_MmergeSort.exit
	movq	%rcx, 24(%rbx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movl	%ebp, %edx
	callq	*192(%rax)
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movl	%ebp, %edx
	movl	$.L_string7, %ecx
	callq	_P_CSortTest_Mstop_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CString
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*128(%rax)
	movq	%rax, %rbp
	movl	%edx, %r14d
	cmpq	$0, _Pshadow_Pstandard_CSystem_Minstance(%rip)
	jne	.LBB175_10
# BB#9:                                 # %_label93.i.i1
	movl	_Pshadow_Pstandard_CSystem_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CSystem_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pstandard_CSystem_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pstandard_CSystem_Minstance(%rip)
.LBB175_10:                             # %_label94.i.i2
	leaq	16(%rsp), %rsi
	movl	$1, %edi
	callq	clock_gettime
	xorl	%ecx, %ecx
	testl	%eax, %eax
	jne	.LBB175_12
# BB#11:
	imulq	$1000000000, 16(%rsp), %rcx # imm = 0x3B9ACA00
	addq	24(%rsp), %rcx
.LBB175_12:                             # %_P_CSortTest_MheapSort.exit
	movq	%rcx, 24(%rbx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	movq	%rbp, %rsi
	movl	%r14d, %edx
	callq	*184(%rax)
	movq	%rbx, %rdi
	movq	%rbp, %rsi
	movl	%r14d, %edx
	movl	$.L_string6, %ecx
	callq	_P_CSortTest_Mstop_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CString
	movq	%rbx, %rdi
	callq	_P_CSortTest_MbucketSort
	cmpq	$0, _Pshadow_Pio_CConsole_Minstance(%rip)
	jne	.LBB175_14
# BB#13:                                # %_label36
	movl	_Pshadow_Pio_CConsole_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pio_CConsole_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pio_CConsole_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pio_CConsole_Minstance(%rip)
.LBB175_14:                             # %_label37
	movl	$1, %edi
	movl	$.Lnewline, %esi
	movl	$1, %edx
	callq	write
	addq	$32, %rsp
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp564:
	.size	_P_CSortTest_Mrun, .Ltmp564-_P_CSortTest_Mrun
	.cfi_endproc

	.globl	_P_CSortTest_Mcreate_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_P_CSortTest_Mcreate_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint,@function
_P_CSortTest_Mcreate_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint: # @_P_CSortTest_Mcreate_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp572:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp573:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp574:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp575:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp576:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp577:
	.cfi_def_cfa_offset 56
	subq	$40, %rsp
.Ltmp578:
	.cfi_def_cfa_offset 96
.Ltmp579:
	.cfi_offset %rbx, -56
.Ltmp580:
	.cfi_offset %r12, -48
.Ltmp581:
	.cfi_offset %r13, -40
.Ltmp582:
	.cfi_offset %r14, -32
.Ltmp583:
	.cfi_offset %r15, -24
.Ltmp584:
	.cfi_offset %rbp, -16
	movl	%ecx, %r14d
	movl	%edx, %r12d
	movl	%esi, %r13d
	movq	%rdi, %rbx
	movq	%rbx, 8(%rsp)           # 8-byte Spill
	movq	$_P_CSortTest_Mclass, (%rbx)
	movq	$0, 24(%rbx)
	movq	$0, 32(%rbx)
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	xorl	%r15d, %r15d
	xorl	%edi, %edi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$0, 16(%rbx)
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	%r13d, %edi
	callq	calloc
	movq	%rax, %rbp
	movl	%r13d, 32(%rsp)
	movq	%rbp, 24(%rsp)
	movl	_Pshadow_Putility_CRandom_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_Pshadow_Putility_CRandom_Mclass, (%rbx)
	movq	%rbx, %rdi
	callq	*_Pshadow_Putility_CRandom_Mclass+64(%rip)
	testl	%r13d, %r13d
	jle	.LBB176_4
# BB#1:                                 # %_label38.lr.ph
	movl	%r14d, 20(%rsp)         # 4-byte Spill
	leal	1(%r14), %r14d
	subl	%r12d, %r14d
	movl	32(%rsp), %r13d
	movq	24(%rsp), %rbp
	.align	16, 0x90
.LBB176_2:                              # %_label38
                                        # =>This Inner Loop Header: Depth=1
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	movl	%r14d, %esi
	callq	*128(%rax)
	addl	%r12d, %eax
	movslq	%r15d, %r15
	movl	%eax, (%rbp,%r15,4)
	incl	%r15d
	cmpl	%r13d, %r15d
	jl	.LBB176_2
# BB#3:
	movl	20(%rsp), %r14d         # 4-byte Reload
.LBB176_4:                              # %_label40
	movq	8(%rsp), %rax           # 8-byte Reload
	movq	%rbp, 8(%rax)
	movl	%r13d, 16(%rax)
	movl	%r12d, 36(%rax)
	movl	%r14d, 32(%rax)
	movq	$0, 24(%rax)
	addq	$40, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp585:
	.size	_P_CSortTest_Mcreate_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint, .Ltmp585-_P_CSortTest_Mcreate_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.cfi_endproc

	.globl	_P_CSortTest_MquickSort
	.align	16, 0x90
	.type	_P_CSortTest_MquickSort,@function
_P_CSortTest_MquickSort:                # @_P_CSortTest_MquickSort
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp591:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp592:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp593:
	.cfi_def_cfa_offset 32
	pushq	%rbx
.Ltmp594:
	.cfi_def_cfa_offset 40
	subq	$24, %rsp
.Ltmp595:
	.cfi_def_cfa_offset 64
.Ltmp596:
	.cfi_offset %rbx, -40
.Ltmp597:
	.cfi_offset %r14, -32
.Ltmp598:
	.cfi_offset %r15, -24
.Ltmp599:
	.cfi_offset %rbp, -16
	movq	%rdi, %rbx
	movq	(%rbx), %rax
	callq	*128(%rax)
	movq	%rax, %r14
	movl	%edx, %r15d
	cmpq	$0, _Pshadow_Pstandard_CSystem_Minstance(%rip)
	jne	.LBB177_2
# BB#1:                                 # %_label93.i
	movl	_Pshadow_Pstandard_CSystem_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CSystem_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pstandard_CSystem_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pstandard_CSystem_Minstance(%rip)
.LBB177_2:                              # %_label94.i
	leaq	8(%rsp), %rsi
	movl	$1, %edi
	callq	clock_gettime
	xorl	%ecx, %ecx
	testl	%eax, %eax
	jne	.LBB177_4
# BB#3:
	imulq	$1000000000, 8(%rsp), %rcx # imm = 0x3B9ACA00
	addq	16(%rsp), %rcx
.LBB177_4:                              # %_P_CSortTest_Mstart.exit
	movq	%rcx, 24(%rbx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*144(%rax)
	movq	(%rbx), %rbp
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movl	%r15d, %edx
	xorl	%ecx, %ecx
	movl	%eax, %r8d
	callq	*200(%rbp)
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movl	%r15d, %edx
	movl	$.L_string5, %ecx
	callq	_P_CSortTest_Mstop_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CString
	addq	$24, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp600:
	.size	_P_CSortTest_MquickSort, .Ltmp600-_P_CSortTest_MquickSort
	.cfi_endproc

	.globl	_P_CSortTest_MquickSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_P_CSortTest_MquickSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint,@function
_P_CSortTest_MquickSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint: # @_P_CSortTest_MquickSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp606:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp607:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp608:
	.cfi_def_cfa_offset 32
	pushq	%rbx
.Ltmp609:
	.cfi_def_cfa_offset 40
	subq	$56, %rsp
.Ltmp610:
	.cfi_def_cfa_offset 96
.Ltmp611:
	.cfi_offset %rbx, -40
.Ltmp612:
	.cfi_offset %r14, -32
.Ltmp613:
	.cfi_offset %r15, -24
.Ltmp614:
	.cfi_offset %rbp, -16
	movq	%rdi, 48(%rsp)
	movl	%edx, 40(%rsp)
	movq	%rsi, 32(%rsp)
	movl	%ecx, 28(%rsp)
	movl	%r8d, 24(%rsp)
	movl	%r8d, %eax
	subl	%ecx, %eax
	cmpl	$2, %eax
	jl	.LBB178_7
# BB#1:                                 # %_label41
	movslq	%ecx, %rax
	movq	32(%rsp), %rdx
	movl	(%rdx,%rax,4), %esi
	movl	%esi, 20(%rsp)
	movl	%ecx, 16(%rsp)
	leal	1(%rcx), %edx
	movl	%edx, 12(%rsp)
	movq	32(%rsp), %rax
	jmp	.LBB178_2
	.align	16, 0x90
.LBB178_5:                              # %_label49
                                        #   in Loop: Header=BB178_2 Depth=1
	incl	%edx
	movl	%edx, 12(%rsp)
	movl	20(%rsp), %esi
.LBB178_2:                              # %_label41
                                        # =>This Inner Loop Header: Depth=1
	cmpl	%r8d, %edx
	jge	.LBB178_6
# BB#3:                                 # %_label44
                                        #   in Loop: Header=BB178_2 Depth=1
	movslq	%edx, %rdi
	movl	(%rax,%rdi,4), %edi
	cmpl	%esi, %edi
	jge	.LBB178_5
# BB#4:                                 # %_label47
                                        #   in Loop: Header=BB178_2 Depth=1
	movslq	%ecx, %rcx
	movl	%edi, (%rax,%rcx,4)
	movl	16(%rsp), %ecx
	incl	%ecx
	movl	%ecx, 16(%rsp)
	movslq	%ecx, %rdx
	movq	32(%rsp), %rax
	movl	(%rax,%rdx,4), %esi
	movslq	12(%rsp), %rdx
	movl	%esi, (%rax,%rdx,4)
	movq	32(%rsp), %rax
	movl	24(%rsp), %r8d
	jmp	.LBB178_5
.LBB178_6:                              # %_label46
	movslq	%ecx, %rcx
	movl	%esi, (%rax,%rcx,4)
	movq	48(%rsp), %rbx
	movq	(%rbx), %rax
	movl	40(%rsp), %r14d
	movq	32(%rsp), %r15
	movl	16(%rsp), %ebp
	movl	28(%rsp), %ecx
	movq	%rbx, %rdi
	movq	%r15, %rsi
	movl	%r14d, %edx
	movl	%ebp, %r8d
	callq	*200(%rax)
	movq	(%rbx), %rax
	incl	%ebp
	movl	24(%rsp), %r8d
	movq	%rbx, %rdi
	movq	%r15, %rsi
	movl	%r14d, %edx
	movl	%ebp, %ecx
	callq	*200(%rax)
.LBB178_7:                              # %_label43
	addq	$56, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp615:
	.size	_P_CSortTest_MquickSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint, .Ltmp615-_P_CSortTest_MquickSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.cfi_endproc

	.globl	_P_CSortTest_MheapSort
	.align	16, 0x90
	.type	_P_CSortTest_MheapSort,@function
_P_CSortTest_MheapSort:                 # @_P_CSortTest_MheapSort
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp620:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp621:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp622:
	.cfi_def_cfa_offset 32
	subq	$16, %rsp
.Ltmp623:
	.cfi_def_cfa_offset 48
.Ltmp624:
	.cfi_offset %rbx, -32
.Ltmp625:
	.cfi_offset %r14, -24
.Ltmp626:
	.cfi_offset %rbp, -16
	movq	%rdi, %rbx
	movq	(%rbx), %rax
	callq	*128(%rax)
	movq	%rax, %r14
	movl	%edx, %ebp
	cmpq	$0, _Pshadow_Pstandard_CSystem_Minstance(%rip)
	jne	.LBB179_2
# BB#1:                                 # %_label93.i
	movl	_Pshadow_Pstandard_CSystem_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CSystem_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pstandard_CSystem_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pstandard_CSystem_Minstance(%rip)
.LBB179_2:                              # %_label94.i
	leaq	(%rsp), %rsi
	movl	$1, %edi
	callq	clock_gettime
	xorl	%ecx, %ecx
	testl	%eax, %eax
	jne	.LBB179_4
# BB#3:
	imulq	$1000000000, (%rsp), %rcx # imm = 0x3B9ACA00
	addq	8(%rsp), %rcx
.LBB179_4:                              # %_P_CSortTest_Mstart.exit
	movq	%rcx, 24(%rbx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movl	%ebp, %edx
	callq	*184(%rax)
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movl	%ebp, %edx
	movl	$.L_string6, %ecx
	callq	_P_CSortTest_Mstop_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CString
	addq	$16, %rsp
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp627:
	.size	_P_CSortTest_MheapSort, .Ltmp627-_P_CSortTest_MheapSort
	.cfi_endproc

	.globl	_P_CSortTest_MheapSort_Pshadow_Pstandard_Cint_A1
	.align	16, 0x90
	.type	_P_CSortTest_MheapSort_Pshadow_Pstandard_Cint_A1,@function
_P_CSortTest_MheapSort_Pshadow_Pstandard_Cint_A1: # @_P_CSortTest_MheapSort_Pshadow_Pstandard_Cint_A1
# BB#0:
	movl	%edx, -8(%rsp)
	movq	%rsi, -16(%rsp)
	movl	-8(%rsp), %r9d
	cmpl	$2, %r9d
	jl	.LBB180_6
# BB#1:                                 # %_label54.preheader.lr.ph
	movl	$1, %r8d
	movq	-16(%rsp), %r10
	.align	16, 0x90
.LBB180_4:                              # %_label54.preheader
                                        # =>This Loop Header: Depth=1
                                        #     Child Loop BB180_2 Depth 2
	leal	-1(%r8), %esi
	movl	%esi, %eax
	shrl	$31, %eax
	leal	-1(%r8,%rax), %eax
	movslq	%r8d, %rcx
	sarl	%eax
	movslq	%eax, %rdx
	movl	(%r10,%rcx,4), %eax
	movl	(%r10,%rdx,4), %edi
	cmpl	%eax, %edi
	jge	.LBB180_3
# BB#5:                                 #   in Loop: Header=BB180_4 Depth=1
	leaq	(%r10,%rcx,4), %rcx
	leaq	(%r10,%rdx,4), %rdx
	.align	16, 0x90
.LBB180_2:                              # %_label53
                                        #   Parent Loop BB180_4 Depth=1
                                        # =>  This Inner Loop Header: Depth=2
	movl	%eax, (%rdx)
	movl	%esi, %eax
	shrl	$31, %eax
	addl	%esi, %eax
	sarl	%eax
	movl	%edi, (%rcx)
	leal	-1(%rax), %esi
	movl	%esi, %ecx
	shrl	$31, %ecx
	leal	-1(%rax,%rcx), %ecx
	sarl	%ecx
	movslq	%ecx, %rcx
	leaq	(%r10,%rcx,4), %rdx
	movl	(%r10,%rcx,4), %edi
	movslq	%eax, %rax
	leaq	(%r10,%rax,4), %rcx
	movl	(%r10,%rax,4), %eax
	cmpl	%eax, %edi
	jl	.LBB180_2
.LBB180_3:                              # %_label55
                                        #   in Loop: Header=BB180_4 Depth=1
	incl	%r8d
	cmpl	%r9d, %r8d
	jl	.LBB180_4
.LBB180_6:                              # %_label57.preheader
	decl	%r9d
	js	.LBB180_15
# BB#7:                                 # %_label56.lr.ph
	movq	-16(%rsp), %r10
	.align	16, 0x90
.LBB180_8:                              # %_label56
                                        # =>This Loop Header: Depth=1
                                        #     Child Loop BB180_9 Depth 2
	movslq	%r9d, %rax
	movl	(%r10,%rax,4), %r8d
	movl	(%r10), %eax
	xorl	%edi, %edi
	movl	%r9d, %esi
	.align	16, 0x90
.LBB180_9:                              # %_label59
                                        #   Parent Loop BB180_8 Depth=1
                                        # =>  This Inner Loop Header: Depth=2
	movl	%edi, %edx
	movslq	%esi, %rsi
	movl	%eax, (%r10,%rsi,4)
	leal	1(%rdx,%rdx), %eax
	leal	2(%rdx,%rdx), %edi
	cmpl	%r9d, %edi
	jge	.LBB180_11
# BB#10:                                # %_label65
                                        #   in Loop: Header=BB180_9 Depth=2
	movslq	%eax, %rsi
	movl	4(%r10,%rsi,4), %ecx
	cmpl	(%r10,%rsi,4), %ecx
	jg	.LBB180_12
.LBB180_11:                             # %select.mid
                                        #   in Loop: Header=BB180_9 Depth=2
	movl	%eax, %edi
.LBB180_12:                             # %_label60
                                        #   in Loop: Header=BB180_9 Depth=2
	cmpl	%r9d, %edi
	jge	.LBB180_14
# BB#13:                                # %_label67
                                        #   in Loop: Header=BB180_9 Depth=2
	movslq	%edi, %rax
	movl	(%r10,%rax,4), %eax
	cmpl	%r8d, %eax
	movl	%edx, %esi
	jg	.LBB180_9
.LBB180_14:                             # %_label61
                                        #   in Loop: Header=BB180_8 Depth=1
	movslq	%edx, %rax
	movl	%r8d, (%r10,%rax,4)
	decl	%r9d
	jns	.LBB180_8
.LBB180_15:                             # %_label58
	ret
.Ltmp628:
	.size	_P_CSortTest_MheapSort_Pshadow_Pstandard_Cint_A1, .Ltmp628-_P_CSortTest_MheapSort_Pshadow_Pstandard_Cint_A1

	.globl	_P_CSortTest_MgetArray
	.align	16, 0x90
	.type	_P_CSortTest_MgetArray,@function
_P_CSortTest_MgetArray:                 # @_P_CSortTest_MgetArray
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp633:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp634:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp635:
	.cfi_def_cfa_offset 32
.Ltmp636:
	.cfi_offset %rbx, -32
.Ltmp637:
	.cfi_offset %r14, -24
.Ltmp638:
	.cfi_offset %rbp, -16
	movq	8(%rdi), %r14
	movl	16(%rdi), %ebp
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbx
	movl	%ebp, (%rbx)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Cint_Mclass, 24(%rax)
	movq	%rbx, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r14, 32(%rax)
	movq	%rax, %rdi
	callq	_Pshadow_Pstandard_CArray_Mcopy
	movq	8(%rax), %rcx
	movq	32(%rax), %rax
	movl	(%rcx), %edx
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp639:
	.size	_P_CSortTest_MgetArray, .Ltmp639-_P_CSortTest_MgetArray
	.cfi_endproc

	.globl	_P_CSortTest_MmergeSort
	.align	16, 0x90
	.type	_P_CSortTest_MmergeSort,@function
_P_CSortTest_MmergeSort:                # @_P_CSortTest_MmergeSort
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp644:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp645:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp646:
	.cfi_def_cfa_offset 32
	subq	$16, %rsp
.Ltmp647:
	.cfi_def_cfa_offset 48
.Ltmp648:
	.cfi_offset %rbx, -32
.Ltmp649:
	.cfi_offset %r14, -24
.Ltmp650:
	.cfi_offset %rbp, -16
	movq	%rdi, %rbx
	movq	(%rbx), %rax
	callq	*128(%rax)
	movq	%rax, %r14
	movl	%edx, %ebp
	cmpq	$0, _Pshadow_Pstandard_CSystem_Minstance(%rip)
	jne	.LBB182_2
# BB#1:                                 # %_label93.i
	movl	_Pshadow_Pstandard_CSystem_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CSystem_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pstandard_CSystem_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pstandard_CSystem_Minstance(%rip)
.LBB182_2:                              # %_label94.i
	leaq	(%rsp), %rsi
	movl	$1, %edi
	callq	clock_gettime
	xorl	%ecx, %ecx
	testl	%eax, %eax
	jne	.LBB182_4
# BB#3:
	imulq	$1000000000, (%rsp), %rcx # imm = 0x3B9ACA00
	addq	8(%rsp), %rcx
.LBB182_4:                              # %_P_CSortTest_Mstart.exit
	movq	%rcx, 24(%rbx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movl	%ebp, %edx
	callq	*192(%rax)
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movl	%ebp, %edx
	movl	$.L_string7, %ecx
	callq	_P_CSortTest_Mstop_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CString
	addq	$16, %rsp
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp651:
	.size	_P_CSortTest_MmergeSort, .Ltmp651-_P_CSortTest_MmergeSort
	.cfi_endproc

	.globl	_P_CSortTest_MmergeSort_Pshadow_Pstandard_Cint_A1
	.align	16, 0x90
	.type	_P_CSortTest_MmergeSort_Pshadow_Pstandard_Cint_A1,@function
_P_CSortTest_MmergeSort_Pshadow_Pstandard_Cint_A1: # @_P_CSortTest_MmergeSort_Pshadow_Pstandard_Cint_A1
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp659:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp660:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp661:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp662:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp663:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp664:
	.cfi_def_cfa_offset 56
	subq	$72, %rsp
.Ltmp665:
	.cfi_def_cfa_offset 128
.Ltmp666:
	.cfi_offset %rbx, -56
.Ltmp667:
	.cfi_offset %r12, -48
.Ltmp668:
	.cfi_offset %r13, -40
.Ltmp669:
	.cfi_offset %r14, -32
.Ltmp670:
	.cfi_offset %r15, -24
.Ltmp671:
	.cfi_offset %rbp, -16
	movl	%edx, %r12d
	movq	%rsi, %r15
	movq	%rdi, 16(%rsp)          # 8-byte Spill
	movl	%r12d, 64(%rsp)
	movq	%r15, 56(%rsp)
	cmpl	$2, %r12d
	jl	.LBB183_11
# BB#1:                                 # %_label69
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %r13d
	movl	$1, %edi
	movl	%r13d, %esi
	callq	calloc
	movq	%rax, %rbp
	movl	%r12d, (%rbp)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %r14d
	movl	%r14d, %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbx)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbx)
	movq	$_Pshadow_Pstandard_Cint_Mclass, 24(%rbx)
	movq	%rbp, 8(%rbx)
	movl	$1, 16(%rbx)
	movq	%r15, 32(%rbx)
	movl	%r14d, %edi
	callq	malloc
	movq	%rax, %rbp
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbp)
	movq	24(%rbx), %rax
	movq	%rax, (%rsp)            # 8-byte Spill
	movl	16(%rbx), %r14d
	movl	%r14d, %edi
	movl	%r13d, %esi
	callq	calloc
	movq	%rax, %r13
	movl	%r12d, %eax
	sarl	%eax
	movl	%eax, 12(%rsp)          # 4-byte Spill
	movl	%eax, (%r13)
	movq	8(%rbx), %rsi
	leal	-1(%r14), %edx
	addq	$4, %rsi
	leaq	4(%r13), %rdi
	callq	memcpy
	movq	32(%rbx), %rsi
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbp)
	movq	(%rsp), %rax            # 8-byte Reload
	movq	%rax, 24(%rbp)
	movl	%r14d, 16(%rbp)
	movq	%r13, 8(%rbp)
	movq	%rsi, 32(%rbp)
	movq	16(%rsp), %rdi          # 8-byte Reload
	movq	(%rdi), %rax
	movq	8(%rbp), %rcx
	movl	(%rcx), %edx
	callq	*192(%rax)
	movl	%edx, 48(%rsp)
	movq	%rax, 40(%rsp)
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %r13d
	movl	$1, %edi
	movl	%r13d, %esi
	callq	calloc
	movq	%rax, %rbp
	movl	%r12d, (%rbp)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %r14d
	movl	%r14d, %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbx)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbx)
	movq	$_Pshadow_Pstandard_Cint_Mclass, 24(%rbx)
	movq	%rbp, 8(%rbx)
	movl	$1, 16(%rbx)
	movq	%r15, 32(%rbx)
	movl	%r14d, %edi
	callq	malloc
	movq	%rax, %rbp
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbp)
	movq	24(%rbx), %rax
	movq	%rax, (%rsp)            # 8-byte Spill
	movl	16(%rbx), %r15d
	movl	%r15d, %edi
	movl	%r13d, %esi
	callq	calloc
	movq	%rax, %r14
	movl	12(%rsp), %r13d         # 4-byte Reload
	subl	%r13d, %r12d
	movl	%r12d, (%r14)
	leaq	4(%r14), %rdi
	movq	8(%rbx), %rsi
	leal	-1(%r15), %edx
	addq	$4, %rsi
	callq	memcpy
	movl	%r13d, %ecx
	movq	(%rsp), %rdx            # 8-byte Reload
	imull	48(%rdx), %ecx
	movq	32(%rbx), %rax
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbp)
	movq	%rdx, 24(%rbp)
	movl	%r15d, 16(%rbp)
	movq	%r14, 8(%rbp)
	movslq	%ecx, %rsi
	addq	%rax, %rsi
	movq	%rsi, 32(%rbp)
	movq	16(%rsp), %rdi          # 8-byte Reload
	movq	(%rdi), %rax
	movq	8(%rbp), %rcx
	movl	(%rcx), %edx
	callq	*192(%rax)
	movl	%edx, 32(%rsp)
	movq	%rax, 24(%rsp)
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	64(%rsp), %r12d
	movl	%r12d, %edi
	callq	calloc
	movq	%rax, %r15
	movl	%r12d, 64(%rsp)
	movq	%r15, 56(%rsp)
	movl	64(%rsp), %r8d
	testl	%r8d, %r8d
	jle	.LBB183_11
# BB#2:                                 # %_label72.lr.ph
	xorl	%edi, %edi
	xorl	%ecx, %ecx
	movq	40(%rsp), %r9
	movl	32(%rsp), %r11d
	movq	56(%rsp), %r10
	movq	24(%rsp), %r14
	movl	48(%rsp), %eax
	movl	%edi, %esi
	xorl	%ebp, %ebp
	xorl	%edx, %edx
	.align	16, 0x90
.LBB183_3:                              # %_label72
                                        # =>This Inner Loop Header: Depth=1
	cmpl	%eax, %edx
	jne	.LBB183_5
# BB#4:                                 # %_label72._label76_crit_edge
                                        #   in Loop: Header=BB183_3 Depth=1
	movslq	%ebp, %rdx
	movl	(%r14,%rdx,4), %ebx
	movl	%ebp, %edi
	movl	%eax, %edx
	jmp	.LBB183_8
	.align	16, 0x90
.LBB183_5:                              # %_label79
                                        #   in Loop: Header=BB183_3 Depth=1
	cmpl	%r11d, %ebp
	movl	%r11d, %ebp
	je	.LBB183_12
# BB#6:                                 # %_label78
                                        #   in Loop: Header=BB183_3 Depth=1
	movslq	%edi, %rdx
	movl	(%r14,%rdx,4), %ebx
	movslq	%esi, %rdx
	cmpl	%ebx, (%r9,%rdx,4)
	movl	%edi, %ebp
	jge	.LBB183_7
.LBB183_12:                             # %_label75
                                        #   in Loop: Header=BB183_3 Depth=1
	movslq	%esi, %rdx
	movl	(%r9,%rdx,4), %edx
	movl	%edx, (%r10,%rcx,4)
	incl	%esi
	movl	%esi, %edx
	jmp	.LBB183_9
.LBB183_7:                              #   in Loop: Header=BB183_3 Depth=1
	movl	%esi, %edx
	.align	16, 0x90
.LBB183_8:                              # %_label76
                                        #   in Loop: Header=BB183_3 Depth=1
	movl	%ebx, (%r10,%rcx,4)
	incl	%edi
	movl	%edi, %ebp
.LBB183_9:                              # %_label77
                                        #   in Loop: Header=BB183_3 Depth=1
	incq	%rcx
	cmpl	%r8d, %ecx
	jl	.LBB183_3
# BB#10:                                # %_label73._label71.loopexit_crit_edge
	movl	64(%rsp), %r12d
	movq	56(%rsp), %r15
.LBB183_11:                             # %_label71
	movq	%r15, %rax
	movl	%r12d, %edx
	addq	$72, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp672:
	.size	_P_CSortTest_MmergeSort_Pshadow_Pstandard_Cint_A1, .Ltmp672-_P_CSortTest_MmergeSort_Pshadow_Pstandard_Cint_A1
	.cfi_endproc

	.globl	_P_CSortTest_MgetMinimum
	.align	16, 0x90
	.type	_P_CSortTest_MgetMinimum,@function
_P_CSortTest_MgetMinimum:               # @_P_CSortTest_MgetMinimum
# BB#0:
	movl	36(%rdi), %eax
	ret
.Ltmp673:
	.size	_P_CSortTest_MgetMinimum, .Ltmp673-_P_CSortTest_MgetMinimum

	.globl	_P_CSortTest_MbucketSort
	.align	16, 0x90
	.type	_P_CSortTest_MbucketSort,@function
_P_CSortTest_MbucketSort:               # @_P_CSortTest_MbucketSort
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp680:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp681:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp682:
	.cfi_def_cfa_offset 32
	pushq	%r12
.Ltmp683:
	.cfi_def_cfa_offset 40
	pushq	%rbx
.Ltmp684:
	.cfi_def_cfa_offset 48
	subq	$16, %rsp
.Ltmp685:
	.cfi_def_cfa_offset 64
.Ltmp686:
	.cfi_offset %rbx, -48
.Ltmp687:
	.cfi_offset %r12, -40
.Ltmp688:
	.cfi_offset %r14, -32
.Ltmp689:
	.cfi_offset %r15, -24
.Ltmp690:
	.cfi_offset %rbp, -16
	movq	%rdi, %rbx
	movq	(%rbx), %rax
	callq	*128(%rax)
	movq	%rax, %r14
	movl	%edx, %r15d
	cmpq	$0, _Pshadow_Pstandard_CSystem_Minstance(%rip)
	jne	.LBB185_2
# BB#1:                                 # %_label93.i
	movl	_Pshadow_Pstandard_CSystem_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CSystem_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pstandard_CSystem_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pstandard_CSystem_Minstance(%rip)
.LBB185_2:                              # %_label94.i
	leaq	(%rsp), %rsi
	movl	$1, %edi
	callq	clock_gettime
	xorl	%ecx, %ecx
	testl	%eax, %eax
	jne	.LBB185_4
# BB#3:
	imulq	$1000000000, (%rsp), %rcx # imm = 0x3B9ACA00
	addq	8(%rsp), %rcx
.LBB185_4:                              # %_P_CSortTest_Mstart.exit
	movq	%rcx, 24(%rbx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*168(%rax)
	movl	%eax, %r12d
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*160(%rax)
	movq	(%rbx), %rbp
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movl	%r15d, %edx
	movl	%r12d, %ecx
	movl	%eax, %r8d
	callq	*112(%rbp)
	movq	%rbx, %rdi
	movq	%r14, %rsi
	movl	%r15d, %edx
	movl	$.L_string8, %ecx
	callq	_P_CSortTest_Mstop_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CString
	addq	$16, %rsp
	popq	%rbx
	popq	%r12
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp691:
	.size	_P_CSortTest_MbucketSort, .Ltmp691-_P_CSortTest_MbucketSort
	.cfi_endproc

	.globl	_P_CSortTest_MbucketSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_P_CSortTest_MbucketSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint,@function
_P_CSortTest_MbucketSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint: # @_P_CSortTest_MbucketSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
# BB#0:
	pushq	%rbp
	pushq	%r15
	pushq	%r14
	pushq	%rbx
	subq	$40, %rsp
	movl	%r8d, %r14d
	movl	%ecx, %ebx
	movl	%edx, %ebp
	movq	%rsi, %r15
	movl	%ebp, 32(%rsp)
	movq	%r15, 24(%rsp)
	subl	%ebx, %r14d
	incl	%r14d
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	%r14d, %edi
	callq	calloc
	movl	%r14d, 16(%rsp)
	movq	%rax, 8(%rsp)
	testl	%ebp, %ebp
	jle	.LBB186_4
# BB#1:                                 # %_label82.lr.ph
	xorl	%eax, %eax
	movq	8(%rsp), %rcx
	.align	16, 0x90
.LBB186_2:                              # %_label82
                                        # =>This Inner Loop Header: Depth=1
	movl	(%r15,%rax,4), %edx
	subl	%ebx, %edx
	movslq	%edx, %rdx
	incl	(%rcx,%rdx,4)
	incq	%rax
	cmpl	%ebp, %eax
	jl	.LBB186_2
# BB#3:                                 # %_label83._label84_crit_edge
	movl	16(%rsp), %r14d
	movq	8(%rsp), %rax
.LBB186_4:                              # %_label84
	testl	%r14d, %r14d
	jle	.LBB186_11
# BB#5:                                 # %_label85.lr.ph
	xorl	%edx, %edx
	movl	$-1, %r8d
	movl	%edx, %esi
	.align	16, 0x90
.LBB186_6:                              # %_label85
                                        # =>This Loop Header: Depth=1
                                        #     Child Loop BB186_8 Depth 2
	movslq	%esi, %rcx
	movl	(%rax,%rcx,4), %edi
	testl	%edi, %edi
	jle	.LBB186_10
# BB#7:                                 # %_label88.lr.ph
                                        #   in Loop: Header=BB186_6 Depth=1
	movl	%edi, %ecx
	negl	%ecx
	cmpl	$-2, %ecx
	cmovlel	%r8d, %ecx
	leal	(%rdi,%rdx), %ebp
	leal	1(%rcx,%rbp), %r9d
	movq	24(%rsp), %rcx
	.align	16, 0x90
.LBB186_8:                              # %_label88
                                        #   Parent Loop BB186_6 Depth=1
                                        # =>  This Inner Loop Header: Depth=2
	leal	(%rsi,%rbx), %ebp
	movslq	%edx, %rdx
	movl	%ebp, (%rcx,%rdx,4)
	incl	%edx
	decl	%edi
	testl	%edi, %edi
	jg	.LBB186_8
# BB#9:                                 #   in Loop: Header=BB186_6 Depth=1
	movl	%r9d, %edx
.LBB186_10:                             # %_label90
                                        #   in Loop: Header=BB186_6 Depth=1
	incl	%esi
	cmpl	%r14d, %esi
	jl	.LBB186_6
.LBB186_11:                             # %_label87
	addq	$40, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp692:
	.size	_P_CSortTest_MbucketSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint, .Ltmp692-_P_CSortTest_MbucketSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint

	.section	.rodata.cst16,"aM",@progbits,16
	.align	16
.LCPI187_0:
	.long	1127219200              # 0x43300000
	.long	1160773632              # 0x45300000
	.long	0                       # 0x0
	.long	0                       # 0x0
.LCPI187_1:
	.quad	4841369599423283200     # double 4.503600e+15
	.quad	4985484787499139072     # double 1.934281e+25
	.section	.rodata.cst8,"aM",@progbits,8
	.align	8
.LCPI187_2:
	.quad	4741671816366391296     # double 1.000000e+09
                                        #  (0x41cdcd6500000000)
	.text
	.globl	_P_CSortTest_MgetTime
	.align	16, 0x90
	.type	_P_CSortTest_MgetTime,@function
_P_CSortTest_MgetTime:                  # @_P_CSortTest_MgetTime
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp695:
	.cfi_def_cfa_offset 16
	subq	$16, %rsp
.Ltmp696:
	.cfi_def_cfa_offset 32
.Ltmp697:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	cmpq	$0, _Pshadow_Pstandard_CSystem_Minstance(%rip)
	jne	.LBB187_2
# BB#1:                                 # %_label91
	movl	_Pshadow_Pstandard_CSystem_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CSystem_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pstandard_CSystem_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pstandard_CSystem_Minstance(%rip)
.LBB187_2:                              # %_label92
	leaq	(%rsp), %rsi
	movl	$1, %edi
	callq	clock_gettime
	xorl	%ecx, %ecx
	testl	%eax, %eax
	jne	.LBB187_4
# BB#3:
	imulq	$1000000000, (%rsp), %rcx # imm = 0x3B9ACA00
	addq	8(%rsp), %rcx
.LBB187_4:                              # %_Pshadow_Pstandard_CSystem_MnanoTime.exit
	subq	24(%rbx), %rcx
	vmovd	%rcx, %xmm0
	vpunpckldq	.LCPI187_0(%rip), %xmm0, %xmm0 # xmm0 = xmm0[0],mem[0],xmm0[1],mem[1]
	vsubpd	.LCPI187_1(%rip), %xmm0, %xmm0
	vhaddpd	%xmm0, %xmm0, %xmm0
	vdivsd	.LCPI187_2(%rip), %xmm0, %xmm0
	addq	$16, %rsp
	popq	%rbx
	ret
.Ltmp698:
	.size	_P_CSortTest_MgetTime, .Ltmp698-_P_CSortTest_MgetTime
	.cfi_endproc

	.globl	_P_CSortTest_Mstart
	.align	16, 0x90
	.type	_P_CSortTest_Mstart,@function
_P_CSortTest_Mstart:                    # @_P_CSortTest_Mstart
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp701:
	.cfi_def_cfa_offset 16
	subq	$16, %rsp
.Ltmp702:
	.cfi_def_cfa_offset 32
.Ltmp703:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	cmpq	$0, _Pshadow_Pstandard_CSystem_Minstance(%rip)
	jne	.LBB188_2
# BB#1:                                 # %_label93
	movl	_Pshadow_Pstandard_CSystem_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CSystem_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pstandard_CSystem_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pstandard_CSystem_Minstance(%rip)
.LBB188_2:                              # %_label94
	leaq	(%rsp), %rsi
	movl	$1, %edi
	callq	clock_gettime
	xorl	%ecx, %ecx
	testl	%eax, %eax
	jne	.LBB188_4
# BB#3:
	imulq	$1000000000, (%rsp), %rcx # imm = 0x3B9ACA00
	addq	8(%rsp), %rcx
.LBB188_4:                              # %_Pshadow_Pstandard_CSystem_MnanoTime.exit
	movq	%rcx, 24(%rbx)
	addq	$16, %rsp
	popq	%rbx
	ret
.Ltmp704:
	.size	_P_CSortTest_Mstart, .Ltmp704-_P_CSortTest_Mstart
	.cfi_endproc

	.globl	_P_CSortTest_MgetArrayEnd
	.align	16, 0x90
	.type	_P_CSortTest_MgetArrayEnd,@function
_P_CSortTest_MgetArrayEnd:              # @_P_CSortTest_MgetArrayEnd
# BB#0:
	movl	16(%rdi), %eax
	ret
.Ltmp705:
	.size	_P_CSortTest_MgetArrayEnd, .Ltmp705-_P_CSortTest_MgetArrayEnd

	.globl	_Pshadow_Ptest_CSortMain_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Ptest_CSortMain_Mcreate,@function
_Pshadow_Ptest_CSortMain_Mcreate:       # @_Pshadow_Ptest_CSortMain_Mcreate
# BB#0:
	movq	$_Pshadow_Ptest_CSortMain_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp706:
	.size	_Pshadow_Ptest_CSortMain_Mcreate, .Ltmp706-_Pshadow_Ptest_CSortMain_Mcreate

	.globl	_Pshadow_Ptest_CSortMain_Mmain_Pshadow_Pstandard_CString_A1
	.align	16, 0x90
	.type	_Pshadow_Ptest_CSortMain_Mmain_Pshadow_Pstandard_CString_A1,@function
_Pshadow_Ptest_CSortMain_Mmain_Pshadow_Pstandard_CString_A1: # @_Pshadow_Ptest_CSortMain_Mmain_Pshadow_Pstandard_CString_A1
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp709:
	.cfi_def_cfa_offset 16
.Ltmp710:
	.cfi_offset %rbx, -16
	movl	_P_CSortTest_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_P_CSortTest_Mclass, (%rbx)
	movq	%rbx, %rdi
	movl	$10000, %esi            # imm = 0x2710
	movl	$-10000, %edx           # imm = 0xFFFFFFFFFFFFD8F0
	movl	$10000, %ecx            # imm = 0x2710
	callq	*_P_CSortTest_Mclass+120(%rip)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*208(%rax)
	movl	_P_CSortTest_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_P_CSortTest_Mclass, (%rbx)
	movq	%rbx, %rdi
	movl	$100000, %esi           # imm = 0x186A0
	movl	$-10000, %edx           # imm = 0xFFFFFFFFFFFFD8F0
	movl	$10000, %ecx            # imm = 0x2710
	callq	*_P_CSortTest_Mclass+120(%rip)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*208(%rax)
	movl	_P_CSortTest_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_P_CSortTest_Mclass, (%rbx)
	movq	%rbx, %rdi
	movl	$1000000, %esi          # imm = 0xF4240
	movl	$-10000, %edx           # imm = 0xFFFFFFFFFFFFD8F0
	movl	$10000, %ecx            # imm = 0x2710
	callq	*_P_CSortTest_Mclass+120(%rip)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*208(%rax)
	xorl	%eax, %eax
	popq	%rbx
	ret
.Ltmp711:
	.size	_Pshadow_Ptest_CSortMain_Mmain_Pshadow_Pstandard_CString_A1, .Ltmp711-_Pshadow_Ptest_CSortMain_Mmain_Pshadow_Pstandard_CString_A1
	.cfi_endproc

	.type	.Lnewline,@object       # @newline
	.section	.rodata,"a",@progbits
.Lnewline:
	.byte	10
	.size	.Lnewline, 1

	.type	_Pshadow_Pstandard_CArray_Mclass,@object # @_Pshadow_Pstandard_CArray_Mclass
	.data
	.globl	_Pshadow_Pstandard_CArray_Mclass
	.align	16
_Pshadow_Pstandard_CArray_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	0                       # 0x0
	.long	(0+40)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CArray_Mcopy
	.quad	_Pshadow_Pstandard_CObject_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CArray_MtoString
	.quad	_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1
	.quad	_Pshadow_Pstandard_CArray_Mdims
	.quad	_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_CT
	.quad	_Pshadow_Pstandard_CArray_Mlength
	.quad	_Pshadow_Pstandard_CArray_Mlengths
	.quad	_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.size	_Pshadow_Pstandard_CArray_Mclass, 168

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
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0, 32

	.type	.L_array1,@object       # @_array1
.L_array1:
	.ascii	 "[]"
	.size	.L_array1, 2

	.type	.L_string1,@object      # @_string1
	.align	16
.L_string1:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array1
	.long	2                       # 0x2
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string1, 32

	.type	.L_array2,@object       # @_array2
.L_array2:
	.ascii	 "[ "
	.size	.L_array2, 2

	.type	.L_string2,@object      # @_string2
	.align	16
.L_string2:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array2
	.long	2                       # 0x2
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string2, 32

	.type	.L_array3,@object       # @_array3
.L_array3:
	.ascii	 ", "
	.size	.L_array3, 2

	.type	.L_string3,@object      # @_string3
	.align	16
.L_string3:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array3
	.long	2                       # 0x2
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string3, 32

	.type	.L_array4,@object       # @_array4
.L_array4:
	.ascii	 " ]"
	.size	.L_array4, 2

	.type	.L_string4,@object      # @_string4
	.align	16
.L_string4:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array4
	.long	2                       # 0x2
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string4, 32

	.type	_Pshadow_Pstandard_Cboolean_Mclass,@object # @_Pshadow_Pstandard_Cboolean_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cboolean_Mclass
	.align	16
_Pshadow_Pstandard_Cboolean_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string014
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	(0+16)&-1
	.long	(0+1)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_Cboolean_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.size	_Pshadow_Pstandard_Cboolean_Mclass, 112

	.type	.L_array013,@object     # @_array013
	.section	.rodata,"a",@progbits
.L_array013:
	.ascii	 "boolean"
	.size	.L_array013, 7

	.type	.L_string014,@object    # @_string014
	.align	16
.L_string014:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array013
	.long	7                       # 0x7
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string014, 32

	.type	_Pshadow_Pstandard_Cbyte_Mclass,@object # @_Pshadow_Pstandard_Cbyte_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cbyte_Mclass
	.align	16
_Pshadow_Pstandard_Cbyte_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string020
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	(0+16)&-1
	.long	(0+1)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_Cbyte_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte
	.quad	_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte
	.quad	_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte
	.quad	_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte
	.quad	_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte
	.quad	_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte
	.size	_Pshadow_Pstandard_Cbyte_Mclass, 160

	.type	.L_array019,@object     # @_array019
	.section	.rodata.cst4,"aM",@progbits,4
.L_array019:
	.ascii	 "byte"
	.size	.L_array019, 4

	.type	.L_string020,@object    # @_string020
	.section	.rodata,"a",@progbits
	.align	16
.L_string020:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array019
	.long	4                       # 0x4
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string020, 32

	.type	_Pshadow_Pstandard_CClass_Mclass,@object # @_Pshadow_Pstandard_CClass_Mclass
	.data
	.globl	_Pshadow_Pstandard_CClass_Mclass
	.align	16
_Pshadow_Pstandard_CClass_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string026
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	0                       # 0x0
	.long	(0+56)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CObject_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CClass_MtoString
	.quad	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass
	.quad	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CInterface
	.quad	_Pshadow_Pstandard_CClass_Mparent
	.size	_Pshadow_Pstandard_CClass_Mclass, 136

	.type	.L_array025,@object     # @_array025
	.section	.rodata,"a",@progbits
	.align	16
.L_array025:
	.ascii	 "shadow.standard@Class"
	.size	.L_array025, 21

	.type	.L_string026,@object    # @_string026
	.align	16
.L_string026:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array025
	.long	21                      # 0x15
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string026, 32

	.type	.L_string128,@object    # @_string128
	.align	16
.L_string128:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array171
	.zero	4
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string128, 32

	.type	_Pshadow_Pstandard_Ccode_Mclass,@object # @_Pshadow_Pstandard_Ccode_Mclass
	.data
	.globl	_Pshadow_Pstandard_Ccode_Mclass
	.align	16
_Pshadow_Pstandard_Ccode_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string044
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	(0+16)&-1
	.long	(0+4)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_Ccode_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_Ccode_MtoString
	.quad	_Pshadow_Pstandard_Ccode_MtoLowerCase
	.quad	_Pshadow_Pstandard_Ccode_MtoUpperCase
	.size	_Pshadow_Pstandard_Ccode_Mclass, 128

	.type	.L_array043,@object     # @_array043
	.section	.rodata.cst4,"aM",@progbits,4
.L_array043:
	.ascii	 "code"
	.size	.L_array043, 4

	.type	.L_string044,@object    # @_string044
	.section	.rodata,"a",@progbits
	.align	16
.L_string044:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array043
	.long	4                       # 0x4
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string044, 32

	.type	_Pshadow_Pstandard_Cdouble_Mclass,@object # @_Pshadow_Pstandard_Cdouble_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cdouble_Mclass
	.align	16
_Pshadow_Pstandard_Cdouble_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string056
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	(0+16)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_Cdouble_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_Cdouble_MtoString
	.quad	_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble
	.quad	_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble
	.quad	_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble
	.quad	_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble
	.quad	_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble
	.quad	_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble
	.size	_Pshadow_Pstandard_Cdouble_Mclass, 160

	.type	.L_array055,@object     # @_array055
	.section	.rodata,"a",@progbits
.L_array055:
	.ascii	 "double"
	.size	.L_array055, 6

	.type	.L_string056,@object    # @_string056
	.align	16
.L_string056:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array055
	.long	6                       # 0x6
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string056, 32

	.type	.Lformat,@object        # @format
	.section	.rodata.str1.1,"aMS",@progbits,1
.Lformat:
	.asciz	 "%f"
	.size	.Lformat, 3

	.type	_Pshadow_Pstandard_Cfloat_Mclass,@object # @_Pshadow_Pstandard_Cfloat_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cfloat_Mclass
	.align	16
_Pshadow_Pstandard_Cfloat_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string064
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	(0+16)&-1
	.long	(0+4)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_Cfloat_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat
	.quad	_Pshadow_Pstandard_Cfloat_Mcompare_Pshadow_Pstandard_Cfloat
	.quad	_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat
	.quad	_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat
	.quad	_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat
	.quad	_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat
	.size	_Pshadow_Pstandard_Cfloat_Mclass, 160

	.type	.L_array063,@object     # @_array063
	.section	.rodata,"a",@progbits
.L_array063:
	.ascii	 "float"
	.size	.L_array063, 5

	.type	.L_string064,@object    # @_string064
	.align	16
.L_string064:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array063
	.long	5                       # 0x5
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string064, 32

	.type	_Pshadow_Pstandard_CInterface_Mclass,@object # @_Pshadow_Pstandard_CInterface_Mclass
	.data
	.globl	_Pshadow_Pstandard_CInterface_Mclass
	.align	16
_Pshadow_Pstandard_CInterface_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string070
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	0                       # 0x0
	.long	(0+32)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CInterface_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CInterface_MtoString
	.quad	_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface
	.size	_Pshadow_Pstandard_CInterface_Mclass, 120

	.type	.L_array069,@object     # @_array069
	.section	.rodata,"a",@progbits
	.align	16
.L_array069:
	.ascii	 "shadow.standard@Interface"
	.size	.L_array069, 25

	.type	.L_string070,@object    # @_string070
	.align	16
.L_string070:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array069
	.long	25                      # 0x19
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string070, 32

	.type	.L_array171,@object     # @_array171
.L_array171:
	.size	.L_array171, 0

	.type	.L_string172,@object    # @_string172
	.align	16
.L_string172:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array171
	.zero	4
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string172, 32

	.type	_Pshadow_Pstandard_Cint_Mclass,@object # @_Pshadow_Pstandard_Cint_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cint_Mclass
	.align	16
_Pshadow_Pstandard_Cint_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string082
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	(0+16)&-1
	.long	(0+4)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_Cint_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_Cint_MtoString
	.quad	_Pshadow_Pstandard_Cint_Mabs
	.quad	_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_Cint_Mcompare_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_Cint_Mmin_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_Cint_Mmodulus_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_Cint_MtoString_Pshadow_Pstandard_Cuint
	.size	_Pshadow_Pstandard_Cint_Mclass, 192

	.type	.L_array081,@object     # @_array081
	.section	.rodata,"a",@progbits
.L_array081:
	.ascii	 "int"
	.size	.L_array081, 3

	.type	.L_string082,@object    # @_string082
	.align	16
.L_string082:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array081
	.long	3                       # 0x3
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string082, 32

	.type	_Pshadow_Pstandard_Clong_Mclass,@object # @_Pshadow_Pstandard_Clong_Mclass
	.data
	.globl	_Pshadow_Pstandard_Clong_Mclass
	.align	16
_Pshadow_Pstandard_Clong_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0100
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	(0+16)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_Clong_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_Clong_MtoString
	.quad	_Pshadow_Pstandard_Clong_Mabs
	.quad	_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong
	.quad	_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong
	.quad	_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong
	.quad	_Pshadow_Pstandard_Clong_Mmax_Pshadow_Pstandard_Clong
	.quad	_Pshadow_Pstandard_Clong_Mmin_Pshadow_Pstandard_Clong
	.quad	_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong
	.quad	_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong
	.quad	_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong
	.quad	_Pshadow_Pstandard_Clong_MtoString_Pshadow_Pstandard_Culong
	.size	_Pshadow_Pstandard_Clong_Mclass, 192

	.type	.L_array099,@object     # @_array099
	.section	.rodata.cst4,"aM",@progbits,4
.L_array099:
	.ascii	 "long"
	.size	.L_array099, 4

	.type	.L_string0100,@object   # @_string0100
	.section	.rodata,"a",@progbits
	.align	16
.L_string0100:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array099
	.long	4                       # 0x4
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0100, 32

	.type	_Pshadow_Pstandard_CMutableString_Mclass,@object # @_Pshadow_Pstandard_CMutableString_Mclass
	.data
	.globl	_Pshadow_Pstandard_CMutableString_Mclass
	.align	16
_Pshadow_Pstandard_CMutableString_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0118
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	0                       # 0x0
	.long	(0+32)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CMutableString_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CMutableString_MtoString
	.quad	_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CMutableString_Mcapacity
	.quad	_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_CMutableString_Mlength
	.quad	_Pshadow_Pstandard_CMutableString_Mreverse
	.size	_Pshadow_Pstandard_CMutableString_Mclass, 168

	.type	.L_array0117,@object    # @_array0117
	.section	.rodata,"a",@progbits
	.align	16
.L_array0117:
	.ascii	 "shadow.standard@MutableString"
	.size	.L_array0117, 29

	.type	.L_string0118,@object   # @_string0118
	.align	16
.L_string0118:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0117
	.long	29                      # 0x1d
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0118, 32

	.type	_Pshadow_Pstandard_CObject_Mclass,@object # @_Pshadow_Pstandard_CObject_Mclass
	.data
	.globl	_Pshadow_Pstandard_CObject_Mclass
	.align	16
_Pshadow_Pstandard_CObject_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0262
	.quad	0
	.long	0                       # 0x0
	.long	(0+8)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CObject_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.size	_Pshadow_Pstandard_CObject_Mclass, 112

	.type	.L_array0261,@object    # @_array0261
	.section	.rodata,"a",@progbits
	.align	16
.L_array0261:
	.ascii	 "shadow.standard@Object"
	.size	.L_array0261, 22

	.type	.L_string0262,@object   # @_string0262
	.align	16
.L_string0262:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0261
	.long	22                      # 0x16
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0262, 32

	.type	_Pshadow_Pstandard_CReference_Mclass,@object # @_Pshadow_Pstandard_CReference_Mclass
	.data
	.globl	_Pshadow_Pstandard_CReference_Mclass
	.align	16
_Pshadow_Pstandard_CReference_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0278
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	0                       # 0x0
	.long	((0+8)&-1)*3
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CReference_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CReference_MtoString
	.quad	_Pshadow_Pstandard_CReference_Mcreate_CT
	.quad	_Pshadow_Pstandard_CReference_Mvalue
	.quad	_Pshadow_Pstandard_CReference_Mvalue_CT
	.size	_Pshadow_Pstandard_CReference_Mclass, 136

	.type	.L_array0277,@object    # @_array0277
	.section	.rodata,"a",@progbits
	.align	16
.L_array0277:
	.ascii	 "shadow.standard@Reference<T>"
	.size	.L_array0277, 28

	.type	.L_string0278,@object   # @_string0278
	.align	16
.L_string0278:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0277
	.long	28                      # 0x1c
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0278, 32

	.type	_Pshadow_Pstandard_Cshort_Mclass,@object # @_Pshadow_Pstandard_Cshort_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cshort_Mclass
	.align	16
_Pshadow_Pstandard_Cshort_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0284
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	(0+16)&-1
	.long	(0+2)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_Cshort_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort
	.quad	_Pshadow_Pstandard_Cshort_Mcompare_Pshadow_Pstandard_Cshort
	.quad	_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort
	.quad	_Pshadow_Pstandard_Cshort_Mmodulus_Pshadow_Pstandard_Cshort
	.quad	_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort
	.quad	_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort
	.size	_Pshadow_Pstandard_Cshort_Mclass, 160

	.type	.L_array0283,@object    # @_array0283
	.section	.rodata,"a",@progbits
.L_array0283:
	.ascii	 "short"
	.size	.L_array0283, 5

	.type	.L_string0284,@object   # @_string0284
	.align	16
.L_string0284:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0283
	.long	5                       # 0x5
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0284, 32

	.type	_Pshadow_Pstandard_CString_Mclass,@object # @_Pshadow_Pstandard_CString_Mclass
	.data
	.globl	_Pshadow_Pstandard_CString_Mclass
	.align	16
_Pshadow_Pstandard_CString_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0290
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	0                       # 0x0
	.long	(0+32)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CString_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CString_MtoString
	.quad	_Pshadow_Pstandard_CString_Mchars
	.quad	_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString
	.quad	_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString
	.quad	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1
	.quad	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1
	.quad	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString
	.quad	_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString
	.quad	_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_CString_Mlength
	.quad	_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_CString_MtoLowerCase
	.quad	_Pshadow_Pstandard_CString_MtoUpperCase
	.size	_Pshadow_Pstandard_CString_Mclass, 208

	.type	.L_array0289,@object    # @_array0289
	.section	.rodata,"a",@progbits
	.align	16
.L_array0289:
	.ascii	 "shadow.standard@String"
	.size	.L_array0289, 22

	.type	.L_string0290,@object   # @_string0290
	.align	16
.L_string0290:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0289
	.long	22                      # 0x16
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0290, 32

	.type	_Pshadow_Pstandard_CSystem_Mclass,@object # @_Pshadow_Pstandard_CSystem_Mclass
	.data
	.globl	_Pshadow_Pstandard_CSystem_Mclass
	.align	16
_Pshadow_Pstandard_CSystem_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0320
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	0                       # 0x0
	.long	(0+8)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CSystem_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Pstandard_CSystem_MnanoTime
	.size	_Pshadow_Pstandard_CSystem_Mclass, 120

	.type	_Pshadow_Pstandard_CSystem_Minstance,@object # @_Pshadow_Pstandard_CSystem_Minstance
	.bss
	.globl	_Pshadow_Pstandard_CSystem_Minstance
	.align	8
_Pshadow_Pstandard_CSystem_Minstance:
	.quad	0
	.size	_Pshadow_Pstandard_CSystem_Minstance, 8

	.type	.L_array0319,@object    # @_array0319
	.section	.rodata,"a",@progbits
	.align	16
.L_array0319:
	.ascii	 "shadow.standard@System"
	.size	.L_array0319, 22

	.type	.L_string0320,@object   # @_string0320
	.align	16
.L_string0320:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0319
	.long	22                      # 0x16
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0320, 32

	.type	_Pshadow_Pstandard_Cubyte_Mclass,@object # @_Pshadow_Pstandard_Cubyte_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cubyte_Mclass
	.align	16
_Pshadow_Pstandard_Cubyte_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0324
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	(0+16)&-1
	.long	(0+1)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_Cubyte_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte
	.quad	_Pshadow_Pstandard_Cubyte_Mcompare_Pshadow_Pstandard_Cubyte
	.quad	_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte
	.quad	_Pshadow_Pstandard_Cubyte_Mmodulus_Pshadow_Pstandard_Cubyte
	.quad	_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte
	.quad	_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte
	.size	_Pshadow_Pstandard_Cubyte_Mclass, 160

	.type	.L_array0323,@object    # @_array0323
	.section	.rodata,"a",@progbits
.L_array0323:
	.ascii	 "ubyte"
	.size	.L_array0323, 5

	.type	.L_string0324,@object   # @_string0324
	.align	16
.L_string0324:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0323
	.long	5                       # 0x5
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0324, 32

	.type	_Pshadow_Pstandard_Cuint_Mclass,@object # @_Pshadow_Pstandard_Cuint_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cuint_Mclass
	.align	16
_Pshadow_Pstandard_Cuint_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0330
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	(0+16)&-1
	.long	(0+4)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_Cuint_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_Cuint_MtoString
	.quad	_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_MtoString_Pshadow_Pstandard_Cuint
	.size	_Pshadow_Pstandard_Cuint_Mclass, 184

	.type	.L_array0329,@object    # @_array0329
	.section	.rodata.cst4,"aM",@progbits,4
.L_array0329:
	.ascii	 "uint"
	.size	.L_array0329, 4

	.type	.L_string0330,@object   # @_string0330
	.section	.rodata,"a",@progbits
	.align	16
.L_string0330:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0329
	.long	4                       # 0x4
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0330, 32

	.type	_Pshadow_Pstandard_Culong_Mclass,@object # @_Pshadow_Pstandard_Culong_Mclass
	.data
	.globl	_Pshadow_Pstandard_Culong_Mclass
	.align	16
_Pshadow_Pstandard_Culong_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0344
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	(0+16)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_Culong_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_Culong_MtoString
	.quad	_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong
	.quad	_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong
	.quad	_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong
	.quad	_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong
	.quad	_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong
	.quad	_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong
	.quad	_Pshadow_Pstandard_Culong_MtoString_Pshadow_Pstandard_Culong
	.size	_Pshadow_Pstandard_Culong_Mclass, 168

	.type	.L_array0343,@object    # @_array0343
	.section	.rodata,"a",@progbits
.L_array0343:
	.ascii	 "ulong"
	.size	.L_array0343, 5

	.type	.L_string0344,@object   # @_string0344
	.align	16
.L_string0344:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0343
	.long	5                       # 0x5
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0344, 32

	.type	_Pshadow_Pstandard_Cushort_Mclass,@object # @_Pshadow_Pstandard_Cushort_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cushort_Mclass
	.align	16
_Pshadow_Pstandard_Cushort_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0354
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	(0+16)&-1
	.long	(0+2)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_Cushort_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort
	.quad	_Pshadow_Pstandard_Cushort_Mcompare_Pshadow_Pstandard_Cushort
	.quad	_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort
	.quad	_Pshadow_Pstandard_Cushort_Mmodulus_Pshadow_Pstandard_Cushort
	.quad	_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort
	.quad	_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort
	.size	_Pshadow_Pstandard_Cushort_Mclass, 160

	.type	.L_array0353,@object    # @_array0353
	.section	.rodata,"a",@progbits
.L_array0353:
	.ascii	 "ushort"
	.size	.L_array0353, 6

	.type	.L_string0354,@object   # @_string0354
	.align	16
.L_string0354:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0353
	.long	6                       # 0x6
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0354, 32

	.type	_Pshadow_Pio_CConsole_Mclass,@object # @_Pshadow_Pio_CConsole_Mclass
	.data
	.globl	_Pshadow_Pio_CConsole_Mclass
	.align	16
_Pshadow_Pio_CConsole_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0358
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	0                       # 0x0
	.long	(0+8)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pio_CConsole_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString
	.quad	_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CString
	.quad	_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pio_CConsole_MprintErrorLine
	.quad	_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pio_CConsole_MprintLine
	.size	_Pshadow_Pio_CConsole_Mclass, 176

	.type	_Pshadow_Pio_CConsole_Minstance,@object # @_Pshadow_Pio_CConsole_Minstance
	.bss
	.globl	_Pshadow_Pio_CConsole_Minstance
	.align	8
_Pshadow_Pio_CConsole_Minstance:
	.quad	0
	.size	_Pshadow_Pio_CConsole_Minstance, 8

	.type	.L_array0357,@object    # @_array0357
	.section	.rodata,"a",@progbits
	.align	16
.L_array0357:
	.ascii	 "shadow.io@Console"
	.size	.L_array0357, 17

	.type	.L_string0358,@object   # @_string0358
	.align	16
.L_string0358:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0357
	.long	17                      # 0x11
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0358, 32

	.type	_Pshadow_Putility_CRandom_Mclass,@object # @_Pshadow_Putility_CRandom_Mclass
	.data
	.globl	_Pshadow_Putility_CRandom_Mclass
	.align	16
_Pshadow_Putility_CRandom_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0362
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	0                       # 0x0
	.long	(0+8)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Putility_CRandom_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Putility_CRandom_Mcreate_Pshadow_Pstandard_Culong
	.quad	_Pshadow_Putility_CRandom_MnextInt
	.quad	_Pshadow_Putility_CRandom_MnextInt_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Putility_CRandom_MnextUInt
	.quad	_Pshadow_Putility_CRandom_MnextUInt_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Putility_CRandom_Mseed_Pshadow_Pstandard_Culong
	.size	_Pshadow_Putility_CRandom_Mclass, 160

	.type	.L_array0361,@object    # @_array0361
	.section	.rodata,"a",@progbits
	.align	16
.L_array0361:
	.ascii	 "shadow.utility@Random"
	.size	.L_array0361, 21

	.type	.L_string0362,@object   # @_string0362
	.align	16
.L_string0362:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0361
	.long	21                      # 0x15
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0362, 32

	.type	_P_CSortTest_Mclass,@object # @_P_CSortTest_Mclass
	.data
	.globl	_P_CSortTest_Mclass
	.align	16
_P_CSortTest_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0368
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	0                       # 0x0
	.long	(0+40)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Pstandard_CObject_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_P_CSortTest_MbucketSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.quad	_P_CSortTest_Mcreate_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.quad	_P_CSortTest_MgetArray
	.quad	_P_CSortTest_MgetArrayEnd
	.quad	_P_CSortTest_MgetArrayLength
	.quad	_P_CSortTest_MgetArrayStart
	.quad	_P_CSortTest_MgetMaximum
	.quad	_P_CSortTest_MgetMinimum
	.quad	_P_CSortTest_MgetTime
	.quad	_P_CSortTest_MheapSort_Pshadow_Pstandard_Cint_A1
	.quad	_P_CSortTest_MmergeSort_Pshadow_Pstandard_Cint_A1
	.quad	_P_CSortTest_MquickSort_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.quad	_P_CSortTest_Mrun
	.size	_P_CSortTest_Mclass, 216

	.type	.L_array0367,@object    # @_array0367
	.section	.rodata.cst16,"aM",@progbits,16
.L_array0367:
	.ascii	 "default@SortTest"
	.size	.L_array0367, 16

	.type	.L_string0368,@object   # @_string0368
	.section	.rodata,"a",@progbits
	.align	16
.L_string0368:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0367
	.long	16                      # 0x10
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0368, 32

	.type	.L_array1369,@object    # @_array1369
	.section	.rodata.cst4,"aM",@progbits,4
.L_array1369:
	.ascii	 "null"
	.size	.L_array1369, 4

	.type	.L_string1370,@object   # @_string1370
	.section	.rodata,"a",@progbits
	.align	16
.L_string1370:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array1369
	.long	4                       # 0x4
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string1370, 32

	.type	.L_array2371,@object    # @_array2371
.L_array2371:
	.ascii	 ":\t\t"
	.size	.L_array2371, 3

	.type	.L_string2372,@object   # @_string2372
	.align	16
.L_string2372:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array2371
	.long	3                       # 0x3
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string2372, 32

	.type	.L_array3373,@object    # @_array3373
	.section	.rodata.cst8,"aM",@progbits,8
.L_array3373:
	.ascii	 " seconds"
	.size	.L_array3373, 8

	.type	.L_string3374,@object   # @_string3374
	.section	.rodata,"a",@progbits
	.align	16
.L_string3374:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array3373
	.long	8                       # 0x8
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string3374, 32

	.type	.L_array4375,@object    # @_array4375
.L_array4375:
	.ascii	 "Array length:\t\t"
	.size	.L_array4375, 15

	.type	.L_string4376,@object   # @_string4376
	.align	16
.L_string4376:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array4375
	.long	15                      # 0xf
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string4376, 32

	.type	.L_array5,@object       # @_array5
.L_array5:
	.ascii	 "Quicksort"
	.size	.L_array5, 9

	.type	.L_string5,@object      # @_string5
	.align	16
.L_string5:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array5
	.long	9                       # 0x9
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string5, 32

	.type	.L_array6,@object       # @_array6
.L_array6:
	.ascii	 "Heap Sort"
	.size	.L_array6, 9

	.type	.L_string6,@object      # @_string6
	.align	16
.L_string6:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array6
	.long	9                       # 0x9
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string6, 32

	.type	.L_array7,@object       # @_array7
.L_array7:
	.ascii	 "Merge Sort"
	.size	.L_array7, 10

	.type	.L_string7,@object      # @_string7
	.align	16
.L_string7:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array7
	.long	10                      # 0xa
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string7, 32

	.type	.L_array8,@object       # @_array8
.L_array8:
	.ascii	 "Bucket Sort"
	.size	.L_array8, 11

	.type	.L_string8,@object      # @_string8
	.align	16
.L_string8:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array8
	.long	11                      # 0xb
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string8, 32

	.type	_Pshadow_Ptest_CSortMain_Mclass,@object # @_Pshadow_Ptest_CSortMain_Mclass
	.data
	.globl	_Pshadow_Ptest_CSortMain_Mclass
	.align	16
_Pshadow_Ptest_CSortMain_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0380
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	0                       # 0x0
	.long	(0+8)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Ptest_CSortMain_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Ptest_CSortMain_Mmain_Pshadow_Pstandard_CString_A1
	.size	_Pshadow_Ptest_CSortMain_Mclass, 120

	.type	.L_array0379,@object    # @_array0379
	.section	.rodata,"a",@progbits
	.align	16
.L_array0379:
	.ascii	 "shadow.test@SortMain"
	.size	.L_array0379, 20

	.type	.L_string0380,@object   # @_string0380
	.align	16
.L_string0380:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0379
	.long	20                      # 0x14
	.zero	4
	.byte	1                       # 0x1
	.zero	7
	.size	.L_string0380, 32


	.section	".note.GNU-stack","",@progbits
