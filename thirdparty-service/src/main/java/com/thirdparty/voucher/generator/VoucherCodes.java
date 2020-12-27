package com.thirdparty.voucher.generator;

import com.thirdparty.voucher.enums.Charset;
import lombok.Data;

import java.util.EnumMap;
import java.util.Random;
@Data
public class VoucherCodes {
    private static final Random RND = new Random(System.currentTimeMillis());
    private static final EnumMap<Charset, String> charsets = new EnumMap<>(Charset.class);
    {
        charsets.put(Charset.ALPHABETIC, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        charsets.put(Charset.ALPHANUMERIC, "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        charsets.put(Charset.NUMBERS, "0123456789");
    }

    public static String generate(CodeConfig config) {
        StringBuilder sb = new StringBuilder();
        char[] chars = charsets.get(config.getCharset()).toCharArray();
        char[] pattern = config.getPattern().toCharArray();

        if (config.getPrefix() != null) {
            sb.append(config.getPrefix());
        }

        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i] == CodeConfig.PATTERN_PLACEHOLDER) {
                sb.append(chars[RND.nextInt(chars.length)]);
            } else {
                sb.append(pattern[i]);
            }
        }

        if (config.getPostfix() != null) {
            sb.append(config.getPostfix());
        }

        return sb.toString();
    }
}
