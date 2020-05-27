package service;

// To fix any import issue - just add those packages to filepath
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringRestApiApplication {

    public static void main(String[] args) {
        // If we have 'cannot access problem -> go to here: http://programmersought.com/article/48731001282/;jsessionid=25AEE2D3812F22013BECE7C1F03AC5FD
        SpringApplication.run(SpringRestApiApplication.class, args);
        //System.out.println(SpringVersion.getVersion());
    }

    // For more reading on Spring params: https://www.baeldung.com/spring-request-param
}
