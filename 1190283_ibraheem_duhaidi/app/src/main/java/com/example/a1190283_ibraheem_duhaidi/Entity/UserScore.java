package com.example.a1190283_ibraheem_duhaidi.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserScore {
    private String name ;
    private String score ;
}
