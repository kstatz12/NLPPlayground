package com.tika_test2;

import com.amazonaws.services.s3.model.S3Object;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.*;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

public class Parser {
    public String parse(S3Object obj) throws IOException, SAXException, TikaException {
        AutoDetectParser parser =  new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metaData = new Metadata();
        try(InputStream stream = obj.getObjectContent()){
            parser.parse(stream, handler, metaData);
            return handler.toString();
        }
    }

}
