package restassuredTests;


import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;


// checking the two api

public class PostalOrCityName {

	public static String apiKey = "d0dcd53eacee408d9cd1d3de37c4bbd7";
	public static String currentWeatherAPIUrl = "https://api.weatherbit.io/v2.0/current";
	public static String forecastWeatherAPIUrl = "https://api.weatherbit.io/v2.0/forecast/daily";
	
	public static String bondibeach_postalCode = "2026";
	public static String bondibeach_cityname = "[North Bondi]";
	public static String countryCode = "AU";
	
	
	// Test API status code
		// param key = apikey
		// param country = provide count
		// param postal_code = bondi beach's post code 2026
		@Test
		public void testCurrentWeatherAPIStatus()
		{
			given()		   
			   .queryParam("key", apiKey)
			   .queryParam("country", countryCode)
			   .queryParam("postal_code", bondibeach_postalCode)
			.when()
			   .get(currentWeatherAPIUrl)
			.then()
			   .statusCode(200);
			
		}
		
		// Test API status code
				// param key = apikey
				// param country = provide count
				// param postal_code = bondi beach's post code 2026
		
		// verify 2026 post code in AU is North Bondi
		
		@Test
		public void checkCityNameInCurrentWeatherAPI()
		{
			
			String cityName = 
			given()		   
			   .queryParam("key", apiKey)
			   .queryParam("country", countryCode)
			   .queryParam("postal_code", bondibeach_postalCode)
			.when()
			   .get(currentWeatherAPIUrl)
			.then()
			   .statusCode(200)
			   .extract().path("data.city_name").toString();
			
			
			System.out.println("in API: " + currentWeatherAPIUrl);
			
			if (cityName.compareTo(bondibeach_cityname) == 0)
			{
				System.out.println("Postal code " + bondibeach_postalCode +  " lead to correct city name" + bondibeach_cityname +" response");
			}			
			else
			{
				System.out.println("Postal code did not lead to correct city name response");
			}
			System.out.println();
		}
		
		@Test
		public void checkCityNameInForecastWeatherAPI()
		{
			
			String cityName = 
			given()		   
			   .queryParam("key", apiKey)
			   .queryParam("country", countryCode)
			   .queryParam("postal_code", bondibeach_postalCode)
			.when()
			   .get(forecastWeatherAPIUrl)
			.then()
			   .statusCode(200)
			   .extract().path("city_name").toString();
			
			
			System.out.println("in API: " + forecastWeatherAPIUrl);
			
			if (cityName.compareTo(bondibeach_cityname) == 0)
			{
				System.out.println("Postal code " + bondibeach_postalCode +  " lead to correct city name" + bondibeach_cityname +" response");
			}
			else
			{
				System.out.println("Postal code did not lead to correct city name response");
			}
			System.out.println();
		}
	
	
}
