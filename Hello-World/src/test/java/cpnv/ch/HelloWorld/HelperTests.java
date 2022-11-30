package cpnv.ch.HelloWorld;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelperTests {



	@BeforeAll
	void test_BeforeAll() {
	}
	@BeforeEach
	void test_BeforeEach() {
	}
	@AfterAll
	void test_AfterAll() {
	}
	@AfterEach
	void test_AfterEach() {
	}



	@Test
	public void test_DoesExist_ExistsCase_Success() {
		//given

		//when

		//then
	}

	@Test
	public void test_DoesExist_NotExists_Success() {
		//given

		//when

		//then
	}

	@Test
	public void test_CreateObject_AlreadyExists_ThrowException() {
		//given

		//when

		//then
	}

	@Test
	public void test_CreateObject_PathNotExists_Success() {
		//given

		//when

		//then
	}

	@Test
	public void test_DownloadObject_NominalCase_Success() {
		//given

		//when

		//then
	}

	@Test
	public void test_DownloadObject_NotExists_ThrowException() {
		//given

		//when

		//then
	}

	@Test
	public void test_PublishObject_NominalCase_Success() {
		//given

		//when

		//then
	}

	@Test
	public void test_PublishObject_ObjectNotFound_ThrowException() {
		//given

		//when

		//then
	}

}
