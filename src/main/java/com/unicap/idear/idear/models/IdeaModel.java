package com.unicap.idear.idear.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = "idIdea")
@NoArgsConstructor
@AllArgsConstructor
public class IdeaModel extends RepresentationModel<IdeaModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIdea;

    private String ideaDescription;
    private String author;

    public IdeaModel(String ideaDescription, UserModel user) {
        this.ideaDescription = ideaDescription;
        this.author = user.getUsername();
    }
}
