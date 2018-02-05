package com.tika_test2;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import edu.stanford.nlp.util.CoreMap;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

public class Worker {
    private static final BasicAWSCredentials creds = new BasicAWSCredentials("", "");
    private static final AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(creds))
            .build();
    private static final Processor processor = new Processor();
    private static final Extractor extractor = new Extractor();
    private static final Parser parser = new Parser();
    private static final S3Object obj = s3Client.getObject("", "");

    public static void main(String[] args) throws TikaException, SAXException, IOException {
        System.out.println("Extracting Text");
        String content = parser.parse(obj);
        System.out.println("Text Extracted from s3 object");
        System.out.println("Breaking extracted text into sentences");
        List<CoreMap> sentences = extractor.Extract(content);
        System.out.println("Sentences extracted.., processing output");
        List tokens = processor.processSentences(sentences);

        for(Object token : tokens){
            EmbeddedToken t = (EmbeddedToken)token;
            String key = t.getName();
            String value = t.getValue();
            String formattedOutput = String.format("'{}' is a {}", key, value);
            System.out.println(formattedOutput);
        }
    }
}
