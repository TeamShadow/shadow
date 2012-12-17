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
	callq	_Pshadow_Ptest_CArrayTest_Mmain
	xorl	%eax, %eax
	popq	%rdx
	ret
.Ltmp2:
	.size	main, .Ltmp2-main
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mdims
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mdims,@function
_Pshadow_Pstandard_CArray_Mdims:        # @_Pshadow_Pstandard_CArray_Mdims
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp7:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp8:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp9:
	.cfi_def_cfa_offset 32
.Ltmp10:
	.cfi_offset %rbx, -32
.Ltmp11:
	.cfi_offset %r14, -24
.Ltmp12:
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
.Ltmp13:
	.size	_Pshadow_Pstandard_CArray_Mdims, .Ltmp13-_Pshadow_Pstandard_CArray_Mdims
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mlength
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mlength,@function
_Pshadow_Pstandard_CArray_Mlength:      # @_Pshadow_Pstandard_CArray_Mlength
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp21:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp22:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp23:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp24:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp25:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp26:
	.cfi_def_cfa_offset 56
	pushq	%rax
.Ltmp27:
	.cfi_def_cfa_offset 64
.Ltmp28:
	.cfi_offset %rbx, -56
.Ltmp29:
	.cfi_offset %r12, -48
.Ltmp30:
	.cfi_offset %r13, -40
.Ltmp31:
	.cfi_offset %r14, -32
.Ltmp32:
	.cfi_offset %r15, -24
.Ltmp33:
	.cfi_offset %rbp, -16
	movq	%rdi, %r15
	movq	8(%r15), %rax
	movl	(%rax), %r14d
	movl	$1, %r13d
	jmp	.LBB2_2
	.align	16, 0x90
.LBB2_1:                                # %_label0
                                        #   in Loop: Header=BB2_2 Depth=1
	movslq	%r13d, %rax
	movq	8(%r15), %rcx
	imull	(%rcx,%rax,4), %r14d
	incl	%r13d
.LBB2_2:                                # %_label1
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
	jl	.LBB2_1
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
.Ltmp34:
	.size	_Pshadow_Pstandard_CArray_Mlength, .Ltmp34-_Pshadow_Pstandard_CArray_Mlength
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mlengths
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mlengths,@function
_Pshadow_Pstandard_CArray_Mlengths:     # @_Pshadow_Pstandard_CArray_Mlengths
# BB#0:
	movq	8(%rdi), %rax
	movl	16(%rdi), %edx
	ret
.Ltmp35:
	.size	_Pshadow_Pstandard_CArray_Mlengths, .Ltmp35-_Pshadow_Pstandard_CArray_Mlengths

	.globl	_Pshadow_Pstandard_CArray_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_MtoString,@function
_Pshadow_Pstandard_CArray_MtoString:    # @_Pshadow_Pstandard_CArray_MtoString
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp41:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp42:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp43:
	.cfi_def_cfa_offset 32
	pushq	%rbx
.Ltmp44:
	.cfi_def_cfa_offset 40
	pushq	%rax
.Ltmp45:
	.cfi_def_cfa_offset 48
.Ltmp46:
	.cfi_offset %rbx, -40
.Ltmp47:
	.cfi_offset %r14, -32
.Ltmp48:
	.cfi_offset %r15, -24
.Ltmp49:
	.cfi_offset %rbp, -16
	movq	%rdi, %r15
	movq	(%r15), %rax
	callq	*144(%rax)
	testl	%eax, %eax
	je	.LBB4_1
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
	jl	.LBB4_6
# BB#4:
	movl	$1, %ebp
	.align	16, 0x90
.LBB4_5:                                # %_label6
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
	jl	.LBB4_5
.LBB4_6:                                # %_label8
	movq	%r14, %rdi
	movl	$.L_string4, %esi
	callq	*%rcx
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*104(%rcx)
	jmp	.LBB4_2
.LBB4_1:                                # %_label3
	movl	$.L_string1, %eax
.LBB4_2:                                # %_label3
	addq	$8, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp50:
	.size	_Pshadow_Pstandard_CArray_MtoString, .Ltmp50-_Pshadow_Pstandard_CArray_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_CT
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_CT,@function
_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_CT: # @_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_CT
# BB#0:
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rdi)
	movq	%rsi, 24(%rdi)
	movl	%ecx, 16(%rdi)
	movq	%rdx, 8(%rdi)
	movq	%r8, 32(%rdi)
	movq	%rdi, %rax
	ret
.Ltmp51:
	.size	_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_CT, .Ltmp51-_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_CT

	.globl	_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1,@function
_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1: # @_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp59:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp60:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp61:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp62:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp63:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp64:
	.cfi_def_cfa_offset 56
	subq	$24, %rsp
.Ltmp65:
	.cfi_def_cfa_offset 80
.Ltmp66:
	.cfi_offset %rbx, -56
.Ltmp67:
	.cfi_offset %r12, -48
.Ltmp68:
	.cfi_offset %r13, -40
.Ltmp69:
	.cfi_offset %r14, -32
.Ltmp70:
	.cfi_offset %r15, -24
.Ltmp71:
	.cfi_offset %rbp, -16
	movl	%ecx, %r13d
	movq	%rdx, %r12
	movq	%rdi, %r14
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%r14)
	movl	%r13d, 16(%rsp)
	movq	%r12, 8(%rsp)
	movq	%rsi, 24(%r14)
	movq	$0, 32(%r14)
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	xorl	%ebp, %ebp
	xorl	%edi, %edi
	callq	calloc
	movq	%rax, 8(%r14)
	movl	$0, 16(%r14)
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %r15
	movl	%r13d, (%r15)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Cint_Mclass, 24(%rax)
	movq	%r15, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r12, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*152(%rcx)
	movl	(%rax), %ebx
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	%ebx, %edi
	callq	calloc
	movl	%ebx, 16(%r14)
	movq	%rax, 8(%r14)
	jmp	.LBB6_2
	.align	16, 0x90
.LBB6_1:                                # %_label9
                                        #   in Loop: Header=BB6_2 Depth=1
	movslq	%ebp, %rax
	movq	8(%rsp), %rcx
	movl	(%rcx,%rax,4), %ecx
	movq	8(%r14), %rdx
	movl	%ecx, (%rdx,%rax,4)
	incl	%ebp
	movl	16(%rsp), %r13d
	movq	8(%rsp), %r12
.LBB6_2:                                # %_label10
                                        # =>This Inner Loop Header: Depth=1
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbx
	movl	%r13d, (%rbx)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Cint_Mclass, 24(%rax)
	movq	%rbx, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r12, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*152(%rcx)
	cmpl	(%rax), %ebp
	jl	.LBB6_1
# BB#3:                                 # %_label11
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*144(%rax)
	movq	24(%r14), %rcx
	movl	48(%rcx), %esi
	movl	%eax, %edi
	callq	calloc
	movq	%rax, 32(%r14)
	movq	%r14, %rax
	addq	$24, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp72:
	.size	_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1, .Ltmp72-_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CArray_Mcopy
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mcopy,@function
_Pshadow_Pstandard_CArray_Mcopy:        # @_Pshadow_Pstandard_CArray_Mcopy
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp77:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp78:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp79:
	.cfi_def_cfa_offset 32
.Ltmp80:
	.cfi_offset %rbx, -32
.Ltmp81:
	.cfi_offset %r14, -24
.Ltmp82:
	.cfi_offset %rbp, -16
	movq	%rdi, %rbx
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %r14
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%r14)
	movl	16(%rbx), %ecx
	movq	8(%rbx), %rdx
	movq	24(%rbx), %rsi
	movq	%r14, %rdi
	callq	*_Pshadow_Pstandard_CArray_Mclass+112(%rip)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*144(%rax)
	testl	%eax, %eax
	jle	.LBB7_3
# BB#1:
	xorl	%ebp, %ebp
	.align	16, 0x90
.LBB7_2:                                # %_label12
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
	jl	.LBB7_2
.LBB7_3:                                # %_label14
	movq	%r14, %rax
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp83:
	.size	_Pshadow_Pstandard_CArray_Mcopy, .Ltmp83-_Pshadow_Pstandard_CArray_Mcopy
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
.Ltmp84:
	.size	_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_Cint, .Ltmp84-_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_Cint

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
	je	.LBB9_1
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
	jmp	.LBB9_2
.LBB9_1:
	movq	(%r15), %rax
.LBB9_2:
	addq	$8, %rsp
	popq	%rbx
	popq	%r12
	popq	%r14
	popq	%r15
	ret
.Ltmp85:
	.size	_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint, .Ltmp85-_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject,@function
_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject
# BB#0:
	pushq	%rax
	movl	%esi, %eax
	movq	%rdi, %rcx
	movq	24(%rcx), %rsi
	imull	48(%rsi), %eax
	movslq	%eax, %rdi
	addq	32(%rcx), %rdi
	testb	$1, 40(%rsi)
	je	.LBB10_1
# BB#2:
	addq	$8, %rdx
	movq	%rdx, %rsi
	movq	%rax, %rdx
	callq	memcpy
	popq	%rax
	ret
.LBB10_1:
	movq	%rdx, (%rdi)
	popq	%rax
	ret
.Ltmp86:
	.size	_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject, .Ltmp86-_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject

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
.Ltmp87:
	.size	_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint, .Ltmp87-_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cboolean_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cboolean_Mcreate,@function
_Pshadow_Pstandard_Cboolean_Mcreate:    # @_Pshadow_Pstandard_Cboolean_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cboolean_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp88:
	.size	_Pshadow_Pstandard_Cboolean_Mcreate, .Ltmp88-_Pshadow_Pstandard_Cboolean_Mcreate

	.globl	_Pshadow_Pstandard_Cbyte_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cbyte_Mcreate,@function
_Pshadow_Pstandard_Cbyte_Mcreate:       # @_Pshadow_Pstandard_Cbyte_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cbyte_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp89:
	.size	_Pshadow_Pstandard_Cbyte_Mcreate, .Ltmp89-_Pshadow_Pstandard_Cbyte_Mcreate

	.globl	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass,@function
_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass: # @_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass
# BB#0:
	.align	16, 0x90
.LBB14_1:                               # %tailrecurse
                                        # =>This Inner Loop Header: Depth=1
	movb	$1, %al
	cmpq	%rsi, %rdi
	je	.LBB14_3
# BB#2:                                 # %_label2
                                        #   in Loop: Header=BB14_1 Depth=1
	movq	32(%rdi), %rdi
	xorb	%al, %al
	testq	%rdi, %rdi
	jne	.LBB14_1
.LBB14_3:                               # %_label0
	ret
.Ltmp90:
	.size	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass, .Ltmp90-_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass

	.globl	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CInterface
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CInterface,@function
_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CInterface: # @_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CInterface
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp98:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp99:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp100:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp101:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp102:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp103:
	.cfi_def_cfa_offset 56
	pushq	%rax
.Ltmp104:
	.cfi_def_cfa_offset 64
.Ltmp105:
	.cfi_offset %rbx, -56
.Ltmp106:
	.cfi_offset %r12, -48
.Ltmp107:
	.cfi_offset %r13, -40
.Ltmp108:
	.cfi_offset %r14, -32
.Ltmp109:
	.cfi_offset %r15, -24
.Ltmp110:
	.cfi_offset %rbp, -16
	movq	%rsi, %r14
	movq	%rdi, %r15
	xorl	%r13d, %r13d
	jmp	.LBB15_4
	.align	16, 0x90
.LBB15_3:                               # %_label10
                                        #   in Loop: Header=BB15_4 Depth=1
	incl	%r13d
.LBB15_4:                               # %_label7
                                        # =>This Inner Loop Header: Depth=1
	movq	8(%r15), %r12
	movl	16(%r15), %ebp
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbx
	movl	%ebp, (%rbx)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CInterface_Mclass, 24(%rax)
	movq	%rbx, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r12, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*144(%rcx)
	movl	%eax, %ecx
	xorb	%al, %al
	cmpl	%ecx, %r13d
	jge	.LBB15_5
# BB#1:                                 # %_label6
                                        #   in Loop: Header=BB15_4 Depth=1
	movslq	%r13d, %rax
	movq	8(%r15), %rcx
	movq	(%rcx,%rax,8), %rdi
	testq	%rdi, %rdi
	je	.LBB15_3
# BB#2:                                 # %_label14
                                        #   in Loop: Header=BB15_4 Depth=1
	movq	%r14, %rsi
	callq	_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface
	movb	%al, %cl
	movb	$1, %al
	testb	$1, %cl
	je	.LBB15_3
.LBB15_5:                               # %_label11
	addq	$8, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp111:
	.size	_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CInterface, .Ltmp111-_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CInterface
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CClass_Mparent
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_Mparent,@function
_Pshadow_Pstandard_CClass_Mparent:      # @_Pshadow_Pstandard_CClass_Mparent
# BB#0:
	movq	32(%rdi), %rax
	ret
.Ltmp112:
	.size	_Pshadow_Pstandard_CClass_Mparent, .Ltmp112-_Pshadow_Pstandard_CClass_Mparent

	.globl	_Pshadow_Pstandard_CClass_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_MtoString,@function
_Pshadow_Pstandard_CClass_MtoString:    # @_Pshadow_Pstandard_CClass_MtoString
# BB#0:
	movq	24(%rdi), %rax
	ret
.Ltmp113:
	.size	_Pshadow_Pstandard_CClass_MtoString, .Ltmp113-_Pshadow_Pstandard_CClass_MtoString

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
	movq	$.L_string126, 24(%rbx)
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
.Ltmp114:
	.size	_Pshadow_Pstandard_CClass_Mcreate, .Ltmp114-_Pshadow_Pstandard_CClass_Mcreate

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
.Ltmp115:
	.size	_Pshadow_Pstandard_CClass_Mallocate, .Ltmp115-_Pshadow_Pstandard_CClass_Mallocate

	.globl	_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint
# BB#0:
	movl	48(%rdi), %eax
	movl	%esi, %edi
	movl	%eax, %esi
	jmp	calloc                  # TAILCALL
.Ltmp116:
	.size	_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint, .Ltmp116-_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Ccode_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Ccode_MtoString,@function
_Pshadow_Pstandard_Ccode_MtoString:     # @_Pshadow_Pstandard_Ccode_MtoString
	.cfi_startproc
# BB#0:
	pushq	%r14
.Ltmp120:
	.cfi_def_cfa_offset 16
	pushq	%rbx
.Ltmp121:
	.cfi_def_cfa_offset 24
	pushq	%rax
.Ltmp122:
	.cfi_def_cfa_offset 32
.Ltmp123:
	.cfi_offset %rbx, -24
.Ltmp124:
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
	callq	*_Pshadow_Pstandard_CString_Mclass+136(%rip)
	movq	%rbx, %rax
	addq	$8, %rsp
	popq	%rbx
	popq	%r14
	ret
.Ltmp125:
	.size	_Pshadow_Pstandard_Ccode_MtoString, .Ltmp125-_Pshadow_Pstandard_Ccode_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_Ccode_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Ccode_Mcreate,@function
_Pshadow_Pstandard_Ccode_Mcreate:       # @_Pshadow_Pstandard_Ccode_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Ccode_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp126:
	.size	_Pshadow_Pstandard_Ccode_Mcreate, .Ltmp126-_Pshadow_Pstandard_Ccode_Mcreate

	.globl	_Pshadow_Pstandard_Cdouble_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_Mcreate,@function
_Pshadow_Pstandard_Cdouble_Mcreate:     # @_Pshadow_Pstandard_Cdouble_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cdouble_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp127:
	.size	_Pshadow_Pstandard_Cdouble_Mcreate, .Ltmp127-_Pshadow_Pstandard_Cdouble_Mcreate

	.globl	_Pshadow_Pstandard_Cfloat_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cfloat_Mcreate,@function
_Pshadow_Pstandard_Cfloat_Mcreate:      # @_Pshadow_Pstandard_Cfloat_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cfloat_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp128:
	.size	_Pshadow_Pstandard_Cfloat_Mcreate, .Ltmp128-_Pshadow_Pstandard_Cfloat_Mcreate

	.globl	_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface,@function
_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface: # @_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp136:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp137:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp138:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp139:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp140:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp141:
	.cfi_def_cfa_offset 56
	pushq	%rax
.Ltmp142:
	.cfi_def_cfa_offset 64
.Ltmp143:
	.cfi_offset %rbx, -56
.Ltmp144:
	.cfi_offset %r12, -48
.Ltmp145:
	.cfi_offset %r13, -40
.Ltmp146:
	.cfi_offset %r14, -32
.Ltmp147:
	.cfi_offset %r15, -24
.Ltmp148:
	.cfi_offset %rbp, -16
	movq	%rsi, %r14
	movq	%rdi, %r15
	cmpq	%r14, %r15
	je	.LBB25_1
# BB#7:                                 # %_label4.preheader
	xorl	%r13d, %r13d
	jmp	.LBB25_6
	.align	16, 0x90
.LBB25_5:                               # %_label7
                                        #   in Loop: Header=BB25_6 Depth=1
	incl	%r13d
.LBB25_6:                               # %_label4
                                        # =>This Inner Loop Header: Depth=1
	movq	8(%r15), %r12
	movl	16(%r15), %ebp
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbx
	movl	%ebp, (%rbx)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CInterface_Mclass, 24(%rax)
	movq	%rbx, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r12, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*144(%rcx)
	movl	%eax, %ecx
	xorb	%al, %al
	cmpl	%ecx, %r13d
	jge	.LBB25_2
# BB#3:                                 # %_label3
                                        #   in Loop: Header=BB25_6 Depth=1
	movslq	%r13d, %rax
	movq	8(%r15), %rcx
	movq	(%rcx,%rax,8), %rdi
	testq	%rdi, %rdi
	je	.LBB25_5
# BB#4:                                 # %_label11
                                        #   in Loop: Header=BB25_6 Depth=1
	movq	%r14, %rsi
	callq	_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface
	testb	$1, %al
	je	.LBB25_5
.LBB25_1:
	movb	$1, %al
.LBB25_2:                               # %_label0
	addq	$8, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp149:
	.size	_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface, .Ltmp149-_Pshadow_Pstandard_CInterface_MisSubtype_Pshadow_Pstandard_CInterface
	.cfi_endproc

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
	movq	$.L_string164, 24(%rbx)
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

	.globl	_Pshadow_Pstandard_Cint_Mabs
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Mabs,@function
_Pshadow_Pstandard_Cint_Mabs:           # @_Pshadow_Pstandard_Cint_Mabs
# BB#0:                                 # %_label5
	movl	8(%rdi), %ecx
	movl	%ecx, %eax
	negl	%eax
	cmovll	%ecx, %eax
	ret
.Ltmp153:
	.size	_Pshadow_Pstandard_Cint_Mabs, .Ltmp153-_Pshadow_Pstandard_Cint_Mabs

	.globl	_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint
# BB#0:                                 # %_label8
	movl	8(%rdi), %eax
	cmpl	%esi, %eax
	cmovll	%esi, %eax
	ret
.Ltmp154:
	.size	_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint, .Ltmp154-_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cint_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_MtoString,@function
_Pshadow_Pstandard_Cint_MtoString:      # @_Pshadow_Pstandard_Cint_MtoString
	.cfi_startproc
# BB#0:
	pushq	%r15
.Ltmp159:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp160:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp161:
	.cfi_def_cfa_offset 32
.Ltmp162:
	.cfi_offset %rbx, -32
.Ltmp163:
	.cfi_offset %r14, -24
.Ltmp164:
	.cfi_offset %r15, -16
	movl	8(%rdi), %ebx
	movl	_Pshadow_Pstandard_CMutableString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %r14
	movq	$_Pshadow_Pstandard_CMutableString_Mclass, (%r14)
	movq	%r14, %rdi
	callq	*_Pshadow_Pstandard_CMutableString_Mclass+64(%rip)
	testl	%ebx, %ebx
	jns	.LBB31_2
# BB#1:                                 # %_label9
	movl	_Pshadow_Pstandard_Ccode_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Ccode_Mclass, (%rax)
	movl	$45, 8(%rax)
	movq	(%r14), %rcx
	movq	%r14, %rdi
	movq	%rax, %rsi
	callq	*112(%rcx)
	negl	%ebx
	.align	16, 0x90
.LBB31_2:                               # %_label12
                                        # =>This Inner Loop Header: Depth=1
	movl	_Pshadow_Pstandard_Ccode_Mclass+44(%rip), %edi
	callq	malloc
	movslq	%ebx, %r15
	imulq	$1717986919, %r15, %rbx # imm = 0x66666667
	movq	%rbx, %rcx
	shrq	$63, %rcx
	movq	$_Pshadow_Pstandard_Ccode_Mclass, (%rax)
	sarq	$34, %rbx
	addl	%ecx, %ebx
	imull	$10, %ebx, %ecx
	negl	%ecx
	leal	48(%r15,%rcx), %ecx
	movl	%ecx, 8(%rax)
	movq	(%r14), %rcx
	movq	%r14, %rdi
	movq	%rax, %rsi
	callq	*112(%rcx)
	addl	$9, %r15d
	cmpl	$18, %r15d
	ja	.LBB31_2
# BB#3:                                 # %_label14
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*104(%rax)
	popq	%rbx
	popq	%r14
	popq	%r15
	ret
.Ltmp165:
	.size	_Pshadow_Pstandard_Cint_MtoString, .Ltmp165-_Pshadow_Pstandard_Cint_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_Cint_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Mcreate,@function
_Pshadow_Pstandard_Cint_Mcreate:        # @_Pshadow_Pstandard_Cint_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cint_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp166:
	.size	_Pshadow_Pstandard_Cint_Mcreate, .Ltmp166-_Pshadow_Pstandard_Cint_Mcreate

	.globl	_Pshadow_Pstandard_Clong_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Mcreate,@function
_Pshadow_Pstandard_Clong_Mcreate:       # @_Pshadow_Pstandard_Clong_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Clong_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp167:
	.size	_Pshadow_Pstandard_Clong_Mcreate, .Ltmp167-_Pshadow_Pstandard_Clong_Mcreate

	.globl	_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject,@function
_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:                                 # %_label2
	pushq	%rbp
.Ltmp172:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp173:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp174:
	.cfi_def_cfa_offset 32
.Ltmp175:
	.cfi_offset %rbx, -32
.Ltmp176:
	.cfi_offset %r14, -24
.Ltmp177:
	.cfi_offset %rbp, -16
	movq	%rdi, %r14
	movl	$.L_string2316, %edi
	testq	%rsi, %rsi
	cmovneq	%rsi, %rdi
	movq	(%rdi), %rax
	callq	*104(%rax)
	movq	%rax, %rbx
	movq	(%rbx), %rax
	movl	24(%r14), %ebp
	movq	%rbx, %rdi
	callq	*168(%rax)
	addl	%eax, %ebp
	movq	(%r14), %rax
	movq	%r14, %rdi
	movl	%ebp, %esi
	callq	*144(%rax)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*168(%rax)
	testl	%eax, %eax
	jle	.LBB34_3
# BB#1:                                 # %_label3.lr.ph
	xorl	%ebp, %ebp
	.align	16, 0x90
.LBB34_2:                               # %_label3
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
	callq	*168(%rax)
	incl	%ebp
	cmpl	%eax, %ebp
	jl	.LBB34_2
.LBB34_3:                               # %_label5
	movq	%r14, %rax
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp178:
	.size	_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject, .Ltmp178-_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CMutableString_Mlength
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_Mlength,@function
_Pshadow_Pstandard_CMutableString_Mlength: # @_Pshadow_Pstandard_CMutableString_Mlength
# BB#0:
	movl	24(%rdi), %eax
	ret
.Ltmp179:
	.size	_Pshadow_Pstandard_CMutableString_Mlength, .Ltmp179-_Pshadow_Pstandard_CMutableString_Mlength

	.globl	_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp186:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp187:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp188:
	.cfi_def_cfa_offset 32
	pushq	%r12
.Ltmp189:
	.cfi_def_cfa_offset 40
	pushq	%rbx
.Ltmp190:
	.cfi_def_cfa_offset 48
	subq	$16, %rsp
.Ltmp191:
	.cfi_def_cfa_offset 64
.Ltmp192:
	.cfi_offset %rbx, -48
.Ltmp193:
	.cfi_offset %r12, -40
.Ltmp194:
	.cfi_offset %r14, -32
.Ltmp195:
	.cfi_offset %r15, -24
.Ltmp196:
	.cfi_offset %rbp, -16
	movl	%esi, %ebp
	movq	%rdi, %rbx
	movq	8(%rbx), %r15
	movl	16(%rbx), %r12d
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %r14
	movl	%r12d, (%r14)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Cubyte_Mclass, 24(%rax)
	movq	%r14, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r15, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*144(%rcx)
	cmpl	%ebp, %eax
	jge	.LBB36_6
# BB#1:                                 # %_label6
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	%ebp, %edi
	callq	calloc
	movl	%ebp, 8(%rsp)
	movq	%rax, (%rsp)
	movl	24(%rbx), %ecx
	cmpl	%ebp, %ecx
	cmovgl	%ebp, %ecx
	testl	%ecx, %ecx
	jle	.LBB36_5
# BB#2:                                 # %_label9.lr.ph
	xorl	%eax, %eax
	movq	(%rsp), %rcx
	.align	16, 0x90
.LBB36_3:                               # %_label9
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
	jl	.LBB36_3
# BB#4:                                 # %_label10._label11_crit_edge
	movl	8(%rsp), %ebp
	movq	(%rsp), %rax
.LBB36_5:                               # %_label11
	movq	%rax, 8(%rbx)
	movl	%ebp, 16(%rbx)
.LBB36_6:                               # %_label8
	movq	%rbx, %rax
	addq	$16, %rsp
	popq	%rbx
	popq	%r12
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp197:
	.size	_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint, .Ltmp197-_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CMutableString_Mcapacity
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_Mcapacity,@function
_Pshadow_Pstandard_CMutableString_Mcapacity: # @_Pshadow_Pstandard_CMutableString_Mcapacity
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp202:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp203:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp204:
	.cfi_def_cfa_offset 32
.Ltmp205:
	.cfi_offset %rbx, -32
.Ltmp206:
	.cfi_offset %r14, -24
.Ltmp207:
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
	movq	(%rax), %rcx
	movq	%rax, %rdi
	popq	%rbx
	popq	%r14
	popq	%rbp
	jmpq	*144(%rcx)  # TAILCALL
.Ltmp208:
	.size	_Pshadow_Pstandard_CMutableString_Mcapacity, .Ltmp208-_Pshadow_Pstandard_CMutableString_Mcapacity
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CMutableString_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_MtoString,@function
_Pshadow_Pstandard_CMutableString_MtoString: # @_Pshadow_Pstandard_CMutableString_MtoString
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp216:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp217:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp218:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp219:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp220:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp221:
	.cfi_def_cfa_offset 56
	subq	$24, %rsp
.Ltmp222:
	.cfi_def_cfa_offset 80
.Ltmp223:
	.cfi_offset %rbx, -56
.Ltmp224:
	.cfi_offset %r12, -48
.Ltmp225:
	.cfi_offset %r13, -40
.Ltmp226:
	.cfi_offset %r14, -32
.Ltmp227:
	.cfi_offset %r15, -24
.Ltmp228:
	.cfi_offset %rbp, -16
	movq	%rdi, %r14
	movl	24(%r14), %r15d
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	%r15d, %edi
	callq	calloc
	movq	%rax, %r12
	movl	%r15d, 16(%rsp)
	movq	%r12, 8(%rsp)
	xorl	%ebx, %ebx
	movq	8(%rsp), %r13
	jmp	.LBB38_2
	.align	16, 0x90
.LBB38_1:                               # %_label12
                                        #   in Loop: Header=BB38_2 Depth=1
	movslq	%ebx, %rax
	movq	8(%r14), %rcx
	movb	(%rcx,%rax), %cl
	movb	%cl, (%r13,%rax)
	incl	%ebx
.LBB38_2:                               # %_label13
                                        # =>This Inner Loop Header: Depth=1
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbp
	movl	%r15d, (%rbp)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Cubyte_Mclass, 24(%rax)
	movq	%rbp, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r12, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*144(%rcx)
	cmpl	%eax, %ebx
	jl	.LBB38_1
# BB#3:                                 # %_label14
	movl	_Pshadow_Pstandard_CString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rbx)
	movl	16(%rsp), %edx
	movq	8(%rsp), %rsi
	movq	%rbx, %rdi
	callq	*_Pshadow_Pstandard_CString_Mclass+128(%rip)
	movq	%rbx, %rax
	addq	$24, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp229:
	.size	_Pshadow_Pstandard_CMutableString_MtoString, .Ltmp229-_Pshadow_Pstandard_CMutableString_MtoString
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
.Ltmp230:
	.size	_Pshadow_Pstandard_CMutableString_Mcreate, .Ltmp230-_Pshadow_Pstandard_CMutableString_Mcreate

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
.Ltmp231:
	.size	_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_Cint, .Ltmp231-_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject,@function
_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	pushq	%r14
.Ltmp235:
	.cfi_def_cfa_offset 16
	pushq	%rbx
.Ltmp236:
	.cfi_def_cfa_offset 24
	pushq	%rax
.Ltmp237:
	.cfi_def_cfa_offset 32
.Ltmp238:
	.cfi_offset %rbx, -24
.Ltmp239:
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
.Ltmp240:
	.size	_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject, .Ltmp240-_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte,@function
_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte
# BB#0:
	addb	%dil, %sil
	movb	%sil, %al
	ret
.Ltmp241:
	.size	_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte, .Ltmp241-_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte

	.globl	_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte,@function
_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte
# BB#0:
	subb	%sil, %dil
	movb	%dil, %al
	ret
.Ltmp242:
	.size	_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte, .Ltmp242-_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte

	.globl	_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte,@function
_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte
# BB#0:
	movb	%sil, %al
	mulb	%dil
	ret
.Ltmp243:
	.size	_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte, .Ltmp243-_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte

	.globl	_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte,@function
_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte
# BB#0:
	movb	%dil, %al
	cbtw
	idivb	%sil
	ret
.Ltmp244:
	.size	_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte, .Ltmp244-_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte

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
.Ltmp245:
	.size	_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte, .Ltmp245-_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte

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
.Ltmp246:
	.size	_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte, .Ltmp246-_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte

	.globl	_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte,@function
_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte
# BB#0:
	addb	%dil, %sil
	movb	%sil, %al
	ret
.Ltmp247:
	.size	_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte, .Ltmp247-_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte

	.globl	_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte,@function
_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte
# BB#0:
	subb	%sil, %dil
	movb	%dil, %al
	ret
.Ltmp248:
	.size	_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte, .Ltmp248-_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte

	.globl	_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte,@function
_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte
# BB#0:
	movb	%sil, %al
	mulb	%dil
	ret
.Ltmp249:
	.size	_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte, .Ltmp249-_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte

	.globl	_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte,@function
_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte
# BB#0:
	movzbl	%dil, %eax
                                        # kill: EAX<def> EAX<kill> AX<def>
	divb	%sil
	ret
.Ltmp250:
	.size	_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte, .Ltmp250-_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte

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
.Ltmp251:
	.size	_Pshadow_Pstandard_Cubyte_Mmodulus_Pshadow_Pstandard_Cubyte, .Ltmp251-_Pshadow_Pstandard_Cubyte_Mmodulus_Pshadow_Pstandard_Cubyte

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
.Ltmp252:
	.size	_Pshadow_Pstandard_Cubyte_Mcompare_Pshadow_Pstandard_Cubyte, .Ltmp252-_Pshadow_Pstandard_Cubyte_Mcompare_Pshadow_Pstandard_Cubyte

	.globl	_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort,@function
_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort
# BB#0:
	addl	%esi, %edi
	movw	%di, %ax
	ret
.Ltmp253:
	.size	_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort, .Ltmp253-_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort

	.globl	_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort,@function
_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort
# BB#0:
	subl	%esi, %edi
	movw	%di, %ax
	ret
.Ltmp254:
	.size	_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort, .Ltmp254-_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort

	.globl	_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort,@function
_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort
# BB#0:
	imull	%esi, %edi
	movw	%di, %ax
	ret
.Ltmp255:
	.size	_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort, .Ltmp255-_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort

	.globl	_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort,@function
_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort
# BB#0:
	movw	%di, %ax
	cwtd
	idivw	%si
	ret
.Ltmp256:
	.size	_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort, .Ltmp256-_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort

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
.Ltmp257:
	.size	_Pshadow_Pstandard_Cshort_Mmodulus_Pshadow_Pstandard_Cshort, .Ltmp257-_Pshadow_Pstandard_Cshort_Mmodulus_Pshadow_Pstandard_Cshort

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
.Ltmp258:
	.size	_Pshadow_Pstandard_Cshort_Mcompare_Pshadow_Pstandard_Cshort, .Ltmp258-_Pshadow_Pstandard_Cshort_Mcompare_Pshadow_Pstandard_Cshort

	.globl	_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort,@function
_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort
# BB#0:
	addl	%esi, %edi
	movw	%di, %ax
	ret
.Ltmp259:
	.size	_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort, .Ltmp259-_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort

	.globl	_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort,@function
_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort
# BB#0:
	subl	%esi, %edi
	movw	%di, %ax
	ret
.Ltmp260:
	.size	_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort, .Ltmp260-_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort

	.globl	_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort,@function
_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort
# BB#0:
	imull	%esi, %edi
	movw	%di, %ax
	ret
.Ltmp261:
	.size	_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort, .Ltmp261-_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort

	.globl	_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort,@function
_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort
# BB#0:
	movw	%di, %ax
	xorl	%edx, %edx
	divw	%si
	ret
.Ltmp262:
	.size	_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort, .Ltmp262-_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort

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
.Ltmp263:
	.size	_Pshadow_Pstandard_Cushort_Mmodulus_Pshadow_Pstandard_Cushort, .Ltmp263-_Pshadow_Pstandard_Cushort_Mmodulus_Pshadow_Pstandard_Cushort

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
.Ltmp264:
	.size	_Pshadow_Pstandard_Cushort_Mcompare_Pshadow_Pstandard_Cushort, .Ltmp264-_Pshadow_Pstandard_Cushort_Mcompare_Pshadow_Pstandard_Cushort

	.globl	_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint
# BB#0:
	addl	%esi, %edi
	movl	%edi, %eax
	ret
.Ltmp265:
	.size	_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint, .Ltmp265-_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint
# BB#0:
	subl	%esi, %edi
	movl	%edi, %eax
	ret
.Ltmp266:
	.size	_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint, .Ltmp266-_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint
# BB#0:
	imull	%esi, %edi
	movl	%edi, %eax
	ret
.Ltmp267:
	.size	_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint, .Ltmp267-_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint
# BB#0:
	movl	%edi, %eax
	cltd
	idivl	%esi
	ret
.Ltmp268:
	.size	_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint, .Ltmp268-_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint

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
.Ltmp269:
	.size	_Pshadow_Pstandard_Cint_Mmodulus_Pshadow_Pstandard_Cint, .Ltmp269-_Pshadow_Pstandard_Cint_Mmodulus_Pshadow_Pstandard_Cint

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
.Ltmp270:
	.size	_Pshadow_Pstandard_Cint_Mcompare_Pshadow_Pstandard_Cint, .Ltmp270-_Pshadow_Pstandard_Cint_Mcompare_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint
# BB#0:
	addl	%esi, %edi
	movl	%edi, %eax
	ret
.Ltmp271:
	.size	_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint, .Ltmp271-_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint
# BB#0:
	subl	%esi, %edi
	movl	%edi, %eax
	ret
.Ltmp272:
	.size	_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint, .Ltmp272-_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint
# BB#0:
	imull	%esi, %edi
	movl	%edi, %eax
	ret
.Ltmp273:
	.size	_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint, .Ltmp273-_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint
# BB#0:
	movl	%edi, %eax
	xorl	%edx, %edx
	divl	%esi
	ret
.Ltmp274:
	.size	_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint, .Ltmp274-_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint

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
.Ltmp275:
	.size	_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint, .Ltmp275-_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint

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
.Ltmp276:
	.size	_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint, .Ltmp276-_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong,@function
_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong
# BB#0:
	addq	%rsi, %rdi
	movq	%rdi, %rax
	ret
.Ltmp277:
	.size	_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong, .Ltmp277-_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong

	.globl	_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong,@function
_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong
# BB#0:
	subq	%rsi, %rdi
	movq	%rdi, %rax
	ret
.Ltmp278:
	.size	_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong, .Ltmp278-_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong

	.globl	_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong,@function
_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong
# BB#0:
	imulq	%rsi, %rdi
	movq	%rdi, %rax
	ret
.Ltmp279:
	.size	_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong, .Ltmp279-_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong

	.globl	_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong,@function
_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong
# BB#0:
	movq	%rdi, %rax
	cqto
	idivq	%rsi
	ret
.Ltmp280:
	.size	_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong, .Ltmp280-_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong

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
.Ltmp281:
	.size	_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong, .Ltmp281-_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong

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
.Ltmp282:
	.size	_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong, .Ltmp282-_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong

	.globl	_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong,@function
_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong
# BB#0:
	addq	%rsi, %rdi
	movq	%rdi, %rax
	ret
.Ltmp283:
	.size	_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong, .Ltmp283-_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong

	.globl	_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong,@function
_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong
# BB#0:
	subq	%rsi, %rdi
	movq	%rdi, %rax
	ret
.Ltmp284:
	.size	_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong, .Ltmp284-_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong

	.globl	_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong,@function
_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong
# BB#0:
	imulq	%rsi, %rdi
	movq	%rdi, %rax
	ret
.Ltmp285:
	.size	_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong, .Ltmp285-_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong

	.globl	_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong,@function
_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong
# BB#0:
	movq	%rdi, %rax
	xorl	%edx, %edx
	divq	%rsi
	ret
.Ltmp286:
	.size	_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong, .Ltmp286-_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong

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
.Ltmp287:
	.size	_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong, .Ltmp287-_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong

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
.Ltmp288:
	.size	_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong, .Ltmp288-_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong

	.globl	_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat,@function
_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat
# BB#0:
	vaddss	%xmm1, %xmm0, %xmm0
	ret
.Ltmp289:
	.size	_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat, .Ltmp289-_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat

	.globl	_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat,@function
_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat
# BB#0:
	vsubss	%xmm1, %xmm0, %xmm0
	ret
.Ltmp290:
	.size	_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat, .Ltmp290-_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat

	.globl	_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat,@function
_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat
# BB#0:
	vmulss	%xmm1, %xmm0, %xmm0
	ret
.Ltmp291:
	.size	_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat, .Ltmp291-_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat

	.globl	_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat,@function
_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat
# BB#0:
	vdivss	%xmm1, %xmm0, %xmm0
	ret
.Ltmp292:
	.size	_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat, .Ltmp292-_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat

	.globl	_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat,@function
_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat
# BB#0:
	jmp	fmodf                   # TAILCALL
.Ltmp293:
	.size	_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat, .Ltmp293-_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat

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
.Ltmp294:
	.size	_Pshadow_Pstandard_Cfloat_Mcompare_Pshadow_Pstandard_Cfloat, .Ltmp294-_Pshadow_Pstandard_Cfloat_Mcompare_Pshadow_Pstandard_Cfloat

	.globl	_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble,@function
_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble
# BB#0:
	vaddsd	%xmm1, %xmm0, %xmm0
	ret
.Ltmp295:
	.size	_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble, .Ltmp295-_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble

	.globl	_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble,@function
_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble
# BB#0:
	vsubsd	%xmm1, %xmm0, %xmm0
	ret
.Ltmp296:
	.size	_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble, .Ltmp296-_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble

	.globl	_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble,@function
_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble
# BB#0:
	vmulsd	%xmm1, %xmm0, %xmm0
	ret
.Ltmp297:
	.size	_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble, .Ltmp297-_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble

	.globl	_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble,@function
_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble
# BB#0:
	vdivsd	%xmm1, %xmm0, %xmm0
	ret
.Ltmp298:
	.size	_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble, .Ltmp298-_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble

	.globl	_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble,@function
_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble
# BB#0:
	jmp	fmod                    # TAILCALL
.Ltmp299:
	.size	_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble, .Ltmp299-_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble

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
.Ltmp300:
	.size	_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble, .Ltmp300-_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble

	.globl	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject,@function
_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	movq	(%rdi), %rax
	movq	96(%rax), %rax
	jmpq	*%rax  # TAILCALL
.Ltmp301:
	.size	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject, .Ltmp301-_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CObject_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_MtoString,@function
_Pshadow_Pstandard_CObject_MtoString:   # @_Pshadow_Pstandard_CObject_MtoString
	.cfi_startproc
# BB#0:
	pushq	%rax
.Ltmp303:
	.cfi_def_cfa_offset 16
	movq	(%rdi), %rax
	callq	*88(%rax)
	movq	24(%rax), %rax
	popq	%rdx
	ret
.Ltmp304:
	.size	_Pshadow_Pstandard_CObject_MtoString, .Ltmp304-_Pshadow_Pstandard_CObject_MtoString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CObject_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_Mcreate,@function
_Pshadow_Pstandard_CObject_Mcreate:     # @_Pshadow_Pstandard_CObject_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_CObject_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp305:
	.size	_Pshadow_Pstandard_CObject_Mcreate, .Ltmp305-_Pshadow_Pstandard_CObject_Mcreate

	.globl	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject,@function
_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
# BB#0:
	cmpq	%rsi, %rdi
	sete	%al
	ret
.Ltmp306:
	.size	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject, .Ltmp306-_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject

	.globl	_Pshadow_Pstandard_CObject_MgetClass
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_MgetClass,@function
_Pshadow_Pstandard_CObject_MgetClass:   # @_Pshadow_Pstandard_CObject_MgetClass
# BB#0:
	movq	(%rdi), %rax
	ret
.Ltmp307:
	.size	_Pshadow_Pstandard_CObject_MgetClass, .Ltmp307-_Pshadow_Pstandard_CObject_MgetClass

	.globl	_Pshadow_Pstandard_CObject_Mcopy
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_Mcopy,@function
_Pshadow_Pstandard_CObject_Mcopy:       # @_Pshadow_Pstandard_CObject_Mcopy
# BB#0:
	movq	%rdi, %rax
	ret
.Ltmp308:
	.size	_Pshadow_Pstandard_CObject_Mcopy, .Ltmp308-_Pshadow_Pstandard_CObject_Mcopy

	.globl	_Pshadow_Pstandard_CObject_Mfreeze
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CObject_Mfreeze,@function
_Pshadow_Pstandard_CObject_Mfreeze:     # @_Pshadow_Pstandard_CObject_Mfreeze
# BB#0:
	movq	%rdi, %rax
	ret
.Ltmp309:
	.size	_Pshadow_Pstandard_CObject_Mfreeze, .Ltmp309-_Pshadow_Pstandard_CObject_Mfreeze

	.globl	_Pshadow_Pstandard_Cshort_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cshort_Mcreate,@function
_Pshadow_Pstandard_Cshort_Mcreate:      # @_Pshadow_Pstandard_Cshort_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cshort_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp310:
	.size	_Pshadow_Pstandard_Cshort_Mcreate, .Ltmp310-_Pshadow_Pstandard_Cshort_Mcreate

	.globl	_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString,@function
_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString: # @_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp315:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp316:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp317:
	.cfi_def_cfa_offset 32
.Ltmp318:
	.cfi_offset %rbx, -32
.Ltmp319:
	.cfi_offset %r14, -24
.Ltmp320:
	.cfi_offset %rbp, -16
	movq	%rsi, %r14
	movq	%rdi, %rbx
	movq	(%rbx), %rax
	callq	*168(%rax)
	movl	%eax, %ebp
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*168(%rax)
	movl	%eax, %ecx
	xorb	%al, %al
	cmpl	%ecx, %ebp
	je	.LBB110_8
# BB#1:                                 # %_label1
	xorl	%ebp, %ebp
	.align	16, 0x90
.LBB110_2:                              # %_label3.i
                                        # =>This Inner Loop Header: Depth=1
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*168(%rax)
	cmpl	%eax, %ebp
	jge	.LBB110_6
# BB#3:                                 # %_label8.i
                                        #   in Loop: Header=BB110_2 Depth=1
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*168(%rax)
	cmpl	%eax, %ebp
	jge	.LBB110_6
# BB#4:                                 # %_label2.i
                                        #   in Loop: Header=BB110_2 Depth=1
	movslq	%ebp, %rax
	movq	8(%r14), %rcx
	movb	(%rcx,%rax), %cl
	movq	8(%rbx), %rdx
	movb	(%rdx,%rax), %al
	incl	%ebp
	subb	%cl, %al
	je	.LBB110_2
# BB#5:                                 # %_label5.i
	movsbl	%al, %ebx
	jmp	.LBB110_7
.LBB110_6:                              # %_label4.i
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*168(%rax)
	movl	%eax, %ebx
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*168(%rax)
	subl	%eax, %ebx
.LBB110_7:                              # %_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString.exit
	testl	%ebx, %ebx
	sete	%al
.LBB110_8:                              # %_label0
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp321:
	.size	_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString, .Ltmp321-_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString,@function
_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString: # @_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp326:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp327:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp328:
	.cfi_def_cfa_offset 32
.Ltmp329:
	.cfi_offset %rbx, -32
.Ltmp330:
	.cfi_offset %r14, -24
.Ltmp331:
	.cfi_offset %rbp, -16
	movq	%rsi, %r14
	movq	%rdi, %rbx
	xorl	%ebp, %ebp
	.align	16, 0x90
.LBB111_1:                              # %_label3
                                        # =>This Inner Loop Header: Depth=1
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*168(%rax)
	cmpl	%eax, %ebp
	jge	.LBB111_6
# BB#2:                                 # %_label8
                                        #   in Loop: Header=BB111_1 Depth=1
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*168(%rax)
	cmpl	%eax, %ebp
	jge	.LBB111_6
# BB#3:                                 # %_label2
                                        #   in Loop: Header=BB111_1 Depth=1
	movslq	%ebp, %rax
	movq	8(%r14), %rcx
	movb	(%rcx,%rax), %cl
	movq	8(%rbx), %rdx
	movb	(%rdx,%rax), %al
	incl	%ebp
	subb	%cl, %al
	je	.LBB111_1
# BB#4:                                 # %_label5
	movsbl	%al, %eax
	jmp	.LBB111_5
.LBB111_6:                              # %_label4
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*168(%rax)
	movl	%eax, %ebx
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*168(%rax)
	subl	%eax, %ebx
	movl	%ebx, %eax
.LBB111_5:                              # %_label5
	popq	%rbx
	popq	%r14
	popq	%rbp
	ret
.Ltmp332:
	.size	_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString, .Ltmp332-_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp340:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp341:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp342:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp343:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp344:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp345:
	.cfi_def_cfa_offset 56
	subq	$24, %rsp
.Ltmp346:
	.cfi_def_cfa_offset 80
.Ltmp347:
	.cfi_offset %rbx, -56
.Ltmp348:
	.cfi_offset %r12, -48
.Ltmp349:
	.cfi_offset %r13, -40
.Ltmp350:
	.cfi_offset %r14, -32
.Ltmp351:
	.cfi_offset %r15, -24
.Ltmp352:
	.cfi_offset %rbp, -16
	movl	%edx, 20(%rsp)          # 4-byte Spill
	movl	%esi, %r15d
	movq	%rdi, %rbx
	movl	_Pshadow_Pstandard_CString_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %r12
	movq	$_Pshadow_Pstandard_CString_Mclass, (%r12)
	movq	%r12, %rdi
	callq	*_Pshadow_Pstandard_CString_Mclass+64(%rip)
	movq	8(%rbx), %r14
	movl	16(%rbx), %ebp
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbx
	movl	%ebp, (%rbx)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbp
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbp)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbp)
	movq	$_Pshadow_Pstandard_Cubyte_Mclass, 24(%rbp)
	movq	%rbx, 8(%rbp)
	movl	$1, 16(%rbp)
	movq	%r14, 32(%rbp)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbx)
	movq	24(%rbp), %rax
	movq	%rax, 8(%rsp)           # 8-byte Spill
	movl	16(%rbp), %r13d
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	%r13d, %edi
	callq	calloc
	movq	%rax, %r14
	movl	20(%rsp), %eax          # 4-byte Reload
	subl	%r15d, %eax
	movl	%eax, (%r14)
	leaq	4(%r14), %rdi
	movq	8(%rbp), %rsi
	leal	-1(%r13), %edx
	addq	$4, %rsi
	callq	memcpy
	movl	%r15d, %ecx
	movq	8(%rsp), %rdx           # 8-byte Reload
	imull	48(%rdx), %ecx
	movq	32(%rbp), %rax
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbx)
	movq	%rdx, 24(%rbx)
	movl	%r13d, 16(%rbx)
	movq	%r14, 8(%rbx)
	movslq	%ecx, %rcx
	addq	%rax, %rcx
	movq	%rcx, 32(%rbx)
	movq	8(%rbx), %rax
	movl	(%rax), %eax
	movl	%eax, 16(%r12)
	movq	%rcx, 8(%r12)
	movq	%r12, %rax
	addq	$24, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp353:
	.size	_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint, .Ltmp353-_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_Mlength
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mlength,@function
_Pshadow_Pstandard_CString_Mlength:     # @_Pshadow_Pstandard_CString_Mlength
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp358:
	.cfi_def_cfa_offset 16
	pushq	%r14
.Ltmp359:
	.cfi_def_cfa_offset 24
	pushq	%rbx
.Ltmp360:
	.cfi_def_cfa_offset 32
.Ltmp361:
	.cfi_offset %rbx, -32
.Ltmp362:
	.cfi_offset %r14, -24
.Ltmp363:
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
	movq	(%rax), %rcx
	movq	%rax, %rdi
	popq	%rbx
	popq	%r14
	popq	%rbp
	jmpq	*144(%rcx)  # TAILCALL
.Ltmp364:
	.size	_Pshadow_Pstandard_CString_Mlength, .Ltmp364-_Pshadow_Pstandard_CString_Mlength
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint,@function
_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint
# BB#0:
	movslq	%esi, %rax
	movq	8(%rdi), %rcx
	movb	(%rcx,%rax), %al
	ret
.Ltmp365:
	.size	_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint, .Ltmp365-_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint

	.globl	_Pshadow_Pstandard_CString_MtoString
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_MtoString,@function
_Pshadow_Pstandard_CString_MtoString:   # @_Pshadow_Pstandard_CString_MtoString
# BB#0:
	movq	%rdi, %rax
	ret
.Ltmp366:
	.size	_Pshadow_Pstandard_CString_MtoString, .Ltmp366-_Pshadow_Pstandard_CString_MtoString

	.globl	_Pshadow_Pstandard_CString_Mchars
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mchars,@function
_Pshadow_Pstandard_CString_Mchars:      # @_Pshadow_Pstandard_CString_Mchars
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp373:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp374:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp375:
	.cfi_def_cfa_offset 32
	pushq	%r12
.Ltmp376:
	.cfi_def_cfa_offset 40
	pushq	%rbx
.Ltmp377:
	.cfi_def_cfa_offset 48
.Ltmp378:
	.cfi_offset %rbx, -48
.Ltmp379:
	.cfi_offset %r12, -40
.Ltmp380:
	.cfi_offset %r14, -32
.Ltmp381:
	.cfi_offset %r15, -24
.Ltmp382:
	.cfi_offset %rbp, -16
	movq	%rdi, %r14
	movq	8(%r14), %r15
	movl	16(%r14), %ebp
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
	movq	%r15, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*144(%rcx)
	movl	%eax, %r15d
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	%r15d, %edi
	callq	calloc
	movq	%rax, %r12
	xorl	%ebx, %ebx
	jmp	.LBB116_2
	.align	16, 0x90
.LBB116_1:                              # %_label10
                                        #   in Loop: Header=BB116_2 Depth=1
	movslq	%ebx, %rax
	movb	(%r12,%rax), %cl
	movq	8(%r14), %rdx
	movb	%cl, (%rdx,%rax)
	incl	%ebx
.LBB116_2:                              # %_label11
                                        # =>This Inner Loop Header: Depth=1
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbp
	movl	%r15d, (%rbp)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Cubyte_Mclass, 24(%rax)
	movq	%rbp, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r12, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*144(%rcx)
	cmpl	%eax, %ebx
	jl	.LBB116_1
# BB#3:                                 # %_label12
	movq	%r12, %rax
	movl	%r15d, %edx
	popq	%rbx
	popq	%r12
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp383:
	.size	_Pshadow_Pstandard_CString_Mchars, .Ltmp383-_Pshadow_Pstandard_CString_Mchars
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mcreate,@function
_Pshadow_Pstandard_CString_Mcreate:     # @_Pshadow_Pstandard_CString_Mcreate
# BB#0:
	pushq	%rbx
	movq	%rdi, %rbx
	movq	$_Pshadow_Pstandard_CString_Mclass, (%rbx)
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	xorl	%edi, %edi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$0, 16(%rbx)
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp384:
	.size	_Pshadow_Pstandard_CString_Mcreate, .Ltmp384-_Pshadow_Pstandard_CString_Mcreate

	.globl	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1,@function
_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1: # @_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp392:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp393:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp394:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp395:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp396:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp397:
	.cfi_def_cfa_offset 56
	subq	$24, %rsp
.Ltmp398:
	.cfi_def_cfa_offset 80
.Ltmp399:
	.cfi_offset %rbx, -56
.Ltmp400:
	.cfi_offset %r12, -48
.Ltmp401:
	.cfi_offset %r13, -40
.Ltmp402:
	.cfi_offset %r14, -32
.Ltmp403:
	.cfi_offset %r15, -24
.Ltmp404:
	.cfi_offset %rbp, -16
	movl	%edx, %r14d
	movq	%rsi, %r12
	movq	%rdi, %r13
	movq	$_Pshadow_Pstandard_CString_Mclass, (%r13)
	movl	%r14d, 16(%rsp)
	movq	%r12, 8(%rsp)
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	xorl	%ebx, %ebx
	xorl	%edi, %edi
	callq	calloc
	movq	%rax, 8(%r13)
	movl	$0, 16(%r13)
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %r15
	movl	%r14d, (%r15)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Cubyte_Mclass, 24(%rax)
	movq	%r15, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r12, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*144(%rcx)
	movl	%eax, %ebp
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	%ebp, %edi
	callq	calloc
	movl	%ebp, 16(%r13)
	movq	%rax, 8(%r13)
	movq	8(%rsp), %r15
	jmp	.LBB118_2
	.align	16, 0x90
.LBB118_1:                              # %_label13
                                        #   in Loop: Header=BB118_2 Depth=1
	movslq	%ebx, %rax
	movb	(%r15,%rax), %cl
	movq	8(%r13), %rdx
	movb	%cl, (%rdx,%rax)
	incl	%ebx
.LBB118_2:                              # %_label14
                                        # =>This Inner Loop Header: Depth=1
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbp
	movl	%r14d, (%rbp)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Cubyte_Mclass, 24(%rax)
	movq	%rbp, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r12, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*144(%rcx)
	cmpl	%eax, %ebx
	jl	.LBB118_1
# BB#3:                                 # %_label15
	movq	%r13, %rax
	addq	$24, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp405:
	.size	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1, .Ltmp405-_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1
	.cfi_endproc

	.globl	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1
	.align	16, 0x90
	.type	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1,@function
_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1: # @_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp413:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp414:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp415:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp416:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp417:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp418:
	.cfi_def_cfa_offset 56
	subq	$24, %rsp
.Ltmp419:
	.cfi_def_cfa_offset 80
.Ltmp420:
	.cfi_offset %rbx, -56
.Ltmp421:
	.cfi_offset %r12, -48
.Ltmp422:
	.cfi_offset %r13, -40
.Ltmp423:
	.cfi_offset %r14, -32
.Ltmp424:
	.cfi_offset %r15, -24
.Ltmp425:
	.cfi_offset %rbp, -16
	movl	%edx, %r14d
	movq	%rsi, %r12
	movq	%rdi, %r13
	movq	$_Pshadow_Pstandard_CString_Mclass, (%r13)
	movl	%r14d, 16(%rsp)
	movq	%r12, 8(%rsp)
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	xorl	%ebx, %ebx
	xorl	%edi, %edi
	callq	calloc
	movq	%rax, 8(%r13)
	movl	$0, 16(%r13)
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %r15
	movl	%r14d, (%r15)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Ccode_Mclass, 24(%rax)
	movq	%r15, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r12, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*144(%rcx)
	movl	%eax, %ebp
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	movl	%ebp, %edi
	callq	calloc
	movl	%ebp, 16(%r13)
	movq	%rax, 8(%r13)
	movq	8(%rsp), %r15
	jmp	.LBB119_2
	.align	16, 0x90
.LBB119_1:                              # %_label16
                                        #   in Loop: Header=BB119_2 Depth=1
	movslq	%ebx, %rax
	movb	(%r15,%rax,4), %cl
	movq	8(%r13), %rdx
	movb	%cl, (%rdx,%rax)
	incl	%ebx
.LBB119_2:                              # %_label17
                                        # =>This Inner Loop Header: Depth=1
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbp
	movl	%r14d, (%rbp)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rax)
	movq	$_Pshadow_Pstandard_Ccode_Mclass, 24(%rax)
	movq	%rbp, 8(%rax)
	movl	$1, 16(%rax)
	movq	%r12, 32(%rax)
	movq	(%rax), %rcx
	movq	%rax, %rdi
	callq	*144(%rcx)
	cmpl	%eax, %ebx
	jl	.LBB119_1
# BB#3:                                 # %_label18
	movq	%r13, %rax
	addq	$24, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp426:
	.size	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1, .Ltmp426-_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1
	.cfi_endproc

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
	movl	_Pshadow_Pstandard_Cubyte_Mclass+48(%rip), %esi
	xorl	%edi, %edi
	callq	calloc
	movq	%rax, 8(%rbx)
	movl	$0, 16(%rbx)
	movq	8(%r14), %rax
	movl	16(%r14), %ecx
	movl	%ecx, 16(%rbx)
	movq	%rax, 8(%rbx)
	movq	%rbx, %rax
	addq	$8, %rsp
	popq	%rbx
	popq	%r14
	ret
.Ltmp427:
	.size	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString, .Ltmp427-_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString

	.globl	_Pshadow_Pstandard_Cubyte_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cubyte_Mcreate,@function
_Pshadow_Pstandard_Cubyte_Mcreate:      # @_Pshadow_Pstandard_Cubyte_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cubyte_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp428:
	.size	_Pshadow_Pstandard_Cubyte_Mcreate, .Ltmp428-_Pshadow_Pstandard_Cubyte_Mcreate

	.globl	_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint
# BB#0:                                 # %_label2
	movl	8(%rdi), %eax
	cmpl	%esi, %eax
	cmovael	%esi, %eax
	ret
.Ltmp429:
	.size	_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint, .Ltmp429-_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint,@function
_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint
# BB#0:                                 # %_label5
	movl	8(%rdi), %eax
	cmpl	%esi, %eax
	cmovbel	%esi, %eax
	ret
.Ltmp430:
	.size	_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint, .Ltmp430-_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint

	.globl	_Pshadow_Pstandard_Cuint_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cuint_Mcreate,@function
_Pshadow_Pstandard_Cuint_Mcreate:       # @_Pshadow_Pstandard_Cuint_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cuint_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp431:
	.size	_Pshadow_Pstandard_Cuint_Mcreate, .Ltmp431-_Pshadow_Pstandard_Cuint_Mcreate

	.globl	_Pshadow_Pstandard_Culong_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Culong_Mcreate,@function
_Pshadow_Pstandard_Culong_Mcreate:      # @_Pshadow_Pstandard_Culong_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Culong_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp432:
	.size	_Pshadow_Pstandard_Culong_Mcreate, .Ltmp432-_Pshadow_Pstandard_Culong_Mcreate

	.globl	_Pshadow_Pstandard_Cushort_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pstandard_Cushort_Mcreate,@function
_Pshadow_Pstandard_Cushort_Mcreate:     # @_Pshadow_Pstandard_Cushort_Mcreate
# BB#0:
	movq	$_Pshadow_Pstandard_Cushort_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp433:
	.size	_Pshadow_Pstandard_Cushort_Mcreate, .Ltmp433-_Pshadow_Pstandard_Cushort_Mcreate

	.globl	_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject,@function
_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject: # @_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	pushq	%rbx
.Ltmp436:
	.cfi_def_cfa_offset 16
.Ltmp437:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	movl	$.L_string2316, %edi
	testq	%rsi, %rsi
	cmovneq	%rsi, %rdi
	movq	(%rdi), %rax
	callq	*104(%rax)
	movl	16(%rax), %edx
	movq	8(%rax), %rdi
	movl	$stdout, %eax
	testq	%rax, %rax
	je	.LBB127_2
# BB#1:
	movq	stdout(%rip), %rcx
	jmp	.LBB127_3
.LBB127_2:
	movq	_imp___iob(%rip), %rcx
	addq	$48, %rcx
.LBB127_3:                              # %_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject.exit
	movl	$1, %esi
	callq	fwrite
	movl	$stdout, %eax
	testq	%rax, %rax
	je	.LBB127_5
# BB#4:
	movq	stdout(%rip), %rsi
	jmp	.LBB127_6
.LBB127_5:
	movq	_imp___iob(%rip), %rsi
	addq	$48, %rsi
.LBB127_6:                              # %_Pshadow_Pio_CConsole_MprintLine.exit
	movl	$10, %edi
	callq	fputc
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp438:
	.size	_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject, .Ltmp438-_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_MprintLine
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_MprintLine,@function
_Pshadow_Pio_CConsole_MprintLine:       # @_Pshadow_Pio_CConsole_MprintLine
# BB#0:
	pushq	%rbx
	movq	%rdi, %rbx
	movl	$stdout, %eax
	testq	%rax, %rax
	je	.LBB128_2
# BB#1:
	movq	stdout(%rip), %rsi
	jmp	.LBB128_3
.LBB128_2:
	movq	_imp___iob(%rip), %rsi
	addq	$48, %rsi
.LBB128_3:                              # %_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString.exit
	movl	$10, %edi
	callq	fputc
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp439:
	.size	_Pshadow_Pio_CConsole_MprintLine, .Ltmp439-_Pshadow_Pio_CConsole_MprintLine

	.globl	_Pshadow_Pio_CConsole_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_Mcreate,@function
_Pshadow_Pio_CConsole_Mcreate:          # @_Pshadow_Pio_CConsole_Mcreate
# BB#0:
	movq	$_Pshadow_Pio_CConsole_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp440:
	.size	_Pshadow_Pio_CConsole_Mcreate, .Ltmp440-_Pshadow_Pio_CConsole_Mcreate

	.globl	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject,@function
_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject: # @_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:                                 # %_label2
	pushq	%rbx
.Ltmp443:
	.cfi_def_cfa_offset 16
.Ltmp444:
	.cfi_offset %rbx, -16
	movq	%rdi, %rbx
	movl	$.L_string2316, %edi
	testq	%rsi, %rsi
	cmovneq	%rsi, %rdi
	movq	(%rdi), %rax
	callq	*104(%rax)
	movl	16(%rax), %edx
	movq	8(%rax), %rdi
	movl	$stdout, %eax
	testq	%rax, %rax
	je	.LBB130_2
# BB#1:
	movq	stdout(%rip), %rcx
	jmp	.LBB130_3
.LBB130_2:
	movq	_imp___iob(%rip), %rcx
	addq	$48, %rcx
.LBB130_3:                              # %_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString.exit
	movl	$1, %esi
	callq	fwrite
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp445:
	.size	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject, .Ltmp445-_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject
	.cfi_endproc

	.globl	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString
	.align	16, 0x90
	.type	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString,@function
_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString: # @_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString
# BB#0:
	pushq	%rbx
	movq	%rdi, %rbx
	movl	16(%rsi), %edx
	movq	8(%rsi), %rdi
	movl	$stdout, %eax
	testq	%rax, %rax
	je	.LBB131_2
# BB#1:
	movq	stdout(%rip), %rcx
	jmp	.LBB131_3
.LBB131_2:
	movq	_imp___iob(%rip), %rcx
	addq	$48, %rcx
.LBB131_3:                              # %out.exit
	movl	$1, %esi
	callq	fwrite
	movq	%rbx, %rax
	popq	%rbx
	ret
.Ltmp446:
	.size	_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString, .Ltmp446-_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString

	.globl	_Pshadow_Ptest_CArrayTest_Mcreate
	.align	16, 0x90
	.type	_Pshadow_Ptest_CArrayTest_Mcreate,@function
_Pshadow_Ptest_CArrayTest_Mcreate:      # @_Pshadow_Ptest_CArrayTest_Mcreate
# BB#0:
	movq	$_Pshadow_Ptest_CArrayTest_Mclass, (%rdi)
	movq	%rdi, %rax
	ret
.Ltmp447:
	.size	_Pshadow_Ptest_CArrayTest_Mcreate, .Ltmp447-_Pshadow_Ptest_CArrayTest_Mcreate

	.globl	_Pshadow_Ptest_CArrayTest_Mmain
	.align	16, 0x90
	.type	_Pshadow_Ptest_CArrayTest_Mmain,@function
_Pshadow_Ptest_CArrayTest_Mmain:        # @_Pshadow_Ptest_CArrayTest_Mmain
	.cfi_startproc
# BB#0:
	pushq	%rbp
.Ltmp455:
	.cfi_def_cfa_offset 16
	pushq	%r15
.Ltmp456:
	.cfi_def_cfa_offset 24
	pushq	%r14
.Ltmp457:
	.cfi_def_cfa_offset 32
	pushq	%r13
.Ltmp458:
	.cfi_def_cfa_offset 40
	pushq	%r12
.Ltmp459:
	.cfi_def_cfa_offset 48
	pushq	%rbx
.Ltmp460:
	.cfi_def_cfa_offset 56
	subq	$40, %rsp
.Ltmp461:
	.cfi_def_cfa_offset 96
.Ltmp462:
	.cfi_offset %rbx, -56
.Ltmp463:
	.cfi_offset %r12, -48
.Ltmp464:
	.cfi_offset %r13, -40
.Ltmp465:
	.cfi_offset %r14, -32
.Ltmp466:
	.cfi_offset %r15, -24
.Ltmp467:
	.cfi_offset %rbp, -16
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	$1, %edi
	callq	calloc
	movq	%rax, %rbp
	movl	$5, (%rbp)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbx
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbx)
	movq	%rbx, %rdi
	movl	$_Pshadow_Pstandard_CObject_Mclass, %esi
	movq	%rbp, %rdx
	movl	$1, %ecx
	callq	*_Pshadow_Pstandard_CArray_Mclass+112(%rip)
	movq	%rbx, 32(%rsp)
	movl	_Pshadow_Pstandard_Cint_Mclass+44(%rip), %edi
	callq	malloc
	xorl	%ebp, %ebp
	movq	$_Pshadow_Pstandard_Cint_Mclass, (%rax)
	movl	$0, 8(%rax)
	movq	(%rbx), %rcx
	movq	%rbx, %rdi
	xorl	%esi, %esi
	movq	%rax, %rdx
	callq	*136(%rcx)
	movl	_Pshadow_Pstandard_Ccode_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Ccode_Mclass, (%rax)
	movl	$50, 8(%rax)
	movq	(%rbx), %rcx
	movq	%rbx, %rdi
	movl	$1, %esi
	movq	%rax, %rdx
	callq	*136(%rcx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	movl	$2, %esi
	movl	$.L_string1322, %edx
	callq	*136(%rax)
	movl	_Pshadow_Pstandard_Cint_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Cint_Mclass, (%rax)
	movl	$88, 8(%rax)
	movq	(%rbx), %rcx
	movq	%rbx, %rdi
	movl	$3, %esi
	movq	%rax, %rdx
	callq	*136(%rcx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	movl	$4, %esi
	xorl	%edx, %edx
	callq	*136(%rax)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*56(%rax)
	movq	%rax, %r15
	movq	%r15, 24(%rsp)
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %r14
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%r14)
	movq	24(%r15), %rax
	movq	%rax, (%rsp)            # 8-byte Spill
	movl	16(%r15), %r12d
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	%r12d, %edi
	callq	calloc
	movq	%rax, %r13
	movl	$3, (%r13)
	leal	-1(%r12), %edx
	leaq	4(%r13), %rdi
	movq	8(%r15), %rsi
	addq	$4, %rsi
	callq	memcpy
	movq	(%rsp), %rcx            # 8-byte Reload
	movslq	48(%rcx), %rax
	addq	32(%r15), %rax
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%r14)
	movq	%rcx, 24(%r14)
	movl	%r12d, 16(%r14)
	movq	%r13, 8(%r14)
	movq	%rax, 32(%r14)
	movq	%r14, 16(%rsp)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*144(%rax)
	testl	%eax, %eax
	jle	.LBB133_2
	.align	16, 0x90
.LBB133_1:                              # %_label0
                                        # =>This Inner Loop Header: Depth=1
	movl	_Pshadow_Pstandard_Cint_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pstandard_Cint_Mclass, (%rax)
	movl	%ebp, 8(%rax)
	movq	(%rbx), %rcx
	movq	%rbx, %rdi
	movl	%ebp, %esi
	movq	%rax, %rdx
	callq	*136(%rcx)
	movq	(%rbx), %rax
	movq	%rbx, %rdi
	callq	*144(%rax)
	incl	%ebp
	cmpl	%eax, %ebp
	jl	.LBB133_1
.LBB133_2:                              # %_label2
	movq	(%r14), %rax
	movq	%r14, %rdi
	xorl	%esi, %esi
	movl	$.L_string2324, %edx
	callq	*136(%rax)
	movq	(%r14), %rax
	movq	%r14, %rdi
	movl	$1, %esi
	movl	$.L_string3326, %edx
	callq	*136(%rax)
	movq	(%r14), %rax
	movq	%r14, %rdi
	movl	$2, %esi
	movl	$.L_string4328, %edx
	callq	*136(%rax)
	cmpq	$0, _Pshadow_Pio_CConsole_Minstance(%rip)
	jne	.LBB133_4
# BB#3:                                 # %_label3
	movl	_Pshadow_Pio_CConsole_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pio_CConsole_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pio_CConsole_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pio_CConsole_Minstance(%rip)
.LBB133_4:                              # %_label4
	movl	$.L_string2316, %edi
	testq	%rbx, %rbx
	cmovneq	%rbx, %rdi
	movq	(%rdi), %rax
	callq	*104(%rax)
	movl	16(%rax), %edx
	movq	8(%rax), %rdi
	movl	$stdout, %eax
	testq	%rax, %rax
	je	.LBB133_6
# BB#5:
	movq	stdout(%rip), %rcx
	jmp	.LBB133_7
.LBB133_6:
	movq	_imp___iob(%rip), %rcx
	addq	$48, %rcx
.LBB133_7:                              # %_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject.exit.i
	movl	$1, %esi
	callq	fwrite
	movl	$stdout, %eax
	testq	%rax, %rax
	je	.LBB133_9
# BB#8:
	movq	stdout(%rip), %rsi
	jmp	.LBB133_10
.LBB133_9:
	movq	_imp___iob(%rip), %rsi
	addq	$48, %rsi
.LBB133_10:                             # %_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject.exit
	movl	$10, %edi
	callq	fputc
	cmpq	$0, _Pshadow_Pio_CConsole_Minstance(%rip)
	jne	.LBB133_12
# BB#11:                                # %_label5
	movl	_Pshadow_Pio_CConsole_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pio_CConsole_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pio_CConsole_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pio_CConsole_Minstance(%rip)
.LBB133_12:                             # %_label6
	movl	$.L_string2316, %edi
	movq	24(%rsp), %rax
	testq	%rax, %rax
	cmovneq	%rax, %rdi
	movq	(%rdi), %rax
	callq	*104(%rax)
	movl	16(%rax), %edx
	movq	8(%rax), %rdi
	movl	$stdout, %eax
	testq	%rax, %rax
	je	.LBB133_14
# BB#13:
	movq	stdout(%rip), %rcx
	jmp	.LBB133_15
.LBB133_14:
	movq	_imp___iob(%rip), %rcx
	addq	$48, %rcx
.LBB133_15:                             # %_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject.exit.i4
	movl	$1, %esi
	callq	fwrite
	movl	$stdout, %eax
	testq	%rax, %rax
	je	.LBB133_17
# BB#16:
	movq	stdout(%rip), %rsi
	jmp	.LBB133_18
.LBB133_17:
	movq	_imp___iob(%rip), %rsi
	addq	$48, %rsi
.LBB133_18:                             # %_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject.exit6
	movl	$10, %edi
	callq	fputc
	cmpq	$0, _Pshadow_Pio_CConsole_Minstance(%rip)
	jne	.LBB133_20
# BB#19:                                # %_label7
	movl	_Pshadow_Pio_CConsole_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pio_CConsole_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pio_CConsole_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pio_CConsole_Minstance(%rip)
.LBB133_20:                             # %_label8
	movl	$.L_string2316, %edi
	movq	16(%rsp), %rax
	testq	%rax, %rax
	cmovneq	%rax, %rdi
	movq	(%rdi), %rax
	callq	*104(%rax)
	movl	16(%rax), %edx
	movq	8(%rax), %rdi
	movl	$stdout, %eax
	testq	%rax, %rax
	je	.LBB133_22
# BB#21:
	movq	stdout(%rip), %rcx
	jmp	.LBB133_23
.LBB133_22:
	movq	_imp___iob(%rip), %rcx
	addq	$48, %rcx
.LBB133_23:                             # %_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject.exit.i8
	movl	$1, %esi
	callq	fwrite
	movl	$stdout, %eax
	testq	%rax, %rax
	je	.LBB133_25
# BB#24:
	movq	stdout(%rip), %rsi
	jmp	.LBB133_26
.LBB133_25:
	movq	_imp___iob(%rip), %rsi
	addq	$48, %rsi
.LBB133_26:                             # %_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject.exit10
	movl	$10, %edi
	callq	fputc
	movl	$0, 12(%rsp)
	movq	32(%rsp), %r14
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*144(%rax)
	testl	%eax, %eax
	js	.LBB133_40
# BB#27:
	xorl	%r12d, %r12d
	.align	16, 0x90
.LBB133_28:                             # %_label9
                                        # =>This Loop Header: Depth=1
                                        #     Child Loop BB133_29 Depth 2
	movl	%r12d, 8(%rsp)
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*144(%rax)
	cmpl	%eax, %r12d
	jg	.LBB133_39
	.align	16, 0x90
.LBB133_29:                             # %_label12
                                        #   Parent Loop BB133_28 Depth=1
                                        # =>  This Inner Loop Header: Depth=2
	cmpq	$0, _Pshadow_Pio_CConsole_Minstance(%rip)
	jne	.LBB133_31
# BB#30:                                # %_label15
                                        #   in Loop: Header=BB133_29 Depth=2
	movl	_Pshadow_Pio_CConsole_Mclass+44(%rip), %edi
	callq	malloc
	movq	$_Pshadow_Pio_CConsole_Mclass, (%rax)
	movq	%rax, %rdi
	callq	*_Pshadow_Pio_CConsole_Mclass+64(%rip)
	movq	%rax, _Pshadow_Pio_CConsole_Minstance(%rip)
.LBB133_31:                             # %_label16
                                        #   in Loop: Header=BB133_29 Depth=2
	movl	12(%rsp), %r13d
	movl	_Pshadow_Pstandard_CArray_Mclass+44(%rip), %edi
	callq	malloc
	movq	%rax, %rbp
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbp)
	movq	24(%r14), %rax
	movq	%rax, (%rsp)            # 8-byte Spill
	movl	16(%r14), %r15d
	movl	_Pshadow_Pstandard_Cint_Mclass+48(%rip), %esi
	movl	%r15d, %edi
	callq	calloc
	movq	%rax, %rbx
	subl	%r13d, %r12d
	movl	%r12d, (%rbx)
	leaq	4(%rbx), %rdi
	movq	8(%r14), %rsi
	leal	-1(%r15), %edx
	addq	$4, %rsi
	callq	memcpy
	testq	%rbp, %rbp
	movq	%rbp, %rdi
	movl	$.L_string2316, %eax
	cmoveq	%rax, %rdi
	movq	(%rsp), %rcx            # 8-byte Reload
	imull	48(%rcx), %r13d
	movq	32(%r14), %rax
	movq	$_Pshadow_Pstandard_CArray_Mclass, (%rbp)
	movq	%rcx, 24(%rbp)
	movl	%r15d, 16(%rbp)
	movq	%rbx, 8(%rbp)
	movslq	%r13d, %rcx
	addq	%rax, %rcx
	movq	%rcx, 32(%rbp)
	movq	(%rdi), %rax
	callq	*104(%rax)
	movl	$stdout, %ecx
	testq	%rcx, %rcx
	movl	16(%rax), %edx
	movq	8(%rax), %rdi
	je	.LBB133_33
# BB#32:                                #   in Loop: Header=BB133_29 Depth=2
	movq	stdout(%rip), %rcx
	jmp	.LBB133_34
	.align	16, 0x90
.LBB133_33:                             #   in Loop: Header=BB133_29 Depth=2
	movq	_imp___iob(%rip), %rcx
	addq	$48, %rcx
.LBB133_34:                             # %_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject.exit.i12
                                        #   in Loop: Header=BB133_29 Depth=2
	movl	$1, %esi
	callq	fwrite
	movl	$stdout, %eax
	testq	%rax, %rax
	je	.LBB133_36
# BB#35:                                #   in Loop: Header=BB133_29 Depth=2
	movq	stdout(%rip), %rsi
	jmp	.LBB133_37
	.align	16, 0x90
.LBB133_36:                             #   in Loop: Header=BB133_29 Depth=2
	movq	_imp___iob(%rip), %rsi
	addq	$48, %rsi
.LBB133_37:                             # %_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject.exit14
                                        #   in Loop: Header=BB133_29 Depth=2
	movl	$10, %edi
	callq	fputc
	movl	8(%rsp), %r12d
	incl	%r12d
	movl	%r12d, 8(%rsp)
	movq	32(%rsp), %r14
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*144(%rax)
	cmpl	%eax, %r12d
	jle	.LBB133_29
# BB#38:                                # %_label13._label14_crit_edge
                                        #   in Loop: Header=BB133_28 Depth=1
	movl	12(%rsp), %r12d
.LBB133_39:                             # %_label14
                                        #   in Loop: Header=BB133_28 Depth=1
	incl	%r12d
	movl	%r12d, 12(%rsp)
	movq	(%r14), %rax
	movq	%r14, %rdi
	callq	*144(%rax)
	cmpl	%eax, %r12d
	jle	.LBB133_28
.LBB133_40:                             # %_label11
	addq	$40, %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	ret
.Ltmp468:
	.size	_Pshadow_Ptest_CArrayTest_Mmain, .Ltmp468-_Pshadow_Ptest_CArrayTest_Mmain
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
	.quad	_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject
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
	.size	.L_string0, 24

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
	.size	.L_string1, 24

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
	.size	.L_string2, 24

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
	.size	.L_string3, 24

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
	.size	.L_string4, 24

	.type	_Pshadow_Pstandard_Cboolean_Mclass,@object # @_Pshadow_Pstandard_Cboolean_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cboolean_Mclass
	.align	16
_Pshadow_Pstandard_Cboolean_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string012
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

	.type	.L_array011,@object     # @_array011
	.section	.rodata,"a",@progbits
.L_array011:
	.ascii	 "boolean"
	.size	.L_array011, 7

	.type	.L_string012,@object    # @_string012
	.align	16
.L_string012:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array011
	.long	7                       # 0x7
	.zero	4
	.size	.L_string012, 24

	.type	_Pshadow_Pstandard_Cbyte_Mclass,@object # @_Pshadow_Pstandard_Cbyte_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cbyte_Mclass
	.align	16
_Pshadow_Pstandard_Cbyte_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string018
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

	.type	.L_array017,@object     # @_array017
	.section	.rodata.cst4,"aM",@progbits,4
.L_array017:
	.ascii	 "byte"
	.size	.L_array017, 4

	.type	.L_string018,@object    # @_string018
	.section	.rodata,"a",@progbits
	.align	16
.L_string018:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array017
	.long	4                       # 0x4
	.zero	4
	.size	.L_string018, 24

	.type	_Pshadow_Pstandard_CClass_Mclass,@object # @_Pshadow_Pstandard_CClass_Mclass
	.data
	.globl	_Pshadow_Pstandard_CClass_Mclass
	.align	16
_Pshadow_Pstandard_CClass_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string024
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

	.type	.L_array023,@object     # @_array023
	.section	.rodata,"a",@progbits
	.align	16
.L_array023:
	.ascii	 "shadow.standard@Class"
	.size	.L_array023, 21

	.type	.L_string024,@object    # @_string024
	.align	16
.L_string024:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array023
	.long	21                      # 0x15
	.zero	4
	.size	.L_string024, 24

	.type	.L_string126,@object    # @_string126
	.align	16
.L_string126:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array163
	.zero	4
	.zero	4
	.size	.L_string126, 24

	.type	_Pshadow_Pstandard_Ccode_Mclass,@object # @_Pshadow_Pstandard_Ccode_Mclass
	.data
	.globl	_Pshadow_Pstandard_Ccode_Mclass
	.align	16
_Pshadow_Pstandard_Ccode_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string042
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
	.size	_Pshadow_Pstandard_Ccode_Mclass, 112

	.type	.L_array041,@object     # @_array041
	.section	.rodata.cst4,"aM",@progbits,4
.L_array041:
	.ascii	 "code"
	.size	.L_array041, 4

	.type	.L_string042,@object    # @_string042
	.section	.rodata,"a",@progbits
	.align	16
.L_string042:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array041
	.long	4                       # 0x4
	.zero	4
	.size	.L_string042, 24

	.type	_Pshadow_Pstandard_Cdouble_Mclass,@object # @_Pshadow_Pstandard_Cdouble_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cdouble_Mclass
	.align	16
_Pshadow_Pstandard_Cdouble_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string050
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
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble
	.quad	_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble
	.quad	_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble
	.quad	_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble
	.quad	_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble
	.quad	_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble
	.size	_Pshadow_Pstandard_Cdouble_Mclass, 160

	.type	.L_array049,@object     # @_array049
	.section	.rodata,"a",@progbits
.L_array049:
	.ascii	 "double"
	.size	.L_array049, 6

	.type	.L_string050,@object    # @_string050
	.align	16
.L_string050:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array049
	.long	6                       # 0x6
	.zero	4
	.size	.L_string050, 24

	.type	_Pshadow_Pstandard_Cfloat_Mclass,@object # @_Pshadow_Pstandard_Cfloat_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cfloat_Mclass
	.align	16
_Pshadow_Pstandard_Cfloat_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string056
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

	.type	.L_array055,@object     # @_array055
	.section	.rodata,"a",@progbits
.L_array055:
	.ascii	 "float"
	.size	.L_array055, 5

	.type	.L_string056,@object    # @_string056
	.align	16
.L_string056:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array055
	.long	5                       # 0x5
	.zero	4
	.size	.L_string056, 24

	.type	_Pshadow_Pstandard_CInterface_Mclass,@object # @_Pshadow_Pstandard_CInterface_Mclass
	.data
	.globl	_Pshadow_Pstandard_CInterface_Mclass
	.align	16
_Pshadow_Pstandard_CInterface_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string062
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

	.type	.L_array061,@object     # @_array061
	.section	.rodata,"a",@progbits
	.align	16
.L_array061:
	.ascii	 "shadow.standard@Interface"
	.size	.L_array061, 25

	.type	.L_string062,@object    # @_string062
	.align	16
.L_string062:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array061
	.long	25                      # 0x19
	.zero	4
	.size	.L_string062, 24

	.type	.L_array163,@object     # @_array163
.L_array163:
	.size	.L_array163, 0

	.type	.L_string164,@object    # @_string164
	.align	16
.L_string164:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array163
	.zero	4
	.zero	4
	.size	.L_string164, 24

	.type	_Pshadow_Pstandard_Cint_Mclass,@object # @_Pshadow_Pstandard_Cint_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cint_Mclass
	.align	16
_Pshadow_Pstandard_Cint_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string074
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
	.size	_Pshadow_Pstandard_Cint_Mclass, 184

	.type	.L_array073,@object     # @_array073
	.section	.rodata,"a",@progbits
.L_array073:
	.ascii	 "int"
	.size	.L_array073, 3

	.type	.L_string074,@object    # @_string074
	.align	16
.L_string074:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array073
	.long	3                       # 0x3
	.zero	4
	.size	.L_string074, 24

	.type	_Pshadow_Pstandard_Clong_Mclass,@object # @_Pshadow_Pstandard_Clong_Mclass
	.data
	.globl	_Pshadow_Pstandard_Clong_Mclass
	.align	16
_Pshadow_Pstandard_Clong_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string088
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
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong
	.quad	_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong
	.quad	_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong
	.quad	_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong
	.quad	_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong
	.quad	_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong
	.size	_Pshadow_Pstandard_Clong_Mclass, 160

	.type	.L_array087,@object     # @_array087
	.section	.rodata.cst4,"aM",@progbits,4
.L_array087:
	.ascii	 "long"
	.size	.L_array087, 4

	.type	.L_string088,@object    # @_string088
	.section	.rodata,"a",@progbits
	.align	16
.L_string088:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array087
	.long	4                       # 0x4
	.zero	4
	.size	.L_string088, 24

	.type	_Pshadow_Pstandard_CMutableString_Mclass,@object # @_Pshadow_Pstandard_CMutableString_Mclass
	.data
	.globl	_Pshadow_Pstandard_CMutableString_Mclass
	.align	16
_Pshadow_Pstandard_CMutableString_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string094
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
	.size	_Pshadow_Pstandard_CMutableString_Mclass, 160

	.type	.L_array093,@object     # @_array093
	.section	.rodata,"a",@progbits
	.align	16
.L_array093:
	.ascii	 "shadow.standard@MutableString"
	.size	.L_array093, 29

	.type	.L_string094,@object    # @_string094
	.align	16
.L_string094:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array093
	.long	29                      # 0x1d
	.zero	4
	.size	.L_string094, 24

	.type	_Pshadow_Pstandard_CObject_Mclass,@object # @_Pshadow_Pstandard_CObject_Mclass
	.data
	.globl	_Pshadow_Pstandard_CObject_Mclass
	.align	16
_Pshadow_Pstandard_CObject_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0236
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

	.type	.L_array0235,@object    # @_array0235
	.section	.rodata,"a",@progbits
	.align	16
.L_array0235:
	.ascii	 "shadow.standard@Object"
	.size	.L_array0235, 22

	.type	.L_string0236,@object   # @_string0236
	.align	16
.L_string0236:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0235
	.long	22                      # 0x16
	.zero	4
	.size	.L_string0236, 24

	.type	_Pshadow_Pstandard_Cshort_Mclass,@object # @_Pshadow_Pstandard_Cshort_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cshort_Mclass
	.align	16
_Pshadow_Pstandard_Cshort_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0254
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

	.type	.L_array0253,@object    # @_array0253
	.section	.rodata,"a",@progbits
.L_array0253:
	.ascii	 "short"
	.size	.L_array0253, 5

	.type	.L_string0254,@object   # @_string0254
	.align	16
.L_string0254:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0253
	.long	5                       # 0x5
	.zero	4
	.size	.L_string0254, 24

	.type	_Pshadow_Pstandard_CString_Mclass,@object # @_Pshadow_Pstandard_CString_Mclass
	.data
	.globl	_Pshadow_Pstandard_CString_Mclass
	.align	16
_Pshadow_Pstandard_CString_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0260
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	0                       # 0x0
	.long	(0+24)&-1
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
	.quad	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1
	.quad	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1
	.quad	_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString
	.quad	_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString
	.quad	_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint
	.quad	_Pshadow_Pstandard_CString_Mlength
	.quad	_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint
	.size	_Pshadow_Pstandard_CString_Mclass, 184

	.type	.L_array0259,@object    # @_array0259
	.section	.rodata,"a",@progbits
	.align	16
.L_array0259:
	.ascii	 "shadow.standard@String"
	.size	.L_array0259, 22

	.type	.L_string0260,@object   # @_string0260
	.align	16
.L_string0260:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0259
	.long	22                      # 0x16
	.zero	4
	.size	.L_string0260, 24

	.type	_Pshadow_Pstandard_Cubyte_Mclass,@object # @_Pshadow_Pstandard_Cubyte_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cubyte_Mclass
	.align	16
_Pshadow_Pstandard_Cubyte_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0286
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

	.type	.L_array0285,@object    # @_array0285
	.section	.rodata,"a",@progbits
.L_array0285:
	.ascii	 "ubyte"
	.size	.L_array0285, 5

	.type	.L_string0286,@object   # @_string0286
	.align	16
.L_string0286:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0285
	.long	5                       # 0x5
	.zero	4
	.size	.L_string0286, 24

	.type	_Pshadow_Pstandard_Cuint_Mclass,@object # @_Pshadow_Pstandard_Cuint_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cuint_Mclass
	.align	16
_Pshadow_Pstandard_Cuint_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0292
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
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint
	.quad	_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint
	.size	_Pshadow_Pstandard_Cuint_Mclass, 176

	.type	.L_array0291,@object    # @_array0291
	.section	.rodata.cst4,"aM",@progbits,4
.L_array0291:
	.ascii	 "uint"
	.size	.L_array0291, 4

	.type	.L_string0292,@object   # @_string0292
	.section	.rodata,"a",@progbits
	.align	16
.L_string0292:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0291
	.long	4                       # 0x4
	.zero	4
	.size	.L_string0292, 24

	.type	_Pshadow_Pstandard_Culong_Mclass,@object # @_Pshadow_Pstandard_Culong_Mclass
	.data
	.globl	_Pshadow_Pstandard_Culong_Mclass
	.align	16
_Pshadow_Pstandard_Culong_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0302
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
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong
	.quad	_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong
	.quad	_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong
	.quad	_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong
	.quad	_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong
	.quad	_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong
	.size	_Pshadow_Pstandard_Culong_Mclass, 160

	.type	.L_array0301,@object    # @_array0301
	.section	.rodata,"a",@progbits
.L_array0301:
	.ascii	 "ulong"
	.size	.L_array0301, 5

	.type	.L_string0302,@object   # @_string0302
	.align	16
.L_string0302:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0301
	.long	5                       # 0x5
	.zero	4
	.size	.L_string0302, 24

	.type	_Pshadow_Pstandard_Cushort_Mclass,@object # @_Pshadow_Pstandard_Cushort_Mclass
	.data
	.globl	_Pshadow_Pstandard_Cushort_Mclass
	.align	16
_Pshadow_Pstandard_Cushort_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0308
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

	.type	.L_array0307,@object    # @_array0307
	.section	.rodata,"a",@progbits
.L_array0307:
	.ascii	 "ushort"
	.size	.L_array0307, 6

	.type	.L_string0308,@object   # @_string0308
	.align	16
.L_string0308:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0307
	.long	6                       # 0x6
	.zero	4
	.size	.L_string0308, 24

	.type	_Pshadow_Pio_CConsole_Mclass,@object # @_Pshadow_Pio_CConsole_Mclass
	.data
	.globl	_Pshadow_Pio_CConsole_Mclass
	.align	16
_Pshadow_Pio_CConsole_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0312
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
	.quad	_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pio_CConsole_MprintLine
	.size	_Pshadow_Pio_CConsole_Mclass, 136

	.type	_Pshadow_Pio_CConsole_Minstance,@object # @_Pshadow_Pio_CConsole_Minstance
	.bss
	.globl	_Pshadow_Pio_CConsole_Minstance
	.align	8
_Pshadow_Pio_CConsole_Minstance:
	.quad	0
	.size	_Pshadow_Pio_CConsole_Minstance, 8

	.type	.L_array0311,@object    # @_array0311
	.section	.rodata,"a",@progbits
	.align	16
.L_array0311:
	.ascii	 "shadow.io@Console"
	.size	.L_array0311, 17

	.type	.L_string0312,@object   # @_string0312
	.align	16
.L_string0312:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0311
	.long	17                      # 0x11
	.zero	4
	.size	.L_string0312, 24

	.type	.L_array2315,@object    # @_array2315
	.section	.rodata.cst4,"aM",@progbits,4
.L_array2315:
	.ascii	 "null"
	.size	.L_array2315, 4

	.type	.L_string2316,@object   # @_string2316
	.section	.rodata,"a",@progbits
	.align	16
.L_string2316:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array2315
	.long	4                       # 0x4
	.zero	4
	.size	.L_string2316, 24

	.type	_Pshadow_Ptest_CArrayTest_Mclass,@object # @_Pshadow_Ptest_CArrayTest_Mclass
	.data
	.globl	_Pshadow_Ptest_CArrayTest_Mclass
	.align	16
_Pshadow_Ptest_CArrayTest_Mclass:
	.quad	_Pshadow_Pstandard_CClass_Mclass
	.zero	16
	.quad	.L_string0320
	.quad	_Pshadow_Pstandard_CObject_Mclass
	.long	0                       # 0x0
	.long	(0+8)&-1
	.long	(0+8)&-1
	.zero	4
	.quad	_Pshadow_Pstandard_CObject_Mcopy
	.quad	_Pshadow_Ptest_CArrayTest_Mcreate
	.quad	_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_Mfreeze
	.quad	_Pshadow_Pstandard_CObject_MgetClass
	.quad	_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject
	.quad	_Pshadow_Pstandard_CObject_MtoString
	.quad	_Pshadow_Ptest_CArrayTest_Mmain
	.size	_Pshadow_Ptest_CArrayTest_Mclass, 120

	.type	.L_array0319,@object    # @_array0319
	.section	.rodata,"a",@progbits
	.align	16
.L_array0319:
	.ascii	 "shadow.test@ArrayTest"
	.size	.L_array0319, 21

	.type	.L_string0320,@object   # @_string0320
	.align	16
.L_string0320:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array0319
	.long	21                      # 0x15
	.zero	4
	.size	.L_string0320, 24

	.type	.L_array1321,@object    # @_array1321
	.section	.rodata.cst4,"aM",@progbits,4
.L_array1321:
	.ascii	 "four"
	.size	.L_array1321, 4

	.type	.L_string1322,@object   # @_string1322
	.section	.rodata,"a",@progbits
	.align	16
.L_string1322:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array1321
	.long	4                       # 0x4
	.zero	4
	.size	.L_string1322, 24

	.type	.L_array2323,@object    # @_array2323
	.section	.rodata.cst4,"aM",@progbits,4
.L_array2323:
	.ascii	 "zero"
	.size	.L_array2323, 4

	.type	.L_string2324,@object   # @_string2324
	.section	.rodata,"a",@progbits
	.align	16
.L_string2324:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array2323
	.long	4                       # 0x4
	.zero	4
	.size	.L_string2324, 24

	.type	.L_array3325,@object    # @_array3325
.L_array3325:
	.ascii	 "one"
	.size	.L_array3325, 3

	.type	.L_string3326,@object   # @_string3326
	.align	16
.L_string3326:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array3325
	.long	3                       # 0x3
	.zero	4
	.size	.L_string3326, 24

	.type	.L_array4327,@object    # @_array4327
.L_array4327:
	.ascii	 "two"
	.size	.L_array4327, 3

	.type	.L_string4328,@object   # @_string4328
	.align	16
.L_string4328:
	.quad	_Pshadow_Pstandard_CString_Mclass
	.quad	.L_array4327
	.long	3                       # 0x3
	.zero	4
	.size	.L_string4328, 24

	.weak	stdout
	.weak	_imp___iob

	.section	".note.GNU-stack","",@progbits
