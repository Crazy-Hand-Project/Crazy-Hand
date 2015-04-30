package com;
//Nabbed this from the internet.
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
public class SeedGetter {
  static long getSeed(Random random) {
    byte[] ba0, ba1, bar;
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(new Random(0));
      ba0 = baos.toByteArray();
      baos = new ByteArrayOutputStream(128);
      oos = new ObjectOutputStream(baos);
      oos.writeObject(new Random(-1));
      ba1 = baos.toByteArray();
      baos = new ByteArrayOutputStream(128);
      oos = new ObjectOutputStream(baos);
      oos.writeObject(random);
      bar = baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("IOException: " + e);
    }
    if (ba0.length != ba1.length || ba0.length != bar.length)
      throw new RuntimeException("bad serialized length");
    int i = 0;
    while (i < ba0.length && ba0[i] == ba1[i]) {
      i++;
    }
    int j = ba0.length;
    while (j > 0 && ba0[j - 1] == ba1[j - 1]) {
      j--;
    }
    if (j - i != 6)
      throw new RuntimeException("6 differing bytes not found");
    // The constant 0x5DEECE66DL is from
    // http://download.oracle.com/javase/6/docs/api/java/util/Random.html .
    return ((bar[i] & 255L) << 40 | (bar[i + 1] & 255L) << 32 |
            (bar[i + 2] & 255L) << 24 | (bar[i + 3] & 255L) << 16 |
            (bar[i + 4] & 255L) << 8 | (bar[i + 5] & 255L)) ^ 0x5DEECE66DL;
  }
}