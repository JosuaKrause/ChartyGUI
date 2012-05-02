package de.woerteler.svg;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.Writer;
import java.util.IdentityHashMap;
import java.util.Map;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import de.woerteler.util.SVGOut;

/**
 * Writes graphics operations in an SVG writer. This class needs Apache Batik.
 * 
 * @author Joschi <josua.krause@googlemail.com>
 */
public class BatikSVG implements SVGOut {

  /**
   * The currently open graphics objects to write the contents to the output
   * stream.
   */
  private final Map<Graphics2D, SVGGraphics2D> openGfx =
      new IdentityHashMap<Graphics2D, SVGGraphics2D>();

  @Override
  public Graphics2D getGraphics(final String name) {
    final DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
    final String svgNS = "http://www.w3.org/2000/svg";
    final Document document = domImpl.createDocument(svgNS, "svg", null);
    final SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(document);
    ctx.setEmbeddedFontsOn(true);
    ctx.setComment("generated by " + name);
    final SVGGraphics2D g = new SVGGraphics2D(ctx, true);
    openGfx.put(g, g);
    return g;
  }

  @Override
  public void write(final Writer out, final Graphics2D g) throws IOException {
    final SVGGraphics2D gfx = openGfx.remove(g);
    if(gfx == null) throw new IllegalArgumentException("invalid graphics object");
    gfx.stream(out, true);
  }

}