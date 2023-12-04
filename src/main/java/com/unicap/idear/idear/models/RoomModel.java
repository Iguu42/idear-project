package com.unicap.idear.idear.models;

import com.unicap.idear.idear.dtos.RoomCreationDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "ROOMS")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idRoom")
public class RoomModel extends RepresentationModel<RoomModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRoom;

    private String roomName;
    private ProblemModel problemModel;
    private TeamModel teamModel;
    private MethodModel methodModel;
    private IdeaModel ideaModel;
    private CommentModel comments;

    public RoomModel(RoomCreationDto roomCreationDto) {
        this.roomName = roomCreationDto.roomName();
        this.problemModel = new ProblemModel(roomCreationDto.problemDataDto());
        this.teamModel = new TeamModel(roomCreationDto.teamDataDto());
        this.methodModel = new MethodModel(roomCreationDto.methodRecordDto());
    }

}
