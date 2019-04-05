package com.cristianperez.sentimentanalyzersample.presentation.console;

import com.cristianperez.sentimentanalyzersample.logic.services.SentimentAnalyzerService;
import com.cristianperez.sentimentanalyzersample.logic.models.SentimentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PresentationController {

    private final String text = "You need to go there! The food is delicious and the interior stunting. Perfect for " +
            "breakfast and lunches. They also served in of the best pizza in whole Bali. I can recommend " +
            "you \"the madu\" poached eggs, avocado, bacon. Mhmmmm tasty!";

    @Autowired
    private SentimentAnalyzerService sentimentAnalyzerService;

    public void start() {
//        sentimentAnalyzerService.initialize();
        SentimentResult sentimentResult = sentimentAnalyzerService.getSentimentResult(text);
        printResults(sentimentResult);
    }

    private void printResults(SentimentResult sentimentResult) {
        System.out.println("Sentiments Classification:");
        System.out.println("Very positive: " + sentimentResult.getSentimentClass().getVeryPositive()+"%");
        System.out.println("Positive: " + sentimentResult.getSentimentClass().getPositive()+"%");
        System.out.println("Neutral: " + sentimentResult.getSentimentClass().getNeutral()+"%");
        System.out.println("Negative: " + sentimentResult.getSentimentClass().getNegative()+"%");
        System.out.println("Very negative: " + sentimentResult.getSentimentClass().getVeryNegative()+"%");
        System.out.println("\nSentiments result:");
        System.out.println("Sentiment Score: " + sentimentResult.getSentimentScore());
        System.out.println("Sentiment Type: " + sentimentResult.getSentimentType());
    }



}
