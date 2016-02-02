package articleGeneration;

import graphGeneration.generation.Article;

/**
 * Created by Arno on 02/02/2016.
 */
public interface HTMLBuildArticle {

    /**
     * Create the HTML file for the given article
     * @param : Article
     */
    public void create(Article article);
}

