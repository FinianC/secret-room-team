package com.secret.utils;

import com.secret.constant.RS;
import com.secret.exception.ServiceException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.function.Supplier;

public class SecretRoomAssert extends Assert {

    public static void notNull(@Nullable Object object, RS rs) {
        if (object == null) {
            throw new ServiceException(rs);
        }
    }

    public static void isTrue(boolean expression,RS rs) {
        if (!expression) {
            throw new ServiceException(rs);
        }
    }
}
