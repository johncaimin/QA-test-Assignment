1.How to run the program

1.1 Please install java 7 or newer 

1.2 Please install Maven automation tool
For example if you use Eclipe IDE. 
Eclipse -> Help -> Install New Software.
Expand " Collaboration " tag.
Select Maven plugin from there.
Click on next .Accept the agreement & click finish.

1.3 Please install TestNG into your local device.
For example if you use Eclipe IDE. Go to Help->Install new software
Type ¡°https://testng.org/testng-eclipse-update-site/¡± into the work with field
Click on next .Accept the agreement & click finish.


1.4 Enter the java files
Enter PostalOrCityName.java or WeatherForecastsAPITest.java.
\WeatherForecastsAPITest\src\test\java\restassuredTests\PostalOrCityName.java
\WeatherForecastsAPITest\src\test\java\restassuredTests\WeatherForecastsAPITest.java
Right click the Run As ¡°TestNG Test¡±

2. Where to find reports
Report will generate in the console window. Sample output also provide in the support PDF file.

3. If any failures.
Make sure you install latest Maven and TestNG library.
The API Key only allow to call the API 50K/per day. 

4. What more and weakness
WeatherForecastsAPITest.java file will call the API around 150-200 time every time it run.
It might consume lost of resources. But this will also test the API performance under call stress.

For more information please check the Question 2 API automation.pdf
 

