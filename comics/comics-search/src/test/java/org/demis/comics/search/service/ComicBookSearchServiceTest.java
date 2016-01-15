package org.demis.comics.search.service;

import org.demis.comics.data.Range;
import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.demis.comics.search.ElasticSearchClientFactoryBean;
import org.demis.comics.search.SearchConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

@ContextHierarchy({
        @ContextConfiguration(classes = SearchConfig.class),
        @ContextConfiguration(classes = ElasticSearchClientFactoryBean.class)
})
public class ComicBookSearchServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("comicBookSearchService")
    private ComicBookSearchService service;

    //@Test
    public void create() throws ExecutionException, InterruptedException {
        ComicBookEntity entity = new ComicBookEntity();
        entity.setId(1L);
        entity.setTitle("Adèle et la bête");
        entity.setIsbn("9782203037090");
        entity.setSummary("Paris, le 4 novembre 1911. Au muséum d'histoire naturelle du jardin des Plantes, un oeuf préhistorique éclot pour libérer un ptérodactyle qui finit par s'élancer dans le ciel de la capitale. Il va s'ensuivre une série d'agressions particulièrement violentes qui finissent par alarmer les esprits. Au même instant, à Lyon, Philippe Boutardieu, auparavant joyeux, est désespéré. Il paraît très inquiet. Les personnes qui voient le monstre meurent les unes après les autres. Le 16 novembre de la même année, Antoine Zborowsky est dans le train vers Paris. Il aborde une jeune femme qui dit s'appeler Edith Rabatjoie. Cette femme n'est autre qu'Adèle Blanc-Sec, qui sillonne la région discrètement pour écrire des romans. Adèle apprend que le gouvernement offre une prime pour la capture du ptérodactyle. Elle compte bien chasser le monstre et toucher la récompense. Son amant, Lucien Ripol, un cambrioleur recherché, perdra la vie dans l'aventure. Adèle va enquêter. Elle est persuadée qu'un lien existe entre la mort de son amant et le ptérodactyle ressuscité.");
        service.create(entity);
    }

    @Test
    public void simpleSearch() throws ExecutionException, InterruptedException {

        ComicBookEntity entity = new ComicBookEntity();
        entity.setId(1L);
        entity.setTitle("Adèle et la bête");
        entity.setIsbn("9782203037090");
        entity.setSummary("Paris, le 4 novembre 1911. Au muséum d'histoire naturelle du jardin des Plantes, un oeuf préhistorique éclot pour libérer un ptérodactyle qui finit par s'élancer dans le ciel de la capitale. Il va s'ensuivre une série d'agressions particulièrement violentes qui finissent par alarmer les esprits. Au même instant, à Lyon, Philippe Boutardieu, auparavant joyeux, est désespéré. Il paraît très inquiet. Les personnes qui voient le monstre meurent les unes après les autres. Le 16 novembre de la même année, Antoine Zborowsky est dans le train vers Paris. Il aborde une jeune femme qui dit s'appeler Edith Rabatjoie. Cette femme n'est autre qu'Adèle Blanc-Sec, qui sillonne la région discrètement pour écrire des romans. Adèle apprend que le gouvernement offre une prime pour la capture du ptérodactyle. Elle compte bien chasser le monstre et toucher la récompense. Son amant, Lucien Ripol, un cambrioleur recherché, perdra la vie dans l'aventure. Adèle va enquêter. Elle est persuadée qu'un lien existe entre la mort de son amant et le ptérodactyle ressuscité.");
        service.create(entity);

        // TODO find another solution
        Thread.sleep(1000);

        List<Long> result = service.searchEverywhere("histoire naturelle", new Range(0, 10), null);
        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0).longValue(), 1L);
    }

    @AfterMethod
    public void deleteAll() {
        service.delete(1L);
    }

}
