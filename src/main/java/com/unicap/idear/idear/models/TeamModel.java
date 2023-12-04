package com.unicap.idear.idear.models;

import com.unicap.idear.idear.dtos.TeamDataDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@EqualsAndHashCode(of = "idTeam")
@NoArgsConstructor
@AllArgsConstructor
public class TeamModel extends RepresentationModel<TeamModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTeam;

    private String teamName;
    private ArrayList<UserModel> participants;

    public TeamModel(TeamDataDto teamDataDto) {
        this.teamName = teamDataDto.teamName();
        this.participants = new ArrayList<>();
    }
}
