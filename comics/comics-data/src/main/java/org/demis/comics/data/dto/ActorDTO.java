package org.demis.comics.data.dto;

public class ActorDTO extends AbstractDTO implements DTO {

    private String coverName;

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

}
