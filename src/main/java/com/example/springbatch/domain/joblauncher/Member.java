package com.example.springbatch.domain.joblauncher;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Member {

    private Long id;

    public Member(Long id) {
        this.id = id;
    }
}
