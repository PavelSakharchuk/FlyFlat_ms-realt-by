package com.spy686.fly.flat.ms.realt.by.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Data
@Accessors(chain = true)
@Document(collection = "rent_flat")
public class RentFlat implements Serializable {
    @Id
    private String id;
    @Field(value = "source")
    private Source source = Source.REALT_BY;
    private long objectId;
    private boolean highlighted;
    private String link;
    private String imageLink;
    private String price;
    private Integer priceUsd;
    private String updatedDate;
    private String title;
    private String location;
    private String originalRoomString;
    private Integer roomCount;
    private String flatParams;
    private String details;
    private String sellerName;
    private List<String> sellerPhones = new ArrayList<>();
    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime lastUpdate = LocalDateTime.now();


    @Getter
    @AllArgsConstructor
    public enum RoomCount {
        ROOM_COUNT_NA("NA", null),
        ROOM_COUNT_0("комната", 0),
        ROOM_COUNT_1("1-комн.", 1),
        ROOM_COUNT_2("2-комн.", 2),
        ROOM_COUNT_3("3-комн.", 3),
        ROOM_COUNT_4("4-комн.", 4),
        ROOM_COUNT_5("5-комн.", 5),
        ROOM_COUNT_6("6-комн.", 6),
        ROOM_COUNT_7("7-комн.", 7),
        ROOM_COUNT_8("8-комн.", 8),
        ROOM_COUNT_9("9-комн.", 9);

        private String roomCountString;
        private Integer roomCount;

        public static RoomCount valueByRoomCountString(String roomCountString) {
            return Arrays.stream(RoomCount.values())
                    .filter(column -> column.getRoomCountString().equals(roomCountString))
                    .findFirst()
//                    .orElse(ROOM_COUNT_NA);
                    .orElseThrow(() -> new RuntimeException(roomCountString));
        }
    }
}
