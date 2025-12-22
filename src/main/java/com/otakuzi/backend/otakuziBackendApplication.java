package com.otakuzi.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.lang.reflect.Method;

@SpringBootApplication
public class otakuziBackendApplication {

	// 🔥 [수정] import 문조차 다 지우고, 100% 문자열로만 로딩합니다.
	// 빨간 줄이 뜰 수가 없는 구조입니다.
	static {
		try {
			// 1. 필요한 클래스들을 이름으로 찾음
			Class<?> managerClass = Class.forName("software.amazon.jdbc.ConnectionPluginManager");
			Class<?> factoryInterface = Class.forName("software.amazon.jdbc.ConnectionPluginFactory");
			Class<?> pluginClass = Class.forName("software.amazon.jdbc.plugin.secretsmanager.SecretsManagerPluginFactory");

			// 2. 플러그인 인스턴스 생성
			Object factoryInstance = pluginClass.getDeclaredConstructor().newInstance();

			// 3. registerPlugin 메서드를 찾아서 실행 (타입 캐스팅 없이 실행)
			Method registerMethod = managerClass.getMethod("registerPlugin", String.class, factoryInterface);
			registerMethod.invoke(null, "secretsManager", factoryInstance);

			System.out.println("✅ [강제 등록 성공] SecretsManager Plugin이 등록되었습니다.");
		} catch (Exception e) {
			// 빌드 시점에 라이브러리가 없어도 앱은 켜지도록 로그만 남김
			System.err.println("⚠️ [강제 등록 패스] 로컬이거나 라이브러리가 없습니다: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(otakuziBackendApplication.class, args);
	}

}
