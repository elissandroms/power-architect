package regress;

import junit.framework.*;
import ca.sqlpower.architect.*;

public class ArchitectExceptionTest extends TestCase {

	public ArchitectExceptionTest(String name) {
		super(name);
	}

	public void testGetCause() {
		Exception cause = new Exception("test");
		ArchitectException ex = new ArchitectException("message", cause);
		Throwable result = ex.getCause();
		assertEquals(cause, result);
	}

	public void testGetMessage() {
		String message = "message";
		ArchitectException ex = new ArchitectException(message);
		String result = ex.getMessage();
		assertEquals(message, result);
	}

}
