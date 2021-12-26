package com.sedia.resume.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sedia.resume.service.AddressService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    final AddressService service;

    @GetMapping("/cities")
    public List<String> getCities() {
        return service.getCityList();
    }

    @GetMapping("/towns/{city}")
    public List<String> getTowns(@PathVariable String city) {
        return service.getTownList(city);
    }

}
