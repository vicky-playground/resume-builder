
package com.sedia.resume.service;

import com.sedia.resume.repository.AddressMapper;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {

    final AddressMapper addressMapper;

    @Cacheable("address")
    public List<String> getCityList() {
        log.debug("取得所有城市資料");
        return addressMapper.getAllCities().stream().distinct().collect(Collectors.toList());
    }

    @Cacheable(value = "address", key = "#city")
    public List<String> getTownList(String city) {
        log.debug("取得 {} 的所有鄉鎮資料", city);
        return addressMapper.getAllTowns(city);
    }

}
