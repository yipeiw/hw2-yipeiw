package edu.cmu.lti.oaqa.openqa.test.team01.passage.WindowScore;

public interface YipeiKeytermWindowScorer {
  public double scoreWindow(int begin, int end, int matchesFound, int totalMatches,
          int keytermsFound, int totalKeyterms, int textSize, float[] weight);
}
