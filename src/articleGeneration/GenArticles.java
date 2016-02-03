package articleGeneration;

import graphGeneration.generation.Article;
import graphGeneration.generation.GenGraphs;

import java.util.List;

/**
 * Generate HTML articles
 * @author Arno Simon
 */
public class GenArticles {

    public static void execute()
    {
        HTMLBuildArticle build = new HTMLBuildArticleImpl();
        List<Article> articles = GenGraphs.getAllEntries().getArticlelist();

        // build html view for each article
        for(Article a : articles)
        {
            build.create(a);
        }
    }
}
