	.file	"ShadowThread.c"
	.text
	.globl	___ShadowThread_Spawn
	.def	___ShadowThread_Spawn;	.scl	2;	.type	32;	.endef
___ShadowThread_Spawn:
LFB4:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$40, %esp
	movl	$4, (%esp)
	call	_malloc
	movl	%eax, -12(%ebp)
	movl	12(%ebp), %eax
	movl	%eax, 12(%esp)
	movl	8(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	$0, 4(%esp)
	movl	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	_pthread_create
	testl	%eax, %eax
	je	L2
	movl	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	_free
	movl	$0, -12(%ebp)
L2:
	movl	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	___createShadowPointer
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE4:
	.globl	___ShadowThread_Yield
	.def	___ShadowThread_Yield;	.scl	2;	.type	32;	.endef
___ShadowThread_Yield:
LFB5:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$8, %esp
	call	_sched_yield
	testl	%eax, %eax
	sete	%al
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE5:
	.ident	"GCC: (i686-posix-dwarf-rev0, Built by MinGW-W64 project) 5.1.0"
	.def	_malloc;	.scl	2;	.type	32;	.endef
	.def	_pthread_create;	.scl	2;	.type	32;	.endef
	.def	_free;	.scl	2;	.type	32;	.endef
	.def	___createShadowPointer;	.scl	2;	.type	32;	.endef
	.def	_sched_yield;	.scl	2;	.type	32;	.endef
