package edu.cmu.lti.oaqa.openqa.test.team01.keyterm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.lti.oaqa.cse.basephase.keyterm.AbstractKeytermUpdater;
import edu.cmu.lti.oaqa.framework.data.Keyterm;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class AddVerb extends AbstractKeytermUpdater {
  private String StopWordFile = "/home/yipeiw/workspace/hw2-yipeiw/src/main/resources/StopWordList.txt";
  @Override
  protected List<Keyterm> updateKeyterms(String question, List<Keyterm> original) {
    List<String> StopWords = new ArrayList<String>();
    try {
      StopWords = LoadStopWords(StopWordFile);
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    
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
            if (!StopWords.contains(words[0])) {
              original.add(new Keyterm(words[0]));
            }else
            {
              System.out.printf("verb stop word: %s, %s",words[0], words[1]);
            }      
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
  
  public List<String> LoadStopWords(String FileName) throws IOException {
    List<String> stopWords = new ArrayList<String>();
    FileReader fr = new FileReader(FileName);
    BufferedReader br = new BufferedReader(fr);
    String line;
    while ((line = br.readLine()) != null) {
      stopWords.add(line.trim());
      System.out.printf("load stop word %s", line.trim());
    }
    br.close();
    return stopWords;

  }

}
