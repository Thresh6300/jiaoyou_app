/**
  * Copyright 2020 bejson.com 
  */
package com.qiqi.jiaoyou_app.vo;
import java.util.List;

/**
 * Auto-generated: 2020-05-11 9:49:11
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Result {

    private int score;
    private List<Face_list> face_list;
    public void setScore(int score) {
         this.score = score;
     }
     public int getScore() {
         return score;
     }

    public void setFace_list(List<Face_list> face_list) {
         this.face_list = face_list;
     }
     public List<Face_list> getFace_list() {
         return face_list;
     }

}