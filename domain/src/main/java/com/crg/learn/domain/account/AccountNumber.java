package com.crg.learn.domain.account;

import java.util.Objects;

public class AccountNumber {

    private final String value;

    public AccountNumber(String value) {
        this.value = value;
    }

    public void writeTo(AccountNumberReader reader) {
        reader.numberValue(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountNumber that = (AccountNumber) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public interface AccountNumberReader {
        void numberValue(String value);
    }

}
