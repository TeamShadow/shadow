	.def	 _main;
	.scl	2;
	.type	32;
	.endef
	.text
	.globl	_main
	.align	16, 0x90
_main:                                  # @main
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp2:
	.cfi_def_cfa_offset 8
Ltmp3:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp4:
	.cfi_def_cfa_register %ebp
	pushl	%eax
	calll	___main
	movl	$0, (%esp)
	calll	__Pshadow_Ptest_CConsoleTest_Mmain
	xorl	%eax, %eax
	addl	$4, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.def	 _shadow.generic.load;
	.scl	2;
	.type	32;
	.endef
	.globl	_shadow.generic.load
	.align	16, 0x90
_shadow.generic.load:                   # @shadow.generic.load
	.cfi_startproc
# BB#0:
	xorl	%eax, %eax
	xorl	%edx, %edx
	ret
	.cfi_endproc

	.def	 _shadow.generic.store;
	.scl	2;
	.type	32;
	.endef
	.globl	_shadow.generic.store
	.align	16, 0x90
_shadow.generic.store:                  # @shadow.generic.store
	.cfi_startproc
# BB#0:
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CArray_Mdims;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CArray_Mdims
	.align	16, 0x90
__Pshadow_Pstandard_CArray_Mdims:       # @_Pshadow_Pstandard_CArray_Mdims
	.cfi_startproc
# BB#0:
	pushl	%ebx
Ltmp9:
	.cfi_def_cfa_offset 8
	pushl	%edi
Ltmp10:
	.cfi_def_cfa_offset 12
	pushl	%esi
Ltmp11:
	.cfi_def_cfa_offset 16
	subl	$24, %esp
Ltmp12:
	.cfi_def_cfa_offset 40
Ltmp13:
	.cfi_offset %esi, -16
Ltmp14:
	.cfi_offset %edi, -12
Ltmp15:
	.cfi_offset %ebx, -8
	movl	40(%esp), %eax
	movl	%eax, 20(%esp)
	movl	16(%eax), %edi
	movl	20(%eax), %ebx
	movl	$0, 4(%esp)
	movl	$24, (%esp)
	calll	_malloc
	movl	%eax, %esi
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%ebx, (%eax)
	movl	%eax, 12(%esp)
	movl	%edi, 4(%esp)
	movl	%esi, (%esp)
	movl	$1, 16(%esp)
	movl	$0, 8(%esp)
	calll	__Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*64(%ecx)
	addl	$24, %esp
	popl	%esi
	popl	%edi
	popl	%ebx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CArray_Mlength;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CArray_Mlength
	.align	16, 0x90
__Pshadow_Pstandard_CArray_Mlength:     # @_Pshadow_Pstandard_CArray_Mlength
	.cfi_startproc
# BB#0:
	pushl	%esi
Ltmp18:
	.cfi_def_cfa_offset 8
	subl	$16, %esp
Ltmp19:
	.cfi_def_cfa_offset 24
Ltmp20:
	.cfi_offset %esi, -8
	movl	24(%esp), %eax
	movl	%eax, 12(%esp)
	movl	16(%eax), %eax
	movl	(%eax), %eax
	movl	%eax, 8(%esp)
	movl	$1, 4(%esp)
	jmp	LBB4_2
	.align	16, 0x90
LBB4_1:                                 # %.label0
                                        #   in Loop: Header=BB4_2 Depth=1
	movl	12(%esp), %eax
	movl	16(%eax), %eax
	movl	4(%esp), %ecx
	movl	8(%esp), %edx
	imull	(%eax,%ecx,4), %edx
	movl	%edx, 8(%esp)
	incl	4(%esp)
LBB4_2:                                 # %.label1
                                        # =>This Inner Loop Header: Depth=1
	movl	12(%esp), %eax
	movl	(%eax), %ecx
	movl	4(%esp), %esi
	movl	%eax, (%esp)
	calll	*52(%ecx)
	cmpl	%eax, %esi
	jl	LBB4_1
# BB#3:                                 # %.label2
	movl	8(%esp), %eax
	addl	$16, %esp
	popl	%esi
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CArray_Mlengths;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CArray_Mlengths
	.align	16, 0x90
__Pshadow_Pstandard_CArray_Mlengths:    # @_Pshadow_Pstandard_CArray_Mlengths
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp22:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %ecx
	movl	%ecx, (%esp)
	movl	16(%ecx), %eax
	movl	20(%ecx), %edx
	popl	%ecx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1
	.align	16, 0x90
__Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1: # @_Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp26:
	.cfi_def_cfa_offset 8
Ltmp27:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp28:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	pushl	%esi
	subl	$36, %esp
Ltmp29:
	.cfi_offset %esi, -12
	movl	8(%ebp), %eax
	movl	%eax, 32(%esp)
	movl	16(%ebp), %eax
	movl	%eax, 28(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 24(%esp)
	movl	24(%ebp), %eax
	movl	%eax, 20(%esp)
	movl	20(%ebp), %eax
	movl	%eax, 16(%esp)
	movl	32(%esp), %eax
	movl	$__Pshadow_Pstandard_CArray_Mclass, (%eax)
	movl	32(%esp), %esi
	movl	$0, 12(%esp)
	movl	$4, 8(%esp)
	movl	$0, 4(%esp)
	movl	$0, (%esp)
	calll	_calloc
	movl	%eax, 16(%esi)
	movl	$0, 20(%esi)
	movl	32(%esp), %eax
	movl	$0, 12(%eax)
	movl	$0, 8(%eax)
	movsd	24(%esp), %xmm0
	movl	32(%esp), %eax
	movsd	%xmm0, 8(%eax)
	movl	16(%esp), %eax
	movl	20(%esp), %ecx
	movl	32(%esp), %edx
	movl	%ecx, 20(%edx)
	movl	%eax, 16(%edx)
	movl	32(%esp), %eax
	addl	$36, %esp
	popl	%esi
	movl	%ebp, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CArray_Mindex_Rint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CArray_Mindex_Rint
	.align	16, 0x90
__Pshadow_Pstandard_CArray_Mindex_Rint: # @_Pshadow_Pstandard_CArray_Mindex_Rint
	.cfi_startproc
# BB#0:
	xorl	%eax, %eax
	xorl	%edx, %edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CArray_Mindex_Rint_RT;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CArray_Mindex_Rint_RT
	.align	16, 0x90
__Pshadow_Pstandard_CArray_Mindex_Rint_RT: # @_Pshadow_Pstandard_CArray_Mindex_Rint_RT
	.cfi_startproc
# BB#0:
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass
	.align	16, 0x90
__Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass: # @_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp31:
	.cfi_def_cfa_offset 20
	movl	20(%esp), %eax
	movl	%eax, 12(%esp)
	movl	24(%esp), %eax
	movl	%eax, 8(%esp)
	cmpl	%eax, 12(%esp)
	je	LBB9_3
# BB#1:                                 # %.label2
	movl	12(%esp), %eax
	cmpl	$0, 16(%eax)
	je	LBB9_2
# BB#4:                                 # %.label5
	movl	16(%eax), %eax
	movl	(%eax), %ecx
	movl	8(%esp), %edx
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	calll	*52(%ecx)
	addl	$16, %esp
	ret
LBB9_3:                                 # %.label0
	movb	$1, %al
	addl	$16, %esp
	ret
LBB9_2:                                 # %.label4
	xorb	%al, %al
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface
	.align	16, 0x90
__Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface: # @_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp35:
	.cfi_def_cfa_offset 8
Ltmp36:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp37:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	pushl	%ebx
	pushl	%edi
	pushl	%esi
	subl	$52, %esp
Ltmp38:
	.cfi_offset %esi, -20
Ltmp39:
	.cfi_offset %edi, -16
Ltmp40:
	.cfi_offset %ebx, -12
	movl	8(%ebp), %eax
	movl	%eax, 48(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 44(%esp)
	movl	$0, 36(%esp)
	movl	$0, 32(%esp)
	jmp	LBB10_6
	.align	16, 0x90
LBB10_5:                                # %.label10
                                        #   in Loop: Header=BB10_6 Depth=1
	movl	32(%esp), %eax
	addl	$1, %eax
	adcl	$0, 36(%esp)
	movl	%eax, 32(%esp)
LBB10_6:                                # %.label7
                                        # =>This Inner Loop Header: Depth=1
	movl	48(%esp), %eax
	movl	4(%eax), %edi
	movl	8(%eax), %ebx
	movl	32(%esp), %eax
	movl	%eax, 24(%esp)          # 4-byte Spill
	movl	36(%esp), %eax
	movl	%eax, 28(%esp)          # 4-byte Spill
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, %esi
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%ebx, (%eax)
	movl	%eax, 12(%esp)
	movl	%edi, 4(%esp)
	movl	%esi, (%esp)
	movl	$1, 16(%esp)
	movl	$0, 8(%esp)
	calll	__Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*64(%ecx)
	cmpl	%eax, 24(%esp)          # 4-byte Folded Reload
	setb	%cl
	sarl	$31, %eax
	cmpl	%eax, 28(%esp)          # 4-byte Folded Reload
	setl	%al
	je	LBB10_8
# BB#7:                                 # %.label7
                                        #   in Loop: Header=BB10_6 Depth=1
	movb	%al, %cl
LBB10_8:                                # %.label7
                                        #   in Loop: Header=BB10_6 Depth=1
	testb	%cl, %cl
	je	LBB10_9
# BB#1:                                 # %.label6
                                        #   in Loop: Header=BB10_6 Depth=1
	movl	48(%esp), %eax
	movl	4(%eax), %eax
	movl	32(%esp), %ecx
	cmpl	$0, (%eax,%ecx,4)
	je	LBB10_5
# BB#2:                                 # %.label14
                                        #   in Loop: Header=BB10_6 Depth=1
	movl	(%eax,%ecx,4), %eax
	movl	(%eax), %ecx
	movl	44(%esp), %edx
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	calll	*52(%ecx)
	testb	$1, %al
	je	LBB10_5
# BB#3:                                 # %.label11
	movb	$1, %al
	jmp	LBB10_4
LBB10_9:                                # %.label8
	xorb	%al, %al
LBB10_4:                                # %.label11
	addl	$52, %esp
	popl	%esi
	popl	%edi
	popl	%ebx
	movl	%ebp, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CClass_Mparent;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CClass_Mparent
	.align	16, 0x90
__Pshadow_Pstandard_CClass_Mparent:     # @_Pshadow_Pstandard_CClass_Mparent
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp42:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %eax
	movl	%eax, (%esp)
	movl	16(%eax), %eax
	popl	%edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CClass_MtoString;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CClass_MtoString
	.align	16, 0x90
__Pshadow_Pstandard_CClass_MtoString:   # @_Pshadow_Pstandard_CClass_MtoString
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp44:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %eax
	movl	%eax, (%esp)
	movl	12(%eax), %eax
	popl	%edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CClass_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CClass_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_CClass_Mcreate:     # @_Pshadow_Pstandard_CClass_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%esi
Ltmp47:
	.cfi_def_cfa_offset 8
	subl	$20, %esp
Ltmp48:
	.cfi_def_cfa_offset 28
Ltmp49:
	.cfi_offset %esi, -8
	movl	28(%esp), %eax
	movl	%eax, 16(%esp)
	movl	$__Pshadow_Pstandard_CClass_Mclass, (%eax)
	movl	16(%esp), %eax
	movl	$0, 24(%eax)
	movl	16(%esp), %eax
	movl	$0, 16(%eax)
	movl	16(%esp), %eax
	movl	$L_.string1, 12(%eax)
	movl	16(%esp), %esi
	movl	$0, 12(%esp)
	movl	$4, 8(%esp)
	movl	$0, 4(%esp)
	movl	$0, (%esp)
	calll	_calloc
	movl	%eax, 4(%esi)
	movl	$0, 8(%esi)
	movl	16(%esp), %eax
	movl	$0, 20(%eax)
	movl	16(%esp), %eax
	addl	$20, %esp
	popl	%esi
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface
	.align	16, 0x90
__Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface: # @_Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp53:
	.cfi_def_cfa_offset 8
Ltmp54:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp55:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	pushl	%ebx
	pushl	%edi
	pushl	%esi
	subl	$52, %esp
Ltmp56:
	.cfi_offset %esi, -20
Ltmp57:
	.cfi_offset %edi, -16
Ltmp58:
	.cfi_offset %ebx, -12
	movl	8(%ebp), %eax
	movl	%eax, 48(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 44(%esp)
	cmpl	%eax, 48(%esp)
	je	LBB14_1
# BB#3:                                 # %.label2
	movl	$0, 36(%esp)
	movl	$0, 32(%esp)
	jmp	LBB14_7
	.align	16, 0x90
LBB14_6:                                # %.label7
                                        #   in Loop: Header=BB14_7 Depth=1
	movl	32(%esp), %eax
	addl	$1, %eax
	adcl	$0, 36(%esp)
	movl	%eax, 32(%esp)
LBB14_7:                                # %.label4
                                        # =>This Inner Loop Header: Depth=1
	movl	48(%esp), %eax
	movl	4(%eax), %edi
	movl	8(%eax), %ebx
	movl	32(%esp), %eax
	movl	%eax, 24(%esp)          # 4-byte Spill
	movl	36(%esp), %eax
	movl	%eax, 28(%esp)          # 4-byte Spill
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, %esi
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%ebx, (%eax)
	movl	%eax, 12(%esp)
	movl	%edi, 4(%esp)
	movl	%esi, (%esp)
	movl	$1, 16(%esp)
	movl	$0, 8(%esp)
	calll	__Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*64(%ecx)
	cmpl	%eax, 24(%esp)          # 4-byte Folded Reload
	setb	%cl
	sarl	$31, %eax
	cmpl	%eax, 28(%esp)          # 4-byte Folded Reload
	setl	%al
	je	LBB14_9
# BB#8:                                 # %.label4
                                        #   in Loop: Header=BB14_7 Depth=1
	movb	%al, %cl
LBB14_9:                                # %.label4
                                        #   in Loop: Header=BB14_7 Depth=1
	testb	%cl, %cl
	je	LBB14_10
# BB#4:                                 # %.label3
                                        #   in Loop: Header=BB14_7 Depth=1
	movl	48(%esp), %eax
	movl	4(%eax), %eax
	movl	32(%esp), %ecx
	cmpl	$0, (%eax,%ecx,4)
	je	LBB14_6
# BB#5:                                 # %.label11
                                        #   in Loop: Header=BB14_7 Depth=1
	movl	(%eax,%ecx,4), %eax
	movl	(%eax), %ecx
	movl	44(%esp), %edx
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	calll	*52(%ecx)
	testb	$1, %al
	je	LBB14_6
LBB14_1:                                # %.label0
	movb	$1, %al
	jmp	LBB14_2
LBB14_10:                               # %.label5
	xorb	%al, %al
LBB14_2:                                # %.label0
	addl	$52, %esp
	popl	%esi
	popl	%edi
	popl	%ebx
	movl	%ebp, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CInterface_MtoString;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CInterface_MtoString
	.align	16, 0x90
__Pshadow_Pstandard_CInterface_MtoString: # @_Pshadow_Pstandard_CInterface_MtoString
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp60:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %eax
	movl	%eax, (%esp)
	movl	12(%eax), %eax
	popl	%edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CInterface_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CInterface_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_CInterface_Mcreate: # @_Pshadow_Pstandard_CInterface_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%esi
Ltmp63:
	.cfi_def_cfa_offset 8
	subl	$20, %esp
Ltmp64:
	.cfi_def_cfa_offset 28
Ltmp65:
	.cfi_offset %esi, -8
	movl	28(%esp), %eax
	movl	%eax, 16(%esp)
	movl	$__Pshadow_Pstandard_CInterface_Mclass, (%eax)
	movl	16(%esp), %eax
	movl	$L_.string124, 12(%eax)
	movl	16(%esp), %esi
	movl	$0, 12(%esp)
	movl	$4, 8(%esp)
	movl	$0, 4(%esp)
	movl	$0, (%esp)
	calll	_calloc
	movl	%eax, 4(%esi)
	movl	$0, 8(%esi)
	movl	16(%esp), %eax
	addl	$20, %esp
	popl	%esi
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.align	16, 0x90
__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp67:
	.cfi_def_cfa_offset 20
	movl	20(%esp), %eax
	movl	%eax, 12(%esp)
	movl	24(%esp), %eax
	movl	%eax, 8(%esp)
	movl	12(%esp), %ecx
	movl	(%ecx), %edx
	movl	%eax, 4(%esp)
	movl	%ecx, (%esp)
	calll	*44(%edx)
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CObject_MtoString;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CObject_MtoString
	.align	16, 0x90
__Pshadow_Pstandard_CObject_MtoString:  # @_Pshadow_Pstandard_CObject_MtoString
	.cfi_startproc
# BB#0:
	subl	$8, %esp
Ltmp69:
	.cfi_def_cfa_offset 12
	movl	12(%esp), %eax
	movl	%eax, 4(%esp)
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*40(%ecx)
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*48(%ecx)
	addl	$8, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CObject_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CObject_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_CObject_Mcreate:    # @_Pshadow_Pstandard_CObject_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp71:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %eax
	movl	%eax, (%esp)
	movl	$__Pshadow_Pstandard_CObject_Mclass, (%eax)
	movl	(%esp), %eax
	popl	%edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.align	16, 0x90
__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject: # @_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	cmpl	8(%esp), %eax
	sete	%al
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CObject_MgetClass;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CObject_MgetClass
	.align	16, 0x90
__Pshadow_Pstandard_CObject_MgetClass:  # @_Pshadow_Pstandard_CObject_MgetClass
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	movl	(%eax), %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CObject_Mcopy;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CObject_Mcopy
	.align	16, 0x90
__Pshadow_Pstandard_CObject_Mcopy:      # @_Pshadow_Pstandard_CObject_Mcopy
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CObject_Mfreeze;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CObject_Mfreeze
	.align	16, 0x90
__Pshadow_Pstandard_CObject_Mfreeze:    # @_Pshadow_Pstandard_CObject_Mfreeze
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString
	.align	16, 0x90
__Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString: # @_Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushl	%ebx
Ltmp76:
	.cfi_def_cfa_offset 8
	pushl	%edi
Ltmp77:
	.cfi_def_cfa_offset 12
	pushl	%esi
Ltmp78:
	.cfi_def_cfa_offset 16
	subl	$20, %esp
Ltmp79:
	.cfi_def_cfa_offset 36
Ltmp80:
	.cfi_offset %esi, -16
Ltmp81:
	.cfi_offset %edi, -12
Ltmp82:
	.cfi_offset %ebx, -8
	movl	36(%esp), %eax
	movl	%eax, 16(%esp)
	movl	40(%esp), %edi
	movl	%edi, 12(%esp)
	movl	16(%esp), %eax
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*64(%ecx)
	movl	%eax, %esi
	movl	(%edi), %eax
	movl	%edi, (%esp)
	calll	*64(%eax)
	cmpl	%eax, %esi
	jne	LBB24_1
# BB#3:                                 # %.label2
	movl	$0, 8(%esp)
	jmp	LBB24_6
	.align	16, 0x90
LBB24_5:                                # %.label8
                                        #   in Loop: Header=BB24_6 Depth=1
	incl	8(%esp)
LBB24_6:                                # %.label4
                                        # =>This Inner Loop Header: Depth=1
	movl	16(%esp), %eax
	movl	(%eax), %ecx
	movl	8(%esp), %esi
	movl	%eax, (%esp)
	calll	*64(%ecx)
	cmpl	%eax, %esi
	jge	LBB24_7
# BB#4:                                 # %.label3
                                        #   in Loop: Header=BB24_6 Depth=1
	movl	16(%esp), %eax
	movl	(%eax), %ecx
	movl	8(%esp), %edx
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	calll	*60(%ecx)
	movb	%al, %bl
	movl	12(%esp), %eax
	movl	(%eax), %ecx
	movl	8(%esp), %edx
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	calll	*60(%ecx)
	cmpb	%al, %bl
	je	LBB24_5
LBB24_1:                                # %.label0
	xorb	%al, %al
	jmp	LBB24_2
LBB24_7:                                # %.label5
	movb	$1, %al
LBB24_2:                                # %.label0
	addl	$20, %esp
	popl	%esi
	popl	%edi
	popl	%ebx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CString_MgetByte_Rint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CString_MgetByte_Rint
	.align	16, 0x90
__Pshadow_Pstandard_CString_MgetByte_Rint: # @_Pshadow_Pstandard_CString_MgetByte_Rint
	.cfi_startproc
# BB#0:
	subl	$8, %esp
Ltmp84:
	.cfi_def_cfa_offset 12
	movl	12(%esp), %eax
	movl	%eax, 4(%esp)
	movl	16(%esp), %eax
	movl	%eax, (%esp)
	movl	4(%esp), %ecx
	movl	4(%ecx), %ecx
	movb	(%ecx,%eax), %al
	addl	$8, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString
	.align	16, 0x90
__Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString: # @_Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushl	%ebx
Ltmp89:
	.cfi_def_cfa_offset 8
	pushl	%edi
Ltmp90:
	.cfi_def_cfa_offset 12
	pushl	%esi
Ltmp91:
	.cfi_def_cfa_offset 16
	subl	$20, %esp
Ltmp92:
	.cfi_def_cfa_offset 36
Ltmp93:
	.cfi_offset %esi, -16
Ltmp94:
	.cfi_offset %edi, -12
Ltmp95:
	.cfi_offset %ebx, -8
	movl	36(%esp), %eax
	movl	%eax, 16(%esp)
	movl	40(%esp), %eax
	movl	%eax, 12(%esp)
	movl	$0, 8(%esp)
	jmp	LBB26_4
	.align	16, 0x90
LBB26_3:                                # %.label14
                                        #   in Loop: Header=BB26_4 Depth=1
	incl	8(%esp)
LBB26_4:                                # %.label10
                                        # =>This Inner Loop Header: Depth=1
	movl	16(%esp), %eax
	movl	(%eax), %ecx
	movl	8(%esp), %edi
	movl	%eax, (%esp)
	calll	*64(%ecx)
	movl	%eax, %esi
	movl	12(%esp), %eax
	movl	(%eax), %ecx
	movl	8(%esp), %ebx
	movl	%eax, (%esp)
	calll	*64(%ecx)
	cmpl	%esi, %edi
	jge	LBB26_6
# BB#5:                                 # %.label10
                                        #   in Loop: Header=BB26_4 Depth=1
	cmpl	%eax, %ebx
	jge	LBB26_6
# BB#1:                                 # %.label9
                                        #   in Loop: Header=BB26_4 Depth=1
	movl	16(%esp), %eax
	movl	(%eax), %ecx
	movl	8(%esp), %edx
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	calll	*60(%ecx)
	movb	%al, %bl
	movl	12(%esp), %eax
	movl	(%eax), %ecx
	movl	8(%esp), %edx
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	calll	*60(%ecx)
	cmpb	%al, %bl
	je	LBB26_3
# BB#2:                                 # %.label12
	movl	16(%esp), %eax
	movl	(%eax), %ecx
	movl	8(%esp), %edx
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	calll	*60(%ecx)
	movb	%al, %bl
	movl	12(%esp), %eax
	movl	(%eax), %ecx
	movl	8(%esp), %edx
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	calll	*60(%ecx)
	subb	%al, %bl
	movsbl	%bl, %eax
	jmp	LBB26_7
LBB26_6:                                # %.label11
	movl	16(%esp), %eax
	movl	(%eax), %ecx
	movl	12(%esp), %edi
	movl	%eax, (%esp)
	calll	*64(%ecx)
	movl	%eax, %esi
	movl	(%edi), %eax
	movl	%edi, (%esp)
	calll	*64(%eax)
	subl	%eax, %esi
	movl	%esi, %eax
LBB26_7:                                # %.label11
	addl	$20, %esp
	popl	%esi
	popl	%edi
	popl	%ebx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CString_Mlength;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CString_Mlength
	.align	16, 0x90
__Pshadow_Pstandard_CString_Mlength:    # @_Pshadow_Pstandard_CString_Mlength
	.cfi_startproc
# BB#0:
	pushl	%ebx
Ltmp100:
	.cfi_def_cfa_offset 8
	pushl	%edi
Ltmp101:
	.cfi_def_cfa_offset 12
	pushl	%esi
Ltmp102:
	.cfi_def_cfa_offset 16
	subl	$24, %esp
Ltmp103:
	.cfi_def_cfa_offset 40
Ltmp104:
	.cfi_offset %esi, -16
Ltmp105:
	.cfi_offset %edi, -12
Ltmp106:
	.cfi_offset %ebx, -8
	movl	40(%esp), %eax
	movl	%eax, 20(%esp)
	movl	4(%eax), %edi
	movl	8(%eax), %ebx
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, %esi
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%ebx, (%eax)
	movl	%eax, 12(%esp)
	movl	%edi, 4(%esp)
	movl	%esi, (%esp)
	movl	$1, 16(%esp)
	movl	$0, 8(%esp)
	calll	__Pshadow_Pstandard_CArray_Mcreate_Rlong_Rint_A1
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*64(%ecx)
	addl	$24, %esp
	popl	%esi
	popl	%edi
	popl	%ebx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CString_MtoString;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CString_MtoString
	.align	16, 0x90
__Pshadow_Pstandard_CString_MtoString:  # @_Pshadow_Pstandard_CString_MtoString
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp108:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %eax
	movl	%eax, (%esp)
	popl	%edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CString_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CString_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_CString_Mcreate:    # @_Pshadow_Pstandard_CString_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%esi
Ltmp111:
	.cfi_def_cfa_offset 8
	subl	$20, %esp
Ltmp112:
	.cfi_def_cfa_offset 28
Ltmp113:
	.cfi_offset %esi, -8
	movl	28(%esp), %eax
	movl	%eax, 16(%esp)
	movl	$__Pshadow_Pstandard_CString_Mclass, (%eax)
	movl	16(%esp), %esi
	movl	$0, 12(%esp)
	movl	$1, 8(%esp)
	movl	$0, 4(%esp)
	movl	$0, (%esp)
	calll	_calloc
	movl	%eax, 4(%esi)
	movl	$0, 8(%esi)
	movl	16(%esp), %eax
	addl	$20, %esp
	popl	%esi
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_MprintLine_Rboolean;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_MprintLine_Rboolean
	.align	16, 0x90
__Pshadow_Pio_CConsole_MprintLine_Rboolean: # @_Pshadow_Pio_CConsole_MprintLine_Rboolean
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp115:
	.cfi_def_cfa_offset 20
	movl	20(%esp), %eax
	movl	%eax, 12(%esp)
	movb	24(%esp), %al
	andb	$1, %al
	movb	%al, 11(%esp)
	movl	12(%esp), %eax
	movl	(%eax), %ecx
	movzbl	11(%esp), %edx
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	calll	*52(%ecx)
	movl	12(%esp), %eax
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*100(%ecx)
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_MprintLine_Rint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_MprintLine_Rint
	.align	16, 0x90
__Pshadow_Pio_CConsole_MprintLine_Rint: # @_Pshadow_Pio_CConsole_MprintLine_Rint
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp117:
	.cfi_def_cfa_offset 20
	movl	20(%esp), %eax
	movl	%eax, 12(%esp)
	movl	24(%esp), %eax
	movl	%eax, 8(%esp)
	movl	12(%esp), %ecx
	movl	(%ecx), %edx
	movl	%eax, 4(%esp)
	movl	%ecx, (%esp)
	calll	*60(%edx)
	movl	12(%esp), %eax
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*100(%ecx)
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_MprintLine_Rcode;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_MprintLine_Rcode
	.align	16, 0x90
__Pshadow_Pio_CConsole_MprintLine_Rcode: # @_Pshadow_Pio_CConsole_MprintLine_Rcode
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp119:
	.cfi_def_cfa_offset 20
	movl	20(%esp), %eax
	movl	%eax, 12(%esp)
	movl	24(%esp), %eax
	movl	%eax, 8(%esp)
	movl	12(%esp), %ecx
	movl	(%ecx), %edx
	movl	%eax, 4(%esp)
	movl	%ecx, (%esp)
	calll	*64(%edx)
	movl	12(%esp), %eax
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*100(%ecx)
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_MprintLine_Rlong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_MprintLine_Rlong
	.align	16, 0x90
__Pshadow_Pio_CConsole_MprintLine_Rlong: # @_Pshadow_Pio_CConsole_MprintLine_Rlong
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp123:
	.cfi_def_cfa_offset 8
Ltmp124:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp125:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	pushl	%esi
	subl	$28, %esp
Ltmp126:
	.cfi_offset %esi, -12
	movl	8(%ebp), %eax
	movl	%eax, 24(%esp)
	movl	16(%ebp), %eax
	movl	%eax, 20(%esp)
	movl	12(%ebp), %ecx
	movl	%ecx, 16(%esp)
	movl	24(%esp), %edx
	movl	(%edx), %esi
	movl	%eax, 8(%esp)
	movl	%ecx, 4(%esp)
	movl	%edx, (%esp)
	calll	*68(%esi)
	movl	24(%esp), %eax
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*100(%ecx)
	addl	$28, %esp
	popl	%esi
	movl	%ebp, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString
	.align	16, 0x90
__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString: # @_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp128:
	.cfi_def_cfa_offset 20
	movl	20(%esp), %eax
	movl	%eax, 12(%esp)
	movl	24(%esp), %eax
	movl	%eax, 8(%esp)
	movl	12(%esp), %ecx
	movl	(%ecx), %edx
	movl	%eax, 4(%esp)
	movl	%ecx, (%esp)
	calll	*72(%edx)
	movl	12(%esp), %eax
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*100(%ecx)
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject
	.align	16, 0x90
__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject: # @_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp130:
	.cfi_def_cfa_offset 20
	movl	20(%esp), %eax
	movl	%eax, 12(%esp)
	movl	24(%esp), %eax
	movl	%eax, 8(%esp)
	movl	12(%esp), %ecx
	movl	(%ecx), %edx
	movl	%eax, 4(%esp)
	movl	%ecx, (%esp)
	calll	*56(%edx)
	movl	12(%esp), %eax
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*100(%ecx)
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_MprintLine;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_MprintLine
	.align	16, 0x90
__Pshadow_Pio_CConsole_MprintLine:      # @_Pshadow_Pio_CConsole_MprintLine
	.cfi_startproc
# BB#0:
	subl	$12, %esp
Ltmp132:
	.cfi_def_cfa_offset 16
	movl	16(%esp), %eax
	movl	%eax, 8(%esp)
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	movl	$10, 4(%esp)
	calll	*64(%ecx)
	addl	$12, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_Mcreate
	.align	16, 0x90
__Pshadow_Pio_CConsole_Mcreate:         # @_Pshadow_Pio_CConsole_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp134:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %eax
	movl	%eax, (%esp)
	movl	$__Pshadow_Pio_CConsole_Mclass, (%eax)
	movl	(%esp), %eax
	popl	%edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_Mprint_Rboolean;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_Mprint_Rboolean
	.align	16, 0x90
__Pshadow_Pio_CConsole_Mprint_Rboolean: # @_Pshadow_Pio_CConsole_Mprint_Rboolean
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp136:
	.cfi_def_cfa_offset 20
	movl	20(%esp), %eax
	movl	%eax, 12(%esp)
	movb	24(%esp), %al
	andb	$1, %al
	movb	%al, 11(%esp)
	testb	%al, %al
	je	LBB38_2
# BB#1:                                 # %.label0
	movl	12(%esp), %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$L_.string168, 4(%esp)
	jmp	LBB38_3
LBB38_2:                                # %.label1
	movl	12(%esp), %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$L_.string2, 4(%esp)
LBB38_3:                                # %.label1
	calll	*72(%eax)
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject
	.align	16, 0x90
__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject: # @_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp138:
	.cfi_def_cfa_offset 20
	movl	20(%esp), %eax
	movl	%eax, 12(%esp)
	movl	24(%esp), %eax
	movl	%eax, 8(%esp)
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*48(%ecx)
	movl	12(%esp), %ecx
	movl	(%ecx), %edx
	movl	%eax, 4(%esp)
	movl	%ecx, (%esp)
	calll	*72(%edx)
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 L_out;
	.scl	2;
	.type	32;
	.endef
	.align	16, 0x90
L_out:                                  # @out
	.cfi_startproc
# BB#0:
	movl	$_stdout, %eax
	testl	%eax, %eax
	je	LBB40_2
# BB#1:
	movl	_stdout, %eax
	ret
LBB40_2:
	movl	__imp___iob, %eax
	addl	$32, %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_Mprint_Rint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_Mprint_Rint
	.align	16, 0x90
__Pshadow_Pio_CConsole_Mprint_Rint:     # @_Pshadow_Pio_CConsole_Mprint_Rint
	.cfi_startproc
# BB#0:
	subl	$12, %esp
Ltmp140:
	.cfi_def_cfa_offset 16
	calll	L_out
	movl	20(%esp), %ecx
	movl	%ecx, 8(%esp)
	movl	%eax, (%esp)
	movl	$L_str.int, 4(%esp)
	calll	_fprintf
	addl	$12, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_Mprint_Rcode;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_Mprint_Rcode
	.align	16, 0x90
__Pshadow_Pio_CConsole_Mprint_Rcode:    # @_Pshadow_Pio_CConsole_Mprint_Rcode
	.cfi_startproc
# BB#0:
	subl	$12, %esp
Ltmp142:
	.cfi_def_cfa_offset 16
	calll	L_out
	movl	20(%esp), %ecx
	movl	%ecx, 8(%esp)
	movl	%eax, (%esp)
	movl	$L_str.code, 4(%esp)
	calll	_fprintf
	addl	$12, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_Mprint_Rlong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_Mprint_Rlong
	.align	16, 0x90
__Pshadow_Pio_CConsole_Mprint_Rlong:    # @_Pshadow_Pio_CConsole_Mprint_Rlong
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp144:
	.cfi_def_cfa_offset 20
	calll	L_out
	movl	28(%esp), %ecx
	movl	%ecx, 12(%esp)
	movl	24(%esp), %ecx
	movl	%ecx, 8(%esp)
	movl	%eax, (%esp)
	movl	$L_str.long, 4(%esp)
	calll	_fprintf
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString
	.align	16, 0x90
__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString: # @_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString
	.cfi_startproc
# BB#0:
	pushl	%edi
Ltmp148:
	.cfi_def_cfa_offset 8
	pushl	%esi
Ltmp149:
	.cfi_def_cfa_offset 12
	subl	$16, %esp
Ltmp150:
	.cfi_def_cfa_offset 28
Ltmp151:
	.cfi_offset %esi, -12
Ltmp152:
	.cfi_offset %edi, -8
	movl	32(%esp), %eax
	movl	4(%eax), %esi
	movl	8(%eax), %edi
	calll	L_out
	movl	%eax, 12(%esp)
	movl	%edi, 8(%esp)
	movl	%esi, (%esp)
	movl	$1, 4(%esp)
	calll	_fwrite
	addl	$16, %esp
	popl	%esi
	popl	%edi
	ret
	.cfi_endproc

	.def	 __Pshadow_Ptest_CConsoleTest_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Ptest_CConsoleTest_Mcreate
	.align	16, 0x90
__Pshadow_Ptest_CConsoleTest_Mcreate:   # @_Pshadow_Ptest_CConsoleTest_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp154:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %eax
	movl	%eax, (%esp)
	movl	$__Pshadow_Ptest_CConsoleTest_Mclass, (%eax)
	movl	(%esp), %eax
	popl	%edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Ptest_CConsoleTest_Mmain;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Ptest_CConsoleTest_Mmain
	.align	16, 0x90
__Pshadow_Ptest_CConsoleTest_Mmain:     # @_Pshadow_Ptest_CConsoleTest_Mmain
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp156:
	.cfi_def_cfa_offset 20
	movl	20(%esp), %eax
	movl	%eax, 12(%esp)
	cmpl	$0, __Pshadow_Pio_CConsole_Minstance
	jne	LBB46_2
# BB#1:                                 # %.label0
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, (%esp)
	calll	__Pshadow_Pio_CConsole_Mcreate
	movl	%eax, __Pshadow_Pio_CConsole_Minstance
LBB46_2:                                # %.label1
	movl	__Pshadow_Pio_CConsole_Minstance, %eax
	movl	%eax, 8(%esp)
	cmpl	$0, __Pshadow_Pio_CConsole_Minstance
	jne	LBB46_4
# BB#3:                                 # %.label2
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, (%esp)
	calll	__Pshadow_Pio_CConsole_Mcreate
	movl	%eax, __Pshadow_Pio_CConsole_Minstance
LBB46_4:                                # %.label3
	movl	__Pshadow_Pio_CConsole_Minstance, %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$12345, 4(%esp)         # imm = 0x3039
	calll	*80(%eax)
	movl	8(%esp), %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$72, 4(%esp)
	calll	*64(%eax)
	cmpl	$0, __Pshadow_Pio_CConsole_Minstance
	jne	LBB46_6
# BB#5:                                 # %.label4
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, (%esp)
	calll	__Pshadow_Pio_CConsole_Mcreate
	movl	%eax, __Pshadow_Pio_CConsole_Minstance
LBB46_6:                                # %.label5
	movl	__Pshadow_Pio_CConsole_Minstance, %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$101, 4(%esp)
	calll	*64(%eax)
	movl	8(%esp), %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$108, 4(%esp)
	calll	*64(%eax)
	cmpl	$0, __Pshadow_Pio_CConsole_Minstance
	jne	LBB46_8
# BB#7:                                 # %.label6
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, (%esp)
	calll	__Pshadow_Pio_CConsole_Mcreate
	movl	%eax, __Pshadow_Pio_CConsole_Minstance
LBB46_8:                                # %.label7
	movl	__Pshadow_Pio_CConsole_Minstance, %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$108, 4(%esp)
	calll	*64(%eax)
	movl	8(%esp), %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$111, 4(%esp)
	calll	*64(%eax)
	cmpl	$0, __Pshadow_Pio_CConsole_Minstance
	jne	LBB46_10
# BB#9:                                 # %.label8
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, (%esp)
	calll	__Pshadow_Pio_CConsole_Mcreate
	movl	%eax, __Pshadow_Pio_CConsole_Minstance
LBB46_10:                               # %.label9
	movl	__Pshadow_Pio_CConsole_Minstance, %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$32, 4(%esp)
	calll	*64(%eax)
	movl	8(%esp), %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$87, 4(%esp)
	calll	*64(%eax)
	cmpl	$0, __Pshadow_Pio_CConsole_Minstance
	jne	LBB46_12
# BB#11:                                # %.label10
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, (%esp)
	calll	__Pshadow_Pio_CConsole_Mcreate
	movl	%eax, __Pshadow_Pio_CConsole_Minstance
LBB46_12:                               # %.label11
	movl	__Pshadow_Pio_CConsole_Minstance, %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$111, 4(%esp)
	calll	*64(%eax)
	movl	8(%esp), %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$114, 4(%esp)
	calll	*64(%eax)
	cmpl	$0, __Pshadow_Pio_CConsole_Minstance
	jne	LBB46_14
# BB#13:                                # %.label12
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, (%esp)
	calll	__Pshadow_Pio_CConsole_Mcreate
	movl	%eax, __Pshadow_Pio_CConsole_Minstance
LBB46_14:                               # %.label13
	movl	__Pshadow_Pio_CConsole_Minstance, %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$108, 4(%esp)
	calll	*64(%eax)
	movl	8(%esp), %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$100, 4(%esp)
	calll	*64(%eax)
	cmpl	$0, __Pshadow_Pio_CConsole_Minstance
	jne	LBB46_16
# BB#15:                                # %.label14
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, (%esp)
	calll	__Pshadow_Pio_CConsole_Mcreate
	movl	%eax, __Pshadow_Pio_CConsole_Minstance
LBB46_16:                               # %.label15
	movl	__Pshadow_Pio_CConsole_Minstance, %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$33, 4(%esp)
	calll	*64(%eax)
	movl	8(%esp), %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$10, 4(%esp)
	calll	*64(%eax)
	cmpl	$0, __Pshadow_Pio_CConsole_Minstance
	jne	LBB46_18
# BB#17:                                # %.label16
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, (%esp)
	calll	__Pshadow_Pio_CConsole_Mcreate
	movl	%eax, __Pshadow_Pio_CConsole_Minstance
LBB46_18:                               # %.label17
	movl	__Pshadow_Pio_CConsole_Minstance, %eax
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*100(%ecx)
	cmpl	$0, __Pshadow_Pio_CConsole_Minstance
	jne	LBB46_20
# BB#19:                                # %.label18
	movl	$0, 4(%esp)
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, (%esp)
	calll	__Pshadow_Pio_CConsole_Mcreate
	movl	%eax, __Pshadow_Pio_CConsole_Minstance
LBB46_20:                               # %.label19
	movl	__Pshadow_Pio_CConsole_Minstance, %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$L_.string180, 4(%esp)
	calll	*92(%eax)
	addl	$16, %esp
	ret
	.cfi_endproc

	.data
	.globl	__Pshadow_Pstandard_CArray_Mclass # @_Pshadow_Pstandard_CArray_Mclass
	.align	16
__Pshadow_Pstandard_CArray_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L_.string0
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pstandard_CArray_Mdims
	.long	__Pshadow_Pstandard_CArray_Mindex_Rint
	.long	__Pshadow_Pstandard_CArray_Mindex_Rint_RT
	.long	__Pshadow_Pstandard_CArray_Mlength
	.long	__Pshadow_Pstandard_CArray_Mlengths

	.align	16                      # @.array0
L_.array0:
	.ascii	 "shadow.standard@Array<T>"

	.align	8                       # @.string0
L_.string0:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L_.array0
	.long	24                      # 0x18

	.globl	__Pshadow_Pstandard_CClass_Mclass # @_Pshadow_Pstandard_CClass_Mclass
	.align	16
__Pshadow_Pstandard_CClass_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L_.string08
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CClass_MtoString
	.long	__Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass
	.long	__Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface
	.long	__Pshadow_Pstandard_CClass_Mparent

	.align	16                      # @.array07
L_.array07:
	.ascii	 "shadow.standard@Class"

	.align	8                       # @.string08
L_.string08:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L_.array07
	.long	21                      # 0x15

L_.array1:                              # @.array1

	.align	8                       # @.string1
L_.string1:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L_.array1
	.zero	4

	.globl	__Pshadow_Pstandard_CInterface_Mclass # @_Pshadow_Pstandard_CInterface_Mclass
	.align	16
__Pshadow_Pstandard_CInterface_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L_.string022
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CInterface_MtoString
	.long	__Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface

	.align	16                      # @.array021
L_.array021:
	.ascii	 "shadow.standard@Interface"

	.align	8                       # @.string022
L_.string022:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L_.array021
	.long	25                      # 0x19

L_.array123:                            # @.array123

	.align	8                       # @.string124
L_.string124:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L_.array123
	.zero	4

	.globl	__Pshadow_Pstandard_CObject_Mclass # @_Pshadow_Pstandard_CObject_Mclass
	.align	16
__Pshadow_Pstandard_CObject_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L_.string034
	.long	0
	.long	1                       # 0x1
	.long	1                       # 0x1
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString

	.align	16                      # @.array033
L_.array033:
	.ascii	 "shadow.standard@Object"

	.align	8                       # @.string034
L_.string034:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L_.array033
	.long	22                      # 0x16

	.globl	__Pshadow_Pstandard_CString_Mclass # @_Pshadow_Pstandard_CString_Mclass
	.align	16
__Pshadow_Pstandard_CString_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L_.string052
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CString_MtoString
	.long	__Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString
	.long	__Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString
	.long	__Pshadow_Pstandard_CString_MgetByte_Rint
	.long	__Pshadow_Pstandard_CString_Mlength

	.align	16                      # @.array051
L_.array051:
	.ascii	 "shadow.standard@String"

	.align	8                       # @.string052
L_.string052:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L_.array051
	.long	22                      # 0x16

	.globl	__Pshadow_Pio_CConsole_Mclass # @_Pshadow_Pio_CConsole_Mclass
	.align	16
__Pshadow_Pio_CConsole_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L_.string066
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pio_CConsole_Mprint_Rboolean
	.long	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pio_CConsole_Mprint_Rint
	.long	__Pshadow_Pio_CConsole_Mprint_Rcode
	.long	__Pshadow_Pio_CConsole_Mprint_Rlong
	.long	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString
	.long	__Pshadow_Pio_CConsole_MprintLine_Rboolean
	.long	__Pshadow_Pio_CConsole_MprintLine_Rint
	.long	__Pshadow_Pio_CConsole_MprintLine_Rcode
	.long	__Pshadow_Pio_CConsole_MprintLine_Rlong
	.long	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString
	.long	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pio_CConsole_MprintLine

	.globl	__Pshadow_Pio_CConsole_Minstance # @_Pshadow_Pio_CConsole_Minstance
	.align	4
__Pshadow_Pio_CConsole_Minstance:
	.long	0

	.align	16                      # @.array065
L_.array065:
	.ascii	 "shadow.io@Console"

	.align	8                       # @.string066
L_.string066:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L_.array065
	.long	17                      # 0x11

L_.array167:                            # @.array167
	.ascii	 "true"

	.align	8                       # @.string168
L_.string168:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L_.array167
	.long	4                       # 0x4

L_.array2:                              # @.array2
	.ascii	 "false"

	.align	8                       # @.string2
L_.string2:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L_.array2
	.long	5                       # 0x5

L_str.int:                              # @str.int
	.asciz	 "%i"

L_str.code:                             # @str.code
	.asciz	 "%lc"

L_str.long:                             # @str.long
	.asciz	 "%lli"

	.globl	__Pshadow_Ptest_CConsoleTest_Mclass # @_Pshadow_Ptest_CConsoleTest_Mclass
	.align	16
__Pshadow_Ptest_CConsoleTest_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L_.string078
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Ptest_CConsoleTest_Mmain

	.align	16                      # @.array077
L_.array077:
	.ascii	 "shadow.test@ConsoleTest"

	.align	8                       # @.string078
L_.string078:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L_.array077
	.long	23                      # 0x17

L_.array179:                            # @.array179
	.ascii	 "Test String\r\n"

	.align	8                       # @.string180
L_.string180:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L_.array179
	.long	13                      # 0xd

	.weak	_stdin
	.weak	_stdout
	.weak	_stderr
	.weak	__imp___iob

