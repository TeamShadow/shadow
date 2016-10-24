	.text
	.def	 @feat.00;
	.scl	3;
	.type	0;
	.endef
	.globl	@feat.00
@feat.00 = 1
	.def	 _getx;
	.scl	2;
	.type	32;
	.endef
	.globl	_getx
	.align	16, 0x90
_getx:                                  # @getx
# BB#0:                                 # %entry
	movl	_x, %eax
	retl

	.def	 _thread_func;
	.scl	2;
	.type	32;
	.endef
	.globl	_thread_func
	.align	16, 0x90
_thread_func:                           # @thread_func
# BB#0:                                 # %entry
	pushl	%ebp
	movl	%esp, %ebp
	addl	$10000000, _x           # imm = 0x989680
	xorl	%eax, %eax
	popl	%ebp
	retl

	.def	 _main;
	.scl	2;
	.type	32;
	.endef
	.globl	_main
	.align	16, 0x90
_main:                                  # @main
# BB#0:                                 # %entry
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%esi
	subl	$16, %esp
	calll	___main
	movl	$4, 4(%esp)
	movl	$5, (%esp)
	calll	_calloc
	movl	%eax, %esi
	movl	%esi, (%esp)
	movl	$0, 12(%esp)
	movl	$_thread_func, 8(%esp)
	movl	$0, 4(%esp)
	calll	_pthread_create
	leal	4(%esi), %eax
	movl	%eax, (%esp)
	movl	$0, 12(%esp)
	movl	$_thread_func, 8(%esp)
	movl	$0, 4(%esp)
	calll	_pthread_create
	leal	8(%esi), %eax
	movl	%eax, (%esp)
	movl	$0, 12(%esp)
	movl	$_thread_func, 8(%esp)
	movl	$0, 4(%esp)
	calll	_pthread_create
	leal	12(%esi), %eax
	movl	%eax, (%esp)
	movl	$0, 12(%esp)
	movl	$_thread_func, 8(%esp)
	movl	$0, 4(%esp)
	calll	_pthread_create
	leal	16(%esi), %eax
	movl	%eax, (%esp)
	movl	$0, 12(%esp)
	movl	$_thread_func, 8(%esp)
	movl	$0, 4(%esp)
	calll	_pthread_create
	movl	(%esi), %eax
	movl	%eax, (%esp)
	movl	$0, 4(%esp)
	calll	_pthread_join
	movl	4(%esi), %eax
	movl	%eax, (%esp)
	movl	$0, 4(%esp)
	calll	_pthread_join
	movl	8(%esi), %eax
	movl	%eax, (%esp)
	movl	$0, 4(%esp)
	calll	_pthread_join
	movl	12(%esi), %eax
	movl	%eax, (%esp)
	movl	$0, 4(%esp)
	calll	_pthread_join
	movl	16(%esi), %eax
	movl	%eax, (%esp)
	movl	$0, 4(%esp)
	calll	_pthread_join
	movl	_x, %eax
	movl	%eax, 4(%esp)
	movl	$L_.str, (%esp)
	calll	_printf
	xorl	%eax, %eax
	addl	$16, %esp
	popl	%esi
	popl	%ebp
	retl

	.bss
	.globl	_x                      # @x
	.align	4
_x:
	.long	0                       # 0x0

	.section	.rdata,"dr"
L_.str:                                 # @.str
	.asciz	"%d\n"


