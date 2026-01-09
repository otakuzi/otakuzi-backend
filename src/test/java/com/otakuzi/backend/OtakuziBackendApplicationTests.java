package com.otakuzi.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class otakuziBackendApplicationTests {

	@Test
	void 고의로_실패하는_테스트() {
		if (true) {
			throw new RuntimeException("이 빌드는 실패해야 합니다!ㅇㅇ");
		}
	}

}
