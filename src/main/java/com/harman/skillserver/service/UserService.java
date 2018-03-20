package com.harman.skillserver.service;

import org.springframework.stereotype.Service;

import com.harman.Model.AppModel.LoginModel;
import com.harman.Model.AppModel.User;


@Service
public interface UserService {

	void addNewUser(User user);
	
	User validateUser(LoginModel login);

}
