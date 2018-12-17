package com.cloudera.director.spi.v2.model.exception;

import static com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition.Type;
import static com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition.Type.ERROR;
import static com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition.Type.WARNING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.Map;

import org.junit.Test;

/**
 * Tests {@link PluginExceptionCondition}.
 */
public class PluginExceptionConditionTest {

  protected static final String MSG0 = "msg0";

  protected static final String MSG1 = "msg1";

  protected static final String[] MESSAGES = {MSG0, MSG1};

  private PluginExceptionCondition condition;

  @Test
  public void testConstructor() {
    for (Type expectedType : Type.values()) {
      condition = new PluginExceptionCondition(expectedType, MSG0);
      Type actualType = condition.getType();
      assertThat(actualType).isEqualTo(expectedType);
      assertThat(condition.getMessage()).isEqualTo(MSG0);
      assertThat(condition.isError()).isEqualTo(expectedType == ERROR);
      assertThat(condition.isWarning()).isEqualTo(expectedType == WARNING);
    }
  }

  @Test
  public void testEqualsAndHashCodeAndCompareTo() {
    Object dummy = new Object();
    Type[] types = Type.values();
    int typeCount = types.length;
    int msgCount = MESSAGES.length;
    for (int typeIndex0 = 0; typeIndex0 < typeCount; ++typeIndex0) {
      for (int msgIndex0 = 0; msgIndex0 < msgCount; ++msgIndex0) {
        PluginExceptionCondition condition0 = new PluginExceptionCondition(
            types[typeIndex0], MESSAGES[msgIndex0]);
        assertThat(condition0).isEqualTo(condition0);
        assertThat(condition0).isNotEqualTo(null);
        assertThat(condition0).isNotEqualTo(dummy);
        assertThat(condition0.hashCode()).isEqualTo(condition0.hashCode());
        assertThat(condition0.compareTo(condition0)).isEqualTo(0);
        int index0 = typeIndex0 * msgCount + msgIndex0;
        for (int typeIndex1 = 0; typeIndex1 < typeCount; ++typeIndex1) {
          for (int msgIndex1 = 0; msgIndex1 < msgCount; ++msgIndex1) {
            int index1 = typeIndex1 * msgCount + msgIndex1;
            PluginExceptionCondition condition1 = new PluginExceptionCondition(
                types[typeIndex1], MESSAGES[msgIndex1]);
            boolean match = (typeIndex0 == typeIndex1) && (msgIndex0 == msgIndex1);
            assertThat(condition1.equals(condition0)).isEqualTo(match);
            if (match) {
              assertThat(condition1.hashCode()).isEqualTo(condition0.hashCode());
            }
            int actualComparisonResult = condition0.compareTo(condition1);
            int expectedSignum = (index0 == index1) ? 0 : ((index0 < index1) ? -1 : 1);
            assertThat(signum(actualComparisonResult))
                .as("[%d, %d](%d), [%d, %d](%d)",
                    typeIndex0, msgIndex0, index0,
                    typeIndex1, msgIndex1, index1)
                .isEqualTo(expectedSignum);
          }
        }
      }
    }
  }

  private int signum(int i) {
    return (i == 0) ? 0 : ((i < 0) ? -1 : 1);
  }

  @Test
  public void testNullMessage() {
    condition = new PluginExceptionCondition(ERROR, (String) null);
    assertThat(condition.getMessage()).isEqualTo(PluginExceptionCondition.DEFAULT_MESSAGE);
    assertThat(condition.getExceptionInfo())
        .containsExactly(entry(PluginExceptionCondition.KEY_MESSAGE, PluginExceptionCondition.DEFAULT_MESSAGE));
  }

  @Test
  public void testEmptyMessage() {
    condition = new PluginExceptionCondition(ERROR, "");
    assertThat(condition.getMessage()).isEqualTo(PluginExceptionCondition.DEFAULT_MESSAGE);
    assertThat(condition.getExceptionInfo())
        .containsExactly(entry(PluginExceptionCondition.KEY_MESSAGE, PluginExceptionCondition.DEFAULT_MESSAGE));
  }

  @Test
  public void testToString() {
    for (Type type : Type.values()) {
      condition = new PluginExceptionCondition(type, MSG0);
      String s = condition.toString();
      assertThat(s.contains(type.toString()));
      assertThat(s.contains(MSG0));
    }
  }

  @Test
  public void testToExceptionInfoMap() {
    Map<String, String> exceptionMap = PluginExceptionCondition.toExceptionInfoMap(MSG0);
    assertThat(exceptionMap).containsExactly(entry(PluginExceptionCondition.KEY_MESSAGE, MSG0));

    exceptionMap = PluginExceptionCondition.toExceptionInfoMap(null);
    assertThat(exceptionMap)
        .containsExactly(entry(PluginExceptionCondition.KEY_MESSAGE, PluginExceptionCondition.DEFAULT_MESSAGE));

    exceptionMap = PluginExceptionCondition.toExceptionInfoMap("");
    assertThat(exceptionMap)
        .containsExactly(entry(PluginExceptionCondition.KEY_MESSAGE, PluginExceptionCondition.DEFAULT_MESSAGE));
  }
}
