import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 4/9/2014.
 * This class takes an imgur web page and extracts teh comments from it into an array of strings
 * I am a little worried about how long it takes to work. Each page takes a little bit to process
 * I am thinking about trying to either
 *  -tweak the selenium settings to make it run faster
 *            or
 *  -try using the imgur api to make a better extractor
 *
 *  this will work for now though at least as  a backup or main thing if needed
 */
public class PageParser {
    public static void main(String [] args)
    {
        WebDriver driver = new FirefoxDriver();

    driver.get("http://imgur.com/gallery/WMCTG");

    List<WebElement> elements = driver.findElements(By.cssSelector(".comment span"));


    ArrayList<String> words = new ArrayList<String>();

    //this was a loop i figured out that would get allt he comments
    //there might be a better way using a selector or somthing to get
    //it from the elements in the first place

    for (int i = 3; i < elements.size() - 7; i += 5) {
        String comment = elements.get(i).getText();
        for (String word : comment.split("\\W+")) {
            word = word.replaceAll("[^A-Za-z]+", "");
            if (!word.equals(""))
                words.add(word.toLowerCase());
        }
    }

    //System.out.println(words);


    //just making sure i can convert to array
    String wordArray[] = new String[words.size()];
    wordArray = words.toArray(wordArray);
    for (String w : wordArray)
        System.out.println(w);


    driver.quit();

    }

}