package com.tika_test2;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;


public class Processor {
    private static final Logger LOG = LoggerFactory.getLogger(Processor.class);

    public List processSentences(List<CoreMap> sentences){
        final List tokens = new ArrayList();
        final StringBuilder sb = new StringBuilder();
        for(CoreMap sentence : sentences){

            processSentence(sentence, tokens, sb);
        }
        return tokens;
    }

    private void processSentence(CoreMap sentence, List tokens, StringBuilder sb){

        String previousToken = "O";
        String currentToken = "O";
        boolean newToken = true;
        for(CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)){
            currentToken = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
            String word = token.get(CoreAnnotations.TextAnnotation.class);
            if (currentToken.equals("O")) {
                if (!previousToken.equals("O") && (sb.length() > 0)) {
                    handle(previousToken, sb, tokens);
                    newToken = true;
                }
                continue;
            }

            if (newToken) {
                previousToken = currentToken;
                newToken = false;
                sb.append(word);
                continue;
            }

            if (currentToken.equals(previousToken)) {
                sb.append(" " + word);
            } else {
                handle(previousToken, sb, tokens);
                newToken = true;
            }
            previousToken = currentToken;
        }
    }

    private void handle(String key, StringBuilder stringBuilder, List tokens){
        LOG.debug("'{}' is a {}", stringBuilder, key);
        tokens.add(new EmbeddedToken(key, stringBuilder.toString()));
        //clear out stringBuilder
        stringBuilder.setLength(0);
    }
}
