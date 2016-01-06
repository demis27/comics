package org.demis.comics.data.dto;

public class ComicBookDTO extends AbstractDTO implements DTO {

    private String title;

    public ComicBookDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
