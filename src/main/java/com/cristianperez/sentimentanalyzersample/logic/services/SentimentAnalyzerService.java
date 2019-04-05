package com.cristianperez.sentimentanalyzersample.logic.services;

import com.cristianperez.sentimentanalyzersample.logic.InvalidInputException;
import com.cristianperez.sentimentanalyzersample.logic.models.SentimentClassification;
import com.cristianperez.sentimentanalyzersample.logic.models.SentimentResult;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.apache.commons.lang3.StringUtils;
import org.ejml.simple.SimpleMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class SentimentAnalyzerService {

    @Autowired
    @Qualifier("sentimentProperties")
    private Properties properties;
    @Autowired
    private StanfordCoreNLP pipeline;

    public SentimentResult getSentimentResult(String text) {
        validateInput(text);

        return getSentimentResult(pipeline.process(text));
    }

    private void validateInput(String text) {
        if (StringUtils.isBlank(text)) {
            throw new InvalidInputException("Invalid input length");
        }
    }

    private SentimentResult getSentimentResult(Annotation annotation) {
        SentimentResult sentimentResult = new SentimentResult();

        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            sentimentResult.setSentimentType(sentence.get(SentimentCoreAnnotations.SentimentClass.class));
            sentimentResult.setSentimentClass(getClassification(RNNCoreAnnotations.getPredictions(tree)));
            sentimentResult.setSentimentScore(RNNCoreAnnotations.getPredictedClass(tree));

            printSentimentResultBySentence(sentimentResult, sentence);
        }
        //TODO: Returning the last result instead of a summary?!?!
        return sentimentResult;
    }

    private SentimentClassification getClassification(SimpleMatrix simpleMatrix) {
        SentimentClassification classification = new SentimentClassification();
        classification.setVeryNegative((int) Math.round(simpleMatrix.get(0) * 100d));
        classification.setNegative((int) Math.round(simpleMatrix.get(1) * 100d));
        classification.setNeutral((int) Math.round(simpleMatrix.get(2) * 100d));
        classification.setPositive((int) Math.round(simpleMatrix.get(3) * 100d));
        classification.setVeryPositive((int) Math.round(simpleMatrix.get(4) * 100d));
        return classification;
    }

    private void printSentimentResultBySentence(SentimentResult sentimentResult, CoreMap sentence) {
        System.out.println("-------------------->");
        System.out.println(sentence);
        System.out.println(sentimentResult);
        System.out.println("-------------------->");
    }
}
