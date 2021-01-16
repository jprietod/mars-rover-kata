package com.jlpa.rover.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
public @Data class Rover {
	Point location;
	Direction direction;

	public void turnLeftDirection() {
		switch (direction.name()) {
		case "N":
			setDirection(Direction.W);
			break;
		case "W":
			setDirection(Direction.S);
			break;
		case "S":
			setDirection(Direction.E);
			break;
		case "E":
			setDirection(Direction.N);
			break;
		default:
			break;
		}

	}

	public void turnRightDirection() {
		switch (direction.name()) {
		case "N":
			setDirection(Direction.E);
			break;
		case "E":
			setDirection(Direction.S);
			break;
		case "S":
			setDirection(Direction.W);
			break;
		case "W":
			setDirection(Direction.N);
			break;
		default:
			break;
		}

	}
}
