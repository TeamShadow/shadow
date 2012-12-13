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
	calll	__Pshadow_Ptest_CArrayTest_Mmain
	xorl	%eax, %eax
	addl	$4, %esp
	popl	%ebp
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
	subl	$28, %esp
Ltmp12:
	.cfi_def_cfa_offset 44
Ltmp13:
	.cfi_offset %esi, -16
Ltmp14:
	.cfi_offset %edi, -12
Ltmp15:
	.cfi_offset %ebx, -8
	movl	44(%esp), %eax
	movl	%eax, 24(%esp)
	movl	16(%eax), %edi
	movl	20(%eax), %ebx
	movl	$24, (%esp)
	calll	_malloc
	movl	%eax, %esi
	movl	$4, (%esp)
	calll	_malloc
	movl	%ebx, (%eax)
	movl	%eax, 16(%esp)
	movl	%edi, 8(%esp)
	movl	%esi, (%esp)
	movl	$1, 20(%esp)
	movl	$0, 12(%esp)
	movl	$__Pshadow_Pstandard_Cint_Mclass, 4(%esp)
	calll	__Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*72(%ecx)
	movl	(%eax), %eax
	addl	$28, %esp
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
	jmp	LBB2_2
	.align	16, 0x90
LBB2_1:                                 # %_label0
                                        #   in Loop: Header=BB2_2 Depth=1
	movl	12(%esp), %eax
	movl	16(%eax), %eax
	movl	4(%esp), %ecx
	movl	8(%esp), %edx
	imull	(%eax,%ecx,4), %edx
	movl	%edx, 8(%esp)
	incl	4(%esp)
LBB2_2:                                 # %_label1
                                        # =>This Inner Loop Header: Depth=1
	movl	12(%esp), %eax
	movl	(%eax), %ecx
	movl	4(%esp), %esi
	movl	%eax, (%esp)
	calll	*56(%ecx)
	cmpl	%eax, %esi
	jl	LBB2_1
# BB#3:                                 # %_label2
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

	.def	 __Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1
	.align	16, 0x90
__Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1: # @_Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1
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
	movl	12(%ebp), %eax
	movl	%eax, 28(%esp)
	movl	20(%ebp), %eax
	movl	%eax, 20(%esp)
	movl	16(%ebp), %eax
	movl	%eax, 16(%esp)
	movl	28(%ebp), %eax
	movl	%eax, 12(%esp)
	movl	24(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	32(%esp), %eax
	movl	$__Pshadow_Pstandard_CArray_Mclass, (%eax)
	movl	28(%esp), %eax
	movl	32(%esp), %ecx
	movl	%eax, 4(%ecx)
	movl	32(%esp), %eax
	movl	$0, 12(%eax)
	movl	$0, 8(%eax)
	movl	32(%esp), %esi
	movl	$4, 4(%esp)
	movl	$0, (%esp)
	calll	_calloc
	movl	%eax, 16(%esi)
	movl	$0, 20(%esi)
	movsd	16(%esp), %xmm0
	movl	32(%esp), %eax
	movsd	%xmm0, 8(%eax)
	movl	8(%esp), %eax
	movl	12(%esp), %ecx
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

	.def	 __Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Cint_A1;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Cint_A1
	.align	16, 0x90
__Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Cint_A1: # @_Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Cint_A1
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp33:
	.cfi_def_cfa_offset 8
Ltmp34:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp35:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	pushl	%ebx
	pushl	%edi
	pushl	%esi
	subl	$52, %esp
Ltmp36:
	.cfi_offset %esi, -20
Ltmp37:
	.cfi_offset %edi, -16
Ltmp38:
	.cfi_offset %ebx, -12
	movl	8(%ebp), %eax
	movl	%eax, 48(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 44(%esp)
	movl	20(%ebp), %eax
	movl	%eax, 36(%esp)
	movl	16(%ebp), %eax
	movl	%eax, 32(%esp)
	movl	48(%esp), %eax
	movl	$__Pshadow_Pstandard_CArray_Mclass, (%eax)
	movl	44(%esp), %eax
	movl	48(%esp), %ecx
	movl	%eax, 4(%ecx)
	movl	48(%esp), %eax
	movl	$0, 12(%eax)
	movl	$0, 8(%eax)
	movl	48(%esp), %esi
	movl	$4, 4(%esp)
	movl	$0, (%esp)
	calll	_calloc
	movl	%eax, 16(%esi)
	movl	$0, 20(%esi)
	movl	32(%esp), %edi
	movl	36(%esp), %ebx
	movl	$24, (%esp)
	calll	_malloc
	movl	%eax, %esi
	movl	$4, (%esp)
	calll	_malloc
	movl	%ebx, (%eax)
	movl	%eax, 16(%esp)
	movl	%edi, 8(%esp)
	movl	%esi, (%esp)
	movl	$1, 20(%esp)
	movl	$0, 12(%esp)
	movl	$__Pshadow_Pstandard_Cint_Mclass, 4(%esp)
	calll	__Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*68(%ecx)
	movl	%eax, %esi
	movl	%esi, (%esp)
	movl	$4, 4(%esp)
	calll	_calloc
	movl	48(%esp), %ecx
	movl	%esi, 20(%ecx)
	movl	%eax, 16(%ecx)
	movl	$0, 28(%esp)
	jmp	LBB5_2
	.align	16, 0x90
LBB5_1:                                 # %_label3
                                        #   in Loop: Header=BB5_2 Depth=1
	movl	28(%esp), %eax
	movl	32(%esp), %ecx
	movl	(%ecx,%eax,4), %ecx
	movl	48(%esp), %edx
	movl	16(%edx), %edx
	movl	%ecx, (%edx,%eax,4)
	incl	28(%esp)
LBB5_2:                                 # %_label4
                                        # =>This Inner Loop Header: Depth=1
	movl	28(%esp), %eax
	movl	%eax, 24(%esp)          # 4-byte Spill
	movl	32(%esp), %ebx
	movl	36(%esp), %edi
	movl	$24, (%esp)
	calll	_malloc
	movl	%eax, %esi
	movl	$4, (%esp)
	calll	_malloc
	movl	%edi, (%eax)
	movl	%eax, 16(%esp)
	movl	%ebx, 8(%esp)
	movl	%esi, (%esp)
	movl	$1, 20(%esp)
	movl	$0, 12(%esp)
	movl	$__Pshadow_Pstandard_Cint_Mclass, 4(%esp)
	calll	__Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*68(%ecx)
	cmpl	%eax, 24(%esp)          # 4-byte Folded Reload
	jl	LBB5_1
# BB#3:                                 # %_label5
	movl	48(%esp), %esi
	movl	(%esi), %eax
	movl	%esi, (%esp)
	calll	*68(%eax)
	movl	(%esi), %ecx
	movl	%eax, 4(%esp)
	movl	%esi, (%esp)
	calll	*52(%ecx)
	movl	48(%esp), %ecx
	movl	%edx, 12(%ecx)
	movl	%eax, 8(%ecx)
	movl	48(%esp), %eax
	addl	$52, %esp
	popl	%esi
	popl	%edi
	popl	%ebx
	movl	%ebp, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CArray_McreateArray_R_Pshadow_Pstandard_Cint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CArray_McreateArray_R_Pshadow_Pstandard_Cint
	.align	16, 0x90
__Pshadow_Pstandard_CArray_McreateArray_R_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CArray_McreateArray_R_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	subl	$8, %esp
Ltmp40:
	.cfi_def_cfa_offset 12
	movl	16(%esp), %eax
	movl	%eax, (%esp)
	movl	$8, 4(%esp)
	calll	_calloc
	xorl	%edx, %edx
	addl	$8, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint
	.align	16, 0x90
__Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	xorl	%eax, %eax
	xorl	%edx, %edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint_RT;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint_RT
	.align	16, 0x90
__Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint_RT: # @_Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint_RT
	.cfi_startproc
# BB#0:
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cboolean_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cboolean_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_Cboolean_Mcreate:   # @_Pshadow_Pstandard_Cboolean_Mcreate
	.cfi_startproc
# BB#0:
	subl	$1, %esp
Ltmp42:
	.cfi_def_cfa_offset 5
	movb	5(%esp), %al
	andb	$1, %al
	movb	%al, (%esp)
	movb	(%esp), %al
	addl	$1, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cbyte_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cbyte_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_Cbyte_Mcreate:      # @_Pshadow_Pstandard_Cbyte_Mcreate
	.cfi_startproc
# BB#0:
	subl	$1, %esp
Ltmp44:
	.cfi_def_cfa_offset 5
	movb	5(%esp), %al
	movb	%al, (%esp)
	addl	$1, %esp
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
Ltmp46:
	.cfi_def_cfa_offset 20
	movl	20(%esp), %eax
	movl	%eax, 12(%esp)
	movl	24(%esp), %eax
	movl	%eax, 8(%esp)
	cmpl	%eax, 12(%esp)
	je	LBB11_3
# BB#1:                                 # %_label2
	movl	12(%esp), %eax
	cmpl	$0, 16(%eax)
	je	LBB11_2
# BB#4:                                 # %_label5
	movl	16(%eax), %eax
	movl	(%eax), %ecx
	movl	8(%esp), %edx
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	calll	*56(%ecx)
	addl	$16, %esp
	ret
LBB11_3:                                # %_label0
	movb	$1, %al
	addl	$16, %esp
	ret
LBB11_2:                                # %_label4
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
Ltmp52:
	.cfi_def_cfa_offset 8
	pushl	%ebx
Ltmp53:
	.cfi_def_cfa_offset 12
	pushl	%edi
Ltmp54:
	.cfi_def_cfa_offset 16
	pushl	%esi
Ltmp55:
	.cfi_def_cfa_offset 20
	subl	$36, %esp
Ltmp56:
	.cfi_def_cfa_offset 56
Ltmp57:
	.cfi_offset %esi, -20
Ltmp58:
	.cfi_offset %edi, -16
Ltmp59:
	.cfi_offset %ebx, -12
Ltmp60:
	.cfi_offset %ebp, -8
	movl	56(%esp), %eax
	movl	%eax, 32(%esp)
	movl	60(%esp), %eax
	movl	%eax, 28(%esp)
	movl	$0, 24(%esp)
	jmp	LBB12_6
	.align	16, 0x90
LBB12_5:                                # %_label10
                                        #   in Loop: Header=BB12_6 Depth=1
	incl	24(%esp)
LBB12_6:                                # %_label7
                                        # =>This Inner Loop Header: Depth=1
	movl	32(%esp), %eax
	movl	4(%eax), %ebx
	movl	8(%eax), %ebp
	movl	24(%esp), %edi
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, %esi
	movl	$4, (%esp)
	calll	_malloc
	movl	%ebp, (%eax)
	movl	%eax, 16(%esp)
	movl	%ebx, 8(%esp)
	movl	%esi, (%esp)
	movl	$1, 20(%esp)
	movl	$0, 12(%esp)
	movl	$__Pshadow_Pstandard_CInterface_Mclass, 4(%esp)
	calll	__Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*64(%ecx)
	cmpl	%eax, %edi
	jge	LBB12_7
# BB#1:                                 # %_label6
                                        #   in Loop: Header=BB12_6 Depth=1
	movl	32(%esp), %eax
	movl	4(%eax), %eax
	movl	24(%esp), %ecx
	cmpl	$0, (%eax,%ecx,4)
	je	LBB12_5
# BB#2:                                 # %_label14
                                        #   in Loop: Header=BB12_6 Depth=1
	movl	(%eax,%ecx,4), %eax
	movl	(%eax), %ecx
	movl	28(%esp), %edx
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	calll	*52(%ecx)
	testb	$1, %al
	je	LBB12_5
# BB#3:                                 # %_label11
	movb	$1, %al
	jmp	LBB12_4
LBB12_7:                                # %_label8
	xorb	%al, %al
LBB12_4:                                # %_label11
	addl	$36, %esp
	popl	%esi
	popl	%edi
	popl	%ebx
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
Ltmp62:
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
Ltmp64:
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
Ltmp67:
	.cfi_def_cfa_offset 8
	subl	$12, %esp
Ltmp68:
	.cfi_def_cfa_offset 20
Ltmp69:
	.cfi_offset %esi, -8
	movl	20(%esp), %eax
	movl	%eax, 8(%esp)
	movl	$__Pshadow_Pstandard_CClass_Mclass, (%eax)
	movl	8(%esp), %eax
	movl	$L__string1, 12(%eax)
	movl	8(%esp), %esi
	movl	$4, 4(%esp)
	movl	$0, (%esp)
	calll	_calloc
	movl	%eax, 4(%esi)
	movl	$0, 8(%esi)
	movl	8(%esp), %eax
	movl	$0, 16(%eax)
	movl	8(%esp), %eax
	movl	$0, 24(%eax)
	movl	8(%esp), %eax
	movl	$0, 20(%eax)
	movl	8(%esp), %eax
	addl	$12, %esp
	popl	%esi
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CClass_McreateObject;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CClass_McreateObject
	.align	16, 0x90
__Pshadow_Pstandard_CClass_McreateObject: # @_Pshadow_Pstandard_CClass_McreateObject
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp71:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %eax
	movl	20(%eax), %eax
	movl	%eax, (%esp)
	calll	_malloc
	popl	%edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Ccode_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Ccode_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_Ccode_Mcreate:      # @_Pshadow_Pstandard_Ccode_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp73:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %eax
	movl	%eax, (%esp)
	popl	%edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cdouble_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cdouble_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_Cdouble_Mcreate:    # @_Pshadow_Pstandard_Cdouble_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp76:
	.cfi_def_cfa_offset 8
Ltmp77:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp78:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	subl	$8, %esp
	movsd	8(%ebp), %xmm0
	movsd	%xmm0, (%esp)
	fldl	(%esp)
	movl	%ebp, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cfloat_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cfloat_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_Cfloat_Mcreate:     # @_Pshadow_Pstandard_Cfloat_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp80:
	.cfi_def_cfa_offset 8
	movss	8(%esp), %xmm0
	movss	%xmm0, (%esp)
	flds	(%esp)
	popl	%eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cint_Mmin_R_Pshadow_Pstandard_Cint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cint_Mmin_R_Pshadow_Pstandard_Cint
	.align	16, 0x90
__Pshadow_Pstandard_Cint_Mmin_R_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mmin_R_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	subl	$12, %esp
Ltmp82:
	.cfi_def_cfa_offset 16
	movl	16(%esp), %eax
	movl	%eax, 8(%esp)
	movl	20(%esp), %eax
	movl	%eax, 4(%esp)
	cmpl	%eax, 8(%esp)
	jge	LBB20_2
# BB#1:                                 # %_label0
	movl	8(%esp), %eax
	jmp	LBB20_3
LBB20_2:                                # %_label1
	movl	4(%esp), %eax
LBB20_3:                                # %_label1
	movl	%eax, (%esp)
	movl	(%esp), %eax
	addl	$12, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cint_Mabs;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cint_Mabs
	.align	16, 0x90
__Pshadow_Pstandard_Cint_Mabs:          # @_Pshadow_Pstandard_Cint_Mabs
	.cfi_startproc
# BB#0:
	subl	$8, %esp
Ltmp84:
	.cfi_def_cfa_offset 12
	movl	12(%esp), %eax
	movl	%eax, 4(%esp)
	testl	%eax, %eax
	js	LBB21_1
# BB#2:                                 # %_label4
	movl	4(%esp), %eax
	jmp	LBB21_3
LBB21_1:                                # %_label3
	xorl	%eax, %eax
	subl	4(%esp), %eax
LBB21_3:                                # %_label4
	movl	%eax, (%esp)
	movl	(%esp), %eax
	addl	$8, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cint_Mmax_R_Pshadow_Pstandard_Cint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cint_Mmax_R_Pshadow_Pstandard_Cint
	.align	16, 0x90
__Pshadow_Pstandard_Cint_Mmax_R_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mmax_R_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	subl	$12, %esp
Ltmp86:
	.cfi_def_cfa_offset 16
	movl	16(%esp), %eax
	movl	%eax, 8(%esp)
	movl	20(%esp), %eax
	movl	%eax, 4(%esp)
	cmpl	%eax, 8(%esp)
	jle	LBB22_2
# BB#1:                                 # %_label6
	movl	8(%esp), %eax
	jmp	LBB22_3
LBB22_2:                                # %_label7
	movl	4(%esp), %eax
LBB22_3:                                # %_label7
	movl	%eax, (%esp)
	movl	(%esp), %eax
	addl	$12, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cint_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cint_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_Cint_Mcreate:       # @_Pshadow_Pstandard_Cint_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp88:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %eax
	movl	%eax, (%esp)
	popl	%edx
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
Ltmp94:
	.cfi_def_cfa_offset 8
	pushl	%ebx
Ltmp95:
	.cfi_def_cfa_offset 12
	pushl	%edi
Ltmp96:
	.cfi_def_cfa_offset 16
	pushl	%esi
Ltmp97:
	.cfi_def_cfa_offset 20
	subl	$36, %esp
Ltmp98:
	.cfi_def_cfa_offset 56
Ltmp99:
	.cfi_offset %esi, -20
Ltmp100:
	.cfi_offset %edi, -16
Ltmp101:
	.cfi_offset %ebx, -12
Ltmp102:
	.cfi_offset %ebp, -8
	movl	56(%esp), %eax
	movl	%eax, 32(%esp)
	movl	60(%esp), %eax
	movl	%eax, 28(%esp)
	cmpl	%eax, 32(%esp)
	je	LBB24_1
# BB#3:                                 # %_label2
	movl	$0, 24(%esp)
	jmp	LBB24_7
	.align	16, 0x90
LBB24_6:                                # %_label7
                                        #   in Loop: Header=BB24_7 Depth=1
	incl	24(%esp)
LBB24_7:                                # %_label4
                                        # =>This Inner Loop Header: Depth=1
	movl	32(%esp), %eax
	movl	4(%eax), %ebx
	movl	8(%eax), %ebp
	movl	24(%esp), %edi
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, %esi
	movl	$4, (%esp)
	calll	_malloc
	movl	%ebp, (%eax)
	movl	%eax, 16(%esp)
	movl	%ebx, 8(%esp)
	movl	%esi, (%esp)
	movl	$1, 20(%esp)
	movl	$0, 12(%esp)
	movl	$__Pshadow_Pstandard_CInterface_Mclass, 4(%esp)
	calll	__Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*64(%ecx)
	cmpl	%eax, %edi
	jge	LBB24_8
# BB#4:                                 # %_label3
                                        #   in Loop: Header=BB24_7 Depth=1
	movl	32(%esp), %eax
	movl	4(%eax), %eax
	movl	24(%esp), %ecx
	cmpl	$0, (%eax,%ecx,4)
	je	LBB24_6
# BB#5:                                 # %_label11
                                        #   in Loop: Header=BB24_7 Depth=1
	movl	(%eax,%ecx,4), %eax
	movl	(%eax), %ecx
	movl	28(%esp), %edx
	movl	%edx, 4(%esp)
	movl	%eax, (%esp)
	calll	*52(%ecx)
	testb	$1, %al
	je	LBB24_6
LBB24_1:                                # %_label0
	movb	$1, %al
	jmp	LBB24_2
LBB24_8:                                # %_label5
	xorb	%al, %al
LBB24_2:                                # %_label0
	addl	$36, %esp
	popl	%esi
	popl	%edi
	popl	%ebx
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
Ltmp104:
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
Ltmp107:
	.cfi_def_cfa_offset 8
	subl	$12, %esp
Ltmp108:
	.cfi_def_cfa_offset 20
Ltmp109:
	.cfi_offset %esi, -8
	movl	20(%esp), %eax
	movl	%eax, 8(%esp)
	movl	$__Pshadow_Pstandard_CInterface_Mclass, (%eax)
	movl	8(%esp), %eax
	movl	$L__string152, 12(%eax)
	movl	8(%esp), %esi
	movl	$4, 4(%esp)
	movl	$0, (%esp)
	calll	_calloc
	movl	%eax, 4(%esi)
	movl	$0, 8(%esi)
	movl	8(%esp), %eax
	addl	$12, %esp
	popl	%esi
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Clong_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Clong_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_Clong_Mcreate:      # @_Pshadow_Pstandard_Clong_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp112:
	.cfi_def_cfa_offset 8
Ltmp113:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp114:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	subl	$8, %esp
	movl	12(%ebp), %edx
	movl	%edx, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	movl	%ebp, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cbyte_Madd_R_Pshadow_Pstandard_Cbyte;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cbyte_Madd_R_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
__Pshadow_Pstandard_Cbyte_Madd_R_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Madd_R_Pshadow_Pstandard_Cbyte
	.cfi_startproc
# BB#0:
	movb	4(%esp), %al
	addb	8(%esp), %al
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cbyte_Msubtract_R_Pshadow_Pstandard_Cbyte;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cbyte_Msubtract_R_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
__Pshadow_Pstandard_Cbyte_Msubtract_R_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Msubtract_R_Pshadow_Pstandard_Cbyte
	.cfi_startproc
# BB#0:
	movb	4(%esp), %al
	subb	8(%esp), %al
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cbyte_Mmultiply_R_Pshadow_Pstandard_Cbyte;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cbyte_Mmultiply_R_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
__Pshadow_Pstandard_Cbyte_Mmultiply_R_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Mmultiply_R_Pshadow_Pstandard_Cbyte
	.cfi_startproc
# BB#0:
	movb	4(%esp), %al
	mulb	8(%esp)
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cbyte_Mdivide_R_Pshadow_Pstandard_Cbyte;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cbyte_Mdivide_R_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
__Pshadow_Pstandard_Cbyte_Mdivide_R_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Mdivide_R_Pshadow_Pstandard_Cbyte
	.cfi_startproc
# BB#0:
	movb	4(%esp), %al
	cbtw
	idivb	8(%esp)
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cbyte_Mmodulus_R_Pshadow_Pstandard_Cbyte;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cbyte_Mmodulus_R_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
__Pshadow_Pstandard_Cbyte_Mmodulus_R_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Mmodulus_R_Pshadow_Pstandard_Cbyte
	.cfi_startproc
# BB#0:
	movb	4(%esp), %al
	cbtw
	idivb	8(%esp)
	movb	%ah, %al
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cbyte_Mcompare_R_Pshadow_Pstandard_Cbyte;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cbyte_Mcompare_R_Pshadow_Pstandard_Cbyte
	.align	16, 0x90
__Pshadow_Pstandard_Cbyte_Mcompare_R_Pshadow_Pstandard_Cbyte: # @_Pshadow_Pstandard_Cbyte_Mcompare_R_Pshadow_Pstandard_Cbyte
	.cfi_startproc
# BB#0:
	movb	4(%esp), %al
	cmpb	8(%esp), %al
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovgel	%ecx, %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cubyte_Madd_R_Pshadow_Pstandard_Cubyte;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cubyte_Madd_R_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
__Pshadow_Pstandard_Cubyte_Madd_R_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Madd_R_Pshadow_Pstandard_Cubyte
	.cfi_startproc
# BB#0:
	movb	4(%esp), %al
	addb	8(%esp), %al
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cubyte_Msubtract_R_Pshadow_Pstandard_Cubyte;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cubyte_Msubtract_R_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
__Pshadow_Pstandard_Cubyte_Msubtract_R_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Msubtract_R_Pshadow_Pstandard_Cubyte
	.cfi_startproc
# BB#0:
	movb	4(%esp), %al
	subb	8(%esp), %al
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cubyte_Mmultiply_R_Pshadow_Pstandard_Cubyte;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cubyte_Mmultiply_R_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
__Pshadow_Pstandard_Cubyte_Mmultiply_R_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Mmultiply_R_Pshadow_Pstandard_Cubyte
	.cfi_startproc
# BB#0:
	movb	4(%esp), %al
	mulb	8(%esp)
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cubyte_Mdivide_R_Pshadow_Pstandard_Cubyte;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cubyte_Mdivide_R_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
__Pshadow_Pstandard_Cubyte_Mdivide_R_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Mdivide_R_Pshadow_Pstandard_Cubyte
	.cfi_startproc
# BB#0:
	movzbl	4(%esp), %eax
                                        # kill: EAX<def> EAX<kill> AX<def>
	divb	8(%esp)
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cubyte_Mmodulus_R_Pshadow_Pstandard_Cubyte;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cubyte_Mmodulus_R_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
__Pshadow_Pstandard_Cubyte_Mmodulus_R_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Mmodulus_R_Pshadow_Pstandard_Cubyte
	.cfi_startproc
# BB#0:
	movzbl	4(%esp), %eax
                                        # kill: EAX<def> EAX<kill> AX<def>
	divb	8(%esp)
	movb	%ah, %al
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cubyte_Mcompare_R_Pshadow_Pstandard_Cubyte;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cubyte_Mcompare_R_Pshadow_Pstandard_Cubyte
	.align	16, 0x90
__Pshadow_Pstandard_Cubyte_Mcompare_R_Pshadow_Pstandard_Cubyte: # @_Pshadow_Pstandard_Cubyte_Mcompare_R_Pshadow_Pstandard_Cubyte
	.cfi_startproc
# BB#0:
	movb	4(%esp), %al
	cmpb	8(%esp), %al
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovael	%ecx, %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cshort_Madd_R_Pshadow_Pstandard_Cshort;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cshort_Madd_R_Pshadow_Pstandard_Cshort
	.align	16, 0x90
__Pshadow_Pstandard_Cshort_Madd_R_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Madd_R_Pshadow_Pstandard_Cshort
	.cfi_startproc
# BB#0:
	movw	4(%esp), %ax
	addw	8(%esp), %ax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cshort_Msubtract_R_Pshadow_Pstandard_Cshort;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cshort_Msubtract_R_Pshadow_Pstandard_Cshort
	.align	16, 0x90
__Pshadow_Pstandard_Cshort_Msubtract_R_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Msubtract_R_Pshadow_Pstandard_Cshort
	.cfi_startproc
# BB#0:
	movw	4(%esp), %ax
	subw	8(%esp), %ax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cshort_Mmultiply_R_Pshadow_Pstandard_Cshort;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cshort_Mmultiply_R_Pshadow_Pstandard_Cshort
	.align	16, 0x90
__Pshadow_Pstandard_Cshort_Mmultiply_R_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Mmultiply_R_Pshadow_Pstandard_Cshort
	.cfi_startproc
# BB#0:
	movw	4(%esp), %ax
	imulw	8(%esp), %ax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cshort_Mdivide_R_Pshadow_Pstandard_Cshort;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cshort_Mdivide_R_Pshadow_Pstandard_Cshort
	.align	16, 0x90
__Pshadow_Pstandard_Cshort_Mdivide_R_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Mdivide_R_Pshadow_Pstandard_Cshort
	.cfi_startproc
# BB#0:
	movw	4(%esp), %ax
	cwtd
	idivw	8(%esp)
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cshort_Mmodulus_R_Pshadow_Pstandard_Cshort;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cshort_Mmodulus_R_Pshadow_Pstandard_Cshort
	.align	16, 0x90
__Pshadow_Pstandard_Cshort_Mmodulus_R_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Mmodulus_R_Pshadow_Pstandard_Cshort
	.cfi_startproc
# BB#0:
	movw	4(%esp), %ax
	cwtd
	idivw	8(%esp)
	movw	%dx, %ax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cshort_Mcompare_R_Pshadow_Pstandard_Cshort;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cshort_Mcompare_R_Pshadow_Pstandard_Cshort
	.align	16, 0x90
__Pshadow_Pstandard_Cshort_Mcompare_R_Pshadow_Pstandard_Cshort: # @_Pshadow_Pstandard_Cshort_Mcompare_R_Pshadow_Pstandard_Cshort
	.cfi_startproc
# BB#0:
	movw	4(%esp), %ax
	cmpw	8(%esp), %ax
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovgel	%ecx, %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cushort_Madd_R_Pshadow_Pstandard_Cushort;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cushort_Madd_R_Pshadow_Pstandard_Cushort
	.align	16, 0x90
__Pshadow_Pstandard_Cushort_Madd_R_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Madd_R_Pshadow_Pstandard_Cushort
	.cfi_startproc
# BB#0:
	movw	4(%esp), %ax
	addw	8(%esp), %ax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cushort_Msubtract_R_Pshadow_Pstandard_Cushort;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cushort_Msubtract_R_Pshadow_Pstandard_Cushort
	.align	16, 0x90
__Pshadow_Pstandard_Cushort_Msubtract_R_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Msubtract_R_Pshadow_Pstandard_Cushort
	.cfi_startproc
# BB#0:
	movw	4(%esp), %ax
	subw	8(%esp), %ax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cushort_Mmultiply_R_Pshadow_Pstandard_Cushort;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cushort_Mmultiply_R_Pshadow_Pstandard_Cushort
	.align	16, 0x90
__Pshadow_Pstandard_Cushort_Mmultiply_R_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Mmultiply_R_Pshadow_Pstandard_Cushort
	.cfi_startproc
# BB#0:
	movw	4(%esp), %ax
	imulw	8(%esp), %ax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cushort_Mdivide_R_Pshadow_Pstandard_Cushort;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cushort_Mdivide_R_Pshadow_Pstandard_Cushort
	.align	16, 0x90
__Pshadow_Pstandard_Cushort_Mdivide_R_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Mdivide_R_Pshadow_Pstandard_Cushort
	.cfi_startproc
# BB#0:
	movw	4(%esp), %ax
	xorl	%edx, %edx
	divw	8(%esp)
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cushort_Mmodulus_R_Pshadow_Pstandard_Cushort;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cushort_Mmodulus_R_Pshadow_Pstandard_Cushort
	.align	16, 0x90
__Pshadow_Pstandard_Cushort_Mmodulus_R_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Mmodulus_R_Pshadow_Pstandard_Cushort
	.cfi_startproc
# BB#0:
	movw	4(%esp), %ax
	xorl	%edx, %edx
	divw	8(%esp)
	movw	%dx, %ax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cushort_Mcompare_R_Pshadow_Pstandard_Cushort;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cushort_Mcompare_R_Pshadow_Pstandard_Cushort
	.align	16, 0x90
__Pshadow_Pstandard_Cushort_Mcompare_R_Pshadow_Pstandard_Cushort: # @_Pshadow_Pstandard_Cushort_Mcompare_R_Pshadow_Pstandard_Cushort
	.cfi_startproc
# BB#0:
	movw	4(%esp), %ax
	cmpw	8(%esp), %ax
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovael	%ecx, %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cint_Madd_R_Pshadow_Pstandard_Cint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cint_Madd_R_Pshadow_Pstandard_Cint
	.align	16, 0x90
__Pshadow_Pstandard_Cint_Madd_R_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Madd_R_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	addl	8(%esp), %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cint_Msubtract_R_Pshadow_Pstandard_Cint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cint_Msubtract_R_Pshadow_Pstandard_Cint
	.align	16, 0x90
__Pshadow_Pstandard_Cint_Msubtract_R_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Msubtract_R_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	subl	8(%esp), %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cint_Mmultiply_R_Pshadow_Pstandard_Cint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cint_Mmultiply_R_Pshadow_Pstandard_Cint
	.align	16, 0x90
__Pshadow_Pstandard_Cint_Mmultiply_R_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mmultiply_R_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	imull	8(%esp), %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cint_Mdivide_R_Pshadow_Pstandard_Cint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cint_Mdivide_R_Pshadow_Pstandard_Cint
	.align	16, 0x90
__Pshadow_Pstandard_Cint_Mdivide_R_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mdivide_R_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	cltd
	idivl	8(%esp)
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cint_Mmodulus_R_Pshadow_Pstandard_Cint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cint_Mmodulus_R_Pshadow_Pstandard_Cint
	.align	16, 0x90
__Pshadow_Pstandard_Cint_Mmodulus_R_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mmodulus_R_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	cltd
	idivl	8(%esp)
	movl	%edx, %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cint_Mcompare_R_Pshadow_Pstandard_Cint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cint_Mcompare_R_Pshadow_Pstandard_Cint
	.align	16, 0x90
__Pshadow_Pstandard_Cint_Mcompare_R_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_Cint_Mcompare_R_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	cmpl	8(%esp), %eax
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovgel	%ecx, %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cuint_Madd_R_Pshadow_Pstandard_Cuint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cuint_Madd_R_Pshadow_Pstandard_Cuint
	.align	16, 0x90
__Pshadow_Pstandard_Cuint_Madd_R_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Madd_R_Pshadow_Pstandard_Cuint
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	addl	8(%esp), %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cuint_Msubtract_R_Pshadow_Pstandard_Cuint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cuint_Msubtract_R_Pshadow_Pstandard_Cuint
	.align	16, 0x90
__Pshadow_Pstandard_Cuint_Msubtract_R_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Msubtract_R_Pshadow_Pstandard_Cuint
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	subl	8(%esp), %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cuint_Mmultiply_R_Pshadow_Pstandard_Cuint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cuint_Mmultiply_R_Pshadow_Pstandard_Cuint
	.align	16, 0x90
__Pshadow_Pstandard_Cuint_Mmultiply_R_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mmultiply_R_Pshadow_Pstandard_Cuint
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	imull	8(%esp), %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cuint_Mdivide_R_Pshadow_Pstandard_Cuint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cuint_Mdivide_R_Pshadow_Pstandard_Cuint
	.align	16, 0x90
__Pshadow_Pstandard_Cuint_Mdivide_R_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mdivide_R_Pshadow_Pstandard_Cuint
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	xorl	%edx, %edx
	divl	8(%esp)
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cuint_Mmodulus_R_Pshadow_Pstandard_Cuint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cuint_Mmodulus_R_Pshadow_Pstandard_Cuint
	.align	16, 0x90
__Pshadow_Pstandard_Cuint_Mmodulus_R_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mmodulus_R_Pshadow_Pstandard_Cuint
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	xorl	%edx, %edx
	divl	8(%esp)
	movl	%edx, %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cuint_Mcompare_R_Pshadow_Pstandard_Cuint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cuint_Mcompare_R_Pshadow_Pstandard_Cuint
	.align	16, 0x90
__Pshadow_Pstandard_Cuint_Mcompare_R_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mcompare_R_Pshadow_Pstandard_Cuint
	.cfi_startproc
# BB#0:
	movl	4(%esp), %eax
	cmpl	8(%esp), %eax
	setne	%al
	movzbl	%al, %ecx
	movl	$-1, %eax
	cmovael	%ecx, %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Clong_Madd_R_Pshadow_Pstandard_Clong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Clong_Madd_R_Pshadow_Pstandard_Clong
	.align	16, 0x90
__Pshadow_Pstandard_Clong_Madd_R_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Madd_R_Pshadow_Pstandard_Clong
	.cfi_startproc
# BB#0:
	movl	8(%esp), %edx
	movl	4(%esp), %eax
	addl	12(%esp), %eax
	adcl	16(%esp), %edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Clong_Msubtract_R_Pshadow_Pstandard_Clong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Clong_Msubtract_R_Pshadow_Pstandard_Clong
	.align	16, 0x90
__Pshadow_Pstandard_Clong_Msubtract_R_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Msubtract_R_Pshadow_Pstandard_Clong
	.cfi_startproc
# BB#0:
	movl	8(%esp), %edx
	movl	4(%esp), %eax
	subl	12(%esp), %eax
	sbbl	16(%esp), %edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Clong_Mmultiply_R_Pshadow_Pstandard_Clong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Clong_Mmultiply_R_Pshadow_Pstandard_Clong
	.align	16, 0x90
__Pshadow_Pstandard_Clong_Mmultiply_R_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Mmultiply_R_Pshadow_Pstandard_Clong
	.cfi_startproc
# BB#0:
	pushl	%esi
Ltmp117:
	.cfi_def_cfa_offset 8
Ltmp118:
	.cfi_offset %esi, -8
	movl	8(%esp), %esi
	movl	16(%esp), %ecx
	movl	%esi, %eax
	mull	%ecx
	imull	20(%esp), %esi
	addl	%edx, %esi
	imull	12(%esp), %ecx
	addl	%esi, %ecx
	movl	%ecx, %edx
	popl	%esi
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Clong_Mdivide_R_Pshadow_Pstandard_Clong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Clong_Mdivide_R_Pshadow_Pstandard_Clong
	.align	16, 0x90
__Pshadow_Pstandard_Clong_Mdivide_R_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Mdivide_R_Pshadow_Pstandard_Clong
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp120:
	.cfi_def_cfa_offset 20
	movl	32(%esp), %eax
	movl	%eax, 12(%esp)
	movl	28(%esp), %eax
	movl	%eax, 8(%esp)
	movl	24(%esp), %eax
	movl	%eax, 4(%esp)
	movl	20(%esp), %eax
	movl	%eax, (%esp)
	calll	___divdi3
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Clong_Mmodulus_R_Pshadow_Pstandard_Clong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Clong_Mmodulus_R_Pshadow_Pstandard_Clong
	.align	16, 0x90
__Pshadow_Pstandard_Clong_Mmodulus_R_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Mmodulus_R_Pshadow_Pstandard_Clong
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp122:
	.cfi_def_cfa_offset 20
	movl	32(%esp), %eax
	movl	%eax, 12(%esp)
	movl	28(%esp), %eax
	movl	%eax, 8(%esp)
	movl	24(%esp), %eax
	movl	%eax, 4(%esp)
	movl	20(%esp), %eax
	movl	%eax, (%esp)
	calll	___moddi3
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Clong_Mcompare_R_Pshadow_Pstandard_Clong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Clong_Mcompare_R_Pshadow_Pstandard_Clong
	.align	16, 0x90
__Pshadow_Pstandard_Clong_Mcompare_R_Pshadow_Pstandard_Clong: # @_Pshadow_Pstandard_Clong_Mcompare_R_Pshadow_Pstandard_Clong
	.cfi_startproc
# BB#0:
	pushl	%edi
Ltmp126:
	.cfi_def_cfa_offset 8
	pushl	%esi
Ltmp127:
	.cfi_def_cfa_offset 12
Ltmp128:
	.cfi_offset %esi, -12
Ltmp129:
	.cfi_offset %edi, -8
	movl	20(%esp), %edx
	movl	12(%esp), %eax
	cmpl	%edx, %eax
	setb	%cl
	movl	24(%esp), %edi
	movl	16(%esp), %esi
	cmpl	%edi, %esi
	setl	%ch
	je	LBB69_2
# BB#1:
	movb	%ch, %cl
LBB69_2:
	xorl	%edi, %esi
	xorl	%edx, %eax
	orl	%esi, %eax
	setne	%al
	movzbl	%al, %edx
	testb	%cl, %cl
	movl	$-1, %eax
	cmovel	%edx, %eax
	popl	%esi
	popl	%edi
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Culong_Madd_R_Pshadow_Pstandard_Culong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Culong_Madd_R_Pshadow_Pstandard_Culong
	.align	16, 0x90
__Pshadow_Pstandard_Culong_Madd_R_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Madd_R_Pshadow_Pstandard_Culong
	.cfi_startproc
# BB#0:
	movl	8(%esp), %edx
	movl	4(%esp), %eax
	addl	12(%esp), %eax
	adcl	16(%esp), %edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Culong_Msubtract_R_Pshadow_Pstandard_Culong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Culong_Msubtract_R_Pshadow_Pstandard_Culong
	.align	16, 0x90
__Pshadow_Pstandard_Culong_Msubtract_R_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Msubtract_R_Pshadow_Pstandard_Culong
	.cfi_startproc
# BB#0:
	movl	8(%esp), %edx
	movl	4(%esp), %eax
	subl	12(%esp), %eax
	sbbl	16(%esp), %edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Culong_Mmultiply_R_Pshadow_Pstandard_Culong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Culong_Mmultiply_R_Pshadow_Pstandard_Culong
	.align	16, 0x90
__Pshadow_Pstandard_Culong_Mmultiply_R_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Mmultiply_R_Pshadow_Pstandard_Culong
	.cfi_startproc
# BB#0:
	pushl	%esi
Ltmp132:
	.cfi_def_cfa_offset 8
Ltmp133:
	.cfi_offset %esi, -8
	movl	8(%esp), %esi
	movl	16(%esp), %ecx
	movl	%esi, %eax
	mull	%ecx
	imull	20(%esp), %esi
	addl	%edx, %esi
	imull	12(%esp), %ecx
	addl	%esi, %ecx
	movl	%ecx, %edx
	popl	%esi
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Culong_Mdivide_R_Pshadow_Pstandard_Culong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Culong_Mdivide_R_Pshadow_Pstandard_Culong
	.align	16, 0x90
__Pshadow_Pstandard_Culong_Mdivide_R_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Mdivide_R_Pshadow_Pstandard_Culong
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp135:
	.cfi_def_cfa_offset 20
	movl	32(%esp), %eax
	movl	%eax, 12(%esp)
	movl	28(%esp), %eax
	movl	%eax, 8(%esp)
	movl	24(%esp), %eax
	movl	%eax, 4(%esp)
	movl	20(%esp), %eax
	movl	%eax, (%esp)
	calll	___udivdi3
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Culong_Mmodulus_R_Pshadow_Pstandard_Culong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Culong_Mmodulus_R_Pshadow_Pstandard_Culong
	.align	16, 0x90
__Pshadow_Pstandard_Culong_Mmodulus_R_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Mmodulus_R_Pshadow_Pstandard_Culong
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp137:
	.cfi_def_cfa_offset 20
	movl	32(%esp), %eax
	movl	%eax, 12(%esp)
	movl	28(%esp), %eax
	movl	%eax, 8(%esp)
	movl	24(%esp), %eax
	movl	%eax, 4(%esp)
	movl	20(%esp), %eax
	movl	%eax, (%esp)
	calll	___umoddi3
	addl	$16, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Culong_Mcompare_R_Pshadow_Pstandard_Culong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Culong_Mcompare_R_Pshadow_Pstandard_Culong
	.align	16, 0x90
__Pshadow_Pstandard_Culong_Mcompare_R_Pshadow_Pstandard_Culong: # @_Pshadow_Pstandard_Culong_Mcompare_R_Pshadow_Pstandard_Culong
	.cfi_startproc
# BB#0:
	pushl	%edi
Ltmp141:
	.cfi_def_cfa_offset 8
	pushl	%esi
Ltmp142:
	.cfi_def_cfa_offset 12
Ltmp143:
	.cfi_offset %esi, -12
Ltmp144:
	.cfi_offset %edi, -8
	movl	20(%esp), %edx
	movl	12(%esp), %eax
	cmpl	%edx, %eax
	setb	%cl
	movl	24(%esp), %edi
	movl	16(%esp), %esi
	cmpl	%edi, %esi
	setb	%ch
	je	LBB75_2
# BB#1:
	movb	%ch, %cl
LBB75_2:
	xorl	%edi, %esi
	xorl	%edx, %eax
	orl	%esi, %eax
	setne	%al
	movzbl	%al, %edx
	testb	%cl, %cl
	movl	$-1, %eax
	cmovel	%edx, %eax
	popl	%esi
	popl	%edi
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cfloat_Madd_R_Pshadow_Pstandard_Cfloat;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cfloat_Madd_R_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
__Pshadow_Pstandard_Cfloat_Madd_R_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Madd_R_Pshadow_Pstandard_Cfloat
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp146:
	.cfi_def_cfa_offset 8
	movss	8(%esp), %xmm0
	addss	12(%esp), %xmm0
	movss	%xmm0, (%esp)
	flds	(%esp)
	popl	%eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cfloat_Msubtract_R_Pshadow_Pstandard_Cfloat;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cfloat_Msubtract_R_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
__Pshadow_Pstandard_Cfloat_Msubtract_R_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Msubtract_R_Pshadow_Pstandard_Cfloat
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp148:
	.cfi_def_cfa_offset 8
	movss	8(%esp), %xmm0
	subss	12(%esp), %xmm0
	movss	%xmm0, (%esp)
	flds	(%esp)
	popl	%eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cfloat_Mmultiply_R_Pshadow_Pstandard_Cfloat;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cfloat_Mmultiply_R_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
__Pshadow_Pstandard_Cfloat_Mmultiply_R_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Mmultiply_R_Pshadow_Pstandard_Cfloat
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp150:
	.cfi_def_cfa_offset 8
	movss	8(%esp), %xmm0
	mulss	12(%esp), %xmm0
	movss	%xmm0, (%esp)
	flds	(%esp)
	popl	%eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cfloat_Mdivide_R_Pshadow_Pstandard_Cfloat;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cfloat_Mdivide_R_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
__Pshadow_Pstandard_Cfloat_Mdivide_R_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Mdivide_R_Pshadow_Pstandard_Cfloat
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp152:
	.cfi_def_cfa_offset 8
	movss	8(%esp), %xmm0
	divss	12(%esp), %xmm0
	movss	%xmm0, (%esp)
	flds	(%esp)
	popl	%eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cfloat_Mmodulus_R_Pshadow_Pstandard_Cfloat;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cfloat_Mmodulus_R_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
__Pshadow_Pstandard_Cfloat_Mmodulus_R_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Mmodulus_R_Pshadow_Pstandard_Cfloat
	.cfi_startproc
# BB#0:
	jmp	_fmodf                  # TAILCALL
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cfloat_Mcompare_R_Pshadow_Pstandard_Cfloat;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cfloat_Mcompare_R_Pshadow_Pstandard_Cfloat
	.align	16, 0x90
__Pshadow_Pstandard_Cfloat_Mcompare_R_Pshadow_Pstandard_Cfloat: # @_Pshadow_Pstandard_Cfloat_Mcompare_R_Pshadow_Pstandard_Cfloat
	.cfi_startproc
# BB#0:
	movss	8(%esp), %xmm0
	movss	4(%esp), %xmm1
	movaps	%xmm1, %xmm2
	cmpneqss	%xmm0, %xmm2
	movd	%xmm2, %ecx
	andl	$1, %ecx
	ucomiss	%xmm1, %xmm0
	movl	$-1, %eax
	cmovbel	%ecx, %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cdouble_Madd_R_Pshadow_Pstandard_Cdouble;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cdouble_Madd_R_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
__Pshadow_Pstandard_Cdouble_Madd_R_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Madd_R_Pshadow_Pstandard_Cdouble
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp155:
	.cfi_def_cfa_offset 8
Ltmp156:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp157:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	subl	$8, %esp
	movsd	8(%ebp), %xmm0
	addsd	16(%ebp), %xmm0
	movsd	%xmm0, (%esp)
	fldl	(%esp)
	movl	%ebp, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cdouble_Msubtract_R_Pshadow_Pstandard_Cdouble;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cdouble_Msubtract_R_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
__Pshadow_Pstandard_Cdouble_Msubtract_R_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Msubtract_R_Pshadow_Pstandard_Cdouble
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp160:
	.cfi_def_cfa_offset 8
Ltmp161:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp162:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	subl	$8, %esp
	movsd	8(%ebp), %xmm0
	subsd	16(%ebp), %xmm0
	movsd	%xmm0, (%esp)
	fldl	(%esp)
	movl	%ebp, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cdouble_Mmultiply_R_Pshadow_Pstandard_Cdouble;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cdouble_Mmultiply_R_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
__Pshadow_Pstandard_Cdouble_Mmultiply_R_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Mmultiply_R_Pshadow_Pstandard_Cdouble
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp165:
	.cfi_def_cfa_offset 8
Ltmp166:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp167:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	subl	$8, %esp
	movsd	8(%ebp), %xmm0
	mulsd	16(%ebp), %xmm0
	movsd	%xmm0, (%esp)
	fldl	(%esp)
	movl	%ebp, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cdouble_Mdivide_R_Pshadow_Pstandard_Cdouble;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cdouble_Mdivide_R_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
__Pshadow_Pstandard_Cdouble_Mdivide_R_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Mdivide_R_Pshadow_Pstandard_Cdouble
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp170:
	.cfi_def_cfa_offset 8
Ltmp171:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp172:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	subl	$8, %esp
	movsd	8(%ebp), %xmm0
	divsd	16(%ebp), %xmm0
	movsd	%xmm0, (%esp)
	fldl	(%esp)
	movl	%ebp, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cdouble_Mmodulus_R_Pshadow_Pstandard_Cdouble;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cdouble_Mmodulus_R_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
__Pshadow_Pstandard_Cdouble_Mmodulus_R_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Mmodulus_R_Pshadow_Pstandard_Cdouble
	.cfi_startproc
# BB#0:
	jmp	_fmod                   # TAILCALL
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cdouble_Mcompare_R_Pshadow_Pstandard_Cdouble;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cdouble_Mcompare_R_Pshadow_Pstandard_Cdouble
	.align	16, 0x90
__Pshadow_Pstandard_Cdouble_Mcompare_R_Pshadow_Pstandard_Cdouble: # @_Pshadow_Pstandard_Cdouble_Mcompare_R_Pshadow_Pstandard_Cdouble
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp175:
	.cfi_def_cfa_offset 8
Ltmp176:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp177:
	.cfi_def_cfa_register %ebp
	movsd	16(%ebp), %xmm0
	movsd	8(%ebp), %xmm1
	movapd	%xmm1, %xmm2
	cmpneqsd	%xmm0, %xmm2
	movd	%xmm2, %ecx
	andl	$1, %ecx
	ucomisd	%xmm1, %xmm0
	movl	$-1, %eax
	cmovbel	%ecx, %eax
	popl	%ebp
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
Ltmp179:
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
Ltmp181:
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
Ltmp183:
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

	.def	 __Pshadow_Pstandard_Cshort_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cshort_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_Cshort_Mcreate:     # @_Pshadow_Pstandard_Cshort_Mcreate
	.cfi_startproc
# BB#0:
	subl	$2, %esp
Ltmp185:
	.cfi_def_cfa_offset 6
	movw	6(%esp), %ax
	movw	%ax, (%esp)
	addl	$2, %esp
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
Ltmp190:
	.cfi_def_cfa_offset 8
	pushl	%edi
Ltmp191:
	.cfi_def_cfa_offset 12
	pushl	%esi
Ltmp192:
	.cfi_def_cfa_offset 16
	subl	$20, %esp
Ltmp193:
	.cfi_def_cfa_offset 36
Ltmp194:
	.cfi_offset %esi, -16
Ltmp195:
	.cfi_offset %edi, -12
Ltmp196:
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
	jne	LBB96_1
# BB#3:                                 # %_label2
	movl	$0, 8(%esp)
	jmp	LBB96_6
	.align	16, 0x90
LBB96_5:                                # %_label8
                                        #   in Loop: Header=BB96_6 Depth=1
	incl	8(%esp)
LBB96_6:                                # %_label4
                                        # =>This Inner Loop Header: Depth=1
	movl	16(%esp), %eax
	movl	(%eax), %ecx
	movl	8(%esp), %esi
	movl	%eax, (%esp)
	calll	*64(%ecx)
	cmpl	%eax, %esi
	jge	LBB96_7
# BB#4:                                 # %_label3
                                        #   in Loop: Header=BB96_6 Depth=1
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
	je	LBB96_5
LBB96_1:                                # %_label0
	xorb	%al, %al
	jmp	LBB96_2
LBB96_7:                                # %_label5
	movb	$1, %al
LBB96_2:                                # %_label0
	addl	$20, %esp
	popl	%esi
	popl	%edi
	popl	%ebx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_CString_MgetByte_R_Pshadow_Pstandard_Cint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_CString_MgetByte_R_Pshadow_Pstandard_Cint
	.align	16, 0x90
__Pshadow_Pstandard_CString_MgetByte_R_Pshadow_Pstandard_Cint: # @_Pshadow_Pstandard_CString_MgetByte_R_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	subl	$8, %esp
Ltmp198:
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
Ltmp203:
	.cfi_def_cfa_offset 8
	pushl	%edi
Ltmp204:
	.cfi_def_cfa_offset 12
	pushl	%esi
Ltmp205:
	.cfi_def_cfa_offset 16
	subl	$20, %esp
Ltmp206:
	.cfi_def_cfa_offset 36
Ltmp207:
	.cfi_offset %esi, -16
Ltmp208:
	.cfi_offset %edi, -12
Ltmp209:
	.cfi_offset %ebx, -8
	movl	36(%esp), %eax
	movl	%eax, 16(%esp)
	movl	40(%esp), %eax
	movl	%eax, 12(%esp)
	movl	$0, 8(%esp)
	jmp	LBB98_4
	.align	16, 0x90
LBB98_3:                                # %_label14
                                        #   in Loop: Header=BB98_4 Depth=1
	incl	8(%esp)
LBB98_4:                                # %_label10
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
	jge	LBB98_6
# BB#5:                                 # %_label10
                                        #   in Loop: Header=BB98_4 Depth=1
	cmpl	%eax, %ebx
	jge	LBB98_6
# BB#1:                                 # %_label9
                                        #   in Loop: Header=BB98_4 Depth=1
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
	je	LBB98_3
# BB#2:                                 # %_label12
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
	jmp	LBB98_7
LBB98_6:                                # %_label11
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
LBB98_7:                                # %_label11
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
Ltmp214:
	.cfi_def_cfa_offset 8
	pushl	%edi
Ltmp215:
	.cfi_def_cfa_offset 12
	pushl	%esi
Ltmp216:
	.cfi_def_cfa_offset 16
	subl	$28, %esp
Ltmp217:
	.cfi_def_cfa_offset 44
Ltmp218:
	.cfi_offset %esi, -16
Ltmp219:
	.cfi_offset %edi, -12
Ltmp220:
	.cfi_offset %ebx, -8
	movl	44(%esp), %eax
	movl	%eax, 24(%esp)
	movl	4(%eax), %edi
	movl	8(%eax), %ebx
	movl	$4, (%esp)
	calll	_malloc
	movl	%eax, %esi
	movl	$4, (%esp)
	calll	_malloc
	movl	%ebx, (%eax)
	movl	%eax, 16(%esp)
	movl	%edi, 8(%esp)
	movl	%esi, (%esp)
	movl	$1, 20(%esp)
	movl	$0, 12(%esp)
	movl	$__Pshadow_Pstandard_Cubyte_Mclass, 4(%esp)
	calll	__Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1
	movl	(%eax), %ecx
	movl	%eax, (%esp)
	calll	*64(%ecx)
	addl	$28, %esp
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
Ltmp222:
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
Ltmp225:
	.cfi_def_cfa_offset 8
	subl	$12, %esp
Ltmp226:
	.cfi_def_cfa_offset 20
Ltmp227:
	.cfi_offset %esi, -8
	movl	20(%esp), %eax
	movl	%eax, 8(%esp)
	movl	$__Pshadow_Pstandard_CString_Mclass, (%eax)
	movl	8(%esp), %esi
	movl	$1, 4(%esp)
	movl	$0, (%esp)
	calll	_calloc
	movl	%eax, 4(%esi)
	movl	$0, 8(%esi)
	movl	8(%esp), %eax
	addl	$12, %esp
	popl	%esi
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cubyte_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cubyte_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_Cubyte_Mcreate:     # @_Pshadow_Pstandard_Cubyte_Mcreate
	.cfi_startproc
# BB#0:
	subl	$1, %esp
Ltmp229:
	.cfi_def_cfa_offset 5
	movb	5(%esp), %al
	movb	%al, (%esp)
	addl	$1, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cuint_Mmin_R_Pshadow_Pstandard_Cuint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cuint_Mmin_R_Pshadow_Pstandard_Cuint
	.align	16, 0x90
__Pshadow_Pstandard_Cuint_Mmin_R_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mmin_R_Pshadow_Pstandard_Cuint
	.cfi_startproc
# BB#0:
	subl	$12, %esp
Ltmp231:
	.cfi_def_cfa_offset 16
	movl	16(%esp), %eax
	movl	%eax, 8(%esp)
	movl	20(%esp), %eax
	movl	%eax, 4(%esp)
	cmpl	%eax, 8(%esp)
	jae	LBB103_2
# BB#1:                                 # %_label0
	movl	8(%esp), %eax
	jmp	LBB103_3
LBB103_2:                               # %_label1
	movl	4(%esp), %eax
LBB103_3:                               # %_label1
	movl	%eax, (%esp)
	movl	(%esp), %eax
	addl	$12, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cuint_Mmax_R_Pshadow_Pstandard_Cuint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cuint_Mmax_R_Pshadow_Pstandard_Cuint
	.align	16, 0x90
__Pshadow_Pstandard_Cuint_Mmax_R_Pshadow_Pstandard_Cuint: # @_Pshadow_Pstandard_Cuint_Mmax_R_Pshadow_Pstandard_Cuint
	.cfi_startproc
# BB#0:
	subl	$12, %esp
Ltmp233:
	.cfi_def_cfa_offset 16
	movl	16(%esp), %eax
	movl	%eax, 8(%esp)
	movl	20(%esp), %eax
	movl	%eax, 4(%esp)
	cmpl	%eax, 8(%esp)
	jbe	LBB104_2
# BB#1:                                 # %_label3
	movl	8(%esp), %eax
	jmp	LBB104_3
LBB104_2:                               # %_label4
	movl	4(%esp), %eax
LBB104_3:                               # %_label4
	movl	%eax, (%esp)
	movl	(%esp), %eax
	addl	$12, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cuint_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cuint_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_Cuint_Mcreate:      # @_Pshadow_Pstandard_Cuint_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp235:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %eax
	movl	%eax, (%esp)
	popl	%edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Culong_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Culong_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_Culong_Mcreate:     # @_Pshadow_Pstandard_Culong_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp238:
	.cfi_def_cfa_offset 8
Ltmp239:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp240:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	subl	$8, %esp
	movl	12(%ebp), %edx
	movl	%edx, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	movl	%ebp, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pstandard_Cushort_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pstandard_Cushort_Mcreate
	.align	16, 0x90
__Pshadow_Pstandard_Cushort_Mcreate:    # @_Pshadow_Pstandard_Cushort_Mcreate
	.cfi_startproc
# BB#0:
	subl	$2, %esp
Ltmp242:
	.cfi_def_cfa_offset 6
	movw	6(%esp), %ax
	movw	%ax, (%esp)
	addl	$2, %esp
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cboolean;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cboolean
	.align	16, 0x90
__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cboolean: # @_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cboolean
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp244:
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

	.def	 __Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cint
	.align	16, 0x90
__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cint: # @_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp246:
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

	.def	 __Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Ccode;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Ccode
	.align	16, 0x90
__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Ccode: # @_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Ccode
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp248:
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

	.def	 __Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Clong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Clong
	.align	16, 0x90
__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Clong: # @_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Clong
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp252:
	.cfi_def_cfa_offset 8
Ltmp253:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp254:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	pushl	%esi
	subl	$28, %esp
Ltmp255:
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
Ltmp257:
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
Ltmp259:
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
Ltmp261:
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
Ltmp263:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %eax
	movl	%eax, (%esp)
	movl	$__Pshadow_Pio_CConsole_Mclass, (%eax)
	movl	(%esp), %eax
	popl	%edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cboolean;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cboolean
	.align	16, 0x90
__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cboolean: # @_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cboolean
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp265:
	.cfi_def_cfa_offset 20
	movl	20(%esp), %eax
	movl	%eax, 12(%esp)
	movb	24(%esp), %al
	andb	$1, %al
	movb	%al, 11(%esp)
	testb	%al, %al
	je	LBB116_2
# BB#1:                                 # %_label0
	movl	12(%esp), %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$L__string1180, 4(%esp)
	jmp	LBB116_3
LBB116_2:                               # %_label1
	movl	12(%esp), %ecx
	movl	(%ecx), %eax
	movl	%ecx, (%esp)
	movl	$L__string2, 4(%esp)
LBB116_3:                               # %_label1
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
Ltmp267:
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
	je	LBB118_2
# BB#1:
	movl	_stdout, %eax
	ret
LBB118_2:
	movl	__imp___iob, %eax
	addl	$32, %eax
	ret
	.cfi_endproc

	.def	 __Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cint;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cint
	.align	16, 0x90
__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cint: # @_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cint
	.cfi_startproc
# BB#0:
	subl	$12, %esp
Ltmp269:
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

	.def	 __Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Ccode;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Ccode
	.align	16, 0x90
__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Ccode: # @_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Ccode
	.cfi_startproc
# BB#0:
	subl	$12, %esp
Ltmp271:
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

	.def	 __Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Clong;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Clong
	.align	16, 0x90
__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Clong: # @_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Clong
	.cfi_startproc
# BB#0:
	subl	$16, %esp
Ltmp273:
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
Ltmp277:
	.cfi_def_cfa_offset 8
	pushl	%esi
Ltmp278:
	.cfi_def_cfa_offset 12
	subl	$16, %esp
Ltmp279:
	.cfi_def_cfa_offset 28
Ltmp280:
	.cfi_offset %esi, -12
Ltmp281:
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

	.def	 __Pshadow_Ptest_CArrayTest_Mcreate;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Ptest_CArrayTest_Mcreate
	.align	16, 0x90
__Pshadow_Ptest_CArrayTest_Mcreate:     # @_Pshadow_Ptest_CArrayTest_Mcreate
	.cfi_startproc
# BB#0:
	pushl	%eax
Ltmp283:
	.cfi_def_cfa_offset 8
	movl	8(%esp), %eax
	movl	%eax, (%esp)
	movl	$__Pshadow_Ptest_CArrayTest_Mclass, (%eax)
	movl	(%esp), %eax
	popl	%edx
	ret
	.cfi_endproc

	.def	 __Pshadow_Ptest_CArrayTest_Mmain;
	.scl	2;
	.type	32;
	.endef
	.globl	__Pshadow_Ptest_CArrayTest_Mmain
	.align	16, 0x90
__Pshadow_Ptest_CArrayTest_Mmain:       # @_Pshadow_Ptest_CArrayTest_Mmain
	.cfi_startproc
# BB#0:
	pushl	%ebp
Ltmp287:
	.cfi_def_cfa_offset 8
Ltmp288:
	.cfi_offset %ebp, -8
	movl	%esp, %ebp
Ltmp289:
	.cfi_def_cfa_register %ebp
	andl	$-8, %esp
	pushl	%esi
	subl	$36, %esp
Ltmp290:
	.cfi_offset %esi, -12
	movl	8(%ebp), %eax
	movl	%eax, 32(%esp)
	movl	$4, 4(%esp)
	movl	$1, (%esp)
	calll	_calloc
	movl	%eax, 24(%esp)
	movl	$1, 28(%esp)
	movl	24(%esp), %eax
	movl	$5, (%eax)
	movl	$__Pshadow_Pstandard_CArray_Mclass, (%esp)
	calll	__Pshadow_Pstandard_CClass_McreateObject
	movl	%eax, %esi
	movl	24(%esp), %eax
	movl	28(%esp), %ecx
	movl	%ecx, 12(%esp)
	movl	%eax, 8(%esp)
	movl	%esi, (%esp)
	movl	$__Pshadow_Pstandard_CObject_Mclass, 4(%esp)
	calll	__Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Cint_A1
	movl	%esi, 20(%esp)
	addl	$36, %esp
	popl	%esi
	movl	%ebp, %esp
	popl	%ebp
	ret
	.cfi_endproc

	.data
	.globl	__Pshadow_Pstandard_CArray_Mclass # @_Pshadow_Pstandard_CArray_Mclass
	.align	16
__Pshadow_Pstandard_CArray_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string0
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	0+24
	.long	8                       # 0x8
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pstandard_CArray_McreateArray_R_Pshadow_Pstandard_Cint
	.long	__Pshadow_Pstandard_CArray_Mdims
	.long	__Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint
	.long	__Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint_RT
	.long	__Pshadow_Pstandard_CArray_Mlength
	.long	__Pshadow_Pstandard_CArray_Mlengths

	.align	16                      # @_array0
L__array0:
	.ascii	 "shadow.standard@Array<T>"

	.align	8                       # @_string0
L__string0:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array0
	.long	24                      # 0x18

	.globl	__Pshadow_Pstandard_Cboolean_Mclass # @_Pshadow_Pstandard_Cboolean_Mclass
	.align	16
__Pshadow_Pstandard_Cboolean_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string010
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString

L__array09:                             # @_array09
	.ascii	 "boolean"

	.align	8                       # @_string010
L__string010:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array09
	.long	7                       # 0x7

	.globl	__Pshadow_Pstandard_Cbyte_Mclass # @_Pshadow_Pstandard_Cbyte_Mclass
	.align	16
__Pshadow_Pstandard_Cbyte_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string014
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pstandard_Cbyte_Madd_R_Pshadow_Pstandard_Cbyte
	.long	__Pshadow_Pstandard_Cbyte_Mcompare_R_Pshadow_Pstandard_Cbyte
	.long	__Pshadow_Pstandard_Cbyte_Mdivide_R_Pshadow_Pstandard_Cbyte
	.long	__Pshadow_Pstandard_Cbyte_Mmodulus_R_Pshadow_Pstandard_Cbyte
	.long	__Pshadow_Pstandard_Cbyte_Mmultiply_R_Pshadow_Pstandard_Cbyte
	.long	__Pshadow_Pstandard_Cbyte_Msubtract_R_Pshadow_Pstandard_Cbyte

L__array013:                            # @_array013
	.ascii	 "byte"

	.align	8                       # @_string014
L__string014:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array013
	.long	4                       # 0x4

	.globl	__Pshadow_Pstandard_CClass_Mclass # @_Pshadow_Pstandard_CClass_Mclass
	.align	16
__Pshadow_Pstandard_CClass_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string018
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	0+28
	.long	8                       # 0x8
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CClass_MtoString
	.long	__Pshadow_Pstandard_CClass_McreateObject
	.long	__Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass
	.long	__Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface
	.long	__Pshadow_Pstandard_CClass_Mparent

	.align	16                      # @_array017
L__array017:
	.ascii	 "shadow.standard@Class"

	.align	8                       # @_string018
L__string018:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array017
	.long	21                      # 0x15

L__array1:                              # @_array1

	.align	8                       # @_string1
L__string1:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array1
	.zero	4

	.globl	__Pshadow_Pstandard_Ccode_Mclass # @_Pshadow_Pstandard_Ccode_Mclass
	.align	16
__Pshadow_Pstandard_Ccode_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string034
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	4                       # 0x4
	.long	4                       # 0x4
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString

L__array033:                            # @_array033
	.ascii	 "code"

	.align	8                       # @_string034
L__string034:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array033
	.long	4                       # 0x4

	.globl	__Pshadow_Pstandard_Cdouble_Mclass # @_Pshadow_Pstandard_Cdouble_Mclass
	.align	16
__Pshadow_Pstandard_Cdouble_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string038
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	8                       # 0x8
	.long	8                       # 0x8
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pstandard_Cdouble_Madd_R_Pshadow_Pstandard_Cdouble
	.long	__Pshadow_Pstandard_Cdouble_Mcompare_R_Pshadow_Pstandard_Cdouble
	.long	__Pshadow_Pstandard_Cdouble_Mdivide_R_Pshadow_Pstandard_Cdouble
	.long	__Pshadow_Pstandard_Cdouble_Mmodulus_R_Pshadow_Pstandard_Cdouble
	.long	__Pshadow_Pstandard_Cdouble_Mmultiply_R_Pshadow_Pstandard_Cdouble
	.long	__Pshadow_Pstandard_Cdouble_Msubtract_R_Pshadow_Pstandard_Cdouble

L__array037:                            # @_array037
	.ascii	 "double"

	.align	8                       # @_string038
L__string038:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array037
	.long	6                       # 0x6

	.globl	__Pshadow_Pstandard_Cfloat_Mclass # @_Pshadow_Pstandard_Cfloat_Mclass
	.align	16
__Pshadow_Pstandard_Cfloat_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string042
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	4                       # 0x4
	.long	4                       # 0x4
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pstandard_Cfloat_Madd_R_Pshadow_Pstandard_Cfloat
	.long	__Pshadow_Pstandard_Cfloat_Mcompare_R_Pshadow_Pstandard_Cfloat
	.long	__Pshadow_Pstandard_Cfloat_Mdivide_R_Pshadow_Pstandard_Cfloat
	.long	__Pshadow_Pstandard_Cfloat_Mmodulus_R_Pshadow_Pstandard_Cfloat
	.long	__Pshadow_Pstandard_Cfloat_Mmultiply_R_Pshadow_Pstandard_Cfloat
	.long	__Pshadow_Pstandard_Cfloat_Msubtract_R_Pshadow_Pstandard_Cfloat

L__array041:                            # @_array041
	.ascii	 "float"

	.align	8                       # @_string042
L__string042:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array041
	.long	5                       # 0x5

	.globl	__Pshadow_Pstandard_Cint_Mclass # @_Pshadow_Pstandard_Cint_Mclass
	.align	16
__Pshadow_Pstandard_Cint_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string046
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	4                       # 0x4
	.long	4                       # 0x4
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pstandard_Cint_Mabs
	.long	__Pshadow_Pstandard_Cint_Madd_R_Pshadow_Pstandard_Cint
	.long	__Pshadow_Pstandard_Cint_Mcompare_R_Pshadow_Pstandard_Cint
	.long	__Pshadow_Pstandard_Cint_Mdivide_R_Pshadow_Pstandard_Cint
	.long	__Pshadow_Pstandard_Cint_Mmax_R_Pshadow_Pstandard_Cint
	.long	__Pshadow_Pstandard_Cint_Mmin_R_Pshadow_Pstandard_Cint
	.long	__Pshadow_Pstandard_Cint_Mmodulus_R_Pshadow_Pstandard_Cint
	.long	__Pshadow_Pstandard_Cint_Mmultiply_R_Pshadow_Pstandard_Cint
	.long	__Pshadow_Pstandard_Cint_Msubtract_R_Pshadow_Pstandard_Cint

L__array045:                            # @_array045
	.ascii	 "int"

	.align	8                       # @_string046
L__string046:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array045
	.long	3                       # 0x3

	.globl	__Pshadow_Pstandard_CInterface_Mclass # @_Pshadow_Pstandard_CInterface_Mclass
	.align	16
__Pshadow_Pstandard_CInterface_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string050
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	0+16
	.long	8                       # 0x8
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CInterface_MtoString
	.long	__Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface

	.align	16                      # @_array049
L__array049:
	.ascii	 "shadow.standard@Interface"

	.align	8                       # @_string050
L__string050:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array049
	.long	25                      # 0x19

L__array151:                            # @_array151

	.align	8                       # @_string152
L__string152:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array151
	.zero	4

	.globl	__Pshadow_Pstandard_Clong_Mclass # @_Pshadow_Pstandard_Clong_Mclass
	.align	16
__Pshadow_Pstandard_Clong_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string062
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	8                       # 0x8
	.long	8                       # 0x8
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pstandard_Clong_Madd_R_Pshadow_Pstandard_Clong
	.long	__Pshadow_Pstandard_Clong_Mcompare_R_Pshadow_Pstandard_Clong
	.long	__Pshadow_Pstandard_Clong_Mdivide_R_Pshadow_Pstandard_Clong
	.long	__Pshadow_Pstandard_Clong_Mmodulus_R_Pshadow_Pstandard_Clong
	.long	__Pshadow_Pstandard_Clong_Mmultiply_R_Pshadow_Pstandard_Clong
	.long	__Pshadow_Pstandard_Clong_Msubtract_R_Pshadow_Pstandard_Clong

L__array061:                            # @_array061
	.ascii	 "long"

	.align	8                       # @_string062
L__string062:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array061
	.long	4                       # 0x4

	.globl	__Pshadow_Pstandard_CObject_Mclass # @_Pshadow_Pstandard_CObject_Mclass
	.align	16
__Pshadow_Pstandard_CObject_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string0126
	.long	0
	.long	0+4
	.long	8                       # 0x8
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString

	.align	16                      # @_array0125
L__array0125:
	.ascii	 "shadow.standard@Object"

	.align	8                       # @_string0126
L__string0126:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array0125
	.long	22                      # 0x16

	.globl	__Pshadow_Pstandard_Cshort_Mclass # @_Pshadow_Pstandard_Cshort_Mclass
	.align	16
__Pshadow_Pstandard_Cshort_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string0144
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	2                       # 0x2
	.long	2                       # 0x2
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pstandard_Cshort_Madd_R_Pshadow_Pstandard_Cshort
	.long	__Pshadow_Pstandard_Cshort_Mcompare_R_Pshadow_Pstandard_Cshort
	.long	__Pshadow_Pstandard_Cshort_Mdivide_R_Pshadow_Pstandard_Cshort
	.long	__Pshadow_Pstandard_Cshort_Mmodulus_R_Pshadow_Pstandard_Cshort
	.long	__Pshadow_Pstandard_Cshort_Mmultiply_R_Pshadow_Pstandard_Cshort
	.long	__Pshadow_Pstandard_Cshort_Msubtract_R_Pshadow_Pstandard_Cshort

L__array0143:                           # @_array0143
	.ascii	 "short"

	.align	8                       # @_string0144
L__string0144:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array0143
	.long	5                       # 0x5

	.globl	__Pshadow_Pstandard_CString_Mclass # @_Pshadow_Pstandard_CString_Mclass
	.align	16
__Pshadow_Pstandard_CString_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string0148
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	0+12
	.long	8                       # 0x8
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CString_MtoString
	.long	__Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString
	.long	__Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString
	.long	__Pshadow_Pstandard_CString_MgetByte_R_Pshadow_Pstandard_Cint
	.long	__Pshadow_Pstandard_CString_Mlength

	.align	16                      # @_array0147
L__array0147:
	.ascii	 "shadow.standard@String"

	.align	8                       # @_string0148
L__string0148:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array0147
	.long	22                      # 0x16

	.globl	__Pshadow_Pstandard_Cubyte_Mclass # @_Pshadow_Pstandard_Cubyte_Mclass
	.align	16
__Pshadow_Pstandard_Cubyte_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string0164
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	1                       # 0x1
	.long	1                       # 0x1
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pstandard_Cubyte_Madd_R_Pshadow_Pstandard_Cubyte
	.long	__Pshadow_Pstandard_Cubyte_Mcompare_R_Pshadow_Pstandard_Cubyte
	.long	__Pshadow_Pstandard_Cubyte_Mdivide_R_Pshadow_Pstandard_Cubyte
	.long	__Pshadow_Pstandard_Cubyte_Mmodulus_R_Pshadow_Pstandard_Cubyte
	.long	__Pshadow_Pstandard_Cubyte_Mmultiply_R_Pshadow_Pstandard_Cubyte
	.long	__Pshadow_Pstandard_Cubyte_Msubtract_R_Pshadow_Pstandard_Cubyte

L__array0163:                           # @_array0163
	.ascii	 "ubyte"

	.align	8                       # @_string0164
L__string0164:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array0163
	.long	5                       # 0x5

	.globl	__Pshadow_Pstandard_Cuint_Mclass # @_Pshadow_Pstandard_Cuint_Mclass
	.align	16
__Pshadow_Pstandard_Cuint_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string0168
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	4                       # 0x4
	.long	4                       # 0x4
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pstandard_Cuint_Madd_R_Pshadow_Pstandard_Cuint
	.long	__Pshadow_Pstandard_Cuint_Mcompare_R_Pshadow_Pstandard_Cuint
	.long	__Pshadow_Pstandard_Cuint_Mdivide_R_Pshadow_Pstandard_Cuint
	.long	__Pshadow_Pstandard_Cuint_Mmax_R_Pshadow_Pstandard_Cuint
	.long	__Pshadow_Pstandard_Cuint_Mmin_R_Pshadow_Pstandard_Cuint
	.long	__Pshadow_Pstandard_Cuint_Mmodulus_R_Pshadow_Pstandard_Cuint
	.long	__Pshadow_Pstandard_Cuint_Mmultiply_R_Pshadow_Pstandard_Cuint
	.long	__Pshadow_Pstandard_Cuint_Msubtract_R_Pshadow_Pstandard_Cuint

L__array0167:                           # @_array0167
	.ascii	 "uint"

	.align	8                       # @_string0168
L__string0168:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array0167
	.long	4                       # 0x4

	.globl	__Pshadow_Pstandard_Culong_Mclass # @_Pshadow_Pstandard_Culong_Mclass
	.align	16
__Pshadow_Pstandard_Culong_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string0172
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	8                       # 0x8
	.long	8                       # 0x8
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pstandard_Culong_Madd_R_Pshadow_Pstandard_Culong
	.long	__Pshadow_Pstandard_Culong_Mcompare_R_Pshadow_Pstandard_Culong
	.long	__Pshadow_Pstandard_Culong_Mdivide_R_Pshadow_Pstandard_Culong
	.long	__Pshadow_Pstandard_Culong_Mmodulus_R_Pshadow_Pstandard_Culong
	.long	__Pshadow_Pstandard_Culong_Mmultiply_R_Pshadow_Pstandard_Culong
	.long	__Pshadow_Pstandard_Culong_Msubtract_R_Pshadow_Pstandard_Culong

L__array0171:                           # @_array0171
	.ascii	 "ulong"

	.align	8                       # @_string0172
L__string0172:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array0171
	.long	5                       # 0x5

	.globl	__Pshadow_Pstandard_Cushort_Mclass # @_Pshadow_Pstandard_Cushort_Mclass
	.align	16
__Pshadow_Pstandard_Cushort_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string0176
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	2                       # 0x2
	.long	2                       # 0x2
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pstandard_Cushort_Madd_R_Pshadow_Pstandard_Cushort
	.long	__Pshadow_Pstandard_Cushort_Mcompare_R_Pshadow_Pstandard_Cushort
	.long	__Pshadow_Pstandard_Cushort_Mdivide_R_Pshadow_Pstandard_Cushort
	.long	__Pshadow_Pstandard_Cushort_Mmodulus_R_Pshadow_Pstandard_Cushort
	.long	__Pshadow_Pstandard_Cushort_Mmultiply_R_Pshadow_Pstandard_Cushort
	.long	__Pshadow_Pstandard_Cushort_Msubtract_R_Pshadow_Pstandard_Cushort

L__array0175:                           # @_array0175
	.ascii	 "ushort"

	.align	8                       # @_string0176
L__string0176:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array0175
	.long	6                       # 0x6

	.globl	__Pshadow_Pio_CConsole_Mclass # @_Pshadow_Pio_CConsole_Mclass
	.align	16
__Pshadow_Pio_CConsole_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string0178
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	0+4
	.long	8                       # 0x8
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cboolean
	.long	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cint
	.long	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Ccode
	.long	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Clong
	.long	__Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString
	.long	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cboolean
	.long	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cint
	.long	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Ccode
	.long	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Clong
	.long	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString
	.long	__Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pio_CConsole_MprintLine

	.globl	__Pshadow_Pio_CConsole_Minstance # @_Pshadow_Pio_CConsole_Minstance
	.align	4
__Pshadow_Pio_CConsole_Minstance:
	.long	0

	.align	16                      # @_array0177
L__array0177:
	.ascii	 "shadow.io@Console"

	.align	8                       # @_string0178
L__string0178:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array0177
	.long	17                      # 0x11

L__array1179:                           # @_array1179
	.ascii	 "true"

	.align	8                       # @_string1180
L__string1180:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array1179
	.long	4                       # 0x4

L__array2:                              # @_array2
	.ascii	 "false"

	.align	8                       # @_string2
L__string2:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array2
	.long	5                       # 0x5

L_str.int:                              # @str.int
	.asciz	 "%i"

L_str.code:                             # @str.code
	.asciz	 "%lc"

L_str.long:                             # @str.long
	.asciz	 "%lli"

	.globl	__Pshadow_Ptest_CArrayTest_Mclass # @_Pshadow_Ptest_CArrayTest_Mclass
	.align	16
__Pshadow_Ptest_CArrayTest_Mclass:
	.long	__Pshadow_Pstandard_CClass_Mclass
	.zero	8
	.long	L__string0190
	.long	__Pshadow_Pstandard_CObject_Mclass
	.long	0+4
	.long	8                       # 0x8
	.long	__Pshadow_Pstandard_CObject_Mcopy
	.long	__Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_Mfreeze
	.long	__Pshadow_Pstandard_CObject_MgetClass
	.long	__Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject
	.long	__Pshadow_Pstandard_CObject_MtoString
	.long	__Pshadow_Ptest_CArrayTest_Mmain

	.align	16                      # @_array0189
L__array0189:
	.ascii	 "shadow.test@ArrayTest"

	.align	8                       # @_string0190
L__string0190:
	.long	__Pshadow_Pstandard_CString_Mclass
	.long	L__array0189
	.long	21                      # 0x15

	.weak	_stdin
	.weak	_stdout
	.weak	_stderr
	.weak	__imp___iob

