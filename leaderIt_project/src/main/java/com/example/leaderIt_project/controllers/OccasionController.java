package com.example.leaderIt_project.controllers;

import com.example.leaderIt_project.custom_exceptions.InvalidParametersForOccasionException;
import com.example.leaderIt_project.custom_exceptions.InvalidParametersForPayloadException;
import com.example.leaderIt_project.dto.OccasionDTO;
import com.example.leaderIt_project.services.ActiveDeviceService;
import com.example.leaderIt_project.services.OccasionService;
import com.example.leaderIt_project.validators.OccasionValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/occasions")
public class OccasionController {

    private final OccasionService occasionService;
    private final OccasionValidator occasionValidator;

    private final ActiveDeviceService activeListService;

    @Autowired
    public OccasionController(OccasionService occasionService, OccasionValidator occasionValidator, ActiveDeviceService activeListService) {
        this.occasionService = occasionService;
        this.occasionValidator = occasionValidator;
        this.activeListService = activeListService;
    }

    @GetMapping()
    public List<OccasionDTO> getAllOccasions() {
        return occasionService.getAllOccasions();
    }

    @GetMapping("/{id}")
    public OccasionDTO getOne(@PathVariable("id") int id) {
        return occasionService.getOne(id);
    }

    @PostMapping()
    public void saveOccasion(@RequestBody @Valid OccasionDTO occasionDTO,
                             BindingResult bindingResult) throws InvalidParametersForPayloadException, InvalidParametersForOccasionException {

        occasionValidator.validate(occasionDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder listErrors = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                listErrors.append(fieldError.getDefaultMessage()).append("; ");
            }
            throw new InvalidParametersForOccasionException(listErrors.toString());
        }
        occasionService.saveOccasion(occasionDTO);
    }
}
