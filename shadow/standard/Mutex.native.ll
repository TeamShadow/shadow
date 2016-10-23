%void = type i8

declare i32 @pthread_mutex_init(i8**, i32*)
declare i32 @pthread_mutex_lock(i8**)
declare i32 @pthread_mutex_unlock(i8**)
declare i32 @pthread_mutex_destroy(i8**)

define void @LockInit(%void** %lock) {
entry:
	%call = call i32 @pthread_mutex_init(%void** %lock, i32* null)
	ret void
}

define void @LockEnter(%void** %lock) {
entry:
	%call = call i32 @pthread_mutex_lock(%void** %lock)
	ret void
}

define void @LockExit(%void** %lock) {
entry:
	%call = call i32 @pthread_mutex_unlock(%void** %lock)
	ret void
}

define void @LockDestroy(%void** %lock) {
entry:
	%call = call i32 @pthread_mutex_destroy(%void** %lock)
	ret void
}