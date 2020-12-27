package com.thirdparty.voucher.generator;

import com.thirdparty.voucher.enums.Charset;
import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
@Data
@ToString(callSuper=true)
public class CodeConfig {
    public final static char PATTERN_PLACEHOLDER = '#';

    private final int length;
    private final Charset charset;
    private final String prefix;
    private final String postfix;
    private final String pattern;

    public CodeConfig(Integer length, Charset charset, String prefix, String postfix, String pattern) {
        if (length == null) {
            length = 8;
        }

        if (charset == null) {
            charset = Charset.ALPHANUMERIC;
        }

        if (pattern == null) {
            char[] chars = new char[length];
            Arrays.fill(chars, PATTERN_PLACEHOLDER);
            pattern = new String(chars);
        }

        this.length = length;
        this.charset = charset;
        this.prefix = prefix;
        this.postfix = postfix;
        this.pattern = pattern;
    }

    public static CodeConfig length(int length) {
        return new CodeConfig(length, null, null, null, null);
    }

    public static CodeConfig pattern(String pattern) {
        return new CodeConfig(null, null, null, null, pattern);
    }

    public int getLength() {
        return length;
    }

    public Charset getCharset() {
        return charset;
    }

    public CodeConfig withCharset(Charset charset) {
        return new CodeConfig(length, charset, prefix, postfix, pattern);
    }

    public String getPrefix() {
        return prefix;
    }

    public CodeConfig withPrefix(String prefix) {
        return new CodeConfig(length, charset, prefix, postfix, pattern);
    }

    public String getPostfix() {
        return postfix;
    }

    public CodeConfig withPostfix(String postfix) {
        return new CodeConfig(length, charset, prefix, postfix, pattern);
    }

    public String getPattern() {
        return pattern;
    }
}
