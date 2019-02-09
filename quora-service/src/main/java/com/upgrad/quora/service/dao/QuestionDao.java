package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public QuestionEntity createQuestion(QuestionEntity questionEntity){
        entityManager.persist(questionEntity);

        return questionEntity;
    }

    public QuestionEntity getQuestionByUUID(final String questionUUID){
        try{
            return entityManager.createNamedQuery("qustionByUuid", QuestionEntity.class).setParameter("uuid", questionUUID).getSingleResult();
        } catch (NoResultException nre){
            return null;
        }
    }


}
