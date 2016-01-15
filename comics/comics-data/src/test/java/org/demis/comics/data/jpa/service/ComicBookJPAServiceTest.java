package org.demis.comics.data.jpa.service;

import org.demis.comics.data.jpa.PersistenceJPAConfiguration;
import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@ContextHierarchy({
        @ContextConfiguration(classes = PersistenceJPAConfiguration.class)
})
public class ComicBookJPAServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("comicBookJPAService")
    public ComicBookJPAService service;

    @Test
    void testCreate() {
        ComicBookEntity comicBook = new ComicBookEntity();
        comicBook.setTitle("Adèle et la bête");
        comicBook.setIsbn("9782203037090");
        comicBook.setSummary("Paris, le 4 novembre 1911. Au muséum d'histoire naturelle du jardin des Plantes, un oeuf préhistorique éclot pour libérer un ptérodactyle qui finit par s'élancer dans le ciel de la capitale. Il va s'ensuivre une série d'agressions particulièrement violentes qui finissent par alarmer les esprits. Au même instant, à Lyon, Philippe Boutardieu, auparavant joyeux, est désespéré. Il paraît très inquiet. Les personnes qui voient le monstre meurent les unes après les autres. Le 16 novembre de la même année, Antoine Zborowsky est dans le train vers Paris. Il aborde une jeune femme qui dit s'appeler Edith Rabatjoie. Cette femme n'est autre qu'Adèle Blanc-Sec, qui sillonne la région discrètement pour écrire des romans. Adèle apprend que le gouvernement offre une prime pour la capture du ptérodactyle. Elle compte bien chasser le monstre et toucher la récompense. Son amant, Lucien Ripol, un cambrioleur recherché, perdra la vie dans l'aventure. Adèle va enquêter. Elle est persuadée qu'un lien existe entre la mort de son amant et le ptérodactyle ressuscité.");

        service.create(comicBook);

        List<ComicBookEntity> comicBookEntities = service.findAll();
        Assert.assertNotNull(comicBookEntities);
        Assert.assertEquals(comicBookEntities.size(), 1);

        ComicBookEntity result = comicBookEntities.get(0);
        Assert.assertEquals(result.getIsbn(), comicBook.getIsbn());
        Assert.assertEquals(result.getTitle(), comicBook.getTitle());
        Assert.assertEquals(result.getSummary(), comicBook.getSummary());
    }

    @AfterMethod
    public void deleteAll() throws EntityNotFoundException {
        List<ComicBookEntity> comicBookEntities = service.findAll();

        for (ComicBookEntity comicBookEntity : comicBookEntities) {
            service.delete(comicBookEntity.getId());
        }
    }
}
