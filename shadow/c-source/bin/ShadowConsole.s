	.file	"ShadowConsole.c"
	.text
	.globl	___ShadowConsole_Initialize
	.def	___ShadowConsole_Initialize;	.scl	2;	.type	32;	.endef
___ShadowConsole_Initialize:
LFB77:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$24, %esp
	movl	$65001, (%esp)
	movl	__imp__SetConsoleCP@4, %eax
	call	*%eax
	subl	$4, %esp
	movl	$65001, (%esp)
	movl	__imp__SetConsoleOutputCP@4, %eax
	call	*%eax
	subl	$4, %esp
	nop
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE77:
	.ident	"GCC: (i686-posix-dwarf-rev0, Built by MinGW-W64 project) 5.1.0"
