package com.sedia.resume.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeRelation {

    List<Integer> education;

    List<Integer> experience;

    List<Integer> skill;

    List<Integer> language;

    List<Integer> license;

}
