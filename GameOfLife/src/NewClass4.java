import java.util.*;

public class NewClass4 {
    public static void main(String[] args)
    {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        HashMap<Integer,Integer> map=new HashMap<Integer, Integer>();
        map.put(1, 50);
        map.put(2, 60);
        map.put(3, 30);
        map.put(4, 60);
        map.put(5, 60);
        int maxValueInMap=(Collections.max(map.values()));  // This will return max value in the Hashmap
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {  // Itrate through hashmap
            if (entry.getValue()==maxValueInMap) {
                list.add(entry.getKey());     // Print the key with max value
            }
        }
        int id = random.nextInt(list.size());

    }
}