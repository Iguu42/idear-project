package com.unicap.idear.idear.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RoomUpdateDto(
        String roomName,
        ProblemDataDto problemDataDto,
        TeamDataDto teamDataDto,
        MethodRecordDto methodRecordDto
) {
}
