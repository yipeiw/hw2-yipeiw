package edu.cmu.lti.oaqa.openqa.test.team01.passage.WindowScore;

public class YipeiKeytermWindowScorerSum implements YipeiKeytermWindowScorer {

  public double scoreWindow(int begin, int end, int matchesFound, int totalMatches,
          int keytermsFound, int totalKeyterms, int textSize, float[] weight) {
    int windowSize = end - begin;
    double offsetScore = ((double) textSize - (double) begin) / (double) textSize;
    return (weight[0] * (double) matchesFound / (double) totalMatches) + weight[1]
            * ((double) keytermsFound / (double) totalKeyterms) + weight[2]
            * (1 - ((double) windowSize / (double) textSize) + weight[3] * offsetScore);
  }
}
