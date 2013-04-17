; shadow.utility@LinkedList<V>

%boolean = type i1
%byte = type i8
%ubyte = type i8
%short = type i16
%ushort = type i16
%int = type i32
%uint = type i32
%code = type i32
%long = type i64
%ulong = type i64
%float = type float
%double = type double

declare i32 @__shadow_personality_v0(...)
declare %"_Pshadow_Pstandard_CException"* @__shadow_catch(i8* nocapture) nounwind
declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone

%"_Pshadow_Putility_CLinkedList_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Putility_CLinkedList"* (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Putility_CLinkedList"*)*, %boolean (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList"*, %int)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList"*, %int)*, void (%"_Pshadow_Putility_CLinkedList"*, %int, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList"*)*, %boolean (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Putility_CLinkedList"*)* }
%"_Pshadow_Putility_CLinkedList" = type { %"_Pshadow_Putility_CLinkedList_Mclass"*, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Putility_CLinkedList_INode"*, %int, %int }
%"_Pshadow_Pstandard_CException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, void (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CException_Mclass"* }
%"_Pshadow_Pstandard_Cint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cint"* (%"_Pshadow_Pstandard_Cint"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cint"*)*, %uint (%"_Pshadow_Pstandard_Cint"*)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cint"*, %uint)* }
%"_Pshadow_Pstandard_Cint" = type { %"_Pshadow_Pstandard_Cint_Mclass"*, %int }
%"_Pshadow_Putility_CIllegalModificationException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Putility_CIllegalModificationException"* (%"_Pshadow_Putility_CIllegalModificationException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, void (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Putility_CIllegalModificationException" = type { %"_Pshadow_Putility_CIllegalModificationException_Mclass"* }
%"_Pshadow_Putility_CLinkedList_INode_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Putility_CLinkedList_INode"*)*, %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Putility_CLinkedList_INode"*)*, void (%"_Pshadow_Putility_CLinkedList_INode"*)*, %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*)*, %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*)*, void (%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList_INode"*)* }
%"_Pshadow_Putility_CLinkedList_INode" = type { %"_Pshadow_Putility_CLinkedList_INode_Mclass"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"* }
%"_Pshadow_Pstandard_Cushort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cushort"* (%"_Pshadow_Pstandard_Cushort"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %int (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)* }
%"_Pshadow_Pstandard_Cushort" = type { %"_Pshadow_Pstandard_Cushort_Mclass"*, %ushort }
%"_Pshadow_Pstandard_Ccode_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Ccode"* (%"_Pshadow_Pstandard_Ccode"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Ccode"*)*, %code (%"_Pshadow_Pstandard_Ccode"*)*, %code (%"_Pshadow_Pstandard_Ccode"*)* }
%"_Pshadow_Pstandard_Ccode" = type { %"_Pshadow_Pstandard_Ccode_Mclass"*, %code }
%"_Pshadow_Pstandard_Cdouble_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cdouble"* (%"_Pshadow_Pstandard_Cdouble"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %int (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)* }
%"_Pshadow_Pstandard_Cdouble" = type { %"_Pshadow_Pstandard_Cdouble_Mclass"*, %double }
%"_Pshadow_Pstandard_Clong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Clong"* (%"_Pshadow_Pstandard_Clong"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Clong"*)*, %ulong (%"_Pshadow_Pstandard_Clong"*)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %int (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %int (%"_Pshadow_Pstandard_Clong"*)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Clong"*, %ulong)* }
%"_Pshadow_Pstandard_Clong" = type { %"_Pshadow_Pstandard_Clong_Mclass"*, %long }
%"_Pshadow_Pstandard_Cfloat_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cfloat"* (%"_Pshadow_Pstandard_Cfloat"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %int (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)* }
%"_Pshadow_Pstandard_Cfloat" = type { %"_Pshadow_Pstandard_Cfloat_Mclass"*, %float }
%"_Pshadow_Pstandard_Cshort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cshort"* (%"_Pshadow_Pstandard_Cshort"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %int (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)* }
%"_Pshadow_Pstandard_Cshort" = type { %"_Pshadow_Pstandard_Cshort_Mclass"*, %short }
%"_Pshadow_Putility_CLinkedList_ILinkedListIterator_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Putility_CLinkedList_ILinkedListIterator"* (%"_Pshadow_Putility_CLinkedList_ILinkedListIterator"*, %"_Pshadow_Putility_CLinkedList"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Putility_CLinkedList_ILinkedListIterator"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList_ILinkedListIterator"*)* }
%"_Pshadow_Putility_CLinkedList_ILinkedListIterator" = type { %"_Pshadow_Putility_CLinkedList_ILinkedListIterator_Mclass"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList"*, %int }
%"_Pshadow_Pstandard_Cbyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cbyte"* (%"_Pshadow_Pstandard_Cbyte"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cbyte"*)*, %ubyte (%"_Pshadow_Pstandard_Cbyte"*)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %int (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cbyte"*, %ubyte)* }
%"_Pshadow_Pstandard_Cbyte" = type { %"_Pshadow_Pstandard_Cbyte_Mclass"*, %byte }
%"_Pshadow_Pstandard_CString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CString_Mclass"*, { %ubyte*, [1 x %int] }, %boolean }
%"_Pshadow_Pstandard_CClass_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass_Mclass"*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, %int, %int, %int }
%"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* (%"_Pshadow_Pstandard_CIndexOutOfBoundsException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, void (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* (%"_Pshadow_Pstandard_CIndexOutOfBoundsException"*, %int)* }
%"_Pshadow_Pstandard_CIndexOutOfBoundsException" = type { %"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass"* }
%"_Pshadow_Pstandard_CArray_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CArray_Mclass"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject"* }
%"_Pshadow_Pstandard_Cboolean_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cboolean"* (%"_Pshadow_Pstandard_Cboolean"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cboolean"*)* }
%"_Pshadow_Pstandard_Cboolean" = type { %"_Pshadow_Pstandard_Cboolean_Mclass"*, %boolean }
%"_P_CUnexpectedNullException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_P_CUnexpectedNullException"* (%"_P_CUnexpectedNullException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, void (%"_Pshadow_Pstandard_CException"*)* }
%"_P_CUnexpectedNullException" = type { %"_P_CUnexpectedNullException_Mclass"* }
%"_Pshadow_Pstandard_Culong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Culong"* (%"_Pshadow_Pstandard_Culong"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Culong"*)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %int (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Culong"*, %ulong)* }
%"_Pshadow_Pstandard_Culong" = type { %"_Pshadow_Pstandard_Culong_Mclass"*, %ulong }
%"_Pshadow_Pstandard_Cubyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cubyte"* (%"_Pshadow_Pstandard_Cubyte"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cubyte"*)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %int (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)* }
%"_Pshadow_Pstandard_Cubyte" = type { %"_Pshadow_Pstandard_Cubyte_Mclass"*, %ubyte }
%"_Pshadow_Pstandard_Cuint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cuint"* (%"_Pshadow_Pstandard_Cuint"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cuint"*)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %int (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %int (%"_Pshadow_Pstandard_Cuint"*)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cuint"*, %uint)* }
%"_Pshadow_Pstandard_Cuint" = type { %"_Pshadow_Pstandard_Cuint_Mclass"*, %uint }
%"_Pshadow_Pstandard_CObject_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CObject_Mclass"* }

@"_Pshadow_Putility_CLinkedList_Mclass" = constant %"_Pshadow_Putility_CLinkedList_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int 0, %int ptrtoint (%"_Pshadow_Putility_CLinkedList"* getelementptr (%"_Pshadow_Putility_CLinkedList"* null, i32 1) to i32), %int ptrtoint (%"_Pshadow_Putility_CLinkedList"** getelementptr (%"_Pshadow_Putility_CLinkedList"** null, i32 1) to i32) }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %"_Pshadow_Putility_CLinkedList"* (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CClass"*)* @"_Pshadow_Putility_CLinkedList_Mcreate", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MtoString", %boolean (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Putility_CLinkedList_Madd_CV", void (%"_Pshadow_Putility_CLinkedList"*)* @"_Pshadow_Putility_CLinkedList_Mclear", %boolean (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Putility_CLinkedList_Mcontains_CV", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList"*, %int)* @"_Pshadow_Putility_CLinkedList_Mdelete_Pshadow_Pstandard_Cint", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList"*, %int)* @"_Pshadow_Putility_CLinkedList_Mindex_Pshadow_Pstandard_Cint", void (%"_Pshadow_Putility_CLinkedList"*, %int, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Putility_CLinkedList_Mindex_Pshadow_Pstandard_Cint_CV", %int (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Putility_CLinkedList_MindexOf_CV", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList"*)* @"_Pshadow_Putility_CLinkedList_Miterator", %boolean (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Putility_CLinkedList_Mremove_CV", %int (%"_Pshadow_Putility_CLinkedList"*)* @"_Pshadow_Putility_CLinkedList_Msize" }
@"_Pshadow_Pstandard_CException_Mclass" = external constant %"_Pshadow_Pstandard_CException_Mclass"
@"_Pshadow_Pstandard_Cint_Mclass" = external constant %"_Pshadow_Pstandard_Cint_Mclass"
@"_Pshadow_Putility_CIllegalModificationException_Mclass" = external constant %"_Pshadow_Putility_CIllegalModificationException_Mclass"
@"_Pshadow_Putility_CLinkedList_INode_Mclass" = external constant %"_Pshadow_Putility_CLinkedList_INode_Mclass"
@"_Pshadow_Pstandard_Cushort_Mclass" = external constant %"_Pshadow_Pstandard_Cushort_Mclass"
@"_Pshadow_Pstandard_Ccode_Mclass" = external constant %"_Pshadow_Pstandard_Ccode_Mclass"
@"_Pshadow_Pstandard_Cdouble_Mclass" = external constant %"_Pshadow_Pstandard_Cdouble_Mclass"
@"_Pshadow_Pstandard_Clong_Mclass" = external constant %"_Pshadow_Pstandard_Clong_Mclass"
@"_Pshadow_Pstandard_Cfloat_Mclass" = external constant %"_Pshadow_Pstandard_Cfloat_Mclass"
@"_Pshadow_Pstandard_Cshort_Mclass" = external constant %"_Pshadow_Pstandard_Cshort_Mclass"
@"_Pshadow_Putility_CLinkedList_ILinkedListIterator_Mclass" = external constant %"_Pshadow_Putility_CLinkedList_ILinkedListIterator_Mclass"
@"_Pshadow_Pstandard_Cbyte_Mclass" = external constant %"_Pshadow_Pstandard_Cbyte_Mclass"
@"_Pshadow_Pstandard_CString_Mclass" = external constant %"_Pshadow_Pstandard_CString_Mclass"
@"_Pshadow_Pstandard_CClass_Mclass" = external constant %"_Pshadow_Pstandard_CClass_Mclass"
@"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass" = external constant %"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass"
@"_Pshadow_Pstandard_CArray_Mclass" = external constant %"_Pshadow_Pstandard_CArray_Mclass"
@"_Pshadow_Pstandard_Cboolean_Mclass" = external constant %"_Pshadow_Pstandard_Cboolean_Mclass"
@"_P_CUnexpectedNullException_Mclass" = external constant %"_P_CUnexpectedNullException_Mclass"
@"_Pshadow_Pstandard_Culong_Mclass" = external constant %"_Pshadow_Pstandard_Culong_Mclass"
@"_Pshadow_Pstandard_Cubyte_Mclass" = external constant %"_Pshadow_Pstandard_Cubyte_Mclass"
@"_Pshadow_Pstandard_Cuint_Mclass" = external constant %"_Pshadow_Pstandard_Cuint_Mclass"
@"_Pshadow_Pstandard_CObject_Mclass" = external constant %"_Pshadow_Pstandard_CObject_Mclass"

define void @"_Pshadow_Putility_CLinkedList_Mclear"(%"_Pshadow_Putility_CLinkedList"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList"*
    store %"_Pshadow_Putility_CLinkedList"* %0, %"_Pshadow_Putility_CLinkedList"** %this
    %2 = load %"_Pshadow_Putility_CLinkedList"** %this
    %3 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %2, i32 0, i32 2
    %4 = load %"_Pshadow_Putility_CLinkedList_INode"** %3
    %5 = getelementptr %"_Pshadow_Putility_CLinkedList_INode"* %4, i32 0, i32 0
    %6 = load %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %5
    %7 = getelementptr %"_Pshadow_Putility_CLinkedList_INode_Mclass"* %6, i32 0, i32 7
    %8 = load void (%"_Pshadow_Putility_CLinkedList_INode"*)** %7
    %9 = load %"_Pshadow_Putility_CLinkedList_INode"** %3
    call void %8(%"_Pshadow_Putility_CLinkedList_INode"* %9)
    ret void
}

define %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CLinkedList_Miterator"(%"_Pshadow_Putility_CLinkedList"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList"*
    store %"_Pshadow_Putility_CLinkedList"* %0, %"_Pshadow_Putility_CLinkedList"** %this
    %2 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Putility_CLinkedList_ILinkedListIterator_Mclass"* @"_Pshadow_Putility_CLinkedList_ILinkedListIterator_Mclass", i32 0, i32 0))
    %3 = bitcast %"_Pshadow_Pstandard_CObject"* %2 to %"_Pshadow_Putility_CLinkedList_ILinkedListIterator"*
    %4 = load %"_Pshadow_Putility_CLinkedList"** %this
    %5 = call %"_Pshadow_Putility_CLinkedList_ILinkedListIterator"* @"_Pshadow_Putility_CLinkedList_ILinkedListIterator_Mcreate"(%"_Pshadow_Putility_CLinkedList_ILinkedListIterator"* %3, %"_Pshadow_Putility_CLinkedList"* %4)
    %6 = bitcast %"_Pshadow_Putility_CLinkedList_ILinkedListIterator"* %3 to %"_Pshadow_Pstandard_CObject"*
    ret %"_Pshadow_Pstandard_CObject"* %6
}

define %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CLinkedList_Mindex_Pshadow_Pstandard_Cint"(%"_Pshadow_Putility_CLinkedList"*, %int) {
    %this = alloca %"_Pshadow_Putility_CLinkedList"*
    %index = alloca %int
    store %"_Pshadow_Putility_CLinkedList"* %0, %"_Pshadow_Putility_CLinkedList"** %this
    store %int %1, %int* %index
    %3 = load %"_Pshadow_Putility_CLinkedList"** %this
    %4 = load %"_Pshadow_Putility_CLinkedList"** %this
    %5 = load %int* %index
    %6 = call %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_MfindIndex_Pshadow_Pstandard_Cint"(%"_Pshadow_Putility_CLinkedList"* %4, %int %5)
    %7 = getelementptr %"_Pshadow_Putility_CLinkedList_INode"* %6, i32 0, i32 0
    %8 = load %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %7
    %9 = getelementptr %"_Pshadow_Putility_CLinkedList_INode_Mclass"* %8, i32 0, i32 13
    %10 = load %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList_INode"*)** %9
    %11 = call %"_Pshadow_Pstandard_CObject"* %10(%"_Pshadow_Putility_CLinkedList_INode"* %6)
    ret %"_Pshadow_Pstandard_CObject"* %11
}

define void @"_Pshadow_Putility_CLinkedList_Mindex_Pshadow_Pstandard_Cint_CV"(%"_Pshadow_Putility_CLinkedList"*, %int, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList"*
    %index = alloca %int
    %value = alloca %"_Pshadow_Pstandard_CObject"*
    store %"_Pshadow_Putility_CLinkedList"* %0, %"_Pshadow_Putility_CLinkedList"** %this
    store %int %1, %int* %index
    store %"_Pshadow_Pstandard_CObject"* %2, %"_Pshadow_Pstandard_CObject"** %value
    %4 = load %"_Pshadow_Putility_CLinkedList"** %this
    %5 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %4, i32 0, i32 4
    %6 = load %int* %index
    %7 = load %int* %5
    %8 = icmp eq %int %6, %7
    br %boolean %8, label %_label0, label %_label1
_label0:
    %9 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Putility_CLinkedList_INode_Mclass"* @"_Pshadow_Putility_CLinkedList_INode_Mclass", i32 0, i32 0))
    %10 = bitcast %"_Pshadow_Pstandard_CObject"* %9 to %"_Pshadow_Putility_CLinkedList_INode"*
    %11 = load %"_Pshadow_Putility_CLinkedList"** %this
    %12 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %11, i32 0, i32 2
    %13 = load %"_Pshadow_Putility_CLinkedList"** %this
    %14 = load %"_Pshadow_Pstandard_CObject"** %value
    %15 = load %"_Pshadow_Putility_CLinkedList_INode"** %12
    %16 = call %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_INode_Mcreate_CV_Pshadow_Putility_CLinkedList_INode"(%"_Pshadow_Putility_CLinkedList_INode"* %10, %"_Pshadow_Putility_CLinkedList"* %13, %"_Pshadow_Pstandard_CObject"* %14, %"_Pshadow_Putility_CLinkedList_INode"* %15)
    br label %_label2
_label1:
    %17 = load %"_Pshadow_Putility_CLinkedList"** %this
    %18 = load %"_Pshadow_Putility_CLinkedList"** %this
    %19 = load %int* %index
    %20 = call %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_MfindIndex_Pshadow_Pstandard_Cint"(%"_Pshadow_Putility_CLinkedList"* %18, %int %19)
    %21 = load %"_Pshadow_Pstandard_CObject"** %value
    %22 = getelementptr %"_Pshadow_Putility_CLinkedList_INode"* %20, i32 0, i32 0
    %23 = load %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %22
    %24 = getelementptr %"_Pshadow_Putility_CLinkedList_INode_Mclass"* %23, i32 0, i32 12
    %25 = load void (%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Pstandard_CObject"*)** %24
    call void %25(%"_Pshadow_Putility_CLinkedList_INode"* %20, %"_Pshadow_Pstandard_CObject"* %21)
    br label %_label2
_label2:
    ret void
}

define %boolean @"_Pshadow_Putility_CLinkedList_Mremove_CV"(%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList"*
    %value = alloca %"_Pshadow_Pstandard_CObject"*
    %node = alloca %"_Pshadow_Putility_CLinkedList_INode"*
    %_temp = alloca %"_Pshadow_Pstandard_CObject"*
    store %"_Pshadow_Putility_CLinkedList"* %0, %"_Pshadow_Putility_CLinkedList"** %this
    store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %value
    store %"_Pshadow_Putility_CLinkedList_INode"* null, %"_Pshadow_Putility_CLinkedList_INode"** %node
    %3 = load %"_Pshadow_Putility_CLinkedList"** %this
    %4 = load %"_Pshadow_Putility_CLinkedList"** %this
    %5 = load %"_Pshadow_Pstandard_CObject"** %value
    %6 = call { %int, %"_Pshadow_Putility_CLinkedList_INode"* } @"_Pshadow_Putility_CLinkedList_MfindNode_CV"(%"_Pshadow_Putility_CLinkedList"* %4, %"_Pshadow_Pstandard_CObject"* %5)
    %7 = extractvalue { %int, %"_Pshadow_Putility_CLinkedList_INode"* } %6, 0
    %8 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0))
    %9 = bitcast %"_Pshadow_Pstandard_CObject"* %8 to %"_Pshadow_Pstandard_Cint"*
    %10 = getelementptr inbounds %"_Pshadow_Pstandard_Cint"* %9, i32 0, i32 0
    store %"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", %"_Pshadow_Pstandard_Cint_Mclass"** %10
    %11 = getelementptr inbounds %"_Pshadow_Pstandard_Cint"* %9, i32 0, i32 1
    store %int %7, %int* %11
    %12 = insertvalue { %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Putility_CLinkedList_INode"* } undef, %"_Pshadow_Pstandard_CObject"* %8, 0
    %13 = extractvalue { %int, %"_Pshadow_Putility_CLinkedList_INode"* } %6, 1
    %14 = insertvalue { %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Putility_CLinkedList_INode"* } %12, %"_Pshadow_Putility_CLinkedList_INode"* %13, 1
    %15 = extractvalue { %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Putility_CLinkedList_INode"* } %14, 0
    store %"_Pshadow_Pstandard_CObject"* %15, %"_Pshadow_Pstandard_CObject"** %_temp
    %16 = extractvalue { %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Putility_CLinkedList_INode"* } %14, 1
    store %"_Pshadow_Putility_CLinkedList_INode"* %16, %"_Pshadow_Putility_CLinkedList_INode"** %node
    %17 = load %"_Pshadow_Putility_CLinkedList_INode"** %node
    %18 = bitcast %"_Pshadow_Putility_CLinkedList_INode"* %17 to %"_Pshadow_Pstandard_CObject"*
    %19 = icmp eq %"_Pshadow_Pstandard_CObject"* %18, null
    br %boolean %19, label %_label4, label %_label5
_label5:
    %20 = load %"_Pshadow_Putility_CLinkedList_INode"** %node
    %21 = getelementptr %"_Pshadow_Putility_CLinkedList_INode"* %20, i32 0, i32 0
    %22 = load %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %21
    %23 = getelementptr %"_Pshadow_Putility_CLinkedList_INode_Mclass"* %22, i32 0, i32 9
    %24 = load void (%"_Pshadow_Putility_CLinkedList_INode"*)** %23
    %25 = load %"_Pshadow_Putility_CLinkedList_INode"** %node
    call void %24(%"_Pshadow_Putility_CLinkedList_INode"* %25)
    ret %boolean true
    br label %_label3
_label4:
    ret %boolean false
    br label %_label3
_label3:
    ret %boolean false
}

define { %int, %"_Pshadow_Putility_CLinkedList_INode"* } @"_Pshadow_Putility_CLinkedList_MfindNode_CV"(%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList"*
    %value = alloca %"_Pshadow_Pstandard_CObject"*
    %index = alloca %int
    %current = alloca %"_Pshadow_Putility_CLinkedList_INode"*
    %_temp = alloca %boolean
    store %"_Pshadow_Putility_CLinkedList"* %0, %"_Pshadow_Putility_CLinkedList"** %this
    store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %value
    store %int 0, %int* %index
    %3 = load %"_Pshadow_Putility_CLinkedList"** %this
    %4 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %3, i32 0, i32 2
    %5 = load %"_Pshadow_Putility_CLinkedList_INode"** %4
    store %"_Pshadow_Putility_CLinkedList_INode"* %5, %"_Pshadow_Putility_CLinkedList_INode"** %current
    br label %_label7
_label6:
    %6 = load %"_Pshadow_Putility_CLinkedList_INode"** %current
    %7 = getelementptr %"_Pshadow_Putility_CLinkedList_INode"* %6, i32 0, i32 0
    %8 = load %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %7
    %9 = getelementptr %"_Pshadow_Putility_CLinkedList_INode_Mclass"* %8, i32 0, i32 10
    %10 = load %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*)** %9
    %11 = call %"_Pshadow_Putility_CLinkedList_INode"* %10(%"_Pshadow_Putility_CLinkedList_INode"* %6)
    store %"_Pshadow_Putility_CLinkedList_INode"* %11, %"_Pshadow_Putility_CLinkedList_INode"** %current
    %12 = load %"_Pshadow_Putility_CLinkedList"** %this
    %13 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %12, i32 0, i32 2
    %14 = load %"_Pshadow_Putility_CLinkedList_INode"** %current
    %15 = load %"_Pshadow_Putility_CLinkedList_INode"** %13
    %16 = icmp eq %"_Pshadow_Putility_CLinkedList_INode"* %14, %15
    br %boolean %16, label %_label9, label %_label10
_label9:
    %17 = sub %int 0, 1
    %18 = insertvalue { %int, %"_Pshadow_Putility_CLinkedList_INode"* } undef, %int %17, 0
    %19 = insertvalue { %int, %"_Pshadow_Putility_CLinkedList_INode"* } %18, %"_Pshadow_Putility_CLinkedList_INode"* null, 1
    ret { %int, %"_Pshadow_Putility_CLinkedList_INode"* } %19
    br label %_label11
_label10:
    br label %_label11
_label11:
    %21 = load %"_Pshadow_Putility_CLinkedList_INode"** %current
    %22 = getelementptr %"_Pshadow_Putility_CLinkedList_INode"* %21, i32 0, i32 0
    %23 = load %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %22
    %24 = getelementptr %"_Pshadow_Putility_CLinkedList_INode_Mclass"* %23, i32 0, i32 13
    %25 = load %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList_INode"*)** %24
    %26 = call %"_Pshadow_Pstandard_CObject"* %25(%"_Pshadow_Putility_CLinkedList_INode"* %21)
    %27 = bitcast %"_Pshadow_Pstandard_CObject"* %26 to %"_Pshadow_Pstandard_CObject"*
    %28 = icmp eq %"_Pshadow_Pstandard_CObject"* %27, null
    br %boolean %28, label %_label15, label %_label16
_label15:
    %29 = load %"_Pshadow_Pstandard_CObject"** %value
    %30 = bitcast %"_Pshadow_Pstandard_CObject"* %29 to %"_Pshadow_Pstandard_CObject"*
    %31 = icmp eq %"_Pshadow_Pstandard_CObject"* %30, null
    store %boolean %31, %boolean* %_temp
    br label %_label17
_label16:
    %32 = getelementptr %"_Pshadow_Putility_CLinkedList_INode"* %21, i32 0, i32 0
    %33 = load %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %32
    %34 = getelementptr %"_Pshadow_Putility_CLinkedList_INode_Mclass"* %33, i32 0, i32 13
    %35 = load %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList_INode"*)** %34
    %36 = call %"_Pshadow_Pstandard_CObject"* %35(%"_Pshadow_Putility_CLinkedList_INode"* %21)
    %37 = bitcast %"_Pshadow_Pstandard_CObject"* %36 to %"_Pshadow_Pstandard_CObject"*
    %38 = getelementptr %"_Pshadow_Pstandard_CObject"* %37, i32 0, i32 0
    %39 = load %"_Pshadow_Pstandard_CObject_Mclass"** %38
    %40 = getelementptr %"_Pshadow_Pstandard_CObject_Mclass"* %39, i32 0, i32 3
    %41 = load %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)** %40
    %42 = getelementptr %"_Pshadow_Putility_CLinkedList_INode"* %21, i32 0, i32 0
    %43 = load %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %42
    %44 = getelementptr %"_Pshadow_Putility_CLinkedList_INode_Mclass"* %43, i32 0, i32 13
    %45 = load %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList_INode"*)** %44
    %46 = call %"_Pshadow_Pstandard_CObject"* %45(%"_Pshadow_Putility_CLinkedList_INode"* %21)
    %47 = bitcast %"_Pshadow_Pstandard_CObject"* %46 to %"_Pshadow_Pstandard_CObject"*
    %48 = load %"_Pshadow_Pstandard_CObject"** %value
    %49 = bitcast %"_Pshadow_Pstandard_CObject"* %48 to %"_Pshadow_Pstandard_CObject"*
    %50 = call %boolean %41(%"_Pshadow_Pstandard_CObject"* %47, %"_Pshadow_Pstandard_CObject"* %49)
    store %boolean %50, %boolean* %_temp
    br label %_label17
_label17:
    %51 = load %boolean* %_temp
    br %boolean %51, label %_label12, label %_label13
_label12:
    %52 = load %int* %index
    %53 = load %"_Pshadow_Putility_CLinkedList_INode"** %current
    %54 = insertvalue { %int, %"_Pshadow_Putility_CLinkedList_INode"* } undef, %int %52, 0
    %55 = insertvalue { %int, %"_Pshadow_Putility_CLinkedList_INode"* } %54, %"_Pshadow_Putility_CLinkedList_INode"* %53, 1
    ret { %int, %"_Pshadow_Putility_CLinkedList_INode"* } %55
    br label %_label14
_label13:
    br label %_label14
_label14:
    %57 = load %int* %index
    %58 = add %int %57, 1
    store %int %58, %int* %index
    br label %_label7
_label7:
    br %boolean true, label %_label6, label %_label8
_label8:
    %59 = insertvalue { %int, %"_Pshadow_Putility_CLinkedList_INode"* } undef, %int 0, 0
    %60 = insertvalue { %int, %"_Pshadow_Putility_CLinkedList_INode"* } %59, %"_Pshadow_Putility_CLinkedList_INode"* null, 1
    ret { %int, %"_Pshadow_Putility_CLinkedList_INode"* } %60
}

define %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CLinkedList_Mdelete_Pshadow_Pstandard_Cint"(%"_Pshadow_Putility_CLinkedList"*, %int) {
    %this = alloca %"_Pshadow_Putility_CLinkedList"*
    %index = alloca %int
    %node = alloca %"_Pshadow_Putility_CLinkedList_INode"*
    %value = alloca %"_Pshadow_Pstandard_CObject"*
    store %"_Pshadow_Putility_CLinkedList"* %0, %"_Pshadow_Putility_CLinkedList"** %this
    store %int %1, %int* %index
    %3 = load %"_Pshadow_Putility_CLinkedList"** %this
    %4 = load %"_Pshadow_Putility_CLinkedList"** %this
    %5 = load %int* %index
    %6 = call %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_MfindIndex_Pshadow_Pstandard_Cint"(%"_Pshadow_Putility_CLinkedList"* %4, %int %5)
    store %"_Pshadow_Putility_CLinkedList_INode"* %6, %"_Pshadow_Putility_CLinkedList_INode"** %node
    %7 = load %"_Pshadow_Putility_CLinkedList_INode"** %node
    %8 = getelementptr %"_Pshadow_Putility_CLinkedList_INode"* %7, i32 0, i32 0
    %9 = load %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %8
    %10 = getelementptr %"_Pshadow_Putility_CLinkedList_INode_Mclass"* %9, i32 0, i32 13
    %11 = load %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList_INode"*)** %10
    %12 = call %"_Pshadow_Pstandard_CObject"* %11(%"_Pshadow_Putility_CLinkedList_INode"* %7)
    %13 = bitcast %"_Pshadow_Pstandard_CObject"* %12 to %"_Pshadow_Pstandard_CObject"*
    %14 = icmp eq %"_Pshadow_Pstandard_CObject"* %13, null
    br %boolean %14, label %_label19, label %_label20
_label20:
    %15 = getelementptr %"_Pshadow_Putility_CLinkedList_INode"* %7, i32 0, i32 0
    %16 = load %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Putility_CLinkedList_INode_Mclass"* %16, i32 0, i32 13
    %18 = load %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList_INode"*)** %17
    %19 = call %"_Pshadow_Pstandard_CObject"* %18(%"_Pshadow_Putility_CLinkedList_INode"* %7)
    store %"_Pshadow_Pstandard_CObject"* %19, %"_Pshadow_Pstandard_CObject"** %value
    %20 = load %"_Pshadow_Putility_CLinkedList_INode"** %node
    %21 = getelementptr %"_Pshadow_Putility_CLinkedList_INode"* %20, i32 0, i32 0
    %22 = load %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %21
    %23 = getelementptr %"_Pshadow_Putility_CLinkedList_INode_Mclass"* %22, i32 0, i32 9
    %24 = load void (%"_Pshadow_Putility_CLinkedList_INode"*)** %23
    %25 = load %"_Pshadow_Putility_CLinkedList_INode"** %node
    call void %24(%"_Pshadow_Putility_CLinkedList_INode"* %25)
    %26 = load %"_Pshadow_Pstandard_CObject"** %value
    ret %"_Pshadow_Pstandard_CObject"* %26
    br label %_label18
_label19:
    %28 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_P_CUnexpectedNullException_Mclass"* @"_P_CUnexpectedNullException_Mclass", i32 0, i32 0))
    %29 = bitcast %"_Pshadow_Pstandard_CObject"* %28 to %"_P_CUnexpectedNullException"*
    %30 = call %"_P_CUnexpectedNullException"* @"_P_CUnexpectedNullException_Mcreate"(%"_P_CUnexpectedNullException"* %29)
    %31 = bitcast %"_P_CUnexpectedNullException"* %29 to %"_Pshadow_Pstandard_CException"*
    %32 = getelementptr %"_Pshadow_Pstandard_CException"* %31, i32 0, i32 0
    %33 = load %"_Pshadow_Pstandard_CException_Mclass"** %32
    %34 = getelementptr %"_Pshadow_Pstandard_CException_Mclass"* %33, i32 0, i32 9
    %35 = load void (%"_Pshadow_Pstandard_CException"*)** %34
    %36 = bitcast %"_P_CUnexpectedNullException"* %29 to %"_Pshadow_Pstandard_CException"*
    call void %35(%"_Pshadow_Pstandard_CException"* %36)
    br label %_label18
_label18:
    ret %"_Pshadow_Pstandard_CObject"* null
}

define %boolean @"_Pshadow_Putility_CLinkedList_Mcontains_CV"(%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList"*
    %value = alloca %"_Pshadow_Pstandard_CObject"*
    store %"_Pshadow_Putility_CLinkedList"* %0, %"_Pshadow_Putility_CLinkedList"** %this
    store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %value
    %3 = load %"_Pshadow_Putility_CLinkedList"** %this
    %4 = getelementptr %"_Pshadow_Putility_CLinkedList"* %3, i32 0, i32 0
    %5 = load %"_Pshadow_Putility_CLinkedList_Mclass"** %4
    %6 = getelementptr %"_Pshadow_Putility_CLinkedList_Mclass"* %5, i32 0, i32 13
    %7 = load %int (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)** %6
    %8 = load %"_Pshadow_Putility_CLinkedList"** %this
    %9 = load %"_Pshadow_Pstandard_CObject"** %value
    %10 = call %int %7(%"_Pshadow_Putility_CLinkedList"* %8, %"_Pshadow_Pstandard_CObject"* %9)
    %11 = sub %int 0, 1
    %12 = icmp eq %int %10, %11
    %13 = xor %boolean %12, true
    ret %boolean %13
}

define %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_MfindIndex_Pshadow_Pstandard_Cint"(%"_Pshadow_Putility_CLinkedList"*, %int) {
    %this = alloca %"_Pshadow_Putility_CLinkedList"*
    %index = alloca %int
    %current = alloca %"_Pshadow_Putility_CLinkedList_INode"*
    store %"_Pshadow_Putility_CLinkedList"* %0, %"_Pshadow_Putility_CLinkedList"** %this
    store %int %1, %int* %index
    %3 = load %int* %index
    %4 = bitcast %int %3 to %uint
    %5 = load %"_Pshadow_Putility_CLinkedList"** %this
    %6 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %5, i32 0, i32 4
    %7 = load %int* %6
    %8 = bitcast %int %7 to %uint
    %9 = icmp uge %uint %4, %8
    br %boolean %9, label %_label21, label %_label22
_label21:
    %10 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass", i32 0, i32 0))
    %11 = bitcast %"_Pshadow_Pstandard_CObject"* %10 to %"_Pshadow_Pstandard_CIndexOutOfBoundsException"*
    %12 = call %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CIndexOutOfBoundsException"* %11)
    %13 = bitcast %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* %11 to %"_Pshadow_Pstandard_CException"*
    %14 = getelementptr %"_Pshadow_Pstandard_CException"* %13, i32 0, i32 0
    %15 = load %"_Pshadow_Pstandard_CException_Mclass"** %14
    %16 = getelementptr %"_Pshadow_Pstandard_CException_Mclass"* %15, i32 0, i32 9
    %17 = load void (%"_Pshadow_Pstandard_CException"*)** %16
    %18 = bitcast %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* %11 to %"_Pshadow_Pstandard_CException"*
    call void %17(%"_Pshadow_Pstandard_CException"* %18)
    br label %_label23
_label22:
    br label %_label23
_label23:
    %19 = load %"_Pshadow_Putility_CLinkedList"** %this
    %20 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %19, i32 0, i32 2
    %21 = load %"_Pshadow_Putility_CLinkedList_INode"** %20
    store %"_Pshadow_Putility_CLinkedList_INode"* %21, %"_Pshadow_Putility_CLinkedList_INode"** %current
    %22 = load %"_Pshadow_Putility_CLinkedList"** %this
    %23 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %22, i32 0, i32 4
    %24 = load %int* %23
    %25 = sdiv %int %24, 2
    %26 = load %int* %index
    %27 = icmp slt %int %26, %25
    br %boolean %27, label %_label24, label %_label25
_label24:
    br label %_label28
_label27:
    %28 = load %"_Pshadow_Putility_CLinkedList_INode"** %current
    %29 = getelementptr %"_Pshadow_Putility_CLinkedList_INode"* %28, i32 0, i32 0
    %30 = load %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %29
    %31 = getelementptr %"_Pshadow_Putility_CLinkedList_INode_Mclass"* %30, i32 0, i32 10
    %32 = load %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*)** %31
    %33 = call %"_Pshadow_Putility_CLinkedList_INode"* %32(%"_Pshadow_Putility_CLinkedList_INode"* %28)
    store %"_Pshadow_Putility_CLinkedList_INode"* %33, %"_Pshadow_Putility_CLinkedList_INode"** %current
    %34 = load %int* %index
    %35 = sub %int %34, 1
    store %int %35, %int* %index
    br label %_label28
_label28:
    %36 = load %int* %index
    %37 = icmp eq %int %36, 0
    %38 = xor %boolean %37, true
    br %boolean %38, label %_label27, label %_label29
_label29:
    br label %_label26
_label25:
    br label %_label31
_label30:
    %39 = load %"_Pshadow_Putility_CLinkedList_INode"** %current
    %40 = getelementptr %"_Pshadow_Putility_CLinkedList_INode"* %39, i32 0, i32 0
    %41 = load %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %40
    %42 = getelementptr %"_Pshadow_Putility_CLinkedList_INode_Mclass"* %41, i32 0, i32 11
    %43 = load %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*)** %42
    %44 = call %"_Pshadow_Putility_CLinkedList_INode"* %43(%"_Pshadow_Putility_CLinkedList_INode"* %39)
    store %"_Pshadow_Putility_CLinkedList_INode"* %44, %"_Pshadow_Putility_CLinkedList_INode"** %current
    %45 = load %int* %index
    %46 = add %int %45, 1
    store %int %46, %int* %index
    br label %_label31
_label31:
    %47 = load %"_Pshadow_Putility_CLinkedList"** %this
    %48 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %47, i32 0, i32 4
    %49 = load %int* %index
    %50 = load %int* %48
    %51 = icmp eq %int %49, %50
    %52 = xor %boolean %51, true
    br %boolean %52, label %_label30, label %_label32
_label32:
    br label %_label26
_label26:
    %53 = load %"_Pshadow_Putility_CLinkedList_INode"** %current
    ret %"_Pshadow_Putility_CLinkedList_INode"* %53
}

define %int @"_Pshadow_Putility_CLinkedList_MindexOf_CV"(%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList"*
    %value = alloca %"_Pshadow_Pstandard_CObject"*
    %index = alloca %int
    %_temp = alloca %"_Pshadow_Pstandard_CObject"*
    store %"_Pshadow_Putility_CLinkedList"* %0, %"_Pshadow_Putility_CLinkedList"** %this
    store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %value
    store %int 0, %int* %index
    %3 = load %"_Pshadow_Putility_CLinkedList"** %this
    %4 = load %"_Pshadow_Putility_CLinkedList"** %this
    %5 = load %"_Pshadow_Pstandard_CObject"** %value
    %6 = call { %int, %"_Pshadow_Putility_CLinkedList_INode"* } @"_Pshadow_Putility_CLinkedList_MfindNode_CV"(%"_Pshadow_Putility_CLinkedList"* %4, %"_Pshadow_Pstandard_CObject"* %5)
    %7 = extractvalue { %int, %"_Pshadow_Putility_CLinkedList_INode"* } %6, 0
    %8 = insertvalue { %int, %"_Pshadow_Pstandard_CObject"* } undef, %int %7, 0
    %9 = extractvalue { %int, %"_Pshadow_Putility_CLinkedList_INode"* } %6, 1
    %10 = bitcast %"_Pshadow_Putility_CLinkedList_INode"* %9 to %"_Pshadow_Pstandard_CObject"*
    %11 = insertvalue { %int, %"_Pshadow_Pstandard_CObject"* } %8, %"_Pshadow_Pstandard_CObject"* %10, 1
    %12 = extractvalue { %int, %"_Pshadow_Pstandard_CObject"* } %11, 0
    store %int %12, %int* %index
    %13 = extractvalue { %int, %"_Pshadow_Pstandard_CObject"* } %11, 1
    store %"_Pshadow_Pstandard_CObject"* %13, %"_Pshadow_Pstandard_CObject"** %_temp
    %14 = load %int* %index
    ret %int %14
}

define %boolean @"_Pshadow_Putility_CLinkedList_Madd_CV"(%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList"*
    %value = alloca %"_Pshadow_Pstandard_CObject"*
    store %"_Pshadow_Putility_CLinkedList"* %0, %"_Pshadow_Putility_CLinkedList"** %this
    store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %value
    %3 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Putility_CLinkedList_INode_Mclass"* @"_Pshadow_Putility_CLinkedList_INode_Mclass", i32 0, i32 0))
    %4 = bitcast %"_Pshadow_Pstandard_CObject"* %3 to %"_Pshadow_Putility_CLinkedList_INode"*
    %5 = load %"_Pshadow_Putility_CLinkedList"** %this
    %6 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %5, i32 0, i32 2
    %7 = load %"_Pshadow_Putility_CLinkedList"** %this
    %8 = load %"_Pshadow_Pstandard_CObject"** %value
    %9 = load %"_Pshadow_Putility_CLinkedList_INode"** %6
    %10 = call %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_INode_Mcreate_CV_Pshadow_Putility_CLinkedList_INode"(%"_Pshadow_Putility_CLinkedList_INode"* %4, %"_Pshadow_Putility_CLinkedList"* %7, %"_Pshadow_Pstandard_CObject"* %8, %"_Pshadow_Putility_CLinkedList_INode"* %9)
    ret %boolean true
}

define %"_Pshadow_Putility_CLinkedList"* @"_Pshadow_Putility_CLinkedList_Mcreate"(%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CClass"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList"*
    %V = alloca %"_Pshadow_Pstandard_CClass"*
    store %"_Pshadow_Putility_CLinkedList"* %0, %"_Pshadow_Putility_CLinkedList"** %this
    store %"_Pshadow_Pstandard_CClass"* %1, %"_Pshadow_Pstandard_CClass"** %V
    %3 = load %"_Pshadow_Putility_CLinkedList"** %this
    %4 = bitcast %"_Pshadow_Putility_CLinkedList"* %3 to %"_Pshadow_Pstandard_CObject"*
    %5 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %4)
    %6 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %0, i32 0, i32 0
    store %"_Pshadow_Putility_CLinkedList_Mclass"* @"_Pshadow_Putility_CLinkedList_Mclass", %"_Pshadow_Putility_CLinkedList_Mclass"** %6
    %7 = load %"_Pshadow_Putility_CLinkedList"** %this
    %8 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %7, i32 0, i32 1
    %9 = load %"_Pshadow_Pstandard_CClass"** %V
    store %"_Pshadow_Pstandard_CClass"* %9, %"_Pshadow_Pstandard_CClass"** %8
    %10 = load %"_Pshadow_Putility_CLinkedList"** %this
    %11 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %10, i32 0, i32 3
    store %int 0, %int* %11
    %12 = load %"_Pshadow_Putility_CLinkedList"** %this
    %13 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %12, i32 0, i32 2
    %14 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Putility_CLinkedList_INode_Mclass"* @"_Pshadow_Putility_CLinkedList_INode_Mclass", i32 0, i32 0))
    %15 = bitcast %"_Pshadow_Pstandard_CObject"* %14 to %"_Pshadow_Putility_CLinkedList_INode"*
    %16 = load %"_Pshadow_Putility_CLinkedList"** %this
    %17 = call %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_INode_Mcreate"(%"_Pshadow_Putility_CLinkedList_INode"* %15, %"_Pshadow_Putility_CLinkedList"* %16)
    store %"_Pshadow_Putility_CLinkedList_INode"* %15, %"_Pshadow_Putility_CLinkedList_INode"** %13
    %18 = load %"_Pshadow_Putility_CLinkedList"** %this
    %19 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %18, i32 0, i32 4
    store %int 0, %int* %19
    ret %"_Pshadow_Putility_CLinkedList"* %0
}

define %int @"_Pshadow_Putility_CLinkedList_Msize"(%"_Pshadow_Putility_CLinkedList"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList"*
    store %"_Pshadow_Putility_CLinkedList"* %0, %"_Pshadow_Putility_CLinkedList"** %this
    %2 = load %"_Pshadow_Putility_CLinkedList"** %this
    %3 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %2, i32 0, i32 4
    %4 = load %int* %3
    ret %int %4
}

declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CException_Mmessage"(%"_Pshadow_Pstandard_CException"*)
declare void @"_Pshadow_Pstandard_CException_Mthrow__"(%"_Pshadow_Pstandard_CException"*)
declare %"_Pshadow_Pstandard_CException"* @"_Pshadow_Pstandard_CException_Mcreate"(%"_Pshadow_Pstandard_CException"*)
declare %"_Pshadow_Pstandard_CException"* @"_Pshadow_Pstandard_CException_Mcreate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)

declare %int @"_Pshadow_Pstandard_Cint_Mmin_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %int @"_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %uint @"_Pshadow_Pstandard_Cint_Mabs"(%"_Pshadow_Pstandard_Cint"*)
declare %int @"_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %int @"_Pshadow_Pstandard_Cint_Mhash"(%"_Pshadow_Pstandard_Cint"*)
declare %int @"_Pshadow_Pstandard_Cint_Mcompare_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %int @"_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %int @"_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cint_MtoString"(%"_Pshadow_Pstandard_Cint"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cint_MtoString_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cint"*, %uint)
declare %int @"_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %"_Pshadow_Pstandard_Cint"* @"_Pshadow_Pstandard_Cint_Mcreate"(%"_Pshadow_Pstandard_Cint"*)
declare %int @"_Pshadow_Pstandard_Cint_Mmodulus_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)

declare %"_Pshadow_Putility_CIllegalModificationException"* @"_Pshadow_Putility_CIllegalModificationException_Mcreate"(%"_Pshadow_Putility_CIllegalModificationException"*)

declare void @"_Pshadow_Putility_CLinkedList_INode_Mclear"(%"_Pshadow_Putility_CLinkedList_INode"*)
declare %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_INode_Mnext"(%"_Pshadow_Putility_CLinkedList_INode"*)
declare void @"_Pshadow_Putility_CLinkedList_INode_Mdelete"(%"_Pshadow_Putility_CLinkedList_INode"*)
declare void @"_Pshadow_Putility_CLinkedList_INode_Mvalue_CV"(%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CLinkedList_INode_Mvalue"(%"_Pshadow_Putility_CLinkedList_INode"*)
declare %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_INode_Mcreate"(%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList"*)
declare %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_INode_Mcreate_CV_Pshadow_Putility_CLinkedList_INode"(%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Putility_CLinkedList_INode"*)
declare %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_INode_Mprev"(%"_Pshadow_Putility_CLinkedList_INode"*)

declare %ushort @"_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort"(%"_Pshadow_Pstandard_Cushort"*, %ushort)
declare %int @"_Pshadow_Pstandard_Cushort_Mcompare_Pshadow_Pstandard_Cushort"(%"_Pshadow_Pstandard_Cushort"*, %ushort)
declare %ushort @"_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort"(%"_Pshadow_Pstandard_Cushort"*, %ushort)
declare %ushort @"_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort"(%"_Pshadow_Pstandard_Cushort"*, %ushort)
declare %ushort @"_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort"(%"_Pshadow_Pstandard_Cushort"*, %ushort)
declare %"_Pshadow_Pstandard_Cushort"* @"_Pshadow_Pstandard_Cushort_Mcreate"(%"_Pshadow_Pstandard_Cushort"*)
declare %ushort @"_Pshadow_Pstandard_Cushort_Mmodulus_Pshadow_Pstandard_Cushort"(%"_Pshadow_Pstandard_Cushort"*, %ushort)

declare %code @"_Pshadow_Pstandard_Ccode_MtoUpperCase"(%"_Pshadow_Pstandard_Ccode"*)
declare %code @"_Pshadow_Pstandard_Ccode_MtoLowerCase"(%"_Pshadow_Pstandard_Ccode"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Ccode_MtoString"(%"_Pshadow_Pstandard_Ccode"*)
declare %"_Pshadow_Pstandard_Ccode"* @"_Pshadow_Pstandard_Ccode_Mcreate"(%"_Pshadow_Pstandard_Ccode"*)

declare %double @"_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble"(%"_Pshadow_Pstandard_Cdouble"*, %double)
declare %int @"_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble"(%"_Pshadow_Pstandard_Cdouble"*, %double)
declare %double @"_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble"(%"_Pshadow_Pstandard_Cdouble"*, %double)
declare %double @"_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble"(%"_Pshadow_Pstandard_Cdouble"*, %double)
declare %double @"_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble"(%"_Pshadow_Pstandard_Cdouble"*, %double)
declare %"_Pshadow_Pstandard_Cdouble"* @"_Pshadow_Pstandard_Cdouble_Mcreate"(%"_Pshadow_Pstandard_Cdouble"*)
declare %double @"_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble"(%"_Pshadow_Pstandard_Cdouble"*, %double)

declare %long @"_Pshadow_Pstandard_Clong_Mmin_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %long @"_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %ulong @"_Pshadow_Pstandard_Clong_Mabs"(%"_Pshadow_Pstandard_Clong"*)
declare %long @"_Pshadow_Pstandard_Clong_Mmax_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %int @"_Pshadow_Pstandard_Clong_Mhash"(%"_Pshadow_Pstandard_Clong"*)
declare %int @"_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %long @"_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %long @"_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Clong_MtoString"(%"_Pshadow_Pstandard_Clong"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Clong_MtoString_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Clong"*, %ulong)
declare %long @"_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %"_Pshadow_Pstandard_Clong"* @"_Pshadow_Pstandard_Clong_Mcreate"(%"_Pshadow_Pstandard_Clong"*)
declare %long @"_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)

declare %float @"_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat"(%"_Pshadow_Pstandard_Cfloat"*, %float)
declare %int @"_Pshadow_Pstandard_Cfloat_Mcompare_Pshadow_Pstandard_Cfloat"(%"_Pshadow_Pstandard_Cfloat"*, %float)
declare %float @"_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat"(%"_Pshadow_Pstandard_Cfloat"*, %float)
declare %float @"_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat"(%"_Pshadow_Pstandard_Cfloat"*, %float)
declare %float @"_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat"(%"_Pshadow_Pstandard_Cfloat"*, %float)
declare %"_Pshadow_Pstandard_Cfloat"* @"_Pshadow_Pstandard_Cfloat_Mcreate"(%"_Pshadow_Pstandard_Cfloat"*)
declare %float @"_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat"(%"_Pshadow_Pstandard_Cfloat"*, %float)

declare %short @"_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort"(%"_Pshadow_Pstandard_Cshort"*, %short)
declare %int @"_Pshadow_Pstandard_Cshort_Mcompare_Pshadow_Pstandard_Cshort"(%"_Pshadow_Pstandard_Cshort"*, %short)
declare %short @"_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort"(%"_Pshadow_Pstandard_Cshort"*, %short)
declare %short @"_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort"(%"_Pshadow_Pstandard_Cshort"*, %short)
declare %short @"_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort"(%"_Pshadow_Pstandard_Cshort"*, %short)
declare %"_Pshadow_Pstandard_Cshort"* @"_Pshadow_Pstandard_Cshort_Mcreate"(%"_Pshadow_Pstandard_Cshort"*)
declare %short @"_Pshadow_Pstandard_Cshort_Mmodulus_Pshadow_Pstandard_Cshort"(%"_Pshadow_Pstandard_Cshort"*, %short)

declare %boolean @"_Pshadow_Putility_CLinkedList_ILinkedListIterator_MhasNext"(%"_Pshadow_Putility_CLinkedList_ILinkedListIterator"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CLinkedList_ILinkedListIterator_Mnext"(%"_Pshadow_Putility_CLinkedList_ILinkedListIterator"*)
declare %"_Pshadow_Putility_CLinkedList_ILinkedListIterator"* @"_Pshadow_Putility_CLinkedList_ILinkedListIterator_Mcreate"(%"_Pshadow_Putility_CLinkedList_ILinkedListIterator"*, %"_Pshadow_Putility_CLinkedList"*)

declare %byte @"_Pshadow_Pstandard_Cbyte_Mmin_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %byte @"_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %ubyte @"_Pshadow_Pstandard_Cbyte_Mabs"(%"_Pshadow_Pstandard_Cbyte"*)
declare %byte @"_Pshadow_Pstandard_Cbyte_Mmax_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %int @"_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %byte @"_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %byte @"_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cbyte_MtoString"(%"_Pshadow_Pstandard_Cbyte"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cbyte_MtoString_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cbyte"*, %ubyte)
declare %byte @"_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %"_Pshadow_Pstandard_Cbyte"* @"_Pshadow_Pstandard_Cbyte_Mcreate"(%"_Pshadow_Pstandard_Cbyte"*)
declare %byte @"_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)

declare %boolean @"_Pshadow_Pstandard_CString_MisEmpty"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoUpperCase"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CString_Miterator"(%"_Pshadow_Pstandard_CString"*)
declare %boolean @"_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoLowerCase"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"*, %int, %int)
declare %int @"_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)
declare %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"*, %int)
declare { %byte*, [1 x %int] } @"_Pshadow_Pstandard_CString_Mchars"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoString"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1"(%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] })
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1"(%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)
declare %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"*)

declare noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"*)
declare noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"*, %int)
declare %boolean @"_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass"(%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisInterface"(%"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisPrimitive"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CClass_Mparent"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CClass_MtoString"(%"_Pshadow_Pstandard_CClass"*)

declare %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CIndexOutOfBoundsException"*)
declare %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CIndexOutOfBoundsException"*, %int)

declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })
declare void @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1_CT"(%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CArray_MgetBaseClass"(%"_Pshadow_Pstandard_CArray"*)
declare %int @"_Pshadow_Pstandard_CArray_Mdims"(%"_Pshadow_Pstandard_CArray"*)
declare { %int*, [1 x %int] } @"_Pshadow_Pstandard_CArray_Mlengths"(%"_Pshadow_Pstandard_CArray"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CArray_MtoString"(%"_Pshadow_Pstandard_CArray"*)
declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"*, %int, %int)
declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })
declare %int @"_Pshadow_Pstandard_CArray_Msize"(%"_Pshadow_Pstandard_CArray"*)
declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcopy"(%"_Pshadow_Pstandard_CArray"*)

declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cboolean_MtoString"(%"_Pshadow_Pstandard_Cboolean"*)
declare %"_Pshadow_Pstandard_Cboolean"* @"_Pshadow_Pstandard_Cboolean_Mcreate"(%"_Pshadow_Pstandard_Cboolean"*)

declare %"_P_CUnexpectedNullException"* @"_P_CUnexpectedNullException_Mcreate"(%"_P_CUnexpectedNullException"*)

declare %ulong @"_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)
declare %int @"_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)
declare %ulong @"_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)
declare %ulong @"_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Culong_MtoString"(%"_Pshadow_Pstandard_Culong"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Culong_MtoString_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)
declare %ulong @"_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)
declare %"_Pshadow_Pstandard_Culong"* @"_Pshadow_Pstandard_Culong_Mcreate"(%"_Pshadow_Pstandard_Culong"*)
declare %ulong @"_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)

declare %ubyte @"_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)
declare %int @"_Pshadow_Pstandard_Cubyte_Mcompare_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)
declare %ubyte @"_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)
declare %ubyte @"_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cubyte_MtoString"(%"_Pshadow_Pstandard_Cubyte"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cubyte_MtoString_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)
declare %ubyte @"_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)
declare %"_Pshadow_Pstandard_Cubyte"* @"_Pshadow_Pstandard_Cubyte_Mcreate"(%"_Pshadow_Pstandard_Cubyte"*)
declare %ubyte @"_Pshadow_Pstandard_Cubyte_Mmodulus_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)

declare %uint @"_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %uint @"_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %uint @"_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %int @"_Pshadow_Pstandard_Cuint_Mhash"(%"_Pshadow_Pstandard_Cuint"*)
declare %int @"_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %uint @"_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %uint @"_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cuint_MtoString"(%"_Pshadow_Pstandard_Cuint"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cuint_MtoString_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %uint @"_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %"_Pshadow_Pstandard_Cuint"* @"_Pshadow_Pstandard_Cuint_Mcreate"(%"_Pshadow_Pstandard_Cuint"*)
declare %uint @"_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)

declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CObject_MgetClass"(%"_Pshadow_Pstandard_CObject"*)
declare %boolean @"_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mfreeze"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CObject_MtoString"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcopy"(%"_Pshadow_Pstandard_CObject"*)

@_array0 = private unnamed_addr constant [28 x %ubyte] c"shadow.utility@LinkedList<V>"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([28 x %byte]* @_array0, i32 0, i32 0), [1 x %int] [%int 28] }, %boolean true }
