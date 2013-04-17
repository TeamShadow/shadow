; shadow.utility@LinkedList<V>:Node

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

%"_Pshadow_Putility_CLinkedList_INode_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Putility_CLinkedList_INode"*)*, %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Putility_CLinkedList_INode"*)*, void (%"_Pshadow_Putility_CLinkedList_INode"*)*, %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*)*, %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*)*, void (%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList_INode"*)* }
%"_Pshadow_Putility_CLinkedList_INode" = type { %"_Pshadow_Putility_CLinkedList_INode_Mclass"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"* }
%"_Pshadow_Pstandard_CException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, void (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CException_Mclass"* }
%"_Pshadow_Pstandard_Cint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cint"* (%"_Pshadow_Pstandard_Cint"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cint"*)*, %uint (%"_Pshadow_Pstandard_Cint"*)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cint"*, %uint)* }
%"_Pshadow_Pstandard_Cint" = type { %"_Pshadow_Pstandard_Cint_Mclass"*, %int }
%"_Pshadow_Putility_CLinkedList_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Putility_CLinkedList"* (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Putility_CLinkedList"*)*, %boolean (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList"*, %int)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList"*, %int)*, void (%"_Pshadow_Putility_CLinkedList"*, %int, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList"*)*, %boolean (%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Putility_CLinkedList"*)* }
%"_Pshadow_Putility_CLinkedList" = type { %"_Pshadow_Putility_CLinkedList_Mclass"*, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Putility_CLinkedList_INode"*, %int, %int }
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

@"_Pshadow_Putility_CLinkedList_INode_Mclass" = constant %"_Pshadow_Putility_CLinkedList_INode_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int 0, %int ptrtoint (%"_Pshadow_Putility_CLinkedList_INode"* getelementptr (%"_Pshadow_Putility_CLinkedList_INode"* null, i32 1) to i32), %int ptrtoint (%"_Pshadow_Putility_CLinkedList_INode"** getelementptr (%"_Pshadow_Putility_CLinkedList_INode"** null, i32 1) to i32) }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList"*)* @"_Pshadow_Putility_CLinkedList_INode_Mcreate", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MtoString", void (%"_Pshadow_Putility_CLinkedList_INode"*)* @"_Pshadow_Putility_CLinkedList_INode_Mclear", %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Putility_CLinkedList_INode"*)* @"_Pshadow_Putility_CLinkedList_INode_Mcreate_CV_Pshadow_Putility_CLinkedList_INode", void (%"_Pshadow_Putility_CLinkedList_INode"*)* @"_Pshadow_Putility_CLinkedList_INode_Mdelete", %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*)* @"_Pshadow_Putility_CLinkedList_INode_Mnext", %"_Pshadow_Putility_CLinkedList_INode"* (%"_Pshadow_Putility_CLinkedList_INode"*)* @"_Pshadow_Putility_CLinkedList_INode_Mprev", void (%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Putility_CLinkedList_INode_Mvalue_CV", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CLinkedList_INode"*)* @"_Pshadow_Putility_CLinkedList_INode_Mvalue" }
@"_Pshadow_Pstandard_CException_Mclass" = external constant %"_Pshadow_Pstandard_CException_Mclass"
@"_Pshadow_Pstandard_Cint_Mclass" = external constant %"_Pshadow_Pstandard_Cint_Mclass"
@"_Pshadow_Putility_CLinkedList_Mclass" = external constant %"_Pshadow_Putility_CLinkedList_Mclass"
@"_Pshadow_Pstandard_Cushort_Mclass" = external constant %"_Pshadow_Pstandard_Cushort_Mclass"
@"_Pshadow_Pstandard_Ccode_Mclass" = external constant %"_Pshadow_Pstandard_Ccode_Mclass"
@"_Pshadow_Pstandard_Cdouble_Mclass" = external constant %"_Pshadow_Pstandard_Cdouble_Mclass"
@"_Pshadow_Pstandard_Clong_Mclass" = external constant %"_Pshadow_Pstandard_Clong_Mclass"
@"_Pshadow_Pstandard_Cfloat_Mclass" = external constant %"_Pshadow_Pstandard_Cfloat_Mclass"
@"_Pshadow_Pstandard_Cshort_Mclass" = external constant %"_Pshadow_Pstandard_Cshort_Mclass"
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

define void @"_Pshadow_Putility_CLinkedList_INode_Mclear"(%"_Pshadow_Putility_CLinkedList_INode"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList_INode"*
    store %"_Pshadow_Putility_CLinkedList_INode"* %0, %"_Pshadow_Putility_CLinkedList_INode"** %this
    %2 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %3 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %2, i32 0, i32 2
    %4 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %5 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %4, i32 0, i32 1
    %6 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %7 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %8 = insertvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } undef, %"_Pshadow_Putility_CLinkedList_INode"* %6, 0
    %9 = insertvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } %8, %"_Pshadow_Putility_CLinkedList_INode"* %7, 1
    %10 = extractvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } %9, 0
    store %"_Pshadow_Putility_CLinkedList_INode"* %10, %"_Pshadow_Putility_CLinkedList_INode"** %3
    %11 = extractvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } %9, 1
    store %"_Pshadow_Putility_CLinkedList_INode"* %11, %"_Pshadow_Putility_CLinkedList_INode"** %5
    %12 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %13 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %12, i32 0, i32 3
    %14 = load %"_Pshadow_Putility_CLinkedList"** %13
    %15 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %14, i32 0, i32 4
    store %int 0, %int* %15
    %16 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %17 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %16, i32 0, i32 3
    %18 = load %"_Pshadow_Putility_CLinkedList"** %17
    %19 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %18, i32 0, i32 3
    %20 = load %int* %19
    %21 = add %int %20, 1
    store %int %21, %int* %19
    ret void
}

define %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_INode_Mnext"(%"_Pshadow_Putility_CLinkedList_INode"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList_INode"*
    store %"_Pshadow_Putility_CLinkedList_INode"* %0, %"_Pshadow_Putility_CLinkedList_INode"** %this
    %2 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %3 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %2, i32 0, i32 1
    %4 = load %"_Pshadow_Putility_CLinkedList_INode"** %3
    ret %"_Pshadow_Putility_CLinkedList_INode"* %4
}

define void @"_Pshadow_Putility_CLinkedList_INode_Mdelete"(%"_Pshadow_Putility_CLinkedList_INode"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList_INode"*
    store %"_Pshadow_Putility_CLinkedList_INode"* %0, %"_Pshadow_Putility_CLinkedList_INode"** %this
    %2 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %3 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %2, i32 0, i32 2
    %4 = load %"_Pshadow_Putility_CLinkedList_INode"** %3
    %5 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %4, i32 0, i32 1
    %6 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %7 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %6, i32 0, i32 1
    %8 = load %"_Pshadow_Putility_CLinkedList_INode"** %7
    %9 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %8, i32 0, i32 2
    %10 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %11 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %10, i32 0, i32 1
    %12 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %13 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %12, i32 0, i32 2
    %14 = load %"_Pshadow_Putility_CLinkedList_INode"** %11
    %15 = load %"_Pshadow_Putility_CLinkedList_INode"** %13
    %16 = insertvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } undef, %"_Pshadow_Putility_CLinkedList_INode"* %14, 0
    %17 = insertvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } %16, %"_Pshadow_Putility_CLinkedList_INode"* %15, 1
    %18 = extractvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } %17, 0
    store %"_Pshadow_Putility_CLinkedList_INode"* %18, %"_Pshadow_Putility_CLinkedList_INode"** %5
    %19 = extractvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } %17, 1
    store %"_Pshadow_Putility_CLinkedList_INode"* %19, %"_Pshadow_Putility_CLinkedList_INode"** %9
    %20 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %21 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %20, i32 0, i32 3
    %22 = load %"_Pshadow_Putility_CLinkedList"** %21
    %23 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %22, i32 0, i32 4
    %24 = load %int* %23
    %25 = sub %int %24, 1
    store %int %25, %int* %23
    %26 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %27 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %26, i32 0, i32 3
    %28 = load %"_Pshadow_Putility_CLinkedList"** %27
    %29 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %28, i32 0, i32 3
    %30 = load %int* %29
    %31 = add %int %30, 1
    store %int %31, %int* %29
    ret void
}

define void @"_Pshadow_Putility_CLinkedList_INode_Mvalue_CV"(%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList_INode"*
    %initialValue = alloca %"_Pshadow_Pstandard_CObject"*
    store %"_Pshadow_Putility_CLinkedList_INode"* %0, %"_Pshadow_Putility_CLinkedList_INode"** %this
    store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %initialValue
    %3 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %4 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %3, i32 0, i32 4
    %5 = load %"_Pshadow_Pstandard_CObject"** %initialValue
    store %"_Pshadow_Pstandard_CObject"* %5, %"_Pshadow_Pstandard_CObject"** %4
    %6 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %7 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %6, i32 0, i32 3
    %8 = load %"_Pshadow_Putility_CLinkedList"** %7
    %9 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %8, i32 0, i32 3
    %10 = load %int* %9
    %11 = add %int %10, 1
    store %int %11, %int* %9
    ret void
}

define %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CLinkedList_INode_Mvalue"(%"_Pshadow_Putility_CLinkedList_INode"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList_INode"*
    store %"_Pshadow_Putility_CLinkedList_INode"* %0, %"_Pshadow_Putility_CLinkedList_INode"** %this
    %2 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %3 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %2, i32 0, i32 4
    %4 = load %"_Pshadow_Pstandard_CObject"** %3
    ret %"_Pshadow_Pstandard_CObject"* %4
}

define %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_INode_Mcreate"(%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList_INode"*
    %outer = alloca %"_Pshadow_Putility_CLinkedList"*
    store %"_Pshadow_Putility_CLinkedList_INode"* %0, %"_Pshadow_Putility_CLinkedList_INode"** %this
    store %"_Pshadow_Putility_CLinkedList"* %1, %"_Pshadow_Putility_CLinkedList"** %outer
    %3 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %4 = bitcast %"_Pshadow_Putility_CLinkedList_INode"* %3 to %"_Pshadow_Pstandard_CObject"*
    %5 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %4)
    %6 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %0, i32 0, i32 0
    store %"_Pshadow_Putility_CLinkedList_INode_Mclass"* @"_Pshadow_Putility_CLinkedList_INode_Mclass", %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %6
    %7 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %8 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %7, i32 0, i32 3
    %9 = load %"_Pshadow_Putility_CLinkedList"** %outer
    store %"_Pshadow_Putility_CLinkedList"* %9, %"_Pshadow_Putility_CLinkedList"** %8
    %10 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %11 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %10, i32 0, i32 1
    %12 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    store %"_Pshadow_Putility_CLinkedList_INode"* %12, %"_Pshadow_Putility_CLinkedList_INode"** %11
    %13 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %14 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %13, i32 0, i32 4
    store %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CObject"** %14
    %15 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %16 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %15, i32 0, i32 2
    %17 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    store %"_Pshadow_Putility_CLinkedList_INode"* %17, %"_Pshadow_Putility_CLinkedList_INode"** %16
    ret %"_Pshadow_Putility_CLinkedList_INode"* %0
}

define %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_INode_Mcreate_CV_Pshadow_Putility_CLinkedList_INode"(%"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Putility_CLinkedList_INode"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList_INode"*
    %outer = alloca %"_Pshadow_Putility_CLinkedList"*
    %initialValue = alloca %"_Pshadow_Pstandard_CObject"*
    %after = alloca %"_Pshadow_Putility_CLinkedList_INode"*
    store %"_Pshadow_Putility_CLinkedList_INode"* %0, %"_Pshadow_Putility_CLinkedList_INode"** %this
    store %"_Pshadow_Putility_CLinkedList"* %1, %"_Pshadow_Putility_CLinkedList"** %outer
    store %"_Pshadow_Pstandard_CObject"* %2, %"_Pshadow_Pstandard_CObject"** %initialValue
    store %"_Pshadow_Putility_CLinkedList_INode"* %3, %"_Pshadow_Putility_CLinkedList_INode"** %after
    %5 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %6 = bitcast %"_Pshadow_Putility_CLinkedList_INode"* %5 to %"_Pshadow_Pstandard_CObject"*
    %7 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %6)
    %8 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %0, i32 0, i32 0
    store %"_Pshadow_Putility_CLinkedList_INode_Mclass"* @"_Pshadow_Putility_CLinkedList_INode_Mclass", %"_Pshadow_Putility_CLinkedList_INode_Mclass"** %8
    %9 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %10 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %9, i32 0, i32 3
    %11 = load %"_Pshadow_Putility_CLinkedList"** %outer
    store %"_Pshadow_Putility_CLinkedList"* %11, %"_Pshadow_Putility_CLinkedList"** %10
    %12 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %13 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %12, i32 0, i32 1
    %14 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    store %"_Pshadow_Putility_CLinkedList_INode"* %14, %"_Pshadow_Putility_CLinkedList_INode"** %13
    %15 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %16 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %15, i32 0, i32 4
    store %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CObject"** %16
    %17 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %18 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %17, i32 0, i32 2
    %19 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    store %"_Pshadow_Putility_CLinkedList_INode"* %19, %"_Pshadow_Putility_CLinkedList_INode"** %18
    %20 = load %"_Pshadow_Putility_CLinkedList_INode"** %after
    %21 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %20, i32 0, i32 2
    %22 = load %"_Pshadow_Putility_CLinkedList_INode"** %21
    %23 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %22, i32 0, i32 1
    %24 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %25 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %24, i32 0, i32 2
    %26 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %27 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %26, i32 0, i32 1
    %28 = load %"_Pshadow_Putility_CLinkedList_INode"** %after
    %29 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %28, i32 0, i32 2
    %30 = load %"_Pshadow_Putility_CLinkedList_INode"** %after
    %31 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %30, i32 0, i32 2
    %32 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %33 = load %"_Pshadow_Putility_CLinkedList_INode"** %31
    %34 = load %"_Pshadow_Putility_CLinkedList_INode"** %after
    %35 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %36 = insertvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } undef, %"_Pshadow_Putility_CLinkedList_INode"* %32, 0
    %37 = insertvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } %36, %"_Pshadow_Putility_CLinkedList_INode"* %33, 1
    %38 = insertvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } %37, %"_Pshadow_Putility_CLinkedList_INode"* %34, 2
    %39 = insertvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } %38, %"_Pshadow_Putility_CLinkedList_INode"* %35, 3
    %40 = extractvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } %39, 0
    store %"_Pshadow_Putility_CLinkedList_INode"* %40, %"_Pshadow_Putility_CLinkedList_INode"** %23
    %41 = extractvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } %39, 1
    store %"_Pshadow_Putility_CLinkedList_INode"* %41, %"_Pshadow_Putility_CLinkedList_INode"** %25
    %42 = extractvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } %39, 2
    store %"_Pshadow_Putility_CLinkedList_INode"* %42, %"_Pshadow_Putility_CLinkedList_INode"** %27
    %43 = extractvalue { %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"*, %"_Pshadow_Putility_CLinkedList_INode"* } %39, 3
    store %"_Pshadow_Putility_CLinkedList_INode"* %43, %"_Pshadow_Putility_CLinkedList_INode"** %29
    %44 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %45 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %44, i32 0, i32 4
    %46 = load %"_Pshadow_Pstandard_CObject"** %initialValue
    store %"_Pshadow_Pstandard_CObject"* %46, %"_Pshadow_Pstandard_CObject"** %45
    %47 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %48 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %47, i32 0, i32 3
    %49 = load %"_Pshadow_Putility_CLinkedList"** %48
    %50 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %49, i32 0, i32 4
    %51 = load %int* %50
    %52 = add %int %51, 1
    store %int %52, %int* %50
    %53 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %54 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %53, i32 0, i32 3
    %55 = load %"_Pshadow_Putility_CLinkedList"** %54
    %56 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList"* %55, i32 0, i32 3
    %57 = load %int* %56
    %58 = add %int %57, 1
    store %int %58, %int* %56
    ret %"_Pshadow_Putility_CLinkedList_INode"* %0
}

define %"_Pshadow_Putility_CLinkedList_INode"* @"_Pshadow_Putility_CLinkedList_INode_Mprev"(%"_Pshadow_Putility_CLinkedList_INode"*) {
    %this = alloca %"_Pshadow_Putility_CLinkedList_INode"*
    store %"_Pshadow_Putility_CLinkedList_INode"* %0, %"_Pshadow_Putility_CLinkedList_INode"** %this
    %2 = load %"_Pshadow_Putility_CLinkedList_INode"** %this
    %3 = getelementptr inbounds %"_Pshadow_Putility_CLinkedList_INode"* %2, i32 0, i32 2
    %4 = load %"_Pshadow_Putility_CLinkedList_INode"** %3
    ret %"_Pshadow_Putility_CLinkedList_INode"* %4
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

declare void @"_Pshadow_Putility_CLinkedList_Mclear"(%"_Pshadow_Putility_CLinkedList"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CLinkedList_Miterator"(%"_Pshadow_Putility_CLinkedList"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CLinkedList_Mindex_Pshadow_Pstandard_Cint"(%"_Pshadow_Putility_CLinkedList"*, %int)
declare void @"_Pshadow_Putility_CLinkedList_Mindex_Pshadow_Pstandard_Cint_CV"(%"_Pshadow_Putility_CLinkedList"*, %int, %"_Pshadow_Pstandard_CObject"*)
declare %boolean @"_Pshadow_Putility_CLinkedList_Mremove_CV"(%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CLinkedList_Mdelete_Pshadow_Pstandard_Cint"(%"_Pshadow_Putility_CLinkedList"*, %int)
declare %boolean @"_Pshadow_Putility_CLinkedList_Mcontains_CV"(%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)
declare %int @"_Pshadow_Putility_CLinkedList_MindexOf_CV"(%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)
declare %boolean @"_Pshadow_Putility_CLinkedList_Madd_CV"(%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Putility_CLinkedList"* @"_Pshadow_Putility_CLinkedList_Mcreate"(%"_Pshadow_Putility_CLinkedList"*, %"_Pshadow_Pstandard_CClass"*)
declare %int @"_Pshadow_Putility_CLinkedList_Msize"(%"_Pshadow_Putility_CLinkedList"*)

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

@_array0 = private unnamed_addr constant [33 x %ubyte] c"shadow.utility@LinkedList<V>:Node"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([33 x %byte]* @_array0, i32 0, i32 0), [1 x %int] [%int 33] }, %boolean true }
