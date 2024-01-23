package org.project.dailyplanner.dailyplanner.controller;

import org.project.dailyplanner.dailyplanner.dto.DailyPlannerRequesDto;
import org.project.dailyplanner.dailyplanner.dto.DailyPlannerResponseDto;
import org.project.dailyplanner.dailyplanner.entity.DailyPlanner;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DailyPlannerController {

    private final JdbcTemplate jdbcTemplate;

    public DailyPlannerController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @PostMapping("/planner")
    public DailyPlannerResponseDto createDaliyPlanner(@RequestBody DailyPlannerRequesDto requesDto) {
        DailyPlanner dailyPlanner = new DailyPlanner(requesDto);

        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO dailyPlanner (name, title, container, password) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, dailyPlanner.getName());
                    preparedStatement.setString(2, dailyPlanner.getTitle());
                    preparedStatement.setString(3, dailyPlanner.getContainer());
                    preparedStatement.setString(4, dailyPlanner.getPassword());
                    return preparedStatement;
                },
                keyHolder);

        int id = keyHolder.getKey().intValue();
        dailyPlanner.setId(id);

        DailyPlannerResponseDto dailyPlannerResponseDto = new DailyPlannerResponseDto(dailyPlanner);

        return dailyPlannerResponseDto;
    }

    @GetMapping("/planner")
    public List<DailyPlannerResponseDto> getDailyPlanner() {
        String sql = "SELECT * FROM daily_planner";

        return jdbcTemplate.query(sql, new RowMapper<DailyPlannerResponseDto>() {
            @Override
            public DailyPlannerResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String title = rs.getString("title");
                String container = rs.getString("container");
                String password = rs.getString("password");
                return new DailyPlannerResponseDto(id, name, title, container, password); // todo 날짜 데이트 넣기
            }
        });
    }

    @PutMapping("/planner/{id}")
    public int updateDailyPlanner(@PathVariable int id, @RequestBody DailyPlannerRequesDto requesDto){
        DailyPlanner dailyPlanner = findById(id);
        if(dailyPlanner != null) {
            String sql = "UPDATE dailyPlanner SET name = ?, container = > WHERE id = ?";
            jdbcTemplate.update(sql, requesDto.getName(), requesDto.getContainer(), id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/planner/{id}")
    public int deleteDailyPlanner(@PathVariable int id) {
        DailyPlanner dailyPlanner = findById(id);
        if(dailyPlanner != null){
            //planner 삭제
            String sql = "DELETE FROM daily_planner WHERE id = ?";
            jdbcTemplate.update(sql, id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    private DailyPlanner findById(int id) {
        // DB 조회
        String sql = "SELECT * FROM daily_planner WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                DailyPlanner dailyPlanner = new DailyPlanner();
                dailyPlanner.setName(resultSet.getString("name"));
                dailyPlanner.setContainer(resultSet.getString("container"));
                return dailyPlanner;
            } else {
                return null;
            }
        }, id);
    }
}