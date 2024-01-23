package org.project.dailyplanner.dailyplanner.entity;

import lombok.Getter;
import lombok.Setter;
import org.project.dailyplanner.dailyplanner.dto.DailyPlannerRequesDto;

@Getter
@Setter
public class DailyPlanner {
    private int id;
    private String name;
    private String title;
    private String container;
    private String password;
    private int date; //todo: 찾아보기 데이트 필드

    public DailyPlanner(DailyPlannerRequesDto requestDto) {
        this.name = requestDto.getName();
        this.title = requestDto.getTitle();
        this.container = requestDto.getContainer();
        this.password = requestDto.getPassword();
    }
    public DailyPlanner(){

    }
}

