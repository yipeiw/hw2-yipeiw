package edu.cmu.lti.oaqa.openqa.test.team01.keyterm;

import java.util.List;

import edu.cmu.lti.oaqa.cse.basephase.keyterm.AbstractKeytermUpdater;
import edu.cmu.lti.oaqa.framework.data.Keyterm;

public class SimpleUpdater extends AbstractKeytermUpdater {

  @Override
  protected List<Keyterm> updateKeyterms(String question, List<Keyterm> original) {
    return original;
  }

}
