package com.cloudera.director.spi.v2.model.exception;

import static com.cloudera.director.spi.v2.model.exception.PluginExceptionDetails.DEFAULT_DETAILS;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Base class for tests involving {@link AbstractPluginException} and its subclasses.
 */
public class AbstractPluginExceptionTest {

  /**
   * The test message.
   */
  protected static final String TEST_MESSAGE = "test message";

  /**
   * The test throwable
   */
  protected static final Throwable TEST_THROWABLE = new Throwable("test");

  /**
   * Constructor parameter variants.
   */
  protected static enum ConstructorVariant {
    NO_ARGS(new Class[]{}) {
      @Override
      public Object[] getParameterValues(String message, Throwable cause,
          PluginExceptionDetails details) {
        return new Object[]{};
      }
    },
    MESSAGE_ONLY(new Class[]{String.class}) {
      @Override
      public Object[] getParameterValues(String message, Throwable cause,
          PluginExceptionDetails details) {
        return new Object[]{message};
      }
    },
    CAUSE_ONLY(new Class[]{Throwable.class}) {
      @Override
      public Object[] getParameterValues(String message, Throwable cause,
          PluginExceptionDetails details) {
        return new Object[]{cause};
      }
    },
    MESSAGE_AND_CAUSE(new Class[]{String.class, Throwable.class}) {
      @Override
      public Object[] getParameterValues(String message, Throwable cause,
          PluginExceptionDetails details) {
        return new Object[]{message, cause};
      }
    },
    MESSAGE_AND_DETAILS(new Class[]{String.class, PluginExceptionDetails.class}) {
      @Override
      public Object[] getParameterValues(String message, Throwable cause,
          PluginExceptionDetails details) {
        return new Object[]{message, details};
      }
    },
    ALL(new Class[]{String.class, Throwable.class, PluginExceptionDetails.class}) {
      @Override
      public Object[] getParameterValues(String message, Throwable cause,
          PluginExceptionDetails details) {
        return new Object[]{message, cause, details};
      }
    };

    /**
     * The constructor parameter types.
     */
    private final Class[] parameterTypes;

    /**
     * Creates an arg variant with the specified parameters.
     *
     * @param parameterTypes the constructor parameter types
     */
    private ConstructorVariant(Class[] parameterTypes) {
      this.parameterTypes = parameterTypes;
    }

    /**
     * Returns the constructor parameter types.
     *
     * @return the constructor parameter types
     */
    public Class[] getParameterTypes() {
      return parameterTypes;
    }

    public abstract Object[] getParameterValues(String message, Throwable cause,
        PluginExceptionDetails details);
  }

  @Test
  public void testConstructors() throws Exception {
    for (ConstructorVariant constructorVariant : ConstructorVariant.values()) {
      try {
        throwException(TEST_MESSAGE, TEST_THROWABLE, DEFAULT_DETAILS, constructorVariant);
      } catch (AbstractPluginException e) {
        validateException(e, TEST_MESSAGE, TEST_THROWABLE, DEFAULT_DETAILS, constructorVariant);
      }
    }
  }

  /**
   * Throws a plugin exception with the appropriate subset of the specified arguments.
   *
   * @param message            the detail message
   * @param cause              the cause
   * @param details            the exception details
   * @param constructorVariant the desired constructor argument variant
   * @throws AbstractPluginException the desired exception
   */
  protected void throwException(String message, Throwable cause,
      PluginExceptionDetails details, ConstructorVariant constructorVariant) throws Exception {
    Class<? extends AbstractPluginException> expectedExceptionClass = getExpectedExceptionClass();
    Class[] parameterTypes = constructorVariant.getParameterTypes();
    Constructor<? extends AbstractPluginException> constructor =
        expectedExceptionClass.getDeclaredConstructor(parameterTypes);
    Object[] args = constructorVariant.getParameterValues(message, cause, details);
    throw constructor.newInstance(args);
  }

  /**
   * Returns the expected exception class.
   *
   * @return the expected exception class
   */
  protected Class<? extends AbstractPluginException> getExpectedExceptionClass() {
    return TestException.class;
  }

  /**
   * Verifies that the specified exception has the specified properties.
   *
   * @param e                  the exception
   * @param message            the expected message
   * @param cause              the expected cause
   * @param details            the expected exception details
   * @param constructorVariant the desired constructor argument variant
   */
  protected void validateException(AbstractPluginException e, String message, Throwable cause,
      PluginExceptionDetails details, ConstructorVariant constructorVariant) {
    String expectedMessage = null;
    switch (constructorVariant) {
      case MESSAGE_ONLY:
      case MESSAGE_AND_CAUSE:
      case MESSAGE_AND_DETAILS:
      case ALL:
        expectedMessage = message;
        break;
      default:
        break;
    }
    Throwable expectedCause = null;
    switch (constructorVariant) {
      case CAUSE_ONLY:
      case MESSAGE_AND_CAUSE:
      case ALL:
        expectedCause = cause;
        break;
      default:
        break;
    }
    PluginExceptionDetails expectedDetails = DEFAULT_DETAILS;
    switch (constructorVariant) {
      case MESSAGE_AND_DETAILS:
      case ALL:
        expectedDetails = details;
        break;
      default:
        break;
    }
    assertThat(e).isNotNull();
    assertThat(e).isInstanceOf(getExpectedExceptionClass());
    assertThat(e.getMessage()).isEqualTo(expectedMessage);
    assertThat(e.getCause()).isEqualTo(expectedCause);
    assertThat(e.getDetails()).isEqualTo(expectedDetails);
  }

  /**
   * Test exception class.
   */
  @SuppressWarnings("UnusedDeclaration")
  public static class TestException extends AbstractPluginException {

    public TestException() {
    }

    public TestException(String message) {
      super(message);
    }

    public TestException(String message, Throwable cause) {
      super(message, cause);
    }

    public TestException(Throwable cause) {
      super(cause);
    }

    public TestException(String message, PluginExceptionDetails details) {
      super(message, details);
    }

    public TestException(String message, Throwable cause, PluginExceptionDetails details) {
      super(message, cause, details);
    }
  }
}
