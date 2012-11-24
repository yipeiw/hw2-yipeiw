package edu.cmu.lti.oaqa.openqa.test.team01.passage;

import java.util.List;

import edu.cmu.lti.oaqa.cse.basephase.ie.AbstractPassageUpdater;
import edu.cmu.lti.oaqa.framework.data.Keyterm;
import edu.cmu.lti.oaqa.framework.data.PassageCandidate;
import edu.cmu.lti.oaqa.framework.data.RetrievalResult;

public class PassageUpdater extends AbstractPassageUpdater {

  @Override
  protected List<PassageCandidate> updatePassages(String arg0, List<Keyterm> arg1,
          List<RetrievalResult> arg2, List<PassageCandidate> arg3) {
    // TODO Auto-generated method stub
    //for back-off query
    return null;
  }

}
