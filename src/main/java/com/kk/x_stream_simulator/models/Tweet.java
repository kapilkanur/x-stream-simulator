package com.kk.x_stream_simulator.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {

    private String id;
    private String username;
    private String message;
    private long timestamp;

}
