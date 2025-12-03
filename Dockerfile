# Multi-stage build로 이미지 크기 최적화
FROM gradle:8.5-jdk17 AS build

# 작업 디렉토리를 /app으로 설정
WORKDIR /app

# Gradle 의존성 캐싱 / 소스 코드보다 먼저 로드
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# 의존성 다운로드
RUN gradle dependencies --no-daemon

# 소스 코드 복사
COPY src ./src

# 빌드 실행 / GitHub Actions에서 이미 test 진행하므로 스킵함
RUN gradle build --no-daemon -x test

# 실행 스테이지 - ARM64 지원하는 이미지 사용
FROM amazoncorretto:17-alpine
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 환경변수 설정
ENV SPRING_PROFILES_ACTIVE=prod

# 포트 노출
EXPOSE 8080

# 헬스체크 (alpine은 wget 사용)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# JAR 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]