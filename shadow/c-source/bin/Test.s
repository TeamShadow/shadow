	.file	"Test.c"
	.text
	.globl	___Test_Allocate
	.def	___Test_Allocate;	.scl	2;	.type	32;	.endef
___Test_Allocate:
LFB4:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$24, %esp
	movl	$4, (%esp)
	call	_malloc
	movl	%eax, (%esp)
	call	___createShadowPointer
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE4:
	.ident	"GCC: (i686-posix-dwarf-rev0, Built by MinGW-W64 project) 5.1.0"
	.def	_malloc;	.scl	2;	.type	32;	.endef
	.def	___createShadowPointer;	.scl	2;	.type	32;	.endef
