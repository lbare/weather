package sample;

public class test {
    public static void main(String[] args) {
        String city = "new orleans"; // city = "new orleans"
        city = city.substring(0, 1).toUpperCase() + city.substring(1); // city = "New orleans"
        city = city.substring(city.indexOf(" "),city.indexOf(" ") + 1).toUpperCase() +
                city.substring(city.indexOf(" ") + 1); // city = "new orleans"
        String state = "la".toUpperCase(); // state = "LA"
        //System.out.println(city + "," + " " + state);
        String test = "test test";
        int wordCount = 1;
        for (int i = 0; i < test.length(); i++) {
            if (test.charAt(i) == ' '){
                wordCount++;
            }
        }
        System.out.println(wordCount);
        /*test = test.substring(test.indexOf(" ") + 1,test.indexOf(" ") + 2).toUpperCase() +
                test.substring(test.indexOf(" ") + 2);

        System.out.println(test);*/

    }
}
