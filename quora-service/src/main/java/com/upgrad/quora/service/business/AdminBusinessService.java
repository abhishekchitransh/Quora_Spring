package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminBusinessService {

    @Autowired
    private CommonBussinessService commonBussinessService;

    @Autowired
    UserDao userDao;
    @Transactional(propagation = Propagation.REQUIRED)

    public String deleteUser(final String userid , final String accessToken) throws AuthorizationFailedException, UserNotFoundException {

       UserEntity userEntity = commonBussinessService.getUser(userid,accessToken);
        UserAuthTokenEntity userAuthTokenEntity = userDao.checkToken(accessToken);

        if(userAuthTokenEntity.getUser().getRole().equals("admin")){
            return userDao.deleteUser(userid);
        }
        else
        {
            throw new AuthorizationFailedException("ATHR-003","Unauthorized Access, Entered user is not an admin");
        }
    }
}
