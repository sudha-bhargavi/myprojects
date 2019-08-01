<h2>Sample Rest Api for the useCases mentioned in the document</h2>


<h3>Design considerations</h3>


Developed Rest Api using Spring Boot framework based on Controller -> Service -> Repository model. 


Implemented on HATEOAS architecture pattern.


Using H2 in memory DB for persistence. Configured JPA ORM layer to talk to H2.


Use Etag headers for synchronization of updates. Other option was to use Last update time or VERSION ATTRIBUTE for syncronization , but wanted to ake sure hashing of object`s content would me more accurate to achieve syncronization.



<h3>STEPS TO RUN THE APPLICATION on MAC/ LINUX</h3>


1.Steps


              1. Make sure maven is availble on your machine
							
							
			2: From a terminal/console create a directory (eg:Test)
			
			3: cd Test
							
			4: git init
							
			4: git clone https://github.com/sudha-bhargavi/myprojects.git
              
              
              5: This will create application repo under the current Test directory called myprojects
              
              
              6: cd myprojects
              
              
              7:In this directory create a directory called seeddata. This directory holds the data pulled from IMDB.
              
              
              8:Extract and copy the 5 seeddata files(seedata.zip provided as an gmail drive link) into this seeddata directory
              
              
              9: CastAndCrew.csv Episode.csv	Principals.csv	 Ratings.csv	Titles.csv 
              
              
              10: Next place the moviedb.mv.db file in the HOME directory eg : ~/moviedb. This will help pre start the db with
	      
	      
	      out creating it again from scratch.(Extract from , moviedb.zip provided via shared google drive link)
              
              
              11: Structure of the current directoty Test/myprojects should now look like this:
              
              
              pom.xml	seeddata	src		target
              
              
              12: From the directory myprojects run the command mvn spring-boot:run 
	      
	      
	      Or you can run application from any IDE IntelliJ / Eclipse by importing the project as maven project.
              
              
              This will run Apache server and deploy application on localhost port 8080 
	      
	      
	      13: I am using in memory H2 Database for persisting the data. This is configured to look into ~/moviedb
	      
	      
	      for file persistence between restarts of the server.
	      
	      
	      http://localhost:8080/h2  will bring up the DB console.Username sa  No password
	      
	      
              jdbc URL for h2 console: jdbc:h2:~/moviedb
	      
              
              14: Open a rest client like postman and access the following URLs to run application.
	      
	      
	      Make sure every request is in new tab if testing from postman
              
            
2. <b>Testing</b>


<h4>1st Part of the Task</h4> - Build an application in Java which pulls movies, their ratings, and cast lists from IMDB.
              
              1: All getAll APIs are paginated with 20 items for each page with page metadata included.
	      
	      
	      This is to achieve HATEOS style architecture.
              
              
              2:URL GET: localhost:8080/movies
              
              
              This API will pull all 2017 movies , their ratings if exitis and other  details.
              
              
              3:URL GET: localhost:8080/tvSeries
              
              
              This API will pull all 2017 tvSeries, ratings and their seson ratings and cast info if exists.
              
              
              4: URL GET: localhost:8080/tvSeries/{id}      localhost:8080/movies/{id} 
              
              
              This API will pull a movie or tvSeries with the give ID, and its ratings and their seson ratings.
              

<h4>2nd Part of the Task</h4>: Tv Series`s  seasons rating is calculated using Average rating of all its episodes
              
              
              5: URL GET: localhost:8080/tvSeries/{id}/rating 
              
              
              This API will return the tvseries rating
              
              
              6: URL GET : http://localhost:8080/tvSeries/{id}/season/{seasonnum}/episodes
              
              
              This API will return all the episodes that belong to a season of a tvSeries.
	      
	      
	      Using this we can see what the ratings of each indiccidual episode is.
	      
	      
	      Average of all the episodes rating is the season rating
              
              
              7: URL GET http://localhost:8080/tvSeries/{id}/season/{seasonnum}/rating
              
              
              This API will return a seasons rating.
	      
	      
	      Average of all the episodes rating is the season rating
	     
<h3> Sample test data for Part2 </h3>


	TvSeries with ID tt10016348 "http://localhost:8080/tvSeries/tt10016348"  has 2 seasons
	
	
	GET Call this API to see the list of episodes as below:
	
	
	http://localhost:8080/tvSeries/tt10016348/season/1/episodes
	
	
	Season1 has 10 episdoes as below  with null rating for each episode
	
	
		{
		"rating": {
		    "rating": null,
		    "votes": null,
		    "movieId": null,
		    "links": []
		},
		"episodeId": "tt10020066",
		"tvSeriesId": "tt10016348",
		"seasonNum": 1,
		"episodeNum": 1
	    },
	    {
		"rating": {
		    "rating": null,
		    "votes": null,
		    "movieId": null,
		    "links": []
		},
		"episodeId": "tt10020068",
		"tvSeriesId": "tt10016348",
		"seasonNum": 1,
		"episodeNum": 2
	    },
	    {
		"rating": {
		    "rating": null,
		    "votes": null,
		    "movieId": null,
		    "links": []
		},
		"episodeId": "tt10020070",
		"tvSeriesId": "tt10016348",
		"seasonNum": 1,
		"episodeNum": 3
	    },
	    {
		"rating": {
		    "rating": null,
		    "votes": null,
		    "movieId": null,
		    "links": []
		},
		"episodeId": "tt10020072",
		"tvSeriesId": "tt10016348",
		"seasonNum": 1,
		"episodeNum": 4
	    },
	    {
		"rating": {
		    "rating": null,
		    "votes": null,
		    "movieId": null,
		    "links": []
		},
		"episodeId": "tt10020074",
		"tvSeriesId": "tt10016348",
		"seasonNum": 1,
		"episodeNum": 5
	    },
	    {
		"rating": {
		    "rating": null,
		    "votes": null,
		    "movieId": null,
		    "links": []
		},
		"episodeId": "tt10020076",
		"tvSeriesId": "tt10016348",
		"seasonNum": 1,
		"episodeNum": 6
	    },
	    {
		"rating": {
		    "rating": null,
		    "votes": null,
		    "movieId": null,
		    "links": []
		},
		"episodeId": "tt10020080",
		"tvSeriesId": "tt10016348",
		"seasonNum": 1,
		"episodeNum": 7
	    },
	    {
		"rating": {
		    "rating": null,
		    "votes": null,
		    "movieId": null,
		    "links": []
		},
		"episodeId": "tt10020082",
		"tvSeriesId": "tt10016348",
		"seasonNum": 1,
		"episodeNum": 8
	    },
	    {
		"rating": {
		    "rating": null,
		    "votes": null,
		    "movieId": null,
		    "links": []
		},
		"episodeId": "tt10020084",
		"tvSeriesId": "tt10016348",
		"seasonNum": 1,
		"episodeNum": 9
	    },
	    {
		"rating": {
		    "rating": null,
		    "votes": null,
		    "movieId": null,
		    "links": []
		},
		"episodeId": "tt10020090",
		"tvSeriesId": "tt10016348",
		"seasonNum": 1,
		"episodeNum": 11
	    }
	    
	    
	    Hence when we do a GET on Season 1`s  rating we get 0 rating value
	    
	    
	    "http://localhost:8080/tvSeries/tt10016348/season/1/rating"  (use Etag header If-Match = 47602)
	    
	    
	    Now do a put on one of the episodes tt10020072 , rating by calling
	    
	    
	    PUT  http://localhost:8080/tvSeries/tt10020090/rating with (Notice here the Id is episode ID)
	    
	    
	   Eg: payload 
	   	{
   		 "rating": 20.0,
  		  "votes": 10,
  		  "movieId": "tt10020090"
  		  }
    
    
    First time you get a precondition failure status where the server will respond back with an Etag. Copy Etag from the
    
    
    server response header and invoke the request again with request Header param as If-Match = <Etagvalue> and above 
    
    
    payload. This should update the season rating to new value. 
    
    
    Lets requery the season rating using http://localhost:8080/tvSeries/tt10016348/season/1/rating
    
    
    first call will return an 204 with non content and a new Etag as the seasons rating got updated with the change in its 
    
    
    episode rating. Resend the Get request with the new Etag and get the new rating which should be avg value of all non 
    
    
    null episodes. In this case just one episode.
	 
	      
<h4>3rd Part of the Task</h4>
	      

	For syncronization I am using EntityTags (ETag)  headers (If-Match header) in the client requests to solve


      LOST updates. This way we can achieve optimistic locking on the resource updates. 


      URL: PUT localhost:8080/tvSeries/{id}/rating


      Sample PUT payload for updating the rating of an episode with id tt10014818


	{
		"movieId": "tt10014818"
		"rating": 210.0,
		"votes": 20,
	}


      The above is a PUT Api call where we can update the rating for a movie or tvseries or an episode.Server is


      made to handle concurrency where the request is expected to have an IF-Match Etag header. When a request is 


      made to update the episode rating we verify if the request has "If-Match" header with a value. This value is 


      compared against the ETag value on the server side. If it matches that implies the resource has not been 


      updated and thisupdate can proceed. If the values do not match then server returns a Precondition failure 


      status with the  new Etag. Client can update hi knowlegde of the resource and rerequest with the new updated 


      Etag.Syncronization: When two concurrrent updates happen, ETag in the header will allow the request to update 


      a resource only if the Etags on the server side match . Request 1 with Etag1 updates a resource. Request 2 


      with ETag1 will now fail and will retry with the updated resoucre and new ETag. This is more of an optimistic 


      approach.
	      
	      
	      
<h3>Integration Junit Testing </h3>

Some integration tests have been added to the application to test the above usecases.


<h3>Enhancements</h3>


Add ORM Caching level caching


Implement security of rest APIs with Oauth tokens using a OAuth protocol and end to end using TLS.


<h3> Swagger API Spec (work in progress) </h3>


Planned on providng a SWAGGER API document. Due to time constarint could not finish the Swagger spec details. Partial not much usable


Swagger link is availble here http://localhost:8080/swagger-ui.html#!







