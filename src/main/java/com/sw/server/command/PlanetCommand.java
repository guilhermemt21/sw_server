package com.sw.server.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlanetCommand {
    private String name;
    private String climate;
    private String terrain;
}
