package com.jlpa.rover.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public @Data class Point {
	Integer xCoordenate;
	Integer yCoordenate;
}
