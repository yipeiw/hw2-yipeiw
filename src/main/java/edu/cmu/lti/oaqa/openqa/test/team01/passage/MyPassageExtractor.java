package edu.cmu.lti.oaqa.openqa.test.team01.passage;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.resource.ResourceInitializationException;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import edu.cmu.lti.oaqa.core.provider.solr.SolrWrapper;
import edu.cmu.lti.oaqa.cse.basephase.ie.AbstractPassageExtractor;
import edu.cmu.lti.oaqa.framework.data.Keyterm;
import edu.cmu.lti.oaqa.framework.data.PassageCandidate;
import edu.cmu.lti.oaqa.framework.data.RetrievalResult;
import edu.cmu.lti.oaqa.openqa.hello.passage.KeytermWindowScorerSum;
//import edu.cmu.lti.oaqa.openqa.hello.passage.PassageCandidateFinder;
import edu.cmu.lti.oaqa.openqa.test.team01.passage.WindowScore.YipeiKeytermWindowScorerSum;

public class MyPassageExtractor extends AbstractPassageExtractor {
  protected SolrWrapper wrapper;
  public String keytermWindowScorer;
  private float[] weight;
  
  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    String serverUrl = (String) aContext.getConfigParameterValue("server");
    Integer serverPort = (Integer) aContext.getConfigParameterValue("port");
    Boolean embedded = (Boolean) aContext.getConfigParameterValue("embedded");
    String core = (String) aContext.getConfigParameterValue("core");
    System.out.printf("url parameter success load\n");
    
    keytermWindowScorer = (String) aContext.getConfigParameterValue("keytermWindowScorer");

    System.out.printf("score parameter success load\n");
    
    weight = new float[4];
    weight[0] = Float.valueOf((String) aContext.getConfigParameterValue("w1")).floatValue();
    weight[1] = Float.valueOf((String) aContext.getConfigParameterValue("w2")).floatValue();
    weight[2] = Float.valueOf((String) aContext.getConfigParameterValue("w3")).floatValue();
    weight[3] = Float.valueOf((String) aContext.getConfigParameterValue("w4")).floatValue();
    
    System.out.printf("score parameter: %f %f %f %f\n", weight[0], weight[1], weight[2], weight[3]);
    System.out.println("initialize() : keytermWindowScorer: " + keytermWindowScorer);
    try {
      this.wrapper = new SolrWrapper(serverUrl, serverPort, embedded, core);
    } catch (Exception e) {
      throw new ResourceInitializationException(e);
    }
  }

  @Override
  protected List<PassageCandidate> extractPassages(String question, List<Keyterm> keyterms,
          List<RetrievalResult> documents) {
    List<PassageCandidate> result = new ArrayList<PassageCandidate>();
    for (RetrievalResult document : documents) {
      System.out.println("RetrievalResult: " + document.toString());
      String id = document.getDocID();
      try {
        // @Alkesh: can you add this call to the SolrWrapper API? - Now work with solr-provider
        // 1.0.5-SNAPSHOT
        String text = wrapper.getDocText(id);
        System.out.println(text);

        MyPassageCandidateFinder finder = null;
        if (keytermWindowScorer == "mysum") {
          finder = new MyPassageCandidateFinder(id, text,
                  new YipeiKeytermWindowScorerSum(), weight);
        }else
        {
          finder = new MyPassageCandidateFinder(id, text,
                  new KeytermWindowScorerSum());        
        }
        // @EHN: to avoid ClassCastException: [Ljava.lang.Object; cannot be cast to
        // [Ljava.lang.String;
        List<String> keytermStrings = Lists.transform(keyterms, new Function<Keyterm, String>() {
          public String apply(Keyterm keyterm) {
            return keyterm.getText();
          }
        });
        List<PassageCandidate> passageSpans = finder.extractPassages(keytermStrings
                .toArray(new String[0]));
        for (PassageCandidate passageSpan : passageSpans)
          result.add(passageSpan);
      } catch (SolrServerException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  @Override
  public void collectionProcessComplete() throws AnalysisEngineProcessException {
    super.collectionProcessComplete();
    wrapper.close();
  }

}
