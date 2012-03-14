#include "stdlib.h"
#include "math.h"

uint_shadow_t _Pshadow_Pstandard_CMath_Mabs_Rint(int_shadow_t x) {
	return abs(x);
}

ulong_shadow_t _Pshadow_Pstandard_CMath_Mabs_Rlong(long_shadow_t x) {
	return labs(x);
}

int_shadow_t _Pshadow_Pstandard_CMath_Mdiv_Rint_Rint(int_shadow_t num, int_shadow_t denom, int_shadow_t *remainder ) {
	div_t result = div(num, denom);
	*remainder = result.rem;
	return result.quot;
}

long_shadow_t _Pshadow_Pstandard_CMath_Mdiv_Rlong_Rlong(long_shadow_t num, long_shadow_t denom, long_shadow_t *remainder ) {
	ldiv_t result = ldiv(num, denom);
	*remainder = result.rem;
	return result.quot;
}

double_shadow_t _Pshadow_Pstandard_CMath_Mabs_Rdouble(double_shadow_t x) {
	return fabs(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Mfloor_Rdouble(double_shadow_t x) {
	return floor(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Mceiling_Rdouble(double_shadow_t x) {
	return ceil(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Mmod_Rdouble_Rdouble(double_shadow_t num, double_shadow_t denom) {
	return fmod(num, denom);
}

double_shadow_t _Pshadow_Pstandard_CMath_Mparts_Rdouble(double_shadow_t x, double_shadow_t *intPart) {
	return modf(x, intPart);
}

double_shadow_t _Pshadow_Pstandard_CMath_Msin_Rdouble(double_shadow_t x) {
	return sin(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Mcos_Rdouble(double_shadow_t x) {
	return cos(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Mtan_Rdouble(double_shadow_t x) {
	return tan(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Masin_Rdouble(double_shadow_t x) {
	return asin(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Macos_Rdouble(double_shadow_t x) {
	return acos(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Matan_Rdouble(double_shadow_t x) {
	return atan(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Matan_Rdouble_Rdouble(double_shadow_t y, double_shadow_t x) {
	return atan2(y, x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Msinh_Rdouble(double_shadow_t x) {
	return sinh(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Mcosh_Rdouble(double_shadow_t x) {
	return cosh(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Mtanh_Rdouble(double_shadow_t x) {
	return tanh(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Msqrt_Rdouble(double_shadow_t x) {
	return sqrt(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Mexp_Rdouble(double_shadow_t x) {
	return exp(x);
}

int_shadow_t _Pshadow_Pstandard_CMath_Mpow_Rint_Ruint(int_shadow_t base, uint_shadow_t exp) {
	int_shadow_t result = 1;
	do {
		if (exp & 1)
			result *= base;
		base *= base;
	} while (exp >>= 1);
	return result;
}

long_shadow_t _Pshadow_Pstandard_CMath_Mpow_Rlong_Ruint(long_shadow_t base, uint_shadow_t exp) {
	long_shadow_t result = 1ll;
	do {
		if (exp & 1)
			result *= base;
		base *= base;
	} while (exp >>= 1);
	return result;
}

double_shadow_t _Pshadow_Pstandard_CMath_Mpow_Rdouble_Ruint(double_shadow_t base, uint_shadow_t exp) {
	double_shadow_t result = 1.0;
	do {
		if (exp & 1)
			result *= base;
		base *= base;
	} while (exp >>= 1);
	return result;
}

double_shadow_t _Pshadow_Pstandard_CMath_Mpow_Rdouble_Rdouble(double_shadow_t base, double_shadow_t exp) {
	return pow(base, exp);
}

double_shadow_t _Pshadow_Pstandard_CMath_Mln_Rdouble(double_shadow_t x) {
	return log(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Mlog_Rdouble(double_shadow_t x) {
	return log10(x);
}

double_shadow_t _Pshadow_Pstandard_CMath_Mlog_Rdouble_Rdouble(double_shadow_t base, double_shadow_t x) {
	return log(x) / log(base);
}
