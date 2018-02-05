package com.tika_test2;

import com.amazonaws.services.s3.model.S3Object;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;

public class Extractor {
    private final Properties _props = new Properties();
    private final StanfordCoreNLP _nlp;
    public Extractor(){
        _props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
        _nlp = new StanfordCoreNLP(_props);
    }
    public List<CoreMap> Extract(String content){
       Annotation document = getAnnotation(content);
       return getSentences(document);
    }

    private Annotation getAnnotation(String content){
        Annotation document = new Annotation(content);
        _nlp.annotate(document);
        return document;
    }

    private List<CoreMap> getSentences(Annotation document){
        return document.get(SentencesAnnotation.class);
    }
}
