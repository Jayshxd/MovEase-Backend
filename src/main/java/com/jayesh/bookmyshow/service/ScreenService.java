package com.jayesh.bookmyshow.service;

import com.jayesh.bookmyshow.dto.request.ScreenRequestDto;
import com.jayesh.bookmyshow.dto.response.ScreenResponseDto;
import com.jayesh.bookmyshow.entities.Screen;
import com.jayesh.bookmyshow.entities.Theatre;
import com.jayesh.bookmyshow.repo.ScreenRepo;
import com.jayesh.bookmyshow.repo.TheatreRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScreenService {
    private final ScreenRepo screenRepo;
    private final TheatreRepo theatreRepo;


    public ScreenResponseDto createAScreen(Long id, ScreenRequestDto req) {
        Theatre theatre = theatreRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Theatre with id " + id + " not found"));
        Screen screen = new Screen();
        mapDtoToScreen(req, screen);
        theatre.addScreen(screen);
        Screen res = screenRepo.save(screen);
        return new ScreenResponseDto(res);
    }


    public List<ScreenResponseDto> createScreensInBulk(Long id, List<ScreenRequestDto> req) {
        Theatre theatre = theatreRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Theatre with id " + id + " not found"));
        List<Screen> screens = req.stream().map(
                screen ->{
                    Screen s = new Screen();
                    mapDtoToScreen(screen, s);
                    theatre.addScreen(s);
                    return s;
                }
        ).collect(Collectors.toList());
        List<Screen> temp = screenRepo.saveAll(screens);
        return  temp.stream().map(ScreenResponseDto::new).collect(Collectors.toList());
    }

    public List<ScreenResponseDto> getAllScreens() {
        List<Screen> res = screenRepo.findAll();
        return res.stream().map(ScreenResponseDto::new).collect(Collectors.toList());
    }

    public ScreenResponseDto getScreenById(Long id) {
        Screen screen = screenRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Screen with id " + id + " not found"));
        return new ScreenResponseDto(screen);
    }

    public ScreenResponseDto updateScreenDetails(Long id, ScreenRequestDto req) {
        Screen screen = screenRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Screen with id " + id + " not found"));
        if(req.getName()!=null && !req.getName().isEmpty()){
        screen.setName(req.getName());
        }
        if(req.getTotalSeats()!=0){
        screen.setTotalSeats(req.getTotalSeats());
        }
        Screen res = screenRepo.save(screen);
        return new ScreenResponseDto(res);
    }

    public ScreenResponseDto updateByPut(Long id, ScreenRequestDto req) {
        Screen screen = screenRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Screen with id " + id + " not found"));
        mapDtoToScreen(req,screen);
        Screen res = screenRepo.save(screen);
        return new ScreenResponseDto(res);
    }

    public void deleteScreen(Long id) {
        screenRepo.deleteById(id);
    }

    public void mapDtoToScreen(ScreenRequestDto dto, Screen screen) {
        screen.setName(dto.getName());
        screen.setTotalSeats(dto.getTotalSeats());

    }

    public List<ScreenResponseDto> getAllScreensForThisTheatre(Long id) {
        Theatre theatre = theatreRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Theatre with id " + id + " not found"));
        return theatre.getScreens().stream().map(ScreenResponseDto::new).collect(Collectors.toList());
    }

    public void deleteAllScreensOfATheatre(Long id) {
        Theatre theatre = theatreRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Theatre with id " + id + " not found"));
        theatre.getScreens().clear();
        theatreRepo.save(theatre);
    }
}
