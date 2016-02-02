package articleGeneration;

import graphGeneration.generation.Article;

import java.io.*;

/**
 * Created by Arno on 02/02/2016.
 */
public class HTMLBuildArticleImpl implements HTMLBuildArticle{

    private static final String OUTPUT_FOLDER = "data/articles/";
    private static final String CSS_FOLDER = "../../resources/html/css/";
    private static final String JS_FOLDER = "../../resources/html/js/";

    public void create(Article article)
    {
        String text = article.getRawtext();
        text = this.parser(text);
        String html = this.getHtml(article, text);

        String filename = article.getTitre()+".html";

        // write buffer in a file
        BufferedWriter out = null;
        try {
            FileWriter fstream = new FileWriter(this.OUTPUT_FOLDER + filename);
            out = new BufferedWriter(fstream);
            out.append(html);
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

    private String parser(String text)
    {
        // Suppression des div
        text = text.replaceAll("<div.*>", "");
        text = text.replaceAll("</div>", "");

        // Suppression des <br>
        text = text.replaceAll("<br\\s?/?>", "");

        // Suppression des <youtube...>
        text = text.replaceAll("<youtube.*>", "");

        //Suppression des <note-image>
        text = text.replaceAll("<note-image\\d*>", "");


        //Suppression des <note-galerie>
        text = text.replaceAll("<note-galerie\\d*>", "");

        // Suppression des <album...>
        text = text.replaceAll("<album.*>", "");

        // Suppression des notes de bas de page [[..]]
        text = text.replaceAll("\\[\\[.*\\]\\]", "");

        // Chapo
        text = text.replaceAll("<chapo>\n?(.*?)</chapo>", "\n\n<div class=\\\"chapo\"><p>$1<\\/p><\\/div>\n");

        // Quotes
        text = text.replaceAll("<quote>\n?(.*?)</quote>", "\n\n<q>$1</q>\n");

        // Poesie
        text = text.replaceAll("<poesie>\n?(.*?)</poesie>", "\n\n<q>$1</q>\n");

        // Images
        text = text.replaceAll("<image=(http://.*?)>", "\n\n<div class=\\\"border\"><img src=\"$1\" alt=\"//\" class=\"miniature\"/></div>\n" +
                "\n");

        // Iframe
        text = text.replaceAll("<iframe.*?src=\"(.*?)\".*?>.*?</iframe>", "\n" +
                "\n" +
                "<div class=\"iframe-wrapper\"><iframe class=\"iframe-content\" src=\"$1\"></iframe><div class=\"iframe-blocker\"></div></div>\n" +
                "\n");

        // <note-texte|texte=...
        text = text.replaceAll("<note-texte\\|texte=(.*?)>", "<div class=\\\"marge\"><div class=\"inner\"><p>$1</p></div></div>\n" +
                "\n");

        // H3 : {{{ }}}
        text = text.replaceAll("\\{\\{\\{\n?(.*?\n*.*?)\\}\\}\\}", "<h3>$1</h3>");

        // Gras : {{ }}
        text = text.replaceAll("\\{\\{\n?(.*?\n*.*?)\\}\\}", "<b>$1</b>");

        // Italique : { }
        text = text.replaceAll("\\{\n?(.*?\n*.*?)\\}", "<i>$1</i>");

        // <li> : -
        text = text.replaceAll("^- (.*)\n", "<li><p>$1</p></li>\n");

        // Rajout des <ul> autour des <li>
        text = text.replaceAll("(<li>(.*</li>\n<li>.*)*</li>)", "<ul>\n$1\n</ul>");

        // [couleur]
        text = text.replaceAll("\\[([a-z\\s]+)\\]", "<span class=\\\"$1\">");
        text = text.replaceAll("\\[/[a-z\\s]+\\]", "</span>");

        // Link
        text = text.replaceAll("\\[(.*?)(->)+(.*?)\\]", "<a href=\\\"$3\" class=\"link\">$1</a>");

        // Paragraphes
        text = text.replaceAll("(^[^<\n]+.+|.+[^/\n>]+)", "<p>$1</p>\n\n");

        // Paragraphes autour des <b> sur une seule ligne
        text = text.replaceAll("(^<b>.*?</b>)", "<p>$1</p>\n\n");

        // Paragraphes autour des <q> sur une seule ligne
        text = text.replaceAll("(^<q>.*?</q>)", "<p>$1</p>\n\n");



        //Supprimer les <p> des <ul>, <img>, <h3> et <div marge>
        text = text.replaceAll("<p>(</ul>)</p>", "$1");
        text = text.replaceAll("<p>( ?<img .*?/>)</p>", "$1");
        text = text.replaceAll("<p>(<h3>.*?</h3>)</p>", "$1");
        text = text.replaceAll("<p>(<div class=\"marge\">.*</div>)</p>", "$1");

        System.out.println(text);

        return text;
    }

    private String getHtml(Article article, String text)
    {
        String html = ("<html>" +
                        "<head>" +
                            "<meta charset=\"UTF-8\">" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">" +
                            "<title>"+article.getTitre()+"</title>" +
                            "<link rel=\"stylesheet\" href=\""+this.CSS_FOLDER+"style.css\" type=\"text/css\">" +
                            "<script src=\"http://code.jquery.com/jquery-1.11.2.min.js\"></script>" +
                            "<script type=\"text/javascript\" src=\""+this.JS_FOLDER+"libjs.js\"></script>" +
                        "</head>" +
                        "<body>" +
                            "<div class=\"lines\"></div>" +
                            "<div id=\"popup_img\" class=\"popup\"><img id=\"imgpopup\" class=\"imgpopup\" src=\"\"/></div>" +
                            "<div class=\"container\">" +
                                "<div class=\"top\">" +
                                    "<div class=\"header\">" +
                                        "<div class=\"author\">"+article.getRawAuthors()+" - "+article.getDate()+"</div>" +
                                        "<div class=\"title\">"+article.getTitre()+"</div>" +
                                        "<div class=\"sub-title\">"+article.getRawSousTitre()+"</div>" +
                                    "</div>" +
                                "</div>" +
                                "<div class=\"content\">" +
                                    text +
                                "</div>" +
                            "</div>" +
                        "</body>" +
                    "</html>");

        return html;


    }
}
