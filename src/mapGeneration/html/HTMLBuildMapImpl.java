package mapGeneration.html;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import elements.ArticleData;
import elements.LayoutData;
import utils.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Implementation of a HTML builder for maps
 * @author Loann Neveu
 */
public class HTMLBuildMapImpl implements HTMLBuildMap{
    private static final String RESOURCES_FOLDER = "resources/maps/";
    private static final String PROLOGUE = "prologue.html";
    private static final String EPILOGUE = "epilogue.html";

    private static final String OUPUT_DIRECTORY = "data/maps/";

    private static final String TITLE_POSITION[] = {"top-right", "top-left", "bottom-right", "bottom-left"};
    private Random rand = new Random();

    StringBuilder HTMLString = null;

    @Override
    public void create(Graph graph, String filename) {
        HTMLString = new StringBuilder();
        // write prologue
        prologue();

        for(Vertex n : graph.getVertices()) {
            node(n);
        }

        // write epilogue
        epilogue();

        // write buffer in a file
        BufferedWriter out = null;
        try {
            System.out.println("buffer content : " + HTMLString.toString());
            System.out.println("file : " + OUPUT_DIRECTORY + filename);

            FileWriter fstream = new FileWriter(OUPUT_DIRECTORY + filename);
            out = new BufferedWriter(fstream);
            out.append(HTMLString);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Write a single node
     */
    private void node(Vertex node) {
        ArticleData data_article = (ArticleData) node.getProperty("article_data");
        LayoutData data_layout = (LayoutData) node.getProperty("layout_data");

        // container + pos
        HTMLString.append("<div class=\"article_container\" style=\"");
        HTMLString.append("position:absolute;left:");
        HTMLString.append(Utils.round(data_layout.getX(), 2));
        HTMLString.append("px;top:");
        HTMLString.append(Utils.round(data_layout.getY(), 2));
        HTMLString.append("px;z-index:");
        HTMLString.append(Utils.round(data_layout.getZ(), 2));
        HTMLString.append("\">");

        // href article
        HTMLString.append("<a href=\"");
        HTMLString.append(data_article.getUrl_article());
        HTMLString.append("\" style=\"with:inherit;height:inherit\">");

        // thumbnail
        HTMLString.append("<img src=\"");
        HTMLString.append(data_article.getThumbnail());
        HTMLString.append("\" alt=\"");
        HTMLString.append(data_article.getName());
        HTMLString.append("\" style=\"width:inherit;height:inherit\">");
        HTMLString.append("</a>");

        // title
        HTMLString.append("<div class=\"title ");
        // random rotation
        HTMLString.append(TITLE_POSITION[rand.nextInt(4)]);
        HTMLString.append("\">");

        HTMLString.append(data_article.getTitle());
        HTMLString.append("</div>");

        // end container
        HTMLString.append("</div>");
    }

    /**
     * Write prologue
     */
    private void prologue() {
        String content = "";
        try {
            content = Utils.readFile(RESOURCES_FOLDER + PROLOGUE, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("prologueFile: " + RESOURCES_FOLDER + PROLOGUE);
        //System.out.println("prologue: " + content);
        HTMLString.append(content);
    }

    /**
     * Write epilogue
     */
    private void epilogue() {
        String content = "";
        try {
            content = Utils.readFile(RESOURCES_FOLDER + EPILOGUE, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HTMLString.append(content);
    }
}
