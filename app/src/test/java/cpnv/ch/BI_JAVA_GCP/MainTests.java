package cpnv.ch.BI_JAVA_GCP;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MainTests {


	@Test
	void contextLoads() {
	}

	@Test
	public void testAdd() {
		assertEquals(42, Integer.sum(19, 23));
	}
	@Test
	public void testDivide() {
		assertThrows(ArithmeticException.class, () -> {
			Integer.divideUnsigned(42, 0);
		});
	}
}
