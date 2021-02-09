package com.umuttepe.studentalumni.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umuttepe.studentalumni.dao.entity.*;
import com.umuttepe.studentalumni.dto.survey.SurveyAddDTO;
import com.umuttepe.studentalumni.dto.survey.question.SurveyQuestionAddDTO;
import com.umuttepe.studentalumni.dto.survey.question.SurveyQuestionDataDTO;
import com.umuttepe.studentalumni.dto.survey.question.SurveyQuestionUpdateDTO;
import com.umuttepe.studentalumni.dto.survey.question.answer.SurveyQuestionAnswerAddDTO;
import com.umuttepe.studentalumni.exception.survey.SurveyNotFoundException;
import com.umuttepe.studentalumni.service.SurveyQuestionAnswerService;
import com.umuttepe.studentalumni.service.SurveyQuestionService;
import com.umuttepe.studentalumni.service.SurveyService;
import com.umuttepe.studentalumni.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/surveys")
public class SurveyController {

    @Autowired
    private UserService userService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyQuestionService surveyQuestionService;

    @Autowired
    private SurveyQuestionAnswerService surveyQuestionAnswerService;

    @GetMapping
    public List<SurveyEntity> listAll() {
        return surveyService.getSurveys();
    }

    @PostMapping
    public SurveyEntity addSurvey(@Valid @RequestBody SurveyAddDTO survey) throws Exception {
        return surveyService.addSurvey(survey);
    }

    @GetMapping("/{id}")
    public SurveyEntity find(@PathVariable(name = "id") Integer id) throws Exception {
        return surveyService.getSurvey(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") Integer id, @Valid @RequestBody SurveyAddDTO surveyDto) throws Exception {
        SurveyEntity survey = surveyService.getSurvey(id);
        survey.setName(surveyDto.getName());
        surveyService.updateSurvey(survey);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) throws Exception {
        surveyService.delete(id);
        return ResponseEntity.ok("ok");
    }


    @PostMapping("/questions")
    public SurveyEntity addQuestion(@Valid @RequestBody SurveyQuestionAddDTO survey) throws Exception {
        return surveyQuestionService.addQuestion(survey);
    }

    @GetMapping("/questions/{id}")
    public SurveyQuestionEntity getQuestion(@PathVariable(name = "id") Integer id) throws Exception {

        return surveyQuestionService.getSurveyQuestion(id);
    }

    @PutMapping("/questions/{id}")
    public SurveyQuestionEntity addQuestion(@PathVariable(name = "id") Integer id, @Valid @RequestBody SurveyQuestionUpdateDTO survey) throws Exception {
        survey.setId(id);
        return surveyQuestionService.updateQuestion(survey);
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable(name = "id") Integer id) throws Exception {
        surveyQuestionService.delete(id);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/questions/{question_id}/answers")
    public SurveyQuestionAnswerEntity findAnswer(Authentication authentication, @PathVariable(name = "question_id") Integer id) throws SurveyNotFoundException {
        UserEntity user = userService.loadByUsername(authentication.getName());
        SurveyQuestionEntity question = surveyQuestionService.getSurveyQuestion(id);
        return surveyQuestionAnswerService.find(question, user);
    }

    @GetMapping("/questions/{question_id}/answers-full")
    public List<SurveyQuestionAnswerEntity> getAllAnswersFull(@PathVariable(name = "question_id") Integer id) throws SurveyNotFoundException, JsonProcessingException {
        SurveyQuestionEntity question = surveyQuestionService.getSurveyQuestion(id);
        return surveyQuestionAnswerService.findByQuestion(question);
    }

    @GetMapping("/questions/{question_id}/percents")
    public Map<String, Integer> getAllAnswerPercent(@PathVariable(name = "question_id") Integer id) throws SurveyNotFoundException, JsonProcessingException {
        Map<String, Integer> percents = new HashMap<>();
        Logger logger = LoggerFactory.getLogger(SurveyController.class);
        SurveyQuestionEntity question = surveyQuestionService.getSurveyQuestion(id);
        List<SurveyQuestionAnswerEntity> answers = surveyQuestionAnswerService.findByQuestion(question);
        List<SurveyQuestionDataDTO> questionDatas = Arrays.asList(new ObjectMapper().readValue(question.getData(), SurveyQuestionDataDTO[].class));
        questionDatas.forEach(a -> {
            percents.put(a.getValue(), 0);
        });
        if (question.getType() == SurveyQuestionStatus.CHECKBOX) {
            answers.forEach(r -> {
                String answer = r.getAnswer();
                try {
                    String[] selectedData = new ObjectMapper().readValue(answer, String[].class);
                    for (String selectedDatum : selectedData) {
                        if (percents.containsKey(selectedDatum)) {
                            Integer total = percents.get(selectedDatum);
                            total++;
                            percents.replace(selectedDatum, total);
                        }

                    }
                } catch (Exception e) {
                    // dont care!!
                }
            });
        }
        if (question.getType() == SurveyQuestionStatus.RADIO) {
            answers.forEach(r -> {
                String answer = r.getAnswer();
                if (percents.containsKey(answer)) {
                    Integer total = percents.get(answer);
                    total++;
                    percents.replace(answer, total);
                }
            });
        }
        return percents;
    }

    @PostMapping("/questions/{question_id}/answers")
    public SurveyQuestionAnswerEntity addAnswer(Authentication authentication, @PathVariable(name = "question_id") Integer questionId, @RequestBody @Valid SurveyQuestionAnswerAddDTO answer) throws SurveyNotFoundException {
        SurveyQuestionEntity question = surveyQuestionService.getSurveyQuestion(questionId);
        UserEntity user = userService.loadByUsername(authentication.getName());
        return surveyQuestionAnswerService.addAnswer(answer, user, question, question.getSurvey());
    }

    @PutMapping("/answers/{id}")
    public SurveyQuestionAnswerEntity updateAnswer(@PathVariable Long id, @RequestBody SurveyQuestionAnswerAddDTO answerData) throws Exception {
        return surveyQuestionAnswerService.updateAnswer(id, answerData);
    }



}
