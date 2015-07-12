package controllers;

import java.util.HashMap;
import java.util.Map;

import models.RescueStatus;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RescueController {
	private String rootUrl = "https://88d800c0.ngrok.io";
	private String chatLink = "https://plus.google.com/hangouts/_/gwyoi6embwxabzvcibxauszbaea";
	private RescueStatus rescueStatus = new RescueStatus();
	private long id = 0;
	
	@RequestMapping("/yes")
	public ResponseEntity<Map<Long, Boolean>> rescue(@RequestParam(value = "rescueId", defaultValue = "") long rescueId) {
		boolean result = rescueStatus.rescue(rescueId);
		if (!result) {
			return new ResponseEntity<Map<Long,Boolean>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<Map<Long,Boolean>>(rescueStatus.getStatus(), HttpStatus.OK);	
		}
	}
	
	@RequestMapping("/no")
	public void deny() {} // No-op
	
	@RequestMapping("/isRescued")
	public ResponseEntity<Map<String, String>> isRescued(@RequestParam(value = "rescueId", defaultValue = "") long rescueId) {
		Boolean result = rescueStatus.isRescued(rescueId);
		Map<String, String> returnVal = new HashMap<>();
		returnVal.put("pickedUp", result + "");
		if (result == null) {
			return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR); 
		} else {
			return new ResponseEntity<Map<String, String>>(returnVal, HttpStatus.OK);	
		}
	}
	
	@RequestMapping("/getLinks")
	public ResponseEntity<Map<String, String>> getLinks() {
		Map<String, String> message = new HashMap<>();
		long thisId = id++;
		rescueStatus.registerRescue(thisId);
		message.put("id", "" + thisId);
		message.put("confirmLink", rootUrl + "/yes?rescueId=" + thisId);
		message.put("denyLink", rootUrl + "/no");
		message.put("chatLink", chatLink);
		return new ResponseEntity<Map<String, String>>(message, HttpStatus.OK);
	}
	
	@RequestMapping("/clear")
	public ResponseEntity<Map<Long,Boolean>> clear() {
		rescueStatus.clear();
		return new ResponseEntity<Map<Long,Boolean>>(HttpStatus.OK);
	}
}
