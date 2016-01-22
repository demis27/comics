package org.demis.comics.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.demis.comics.business.service.ComicBookBusinessService;
import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.demis.comics.web.RestConfiguration;
import org.demis.comics.web.dto.ComicBookWebDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(classes = RestConfiguration.class)
})
public class ComicBookControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ComicBookBusinessService businessService;
    private MockMvc mockMvc;

    @BeforeClass
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        List<ComicBookEntity> entities = businessService.findAll();
        for (ComicBookEntity entity : entities) {
            businessService.delete(entity.getId());
        }

    }

    @Test
    public void testGelAll() throws Exception {
        mockMvc.perform(get("/comicBook").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }


    @Test
    public void testLifeCycle() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Test GET collection
        mockMvc.perform(get("/comicBook").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        // Test POST a new resource and get the id
        String newComicBook = "{ \"title\": \"Adele et la bête\", \"isbn\": \"9782203037090\", \"summary\": \"Paris, le 4 novembre 1911. Au muséum d'histoire naturelle du jardin des Plantes, un oeuf préhistorique éclot pour libérer un ptérodactyle qui finit par s'élancer dans le ciel de la capitale. Il va s'ensuivre une série d'agressions particulièrement violentes qui finissent par alarmer les esprits. Au même instant, à Lyon, Philippe Boutardieu, auparavant joyeux, est désespéré. Il paraît très inquiet. Les personnes qui voient le monstre meurent les unes après les autres. Le 16 novembre de la même année, Antoine Zborowsky est dans le train vers Paris. Il aborde une jeune femme qui dit s'appeler Edith Rabatjoie. Cette femme n'est autre qu'Adèle Blanc-Sec, qui sillonne la région discrètement pour écrire des romans. Adèle apprend que le gouvernement offre une prime pour la capture du ptérodactyle. Elle compte bien chasser le monstre et toucher la récompense. Son amant, Lucien Ripol, un cambrioleur recherché, perdra la vie dans l'aventure. Adèle va enquêter. Elle est persuadée qu'un lien existe entre la mort de son amant et le ptérodactyle ressuscité.\"}";
        MvcResult postResponse = mockMvc.perform(post("/comicBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComicBook))
                .andExpect(status().isCreated()).andReturn();
        ComicBookWebDTO postDTOResponse = mapper.readValue(postResponse.getResponse().getContentAsString(), ComicBookWebDTO.class);
        // Test GET collection and test we return a resource
        mockMvc.perform(get("/comicBook")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // Test GET the created resource
        mockMvc.perform(get("/comicBook/" + postDTOResponse.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // Test PUT modify the resource
        String modifiedComicBook = "{ \"title\": \"Adèle et la bête\", \"isbn\": \"9782203037090\", \"summary\": \"Paris, le 4 novembre 1911. Au muséum d'histoire naturelle du jardin des Plantes, un oeuf préhistorique éclot pour libérer un ptérodactyle qui finit par s'élancer dans le ciel de la capitale. Il va s'ensuivre une série d'agressions particulièrement violentes qui finissent par alarmer les esprits. Au même instant, à Lyon, Philippe Boutardieu, auparavant joyeux, est désespéré. Il paraît très inquiet. Les personnes qui voient le monstre meurent les unes après les autres. Le 16 novembre de la même année, Antoine Zborowsky est dans le train vers Paris. Il aborde une jeune femme qui dit s'appeler Edith Rabatjoie. Cette femme n'est autre qu'Adèle Blanc-Sec, qui sillonne la région discrètement pour écrire des romans. Adèle apprend que le gouvernement offre une prime pour la capture du ptérodactyle. Elle compte bien chasser le monstre et toucher la récompense. Son amant, Lucien Ripol, un cambrioleur recherché, perdra la vie dans l'aventure. Adèle va enquêter. Elle est persuadée qu'un lien existe entre la mort de son amant et le ptérodactyle ressuscité.\"}";
        MvcResult putResponse = mockMvc.perform(put("/comicBook/" + postDTOResponse.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(modifiedComicBook))
                .andExpect(status().isOk()).andReturn();
        ComicBookWebDTO putDTOResponse = mapper.readValue(putResponse.getResponse().getContentAsString(), ComicBookWebDTO.class);
        Assert.assertEquals(putDTOResponse.getTitle(), "Adèle et la bête");
        // Test DELETE the create resource
        mockMvc.perform(delete("/comicBook/" + postDTOResponse.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        // Test GET no more resource
        mockMvc.perform(get("/comicBook")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
