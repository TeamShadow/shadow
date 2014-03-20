immutable class shadow.test@ExceptionTest extends shadow.standard@Object {
    create(shadow.test@ExceptionTest this) => (shadow.test@ExceptionTest) {
        // block _0_(null);
        return this;
    }
    main(shadow.test@ExceptionTest this, default@String[] args) => (int) {
        // block _0_(null);
        shadow.test@ExceptionTest:test3(this);
        // label _1_;
        return 0;
    }
    test(shadow.test@ExceptionTest this) => () {
        // block _0_(null);
        // label _1_;
        throw cast<shadow.standard@Object>(shadow.standard@IndexOutOfBoundsException:create);
_1_:
        return;
    }
    test1(shadow.test@ExceptionTest this) => () {
        shadow.test@ExceptionA ex;
        // block _0_(null);
        // label _1_;
        // block _2_(_0_);
        // label _3_;
        // block _4_(_2_);
        // label _5_;
        // label _6_;
        // block _7_(_4_);
        shadow.test@ExceptionTest:test(this);
        goto _1_;
_5_:
        // landingpad;
_6_:
        // unwind;
        // label _8_;
        goto int:equal(typeid(_exception), typeid(ExceptionA:class)) ? _3_, _8_;
_3_:
        ex = catch shadow.test@ExceptionA;
        // label _9_;
        // label _10_;
        goto shadow.io@Console:instance === cast<shadow.io@Console>(default@null) ? _9_, _10_;
_9_:
        shadow.io@Console:instance = shadow.io@Console:create(cast<shadow.standard@Object>(shadow.io@Console:create));
_10_:
        goto _1_;
_8_:
// Missing terminator!!!
_1_:
        return;
    }
    test2(shadow.test@ExceptionTest this) => () {
        shadow.test@ExceptionA ex;
        shadow.test@ExceptionB ex1;
        // block _0_(null);
        // label _1_;
        // block _2_(_0_);
        // label _3_;
        // label _4_;
        // block _5_(_2_);
        // label _6_;
        // label _7_;
        // block _8_(_5_);
        shadow.test@ExceptionTest:test1(this);
        goto _1_;
_6_:
        // landingpad;
_7_:
        // unwind;
        // label _9_;
        goto int:equal(typeid(_exception), typeid(ExceptionA:class)) ? _3_, _9_;
_3_:
        ex = catch shadow.test@ExceptionA;
        // label _10_;
        // label _11_;
        goto shadow.io@Console:instance === cast<shadow.io@Console>(default@null) ? _10_, _11_;
_10_:
        shadow.io@Console:instance = shadow.io@Console:create(cast<shadow.standard@Object>(shadow.io@Console:create));
_11_:
        goto _1_;
_9_:
        // label _12_;
        goto int:equal(typeid(_exception), typeid(ExceptionB:class)) ? _4_, _12_;
_4_:
        ex1 = catch shadow.test@ExceptionB;
        // label _13_;
        // label _14_;
        goto shadow.io@Console:instance === cast<shadow.io@Console>(default@null) ? _13_, _14_;
_13_:
        shadow.io@Console:instance = shadow.io@Console:create(cast<shadow.standard@Object>(shadow.io@Console:create));
_14_:
        goto _1_;
_12_:
// Missing terminator!!!
_1_:
        return;
    }
    test3(shadow.test@ExceptionTest this) => () {
        shadow.standard@Exception ex;
        // block _0_(null);
        // label _1_;
        // block _2_(_0_);
        // label _3_;
        // block _4_(_2_);
        // label _5_;
        // label _6_;
        // block _7_(_4_);
        shadow.test@ExceptionTest:test2(this);
        goto _1_;
_5_:
        // landingpad;
_6_:
        // unwind;
        // label _8_;
        goto int:equal(typeid(_exception), typeid(Exception:class)) ? _3_, _8_;
_3_:
        ex = catch shadow.standard@Exception;
        // label _9_;
        // label _10_;
        goto shadow.io@Console:instance === cast<shadow.io@Console>(default@null) ? _9_, _10_;
_9_:
        shadow.io@Console:instance = shadow.io@Console:create(cast<shadow.standard@Object>(shadow.io@Console:create));
_10_:
        goto _1_;
_8_:
// Missing terminator!!!
_1_:
        return;
    }
}
