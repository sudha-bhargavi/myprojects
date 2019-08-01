<h2>Sample Rest Api for the useCases mentioned in the document</h2>


<h3>Design considerations</h3>


Developed Rest Api using Spring framework based on Controller -> Service -> Repository model. 


Implemented on HATEOS architecture pattern.


Using H2 in memory DB for persistence. Configured JPA ORM talk to H2.


Use Etag headers for syncroniation of updates. Other option was to use Last update time or VERSION ATTRIBUTE for syncronization , but wanted to ake sure hashing of object`s content would me more accurate to achieve syncronization.



<h3>STEPS TO RUN THE APPLICATION on MAC/ LINUX</h3>


1.Steps


              1. Make sure maven is availble on your machine
							
							
			2: From a Terminal create a directory eg:Test 
			
			3: cd Test
							
			4: git init
							
			4: git clone https://github.com/sudha-bhargavi/myprojects.git
              
              
              5: This will create application repo in the current directory called myprojects
              
              
              6: cd myprojects
              
              
              7:In this directory create a directory called seeddata. This directory holds the data pulled from IMDB.
              
              
              8:Copy the seeddata files(seedata.zip provided as an attachment) into this seeddata directory
              
              
              9: CastAndCrew.csv Episode.csv	Principals.csv	 Ratings.csv	Titles.csv 
              
              
              10: Next place the provided moviedb.mv.db file in the HOME directory eg : ~/moviedb. This will help pre start  
	      
	      
	      db with out creating it again from scratch.
              
              
              11: Structure of the current directoty Test/myprojects should now look like this:
              
              
              pom.xml		seeddata	src		target
              
              
              12: From the directory myprojects run the command mvn spring-boot:run
              
              
              This will run Apache server and deploy application on localhost port 8080 
	      
	      
	      13: I am using in memory H2 Database for persisting the data. This is configured to look into ~/moviedb
	      
	      
	      for file persistence between restarts of the server. Make sure provided moviedb file in HOME directory.
	      
	      
	      http://localhost:8080/h2  will bring up the DB console.
              
              
              14: Open a rest client like postman and access the following URLs to run application
              
            
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
	      
	      
	      Using this we can see what the ratings of each individual episode is.
	      
	      
	      Average of all the episodes rating will be the season`s rating
              
              
              7: URL GET http://localhost:8080/tvSeries/{id}/season/{seasonnum}/rating
              
              
              This API will return a seasons rating.
	      
	      
	      Average of all the episodes rating is the season rating
	      
	      
<h4>3rd Part of the Task</h4>
	      
	      
	      For syncronization I am using EntityTags (ETag) headers (If-Match header) in the client requests to solve
	       
	      
	      LOST updates. This way we can achieve optimistic locking on the concurrent resource updates. 
	      
	      
	      URL: PUT localhost:8080/tvSeries/{id}/rating
	      
	      
	      Sample PUT payload for updating the rating of an episode with id tt10014818
	      
	      
	        {
			"movieId": "tt10014818"
			"rating": 210.0,
			"votes": 20,
		}
	      
	      
	      The above is a PUT Api call where we can update the rating for a movie or tvseries or an episode.Server is made 
	      
	      
	      to handle concurrency where the request is expected to have an IF-Match Etag header. When a request is made to
	      
	      
	      update the episode rating we verify if the request has "If-Match" header with a value. This value is compared
	      
	      
	      against the ETag value on the server side. If it matches that implies the resource has not been updated and this
	      
	      
	      update can proceed. If the values do not match then server returns a Precondition failure status with the 
	      
	      
	      new Etag. Client can update hi knowlegde of the resource and rerequest with the new updated Etag.
	      
	      
	      Syncronization: When two concurrrent updates happen, ETag in the header will allow the request to update a res
	      
	      
	      ource only if the Etags on the server side match . Request 1 with Etag 1 updates a resource. Request 2 with ETag 
	      
	      
	      1 will now fail and will retry with the updated resource and new ETag. This is more of an optimistic approach.
	      
	      
	      
<h3>Integration Junit Testing </h3>

Some integration tests have been added to the application to test the above 3 usecases.  
	      
	      
	      
	      
	      
	    
              
              
              





