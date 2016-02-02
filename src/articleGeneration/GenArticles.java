package articleGeneration;

import graphGeneration.generation.Article;
import graphGeneration.generation.GenGraphs;

import java.util.List;

/**
 * Created by Arno on 02/02/2016.
 */
public class GenArticles {

    public static void execute()
    {
        HTMLBuildArticle build = new HTMLBuildArticleImpl();
        List<Article> articles = GenGraphs.getAllEntries().getArticlelist();


        for(Article a : articles)
        {
            build.create(a);break;
        }
    }
}
