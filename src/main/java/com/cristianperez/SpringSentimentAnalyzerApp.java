package com.cristianperez;

import com.cristianperez.sentimentanalyzersample.presentation.console.PresentationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSentimentAnalyzerApp implements CommandLineRunner {

    @Autowired
    private PresentationController presentationController;

    public static void main(String[] args) {
        SpringApplication.run(SpringSentimentAnalyzerApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        presentationController.start();

    }
}
