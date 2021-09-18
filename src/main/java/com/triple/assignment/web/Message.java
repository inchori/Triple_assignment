package com.triple.assignment.web;

import lombok.Data;

@Data
public class Message {
    private StatusEnum status;
    private String message;
    private Object data;
}
