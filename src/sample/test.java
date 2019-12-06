package sample;

public class test {
    public static void main(String[] args) {
        test t = new test();
        String city = "new york city/nyc";
        if (city.indexOf('/') > 0){
            city = city.substring(0, city.indexOf('/'));
        }
        String state = "la".toUpperCase(); // capitalize State
        String cityState = city + "," + " " + state;
        cityState = t.capitalizeWords(cityState);
        System.out.println(cityState);
    }

    public String capitalizeWords(String str){
        String words[]=str.split("\\s");
        String capitalizeWord="";
        for(String w:words){
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst+" ";
        }
        return capitalizeWord.trim();
    }
}
