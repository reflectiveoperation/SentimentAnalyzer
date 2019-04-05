package com.cristianperez.sentimentanalyzersample.logic.models;

import org.springframework.stereotype.Component;

@Component
public class SentimentResult {

    private String sentimentType;
    private int sentimentScore;
    private SentimentClassification sentimentClass;

    public String getSentimentType() {
        return sentimentType;
    }

    public void setSentimentType(String sentimentType) {
        this.sentimentType = sentimentType;
    }

    public int getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(int sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public SentimentClassification getSentimentClass() {
        return sentimentClass;
    }

    public void setSentimentClass(SentimentClassification sentimentClass) {
        this.sentimentClass = sentimentClass;
    }

    @Override
    public String toString() {
        return "SentimentResult{" +
                "sentimentType='" + sentimentType + '\'' +
                ", sentimentScore=" + sentimentScore +
                ", sentimentClass=" + sentimentClass +
                '}';
    }
}
