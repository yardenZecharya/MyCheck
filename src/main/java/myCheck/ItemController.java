package main.java.myCheck;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@RestController
public class ItemController {


    @RequestMapping("/items")
    public JSONArray getItemRecommendations(@RequestParam(value="itemID") long itemID) {
    	
    	final String url = 
    			"http://mycheckjavaexam-env.eu-west-1.elasticbeanstalk.com/api/v1/recommend/franco?item={itemID}";
    	RestTemplate restTemplate =  new RestTemplate();
    	
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("itemID", String.valueOf(itemID));
    	
    	
    	JSONArray result = restTemplate.getForObject(url, JSONArray.class, params);
    	
    	return result;

    }
    
    @RequestMapping("/yarden")
    public JSONObject getItem() {
    	
    	/*final String url = 
    			"http://mycheckjavaexam-env.eu-west-1.elasticbeanstalk.com/api/v1/recommend/franco?item={itemID}";
    	RestTemplate restTemplate =  new RestTemplate();
    	
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("itemID", String.valueOf(itemID));
    	
    	
    	JSONArray result = restTemplate.getForObject(url, JSONArray.class, params);
    	*/
    	JSONObject j = new JSONObject();
    	j.appendField("yarden", "try");
    	
    	return j;

    }
    
    @RequestMapping("/itemsRecusive")
    public JSONArray getItemrecursiveRecommendations(@RequestParam(value="itemID") long itemID,
    												 @RequestParam(value="depth") int depth) {
    	

    	JSONArray startJson = new JSONArray();
    	
    	JSONObject startObject = new JSONObject();
    	startObject.appendField("itemID", itemID);
    	
    	startJson.add(startObject);
    	
    	return fetchAndPrint(startJson, depth);
    }
    
     
    private JSONArray fetchAndPrint(JSONArray itemsList, int depth) {
    
    	JSONArray finalResults = itemsList;
    	
    	if(depth > 0) {
    		JSONArray result = new JSONArray();
    	    		
    		for (Object object : itemsList) {
    			JSONObject  currItem = new JSONObject();
    			HashMap<String, String> currObject = (HashMap<String, String>) object;
    			for (String o : currObject.keySet()) {
					currItem.appendField(o, currObject.get(o));
				}
    			
    			long currID = Long.valueOf(String.valueOf(currItem.get("itemID")));
    			
    			JSONArray currRecom = getItemRecommendations(currID);
    			currRecom = fetchAndPrint(currRecom, depth-1);
    			
    			currItem.appendField("Recommendations", currRecom);
    			//fetchAndPrint(getItemRecommendations(currID), depth-1);
    			
    			result.add(currItem);
			}
    		    		
    		finalResults = result;
    	
    		
    	}
    	
    	return finalResults;
    
    }
}