package com.jlpa.rover.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
public @Data class Grid {
	Integer x_Length;
	Integer y_Length;
	//Usamos set y no un list porque el set no puede tener elementos iguales
	Set<Point> obstacles= new HashSet<Point>(); 
}
