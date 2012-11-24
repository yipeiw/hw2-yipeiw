package edu.cmu.lti.oaqa.openqa.test.team01.keyterm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.lti.oaqa.cse.basephase.keyterm.AbstractKeytermUpdater;
import edu.cmu.lti.oaqa.framework.data.Keyterm;

public class ExcludeStopWord extends AbstractKeytermUpdater {
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
    
    List<Keyterm> Excluded = new ArrayList<Keyterm>();
    for(Keyterm keyterm:original)
    {
      if (!StopWords.contains(keyterm.toString()))
      {
        Excluded.add(keyterm);
        System.out.println(keyterm.toString());
      }
    }
    return Excluded;
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
