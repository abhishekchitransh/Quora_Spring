package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.QuestionBusinessService;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class QuestionController {

    @Autowired
    QuestionBusinessService questionBusinessService;

    @RequestMapping(method= RequestMethod.POST, path="/question/create", consumes= MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(final QuestionRequest questionRequest, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, UserNotFoundException
    {
        String [] bearerToken = authorization.split("Bearer ");


        final QuestionEntity questionEntity = new QuestionEntity();

        questionEntity.setUuid(UUID.randomUUID().toString());
        questionEntity.setContent(questionRequest.getContent());
        questionEntity.setDate(ZonedDateTime.now());

        final QuestionEntity createdQuestion = questionBusinessService.createQuestion(questionEntity, bearerToken[1]);

        QuestionResponse questionResponse = new QuestionResponse().id(createdQuestion.getUuid()).status("Question Created Successfully");

        return new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path="question/edit/{questionId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionEditResponse> editQuestion(@PathVariable("questionId") final String questionUUID, final QuestionEditRequest questionEditRequest, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, InvalidQuestionException
    {
        String [] bearerToken = authorization.split("Bearer ");

        final QuestionEntity questionEntity = questionBusinessService.editQuestionByUUID(questionUUID, questionEditRequest.getContent(), bearerToken[1]);

        QuestionEditResponse questionEditResponse = new QuestionEditResponse().id(questionEntity.getUuid()).status("Question Updated Successfully");
        return new ResponseEntity<QuestionEditResponse>(questionEditResponse, HttpStatus.OK );
    }

    @RequestMapping(method = RequestMethod.GET, path="question/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestions(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException,InvalidQuestionException {
        String [] bearerToken = authorization.split("Bearer ");

        List<QuestionEntity> QB = questionBusinessService.getAllQuestions(bearerToken[1]);

        List<QuestionDetailsResponse> questions = new ArrayList<>();

        for(QuestionEntity questionEntity : QB){
            QuestionDetailsResponse questionDetailsResponse = new QuestionDetailsResponse().id(questionEntity.getUuid()).content(questionEntity.getContent());
            questions.add(questionDetailsResponse);
        }

        return new ResponseEntity<List<QuestionDetailsResponse>>(questions,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path="question/all/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestionsByUserID(@PathVariable("userId") final String userUUID, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, UserNotFoundException {
        String [] bearerToken = authorization.split("Bearer ");

        List<QuestionEntity> QB = questionBusinessService.getAllQuestionsByUUUID(userUUID, bearerToken[1]);

        List<QuestionDetailsResponse> questions = new ArrayList<>();

        for(QuestionEntity questionEntity : QB){
            QuestionDetailsResponse questionDetailsResponse = new QuestionDetailsResponse().id(questionEntity.getUuid()).content(questionEntity.getContent());
            questions.add(questionDetailsResponse);
        }

        return new ResponseEntity<List<QuestionDetailsResponse>>(questions,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path="question/delete/{questionId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionDeleteResponse> deleteQuestion(@PathVariable("questionId") final String questionUUID, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, InvalidQuestionException
    {
        String [] bearerToken = authorization.split("Bearer ");

        String DelQuestionUUID = questionBusinessService.deleteQuestionByUuId(questionUUID, bearerToken[1]);

        QuestionDeleteResponse questionDeleteResponse = new QuestionDeleteResponse().id(questionUUID).status("Question Deleted");

        return new ResponseEntity<QuestionDeleteResponse>(questionDeleteResponse, HttpStatus.OK);

    }
}
