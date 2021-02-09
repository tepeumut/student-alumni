package com.umuttepe.studentalumni.dao;

import com.umuttepe.studentalumni.dao.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyDao extends JpaRepository<SurveyEntity, Integer> {
}
