package restassuredTests;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;


/*

Given I like to surf in any of 2 beaches <Out of top ten> of Sydney
And I only like to surf on <Monday & Friday> in next 16 days
When I look up the the weather forecast for the next 16 days with
POSTAL CODES
Then I check to if see the temperature is between <12℃ and 30℃>
And I check wind speed to be between 3 and 9
And I check to see if UV index is <= 12
And I Pick best suitable spot out of top two spots, based upon suitable
weather forecast for the day

Output
Show two suitable spots in the results
Display their, co-ordinates or postal codes


*/


public class WeatherForecast {

	//**
	public static String apiKey = "d0dcd53eacee408d9cd1d3de37c4bbd7";
	public static String apiUrl = "https://api.weatherbit.io/v2.0/forecast/daily";
	public static String countryCode = "AU";
	public static int numberOfBeaches = 10;
	public static int daysOfForecast = 16;
	public static String[] dayToSurf = {"Monday", "Friday"};
			
	//d0dcd53eacee408d9cd1d3de37c4bbd7
	
	//bf90f092e2c842acbb1732c7fc6ffccc
	
	public  List<String>beachs = new ArrayList<>();
	
	public List<String>cityName = new  ArrayList<>();
	public Beach[] beache_recorder = new Beach[numberOfBeaches];
	
	
			
	@BeforeClass
	
	public void beachesDataInput()
	{
			// data for beach name and beach city.
		
		beachs.add("Bondi Beach");
		beachs.add("Manly Beach"); 
		beachs.add("Shelly Beach"); 
		beachs.add("Palm Beach"); 
		beachs.add("Balmoral Beach");
		beachs.add("Bronte Beach");
		beachs.add("Bilgola Beach");
		beachs.add("Shark Beach");
		beachs.add("Coogee Beach");
		beachs.add("Freshwater Beach");
		
		cityName.add("North Bondi");
		cityName.add("Manly");		
		cityName.add("Ettalong");		
		cityName.add("Newport");
		cityName.add("Holmesville");
		cityName.add("Bronte");
		cityName.add("Newport");
		cityName.add("Maclean");
		cityName.add("Coogee");
		cityName.add("Curl Curl");

	}
	
	
	// checking the api call usage remaining in provide key
	// param key = apikey
	// param country = provide country code
	// param city = first city name which is North Bondi
	@Test	
	public void apiUsageCheck()
	{
	
		String count = 
		given()		   
		   .queryParam("key", apiKey)
		.when()
		   .get("https://api.weatherbit.io/v2.0/subscription/usage")
		.then()
		   .statusCode(200)
		   .extract().path("calls_remaining").toString();
		
		
		System.out.println("Useage remaining: " + count);
	
		
	}
	
	
	
	// Test API status code
	// param key = apikey
	// param country = provide count
	// param city = first city name which is North Bondi
	@Test
	public void testWeatherAPIStatus()
	{
		given()		   
		   .queryParam("key", apiKey)
		   .queryParam("country", countryCode)
		   .queryParam("city", cityName.get(0))
		.when()
		   .get(apiUrl)
		.then()
		   .statusCode(200);
		
	}
	
	// General output API response log with first beach city
	// param key = apikey
	// param country = provide count
	// param city = first city name which is North Bondi
	@Test
	public void testLog()
	{	
		
		given()
		   .queryParam("key", apiKey)
		   .queryParam("country", countryCode)
		   .queryParam("city", cityName.get(0))
		.when()
		   .get(apiUrl)
		.then()   
		   .log().all();
		
		
	}

	// Test API status code
	// param key = apikey
	// param country = provide count
	// param city = list of beachs city name
	
	// verify condition:
	// 1. dateTime is Monday or Friday
	// 2. low_temp > 12 && max_temp <30
	// 3. win speed between 3 to 0
	// 4. Uv <=12
	
	
	// Analysis 
	// Sorting base on the numbers of day which satisfy above conditions
	
	// Report and result
	// Report all the beach name and the dates which satify above conditions
	// result is the top 2 beachs
		
	@Test
	public void verifyTemp() 		
	{			
		
	// check the day is Monday & Friday	
	for (int checkBeachsNo = 0; checkBeachsNo < numberOfBeaches ; checkBeachsNo++)
	{
		int daysFoundinthisbeach = 0;
		List<String> dates = new ArrayList<String>();
		
		
		System.out.println("*******************************************");
		System.out.println("Start checking for the - " + beachs.get(checkBeachsNo));
			
			
		for (int checkday = 0 ; checkday < daysOfForecast; checkday++)
		{
			boolean isMondayOrFri = false;	
			String tempDateJson = "data[" + checkday + "].datetime";
			
			//System.out.println(tempDateJson);

		
			String dateOfForecast = 
					given()
						.queryParam("key", apiKey)
						.queryParam("country", countryCode)
						.queryParam("city", cityName.get(checkBeachsNo))
					.when()
						.get(apiUrl)
					.then()
						.extract().path(tempDateJson).toString();;
	  
		
		String[] parts = dateOfForecast.split("-");
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]);
		int day = Integer.parseInt(parts[2]);		
		LocalDate thisday = LocalDate.of(year, month, day);
		isMondayOrFri = isMonOrFri(thisday);
		
		if (isMondayOrFri == true)
		{
			System.out.println(dateOfForecast + " is either Monday or Friday, good day for surfing");
			
			// Getting the max and min temperture and verify the temperature between 12-30
	
				float low_temp = get_floatValueFromAPI(cityName.get(checkBeachsNo), "data[" + checkday + "].low_temp" );
				System.out.println("Min temperature: " + low_temp);		
				float max_temp = get_floatValueFromAPI(cityName.get(checkBeachsNo), "data[" + checkday + "].max_temp" );
				System.out.println("Max temperature: " + max_temp);	

				
				if (low_temp > 12 && max_temp < 30)
				{
					System.out.println("Temperature is between 12 and 30 ");
					float winspeed = get_floatValueFromAPI(cityName.get(checkBeachsNo), "data[" + checkday + "].wind_spd" );
					if (winspeed >3 && winspeed <9)
					{
						System.out.println("win speed is between 3 and 9 ");
						float uv = get_floatValueFromAPI(cityName.get(checkBeachsNo), "data[" + checkday + "].uv" );
						if (uv <= 12)
						{
							System.out.println("uv index is <= 12 ");
							System.out.println("Found the suitable beach, beach name: "+ beachs.get(checkBeachsNo) + " at " + dateOfForecast );
							System.out.println();
							daysFoundinthisbeach++;
							dates.add(dateOfForecast);
							
						}
					}
				}

				
		}
		else {
			//is not Monday or Friday, i am not going to surf");
		}
		
		
		
		
		
	}
		
		Beach this_beach = new Beach(daysFoundinthisbeach,beachs.get(checkBeachsNo),dates);	
		beache_recorder[checkBeachsNo] = this_beach;
		
		//System.out.println("End of checking for the - " + beache_recorder.get(checkBeachsNo).beachName);
		//System.out.println("There is/are " + beache_recorder.get(checkBeachsNo).daysfound + " days you can surf in" + beachs.get(checkBeachsNo));
		System.out.println("*******************************************");
		System.out.println();
		
		
		}
	
	
		// data analysis and select the best 2 spots
	
	System.out.println("Summary ********************************");
	Arrays.sort(beache_recorder);
	
	System.out.println("AFTER sort *****************************");
	
	for (int i = 0 ; i < numberOfBeaches; i++)
	{
		System.out.println("On " + beache_recorder[i].getBeachName() +" I can surf for " + beache_recorder[i].getDaysfound() + " days");

		for(int k = 0; k< beache_recorder[i].getDates().size() ; k++)
		{
			System.out.println(beache_recorder[i].getDates().get(k));
			
		}
		
		
	}
	System.out.println();
	System.out.println("Final result ****************************");
	System.out.println("Best 2 Spots: ");
	for (int i = 0 ; i < 2; i++)
	{
		System.out.println("On " + beache_recorder[i].getBeachName() +" I can surf for " + beache_recorder[i].getDaysfound() + " days");
	
	}

	}
	
	
	

	
	// verify the date is Monday or Friday
	
	public static boolean isMonOrFri(final LocalDate ld) 
    {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.MONDAY || day == DayOfWeek.FRIDAY;
    }
	
	// extract the JSON response which is Float format
	public float get_floatValueFromAPI(String cityName, String jsonOfTemp)
	{
		//System.out.println("enter function");	
		String temp = 
				given()
				   .queryParam("key", apiKey)
				   .queryParam("country", countryCode)
				   .queryParam("city", cityName)
				.when()
				   .get(apiUrl)
				.then()
					.extract().path(jsonOfTemp).toString();
		
		
		return Float.parseFloat(temp);
		
	}
	
	
	
}









