package com.unicap.idear.idear.services;

import com.unicap.idear.idear.dtos.RoomCreationDto;
import com.unicap.idear.idear.dtos.RoomUpdateDto;
import com.unicap.idear.idear.models.MethodModel;
import com.unicap.idear.idear.models.ProblemModel;
import com.unicap.idear.idear.models.RoomModel;
import com.unicap.idear.idear.models.TeamModel;
import com.unicap.idear.idear.repositories.RoomRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomModel saveRoom(RoomCreationDto roomCreationDto) {
        var roomModel = new RoomModel(roomCreationDto);

        return roomRepository.save(roomModel);
    }

    public Optional<RoomModel> updateRoom(long idRoom, RoomUpdateDto roomUpdateDto) {
        Optional<RoomModel> optionalRoom = roomRepository.findById(idRoom);

        if (optionalRoom.isPresent()) {
            RoomModel roomModel = optionalRoom.get();
//            BeanUtils.copyProperties(roomUpdateDto, roomModel);

            if (roomUpdateDto.roomName() != null) {
                roomModel.setRoomName(roomUpdateDto.roomName());
            }
            if (roomUpdateDto.problemDataDto() != null) {
                ProblemModel existingProblemModel = roomModel.getProblemModel();
                existingProblemModel.setProblemTitle(roomUpdateDto.problemDataDto().problemTitle());
                existingProblemModel.setProblemDescription(roomUpdateDto.problemDataDto().problemDescription());
            }
            if (roomUpdateDto.teamDataDto() != null) {
                TeamModel existingTeamModel = roomModel.getTeamModel();
                existingTeamModel.setTeamName(roomUpdateDto.teamDataDto().teamName());
            }
            if (roomUpdateDto.methodRecordDto() != null) {
                MethodModel existingMethodModel = roomModel.getMethodModel();
                existingMethodModel.setMethodName(roomUpdateDto.methodRecordDto().methodName());
                existingMethodModel.setMethodDescription(roomUpdateDto.methodRecordDto().methodDescription());
                existingMethodModel.setMethodImage(roomUpdateDto.methodRecordDto().methodImage());
            }
            return Optional.of(roomRepository.save(roomModel));
        }
        return Optional.empty();
    }

    public Optional<RoomModel> getOneRoom(long idRoom) {
        return roomRepository.findById(idRoom);
    }

    public List<RoomModel> getAllRooms() {
        return roomRepository.findAll();
    }

    public boolean deleteRoom(long idMethod) {
        Optional<RoomModel> optionalRoom = roomRepository.findById(idMethod);

        if (optionalRoom.isPresent()) {
            roomRepository.delete(optionalRoom.get());
            return true;
        }
        return false;
    }
}
