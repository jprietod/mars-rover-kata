package com.jlpa.rover.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jlpa.rover.model.Direction;
import com.jlpa.rover.model.Grid;
import com.jlpa.rover.model.Point;
import com.jlpa.rover.model.Rover;
import com.jlpa.rover.service.RoverService;

@RestController
public class RoverRestController {

	@Autowired
	private RoverService roverService;

	@GetMapping(value = "getRover")
	public ResponseEntity<Rover> getRover() {
		try {
			return ResponseEntity.ok(roverService.getRover());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PutMapping(value = "setRoverLocationAndDirection/{xCoord}/{yCoord}/{direction}")
	public ResponseEntity<Rover> setRoverLocationAndDirection(@PathVariable(value = "xCoord") Integer xCoord,
			@PathVariable(value = "yCoord") Integer yCoord, @PathVariable(value = "direction") Direction direction) {
		Point location = new Point();
		location.setXCoordenate(xCoord);
		location.setYCoordenate(yCoord);
		try {
			return ResponseEntity.ok(roverService.setRoverLocationAndDirection(location, direction));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping(value = "getGrid")
	public ResponseEntity<Grid> getGrid() {
		try {
			return ResponseEntity.ok(roverService.getGrid());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PutMapping(value = "setGridDimension/{x_Length}/{y_Width}")
	public ResponseEntity<Boolean> setGridDimension(@PathVariable(value = "x_Length") Integer x_Length,
			@PathVariable(value = "y_Width") Integer y_Width) {
		try {
			return ResponseEntity.ok(roverService.setGridDimension(x_Length, y_Width));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping(value = "addObstacleToGrid/{xCoord}/{yCoord}")
	public ResponseEntity<Boolean> addObstacleToGrid(@PathVariable(value = "xCoord") Integer xCoord,
			@PathVariable(value = "yCoord") Integer yCoord) {
		Point location = new Point();
		location.setXCoordenate(xCoord);
		location.setYCoordenate(yCoord);
		try {
			return ResponseEntity.ok(roverService.addObstacleToGrid(location));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@DeleteMapping(value = "deleteObstacleFromGrid/{xCoord}/{yCoord}")
	public ResponseEntity<Boolean> deleteObstacleFromGrid(@PathVariable(value = "xCoord") Integer xCoord,
			@PathVariable(value = "yCoord") Integer yCoord) {
		Point location = new Point();
		location.setXCoordenate(xCoord);
		location.setYCoordenate(yCoord);
		try {
			return ResponseEntity.ok(roverService.deleteObstacleFromGrid(location));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@DeleteMapping(value = "deleteAllObstacleListFromGrid")
	public ResponseEntity<Boolean> deleteObstacleFromGrid() {
		try {
			return ResponseEntity.ok(roverService.deleteAllObstacleListFromGrid());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PutMapping(value = "moveRover/{commands}")
	public ResponseEntity<String> moveRover(@PathVariable(value = "commands") String commands) {
		try {
			return ResponseEntity.ok(roverService.moveRover(commands));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
