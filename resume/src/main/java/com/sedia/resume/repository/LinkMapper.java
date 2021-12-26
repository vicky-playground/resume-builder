package com.sedia.resume.repository;

import com.sedia.resume.entity.LinkEntity;

import java.util.List;

public interface LinkMapper {

    List<LinkEntity> findAll(int userId);

    void save(LinkEntity user);

    void update(LinkEntity user);

    void delete(int id, int userId);

}
