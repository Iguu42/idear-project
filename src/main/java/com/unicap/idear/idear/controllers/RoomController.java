package com.unicap.idear.idear.controllers;

import com.unicap.idear.idear.dtos.RoomCreationDto;
import com.unicap.idear.idear.dtos.RoomUpdateDto;
import com.unicap.idear.idear.models.RoomModel;
import com.unicap.idear.idear.repositories.RoomRepository;
import com.unicap.idear.idear.services.RoomService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    private RoomRepository repository;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/")
    @Transactional
    public ResponseEntity createRoom(@RequestBody @Valid RoomCreationDto roomCreationDto, UriComponentsBuilder uriComponentsBuilder) {
        RoomModel savedRoom = roomService.saveRoom(roomCreationDto);

        var uri = uriComponentsBuilder.path("rooms/{id}").buildAndExpand(savedRoom.getIdRoom()).toUri();

        return ResponseEntity.created(uri).body(savedRoom);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity editRoom(@PathVariable(value = "id") long idRoom, @RequestBody @Valid RoomUpdateDto roomUpdateDto) {

        Optional<RoomModel> updatedRoom = roomService.updateRoom(idRoom, roomUpdateDto);

        if (updatedRoom.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedRoom.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Room not found, the specified room ID does not exist.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneRoom(@PathVariable(value = "id") long idRoom) {
        Optional<RoomModel> room = roomService.getOneRoom(idRoom);

        if (room.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found, the specified room ID does not exist.");
        }

        room.get().add(linkTo(methodOn(RoomController.class).getAllRooms()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(room.get());
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllRooms() {
        List<RoomModel> roomsList = roomService.getAllRooms();

        if (!roomsList.isEmpty()) {
            for (RoomModel room : roomsList) {
                Long idRoom = room.getIdRoom();
                room.add(linkTo(methodOn(RoomController.class).getOneRoom(idRoom)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(roomsList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable(value = "id") long idRoom) {
        boolean deleted = roomService.deleteRoom(idRoom);

        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Room deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Room not found, the specified room ID does not exist.");
    }
}
