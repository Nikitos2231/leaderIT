package com.example.leaderIt_project.controllers;

import com.example.leaderIt_project.custom_exceptions.InvalidParametersForOccasionException;
import com.example.leaderIt_project.custom_exceptions.InvalidParametersForPayloadException;
import com.example.leaderIt_project.dto.OccasionDTO;
import com.example.leaderIt_project.services.OccasionService;
import com.example.leaderIt_project.validators.OccasionValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/occasions")
public class OccasionController {

    private final OccasionService occasionService;
    private final OccasionValidator occasionValidator;

    @Autowired
    public OccasionController(OccasionService occasionService, OccasionValidator occasionValidator) {
        this.occasionService = occasionService;
        this.occasionValidator = occasionValidator;
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

        if (!occasionService.saveOccasion(occasionDTO)) {
            throw new InvalidParametersForOccasionException("Wrong key!");
        }
    }

    @GetMapping("/statistic")
    public Map<String, Integer> getStatistics(@RequestParam(value = "start_date", required = false, defaultValue = "2000-01-01") String startDate,
                                              @RequestParam(value = "end_date", required = false, defaultValue = "2025-01-01") String endDate) {
        return occasionService.getStatistics(startDate, endDate);
    }
}
