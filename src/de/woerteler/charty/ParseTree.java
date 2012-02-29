package de.woerteler.charty;

import java.awt.image.BufferedImage;

import de.woerteler.charty.ChartParser.Edge;

/**
 * Class representing a parse tree generated by the {@link ChartParser}.
 *
 * @author Leo Woerteler
 */
public final class ParseTree {

  /** Root edge of this tree. */
  private final Edge edge;

  /** The method that is used to draw the tree. */
  private final DisplayMethod method;

  /** The image. */
  private BufferedImage image;

  /**
   * Constructor.
   *
   * @param e root edge
   * @param m the drawing method
   */
  ParseTree(final Edge e, final DisplayMethod m) {
    method = m;
    edge = e;
  }

  /**
   * Get the image of this parse tree.
   *
   * @return parse tree image
   * @throws Exception if the conversion fails
   */
  public synchronized BufferedImage getImage() throws Exception {
    if(image == null) {
      image = method.getImage(edge);
    }
    return image;
  }

}
