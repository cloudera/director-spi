// (c) Copyright 2015 Cloudera, Inc.

package com.cloudera.director.spi.v2.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class KeySerializationTest {

  private KeySerialization keySerialization;

  @Before
  public void setUp() {
    keySerialization = new KeySerialization();
  }

  @Test
  public void testToStringAndBack() throws Exception {
    Random r = new Random();

    for (int i = 0; i < 40; i++) {
      BigDecimal d = new BigDecimal(r.nextDouble());
      String s = keySerialization.serializeToString(d);
      BigDecimal d2 = keySerialization.deserializeFromString(s, BigDecimal.class);

      assertThat(d2).isEqualTo(d);
    }
  }

}
