package com.sedia.resume.repository;

import com.sedia.resume.entity.AddressEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AddressMapper {

    // @Select("SELECT CITY FROM ADDRESS")
    List<String> getAllCities();

    // @Select("SELECT * FROM ADDRESS WHERE city = #{city}")
    List<String> getAllTowns(String city);

    // @Select("SELECT CITY FROM ADDRESS WHERE id = #{id}")
    // String selectCityById(int id);
    //
    // @Select("SELECT TOWN FROM ADDRESS WHERE id = #{id}")
    // String selectTownById(int id);
    //

}
