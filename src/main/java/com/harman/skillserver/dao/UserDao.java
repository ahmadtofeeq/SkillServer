package com.harman.skillserver.dao;

import com.harman.Model.AppModel.LoginModel;
import com.harman.Model.AppModel.User;

public interface UserDao {

	void register(User user);

	User validateUser(LoginModel login);

}
