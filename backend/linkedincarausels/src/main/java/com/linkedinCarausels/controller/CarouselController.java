package com.linkedinCarausels.controller;


import com.linkedinCarausels.dto.CarouselRequest;
import com.linkedinCarausels.entity.SlideSet;
import com.linkedinCarausels.service.CarouselService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/carousel")
public class CarouselController {

    private final CarouselService carouselService;

    public CarouselController(CarouselService carouselService) {
        this.carouselService = carouselService;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<String>> generateSlides(@RequestBody CarouselRequest request) {
       return ResponseEntity.ok(carouselService.generateSlides(request.getContent()));
    }

    @PostMapping("/save")
    public ResponseEntity<SlideSet> saveCarousel(@RequestBody CarouselRequest request){
        return ResponseEntity.ok(carouselService.saveCarousel(request));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SlideSet>> getAllCarousels(){
        return ResponseEntity.ok(carouselService.getAllCarousels());
    }

    @GetMapping("/export/pdf/{id}")
    public ResponseEntity<byte[]> exportPdf(@PathVariable long id) throws java.io.IOException{
        File pdf = carouselService.exportCarouselAsPdf(id);

        byte[] fileBytes = java.nio.file.Files.readAllBytes(pdf.toPath());

        return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + pdf.getName()).header("Content-Type","application/pdf").body(fileBytes);
    }
}
