	.def	 _Mconstructor;
	.scl	2;
	.type	32;
	.endef
	.text
	.globl	_Mconstructor
	.align	16, 0x90
_Mconstructor:                          # @_Mconstructor
	.cfi_startproc
# BB#0:                                 # %entry
	ret
	.cfi_endproc

	.def	 _Mmain_R_Pshadow_Pstandard_CString_A1;
	.scl	2;
	.type	32;
	.endef
	.globl	_Mmain_R_Pshadow_Pstandard_CString_A1
	.align	16, 0x90
_Mmain_R_Pshadow_Pstandard_CString_A1:  # @_Mmain_R_Pshadow_Pstandard_CString_A1
	.cfi_startproc
# BB#0:                                 # %entry
	pushq	%rbp
.Ltmp2:
	.cfi_def_cfa_offset 16
.Ltmp3:
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
.Ltmp4:
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movq	$6, -8(%rbp)
	movl	$16, %eax
	callq	___chkstk
	movq	%rsp, %r8
	movl	$4, (%r8)
	movl	$16, %eax
	callq	___chkstk
	movq	%rsp, %rdx
	movl	$3, (%rdx)
	movl	$16, %eax
	callq	___chkstk
	movl	$45, (%rsp)
	movl	(%rdx), %eax
	addl	%eax, (%r8)
	addl	$7, (%rdx)
	movb	(%rdx), %cl
	movl	(%r8), %eax
	addl	%eax, %eax
	shll	%cl, %eax
	movl	%eax, (%rsp)
	movl	(%r8), %ecx
	imull	(%rdx), %ecx
                                        # kill: CL<def> CL<kill> ECX<kill>
	shll	%cl, %eax
	movl	%eax, (%rsp)
	movl	(%r8), %eax
	orl	(%rdx), %eax
	movl	%eax, (%r8)
	andl	(%rdx), %eax
	movl	%eax, (%r8)
	xorl	(%rdx), %eax
	movl	%eax, (%r8)
	orl	(%rdx), %eax
	movl	%eax, (%rdx)
	andl	(%r8), %eax
	movl	%eax, (%rdx)
	xorl	(%r8), %eax
	movl	%eax, (%rdx)
	movq	%rbp, %rsp
	popq	%rbp
	ret
	.cfi_endproc


