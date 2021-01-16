package com.jlpa.rover.service;

import com.jlpa.rover.model.Direction;
import com.jlpa.rover.model.Grid;
import com.jlpa.rover.model.Point;
import com.jlpa.rover.model.Rover;

public interface RoverService {

	public Rover getRover();

	public Rover setRoverLocationAndDirection(Point location, Direction direction);

	public Grid getGrid();	

	public Boolean setGridDimension(Integer x_Length, Integer y_Width);

	public Boolean addObstacleToGrid(Point obstacleLocation);

	public Boolean deleteObstacleFromGrid(Point location);

	public Boolean deleteAllObstacleListFromGrid();

	public String moveRover(String command) throws Exception;
}
