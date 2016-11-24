	.file	"ShadowSignaler.c"
	.text
	.def	_InvalidatePointer;	.scl	3;	.type	32;	.endef
_InvalidatePointer:
LFB4:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$24, %esp
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	%eax, (%esp)
	call	_free
	movl	8(%ebp), %eax
	movl	$0, (%eax)
	nop
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE4:
	.globl	___ShadowSignaler_Initialize
	.def	___ShadowSignaler_Initialize;	.scl	2;	.type	32;	.endef
___ShadowSignaler_Initialize:
LFB5:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$40, %esp
	movl	$8, (%esp)
	call	_malloc
	movl	%eax, -12(%ebp)
	movl	-12(%ebp), %eax
	movl	$0, 4(%esp)
	movl	%eax, (%esp)
	call	_pthread_cond_init
	testl	%eax, %eax
	je	L3
	leal	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	_InvalidatePointer
	jmp	L4
L3:
	movl	-12(%ebp), %eax
	addl	$4, %eax
	movl	$0, 4(%esp)
	movl	%eax, (%esp)
	call	_pthread_mutex_init
	testl	%eax, %eax
	je	L4
	movl	-12(%ebp), %eax
	addl	$4, %eax
	movl	%eax, (%esp)
	call	_pthread_cond_destroy
	leal	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	_InvalidatePointer
L4:
	movl	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	___createShadowPointer
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE5:
	.globl	___ShadowSignaler_Destroy
	.def	___ShadowSignaler_Destroy;	.scl	2;	.type	32;	.endef
___ShadowSignaler_Destroy:
LFB6:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$40, %esp
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	___extractRawPointer
	movl	%eax, -12(%ebp)
	movl	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	_pthread_cond_destroy
	testl	%eax, %eax
	sete	%al
	movzbl	%al, %eax
	movl	%eax, -16(%ebp)
	movl	-12(%ebp), %eax
	addl	$4, %eax
	movl	%eax, (%esp)
	call	_pthread_mutex_destroy
	testl	%eax, %eax
	sete	%al
	movzbl	%al, %eax
	andl	%eax, -16(%ebp)
	movl	-16(%ebp), %eax
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE6:
	.globl	___ShadowSignaler_Wait
	.def	___ShadowSignaler_Wait;	.scl	2;	.type	32;	.endef
___ShadowSignaler_Wait:
LFB7:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$40, %esp
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	___extractRawPointer
	movl	%eax, -12(%ebp)
	movl	-12(%ebp), %eax
	addl	$4, %eax
	movl	%eax, (%esp)
	call	_pthread_mutex_lock
	movl	-12(%ebp), %eax
	leal	4(%eax), %edx
	movl	-12(%ebp), %eax
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	call	_pthread_cond_wait
	movl	%eax, -16(%ebp)
	movl	-12(%ebp), %eax
	addl	$4, %eax
	movl	%eax, (%esp)
	call	_pthread_mutex_unlock
	cmpl	$0, -16(%ebp)
	sete	%al
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE7:
	.def	___divdi3;	.scl	2;	.type	32;	.endef
	.def	___moddi3;	.scl	2;	.type	32;	.endef
	.globl	___ShadowSignaler_WaitTimeout
	.def	___ShadowSignaler_WaitTimeout;	.scl	2;	.type	32;	.endef
___ShadowSignaler_WaitTimeout:
LFB8:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$56, %esp
	movl	12(%ebp), %eax
	movl	%eax, -32(%ebp)
	movl	16(%ebp), %eax
	movl	%eax, -28(%ebp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	___extractRawPointer
	movl	%eax, -12(%ebp)
	movl	-32(%ebp), %eax
	movl	-28(%ebp), %edx
	movl	$1000000000, 8(%esp)
	movl	$0, 12(%esp)
	movl	%eax, (%esp)
	movl	%edx, 4(%esp)
	call	___divdi3
	movl	%eax, -24(%ebp)
	movl	-32(%ebp), %eax
	movl	-28(%ebp), %edx
	movl	$1000000000, 8(%esp)
	movl	$0, 12(%esp)
	movl	%eax, (%esp)
	movl	%edx, 4(%esp)
	call	___moddi3
	movl	%eax, -20(%ebp)
	movl	-12(%ebp), %eax
	addl	$4, %eax
	movl	%eax, (%esp)
	call	_pthread_mutex_lock
	movl	-12(%ebp), %eax
	leal	4(%eax), %ecx
	movl	-12(%ebp), %eax
	leal	-24(%ebp), %edx
	movl	%edx, 8(%esp)
	movl	%ecx, 4(%esp)
	movl	%eax, (%esp)
	call	_pthread_cond_timedwait
	movl	%eax, -16(%ebp)
	movl	-12(%ebp), %eax
	addl	$4, %eax
	movl	%eax, (%esp)
	call	_pthread_mutex_unlock
	cmpl	$0, -16(%ebp)
	setne	%al
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE8:
	.globl	___ShadowSignaler_Broadcast
	.def	___ShadowSignaler_Broadcast;	.scl	2;	.type	32;	.endef
___ShadowSignaler_Broadcast:
LFB9:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$40, %esp
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	___extractRawPointer
	movl	%eax, -12(%ebp)
	movl	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	_pthread_cond_broadcast
	testl	%eax, %eax
	sete	%al
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE9:
	.ident	"GCC: (i686-posix-dwarf-rev0, Built by MinGW-W64 project) 5.1.0"
	.def	_free;	.scl	2;	.type	32;	.endef
	.def	_malloc;	.scl	2;	.type	32;	.endef
	.def	_pthread_cond_init;	.scl	2;	.type	32;	.endef
	.def	_pthread_mutex_init;	.scl	2;	.type	32;	.endef
	.def	_pthread_cond_destroy;	.scl	2;	.type	32;	.endef
	.def	___createShadowPointer;	.scl	2;	.type	32;	.endef
	.def	___extractRawPointer;	.scl	2;	.type	32;	.endef
	.def	_pthread_mutex_destroy;	.scl	2;	.type	32;	.endef
	.def	_pthread_mutex_lock;	.scl	2;	.type	32;	.endef
	.def	_pthread_cond_wait;	.scl	2;	.type	32;	.endef
	.def	_pthread_mutex_unlock;	.scl	2;	.type	32;	.endef
	.def	_pthread_cond_timedwait;	.scl	2;	.type	32;	.endef
	.def	_pthread_cond_broadcast;	.scl	2;	.type	32;	.endef
