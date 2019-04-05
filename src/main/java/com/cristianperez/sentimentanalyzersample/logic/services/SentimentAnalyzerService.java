package com.cristianperez.sentimentanalyzersample.logic.services;

import com.cristianperez.sentimentanalyzersample.logic.models.SentimentClassification;
import com.cristianperez.sentimentanalyzersample.logic.models.SentimentResult;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
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
    @Autowired
    private SentimentClassification classification;


    public SentimentResult getSentimentResult(String text) {


        SentimentResult sentimentResult = new SentimentResult();

        if (text != null && text.length() > 0) {

            Annotation annotation = pipeline.process(text);

            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {



                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);


                //System.out.println(tree);


                SimpleMatrix simpleMatrix = RNNCoreAnnotations.getPredictions(tree);

                //System.out.println(simpleMatrix);

                classification.setVeryNegative((int) Math.round(simpleMatrix.get(0) * 100d));
                classification.setNegative((int) Math.round(simpleMatrix.get(1) * 100d));
                classification.setNeutral((int) Math.round(simpleMatrix.get(2) * 100d));
                classification.setPositive((int) Math.round(simpleMatrix.get(3) * 100d));
                classification.setVeryPositive((int) Math.round(simpleMatrix.get(4) * 100d));
                String sentimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
                sentimentResult.setSentimentType(sentimentType);
                sentimentResult.setSentimentClass(classification);
                sentimentResult.setSentimentScore(RNNCoreAnnotations.getPredictedClass(tree));

                
                System.out.println("-------------------->");
                System.out.println(sentence);
                System.out.println(classification);
                System.out.println(sentimentResult);
                System.out.println("-------------------->");

            }
        }
        return sentimentResult;
    }

}
