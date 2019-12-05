package sample;

import java.util.ArrayList;

public class APIKeys {
    public static ArrayList<String> ID = new ArrayList<String>();
    public static ArrayList<String> Key = new ArrayList<String>();
    public static int place = 0;

    public APIKeys(){
        addToArrayList();
    }

    public static String ClientID(){
        try {
            return ID.get(place);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("out of keys");
        }
        return "";
    }

    public static String ClientKey(){
        try {
            return Key.get(place);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("out of keys");
        }
        return "";
    }

    public static void addToArrayList(){
        ID.add("jqI4SN5g22BSyrI7rBIFb");
        ID.add("Q8oS9ELk3U5xxClZQzYNk");
        ID.add("PjPsvPeDsDwV2Bv3Rkj5L");
        ID.add("gM17D2FbxaQsBZl8jBfN1");
        Key.add("YqPdilijMvTmHzQ01vEGvbWo95iUmIFaw7L47fXR");
        Key.add("FSS8x7hLnFJS4VZ7h6XBRcgYuCKDKLcfW7bfF9iU");
        Key.add("2gfSilTjsLKSAErTozk2U91wpLl7zD4p7Luj3IuU");
        Key.add("K6yHaNfUKiQdO5xfq6cq4yKwYErn1sbGQHJF4mkP");
    }

    public static void nextKey(){
        place++;
    }
}
