package com.sw.server.planet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Planet {
    private Long id;
    private String name;
    private String terrain;
    private String climate;
    private Integer apparitions;
}
