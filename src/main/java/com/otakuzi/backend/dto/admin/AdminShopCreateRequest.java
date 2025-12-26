package com.otakuzi.backend.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter // 관리자 페이지에서 폼 데이터 바인딩을 위해 Setter 사용 (혹은 @Data)
@NoArgsConstructor
public class AdminShopCreateRequest {

    @NotBlank(message = "상점 이름은 필수입니다.")
    private String name;

    private String phone;
    private String placeUrl;

    @NotBlank(message = "주소(지번)는 필수입니다.")
    private String addressName;

    @NotBlank(message = "도로명 주소는 필수입니다.")
    private String roadAddressName;

    @NotBlank(message = "경도(x)는 필수입니다.")
    private String x;

    @NotBlank(message = "위도(y)는 필수입니다.")
    private String y;

    @NotEmpty(message = "카테고리는 최소 1개 이상 선택해야 합니다.")
    private List<Long> categoryIds;
}