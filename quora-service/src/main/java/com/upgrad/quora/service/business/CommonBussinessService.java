package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class CommonBussinessService {

    @Autowired
    private UserDao userDao;

    public UserEntity getUser(final String id , final String authorizedToken) throws AuthorizationFailedException, UserNotFoundException {

        UserAuthTokenEntity userAuth =  userDao.checkToken(authorizedToken);

        if(userAuth == null)
        {
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        final ZonedDateTime signOutUserTime = userAuth.getLogoutAt();

        if(signOutUserTime!=null && userAuth!=null)
        {
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
        }
        UserEntity user = userAuth.getUser();

        if(id.equals(user.getUuid()))
        {
            return user;
        }
        else {
            throw new UserNotFoundException("USR-001", "User with entered uuid does not exist .");
        }
    }
}
