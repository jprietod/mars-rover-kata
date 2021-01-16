package com.jlpa.rover.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jlpa.rover.model.Direction;
import com.jlpa.rover.model.Point;

@SpringBootTest
public class RoverServiceTest {

	
	@Autowired
	private RoverService roverService;

	private Point initialPoint;

	@BeforeEach
	public void beforeRoverTest() {
		roverService.setGridDimension(9, 9);
		initialPoint = new Point(1, 2);
		roverService.setRoverLocationAndDirection(initialPoint, Direction.N);
	}

	@Test
	public void newInstanceShouldSetRoverCoordinatesAndDirection() {
		assertThat(roverService.getRover().getLocation()).usingRecursiveComparison().isEqualTo(initialPoint);
	}

	@Test
	public void moveRoverShouldMoveForwardWhenCommandIsF() throws Exception {
		int expected = initialPoint.getYCoordenate() + 1;
		roverService.moveRover("F");
		assertThat(roverService.getRover().getLocation().getYCoordenate()).isEqualTo(expected);
	}

	@Test
	public void moveRoverShouldMoveBackwardWhenCommandIsB() throws Exception {
		int expected = initialPoint.getYCoordenate() - 1;
		roverService.moveRover("B");
		assertThat(roverService.getRover().getLocation().getYCoordenate()).isEqualTo(expected);
	}

	@Test
	public void moveRoverShouldTurnLeftWhenCommandIsL() throws Exception {
		roverService.moveRover("L");
		assertThat(roverService.getRover().getDirection()).isEqualTo(Direction.W);
	}

	@Test
	public void moveRoverShouldTurnRightWhenCommandIsR() throws Exception {
		roverService.moveRover("R");
		assertThat(roverService.getRover().getDirection()).isEqualTo(Direction.E);
	}

	@Test
	public void moveRoverShouldIgnoreCase() throws Exception {
		roverService.moveRover("r");
		assertThat(roverService.getRover().getDirection()).isEqualTo(Direction.E);
	}

	@Test
	public void moveRoverShouldThrowExceptionWhenCommandIsUnknown() throws Exception {
		 Assertions.assertThrows(Exception.class, () -> {
			 roverService.moveRover("X");
			  });		
	}

	@Test
	public void receiveCommandsShouldBeAbleToReceiveMultipleCommands() throws Exception {
		int expected = initialPoint.getXCoordenate() + 1;
		roverService.moveRover("RFR");
		assertThat(roverService.getRover().getLocation().getXCoordenate()).isEqualTo(expected);
		assertThat(roverService.getRover().getDirection()).isEqualTo(Direction.S);
	}

	@Test
	public void receiveCommandShouldWhatFromOneEdgeOfTheGridToAnother() throws Exception {
		int expected = roverService.getGrid().getX_Length()  + initialPoint.getXCoordenate() - 2;
		roverService.moveRover("LFFF");
		assertThat(roverService.getRover().getLocation().getXCoordenate()).isEqualTo(expected);
	}

	@Test
	public void receiveCommandsShouldStopWhenObstacleIsFound() throws Exception {
		int expected = initialPoint.getXCoordenate() + 1;
		roverService.addObstacleToGrid(new Point(expected + 1, initialPoint.getYCoordenate()));
		roverService.setRoverLocationAndDirection(initialPoint, Direction.E);
		roverService.moveRover("FFFRF");
		assertThat(roverService.getRover().getLocation().getXCoordenate()).isEqualTo(expected);
		assertThat(roverService.getRover().getDirection()).isEqualTo(Direction.E);
	}

	@Test
	public void positionShouldReturnXYAndDirection() throws Exception {
		assertThat(roverService.moveRover("LFFFRFF")).isEqualTo("8 X 4 N");
	}

	@Test
	public void positionShouldReturnNokWhenObstacleIsFound() throws Exception {
		roverService.addObstacleToGrid(new Point(initialPoint.getXCoordenate() + 1, initialPoint.getYCoordenate()));
		roverService.setRoverLocationAndDirection(initialPoint, Direction.E);
		assertThat(roverService.moveRover("F")).endsWith(" OBSTACLE");
	}
}
