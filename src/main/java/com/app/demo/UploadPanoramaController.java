package com.app.demo;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UploadPanoramaController {
	private final AtomicLong counter = new AtomicLong();
	

	Map<Integer, UploadPanorama> panoramaData = new HashMap<Integer, UploadPanorama>();
	
	  @RequestMapping(value = UploadPanoramaConst.GET_ALL_PANORAMAS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	   public @ResponseBody List<UploadPanorama> listAllUPanoramas() {
		  	List<UploadPanorama> panoramas = new ArrayList<UploadPanorama>();
		  	Set<Integer> panoramaIdKeys = panoramaData.keySet();

		  	for (Integer i : panoramaIdKeys) {
		  		panoramas.add(panoramaData.get(i));
		  	}
		  	
		  	return panoramas;
	    }
	  
	  @RequestMapping(value = UploadPanoramaConst.GET_PANORAMA_BY_ID, method = RequestMethod.GET)
		public @ResponseBody UploadPanorama getPanorama(@PathVariable("id") int panoramaId) {
			return panoramaData.get(panoramaId);
	  }
	  
	  @RequestMapping(value = UploadPanoramaConst.CREATE_PANORAMA, method = RequestMethod.POST)
		public ResponseEntity<String> createPanorama (@RequestBody UploadPanorama panorama) {
		  	
		  Timestamp now = new Timestamp(System.currentTimeMillis());
		  boolean a = true;
		  	if(panoramaData.size() > 0) {
			  	UploadPanorama up = this.getPanorama(panoramaData.size());
			  	long diff = now.getTime() -up.getTmp().getTime() ;
			  	System.out.println(diff);
			  	if(diff > 60000) {
			  		a = false;
			  	} else {
			  		this.addPanorama(panorama);
			  	}
		  	} else {
				this.addPanorama(panorama);
		  	}
		  	
		  	if(a)
		  		return new ResponseEntity<String>(HttpStatus.OK);
		  	else 
		  		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
	  
	  public void addPanorama(UploadPanorama panorama) {
		  Timestamp now = new Timestamp(System.currentTimeMillis());
		  int id = panoramaData.size() + 1;
		  panorama.setTmp(now);
			panorama.setNumberPanoramas(panorama.getNumberPanoramas());
			panorama.setId(id);
			
			panoramaData.put(id, panorama);
		  
	  }
	 
	  @RequestMapping(value = UploadPanoramaConst.GET_STADISTICS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	   public @ResponseBody Stadistics getStadistics() {
		  	Stadistics stadistics =  new Stadistics();
		  	stadistics.setSum(panoramaData.values().stream().mapToInt(i -> i.getNumberPanoramas()).sum());
		  	stadistics.setAvg(panoramaData.values().stream().mapToInt(i -> i.getNumberPanoramas()).average().getAsDouble());
		  	stadistics.setMax(panoramaData.values().stream().mapToInt(i -> i.getNumberPanoramas()).max().getAsInt());
		  	stadistics.setMin(panoramaData.values().stream().mapToInt(i -> i.getNumberPanoramas()).min().getAsInt());
		  	stadistics.setCount(panoramaData.size());
	
		  	return stadistics;
	    }
	  
	  	
		
}
