package com.qiqi.jiaoyou_app.vo;

import lombok.Data;

@Data
public class Face {
    private String image;
    private String image_type;
    private String face_type;
    private String quality_control;
    private String liveness_control;
}