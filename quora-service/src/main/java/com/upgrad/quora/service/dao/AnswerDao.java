package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    public AnswerEntity createAnswer(AnswerEntity answerEntity){
        entityManager.persist(answerEntity);
        return answerEntity;
    }

    public UserAuthTokenEntity getUserAuthToken(final String accesstoken){
        try{
            return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class).setParameter("accessToken", accesstoken).getSingleResult();
        } catch (NoResultException nre){
            return null;
        }
    }

    public AnswerEntity getAnswerByUUID(final String answerUUID){
        try{
            return entityManager.createNamedQuery("answerByUuid", AnswerEntity.class).setParameter("uuid", answerUUID).getSingleResult();
        } catch (NoResultException nre){
            return null;
        }
    }

    public AnswerEntity updateAnswer(AnswerEntity answerEntity){
        entityManager.merge(answerEntity);
        return answerEntity;
    }

    public List<AnswerEntity> getAllAnswersToQuestion(Integer questionId){
        try{
            return entityManager.createNamedQuery("answersByQuestion", AnswerEntity.class).setParameter("questionId", questionId).getResultList();
        } catch (NoResultException nre){
            return null;
        }
    }

    public void deleteAnswer(AnswerEntity answerEntity){
        entityManager.remove(answerEntity);
    }
}
