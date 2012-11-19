package edu.cmu.lti.oaqa.openqa.test.team01.keyterm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.lti.oaqa.cse.basephase.keyterm.AbstractKeytermUpdater;
import edu.cmu.lti.oaqa.framework.data.Keyterm;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class AddVerb extends AbstractKeytermUpdater {

  @Override
  protected List<Keyterm> updateKeyterms(String question, List<Keyterm> original) {
    
    MaxentTagger tagger;
    try {
      tagger = new MaxentTagger("/home/yipeiw/workspace/hw2-yipeiw/taggers/left3words-wsj-0-18.tagger");
    
      String tagged = tagger.tagString(question);
      
      System.out.println(tagged);
      String[] pairs = tagged.split("\\s+");
      for(int i=0; i< pairs.length; i++)
      {
          String[] words = pairs[i].split("_");
          if (words[1].indexOf("V")!=-1)
          {
            System.out.println(words[0]);
            System.out.println(words[1]);
            original.add(new Keyterm(words[1]));       
          }
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return original;
  }

}
