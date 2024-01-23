package org.project.dailyplanner.dailyplanner.dto;

import lombok.Getter;
import org.project.dailyplanner.dailyplanner.entity.DailyPlanner;

@Getter
public class DailyPlannerResponseDto {
    private int id;
    private String name;
    private String title;
    private String container;
    private String password;
    private int date; //todo: 찾아보기 데이트 필드

    public DailyPlannerResponseDto(DailyPlanner dailyPlanner) {
        this.id = dailyPlanner.getId();
        this.name = dailyPlanner.getName();
        this.title = dailyPlanner.getTitle();
        this.container = dailyPlanner.getContainer();
        this.password = dailyPlanner.getPassword();
        this.date = dailyPlanner.getDate();
    }

    public DailyPlannerResponseDto(int id, String name, String title, String container, String password){
        this.id = id;
        this.name = name;
        this.title = title;
        this.container = container;
        this.password = password;
        this.date = date;
    }
}
