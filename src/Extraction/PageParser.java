package Extraction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

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
	
	private static WebDriver driver;
    public static void main(String [] args) throws Exception
    {
    	
    	DesiredCapabilities dCaps;
    	dCaps = new DesiredCapabilities();
    	dCaps.setJavascriptEnabled(true);
    	dCaps.setCapability("takesScreenshot",false);
    	
    	//to get phantom driver to work download phantomjs
    	//add to path
    	//start phantomjs in driver mode (phantomjs --webdriver= 8910)
        driver = new PhantomJSDriver(dCaps);
        
    	Queue<String> urlQueue = new LinkedList<String>();
    	
    	driver.get("http://imgur.com/gallery");

    	
    	JavascriptExecutor jse = (JavascriptExecutor)driver;
    	
    	//at 10 was 537
    	//at 50 was 912
    	//hard to get past aorund 907 need to try clicking link at this point
    	for(int i=0;i<60;i++)
    	{
    		jse.executeScript("window.scrollTo(0, document.body.scrollHeight);", "");
    		Thread.sleep(100);
    	}
    	List<WebElement> elements = driver.findElements(By.cssSelector(".posts img"));
    	
    	
    
    	for(int i=0;i<elements.size();i++)
    	{
    		System.out.println(i+") "+elements.get(i).getAttribute("src"));
    	}
    	
    	
    	
    	
    	
    	/*
    	
        
        for(int i=0;i<1000;i++)
        {
    	ImgurImage img = ExtractImage("http://imgur.com/gallery/WMCTG");
    
    	for(String word : img.comments)
    		System.out.println(i+") "+word);
    		
    	
        }
        */
    	
    	
    	 driver.quit();


    }
    
    
    public static ImgurImage ExtractImage(String url)
    {
    	
    

    
    driver.get(url);

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

        
   
    
    
    
    ImgurImage image = new ImgurImage();
    image.uri = "somthing";
    image.comments =wordArray;
    
    return image;
    }
    
    

}

class ImgurImage
{
	public String uri;
	public String[] comments;
}
