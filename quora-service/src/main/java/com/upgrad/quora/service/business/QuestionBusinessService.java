package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;


@Service
public class QuestionBusinessService {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity createQuestion(QuestionEntity questionEntity, final String authorizaionToken) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuth =  userDao.checkToken(authorizaionToken);


        if(userAuth == null)
        {
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        final ZonedDateTime signOutUserTime = userAuth.getLogoutAt();

        if(signOutUserTime!=null && userAuth != null)
        {
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
        }

        questionEntity.setUser_id(userAuth.getUser());
        return questionDao.createQuestion(questionEntity);

    }

    public QuestionEntity getQuestionByUUID(final String questionUUID) throws InvalidQuestionException {
        QuestionEntity questionEntity = questionDao.getQuestionByUUID(questionUUID);
        if(questionEntity == null){
            throw new InvalidQuestionException("QUES-001","The question entered is invalid");
        }
        return questionEntity;
    }
}
