package com.otakuzi.backend.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class BadWordValidator {

    private final Set<String> badWords = new HashSet<>();
    private static final String[] BAD_WORD_FILES = {
            "bad-words-ko.txt",
            "bad-words-en.txt"
    };

    @PostConstruct
    public void init() {
        for (String fileName : BAD_WORD_FILES) {
            loadWordsFromFile(fileName);
        }

        log.info("금지어 불러오기 최종 완료 : 총{}개 단어", badWords.size());
    }

    private void loadWordsFromFile(String fileName) {
        try {
            ClassPathResource resource = new ClassPathResource(fileName);

            if (!resource.exists()) {
                log.warn("파일을 찾을 수 없음: {}", fileName);
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                reader.lines()
                        .map(String::trim)
                        .filter(word -> !word.isEmpty())
                        .forEach(word -> {
                            badWords.add(word.toLowerCase());
                        });
            }

            log.info("파일 로딩 성공: {}", fileName);
        } catch (IOException e) {
            log.error("파일 불러오기 중 오류 발생: {}", fileName, e);
        }
    }

    public void validate(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            return;
        }

        String lowerNickname = nickname.trim().toLowerCase();

        if (badWords.contains(lowerNickname)) {
            throw new IllegalArgumentException("사용할 수 없는 닉네임입니다.");
        }
    }
}
