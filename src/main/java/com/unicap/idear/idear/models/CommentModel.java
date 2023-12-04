package com.unicap.idear.idear.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "idComment")
@NoArgsConstructor
@AllArgsConstructor
public class CommentModel extends RepresentationModel<CommentModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComment;

    private String commentDescription;
    private String author;

    private RoomModel room;

    public CommentModel(String commentDescription, UserModel user) {
        this.commentDescription = commentDescription;
        this.author = user.getUsername();
    }
}
