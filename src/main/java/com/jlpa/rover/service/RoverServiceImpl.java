package com.jlpa.rover.service;

import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlpa.rover.model.Direction;
import com.jlpa.rover.model.Grid;
import com.jlpa.rover.model.Point;
import com.jlpa.rover.model.Rover;

@Service
public class RoverServiceImpl implements RoverService {

	@Autowired
	private Rover rover;

	@Autowired
	private Grid grid;

	@Override
	public Rover getRover() {
		if (rover != null)
			return rover;
		return null;
	}

	@Override
	public Rover setRoverLocationAndDirection(Point location, Direction direction) {
		try {
			if (rover != null && grid != null && validatePointInGrid(location)) {
				if (location != null)
					rover.setLocation(location);
				if (direction != null)
					rover.setDirection(direction);
			}
			return rover;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Grid getGrid() {
		if (grid != null)
			return grid;
		return null;
	}

	@Override
	public Boolean setGridDimension(Integer x_Length, Integer y_Length) {
		try {
			if (validateGridChangeWithRover(x_Length, y_Length) && grid != null) {
				grid.setX_Length(x_Length);
				grid.setY_Length(y_Length);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Boolean validateGridChangeWithRover(Integer x_Length, Integer y_Length) {
		try {
			// Validamos que al cambiar el tamaño del grid el rover no queda fuera de los
			// limites
			if (x_Length == null || y_Length == null) {
				return false;
			} else if (rover != null && rover.getLocation() != null && rover.getLocation().getXCoordenate() != null
					&& rover.getLocation().getXCoordenate() > x_Length)
				return false;
			else if (rover != null && rover.getLocation() != null && rover.getLocation().getYCoordenate() != null
					&& rover.getLocation().getYCoordenate() > y_Length)
				return false;
			else
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean addObstacleToGrid(Point obstacleLocation) {
		try {
			// Validamos que el obstaculo no venga nulo y que está dentro del grid
			if (grid != null && obstacleLocation != null && obstacleLocation.getXCoordenate() != null
					&& obstacleLocation.getXCoordenate() < grid.getX_Length()
					&& obstacleLocation.getYCoordenate() != null
					&& obstacleLocation.getYCoordenate() < grid.getY_Length()) {
				if (grid.getObstacles() == null) {
					grid.setObstacles(new LinkedHashSet<Point>());
				}
				grid.getObstacles().add(obstacleLocation);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean deleteObstacleFromGrid(Point obstacleLocation) {
		try {
			if (obstacleLocation != null && obstacleLocation.getXCoordenate() != null
					&& obstacleLocation.getYCoordenate() != null && grid != null && grid.getObstacles() != null
					&& grid.getObstacles().contains(obstacleLocation)) {
				grid.getObstacles().remove(obstacleLocation);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean deleteAllObstacleListFromGrid() {
		try {
			if (grid != null) {
				grid.setObstacles(null);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String moveRover(String commands) throws Exception {
		
			if (validateRover() && validateGrid() && commands != null && !commands.equals("")) {
				for (char command : commands.toCharArray()) {
					switch (Character.toLowerCase(command)) {
					case 'f': {
						Point nextPoint = moveForward();
						if (grid.getObstacles().contains(nextPoint)) {
							return this.getPosition(true);
						}
						rover.setLocation(nextPoint);
						break;
					}
					case 'b': {
						Point nextPoint = moveBackward();
						if (grid.getObstacles().contains(nextPoint)) {
							return this.getPosition(true);
						}							
						rover.setLocation(nextPoint);
						break;
					}
					case 'l':
						rover.turnLeftDirection();
						break;
					case 'r':
						rover.turnRightDirection();
						break;
					default:
						throw new Exception("Command " + command + " is unknown.");
					}
				}
			}
			return this.getPosition(false);
		
	}

	private Point moveBackward() {
		return calculateNextPoint(-1);

	}

	private Point moveForward() {
		return calculateNextPoint(1);
	}

	private Point calculateNextPoint(Integer stepSize) {
		Point nextPoint = new Point();
		if (rover.getDirection().equals(Direction.N) || rover.getDirection().equals(Direction.S)) {
			nextPoint.setXCoordenate(rover.getLocation().getXCoordenate());
			Integer yCoord;
			if (rover.getDirection().equals(Direction.N)) {
				yCoord = rover.getLocation().getYCoordenate() + stepSize;
			} else {
				yCoord = rover.getLocation().getYCoordenate() + (stepSize * -1);
			}
			nextPoint.setYCoordenate(yCoord);
		} else {
			nextPoint.setYCoordenate(rover.getLocation().getYCoordenate());
			Integer xCoord;
			if (rover.getDirection().equals(Direction.E)) {
				xCoord = rover.getLocation().getXCoordenate() + stepSize;
			} else {
				xCoord = rover.getLocation().getXCoordenate() + (stepSize * -1);
			}
			nextPoint.setXCoordenate(xCoord);
		}
		return updatePointWithBounds(nextPoint);
	}

	private Point updatePointWithBounds(Point nextPoint) {
		if (nextPoint.getXCoordenate() < 0)
			nextPoint.setXCoordenate(grid.getX_Length());
		if (nextPoint.getXCoordenate() > grid.getX_Length())
			nextPoint.setXCoordenate(0);
		if (nextPoint.getYCoordenate() < 0)
			nextPoint.setYCoordenate(grid.getY_Length());
		if (nextPoint.getYCoordenate() > grid.getY_Length())
			nextPoint.setYCoordenate(0);
		return nextPoint;
	}

	private boolean validateGrid() {
		if (grid != null && grid.getX_Length() != null && grid.getY_Length() != null)
			return true;
		return false;
	}

	private boolean validateRover() {
		if (rover != null && rover.getDirection() != null && rover.getLocation() != null
				&& rover.getLocation().getXCoordenate() != null && rover.getLocation().getYCoordenate() != null)
			return true;
		return false;
	}

	private boolean validatePointInGrid(Point location) {
		if (grid.getX_Length() == null || grid.getY_Length() == null || location.getXCoordenate() < 0
				|| location.getXCoordenate() > grid.getX_Length() || location.getYCoordenate() < 0
				|| location.getYCoordenate() > grid.getY_Length()) {
			return false;
		}
		return true;
	}

	public String getPosition(Boolean foundObstacle) {
		String status = "";
		if (foundObstacle) {
			status = " OBSTACLE";
		}
		return rover.getLocation().getXCoordenate() + " X " + rover.getLocation().getYCoordenate() + " "
				+ rover.getDirection() + status;
	}
}
