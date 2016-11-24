	.file	"ShadowSystem.c"
	.def	___udivdi3;	.scl	2;	.type	32;	.endef
	.text
	.globl	___ShadowSystem_GetNanoTime
	.def	___ShadowSystem_GetNanoTime;	.scl	2;	.type	32;	.endef
___ShadowSystem_GetNanoTime:
LFB77:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	pushl	%ebx
	subl	$36, %esp
	.cfi_offset 3, -12
	leal	-16(%ebp), %eax
	movl	%eax, (%esp)
	movl	__imp__QueryPerformanceFrequency@4, %eax
	call	*%eax
	subl	$4, %esp
	testl	%eax, %eax
	jne	L2
	movl	$0, %eax
	movl	$0, %edx
	jmp	L5
L2:
	leal	-24(%ebp), %eax
	movl	%eax, (%esp)
	movl	__imp__QueryPerformanceCounter@4, %eax
	call	*%eax
	subl	$4, %esp
	testl	%eax, %eax
	jne	L4
	movl	$0, %eax
	movl	$0, %edx
	jmp	L5
L4:
	movl	-24(%ebp), %eax
	movl	-20(%ebp), %edx
	imull	$1000000000, %edx, %ebx
	imull	$0, %eax, %ecx
	addl	%ebx, %ecx
	movl	$1000000000, %ebx
	mull	%ebx
	addl	%edx, %ecx
	movl	%ecx, %edx
	movl	-16(%ebp), %ecx
	movl	-12(%ebp), %ebx
	movl	%ecx, 8(%esp)
	movl	%ebx, 12(%esp)
	movl	%eax, (%esp)
	movl	%edx, 4(%esp)
	call	___udivdi3
L5:
	movl	-4(%ebp), %ebx
	leave
	.cfi_restore 5
	.cfi_restore 3
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE77:
	.globl	___ShadowSystem_GetEpochNanoTime
	.def	___ShadowSystem_GetEpochNanoTime;	.scl	2;	.type	32;	.endef
___ShadowSystem_GetEpochNanoTime:
LFB78:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	pushl	%ebx
	subl	$36, %esp
	.cfi_offset 3, -12
	leal	-16(%ebp), %eax
	movl	%eax, (%esp)
	movl	__imp__GetSystemTimeAsFileTime@4, %eax
	call	*%eax
	subl	$4, %esp
	movl	-16(%ebp), %eax
	movl	-12(%ebp), %edx
	imull	$100, %edx, %ebx
	imull	$0, %eax, %ecx
	addl	%ebx, %ecx
	movl	$100, %ebx
	mull	%ebx
	addl	%edx, %ecx
	movl	%ecx, %edx
	addl	$-1282015232, %eax
	adcl	$1583777012, %edx
	movl	-4(%ebp), %ebx
	leave
	.cfi_restore 5
	.cfi_restore 3
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE78:
	.ident	"GCC: (i686-posix-dwarf-rev0, Built by MinGW-W64 project) 5.1.0"
