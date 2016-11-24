	.file	"ShadowMutex.c"
	.text
	.globl	___ShadowMutex_Initialize
	.def	___ShadowMutex_Initialize;	.scl	2;	.type	32;	.endef
___ShadowMutex_Initialize:
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
	movl	$0, 4(%esp)
	movl	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	_pthread_mutex_init
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
	.globl	___ShadowMutex_Destroy
	.def	___ShadowMutex_Destroy;	.scl	2;	.type	32;	.endef
___ShadowMutex_Destroy:
LFB5:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$24, %esp
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	___extractRawPointer
	movl	%eax, (%esp)
	call	_pthread_mutex_destroy
	testl	%eax, %eax
	sete	%al
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE5:
	.globl	___ShadowMutex_Lock
	.def	___ShadowMutex_Lock;	.scl	2;	.type	32;	.endef
___ShadowMutex_Lock:
LFB6:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$24, %esp
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	___extractRawPointer
	movl	%eax, (%esp)
	call	_pthread_mutex_lock
	testl	%eax, %eax
	sete	%al
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE6:
	.globl	___ShadowMutex_Unlock
	.def	___ShadowMutex_Unlock;	.scl	2;	.type	32;	.endef
___ShadowMutex_Unlock:
LFB7:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$24, %esp
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	___extractRawPointer
	movl	%eax, (%esp)
	call	_pthread_mutex_unlock
	testl	%eax, %eax
	sete	%al
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE7:
	.ident	"GCC: (i686-posix-dwarf-rev0, Built by MinGW-W64 project) 5.1.0"
	.def	_malloc;	.scl	2;	.type	32;	.endef
	.def	_pthread_mutex_init;	.scl	2;	.type	32;	.endef
	.def	_free;	.scl	2;	.type	32;	.endef
	.def	___createShadowPointer;	.scl	2;	.type	32;	.endef
	.def	___extractRawPointer;	.scl	2;	.type	32;	.endef
	.def	_pthread_mutex_destroy;	.scl	2;	.type	32;	.endef
	.def	_pthread_mutex_lock;	.scl	2;	.type	32;	.endef
	.def	_pthread_mutex_unlock;	.scl	2;	.type	32;	.endef
