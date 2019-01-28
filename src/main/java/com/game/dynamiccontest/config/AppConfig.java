package com.game.dynamiccontest.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.FileInputStream;
import java.io.IOException;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class AppConfig {
    @Bean
    public FirebaseApp setupFirebase() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://test-1d656.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
        System.out.println("Firebase: " + FirebaseApp.getInstance().getName());
        return FirebaseApp.getInstance();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.game.dynamiccontest.controller"))
                .paths(regex("/*.*"))
                .build();
//                .apiInfo(metaData());
    }
}
