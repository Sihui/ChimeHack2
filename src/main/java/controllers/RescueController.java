package controllers;

import java.util.Map;

import models.RescueStatus;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RescueController {
	private RescueStatus rescueStatus = new RescueStatus();
	
	@RequestMapping("/registerRescue")
	public ResponseEntity<Map<Long, Boolean>> registerRescue(@RequestParam(value="rescueId", defaultValue = "") long rescueId) {
		boolean result = rescueStatus.registerRescue(rescueId);
		if (!result) {
			return new ResponseEntity<Map<Long,Boolean>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<Map<Long, Boolean>>(rescueStatus.getStatus(), HttpStatus.OK);			
		}
	}
	
	@RequestMapping("/rescue")
	public ResponseEntity<Map<Long, Boolean>> rescue(@RequestParam(value = "rescueId", defaultValue = "") long rescueId) {
		boolean result = rescueStatus.rescue(rescueId);
		if (!result) {
			return new ResponseEntity<Map<Long,Boolean>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<Map<Long,Boolean>>(rescueStatus.getStatus(), HttpStatus.OK);	
		}
	}
	
	@RequestMapping("/isRescued")
	public ResponseEntity<Boolean> isRescued(long rescueId) {
		Boolean result = rescueStatus.isRescued(rescueId);
		if (result == null) {
			return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR); 
		} else {
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);	
		}
	}
	
	@RequestMapping("/clear")
	public ResponseEntity<Map<Long,Boolean>> clear() {
		rescueStatus.clear();
		return new ResponseEntity<Map<Long,Boolean>>(HttpStatus.OK);
	}
}
