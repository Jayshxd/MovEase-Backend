package com.jayesh.bookmyshow.service;

import com.jayesh.bookmyshow.dto.request.ScreenRequestDto;
import com.jayesh.bookmyshow.dto.request.TheatreRequestDto;
import com.jayesh.bookmyshow.dto.response.ScreenResponseDto;
import com.jayesh.bookmyshow.dto.response.TheatreResponseDto;
import com.jayesh.bookmyshow.entities.Screen;
import com.jayesh.bookmyshow.entities.Theatre;
import com.jayesh.bookmyshow.repo.TheatreRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TheatreService {
    private final TheatreRepo theatreRepo;

    public List<TheatreResponseDto> findTheatres(String name,String city,String address) {
        List<Theatre> theatres = theatreRepo.findAll();

        if(name!=null && !name.isEmpty()){
        theatres=theatres.stream().filter(x->x.getName().toLowerCase().contains(name.toLowerCase())).toList();
        }
        if(city!=null && !city.isEmpty()){
            theatres=theatres.stream().filter(x->x.getCity().toLowerCase().contains(city.toLowerCase())).toList();
        }
        if(address!=null && !address.isEmpty()){
            theatres=theatres.stream().filter(x->x.getAddress().toLowerCase().contains(address.toLowerCase())).toList();
        }
        return theatres.stream().map(TheatreResponseDto::new).collect(Collectors.toList());
    }

    public TheatreResponseDto getTheatreById(Long id) {
        Theatre res = theatreRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Theatre not found"));
        return new TheatreResponseDto(res);
    }

    //create a theater
    public TheatreResponseDto createTheatre(TheatreRequestDto req) {
        Theatre theatre = new Theatre();
        mapTheatreDtoToEntity(theatre,req);
        Theatre saved = theatreRepo.save(theatre);
        return new TheatreResponseDto(saved);
    }

    @Transactional
    public List<TheatreResponseDto> createTheatresInBulk(List<TheatreRequestDto> req) {
        List<Theatre> theatres = req.stream().map(
                dto->{
                    Theatre theatre = new Theatre();
                    mapTheatreDtoToEntity(theatre,dto);
                    return theatre;
                }
        ).toList();
        List<Theatre> temp =theatreRepo.saveAll(theatres);
        return temp.stream().map(TheatreResponseDto::new).toList();
    }


    //PUT : for updating theater
    public TheatreResponseDto updateTheatreByPut(Long id,TheatreRequestDto req) {
        Theatre theatre = theatreRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Theatre not found"));
        mapTheatreDtoToEntity(theatre,req);
        Theatre temp = theatreRepo.save(theatre);
        return new TheatreResponseDto(temp);
    }

    //Patch : for Updating theater
    public TheatreResponseDto updateTheatreDetails(Long id,TheatreRequestDto req) {
        Theatre theatre = theatreRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Theatre not found"));
        if(req.getName()!=null && !req.getName().isEmpty()){
            theatre.setName(req.getName());
        }
        if(req.getCity()!=null && !req.getCity().isEmpty()){
            theatre.setCity(req.getCity());
        }
        if(req.getAddress()!=null && !req.getAddress().isEmpty()){
            theatre.setAddress(req.getAddress());
        }
        Theatre temp = theatreRepo.save(theatre);
        return new TheatreResponseDto(temp);
    }

    public void deleteTheatre(Long id) {
        theatreRepo.deleteById(id);
    }

    //helper mehthod
    public void mapTheatreDtoToEntity(Theatre theatre, TheatreRequestDto theatreRequestDto) {
        theatre.setName(theatreRequestDto.getName());
        theatre.setAddress(theatreRequestDto.getAddress());
        theatre.setCity(theatreRequestDto.getCity());
    }

}
