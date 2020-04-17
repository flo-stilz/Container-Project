import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class dataMap {
    
    Map <String, ArrayList<Integer>> dataMap = new HashMap<String, ArrayList<Integer>>();
    ArrayList<String> specificIDList = new ArrayList<String>();
}


public class tempDataMap extends dataMap {

    public boolean doesKeyExist (String uniqueID) {
        return specificIDList.contains(uniqueID);
    }
}

//instead of using datamap - possibly iterate over containerList.

//for O1
//iterate through list of Journeys
//pick out all containers for a specific journey and display their measurements