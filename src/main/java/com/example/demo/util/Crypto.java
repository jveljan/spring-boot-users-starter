package com.example.demo.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import lombok.SneakyThrows;

@Service
public class Crypto {

  private static final char[] HEX_CHARS = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  private static SecureRandom random = new SecureRandom();

  @SneakyThrows
  public static String randomKey(int len) {
    MessageDigest sha1 = MessageDigest.getInstance("sha1");
    byte[] bytes = new byte[len];
    random.nextBytes(bytes);
    byte[] digest = sha1.digest(bytes);
    return new String(encodeHex(digest));
  }

  private static char[] encodeHex(byte[] bytes) {
    char[] chars = new char[bytes.length * 2];

    for (int i = 0; i < chars.length; i += 2) {
      byte b = bytes[i / 2];
      chars[i] = HEX_CHARS[b >>> 4 & 15];
      chars[i + 1] = HEX_CHARS[b & 15];
    }

    return chars;
  }
}
