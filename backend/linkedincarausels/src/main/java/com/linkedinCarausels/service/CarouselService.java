package com.linkedinCarausels.service;


import com.linkedinCarausels.dto.CarouselRequest;
import com.linkedinCarausels.entity.SlideSet;
import com.linkedinCarausels.repository.SlideSetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarouselService {


    private final SlideSetRepository slideSetRepository;

    public CarouselService(SlideSetRepository slideSetRepository) {
        this.slideSetRepository = slideSetRepository;
    }

    public List<String> generateSlides(String content){
        String[] splitSlide =  content.split("---");
        List<String> slides = new ArrayList<>();
        for(String slide: splitSlide){
            slides.add(slide.trim());
        }

        return slides;
    }

    public SlideSet saveCarousel(CarouselRequest request){
        List<String> slides = generateSlides(request.getContent());
        SlideSet slideSet = SlideSet.builder().title(request.getTitle())
                .slides(slides).createdBy(request.getCreatedBy()).createdAt(LocalDateTime.now().toString()).build();


        return slideSetRepository.save(slideSet);

    }

    public List<SlideSet> getAllCarousels(){
        return slideSetRepository.findAll();
    }
}
